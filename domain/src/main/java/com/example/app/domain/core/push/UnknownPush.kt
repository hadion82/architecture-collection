package com.example.app.domain.core.push

import timber.log.Timber

class UnknownPush : Push() {

    override fun run(data: Map<String, String>) {
        Timber.d("data : $data")
    }
}
