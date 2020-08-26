package com.example.architecture.collection.ui.user

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.bumptech.glide.Glide
import com.bumptech.glide.ListPreloader
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.architecture.R
import com.example.architecture.databinding.ListItemUserBinding
import com.example.core.ui.listener.Throttle
import com.example.data.entity.UserEntity
import java.util.*

class UserAdapter(
    private val context: Context,
    private val throttle: Throttle.ClickListener,
    private val onClick: (item: UserEntity?) -> Unit
) :
    PagingDataAdapter<UserEntity, UserViewHolder>(
        UserDiffCallback()
    ) {

    class UserDiffCallback : DiffUtil.ItemCallback<UserEntity>() {
        override fun areItemsTheSame(oldItem: UserEntity, newItem: UserEntity) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: UserEntity, newItem: UserEntity) =
            oldItem.name == newItem.name
                    && oldItem.avatarUrl == newItem.avatarUrl

        override fun getChangePayload(oldItem: UserEntity, newItem: UserEntity): Any? =
            UserViewHolder.Payload(
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
        holder.onBindViewHolder(getItem(position))

    override fun onBindViewHolder(
        holder: UserViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if(payloads.isNullOrEmpty())
            super.onBindViewHolder(holder, position, payloads)
        else
            holder.onBindViewHolder(payloads[0] as UserViewHolder.Payload)
    }

    inner class AvatarPreloadModelProvider: ListPreloader.PreloadModelProvider<UserEntity> {
        override fun getPreloadItems(position: Int): MutableList<UserEntity> =
            Collections.singletonList(getItem(position))

        override fun getPreloadRequestBuilder(item: UserEntity): RequestBuilder<*>? =
            Glide.with(context)
                .load(item.avatarUrl)
                .placeholder(R.drawable.ic_profile_default)
                .error(R.drawable.ic_profile_default)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
    }

    fun createPreLoader() : AvatarPreloadModelProvider =
        AvatarPreloadModelProvider()
}