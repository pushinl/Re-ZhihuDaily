package com.example.rezhihudaily.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.bumptech.glide.Glide
import com.example.rezhihudaily.R
import com.example.rezhihudaily.databinding.ActivityHomeBinding
import com.example.rezhihudaily.databinding.ActivityNewsContentBinding

class NewsContentActivity : AppCompatActivity() {

    companion object {
        const val NEWS_TITLE = "news_title"
        const val NEWS_IMAGE_ID = "news_image_id"
    }

    private lateinit var binding: ActivityNewsContentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewsContentBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val newsTitle = intent.getStringExtra(NEWS_TITLE) ?: " "
        val newsImageID = intent.getIntExtra(NEWS_IMAGE_ID, 0)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.collapsingToolbar.title = newsTitle
        Glide.with(this).load(newsImageID).into(binding.newsContentImageView)
        binding.newsContentText.text = "CONTENT"
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}