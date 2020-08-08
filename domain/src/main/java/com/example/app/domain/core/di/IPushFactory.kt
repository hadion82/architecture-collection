package com.example.app.domain.core.di

import com.example.app.domain.core.push.Push

interface IPushFactory {
    fun <T : Push> create(modelClass: Class<T>): T?
}