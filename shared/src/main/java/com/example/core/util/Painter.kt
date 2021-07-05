package com.example.core.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import androidx.annotation.DrawableRes
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.model.GlideUrl
import com.example.core.extensions.pixel
import java.lang.Exception

object Painter {

    private fun transparent(width: Float, height: Float) =
        Bitmap.createBitmap(width.pixel, height.pixel, Bitmap.Config.ARGB_8888)

    private fun create(width: Float, height: Float, color: Int) =
        transparent(width, height).apply {
            eraseColor(color)
        }

    private fun create(context: Context, @DrawableRes resId: Int) =
        try {
            defaultGlide(context)
                .load(resId)
                .submit().get()
        } catch (e: Exception) {
            Glide.get(context).clearDiskCache()
            null
        }

    private fun create(context: Context, @DrawableRes resId: Int, width: Float, height: Float) =
        try {
            defaultGlide(context)
                .load(resId)
                .submit().get()
        } catch (e: Exception) {
            Glide.get(context).clearDiskCache()
            null
        }

    private fun circle(context: Context, url: String?, size: Float) =
        url?.run {
            try {
                circleGlide(context, size.pixel)
                    .load(this)
                    .submit().get()
            } catch (e: Exception) {
                Glide.get(context).clearDiskCache()
                null
            }
        }

    private fun circle(context: Context, url: GlideUrl, size: Float) =
        try {
            circleGlide(context, size.pixel)
                .load(url)
                .submit().get()
        } catch (e: Exception) {
            Glide.get(context).clearDiskCache()
            null
        }

    private fun circle(context: Context, url: String?, @DrawableRes resId: Int, size: Float) =
        url?.run {
            try {
                circleGlide(context, size.pixel)
                    .error(resId)
                    .load(this)
                    .submit().get()
            } catch (e: Exception) {
                Glide.get(context).clearDiskCache()
                null
            }
        }

    private fun circle(context: Context, url: GlideUrl, @DrawableRes resId: Int, size: Float) =
        try {
            circleGlide(context, size.pixel)
                .error(resId)
                .load(url)
                .submit().get()
        } catch (e: Exception) {
            Glide.get(context).clearDiskCache()
            null
        }

    private fun scaled(
        context: Context,
        @DrawableRes resId: Int,
        width: Float,
        height: Float
    ): Bitmap? {
        return defaultGlide(context)
            .load(resId)
            .override(width.pixel, height.pixel)
            .submit().get()
    }

    private fun scaled(context: Context, url: String?, size: Float) =
        scaled(context, url, size, size)

    private fun scaled(context: Context, url: String?, width: Float, height: Float) =
        url?.run {
            try {
                defaultGlide(context)
                    .load(this)
                    .override(width.pixel, height.pixel)
                    .submit().get()
            } catch (e: Exception) {
                null
            }
        }

    private fun scaled(context: Context, url: GlideUrl, width: Float, height: Float) =
        try {
            defaultGlide(context)
                .load(url)
                .override(width.pixel, height.pixel)
                .submit().get()
        } catch (e: Exception) {
            null
        }

    private fun crop(context: Context, url: String?, width: Float, height: Float) =
        url?.run {
            try {
                defaultGlide(context)
                    .centerCrop()
                    .load(this)
                    .override(width.pixel, height.pixel)
                    .submit().get()
            } catch (e: Exception) {
                null
            }
        }

    private fun crop(context: Context, url: GlideUrl?, width: Float, height: Float) =
        try {
            url?.run {
                defaultGlide(context)
                    .centerCrop()
                    .load(this)
                    .override(width.pixel, height.pixel)
                    .submit().get()
            }
        } catch (e: Exception) {
            null
        }

    private fun overlay(source: Bitmap, overlay: Bitmap, x: Float, y: Float): Bitmap {
        Canvas(source).drawBitmap(
            overlay,
            x.pixel.toFloat(),
            y.pixel.toFloat(),
            null
        )
        return source
    }

    fun overlay(context: Context) =
        Source(context)

    private fun defaultGlide(context: Context) = Glide.with(context)
        .asBitmap().diskCacheStrategy(DiskCacheStrategy.ALL)

    private fun circleGlide(context: Context, size: Int) =
        defaultGlide(context).override(size).circleCrop()

    class Source(
        private val context: Context
    ) {

        fun create(@DrawableRes resId: Int, width: Float, height: Float) =
            Overlay(context, Painter.create(context, resId, width, height))

        fun create(@DrawableRes resId: Int) =
            Overlay(context, create(context, resId))

        fun transparent(width: Float, height: Float) =
            Overlay(context, Painter.transparent(width, height))

        fun circle(url: String?, size: Float) =
            Overlay(context, circle(context, url, size))

        fun scaled(@DrawableRes resId: Int, width: Float, height: Float) =
            Overlay(context, scaled(context, resId, width, height))

        fun crop(url: String?, size: Float) =
            crop(url, size, size)

        fun crop(url: String?, width: Float, height: Float) =
            Overlay(context, crop(context, url, width, height))
    }

    class Overlay(
        private val context: Context,
        private val source: Bitmap?
    ) {

        fun create(@DrawableRes resId: Int, width: Float, height: Float) =
            OverlayCanvas(context, source, Painter.create(context, resId, width, height))

        fun create(@DrawableRes resId: Int) =
            OverlayCanvas(context, source, Painter.create(context, resId))


        fun circle(url: String?, size: Float) =
            OverlayCanvas(context, source, circle(context, url, size))

        fun circle(url: String?, @DrawableRes resId: Int, size: Float) =
            OverlayCanvas(context, source, circle(context, url, resId, size))

        fun scaled(@DrawableRes resId: Int, size: Float) =
            OverlayCanvas(context, source, scaled(context, resId, size, size))

        fun scaled(@DrawableRes resId: Int, width: Float, height: Float) =
            OverlayCanvas(context, source, scaled(context, resId, width, height))

        fun scaled(url: String?, size: Float) =
            scaled(url, size, size)


        fun scaled(url: String?, width: Float, height: Float) =
            OverlayCanvas(context, source, scaled(context, url, width, height))

        fun crop(url: String?, size: Float) =
            crop(url, size, size)

        fun crop(url: GlideUrl?, size: Float) =
            crop(url, size, size)

        private fun crop(url: String?, width: Float, height: Float) =
            OverlayCanvas(context, source, crop(context, url, width, height))

        private fun crop(url: GlideUrl?, width: Float, height: Float) =
            OverlayCanvas(context, source, crop(context, url, width, height))

        fun getSource() = source
    }

    class OverlayCanvas(
        private val context: Context,
        private val source: Bitmap?,
        private val overlay: Bitmap?
    ) {
        fun paint(x: Float = 0.toFloat(), y: Float = 0.toFloat()): Overlay {
            if (source != null && overlay != null) {
                overlay(source, overlay, x, y)
            }
            return Overlay(context, source)
        }
    }
}