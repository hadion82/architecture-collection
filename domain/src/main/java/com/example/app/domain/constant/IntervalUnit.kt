package com.example.app.domain.constant

class IntervalUnit {

    companion object {
        const val ONE_SECONDS = 1000L

        const val ONE_MINUTES = 60 * ONE_SECONDS

        const val HARF_HOUR = 30 * ONE_MINUTES

        const val ONE_HOUR = 2 * HARF_HOUR

        const val ONE_DAY = 24 * ONE_HOUR
    }
}