package com.example.investforecast.ui.mystocks

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.investforecast.databinding.ItemMystockBinding
import com.example.investforecast.domain.model.Portfolio

class MyStocksAdapter(private val onClick: (String) -> Unit) :
    ListAdapter<Portfolio.InvestmentPortfolio.Stock, MyStocksAdapter.StockInfoViewHolder>(
        StockInfoDiffCallback()
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StockInfoViewHolder {
        val binding = ItemMystockBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StockInfoViewHolder(binding, onClick)
    }

    override fun onBindViewHolder(holder: StockInfoViewHolder, position: Int) {
        val stockInfo = getItem(position)
        holder.bind(stockInfo)
    }

    inner class StockInfoViewHolder(
        private val binding: ItemMystockBinding,
        private val onClick: (String) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(stockInfo: Portfolio.InvestmentPortfolio.Stock) {
            binding.tvCount.text = "${stockInfo.count} шт."
            binding.tvName.text = stockInfo.name
            binding.tvPerOne.text = stockInfo.pricePerOne.toString()
            binding.tvTotal.text = stockInfo.total.toString()
            binding.root.setOnClickListener {
                onClick(stockInfo.ticker)
            }
        }
    }

    private class StockInfoDiffCallback :
        DiffUtil.ItemCallback<Portfolio.InvestmentPortfolio.Stock>() {
        override fun areItemsTheSame(
            oldItem: Portfolio.InvestmentPortfolio.Stock,
            newItem: Portfolio.InvestmentPortfolio.Stock
        ): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(
            oldItem: Portfolio.InvestmentPortfolio.Stock,
            newItem: Portfolio.InvestmentPortfolio.Stock
        ): Boolean {
            return oldItem == newItem
        }
    }
}