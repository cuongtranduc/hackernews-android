package com.cuongtd.hackernews.repository

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.room.Room
import com.cuongtd.hackernews.model.Result
import com.cuongtd.hackernews.model.Story
import com.cuongtd.hackernews.model.room.AppDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
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

    val apiService: ApiService = getRetrofit().create(ApiService::class.java)
}


class StoryRepository(context: Context) {
    private val apiService = RetrofitBuilder.apiService
    private val db = Room.databaseBuilder(
        context,
        AppDatabase::class.java, "story"
    ).build()
    private val storyDao = db.storyDao()

    suspend fun getNewStories(updateStories: (List<Story>) -> Unit, page: Int) {
        val mlc: Call<Result> = apiService.getNewStories(page.toString())
        withContext(Dispatchers.IO) {
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

    suspend fun getTopStories(updateStories: (List<Story>) -> Unit, page: Int) {
        val mlc: Call<Result> = apiService.getTopStories(page.toString())
        withContext(Dispatchers.IO) {
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

    suspend fun getAllFavoriteStories(): LiveData<List<StoryEntity>> {
        return withContext(Dispatchers.IO) {
            storyDao.getAll()
        }
    }

    fun getSingle(id: String): LiveData<StoryEntity>? {
//        return withContext(Dispatchers.IO) {
        return storyDao.getSingle(id)
//        }
    }

    fun addFavoriteStory(story: StoryEntity) {
        storyDao.insert(story)
    }

    fun deleteFavoriteStory(story: StoryEntity) {
        storyDao.delete(story)
    }
}