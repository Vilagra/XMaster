package com.example.xmaster.market

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.xmaster.data.RepositoryImpl
import com.example.xmaster.databinding.ProjectsBinding
import com.example.xmaster.utils.CustomFactory

class MarketFragment : Fragment() {

    companion object {
        fun newInstance() = MarketFragment()
    }

    private var mMarketViewModel: MarketViewModel? = null


    override fun onAttach(context: Context) {
        super.onAttach(context)
        val repo = RepositoryImpl()
        val factory = CustomFactory(repo)
        mMarketViewModel = ViewModelProviders.of(this, factory).get(MarketViewModel::class.java)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = ProjectsBinding.inflate(inflater, container, false)
        binding.setVm(mMarketViewModel)
        binding.setLifecycleOwner(this)
        return binding.getRoot()
    }

}
