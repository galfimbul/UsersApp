package ru.aevshvetsov.usersapp.ui.screens

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_users.*
import ru.aevshvetsov.usersapp.R
import ru.aevshvetsov.usersapp.UsersApp
import ru.aevshvetsov.usersapp.database.UserEntity
import ru.aevshvetsov.usersapp.ui.adapters.ItemDismissListener
import ru.aevshvetsov.usersapp.ui.adapters.ItemOnClickListener
import ru.aevshvetsov.usersapp.ui.adapters.UsersListAdapter
import ru.aevshvetsov.usersapp.utils.UsersListTouchHelper
import ru.aevshvetsov.usersapp.viewmodels.UsersViewModel
import ru.aevshvetsov.usersapp.viewmodels.ViewModelProviderFactory
import javax.inject.Inject

class UsersFragment : Fragment() {

    @Inject
    lateinit var factory: ViewModelProviderFactory
    private val usersViewModel by lazy {
        ViewModelProvider(this, factory).get(UsersViewModel::class.java)
    }
    @Inject
    lateinit var usersListAdapter: UsersListAdapter
    lateinit var usersList: List<UserEntity>
    private var itemClickListener: ItemClickListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val component = (requireActivity().application as UsersApp).appComponent.getUsersListSubcomponent()
        component.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_users, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        usersViewModel.getUserRequest()
        usersViewModel.getUsersFromDB().observe(this) {
            usersList = it
            updateUI()
        }
        users_list_refresh.setOnRefreshListener {
            usersViewModel.getUserRequest()
        }
    }

    private fun updateUI() {
        val decoration = DividerItemDecoration(activity, DividerItemDecoration.VERTICAL)
        val touchHandler = ItemTouchHelper(
            UsersListTouchHelper(
                usersListAdapter,
                ItemTouchHelper.UP or ItemTouchHelper.DOWN,
                ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
            )
        )
        with(rv_users_list) {
            addItemDecoration(decoration)
            layoutManager = LinearLayoutManager(activity)
            adapter = usersListAdapter
        }
        touchHandler.attachToRecyclerView(rv_users_list)
        usersListAdapter.submitList(usersList)
        if (users_list_refresh.isRefreshing) {
            users_list_refresh.isRefreshing = false
        }
        usersListAdapter.attachItemDismissListener(object : ItemDismissListener {
            override fun deleteItemFromDatabase(item: UserEntity) {
                usersViewModel.deleteItemFromDatabase(item)
            }
        })
        usersListAdapter.attachItemOnClickListener(object : ItemOnClickListener {
            override fun click(item: View) {
                val index = rv_users_list.getChildAdapterPosition(item)
                itemClickListener?.onItemClick(usersList[index])

            }
        })
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is ItemClickListener) {
            itemClickListener = context
        }
    }

    override fun onDetach() {
        super.onDetach()
        itemClickListener = null
    }

    companion object {
        @JvmStatic
        fun newInstance() = UsersFragment()
    }
}
