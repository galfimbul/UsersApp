package ru.aevshvetsov.usersapp.ui.screens

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_users.*
import ru.aevshvetsov.usersapp.Constants.IS_INITIALIZED_KEY
import ru.aevshvetsov.usersapp.R
import ru.aevshvetsov.usersapp.UsersApp
import ru.aevshvetsov.usersapp.database.UserEntity
import ru.aevshvetsov.usersapp.ui.MainActivity
import ru.aevshvetsov.usersapp.ui.adapters.ItemClickListener
import ru.aevshvetsov.usersapp.ui.adapters.ItemDismissListener
import ru.aevshvetsov.usersapp.ui.adapters.ItemOnClickListener
import ru.aevshvetsov.usersapp.ui.adapters.UsersListAdapter
import ru.aevshvetsov.usersapp.utils.UsersListTouchHelper
import ru.aevshvetsov.usersapp.viewmodels.UsersViewModel
import ru.aevshvetsov.usersapp.viewmodels.ViewModelProviderFactory
import timber.log.Timber
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
    private var isInitialized: Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val component = (requireActivity().application as UsersApp).appComponent.getUsersListSubcomponent()

        component.inject(this)

        arguments?.let {
            isInitialized = it.getBoolean(IS_INITIALIZED_KEY)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_users, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar()
        updateUI()
        // Если происходит смена конфигурации, запрос в интернет не нужен. В базе актуальные данные
        if (!usersViewModel.isInitialize)
            getUsersNetworkRequest()
        // Подписываемся на изменения списка пользователей из базы
        usersViewModel.getUsersFromDB().observe(this) { listFromDB ->
            users_list_refresh.isRefreshing = false
            when {
                listFromDB == null || listFromDB.isEmpty() -> {
                    Timber.d("users list from DB: null or empty")
                    usersList =
                        listOf(UserEntity(id = -1, firstName = getString(R.string.users_list_empty_database_error)))
                    usersListAdapter.submitList(usersList)
                }
                else -> {
                    Timber.d("users list from DB: ${listFromDB.joinToString()}")
                    usersList = listFromDB
                    usersListAdapter.submitList(usersList)
                    usersViewModel.isInitialize = true
                }
            }

        }
        users_list_refresh.setOnRefreshListener {
            getUsersNetworkRequest()
        }
    }

    private fun initToolbar() {
        activity?.setTitle(R.string.users_fragment_title)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
        (activity as AppCompatActivity).supportActionBar?.setDisplayShowHomeEnabled(false)
    }

    private fun checkInternetConnection(): Boolean {
        return (activity as MainActivity).isNetworkConnected
    }

    private fun getUsersNetworkRequest() {
        if (checkInternetConnection()) {
            Timber.d("network is active. network request granted")
            usersViewModel.getUserRequest()

        } else {
            Timber.d("network isn't active. network request forbidden")
            showToast(getString(R.string.users_list_network_lost_results_from_database))
            users_list_refresh.isRefreshing = false
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }


    private fun updateUI() {
        val decoration = DividerItemDecoration(activity, DividerItemDecoration.VERTICAL)

        //Устанавливаем тач хелпер для обработки свайпа
        val touchHandler = ItemTouchHelper(
            UsersListTouchHelper(
                usersListAdapter,
                ItemTouchHelper.LEFT
            )
        )
        with(rv_users_list) {
            addItemDecoration(decoration)
            layoutManager = LinearLayoutManager(activity)
            adapter = usersListAdapter
        }

        touchHandler.attachToRecyclerView(rv_users_list)
        if (users_list_refresh.isRefreshing) {
            users_list_refresh.isRefreshing = false
        }
        //Устанавливаем слушатель свайпа для удаления элемента из базы
        usersListAdapter.attachItemDismissListener(object : ItemDismissListener {
            override fun deleteItemFromDatabase(item: UserEntity) {
                usersViewModel.deleteItemFromDatabase(item)
            }
        })
        //Слушатель клика для открытия фрашмента с деталями
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
        fun newInstance(isInitialized: Boolean) =
            UsersFragment().apply {
                arguments = Bundle().apply {
                    putBoolean(IS_INITIALIZED_KEY, isInitialized)
                }
            }
    }
}
