package com.example.architecture.collection.ui.user

import android.os.Bundle
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.architecture.R
import com.example.architecture.databinding.ActivityUserBinding
import com.example.core.ui.activity.ComponentActivity
import com.example.data.repository.UserRepository
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class UserActivity: ComponentActivity<ActivityUserBinding>() {

    @Inject lateinit var repository: UserRepository

    override fun layout(): Int =
        R.layout.activity_user

    override fun onBind(savedInstanceState: Bundle?, binding: ActivityUserBinding) {
        val pagedListConfig = PagedList.Config.Builder()
            .setPageSize(20)
            .setInitialLoadSizeHint(20)
            .setPrefetchDistance(10)
            .setEnablePlaceholders(false)
            .build()

        val pageList = LivePagedListBuilder(
            repository.loadUsers(), pagedListConfig)

        val adapter = UserAdapter(this) {
            Timber.d("click")
        }
        binding.userList.adapter = adapter
        pageList.build().collect {
            adapter.submitList(it)
        }
    }
}
