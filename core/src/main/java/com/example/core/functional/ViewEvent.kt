package com.example.core.functional

class ViewEvent<out T>(private val event: T) {
    private var hasBeenHandled = false

    private fun getEventIfNotHandled(): T? {
        return if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            event
        }
    }

    fun getEvent() = getEventIfNotHandled()

    fun peekContent(): T = event
}