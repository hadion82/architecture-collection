package com.example.core.ui.adapter

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

abstract class BindingAdapter<E, B : ViewDataBinding> (val br : Int) : RecyclerView.Adapter<BindingAdapter.ViewHolder<E, B>>() {

    lateinit var items : MutableList<E>

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder<E, B>, position: Int) {
        holder.onBindViewHolder(items[position])
    }

    class ViewHolder<E, B : ViewDataBinding>(val binding: B, val br : Int) : RecyclerView.ViewHolder(binding.root) {

        fun onBindViewHolder(item : E) {
            binding.setVariable(br, item)
            binding.executePendingBindings()
        }
    }
}