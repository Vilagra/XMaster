package com.example.xmaster.ui.news

import androidx.fragment.app.viewModels
import com.example.xmaster.R
import com.example.xmaster.databinding.NewsFragmentBinding
import com.example.xmaster.ui.BaseFragment
import com.example.xmaster.ui.ViewModelFactory
import javax.inject.Inject

class NewsFragment : BaseFragment<NewsFragmentBinding>() {

    override val contentLayoutId: Int
        get() = R.layout.news_fragment

    @Inject
    lateinit var factory: ViewModelFactory
    private val viewModel: NewsViewModel by viewModels {
        factory
    }

}
