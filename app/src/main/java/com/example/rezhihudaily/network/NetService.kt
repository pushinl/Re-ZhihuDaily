package com.example.rezhihudaily.network

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface NetService {
    @get:GET("latest")
    val dataList: Call<Bean>

    @GET("before/{date}")
    fun getList(@Path("date") date:String): Call<Bean>
}