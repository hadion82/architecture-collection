package com.example.domain.core.viewmodel

import android.content.Context
import com.example.data.core.NetworkFailure
import com.example.domain.core.Data
import com.example.core.extensions.isOnline
import com.example.core.functional.Result
import com.example.core.ui.viewmodel.DisposableViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

abstract class NetworkViewModel : DisposableViewModel() {

    @ApplicationContext lateinit var context: Context

    fun <T> Single<Data<T>>.networkScheduler() =
        this.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    fun <T> Single<Data<T>>.ioScheduler() =
        this.subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())

    fun <T> Single<Data<T>>.process() =
        if (context.isOnline())
            this.map { dispatch(it) }
                .onErrorReturn {
                    Result.Failure(
                        NetworkFailure.NetworkException(
                            Exception(it)
                        )
                    )
                }
        else this.map { Result.Failure(NetworkFailure.ConnectionError) }

    private fun <T> dispatch(data: Data<T>): Result<T?, NetworkFailure> =
        if (data.status == 100)
            Result.Success(data.data)
        else
            Result.Failure(
                NetworkFailure.ServerError(
                    data.status,
                    data.errorCode,
                    data.errorMessage
                )
            )
}