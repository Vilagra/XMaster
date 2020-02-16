package com.example.xmaster.ui.news

import androidx.lifecycle.ViewModel
import com.example.xmaster.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
@Suppress("UNUSED")
internal abstract class NewsModule {

    @ContributesAndroidInjector
    internal abstract fun contributeNewsFragment(): NewsFragment

    @Binds
    @IntoMap
    @ViewModelKey(NewsViewModel::class)
    abstract fun bindNewsViewModel(viewModel: NewsViewModel): ViewModel
}