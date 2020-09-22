package ru.aevshvetsov.usersapp.ui.adapters

import ru.aevshvetsov.usersapp.database.UserEntity

interface ItemDismissListener {
    fun deleteItemFromDatabase(item: UserEntity)

}
