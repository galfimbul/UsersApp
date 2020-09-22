package ru.aevshvetsov.usersapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import ru.aevshvetsov.usersapp.database.UserEntity
import ru.aevshvetsov.usersapp.repositories.IUsersRepository
import javax.inject.Inject

class UsersViewModel @Inject constructor(val repository: IUsersRepository) : ViewModel() {

    private val job = Job()

    fun getUsersFromDB(): LiveData<List<UserEntity>> {
        return repository.getUsersFromDatabase()
    }

    fun getUserRequest() {
        CoroutineScope(Dispatchers.IO + job).launch {
            val feedList = repository.getUsersFromServer()
            repository.setDataToDatabase(feedList)

        }
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }

    fun deleteItemFromDatabase(item: UserEntity) {
        repository.deleteItemFromDatabase(item)
    }
}
