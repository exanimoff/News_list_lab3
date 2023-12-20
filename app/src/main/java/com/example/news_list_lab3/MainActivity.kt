package com.example.news_list_lab3

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var newsAdapter: NewsAdapter
    private lateinit var searchEditText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView: RecyclerView = findViewById(R.id.newsRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        newsAdapter = NewsAdapter(emptyList())
        recyclerView.adapter = newsAdapter

        searchEditText = findViewById(R.id.searchEditText)

        val searchButton: Button = findViewById(R.id.searchButton)
        searchButton.setOnClickListener {
            val query = searchEditText.text.toString().trim()
            if (query.isNotEmpty()) {
                getNews(query)
            } else {
                Log.d("MainActivity", "Empty query. Please enter a search term.")
            }
        }
    }

    private fun getNews(query: String) {
        Log.d("MainActivity", "getNews called with query: $query")
        val call: Call<NewsResponse> = ApiClient.apiService.getNews(query)

        call.enqueue(object : Callback<NewsResponse> {
            override fun onResponse(call: Call<NewsResponse>, response: Response<NewsResponse>) {
                Log.d("MainActivity", "onResponse called")
                if (response.isSuccessful) {
                    val newsResponse = response.body()
                    val newsList = newsResponse?.results ?: emptyList()
                    Log.d("MainActivity", "Received ${newsList.size} news articles") // Добавим этот лог
                    newsAdapter.setData(newsList)
                } else {
                    Log.e("MainActivity", "Error: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
                Log.e("MainActivity", "onFailure called", t)
                // Обработка ошибок при запросе
            }
        })
    }

}
