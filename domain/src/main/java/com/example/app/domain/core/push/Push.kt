package com.example.app.domain.core.push

abstract class Push {

    abstract fun run(data: Map<String, String>)

    companion object {
        const val RECEIVER_CHILD = "CHILD"
        const val RECEIVER_GUARDIAN = "GUARDIAN"

        const val FIELD_TYPE = "TYPE"
        const val RECEIVER_TYPE = "RECEIVER_TYPE"

        const val CHILD_CHANGED = "CHILD_CHANGED"
    }

    fun toast(toastManager: IPushToastManager) {

    }

}
