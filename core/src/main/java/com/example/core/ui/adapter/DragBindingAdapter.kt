package com.example.core.ui.adapter

import android.view.MotionEvent
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.ItemTouchHelper
import java.util.*

abstract class DragBindingAdapter<E, B : ViewDataBinding>(private val helper : ItemTouchHelper, br : Int) : BindingAdapter<E, B>(br) {

    override fun onBindViewHolder(holder: ViewHolder<E, B>, position: Int) {
        super.onBindViewHolder(holder, position)
        holder.itemView.setOnTouchListener{
                _, event ->
            if(event.action == MotionEvent.ACTION_DOWN) {
                helper.startDrag(holder)
            }
            return@setOnTouchListener false
        }
    }

    fun onItemMove(fromPosition: Int, toPosition : Int) {
        if (fromPosition < toPosition) {
            for (i in fromPosition until toPosition) {
                Collections.swap(items, i, i + 1)
            }
        } else {
            for (i in fromPosition downTo toPosition + 1) {
                Collections.swap(items, i, i - 1)
            }
        }
        notifyItemMoved(fromPosition, toPosition)
    }
}