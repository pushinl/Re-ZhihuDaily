package com.example.rezhihudaily.adapter

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.Glide
import com.example.rezhihudaily.`class`.TopNewsBean
import com.example.rezhihudaily.activity.NewsContentActivity
import com.example.rezhihudaily.databinding.BannerBinding

class ViewPagerAdapter(private val context: Context, private val topNewsList: List<TopNewsBean>): PagerAdapter() {
    private val topStories = mutableListOf<TopNewsBean>()
    init {
        topStories.addAll(topNewsList)
    }

    override fun getCount(): Int {
        return topStories.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val binding = BannerBinding.inflate(LayoutInflater.from(container.context), container, false)
        binding.topNewsTitle.text = topStories[position].title
        binding.topNewsHint.text = topStories[position].hint
        Glide.with(context).load(topStories[position].image).into(binding.topNewsImage)
        binding.root.setOnClickListener {
            val aNews = topNewsList[position]
            val intent = Intent(context, NewsContentActivity::class.java).apply {
                putExtra(NewsContentActivity.NEWS_TITLE, aNews.title)
                putExtra(NewsContentActivity.NEWS_IMAGE, aNews.image)
                putExtra(NewsContentActivity.NEWS_HINT, aNews.hint)
                putExtra(NewsContentActivity.NEWS_URL, aNews.url)
            }
            context.startActivity(intent)
        }
        container.addView(binding.root)
        return binding.root
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

}