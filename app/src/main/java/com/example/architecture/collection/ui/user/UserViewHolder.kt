package com.example.architecture.collection.ui.user

import androidx.recyclerview.widget.RecyclerView
import com.example.data.entity.UserEntity
import com.example.core.ui.listener.Throttle
import com.example.architecture.databinding.ListItemUserBinding

class UserViewHolder(
    private val binding: ListItemUserBinding,
    private val throttle: Throttle.ClickListener,
    private val onClick: () -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun onBindViewHolder(item: UserEntity?) {
        throttle.throttleClick(
            binding.userName, 200
        ) {
            onClick()
        }
    }
}