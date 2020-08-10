package com.example.architecture

import android.os.Bundle
import com.example.architecture.databinding.ActivityDemoBinding
import com.example.core.ui.activity.ComponentActivity
import com.example.data.repository.UserRepository
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DemoActivity(
    val repository: UserRepository
): ComponentActivity<ActivityDemoBinding>() {

    override fun layout(): Int =
        R.layout.activity_demo

    override fun onBind(savedInstanceState: Bundle?, binding: ActivityDemoBinding) {
//        binding.model = viewModel
//        val pagedListConfig = PagedList.Config.Builder()
//            .setPageSize(20)
//            .setInitialLoadSizeHint(20)
//            .setPrefetchDistance(10)
//            .setEnablePlaceholders(false)
//            .build()
//
//        val pageList = LivePagedListBuilder(
//            repository.loadUsers(), pagedListConfig)
//
//        val adapter = UserAdapter(this) {
//            Timber.d("click")
//        }
//        binding.userList.adapter = adapter
//        pageList.build().collect {
//            adapter.submitList(it)
//        }
    }
}
