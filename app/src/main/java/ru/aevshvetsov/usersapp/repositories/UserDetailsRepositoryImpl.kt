package ru.aevshvetsov.usersapp.repositories

import androidx.lifecycle.LiveData
import ru.aevshvetsov.usersapp.database.UserEntity
import ru.aevshvetsov.usersapp.database.UsersAppDatabase
import javax.inject.Inject

/**
 * Created by Alexander Shvetsov on 22.09.2020
 */
class UserDetailsRepositoryImpl @Inject constructor(val database: UsersAppDatabase) : IUserDetailsRepository {
    private val userInfoDAO = database.UsersListDAO()
    lateinit var userInfoFromDB: LiveData<UserEntity>

    override fun getUsersInfoFromDatabase(id: String): LiveData<UserEntity> {
        userInfoFromDB = userInfoDAO.getUserInfo(id)
        return userInfoFromDB

    }

    override fun deleteItemFromDatabase(item: UserEntity) {
        userInfoDAO.delete(item)
    }

}