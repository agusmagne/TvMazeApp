package com.example.tvmazeapp.di.app

import androidx.lifecycle.ViewModelProvider
import com.example.tvmazeapp.viewmodels.ViewModelProvidersFactory
import dagger.Binds
import dagger.Module

@Module
abstract class ViewModelFactoryModule {

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelProvidersFactory)
            : ViewModelProvider.Factory

}