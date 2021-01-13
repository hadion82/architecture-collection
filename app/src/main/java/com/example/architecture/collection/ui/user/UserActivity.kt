package com.example.architecture.collection.ui.user

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.coroutineScope
import androidx.lifecycle.lifecycleScope
import androidx.paging.PagingData
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.window.DeviceState.*
import com.bumptech.glide.integration.recyclerview.RecyclerViewPreloader
import com.bumptech.glide.util.FixedPreloadSizeProvider
import com.example.architecture.databinding.ActivityUserBinding
import com.example.core.extensions.launchWhenStartedUntilStopped
import com.example.core.extensions.pixel
import com.example.core.extensions.queryTextSubmit
import com.example.core.ui.IntentView
import com.example.data.entity.UserEntity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import timber.log.Timber

@AndroidEntryPoint
class UserActivity : AppCompatActivity(), IntentView<UserViewIntent, UserViewState> {

    @FlowPreview
    @ExperimentalCoroutinesApi
    private val viewModel by viewModels<UserViewModel>()
    private val binding by lazy { ActivityUserBinding.inflate(layoutInflater) }

    private val openUserDetailClickFlow: Flow<View>
        get() = emptyFlow()

    private val openUserDetailIntent = openUserDetailClickFlow
        .map { v ->
            Timber.d("click id : ${v.tag as Long}")
            UserViewIntent.OpenUserDetailIntent(v.tag as Long)
        }

    private val userAdapter = UserAdapter(openUserDetailClickFlow)

    @ExperimentalCoroutinesApi
    private val queryChangedIntent: Flow<UserViewIntent.QueryChangedIntent> by lazy {
        binding.search.queryTextSubmit()
            .map { query -> UserViewIntent.QueryChangedIntent(query?.toString() ?: "") }
    }

    @ExperimentalCoroutinesApi
    override val intents by lazy {
        merge(openUserDetailIntent, queryChangedIntent)
    }

    @FlowPreview
    @ExperimentalCoroutinesApi
    @InternalCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val sizeProvider = FixedPreloadSizeProvider<UserEntity>(100.pixel, 100.pixel)
        val modelProvider = userAdapter.createPreLoader(this)
        val preLoader = RecyclerViewPreloader(
            this,
            modelProvider,
            sizeProvider,
            20
        )

        binding.userList.run {
            itemAnimator = null
            adapter = userAdapter
            setHasFixedSize(true)
            addOnScrollListener(preLoader)
        }
        bindViewModel()
    }

    @FlowPreview
    @ExperimentalCoroutinesApi
    private fun bindViewModel() {
        viewModel.viewState.onEach { render(it) }
            .launchWhenStartedUntilStopped(this)

        intents.onEach { viewModel.processIntents(it) }
            .launchIn(lifecycle.coroutineScope)
    }

    override suspend fun render(state: UserViewState) {
        binding.renderLoadingState(state)
        state.event?.getEvent()?.let { event ->
            when (event) {
                is UserViewEvent.OpenDetailInfo -> openUserDetailInfo()
                is UserViewEvent.LoadPagingData -> renderLoadPagingData(event.data)
                is UserViewEvent.LoadFailed -> renderLoadingFailedSate(event.exception)
            }
        }
    }

    private fun openUserDetailInfo() {
        //TODO start UserDetailActivity
    }

    private fun ActivityUserBinding.renderLoadingState(state: UserViewState) {
        progress.visibility = if (state.isLoading) View.VISIBLE else View.GONE
    }

    private var pagingJob: Job? = null
    private suspend fun renderLoadPagingData(pagingFlow: Flow<PagingData<UserEntity>>) {
        pagingJob?.cancel()
        pagingJob = lifecycleScope.launch {
            pagingFlow.collectLatest {
                Timber.d("paging data -> $it")
                userAdapter.submitData(it)
            }
        }
    }

    private suspend fun renderLoadingFailedSate(exception: Exception) {
        userAdapter.submitData(PagingData.empty())
        Toast.makeText(
            this@UserActivity, exception.message, Toast.LENGTH_LONG
        ).show()
    }
}
