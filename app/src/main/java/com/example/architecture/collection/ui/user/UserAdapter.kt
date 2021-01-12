package com.example.architecture.collection.ui.user

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.bumptech.glide.Glide
import com.bumptech.glide.ListPreloader
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.architecture.R
import com.example.architecture.databinding.ListItemUserBinding
import com.example.core.extensions.clicks
import com.example.data.entity.UserEntity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapMerge
import timber.log.Timber
import java.util.*

class UserAdapter(
    private val clickFlow: Flow<View>
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

        override fun getChangePayload(oldItem: UserEntity, newItem: UserEntity): Any =
            UserViewHolder.Payload(
                oldItem.name extract newItem.name,
                oldItem.avatarUrl extract newItem.avatarUrl
            )

        private infix fun <R : Any> R?.extract(newValue: R?) =
            if (newValue != null && this != newValue) newValue else null
    }

    @FlowPreview
    @ExperimentalCoroutinesApi
    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ) = UserViewHolder(
        ListItemUserBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )
    ).also { holder -> clickFlow.flatMapMerge { holder.itemView.clicks() } }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onBindViewHolder(
        holder: UserViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if (payloads.isEmpty())
            super.onBindViewHolder(holder, position, payloads)
        else
            payloads.forEach { holder.bind(it as UserViewHolder.Payload) }
    }

    inner class AvatarPreloadModelProvider(private val context: Context) :
        ListPreloader.PreloadModelProvider<UserEntity> {
        override fun getPreloadItems(position: Int): MutableList<UserEntity> =
            Collections.singletonList(getItem(position))

        override fun getPreloadRequestBuilder(item: UserEntity): RequestBuilder<*>? =
            Glide.with(context)
                .load(item.avatarUrl)
                .placeholder(R.drawable.ic_profile_default)
                .error(R.drawable.ic_profile_default)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
    }

    fun createPreLoader(context: Context): AvatarPreloadModelProvider =
        AvatarPreloadModelProvider(context)
}