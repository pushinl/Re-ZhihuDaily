package com.example.rezhihudaily.network

class Bean {
    lateinit var date: String
    lateinit var stories: List<StoryBean>
    lateinit var top_stories: List<TopStoryBean>

    data class StoryBean(
        val image_hue: String,
        val title: String,
        val url: String,
        val hint: String,
        val ga_prefix: String,
        val images: List<String>,
        val type: Int,
        val id: Int,
    )

    data class TopStoryBean(
        val image_hue: String,
        val title: String,
        val url: String,
        val hint: String,
        val ga_prefix: String,
        val image: String,
        val type: Int,
        val id: Int,
    )
}