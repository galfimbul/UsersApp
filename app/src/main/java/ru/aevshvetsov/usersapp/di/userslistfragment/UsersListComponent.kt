package ru.aevshvetsov.usersapp.di.userslistfragment

import dagger.Subcomponent
import ru.aevshvetsov.usersapp.ui.screens.UsersFragment

@Subcomponent(
    modules = [UsersListModule::class,
        UsersListViewModelsModule::class]
)
interface UsersListComponent {
    fun inject(usersFragment: UsersFragment)

}
