package ru.aevshvetsov.usersapp.ui.viewholders

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.users_list_blank_item.view.*
import ru.aevshvetsov.usersapp.database.UserEntity

class BlankUserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val emptyMessage: TextView = itemView.tv_empty_database_message

    fun bind(item: UserEntity) {
        val message = item.firstName
        emptyMessage.text = message
    }

}
