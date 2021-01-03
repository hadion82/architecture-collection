package com.example.architecture.collection.ui.user

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.coroutineScope
import androidx.paging.PagingData
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.window.DeviceState.*
import com.bumptech.glide.integration.recyclerview.RecyclerViewPreloader
import com.bumptech.glide.util.FixedPreloadSizeProvider
import com.example.architecture.databinding.ActivityUserBinding
import com.example.core.extensions.launchWhenStartedUntilStopped
import com.example.core.extensions.pixel
import com.example.core.extensions.textChanges
import com.example.data.entity.UserEntity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.*
import java.lang.Exception

@AndroidEntryPoint
class UserActivity : AppCompatActivity {

    private val viewModel by viewModels<UserViewModel>()
    private val binding by lazy(LazyThreadSafetyMode.NONE) {
        ActivityUserBinding.inflate(
            layoutInflater
        )
    }

    private val openUserDetailClickFlow: Flow<View>
        get() = emptyFlow<View>()

    private val openUserDetailIntent = openUserDetailClickFlow
        .map { v -> UserViewIntent.OpenUserDetailIntent(v.tag as Long) }

    private val userAdapter = UserAdapter(openUserDetailClickFlow)

    @ExperimentalCoroutinesApi
    private val queryChangedIntent: Flow<UserViewIntent.QueryChangedIntent>
        get() = binding.search.textChanges()
            .map { query -> UserViewIntent.QueryChangedIntent(query?.toString() ?: "") }

    @ExperimentalCoroutinesApi
    private val intents = merge(openUserDetailIntent, queryChangedIntent)

    @FlowPreview
    @ExperimentalCoroutinesApi
    @InternalCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val sizeProvider = FixedPreloadSizeProvider<UserEntity>(100.pixel, 100.pixel)
        val modelProvider = userAdapter.createPreLoader(this)
        val preLoader = RecyclerViewPreloader(
            this,
            modelProvider,
            sizeProvider,
            10
        )
        binding.run {
            userList.itemAnimator = DefaultItemAnimator().apply {
                addDuration = 300
                removeDuration = 300
            }
            userList.adapter = userAdapter
            userList.addOnScrollListener(preLoader)
        }
        bindViewModel()
    }

    @FlowPreview
    @ExperimentalCoroutinesApi
    private fun bindViewModel() {
        viewModel.viewState.onEach { render(it) }
            .launchWhenStartedUntilStopped(this)

        intents.onEach { viewModel.processIntents(it) }
            .onStart { UserViewIntent.Initialize }
            .launchIn(lifecycle.coroutineScope)
    }

    private suspend fun render(state: UserViewState) {
        binding.renderLoadingState(state)
        state.event?.getEvent()?.let { event ->
            when (event) {
                is UserViewEvent.OpenDetailInfo -> openUserDetailInfo()
                is UserViewEvent.LoadPagingData -> binding.renderLoadPagingData(event.data)
                is UserViewEvent.LoadFailed -> binding.renderLoadingFailedSate(event.exception)
            }
        }
    }

    private fun openUserDetailInfo() {

    }

    private suspend fun ActivityUserBinding.renderLoadingState(state: UserViewState) {
        progress.visibility = if (state.isLoading) View.VISIBLE else View.GONE
    }

    private suspend fun ActivityUserBinding.renderLoadPagingData(pagingFlow: Flow<PagingData<UserEntity>>) {
        progress.visibility = View.GONE
        pagingFlow.onEach { userAdapter.submitData(it) }
            .launchIn(lifecycle.coroutineScope)
    }

    private suspend fun ActivityUserBinding.renderLoadingFailedSate(exception: Exception) {
        progress.visibility = View.GONE
        userAdapter.submitData(PagingData.empty())
        Toast.makeText(
            this@UserActivity, exception.message, Toast.LENGTH_LONG
        ).show()
    }
}
