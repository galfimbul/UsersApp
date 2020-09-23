package ru.aevshvetsov.usersapp.repositories

import androidx.lifecycle.LiveData
import ru.aevshvetsov.usersapp.database.UserEntity

/**
 * Created by Alexander Shvetsov on 22.09.2020
 */
interface IUserDetailsRepository {
    fun getUsersInfoFromDatabase(id: String): LiveData<UserEntity>
    fun deleteUserFromDatabase(item: UserEntity)
    fun saveUserInfoChangesToDB(changedUser: UserEntity)
}