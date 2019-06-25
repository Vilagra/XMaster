package com.example.xmaster.market

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.xmaster.MyApplication
import com.example.xmaster.databinding.MarketFragmentBinding
import com.example.xmaster.utils.CustomFactory
import javax.inject.Inject

class MarketFragment : Fragment() {

    lateinit var binding: MarketFragmentBinding

    companion object {
        fun newInstance() = MarketFragment()
    }

    private var mMarketViewModel: MarketViewModel? = null


    @Inject
    lateinit var factory: CustomFactory


    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity?.application as MyApplication).component.inject(this)
        mMarketViewModel = ViewModelProviders.of(this, factory).get(MarketViewModel::class.java)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = MarketFragmentBinding.inflate(inflater, container, false)
        return binding.getRoot()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.setVm(mMarketViewModel)
        binding.setLifecycleOwner(viewLifecycleOwner)
        mMarketViewModel?.toastMessages?.observe(viewLifecycleOwner, Observer { res ->
            if (res != null && res != -1) {
                val message = getString(res)
                Toast.makeText(context, message, Toast.LENGTH_LONG).show()
            }
        })
    }
}
