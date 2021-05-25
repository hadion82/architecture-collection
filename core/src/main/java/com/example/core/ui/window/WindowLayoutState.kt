package com.example.core.ui.window

import android.content.Context
import android.os.Handler
import android.os.Looper
import androidx.core.util.Consumer
import androidx.window.WindowLayoutInfo
import androidx.window.WindowManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.sendBlocking
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.concurrent.Executor

class WindowLayoutState(
    context: Context
) {
    private val windowManager: WindowManager = WindowManager(context)

    private val handler = Handler(Looper.getMainLooper())
    private val mainThreadExecutor = Executor { r: Runnable -> handler.post(r) }

    private var deviceStateJob: Job? = null

    @OptIn(ExperimentalCoroutinesApi::class)
    fun registerLayoutChange(
        scope: CoroutineScope,
        deviceStateCallback: (WindowLayoutInfo) -> Unit
    ) {
        deviceStateJob?.cancel()
        deviceStateJob = scope.launch {
            callbackFlow<WindowLayoutInfo> {
                val consumer: Consumer<WindowLayoutInfo> = Consumer {
                    sendBlocking(it)
                }
                windowManager.registerLayoutChangeCallback(
                    mainThreadExecutor, consumer
                )
                awaitClose { windowManager.unregisterLayoutChangeCallback(consumer) }
            }.collectLatest {
                deviceStateCallback(it)
            }
        }
    }

    fun unregisterLayoutChange() =
        deviceStateJob?.cancel()
}