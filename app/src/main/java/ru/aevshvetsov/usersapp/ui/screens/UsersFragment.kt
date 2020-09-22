package ru.aevshvetsov.usersapp.ui.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import kotlinx.android.synthetic.main.fragment_users.*
import ru.aevshvetsov.usersapp.R
import ru.aevshvetsov.usersapp.UsersApp
import ru.aevshvetsov.usersapp.viewmodels.UsersViewModel
import ru.aevshvetsov.usersapp.viewmodels.ViewModelProviderFactory
import javax.inject.Inject

class UsersFragment : Fragment() {

    @Inject
    lateinit var factory: ViewModelProviderFactory
    private val usersViewModel by lazy {
        ViewModelProvider(this, factory).get(UsersViewModel::class.java)
    }

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
        usersViewModel.getUsersFromServer().observe(this) {
            tv_test.text = it.users[0].email

        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = UsersFragment()
    }
}
