package com.example.core.ui.adapter

import androidx.databinding.ObservableArrayList
import androidx.recyclerview.widget.RecyclerView

abstract class ObservableBindingAdapter<E, VH : RecyclerView.ViewHolder>: RecyclerView.Adapter<VH> {

    lateinit var items : ObservableArrayList<E>

    constructor()

    constructor(items : ObservableArrayList<E>) {
        this.items = items
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun setItem(items : ObservableArrayList<E>) {
        if(items.isNotEmpty()) {
            this.items = items
        }
    }
}