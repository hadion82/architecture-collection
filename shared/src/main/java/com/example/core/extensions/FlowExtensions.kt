package com.example.core.extensions

import androidx.lifecycle.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn

fun <T> Flow<T>.launchWhileStartedIn(owner: LifecycleOwner) {
    if (owner.lifecycle.currentState == Lifecycle.State.DESTROYED) {
        return
    }
    WhileStaredCollector(owner, this)
}

class WhileStaredCollector<T>(
    owner: LifecycleOwner,
    private val flow: Flow<T>,
): DefaultLifecycleObserver {

    private var job: Job? = null

    override fun onStart(owner: LifecycleOwner) {
        job = flow.launchIn(owner.lifecycleScope)
        super.onStart(owner)
    }

    override fun onStop(owner: LifecycleOwner) {
        job?.cancel()
        super.onStop(owner)
    }

    override fun onDestroy(owner: LifecycleOwner) {
        owner.lifecycle.removeObserver(this)
        super.onDestroy(owner)
    }

    init {
        owner.lifecycle.addObserver(this)
    }
}