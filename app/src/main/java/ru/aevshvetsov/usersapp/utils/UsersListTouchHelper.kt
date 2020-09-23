package ru.aevshvetsov.usersapp.utils

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.view.View
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import ru.aevshvetsov.usersapp.R
import ru.aevshvetsov.usersapp.ui.adapters.UsersListAdapter
import ru.aevshvetsov.usersapp.ui.viewholders.UsersListViewHolder

class UsersListTouchHelper(val adapter: UsersListAdapter, swipeDirs: Int) :
    ItemTouchHelper.Callback() {
    private val bgRect = RectF()
    private val bgPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val swipeFlags = swipeDirs
    override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int {

        return if (viewHolder is UsersListViewHolder) {
            makeMovementFlags(0, swipeFlags)

        } else {
            makeFlag(ItemTouchHelper.ACTION_STATE_IDLE, ItemTouchHelper.START)
        }
    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return true
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        adapter.onItemDismiss(viewHolder.adapterPosition)
    }

    override fun onChildDraw(
        canvas: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
            val itemView = viewHolder.itemView
            if (dX < 0)
                drawText(canvas, itemView, dX)
        }
        super.onChildDraw(canvas, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
    }

    private fun drawText(canvas: Canvas, itemView: View, dX: Float) {
        val space = itemView.resources.getDimensionPixelSize(R.dimen.spacing_normal_16)
        with(bgRect) {
            left = itemView.right.toFloat() + dX
            top = itemView.top.toFloat()
            right = itemView.right.toFloat()
            bottom = itemView.bottom.toFloat()
        }
        with(bgPaint) {
            color = Color.RED
            textSize = itemView.resources.getDimensionPixelSize(R.dimen.icon_size).toFloat()
        }
        canvas.drawRect(bgRect, bgPaint)
        bgPaint.color = Color.WHITE
        canvas.drawText(
            itemView.resources.getString(R.string.text_on_swipe),
            bgRect.left + space,
            bgRect.centerY() - (bgPaint.descent() + bgPaint.ascent()) / 2,
            bgPaint
        )
    }

    interface ItemTouchHelperAdapter {

        fun onItemMove(fromPosition: Int, toPosition: Int)

        fun onItemDismiss(position: Int)
    }

}