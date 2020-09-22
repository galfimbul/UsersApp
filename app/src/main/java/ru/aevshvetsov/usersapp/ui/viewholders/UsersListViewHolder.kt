package ru.aevshvetsov.usersapp.ui.viewholders

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.users_list_item.view.*
import ru.aevshvetsov.usersapp.database.UserEntity
import ru.aevshvetsov.usersapp.extensions.loadImage

class UsersListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val userName: TextView = itemView.tv_user_name
    private val userAvatar: ImageView = itemView.iv_user_avatar

    fun bind(item: UserEntity) {
        val username = "${item.firstName} ${item.lastName}"
        userName.text = username
        userAvatar.loadImage(item.avatar)
    }

}


