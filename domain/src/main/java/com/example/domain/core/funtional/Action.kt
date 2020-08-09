package com.example.domain.core.funtional

sealed class Action {

    data class ViewAction(val action: Int): Action()
    data class Toast(val message: String, val duration: Int = 1000): Action()
    data class Dialog(val title: String, val message: String): Action()
    object Loading: Action()
}