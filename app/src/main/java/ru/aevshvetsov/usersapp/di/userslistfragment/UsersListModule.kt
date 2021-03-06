package ru.aevshvetsov.usersapp.di.userslistfragment

import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import ru.aevshvetsov.usersapp.database.UsersAppDatabase
import ru.aevshvetsov.usersapp.network.UsersListApi
import ru.aevshvetsov.usersapp.repositories.IUsersRepository
import ru.aevshvetsov.usersapp.repositories.UsersRepositoryImpl
import ru.aevshvetsov.usersapp.ui.adapters.UsersListAdapter

@Module
class UsersListModule {
    @Provides
    fun provideUsersListApi(retrofit: Retrofit): UsersListApi {
        return retrofit.create(UsersListApi::class.java)
    }

    @Provides
    fun provideUsersListRepository(api: UsersListApi, database: UsersAppDatabase): IUsersRepository {
        return UsersRepositoryImpl(api, database)
    }

    @Provides
    fun provideUsersListAdapter(): UsersListAdapter {
        return UsersListAdapter()
    }

}
