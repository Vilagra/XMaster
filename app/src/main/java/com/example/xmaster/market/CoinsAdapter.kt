package com.example.xmaster.market


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.xmaster.R
import com.example.xmaster.data.model.Coin
import com.example.xmaster.databinding.CoinBinding
import kotlinx.android.synthetic.main.li_coins.view.*

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
            val url = item.imageURL?.apply { "$this?w360" } ?: null
            Glide.with(itemView)
                    .load(url)
                    .centerCrop()
                    .placeholder(R.drawable.ic_image_place_holder)
                    .error(R.drawable.ic_broken_image)
                    .fallback(R.drawable.ic_no_image)
                    .into(itemView.picture)
            mCoinBinding.setCoin(CoinListItemViewModel(item))
            mCoinBinding.executePendingBindings()
        }
    }

    interface OnItemClickListener {

        fun onItemClick(username: String)
    }
}
