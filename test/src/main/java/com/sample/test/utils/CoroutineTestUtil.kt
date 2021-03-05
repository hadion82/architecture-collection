package com.sample.test.utils

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

object CoroutineTestUtil {

    fun <T> getValue(flow: Flow<T>, scope: CoroutineScope, count: Int = 1): T? {
        var data: T? = null
        val latch = CountDownLatch(count)
        val job = flow.onEach {
            data = it
            latch.countDown()
        }.launchIn(scope)
        latch.await(2, TimeUnit.SECONDS)
        job.cancel()
        return data
    }
}