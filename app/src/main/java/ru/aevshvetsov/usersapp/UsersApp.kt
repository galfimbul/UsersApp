package ru.aevshvetsov.usersapp

import android.app.Application
import ru.aevshvetsov.usersapp.di.AppComponentDagger
import ru.aevshvetsov.usersapp.di.AppModuleDagger
import ru.aevshvetsov.usersapp.di.DaggerAppComponentDagger
import timber.log.Timber

/**
 * Created by Alexander Shvetsov on 22.09.2020
 */
class UsersApp : Application() {
    lateinit var appComponent: AppComponentDagger
    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponentDagger.builder()
            .appModuleDagger(AppModuleDagger(this))
            .build()
        Timber.plant(Timber.DebugTree())
    }
}