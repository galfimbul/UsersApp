package ru.aevshvetsov.usersapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.activity_main.*
import ru.aevshvetsov.usersapp.R
import ru.aevshvetsov.usersapp.database.UserEntity
import ru.aevshvetsov.usersapp.ui.screens.ItemClickListener
import ru.aevshvetsov.usersapp.ui.screens.UserDetailsFragment
import ru.aevshvetsov.usersapp.ui.screens.UsersFragment

class MainActivity : AppCompatActivity(), ItemClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        setFragment(UsersFragment.newInstance())
    }

    private fun setFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }

    private fun setFragmentAndAddToBackStack(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }

    override fun onItemClick(item: UserEntity) {
        val fragment = UserDetailsFragment.newInstance(item.id.toString())
        setFragmentAndAddToBackStack(fragment)
    }
}
