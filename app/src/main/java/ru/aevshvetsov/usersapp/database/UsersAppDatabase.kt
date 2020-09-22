package ru.aevshvetsov.usersapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/**
 * Created by Alexander Shvetsov on 14.02.2020
 */
@Database(entities = [UserEntity::class], version = 1, exportSchema = false)
abstract class UsersAppDatabase : RoomDatabase() {

    abstract fun UsersListDAO(): UsersListDAO

    companion object {
        @Volatile
        private var INSTANCE: UsersAppDatabase? = null

        fun getDatabase(context: Context): UsersAppDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    UsersAppDatabase::class.java,
                    "converter_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}