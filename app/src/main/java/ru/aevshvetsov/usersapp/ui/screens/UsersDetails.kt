package ru.aevshvetsov.usersapp.ui.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import ru.aevshvetsov.usersapp.R
import ru.aevshvetsov.usersapp.viewmodels.UsersDetailsViewModel

private const val ID = "id"

class UsersDetails : Fragment() {
    private var id: String? = null

    companion object {
        @JvmStatic
        fun newInstance(id: String) =
            UsersDetails().apply {
                arguments = Bundle().apply {
                    putString(ID, id)
                }
            }
    }

    private lateinit var viewModel: UsersDetailsViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            id = it.getString(ID)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.users_details_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(UsersDetailsViewModel::class.java)
    }
}
