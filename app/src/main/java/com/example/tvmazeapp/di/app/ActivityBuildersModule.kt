package com.example.tvmazeapp.di.app

import com.example.tvmazeapp.di.main.MainModule
import com.example.tvmazeapp.di.main.MainViewModelModule
import com.example.tvmazeapp.view.AuthActivity
import com.example.tvmazeapp.view.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuildersModule {

    @ContributesAndroidInjector()
    abstract fun contributeAuthActivity(): AuthActivity

    @ContributesAndroidInjector(
        modules = [
            MainViewModelModule::class,
            MainModule::class]
    )
    abstract fun contributeMainActivity(): MainActivity

}