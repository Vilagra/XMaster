package com.example.xmaster.ui.assets

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import com.example.xmaster.R
import dagger.android.support.DaggerFragment

class AssetsFragment : DaggerFragment() {

    companion object {
        fun newInstance() = AssetsFragment()
    }
    private lateinit var viewModel: AssetsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.assets_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(AssetsViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
