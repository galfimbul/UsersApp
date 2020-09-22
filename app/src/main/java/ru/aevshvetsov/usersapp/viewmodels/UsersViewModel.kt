package ru.aevshvetsov.usersapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import kotlinx.coroutines.Dispatchers
import ru.aevshvetsov.usersapp.models.NetworkResponse
import ru.aevshvetsov.usersapp.repositories.IUsersRepository
import javax.inject.Inject

class UsersViewModel @Inject constructor(val repository: IUsersRepository) : ViewModel() {

    fun getUsersFromServer(): LiveData<NetworkResponse> {
        return liveData(Dispatchers.IO) {
            val feedList = repository.getUsersFromServer()
            emit(feedList)
        }
    }
}
