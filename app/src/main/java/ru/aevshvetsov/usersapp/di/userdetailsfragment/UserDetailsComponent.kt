package ru.aevshvetsov.usersapp.di.userdetailsfragment

import dagger.Subcomponent
import ru.aevshvetsov.usersapp.ui.screens.UserDetailsFragment

@Subcomponent(
    modules = [UserDetailsModule::class,
        UserDetailsViewModelsModule::class]
)
interface UserDetailsComponent {
    fun inject(userDetailsFragment: UserDetailsFragment)

}
