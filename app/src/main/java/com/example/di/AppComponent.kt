package com.example.di

import com.example.DemoApplication
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        ApplicationModule::class,
        ActivityModule::class,
        DatabaseModule::class,
        ViewModelModule::class
    ]
)
interface AppComponent : AndroidInjector<DemoApplication> {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: DemoApplication): Builder

        fun build(): AppComponent
    }
}