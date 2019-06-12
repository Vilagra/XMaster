package com.example.xmaster.market


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.xmaster.data.model.Coin
import com.example.xmaster.databinding.CoinBinding

class CoinsAdapter() :
    PagedListAdapter<Coin, CoinsAdapter.CoinsHolder>(CALLBACK) {

    @NonNull
    override fun onCreateViewHolder(@NonNull parent: ViewGroup, viewType: Int): CoinsHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = CoinBinding.inflate(inflater, parent, false)
        return CoinsHolder(binding)
    }

    override fun onBindViewHolder(@NonNull holder: CoinsHolder, position: Int) {
        val project = getItem(position)
        if (project != null) {
            holder.bind(project)
        }
    }

    companion object {

        private val CALLBACK = object : DiffUtil.ItemCallback<Coin>() {
            override fun areItemsTheSame(oldItem: Coin, newItem: Coin): Boolean {
                return oldItem.name.equals(newItem.name)
            }

            override fun areContentsTheSame(oldItem: Coin, newItem: Coin): Boolean {
                return oldItem.equals(newItem)
            }
        }
    }

    class CoinsHolder(private val mCoinBinding: CoinBinding) :
        RecyclerView.ViewHolder(mCoinBinding.getRoot()) {

        fun bind(item: Coin) {
            mCoinBinding.setCoin(CoinListItemViewModel(item))
            mCoinBinding.executePendingBindings()
        }
    }

    interface OnItemClickListener {

        fun onItemClick(username: String)
    }
}
