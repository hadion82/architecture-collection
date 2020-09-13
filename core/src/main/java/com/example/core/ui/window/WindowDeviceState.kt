package com.example.core.ui.window

import android.content.Context
import android.os.Handler
import android.os.Looper
import androidx.core.util.Consumer
import androidx.window.DeviceState
import androidx.window.WindowBackend
import androidx.window.WindowManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.sendBlocking
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.concurrent.Executor

class WindowDeviceState(
    context: Context,
    windowBackend: WindowBackend?
) {

    private val windowManager: WindowManager = WindowManager(context, windowBackend)

    private val handler = Handler(Looper.getMainLooper())
    private val mainThreadExecutor = Executor { r: Runnable -> handler.post(r) }

    private var deviceStateJob: Job? = null

    @OptIn(ExperimentalCoroutinesApi::class)
    fun registerDeviceStateChange(
        scope: CoroutineScope,
        deviceStateCallback: (DeviceState) -> Unit
    ) {
        deviceStateJob?.cancel()
        deviceStateJob = scope.launch {
            callbackFlow<DeviceState> {
                val consumer: Consumer<DeviceState> = Consumer {
                    sendBlocking(it)
                }
                windowManager.registerDeviceStateChangeCallback(
                    mainThreadExecutor, consumer
                )
                awaitClose { windowManager.unregisterDeviceStateChangeCallback(consumer) }
            }.collectLatest {
                deviceStateCallback(it)
            }
        }
    }

    fun unregisterDeviceStateChange() =
        deviceStateJob?.cancel()
}