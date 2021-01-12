package com.example.architecture.collection.ui.user

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.architecture.databinding.ListItemUserBinding
import com.example.data.entity.UserEntity
import timber.log.Timber

class UserViewHolder internal constructor(
    private val binding: ListItemUserBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: UserEntity?) {
        binding.root.tag = item
        binding.userName.text = item?.name
        Glide.with(binding.root).load(item?.avatarUrl)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(binding.avatar)
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