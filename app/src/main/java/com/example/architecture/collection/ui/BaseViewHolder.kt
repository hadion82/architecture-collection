package com.example.architecture.collection.ui

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class BaseViewHolder<T>(root: View) : RecyclerView.ViewHolder(root) {
    abstract fun onBindViewHolder(item: T, position: Int)
}