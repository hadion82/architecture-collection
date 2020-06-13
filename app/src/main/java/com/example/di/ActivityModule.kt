package com.example.di

import com.example.demo.DemoActivity
import com.example.di.scope.ActivityScoped
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {

    @ActivityScoped
    @ContributesAndroidInjector(
        modules = [
            DemoModule::class
        ]
    )
    internal abstract fun contributeDemoActivity(): DemoActivity
}