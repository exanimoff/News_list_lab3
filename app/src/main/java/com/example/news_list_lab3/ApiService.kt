package com.example.news_list_lab3

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("1/news")
    fun getNews(
        @Query("q") query: String,
        @Query("apikey") apiKey: String = ApiClient.API_KEY
    ): Call<NewsResponse>
}



