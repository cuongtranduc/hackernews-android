package com.cuongtd.hackernews.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Room
import com.cuongtd.hackernews.model.Result
import com.cuongtd.hackernews.model.Story
import com.cuongtd.hackernews.model.room.AppDatabase
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import com.cuongtd.hackernews.model.room.Story as StoryEntity


interface ApiService {
    @GET("search_by_date?tags=story&")
    fun getNewStories(@Query("page") page: String): Call<Result>

    @GET("search?tags=front_page&")
    fun getTopStories(@Query("page") page: String): Call<Result>
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


class StoryRepository(context: Context) {
    private val apiService = RetrofitBuilder.apiService
    val db = Room.databaseBuilder(
        context,
        AppDatabase::class.java, "story"
    )
//        .allowMainThreadQueries()
        .build()
    val storyDao = db.storyDao()

    fun getNewStories(updateStories: (List<Story>) -> Unit, page: Int) {
        val mlc: Call<Result> = apiService.getNewStories(page.toString())
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

    fun getTopStories(updateStories: (List<Story>) -> Unit, page: Int) {
        val mlc: Call<Result> = apiService.getTopStories(page.toString())
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

    fun getAllFavoriteStories(): LiveData<List<StoryEntity>> {
        return storyDao.getAll()
    }

    fun getSingle(id: String): LiveData<StoryEntity>? {
        return storyDao.getSingle(id)
    }

    fun addFavoriteStory(story: StoryEntity) {
        storyDao.insert(story)
    }

    fun deleteFavoriteStory(story: StoryEntity) {
        storyDao.delete(story)
    }
}