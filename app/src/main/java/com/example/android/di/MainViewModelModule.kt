package com.example.android.di

import androidx.lifecycle.ViewModel
import com.example.android.di.scopes.FSCScope
import com.example.android.viewmodel.MainViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class MainViewModelModule {

    @Binds
    @IntoMap
    @FSCScope
    @ViewModelKey(MainViewModel::class)
    internal abstract fun bindViewModel(
        mainViewModel: MainViewModel
    ): ViewModel
}
