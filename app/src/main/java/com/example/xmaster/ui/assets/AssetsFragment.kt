package com.example.xmaster.ui.assets


import androidx.fragment.app.viewModels
import com.example.xmaster.R
import com.example.xmaster.databinding.AssetsFragmentBinding
import com.example.xmaster.ui.BaseFragment
import com.example.xmaster.ui.ViewModelFactory
import javax.inject.Inject

class AssetsFragment : BaseFragment<AssetsFragmentBinding>() {

    override val contentLayoutId: Int
        get() = R.layout.assets_fragment

    @Inject
    lateinit var factory: ViewModelFactory
    private val viewModel: AssetsViewModel by viewModels {
        factory
    }
}
