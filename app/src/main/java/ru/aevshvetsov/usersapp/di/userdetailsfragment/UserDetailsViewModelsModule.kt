package ru.aevshvetsov.usersapp.di.userdetailsfragment

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import ru.aevshvetsov.usersapp.di.ViewModelKey
import ru.aevshvetsov.usersapp.viewmodels.UsersViewModel

@Module
abstract class UserDetailsViewModelsModule {
    @Binds
    @IntoMap
    @ViewModelKey(UsersViewModel::class)
    abstract fun bindListOfBoardsViewModel(listOfBoardsViewModel: UsersViewModel): ViewModel

}
