package com.example.rezhihudaily.recylerview

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.rezhihudaily.databinding.NewsDatelineBinding
import com.example.rezhihudaily.databinding.NewsItemBinding
import com.example.rezhihudaily.model.NewsBean
import com.example.rezhihudaily.ui.NewsContentActivity


class Adapter(private val context: Context, private val newsList: List<NewsBean>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    inner class ItemViewHolder(binding: NewsItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val newsImage: ImageView = binding.newsImage
        val newsTitle: TextView = binding.newsTitle
        val newsHint: TextView = binding.newsHint
    }

    inner class DatelineViewHolder(binding: NewsDatelineBinding) : RecyclerView.ViewHolder(binding.root) {
        val date: TextView = binding.newsDate
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if(viewType == 1) {
            val binding = NewsItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            val holder = ItemViewHolder(binding)
            holder.itemView.setOnClickListener {
                val position = holder.adapterPosition
                val aNews = newsList[position]
                val intent = Intent(context, NewsContentActivity::class.java).apply {
                    putExtra(NewsContentActivity.NEWS_TITLE, aNews.title)
                    putExtra(NewsContentActivity.NEWS_IMAGE, aNews.image)
                    putExtra(NewsContentActivity.NEWS_HINT, aNews.hint)
                    putExtra(NewsContentActivity.NEWS_URL, aNews.url)
                }
                context.startActivity(intent)
            }
            return holder
        } else {
            val binding = NewsDatelineBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return DatelineViewHolder(binding)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        when(holder) {
            is ItemViewHolder -> {
                val aNews = newsList[position]
                holder.newsTitle.text = aNews.title
                holder.newsHint.text = aNews.hint
                Glide.with(context).load(aNews.image).into(holder.newsImage)//加载图片
            }

            is DatelineViewHolder -> {
                val aNews = newsList[position]
                holder.date.text = getDate(aNews.date, position)
            }
        }

    }

    override fun getItemCount() = newsList.size

    override fun getItemViewType(position: Int): Int {
        if(position % 6 == 0) return 0
        return 1
    }


    fun getDate(date: String, position: Int): String {
        var dateText: String
        if(position == 0) {
            return "今日热闻"
        }
        dateText = if(date[4] == '0') date.substring(date.length - 3, date.length-2)
        else date.substring(date.length - 4, date.length-2)
        dateText += "月"
        dateText += if(date[6] == '0') date.substring(date.length - 1, date.length)
        else date.substring(date.length - 2, date.length)
        dateText += "日"
        return dateText
    }
}
