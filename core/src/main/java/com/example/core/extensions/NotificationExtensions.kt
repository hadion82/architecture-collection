package com.example.core.extensions

import android.content.Context
import androidx.core.app.NotificationCompat

inline fun <reified T : NotificationCompat.Builder> T.notify(
    context: Context,
    id: Int = 0
) =
    context.notificationManager().notify(id, build())