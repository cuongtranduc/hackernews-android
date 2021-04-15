package com.cuongtd.hackernews.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cuongtd.hackernews.repository.StoryRepository
import kotlinx.coroutines.launch

class NewStoryViewModel(context: Context) : ViewModel() {
    private val storyRepository = StoryRepository(context)
    private var page = 0;

    var isLoadingMore = MutableLiveData(false)
    var isRefreshing = MutableLiveData(false)
    var disableLoadingMore = MutableLiveData(false)

    private val _stories = MutableLiveData<List<com.cuongtd.hackernews.model.Story>>(listOf())
    val stories: LiveData<List<com.cuongtd.hackernews.model.Story>>
        get() = _stories

    private fun updateStories(newStories: List<com.cuongtd.hackernews.model.Story>) {
        _stories.value = if (page == 0) newStories else _stories.value!!.plus(newStories)
        if (newStories.isEmpty()) {
            disableLoadingMore.value = true
        }
        isLoadingMore.value = false
        isRefreshing.value = false
    }

    fun getStories() {
        if (isLoadingMore.value == true) {
            return
        }
        if (page >= 1) {
            isLoadingMore.value = true
        }
        viewModelScope.launch {
            storyRepository.getNewStories(::updateStories, page++)
        }
    }

    fun refreshStories() {
        page = 0
        isRefreshing.value = true
        viewModelScope.launch {
            storyRepository.getNewStories(::updateStories, page)
        }
    }

    init {
        getStories()
    }

    override fun onCleared() {
        super.onCleared()
    }
}