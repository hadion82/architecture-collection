package com.example.app.domain.core.push

interface IPushToastManager {
    fun show(resId: Int, message: String?)

    fun show(url: String?, message: String?)

    fun isForeground(): Boolean

    fun isHome(): Boolean

    fun onGroupDeleted(name: String?)

}