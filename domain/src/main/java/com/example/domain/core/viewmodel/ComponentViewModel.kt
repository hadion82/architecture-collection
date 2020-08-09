package com.example.domain.core.viewmodel

import androidx.lifecycle.viewModelScope
import com.example.data.core.NetworkFailure
import com.example.domain.core.Data
import com.example.domain.core.net.DomainApiHandler
import com.example.core.functional.Result
import io.reactivex.Single
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

open class ComponentViewModel : NetworkViewModel() {

    private val _progress: com.example.core.alias.BooleanMutableLiveData by lazy {
        com.example.core.alias.BooleanMutableLiveData(
            false
        )
    }
    val progress: com.example.core.alias.BooleanLiveData by lazy { _progress }

    fun launch(dispatchers: CoroutineDispatcher =
                       Dispatchers.Default, launch: suspend () -> Unit) =
            viewModelScope.launch(dispatchers) { launch() }

    fun <T> async(dispatchers: CoroutineDispatcher =
                          Dispatchers.Default, launch: suspend () -> T) =
            viewModelScope.async(dispatchers) { launch() }

    fun <T> Single<Data<T>>.networkRequest() =
            this.networkScheduler().process().dispatch()

    fun <T> Single<Data<T>>.ioRequest() =
            this.ioScheduler().process().dispatch()

    private fun <T> Single<out Result<T, NetworkFailure>>.dispatch() =
            this.doOnSubscribe { _progress.postValue(true) }
                    .doFinally { _progress.postValue(false) }
                    .map { if(it is Result.Failure) DomainApiHandler.handle(it) }
}