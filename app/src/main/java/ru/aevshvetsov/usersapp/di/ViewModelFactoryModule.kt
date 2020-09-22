package ru.aevshvetsov.usersapp.di

import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import ru.aevshvetsov.usersapp.viewmodels.ViewModelProviderFactory

@Module
abstract class ViewModelFactoryModule {
    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelProviderFactory): ViewModelProvider.Factory

}
