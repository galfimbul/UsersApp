package ru.aevshvetsov.usersapp.ui.screens

import ru.aevshvetsov.usersapp.database.UserEntity

interface ItemClickListener {
    fun onItemClick(item: UserEntity)
}
