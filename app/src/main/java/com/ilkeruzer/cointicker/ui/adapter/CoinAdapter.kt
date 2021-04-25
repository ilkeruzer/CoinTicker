package com.ilkeruzer.cointicker.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ilkeruzer.cointicker.R
import com.ilkeruzer.cointicker.data.local.CoinDbModel
import com.ilkeruzer.cointicker.databinding.ItemCoinLayoutBinding

class CoinAdapter :
        PagingDataAdapter<CoinDbModel, CoinAdapter.ViewHolder>(POST_COMPARATOR) {

    companion object {
        val POST_COMPARATOR = object : DiffUtil.ItemCallback<CoinDbModel>() {
            override fun areContentsTheSame(oldItem: CoinDbModel, newItem: CoinDbModel): Boolean =
                    oldItem == newItem

            override fun areItemsTheSame(oldItem: CoinDbModel, newItem: CoinDbModel): Boolean =
                    false

            override fun getChangePayload(oldItem: CoinDbModel, newItem: CoinDbModel): Any {
                return newItem
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    override fun onBindViewHolder(
            holder: ViewHolder,
            position: Int,
            payloads: MutableList<Any>
    ) {
        if (payloads.isEmpty()) {
            onBindViewHolder(holder, position)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemCoinLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    inner class ViewHolder(itemView: ItemCoinLayoutBinding) :
        RecyclerView.ViewHolder(itemView.root) {

        private var binding = itemView

        fun bind(data: CoinDbModel) {
            binding.nameTextView.text = data.name
            binding.symbolTextView.text = data.symbol
            Log.d("count",data.code)
        }
    }

}
