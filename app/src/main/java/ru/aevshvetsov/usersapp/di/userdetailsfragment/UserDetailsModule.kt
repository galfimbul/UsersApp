package ru.aevshvetsov.usersapp.di.userdetailsfragment

import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import ru.aevshvetsov.usersapp.network.UsersListApi
import ru.aevshvetsov.usersapp.ui.adapters.UsersListAdapter

@Module
class UserDetailsModule {
    @Provides
    fun provideListOfBoardsApi(retrofit: Retrofit): UsersListApi {
        return retrofit.create(UsersListApi::class.java)
    }

    @Provides
    fun provideListOfBoardsAdapter(): UsersListAdapter {
        return UsersListAdapter()
    }

}
