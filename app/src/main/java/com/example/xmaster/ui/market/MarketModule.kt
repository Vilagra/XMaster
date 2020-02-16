package com.example.xmaster.ui.market

import androidx.lifecycle.ViewModel
import com.example.xmaster.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
@Suppress("UNUSED")
internal abstract class MarketModule {


    @ContributesAndroidInjector
    internal abstract fun contributeScheduleAgendaFragment(): MarketFragment

    @Binds
    @IntoMap
    @ViewModelKey(MarketViewModel::class)
    abstract fun bindAgendaViewModel(viewModel: MarketViewModel): ViewModel
}