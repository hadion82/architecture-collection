package com.example.architecture.collection.ui.user

import androidx.paging.PositionalDataSource
import com.example.data.entity.UserEntity
import com.example.data.repository.UserRepository
import com.example.core.data.Args2
import com.example.core.data.Args3
import com.example.core.functional.map
import com.example.core.functional.onSuccess
import com.example.core.functional.zipWith
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class UserPositionalDataSource @Inject constructor(
    private val userRepository: UserRepository
) : PositionalDataSource<UserEntity>(), CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO + Job()

    override fun loadRange(params: LoadRangeParams, callback: LoadRangeCallback<UserEntity>) {
        launch {

        }
    }

    override fun loadInitial(params: LoadInitialParams, callback: LoadInitialCallback<UserEntity>) {

        launch {
            userRepository.getCount().map {
                val firstLoadPosition = computeInitialLoadPosition(params, it)
                val firstLoadSize = computeInitialLoadSize(params, firstLoadPosition, it)
                Args2(firstLoadPosition, firstLoadSize)
            }.zipWith(userRepository.getUser()) { args, list ->
                Args3(list, args.arg0, args.arg1)
            }.onSuccess {
                callback.onResult(it.arg0, it.arg1, it.arg2)
            }
        }
    }


}