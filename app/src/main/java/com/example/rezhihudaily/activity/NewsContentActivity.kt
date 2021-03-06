package com.example.rezhihudaily.activity

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.rezhihudaily.databinding.ActivityNewsContentBinding


class NewsContentActivity : AppCompatActivity() {

    companion object {
        const val NEWS_TITLE = "news_title"
        const val NEWS_IMAGE = "news_image"
        const val NEWS_HINT = "news_hint"
        const val NEWS_URL = "news_url"
    }

    private lateinit var binding: ActivityNewsContentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewsContentBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val newsTitle = intent.getStringExtra(NEWS_TITLE)
        val newsImageID = intent.getStringExtra(NEWS_IMAGE)
        val newsHint = intent.getStringExtra(NEWS_HINT)
        val newsURL = intent.getStringExtra(NEWS_URL)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.collapsingToolbar.title = newsTitle
        Glide.with(this).load(newsImageID).into(binding.newsContentImageView)
        val webView = binding.newsContent
        webView.settings.javaScriptEnabled = true
        newsURL?.let { webView.loadUrl(it) }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {//默认选中，暂时没用
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}