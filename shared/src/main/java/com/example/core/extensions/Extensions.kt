package com.example.core.extensions

import android.content.Context
import android.os.PowerManager

inline fun wake(
    context: Context,
    timeout: Long = 10 * 1000,
    isRelease: Boolean = true,
    block: () -> Unit
) {
    val manager = context.getSystemService(Context.POWER_SERVICE) as PowerManager
    val wakeLock = manager.newWakeLock(
        PowerManager.PARTIAL_WAKE_LOCK,
        "${Thread.currentThread().stackTrace[4].className}:${System.currentTimeMillis()}"
    )
    if (!wakeLock.isHeld) {
        wakeLock.acquire(timeout)
    }
    block()
    if (isRelease && wakeLock.isHeld) {
        wakeLock.release()
    }
}

inline fun <R> wakeRun(
    context: Context,
    timeout: Long = 10 * 1000,
    isRelease: Boolean = true,
    block: () -> R
): R {
    val manager = context.getSystemService(Context.POWER_SERVICE) as PowerManager
    val wakeLock = manager.newWakeLock(
        PowerManager.PARTIAL_WAKE_LOCK,
        "${Thread.currentThread().stackTrace[4].className}:${System.currentTimeMillis()}"
    )
    if (!wakeLock.isHeld) {
        wakeLock.acquire(timeout)
    }
    val result = block()
    if (isRelease && wakeLock.isHeld) {
        wakeLock.release()
    }
    return result
}

fun acquire(context: Context, timeout: Long = 10 * 1000): PowerManager.WakeLock {
    val manager = context.getSystemService(Context.POWER_SERVICE) as PowerManager
    val wakeLock = manager.newWakeLock(
        PowerManager.PARTIAL_WAKE_LOCK,
        "${Thread.currentThread().stackTrace[4].className}:${System.currentTimeMillis()}"
    )
    if (!wakeLock.isHeld) {
        wakeLock.acquire(timeout)
    }
    return wakeLock
}