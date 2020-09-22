package ru.aevshvetsov.usersapp.repositories

import ru.aevshvetsov.usersapp.models.NetworkResponse

/**
 * Created by Alexander Shvetsov on 22.09.2020
 */
interface IUsersRepository {
    suspend fun getUsersFromServer(): NetworkResponse
    fun getUsersFromDatabase()
}