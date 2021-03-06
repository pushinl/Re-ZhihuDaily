package com.example.rezhihudaily.`class`

import java.io.Serializable

data class NewsBean(
    val title: String,
    val hint: String,
    val image: String,
    val url: String,
    val date: String
)

data class TopNewsBean(
    val title: String,
    val hint: String,
    val image: String,
    val url: String,
    val position: Int
)