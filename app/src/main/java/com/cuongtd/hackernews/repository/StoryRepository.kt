package com.cuongtd.hackernews.repository

import Result
import Story
import android.graphics.Movie
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import android.R
import android.util.Log
import timber.log.Timber


interface ApiService {
    @GET("search_by_date?tags=story")
    fun getStories(): Call<Result>

    @GET("search?tags=front_page")
    fun getTopStories(): Call<Result>
}

object RetrofitBuilder {
    var BASE_URL = "https://hn.algolia.com/api/v1/"


    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build() //Doesn't require the adapter
    }

    val apiService = getRetrofit().create(ApiService::class.java)
}



class StoryRepository {
    private val apiService = RetrofitBuilder.apiService

    fun getStories(updateStories: (List<Story>) -> Unit) {
        val mlc: Call<Result> = apiService.getStories()
        mlc.enqueue(object : Callback<Result> {
            override fun onResponse(call: Call<Result>?, response: Response<Result>?) {
                if (response?.body() != null) {
                    updateStories(response?.body()!!.hits)
                }
            }

            override fun onFailure(call: Call<Result>?, t: Throwable?) {
            }
        })
    }

    fun getTopStories(updateStories: (List<Story>) -> Unit) {
        val mlc: Call<Result> = apiService.getTopStories()
        mlc.enqueue(object : Callback<Result> {
            override fun onResponse(call: Call<Result>?, response: Response<Result>?) {
                if (response?.body() != null) {
                    updateStories(response?.body()!!.hits)
                }
            }

            override fun onFailure(call: Call<Result>?, t: Throwable?) {
            }
        })
    }
}