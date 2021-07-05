package com.example.core.extensions

import android.view.View
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.ScaleAnimation
import androidx.annotation.UiThread

@UiThread
inline fun View.fadeOut() {
    fadeOut(500)
}

@UiThread
inline fun View.fadeIn() {
    fadeIn(500)
}


inline fun View.scaleUp(durationMillis: Long = 250) {
    this.startAnimation(
        ScaleAnimation(
            0f,
            1f,
            0f,
            1f,
            Animation.RELATIVE_TO_SELF,
            0.5f,
            Animation.RELATIVE_TO_SELF,
            1f
        ).apply {
            duration = durationMillis
            fillAfter = true
            setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationRepeat(animation: Animation?) {
                }

                override fun onAnimationEnd(animation: Animation?) {

                }

                override fun onAnimationStart(animation: Animation?) {
                    visibility = View.VISIBLE
                }
            })
        })
}

inline fun View.scaleDown(durationMillis: Long = 250) {
    this.startAnimation(
        ScaleAnimation(
        1f, 0f, 1f, 0f,
        Animation.RELATIVE_TO_SELF,
        0.5f,
        Animation.RELATIVE_TO_SELF,
        1f
    ).apply {
        duration = durationMillis
        fillAfter = true
        setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationRepeat(animation: Animation?) {
            }

            override fun onAnimationEnd(animation: Animation?) {
                visibility = View.GONE
            }

            override fun onAnimationStart(animation: Animation?) {
                visibility = View.VISIBLE
            }
        })
    })
}

inline fun View.fadeIn(durationMillis: Long = 250) {
    this.startAnimation(AlphaAnimation(0F, 1F).apply {
        duration = durationMillis
        fillAfter = true
    })
}

inline fun View.fadeOut(durationMillis: Long = 250) {
    this.startAnimation(AlphaAnimation(1F, 0F).apply {
        duration = durationMillis
        fillAfter = true
    })
}