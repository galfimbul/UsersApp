package ru.aevshvetsov.usersapp.repositories

import ru.aevshvetsov.usersapp.models.NetworkResponse
import ru.aevshvetsov.usersapp.network.UsersListApi
import javax.inject.Inject

/**
 * Created by Alexander Shvetsov on 22.09.2020
 */
class UsersRepositoryImpl @Inject constructor(val retrofit: UsersListApi) : IUsersRepository {
    override suspend fun getUsersFromServer(): NetworkResponse {
        return retrofit.getUsersList(page = "2")
    }

    override fun getUsersFromDatabase() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}