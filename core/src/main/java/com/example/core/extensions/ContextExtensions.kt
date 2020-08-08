package com.example.core.extensions

import android.app.DownloadManager
import android.app.NotificationManager
import android.content.Context
import android.net.ConnectivityManager

fun Context.notificationManager() =
    getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

fun Context.downloadManager() =
    getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager

fun Context.connectivityManager() =
    getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

fun Context.isOnline() =
    connectivityManager().activeNetworkInfo?.isConnectedOrConnecting ?: false
