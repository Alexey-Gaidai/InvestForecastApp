package com.example.investforecast.ui.stocks

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.investforecast.databinding.ItemStockBinding
import com.example.investforecast.domain.model.StockInfo

class StocksAdapter(private val onClick: (String) -> Unit) :
    ListAdapter<StockInfo, StocksAdapter.StockInfoViewHolder>(StockInfoDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StockInfoViewHolder {
        val binding = ItemStockBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StockInfoViewHolder(binding, onClick)
    }

    override fun onBindViewHolder(holder: StockInfoViewHolder, position: Int) {
        val stockInfo = getItem(position)
        holder.bind(stockInfo)
    }

    inner class StockInfoViewHolder(
        private val binding: ItemStockBinding,
        private val onClick: (String) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(stockInfo: StockInfo) {
            binding.tvTicker.text = stockInfo.ticker
            binding.tvName.text = stockInfo.name
            binding.tvCost.text = stockInfo.lastPrice.toString()
            binding.root.setOnClickListener {
                onClick(stockInfo.ticker)
            }
        }
    }

    private class StockInfoDiffCallback : DiffUtil.ItemCallback<StockInfo>() {
        override fun areItemsTheSame(oldItem: StockInfo, newItem: StockInfo): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: StockInfo, newItem: StockInfo): Boolean {
            return oldItem == newItem
        }
    }
}