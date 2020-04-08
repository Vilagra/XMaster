package com.example.xmaster.ui.login

import androidx.lifecycle.ViewModel
import com.example.xmaster.di.ViewModelKey
import com.example.xmaster.ui.login.main.MainLoginViewModel
import com.example.xmaster.ui.login.main.MainLoginFragment
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
abstract class LoginModule {

    @ContributesAndroidInjector
    abstract fun provideLoginFragment() : MainLoginFragment

    @Binds
    @IntoMap
    @ViewModelKey(MainLoginViewModel::class)
    abstract fun provideLoginViewModel(vm: MainLoginViewModel): ViewModel
}