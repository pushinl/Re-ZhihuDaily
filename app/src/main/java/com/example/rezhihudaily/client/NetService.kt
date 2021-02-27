package com.example.rezhihudaily.client

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface NetService {
    @get:GET("latest")
    val dataList: Call<Bean>

    @GET("before/{date}")
    fun getList(@Path("date") date:String): Call<Bean>

    @GET("{id}")
    fun getNews(@Path("id") id: Int): Call<Bean.StoryBean>
}