package ru.aevshvetsov.usersapp.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.aevshvetsov.usersapp.R
import ru.aevshvetsov.usersapp.database.UserEntity
import ru.aevshvetsov.usersapp.ui.viewholders.UsersListViewHolder

/**
 * Created by Alexander Shvetsov on 22.09.2020
 */
class UsersListAdapter : RecyclerView.Adapter<UsersListViewHolder>() {

    private lateinit var itemsList: MutableList<UserEntity>
    private lateinit var itemDismissListener: ItemDismissListener
    private var onItemClickListener: ItemOnClickListener? =
        null // обработчик нажатий для элементов RecyclerView

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsersListViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(
            R.layout.users_list_item,
            parent,
            false
        )
        view.setOnClickListener { onItemClickListener?.click(it) }
        return UsersListViewHolder(view)
    }

    override fun getItemCount(): Int {
        return itemsList.size
    }

    override fun onBindViewHolder(holder: UsersListViewHolder, position: Int) {
        holder.bind(itemsList[position])
    }

    fun submitList(list: List<UserEntity>) {
        itemsList = list.toMutableList()
        notifyDataSetChanged()
    }

    fun attachItemDismissListener(listener: ItemDismissListener) {
        itemDismissListener = listener
    }

    fun attachItemOnClickListener(listener: ItemOnClickListener) {
        onItemClickListener = listener
    }

    fun onItemDismiss(adapterPosition: Int) {
        itemDismissListener.deleteItemFromDatabase(itemsList[adapterPosition])
    }
}

interface ItemOnClickListener {
    fun click(item: View)

}
