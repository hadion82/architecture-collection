package com.example.architecture.collection.ui.user

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.ListPreloader
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.data.entity.UserEntity
import com.example.core.ui.listener.Throttle
import com.example.architecture.databinding.ListItemUserBinding
import timber.log.Timber
import java.util.*

class UserViewHolder internal constructor(
    private val binding: ListItemUserBinding,
    private val throttle: Throttle.ClickListener,
    private val onClick: (item: UserEntity?) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    private val _userName: MutableLiveData<String?> = MutableLiveData()
    val userName: LiveData<String?> get() = _userName

    private val _avatarUrl: MutableLiveData<String?>  = MutableLiveData()
    val avatarUrl: LiveData<String?> get() = _avatarUrl

    var item: UserEntity? = null

    init {
        Timber.d("create view holder")
        throttle.throttleClick(
            binding.root, 500
        ) {
            onClick(item)
        }
    }

    fun onBindViewHolder(item: UserEntity?) {
        Timber.d("onBindViewHolder item: $item")
        binding.itemModel = this
        this.item = item
        _userName.value = item?.name
        _avatarUrl.value = item?.avatarUrl
    }

    fun onBindViewHolder(payload: Payload) {
        Timber.d("onBindViewHolder payload: $payload")
        payload.name?.let { _userName.value = it }
        payload.url?.let { _avatarUrl.value = it }
    }

    data class Payload(val name: String?, val url: String?)
}