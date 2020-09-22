package ru.aevshvetsov.usersapp.di.userdetailsfragment

import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import ru.aevshvetsov.usersapp.database.UsersAppDatabase
import ru.aevshvetsov.usersapp.network.UsersListApi
import ru.aevshvetsov.usersapp.repositories.IUserDetailsRepository
import ru.aevshvetsov.usersapp.repositories.UserDetailsRepositoryImpl

@Module
class UserDetailsModule {
    @Provides
    fun provideListOfBoardsApi(retrofit: Retrofit): UsersListApi {
        return retrofit.create(UsersListApi::class.java)
    }
    @Provides
    fun provideUserInfoRepository(database: UsersAppDatabase): IUserDetailsRepository {
        return UserDetailsRepositoryImpl(database)
    }

}
