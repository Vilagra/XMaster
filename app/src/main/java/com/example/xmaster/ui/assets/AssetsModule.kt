package com.example.xmaster.ui.assets

import androidx.lifecycle.ViewModel
import com.example.xmaster.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
@Suppress("UNUSED")
internal abstract class AssetsModule {


    @ContributesAndroidInjector
    internal abstract fun contributeScheduleAgendaFragment(): AssetsFragment

    @Binds
    @IntoMap
    @ViewModelKey(AssetsViewModel::class)
    abstract fun bindAgendaViewModel(viewModel: AssetsViewModel): ViewModel
}