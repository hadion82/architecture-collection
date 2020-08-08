package com.example.app.domain.core.push

import android.text.TextUtils
import androidx.lifecycle.MutableLiveData
import com.google.firebase.messaging.RemoteMessage
import com.example.app.domain.core.di.IPushFactory
import io.kiwiplus.app.core.executor.AppExecutor
import io.kiwiplus.app.core.functional.Event
import io.kiwiplus.app.core.util.postEvent
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import timber.log.Timber
import java.util.concurrent.ExecutorService

object PushSubject {

    private val compositeDisposable = CompositeDisposable()

    private var pushSubject: PublishSubject<RemoteMessage> =
        createPushSubject(
            AppExecutor.pushService
        )

    lateinit var factory: IPushFactory

    private val _pushLive = MutableLiveData<Event<Map<String, String>>>()
    val pushLive = _pushLive

    fun init(factory: IPushFactory) {
        PushSubject.factory = factory
    }

    fun geSubject() = pushSubject
    fun push(message: RemoteMessage) {
        if (!pushSubject.hasObservers()) {
            pushSubject =
                createPushSubject(
                    AppExecutor.pushService
                )
        }
        pushSubject.onNext(message)
    }


    @Throws(IllegalAccessException::class, InstantiationException::class)
    fun newPush(type: String): Push? {
        if (TextUtils.isEmpty(type)) {
            throw IllegalArgumentException()
        }
        return PushType.toPush(
            type,
            factory
        )
    }

    private fun createPushSubject(executorService: ExecutorService): PublishSubject<RemoteMessage> {
        return PublishSubject.create<RemoteMessage>().apply {
            add(
                subscribeOn(Schedulers.from(executorService))
                    .observeOn(Schedulers.from(executorService))
                    .map {
                        it.data[Push.FIELD_TYPE]?.run {
                            newPush(
                                this
                            )?.run(it.data)
                            _pushLive.postEvent(
                                it.data
                            )
                        }
                    }
                    .subscribe({ Timber.d("onSuccess") }
                        , { error -> Timber.d("error -> $error") })
            )
        }
    }

    private fun add(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }

    fun clear() {
        compositeDisposable.clear()
    }

    private fun onError(error: Throwable) {
//        Timber.e("error", "Push Exception : " + error.message)
    }
}
