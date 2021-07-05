package com.example.core.presentation

interface PresentationProcessor<in T, out R> {
    suspend fun invoke(value: T): R
}