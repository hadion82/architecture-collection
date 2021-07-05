package com.example.architecture.collection.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.architecture.collection.feature.user.UserViewIntent
import com.example.architecture.collection.feature.user.UserViewModelDelegate
import com.example.architecture.collection.feature.user.UserViewModelDelegateImpl
import kotlinx.coroutines.*
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class UserUpdateBroadcastReceiver @Inject constructor(): BroadcastReceiver(), CoroutineScope {

    companion object {
        val ACTION_REFRESH = "com.architecture.action.USER_REFRESH"
    }

    override val coroutineContext: CoroutineContext
        get() = SupervisorJob() + Dispatchers.Main

    @Inject
    lateinit var userViewModelDelete: UserViewModelDelegate

    override fun onReceive(context: Context?, intent: Intent?) {
        launch { userViewModelDelete.processIntents(UserViewIntent.Refresh()) }
    }
}