package ru.aevshvetsov.usersapp.ui.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import kotlinx.android.synthetic.main.users_details_fragment.*
import ru.aevshvetsov.usersapp.R
import ru.aevshvetsov.usersapp.UsersApp
import ru.aevshvetsov.usersapp.database.UserEntity
import ru.aevshvetsov.usersapp.extensions.loadImage
import ru.aevshvetsov.usersapp.viewmodels.UserDetailsViewModel
import ru.aevshvetsov.usersapp.viewmodels.ViewModelProviderFactory
import javax.inject.Inject

private const val ID = "id"

class UserDetailsFragment : Fragment() {
    private var id: String? = null

    @Inject
    lateinit var factory: ViewModelProviderFactory
    private val userDetailsViewModel by lazy {
        ViewModelProvider(this, factory).get(UserDetailsViewModel::class.java)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            id = it.getString(ID)
        }
        val component = (requireActivity().application as UsersApp).appComponent.getUserDetailsSubcomponent()
        component.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.users_details_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userDetailsViewModel.getUsersInfoFromDB(id!!).observe(this) {
            val item = it
            parseItem(item)
        }
    }

    private fun parseItem(item: UserEntity) {
        iv_users_details_avatar.loadImage(item.avatar)
        et_users_details_first_name.setText(item.firstName)
        et_users_details_last_name.setText(item.lastName)
        et_users_details_email.setText(item.email)
    }

    companion object {
        @JvmStatic
        fun newInstance(id: String) =
            UserDetailsFragment().apply {
                arguments = Bundle().apply {
                    putString(ID, id)
                }
            }
    }
}
