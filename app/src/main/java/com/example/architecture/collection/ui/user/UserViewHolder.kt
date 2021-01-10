package com.example.architecture.collection.ui.user

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.architecture.databinding.ListItemUserBinding
import com.example.data.entity.UserEntity

class UserViewHolder internal constructor(
    private val binding: ListItemUserBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: UserEntity?) {
        with(binding) {
            root.tag = item
            userName.text = item?.name
            Glide.with(root).load(item?.avatarUrl)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(avatar)
        }
    }

    fun bind(payload: Payload) {
        with(binding) {
            payload.also {
                it.name?.let { name -> userName.text = name }
                it.url?.let { url ->
                    Glide.with(root).load(url)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(avatar)
                }
            }
        }
    }

    data class Payload(val name: String?, val url: String?)
}