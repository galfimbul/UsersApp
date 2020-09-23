package ru.aevshvetsov.usersapp.ui

import android.Manifest
import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkRequest
import android.os.Bundle
import androidx.annotation.RequiresPermission
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.activity_main.*
import ru.aevshvetsov.usersapp.R
import ru.aevshvetsov.usersapp.database.UserEntity
import ru.aevshvetsov.usersapp.ui.adapters.ItemClickListener
import ru.aevshvetsov.usersapp.ui.screens.UserDetailsFragment
import ru.aevshvetsov.usersapp.ui.screens.UsersFragment
import timber.log.Timber

class MainActivity : AppCompatActivity(), ItemClickListener {
    lateinit var connectivityManager: ConnectivityManager
    lateinit var connectivityManagerCallback: ConnectivityManager.NetworkCallback
    var isNetworkConnected: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        setCheckConnections()
        if (savedInstanceState == null) {
            setFragment(UsersFragment.newInstance(false))
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        connectivityManager.unregisterNetworkCallback(connectivityManagerCallback)
    }


    private fun setCheckConnections() {

        connectivityManager = applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkRequest = NetworkRequest.Builder().build()
        connectivityManagerCallback =
            object : ConnectivityManager.NetworkCallback() {

                private val activeNetworks: MutableList<Network> = mutableListOf()

                @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
                override fun onAvailable(network: Network) {
                    super.onAvailable(network)

                    // Add to list of active networks if not already in list
                    if (activeNetworks.none { activeNetwork -> activeNetwork.networkHandle == network.networkHandle }) activeNetworks.add(
                        network
                    )
                    isNetworkConnected = activeNetworks.isNotEmpty()
                }

                @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
                override fun onLost(network: Network) {
                    super.onLost(network)

                    // Remove network from active network list
                    activeNetworks.removeAll { activeNetwork -> activeNetwork.networkHandle == network.networkHandle }
                    isNetworkConnected = activeNetworks.isNotEmpty()
                }
            }
        connectivityManager.registerNetworkCallback(networkRequest, connectivityManagerCallback)
        Timber.d("setCheckConnections() is successful")
    }

    private fun setFragment(fragment: Fragment) {
        Timber.d("setFragment: ${fragment.javaClass.simpleName}")
        supportFragmentManager
            .beginTransaction()
            .setCustomAnimations(R.anim.left_right, R.anim.right_left, R.anim.right_left, R.anim.left_right)
            .replace(R.id.fragment_container, fragment)
            .commit()
    }

    private fun setFragmentAndAddToBackStack(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .setCustomAnimations(R.anim.right_left, R.anim.left_right, R.anim.right_left, R.anim.left_right)
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }

    override fun onItemClick(item: UserEntity) {
        val fragment = UserDetailsFragment.newInstance(item.id.toString())
        setFragmentAndAddToBackStack(fragment)
    }
}
