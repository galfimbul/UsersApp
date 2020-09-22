package ru.aevshvetsov.usersapp.utils

import android.content.res.Resources

/**
 * Created by Alexander Shvetsov on 22.09.2020
 */
object Utils {
    fun dpToPx(dp: Int): Float {
        return dp.toFloat() * Resources.getSystem().displayMetrics.density
    }
}