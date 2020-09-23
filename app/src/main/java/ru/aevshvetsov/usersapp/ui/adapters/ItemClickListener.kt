package ru.aevshvetsov.usersapp.ui.adapters

import ru.aevshvetsov.usersapp.database.UserEntity

interface ItemClickListener {
    fun onItemClick(item: UserEntity)
}
