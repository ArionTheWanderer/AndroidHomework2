package com.example.android.di

import androidx.lifecycle.ViewModelProvider
import com.example.android.viewmodel.MyViewModelFactory
import dagger.Binds
import dagger.Module

@Module
abstract class ViewModelFactoryModule {
    @Binds
    internal abstract fun bindViewModelFactory(
        factory: MyViewModelFactory
    ): ViewModelProvider.Factory
}
