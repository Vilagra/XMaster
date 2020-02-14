package com.example.xmaster.ui.market

import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.xmaster.R
import com.example.xmaster.databinding.MarketFragmentBinding
import com.example.xmaster.ui.BaseFragment
import com.example.xmaster.ui.ViewModelFactory
import javax.inject.Inject

class MarketFragment : BaseFragment<MarketFragmentBinding>() {

    override val contentLayoutId: Int
        get() = R.layout.market_fragment

    @Inject
    lateinit var factory: ViewModelFactory
    private val mMarketViewModel: MarketViewModel by viewModels { factory }


    override fun setupBinding() {
        binding.setVm(mMarketViewModel)

    }

    override fun setupViewModel() {
        mMarketViewModel.toastMessages.observe(viewLifecycleOwner, Observer { res ->
            res?.run {
                Toast.makeText(context, getString(this), Toast.LENGTH_LONG).show()
            }
        })
    }

}
