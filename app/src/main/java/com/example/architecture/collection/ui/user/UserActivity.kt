package com.example.architecture.collection.ui.user

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import androidx.paging.map
import androidx.paging.toLiveData
import com.example.architecture.R
import com.example.architecture.databinding.ActivityUserBinding
import com.example.core.functional.Result
import com.example.core.ui.activity.ComponentActivity
import com.example.data.entity.UserEntity
import com.example.data.repository.UserRepository
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class UserActivity: ComponentActivity<ActivityUserBinding>() {

    override fun layout(): Int =
        R.layout.activity_user

    override fun onBind(savedInstanceState: Bundle?, binding: ActivityUserBinding) {

        val adapter = UserAdapter(this) {
            Timber.d("click")
        }
        binding.userList.adapter = adapter
        lifecycleScope.launch {
            binding.model?.loadUsers()?.collectLatest {
                adapter.submitData(it)
            }
        }
    }
}
