package com.example.architecture.collection.ui.user

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.data.entity.UserEntity
import com.example.architecture.databinding.ListItemUserBinding

class UserViewHolder internal constructor(
    private val binding: ListItemUserBinding
) : RecyclerView.ViewHolder(binding.root) {

    val viewModel: ItemViewModel = ItemViewModel()

    private var item: UserEntity? = null

    init {
        binding.itemModel = viewModel
    }

    fun onBindViewHolder(item: UserEntity?) {
        this.item = item
        binding.root.tag = item
        viewModel.setUserName(item?.name)
        viewModel.setAvatarUrl(item?.avatarUrl)
    }

    fun onBindViewHolder(payload: Payload) {
        payload.name?.let { viewModel.setUserName(item?.name) }
        payload.url?.let { viewModel.setAvatarUrl(item?.avatarUrl) }
    }

    data class Payload(val name: String?, val url: String?)

    class ItemViewModel {
        private val _userName: MutableLiveData<String?> = MutableLiveData()
        val userName: LiveData<String?> get() = _userName

        private val _avatarUrl: MutableLiveData<String?> = MutableLiveData()
        val avatarUrl: LiveData<String?> get() = _avatarUrl

        fun setUserName(name: String?) {
            _userName.value = name
        }

        fun setAvatarUrl(url: String?) {
            _avatarUrl.value = url
        }
    }
}