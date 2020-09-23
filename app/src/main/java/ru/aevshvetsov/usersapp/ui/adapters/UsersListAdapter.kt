package ru.aevshvetsov.usersapp.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.aevshvetsov.usersapp.Constants.BLANK_VIEW_TYPE
import ru.aevshvetsov.usersapp.Constants.NORMAL_VIEW_TYPE
import ru.aevshvetsov.usersapp.R
import ru.aevshvetsov.usersapp.database.UserEntity
import ru.aevshvetsov.usersapp.ui.viewholders.BlankUserViewHolder
import ru.aevshvetsov.usersapp.ui.viewholders.UsersListViewHolder
import timber.log.Timber

/**
 * Created by Alexander Shvetsov on 22.09.2020
 */
class UsersListAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var itemsList: List<UserEntity> = emptyList()
    private var itemDismissListener: ItemDismissListener? = null
    // обработчик нажатий на элемент RecyclerView
    private var onItemClickListener: ItemOnClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        when (viewType) {
            NORMAL_VIEW_TYPE -> {
                return createNormalViewHolder(parent, inflater)
            }
            BLANK_VIEW_TYPE -> {
                return createBlankViewHolder(parent, inflater)
            }
        }

        val view = inflater.inflate(
            R.layout.users_list_item,
            parent,
            false
        )
        view.setOnClickListener { onItemClickListener?.click(it) }
        return UsersListViewHolder(view)
    }

    private fun createBlankViewHolder(parent: ViewGroup, inflater: LayoutInflater): BlankUserViewHolder {
        val view = inflater.inflate(
            R.layout.users_list_blank_item,
            parent,
            false
        )
        return BlankUserViewHolder(view)

    }

    private fun createNormalViewHolder(parent: ViewGroup, inflater: LayoutInflater): UsersListViewHolder {

        val view = inflater.inflate(
            R.layout.users_list_item,
            parent,
            false
        )
        view.setOnClickListener { onItemClickListener?.click(it) }
        return UsersListViewHolder(view)

    }

    override fun getItemCount(): Int {
        Timber.d("getItemCount called")
        return itemsList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is UsersListViewHolder -> {
                holder.bind(itemsList[position])
            }
            is BlankUserViewHolder -> {
                holder.bind(itemsList[position])
            }
        }

    }

    override fun getItemViewType(position: Int): Int {
        return if (itemsList[position].id == -1)
            BLANK_VIEW_TYPE
        else NORMAL_VIEW_TYPE
    }

    fun submitList(list: List<UserEntity>) {
        Timber.d("submit list called")
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
        itemDismissListener?.deleteItemFromDatabase(itemsList[adapterPosition])
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        itemDismissListener = null
    }
}

interface ItemOnClickListener {
    fun click(item: View)

}
