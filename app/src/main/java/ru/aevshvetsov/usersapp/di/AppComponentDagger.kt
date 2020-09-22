package ru.aevshvetsov.usersapp.di

import android.app.Application
import android.content.Context
import dagger.Component
import ru.aevshvetsov.usersapp.di.userdetailsfragment.UserDetailsComponent
import ru.aevshvetsov.usersapp.di.userslistfragment.UsersListComponent
import javax.inject.Singleton

/**
 * Created by Alexander Shvetsov on 26.01.2020
 */
@Singleton
@Component(modules = [AppModuleDagger::class, ViewModelFactoryModule::class])
interface AppComponentDagger {
    fun context(): Context
    fun applicationContext(): Application
    fun getUsersListSubcomponent(): UsersListComponent
    fun getUserDetailsSubcomponent(): UserDetailsComponent

}