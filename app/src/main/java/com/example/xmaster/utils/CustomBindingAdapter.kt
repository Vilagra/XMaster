package com.example.xmaster.utils

import androidx.databinding.BindingAdapter
import androidx.paging.PagedList
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.xmaster.data.Status
import com.example.xmaster.data.model.Coin
import com.example.xmaster.market.CoinsAdapter


object CustomBindingAdapter {

    @BindingAdapter("bind:data")
    @JvmStatic
    fun configureRecyclerView(
        recyclerView: RecyclerView,
        coins: PagedList<Coin>?
    ) {
        val adapter = CoinsAdapter()
        adapter.submitList(coins)
        var manager = LinearLayoutManager(recyclerView.context)
        recyclerView.layoutManager = manager
        recyclerView.adapter = adapter
        if (recyclerView.itemDecorationCount < 1) {
            var dividerItemDecoration = DividerItemDecoration(recyclerView.getContext(), manager.orientation);
            recyclerView.addItemDecoration(dividerItemDecoration)
        }
    }

    @BindingAdapter("bind:refreshState", "bind:onRefresh")
    @JvmStatic
    fun configureSwipeRefreshLayout(
        layout: SwipeRefreshLayout,
        status: Status?,
        listener: SwipeRefreshLayout.OnRefreshListener
    ) {
        layout.setOnRefreshListener(listener)
        layout.post { layout.isRefreshing = status == Status.LOADING }
    }

    /*   @BindingAdapter("bind:onRefresh")
       @JvmStatic
       fun configureSwipeRefreshLayout(
           layout: SwipeRefreshLayout,
           listener: SwipeRefreshLayout.OnRefreshListener
       ) {
           layout.setOnRefreshListener(listener)
       }*/


}
