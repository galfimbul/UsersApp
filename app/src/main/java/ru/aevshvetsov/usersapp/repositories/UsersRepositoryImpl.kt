package ru.aevshvetsov.usersapp.repositories

import androidx.lifecycle.LiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.aevshvetsov.usersapp.database.UserEntity
import ru.aevshvetsov.usersapp.database.UsersAppDatabase
import ru.aevshvetsov.usersapp.models.NetworkResponse
import ru.aevshvetsov.usersapp.network.UsersListApi
import javax.inject.Inject

/**
 * Created by Alexander Shvetsov on 22.09.2020
 */
class UsersRepositoryImpl @Inject constructor(val retrofit: UsersListApi, val database: UsersAppDatabase) :
    IUsersRepository {

    private val usersListDAO = database.UsersListDAO()
    private val usersListFromDB = usersListDAO.getUsersList()
    override suspend fun getUsersFromServer(): NetworkResponse {
        return retrofit.getUsersList(page = "2")
    }

    override fun getUsersFromDatabase(): LiveData<List<UserEntity>> {
        return usersListFromDB
    }

    override fun setDataToDatabase(feedList: NetworkResponse) {
        CoroutineScope(Dispatchers.IO).launch {
            usersListDAO.insert(feedList.users.map {
                UserEntity(
                    id = it.id,
                    email = it.email,
                    firstName = it.firstName,
                    lastName = it.lastName,
                    avatar = it.avatar
                )
            })
        }
    }

    override fun deleteItemFromDatabase(item: UserEntity) {
        CoroutineScope(Dispatchers.IO).launch {
            usersListDAO.delete(item)
        }

    }
}