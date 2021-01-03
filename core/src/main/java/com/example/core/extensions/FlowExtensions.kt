package com.example.core.extensions

import androidx.lifecycle.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn

fun <T> Flow<T>.launchWhenStartedUntilStopped(owner: LifecycleOwner) {
    if (owner.lifecycle.currentState == Lifecycle.State.DESTROYED) {
        return
    }
    LifecycleController(this, owner, Lifecycle.State.STARTED, Lifecycle.Event.ON_STOP)
}

class LifecycleController<T>(
    private val flow: Flow<T>,
    private val owner: LifecycleOwner,
    private val launchState: Lifecycle.State,
    private val cancelEvent: Lifecycle.Event
) {
    private var job: Job? = null

    private val observer = LifecycleEventObserver { source, event ->
        when {
            source.lifecycle.currentState == launchState ->
                job = flow.launchIn(owner.lifecycleScope)
            event == cancelEvent -> job?.cancel()
            source.lifecycle.currentState == Lifecycle.State.DESTROYED -> handleDestroy(job)
        }
    }

    init {
        owner.lifecycle.addObserver(observer)
    }

    private fun handleDestroy(job: Job?) {
        job?.cancel()
        owner.lifecycle.removeObserver(observer)
    }
}