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
import com.example.xmaster.data.RepositoryFactory
import com.example.xmaster.databinding.MarketFragmentBinding
import com.example.xmaster.utils.CustomFactory

class MarketFragment : Fragment() {

    companion object {
        fun newInstance() = MarketFragment()
    }

    private var mMarketViewModel: MarketViewModel? = null


    override fun onAttach(context: Context) {
        super.onAttach(context)
        val repo = RepositoryFactory.provideRepository(context)
        val factory = CustomFactory(repo)
        mMarketViewModel = ViewModelProviders.of(this, factory).get(MarketViewModel::class.java)
        mMarketViewModel?.toastMessages?.observe(this, Observer { res ->
            if (res != null && res != -1) {
                val message = getString(res)
                Toast.makeText(context, message, Toast.LENGTH_LONG).show()
            }
        })

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = MarketFragmentBinding.inflate(inflater, container, false)
        binding.setVm(mMarketViewModel)
        binding.setLifecycleOwner(this)
        return binding.getRoot()
    }

}
