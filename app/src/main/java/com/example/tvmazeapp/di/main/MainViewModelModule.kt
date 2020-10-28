package com.example.tvmazeapp.di.main

import androidx.lifecycle.ViewModel
import com.example.tvmazeapp.di.app.ViewModelKey
import com.example.tvmazeapp.viewmodel.MainViewModel2
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class MainViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel2::class)
    abstract fun bindMainViewModel(viewModel: MainViewModel2): ViewModel

}