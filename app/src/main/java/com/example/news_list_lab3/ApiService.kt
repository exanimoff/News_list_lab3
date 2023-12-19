package com.example.news_list_lab3

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("your/api/endpoint")
    fun getNews(
        @Query("q") query: String,
        @Query("apiKey") apiKey: String = ApiClient.API_KEY
    ): Call<NewsResponse>
}



