package ru.aevshvetsov.usersapp.ui.screens

import android.os.Bundle
import android.view.*
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
    lateinit var userInfo: UserEntity

    @Inject
    lateinit var factory: ViewModelProviderFactory
    private val userDetailsViewModel by lazy {
        ViewModelProvider(this, factory).get(UserDetailsViewModel::class.java)
    }
    lateinit var toolbarMenu: Menu
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
        initToolbar()
        userDetailsViewModel.getUsersInfoFromDB(id!!).observe(this) {
            userInfo = it
            parseItem(userInfo)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.toolbar_menu, menu)
        toolbarMenu = menu
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_edit -> {
                setEditEnabled()
            }
            R.id.menu_apply -> {
                saveChangesToDB()
            }
            R.id.menu_delete -> {
                deleteUserFromDB(userInfo)
            }
        }
        return true
    }

    private fun deleteUserFromDB(userInfo: UserEntity) {
        userDetailsViewModel.deleteUserFromDatabase(userInfo)
        activity?.supportFragmentManager!!.popBackStack()
    }

    private fun saveChangesToDB() {
        val editMenuItem = toolbarMenu.findItem(R.id.menu_edit)
        val applyMenuItem = toolbarMenu.findItem(R.id.menu_apply)
        editMenuItem.isVisible = true
        applyMenuItem.isVisible = false
        setEditTextEnableState(false)
        val changedFirstName = et_users_details_first_name.text.toString()
        val changedLastName = et_users_details_last_name.text.toString()
        val changedEmail = et_users_details_email.text.toString()

        val changedUser = userInfo
            .copy(
                firstName = changedFirstName,
                lastName = changedLastName,
                email = changedEmail
            )
        userDetailsViewModel.saveUserInfoChangesToDB(changedUser)
    }

    private fun setEditEnabled() {
        val editMenuItem = toolbarMenu.findItem(R.id.menu_edit)
        val applyMenuItem = toolbarMenu.findItem(R.id.menu_apply)
        editMenuItem.isVisible = false
        applyMenuItem.isVisible = true
        setEditTextEnableState(true)

    }

    private fun setEditTextEnableState(state: Boolean) {
        et_users_details_first_name.isEnabled = state
        et_users_details_last_name.isEnabled = state
        et_users_details_email.isEnabled = state
    }

    private fun initToolbar() {
        setHasOptionsMenu(true)
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
