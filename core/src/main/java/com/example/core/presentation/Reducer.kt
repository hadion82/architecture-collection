package com.example.core.presentation

interface Reducer<VS> {
    fun reduce(viewState: VS): VS
}