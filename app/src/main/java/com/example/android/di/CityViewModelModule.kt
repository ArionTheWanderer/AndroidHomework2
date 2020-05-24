package com.example.android.di

import androidx.lifecycle.ViewModel
import com.example.android.di.scopes.SSCScope
import com.example.android.viewmodel.CityViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class CityViewModelModule {
    @Binds
    @IntoMap
    @SSCScope
    @ViewModelKey(CityViewModel::class)
    internal abstract fun bindViewModel2(
        cityViewModel: CityViewModel
    ): ViewModel
}
