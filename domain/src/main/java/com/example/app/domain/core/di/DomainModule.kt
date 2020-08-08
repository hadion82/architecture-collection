package com.example.app.domain.core.di

import dagger.Module
import dagger.Provides
import io.kiwiplus.app.core.notification.NotificationFactoryImpl
import com.example.app.domain.core.push.PushSubject

@Module
class DomainModule {

    @Provides
    fun provideRxSubject(): PushSubject =
        PushSubject

    @Provides
    fun provideNotificationFactory(): NotificationFactoryImpl =
        com.example.core.CoreDelegate.notification
}