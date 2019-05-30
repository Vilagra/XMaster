package com.example.xmaster.market

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.xmaster.data.Repository

class MarketViewModel(repository: Repository) : ViewModel() {

    private val mOnItemClickListener: ProjectsAdapter.OnItemClickListener
    private val mIsLoading = MutableLiveData()
    private val mIsErrorVisible = MutableLiveData()
    private val mProjects: LiveData<PagedList<RichProject>>
    private val mOnRefreshListener = SwipeRefreshLayout.OnRefreshListener({ this.updateProjects() })
}
