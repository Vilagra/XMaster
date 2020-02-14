package com.example.xmaster.ui.market


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

class CoinsAdapter() : PagedListAdapter<Coin, CoinsAdapter.CoinsHolder>(CoinUtil) {

    @NonNull
    override fun onCreateViewHolder(@NonNull parent: ViewGroup, viewType: Int): CoinsHolder {
        val binding = CoinBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CoinsHolder(binding)
    }

    override fun onBindViewHolder(@NonNull holder: CoinsHolder, position: Int) {
        getItem(position)?.run { holder.bind(this) }
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
            mCoinBinding.setCoin(item)
            mCoinBinding.executePendingBindings()
        }
    }

}

object CoinUtil : DiffUtil.ItemCallback<Coin>() {
        override fun areItemsTheSame(oldItem: Coin, newItem: Coin): Boolean {
            return oldItem.name.equals(newItem.name)
        }

        override fun areContentsTheSame(oldItem: Coin, newItem: Coin): Boolean {
            return oldItem.equals(newItem)
        }
    }

