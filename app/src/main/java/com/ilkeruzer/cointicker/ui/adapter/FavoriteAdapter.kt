package com.ilkeruzer.cointicker.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ilkeruzer.cointicker.data.local.CoinDbModel
import com.ilkeruzer.cointicker.databinding.ItemCoinLayoutBinding

class FavoriteAdapter(
    var coins: List<CoinDbModel>
): RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {

    private var listener: FavoritesListener?= null

    inner class FavoriteViewHolder(itemView: ItemCoinLayoutBinding): RecyclerView.ViewHolder(itemView.root) {

        private var binding = itemView

        @SuppressLint("SetTextI18n")
        fun bind(data: CoinDbModel) {

            binding.nameTextView.text = data.name
            binding.symbolTextView.text = data.symbol

            binding.root.setOnClickListener {
                listener?.favoriteClicked(data)
            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val binding = ItemCoinLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return FavoriteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        val data = coins[position]
        holder.bind(data)
    }

    override fun getItemCount(): Int {
        return coins.size
    }

    fun setList(stations: List<CoinDbModel>) {
        this.coins = stations
    }

    fun setListener(listener: FavoritesListener) {
        this.listener = listener
    }

    interface FavoritesListener {

        fun favoriteClicked(data: CoinDbModel)

    }
}