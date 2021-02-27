package com.example.rezhihudaily.recylerview

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.rezhihudaily.databinding.NewsItemBinding
import com.example.rezhihudaily.model.NewsBean
import com.example.rezhihudaily.ui.NewsContentActivity

class Adapter(private val context: Context, private val newsList: List<NewsBean>) : RecyclerView.Adapter<Adapter.ViewHolder>() {

    inner class ViewHolder(binding: NewsItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val newsImage: ImageView = binding.newsImage
        val newsTitle: TextView = binding.newsTitle
        val newsHint: TextView = binding.newsHint
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = NewsItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val holder = ViewHolder(binding)
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
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val aNews = newsList[position]
        holder.newsTitle.text = aNews.title
        holder.newsHint.text = aNews.hint
        Glide.with(context).load(aNews.image).into(holder.newsImage)//加载图片
    }

    override fun getItemCount() = newsList.size

}
