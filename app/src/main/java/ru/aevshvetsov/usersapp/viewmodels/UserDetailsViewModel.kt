package ru.aevshvetsov.usersapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Job
import ru.aevshvetsov.usersapp.database.UserEntity
import ru.aevshvetsov.usersapp.repositories.IUserDetailsRepository
import javax.inject.Inject

class UserDetailsViewModel @Inject constructor(val repository: IUserDetailsRepository) : ViewModel() {

    private val job = Job()

    fun getUsersInfoFromDB(id: String): LiveData<UserEntity> {
        return repository.getUsersInfoFromDatabase(id)
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }

    fun deleteUserFromDatabase(item: UserEntity) {
        repository.deleteUserFromDatabase(item)
    }

    fun saveUserInfoChangesToDB(changedUser: UserEntity) {
        repository.saveUserInfoChangesToDB(changedUser)
    }
}
