package com.example.architecture

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import com.example.data.entity.UserEntity
import com.example.core.ui.listener.Throttle
import com.example.architecture.databinding.ListItemUserBinding

class UserAdapter(
    private val throttle: Throttle.ClickListener,
    private val onClick: () -> Unit
) :
    PagedListAdapter<UserEntity, UserViewHolder>(UserDiffCallback()) {

    class UserDiffCallback : DiffUtil.ItemCallback<UserEntity>() {
        override fun areItemsTheSame(oldItem: UserEntity, newItem: UserEntity) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: UserEntity, newItem: UserEntity) =
            oldItem.name == newItem.name
                    && oldItem.avatarUrl == newItem.avatarUrl

        override fun getChangePayload(oldItem: UserEntity, newItem: UserEntity): Any? =
            Payload(
                extractItem(oldItem.name, newItem.name),
                extractItem(oldItem.avatarUrl, newItem.avatarUrl)
            )

        private fun <R> extractItem(oldValue: R?, newValue: R?): R? =
            if (oldValue != newValue) newValue else null
    }

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ) = UserViewHolder(
        ListItemUserBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        ), throttle, onClick
    )

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) =
        holder.onBindViewHolder(currentList?.get(position))

    data class Payload(val name: String?, val avatarUrl: String?)
}