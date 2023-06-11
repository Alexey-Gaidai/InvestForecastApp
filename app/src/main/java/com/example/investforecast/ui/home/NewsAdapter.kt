package com.example.investforecast.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.investforecast.databinding.ItemMystockBinding
import com.example.investforecast.databinding.ItemNewsBinding
import com.example.investforecast.domain.model.News
import com.example.investforecast.domain.model.Portfolio

class NewsAdapter(private val onClick: (String) -> Unit) :
    ListAdapter<News, NewsAdapter.NewsViewHolder>(
        NewsDiffCallback()
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val binding = ItemNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NewsViewHolder(binding, onClick)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val stockInfo = getItem(position)
        holder.bind(stockInfo)
    }

    inner class NewsViewHolder(
        private val binding: ItemNewsBinding,
        private val onClick: (String) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(news: News) {
            Glide
                .with(binding.root)
                .load(news.imageUrl)
                .into(binding.ivImage)
            binding.tvTitle.text = news.title
            binding.tvName.text = news.name
            binding.tvIndustries.text = news.industry
            binding.root.setOnClickListener {
                onClick(news.url)
            }
        }
    }

    private class NewsDiffCallback :
        DiffUtil.ItemCallback<News>() {
        override fun areItemsTheSame(
            oldItem: News,
            newItem: News
        ): Boolean {
            return oldItem.title == newItem.title
        }

        override fun areContentsTheSame(
            oldItem: News,
            newItem: News
        ): Boolean {
            return oldItem == newItem
        }
    }
}