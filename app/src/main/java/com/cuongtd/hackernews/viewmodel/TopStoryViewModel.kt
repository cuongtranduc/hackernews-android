package com.cuongtd.hackernews.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cuongtd.hackernews.model.Story
import com.cuongtd.hackernews.repository.StoryRepository
import kotlinx.coroutines.*
import java.util.*

const val ONE_MIN: Long = 60000 // in millisecond

class TopStoryViewModel(context: Context) : ViewModel() {
    private val storyRepository = StoryRepository(context)
    private var page = 0
    private var lastUpdateTime = Date()
    private var repeatScheduleFun: Job

    var timeSinceLastUpdated = MutableLiveData(0)
    var isLoadingMore = MutableLiveData(false)
    var isRefreshing = MutableLiveData(false)
    var disableLoadingMore = MutableLiveData(false)

    private val _stories = MutableLiveData<List<Story>>(listOf())
    val stories: LiveData<List<Story>>
        get() = _stories

    private fun updateStories(newStories: List<Story>) {
        viewModelScope.launch(Dispatchers.Main) {
            if (page == 0) {
                _stories.value = newStories

                //reset last updated time
                timeSinceLastUpdated.value = 0
                lastUpdateTime = Date()

                isRefreshing.value = false
            } else { // load more
                _stories.value = _stories.value!!.plus(newStories)
                isLoadingMore.value = false
            }
            if (newStories.isEmpty()) {
                disableLoadingMore.value = true
            }
        }
    }

    fun getStories() {
        if (isLoadingMore.value == true) {
            return
        }
        if (page >= 1) {
            isLoadingMore.value = true
        }
        viewModelScope.launch(Dispatchers.IO) {
            storyRepository.getTopStories(::updateStories, page++)
        }
    }

    fun refreshStories() {
        page = 0
        isRefreshing.value = true
        viewModelScope.launch(Dispatchers.IO) {
            storyRepository.getTopStories(::updateStories, page)
        }
    }

    private fun scheduleTask(): Job {
        return viewModelScope.launch(Dispatchers.Default) {
            while (isActive) {
                val lastTime = ((Date().time - lastUpdateTime.time) / ONE_MIN).toInt()
                timeSinceLastUpdated.postValue(lastTime)
                delay(ONE_MIN)
            }
        }
    }

    init {
        getStories()
        //start the loop
        repeatScheduleFun = scheduleTask()
    }

    override fun onCleared() {
        super.onCleared()
        repeatScheduleFun.cancel()
    }
}