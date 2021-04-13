package com.cuongtd.hackernews.viewmodel

import android.content.Context
import androidx.lifecycle.*
import androidx.room.Room
import com.cuongtd.hackernews.model.Story
import com.cuongtd.hackernews.model.room.AppDatabase
import com.cuongtd.hackernews.repository.StoryRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import com.cuongtd.hackernews.model.room.Story as StoryEntity

class StoryViewModel(context: Context) : ViewModel() {
    private val storyRepository = StoryRepository(context)

    private var _favoriteStories =
        MutableLiveData<List<StoryEntity>>()
    val favoriteStories: LiveData<List<StoryEntity>>
        get() = _favoriteStories

    private val _stories = MutableLiveData<List<Story>>()
    val stories: LiveData<List<Story>>
        get() = _stories

    fun updateStories(newStories: List<Story>) {
        _stories.value = newStories
    }

    private val _topStories = MutableLiveData<List<Story>>()
    val topStories: LiveData<List<Story>>
        get() = _topStories

    fun updateTopStories(newStories: List<Story>) {
        _topStories.value = newStories
    }

    fun addFavoriteStory(story: StoryEntity) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                storyRepository.addFavoriteStory(story)
            }
        }
    }

    fun deleteFavoriteStory(story: StoryEntity) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                storyRepository.deleteFavoriteStory(story)
            }
        }
    }

    fun getSingle(id: String): LiveData<StoryEntity>? =
        storyRepository.getSingle(id)

    init {
        viewModelScope.launch {
            storyRepository.getTopStories(::updateTopStories)
            storyRepository.getStories(::updateStories)
            storyRepository.getAllFavoriteStories().observeForever {
                _favoriteStories.value = it
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
    }
}