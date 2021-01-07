package com.example.core.presentation

interface PresentationProcessor<T, R> {
    suspend fun invoke(value: T): R
}