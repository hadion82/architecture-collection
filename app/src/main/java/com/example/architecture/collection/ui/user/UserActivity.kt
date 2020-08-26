package com.example.architecture.collection.ui.user

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.paging.*
import com.bumptech.glide.integration.recyclerview.RecyclerViewPreloader
import com.bumptech.glide.util.FixedPreloadSizeProvider
import com.example.architecture.R
import com.example.architecture.databinding.ActivityUserBinding
import com.example.core.extensions.launch
import com.example.core.extensions.pixel
import com.example.core.functional.subscribe
import com.example.core.ui.activity.ComponentActivity
import com.example.data.core.NetworkFailure
import com.example.data.entity.UserEntity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import timber.log.Timber

@AndroidEntryPoint
class UserActivity : ComponentActivity<ActivityUserBinding>(), SearchView.OnQueryTextListener {

    override fun layout(): Int =
        R.layout.activity_user

    override fun onBind(savedInstanceState: Bundle?, binding: ActivityUserBinding) {

        val adapter = UserAdapter(this, this) {
            Timber.d("click")
        }
        val sizeProvider = FixedPreloadSizeProvider<UserEntity>(100.pixel, 100.pixel)
        val modelProvider = adapter.createPreLoader()
        val preLoader = RecyclerViewPreloader(
            this,
            modelProvider,
            sizeProvider,
            10
        )
        binding.run {
            userList.adapter = adapter
            search.let { view ->
                view.isSubmitButtonEnabled = true
                view.setOnQueryTextListener(this@UserActivity)
            }
            userList.addOnScrollListener(preLoader)
        }
        collectUsers()
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        query?.let { binding.model?.searchByUserName(query) }
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean = true

    private fun collectUsers() = launch(Dispatchers.IO) {
        binding.model?.loadUserByName()?.collectLatest {
            it.subscribe(this::onSuccess, this::onFailure)
        }
    }

    private fun onSuccess(pagingDataFlow: Flow<PagingData<UserEntity>>) = launch {
        pagingDataFlow.collectLatest { data ->
            (binding.userList.adapter as UserAdapter).submitData(data)
        }
    }

    @SuppressLint("ShowToast")
    private fun onFailure(failure: NetworkFailure) {
        Toast.makeText(
            this, when (failure) {
                is NetworkFailure.ConnectionError -> "Network not connected"
                is NetworkFailure.ServerError -> failure.errorMessage
                is NetworkFailure.Exception -> failure.exception.message
            }, Toast.LENGTH_LONG
        )
    }
}
