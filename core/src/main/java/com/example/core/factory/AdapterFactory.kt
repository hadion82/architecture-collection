package com.example.core.factory

import androidx.recyclerview.widget.RecyclerView

interface AdapterFactory<VH: RecyclerView.ViewHolder> {

    fun createAdapter(): RecyclerView.Adapter<VH>
}