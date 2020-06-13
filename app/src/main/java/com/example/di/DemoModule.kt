package com.example.di

import androidx.lifecycle.ViewModel
import com.example.demo.DemoViewModel
import com.example.di.key.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class DemoModule {

    @Binds
    @IntoMap
    @ViewModelKey(DemoViewModel::class)
    internal abstract fun bindViewModel(viewModel: DemoViewModel): ViewModel
}