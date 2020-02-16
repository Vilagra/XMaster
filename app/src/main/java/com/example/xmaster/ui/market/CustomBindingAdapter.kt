package com.example.xmaster.ui.market

import androidx.databinding.BindingAdapter
import androidx.paging.PagedList
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.xmaster.data.model.Coin


object CustomBindingAdapter {

    @BindingAdapter("bind:data")
    @JvmStatic
    fun configureRecyclerView(
        recyclerView: RecyclerView,
        coins: PagedList<Coin>?
    ) {
        val adapter = recyclerView.adapter as? CoinsAdapter ?: CoinsAdapter()
        adapter.submitList(coins)
        recyclerView.run {
            var manager = LinearLayoutManager(recyclerView.context)
            layoutManager = manager
            this.adapter = adapter
            if (itemDecorationCount < 1) {
                var dividerItemDecoration = DividerItemDecoration(getContext(), manager.orientation);
                addItemDecoration(dividerItemDecoration)
            }
        }

    }

    @BindingAdapter("bind:refreshState", "bind:onRefresh")
    @JvmStatic
    fun configureSwipeRefreshLayout(
        layout: SwipeRefreshLayout,
        isLoading: Boolean,
        listener: SwipeRefreshLayout.OnRefreshListener
    ) {
        layout.run {
            setOnRefreshListener(listener)
            post { isRefreshing = isLoading }
        }

    }
}
