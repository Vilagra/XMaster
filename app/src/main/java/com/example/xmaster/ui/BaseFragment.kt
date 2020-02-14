package com.example.xmaster.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import dagger.android.support.DaggerFragment

abstract class BaseFragment<B : ViewDataBinding> : DaggerFragment() {

    protected lateinit var binding: B

    abstract val contentLayoutId: Int

    protected open fun setupBinding() {}

    protected open fun setupViewModel() {}


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = DataBindingUtil.inflate(inflater, contentLayoutId, container, false)
        binding.lifecycleOwner = this
        setupBinding()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        setupViewModel()
    }

}