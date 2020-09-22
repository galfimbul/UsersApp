package ru.aevshvetsov.usersapp.repositories

import androidx.lifecycle.LiveData
import ru.aevshvetsov.usersapp.database.UserEntity
import ru.aevshvetsov.usersapp.models.NetworkResponse

/**
 * Created by Alexander Shvetsov on 22.09.2020
 */
interface IUsersRepository {
    suspend fun getUsersFromServer(): NetworkResponse
    fun getUsersFromDatabase(): LiveData<List<UserEntity>>
    fun setDataToDatabase(feedList: NetworkResponse)
    fun deleteItemFromDatabase(item: UserEntity)
}