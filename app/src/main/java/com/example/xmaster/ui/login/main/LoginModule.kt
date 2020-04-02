package com.example.xmaster.ui.login.main

import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class LoginModule {

    @ContributesAndroidInjector
    abstract fun provideLoginFragment() : MainLoginFragment
}