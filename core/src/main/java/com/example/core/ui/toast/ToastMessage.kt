package com.example.core.ui.toast

import android.widget.Toast

class ToastMessage {
    val message : String
    val duration : Int

    constructor(message: String) {
        this.message = message
        this.duration = Toast.LENGTH_SHORT
    }

    constructor(resourceId: Int) {
        this.message = com.example.app.domain.AppHelper.app.getString(resourceId)
        this.duration = Toast.LENGTH_SHORT
    }

    constructor(message: String, duration: Int) {
        this.message = message
        this.duration = duration
    }

    constructor(resourceId: Int, duration: Int) {
        this.message = com.example.app.domain.AppHelper.app.getString(resourceId)
        this.duration = duration
    }
}