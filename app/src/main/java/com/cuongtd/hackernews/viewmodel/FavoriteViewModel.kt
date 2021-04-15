package com.cuongtd.hackernews.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cuongtd.hackernews.repository.StoryRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import com.cuongtd.hackernews.model.room.Story as StoryEntity

class FavoriteViewModel(context: Context) : ViewModel() {
    private val storyRepository = StoryRepository(context)

    private var _favoriteStories =
        MutableLiveData<List<StoryEntity>>(emptyList())
    val favoriteStories: LiveData<List<StoryEntity>>
        get() = _favoriteStories

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

    fun getFavoriteById(id: String): LiveData<StoryEntity>? =
        storyRepository.getSingle(id)

    init {
        viewModelScope.launch {
            storyRepository.getAllFavoriteStories().observeForever {
                _favoriteStories.value = it
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
    }
}