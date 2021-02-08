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
import com.example.rezhihudaily.ui.NewsContentActivity

class Adapter(val context: Context, val newsList: List<News>) : RecyclerView.Adapter<Adapter.ViewHolder>() {

    inner class ViewHolder(binding: NewsItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val newsImage: ImageView = binding.newsImage
        val newsTitle: TextView = binding.newsTitle
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = NewsItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val holder = ViewHolder(binding)
        holder.itemView.setOnClickListener {
            val position = holder.adapterPosition
            val anews = newsList[position]
            val intent = Intent(context, NewsContentActivity::class.java).apply {
                putExtra(NewsContentActivity.NEWS_TITLE, anews.newsTitle)
                putExtra(NewsContentActivity.NEWS_IMAGE_ID, anews.newsid)
            }
            context.startActivity(intent)
        }
        return holder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val anews = newsList[position]
        holder.newsTitle.text = anews.newsTitle
        Glide.with(context).load(anews.newsid).into(holder.newsImage)//加载图片
    }

    override fun getItemCount() = newsList.size

}
