package com.example.rezhihudaily.model

import android.content.ContentValues.TAG
import android.util.Log
import com.example.rezhihudaily.client.Bean
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
) : Serializable