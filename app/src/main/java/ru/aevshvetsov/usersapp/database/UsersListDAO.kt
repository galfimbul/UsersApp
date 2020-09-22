package ru.aevshvetsov.usersapp.database

import androidx.lifecycle.LiveData
import androidx.room.*

/**
 * Created by Alexander Shvetsov on 14.02.2020
 */
@Dao
interface UsersListDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(userList: List<UserEntity>)

    @Update
    fun update(userList: List<UserEntity>)

    @Delete
    fun delete(user: UserEntity)

    @Query("DELETE FROM users_table")
    fun deleteAllUsers()

    @Query("SELECT * FROM users_table ORDER BY id")
    fun getUsersList(): LiveData<List<UserEntity>>

    @Query("SELECT * FROM users_table WHERE id =:currencyId")
    fun getUserInfo(currencyId: String): LiveData<UserEntity>

}