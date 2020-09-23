package ru.aevshvetsov.usersapp.database

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by Alexander Shvetsov on 14.02.2020
 */
@Entity(tableName = "users_table")
data class UserEntity(
    @PrimaryKey
    val id: Int,
    var email: String = "",
    var firstName: String = "",
    var lastName: String = "",
    var avatar: String = ""
)