package com.example.core.util

import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import android.os.Environment
import com.example.core.extensions.downloadManager

object FileDownloader {
    private val TAG = FileDownloader::class.java.simpleName
    fun from(context: Context, url: String?): Downloader {
        return Downloader(context, url)
    }

    class Downloader(val context: Context, url: String?) {
        private val request: DownloadManager.Request = DownloadManager.Request(Uri.parse(url))
        private var receiver: DownloadCompleteReceiver? = null
        private var callback: Callback? = null
        fun title(title: String?): Downloader {
            request.setTitle(title)
            return this
        }

        fun description(title: String?): Downloader {
            request.setDescription(title)
            return this
        }

        fun mimeType(type: String?): Downloader {
            request.setMimeType(type)
            return this
        }

        fun notification(visibility: Int): Downloader {
            request.setNotificationVisibility(visibility)
            return this
        }

        fun downloadUi(visibility: Boolean): Downloader {
            request.setVisibleInDownloadsUi(visibility)
            return this
        }

        fun allowedOverMetered(allow: Boolean): Downloader {
            request.setAllowedOverMetered(allow)
            return this
        }

        fun header(header: String, value: String?) = apply {
            value?.run {
                request.addRequestHeader(header, this)
            }
        }

        fun path(fileName: String?): Downloader {
            request.setDestinationInExternalPublicDir(
                Environment.DIRECTORY_DOWNLOADS,
                fileName
            )
            return this
        }

        fun download(callback: Callback?): Disposable {
            this.callback = callback
            receiver = DownloadCompleteReceiver()
            context.registerReceiver(
                receiver,
                IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE)
            )
            return Disposable(context, context.downloadManager().enqueue(request))
        }

        fun download(callback: (result: Result?) -> Unit): Disposable {
            this.callback = object : Callback {
                override fun onDownloadCompleted(result: Result?) {
                    callback(result)
                }

                override fun onDownloadFailed(result: Result?) {
                    callback(result)
                }
            }
            receiver = DownloadCompleteReceiver()
            context.registerReceiver(
                receiver,
                IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE)
            )
            return Disposable(context, context.downloadManager().enqueue(request))
        }

        inner class DownloadCompleteReceiver : BroadcastReceiver() {
            override fun onReceive(
                context: Context,
                intent: Intent
            ) {
                val id =
                    intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1L)
                val query = DownloadManager.Query()
                query.setFilterById(id)
                val cursor = context.downloadManager().query(query)
                if (cursor.moveToFirst()) {
                    val status =
                        cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS))
                    val reason =
                        cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_REASON))
                    val localUri =
                        cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI))
                    val uri =
                        cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_URI))
                    val mimeType =
                        cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_MEDIA_TYPE))
                    val result =
                        Result(status, uri, localUri, mimeType, reason)
                    if (DownloadManager.STATUS_SUCCESSFUL == result.status) {
                        callback?.onDownloadCompleted(result)
                    } else {
                        callback?.onDownloadFailed(result)
                    }
                    cursor.close()
                }
                context.unregisterReceiver(receiver)
            }
        }

    }

    class Disposable(
        private val context: Context,
        private val id: Long) {
        fun cancel() {
            context.downloadManager().remove(id)
        }

    }

    data class Result(
        val status: Int,
        val uri: String,
        val localUri: String,
        val mimeType: String,
        val reason: Int
    ) {
        fun isSuccess() =
            status == DownloadManager.STATUS_SUCCESSFUL
    }

    interface Callback {
        fun onDownloadCompleted(result: Result?)
        fun onDownloadFailed(result: Result?)
    }
}