package com.example.core.presentation

interface PresentationProcessor<T, R> {
    suspend fun to(value: T): R
}