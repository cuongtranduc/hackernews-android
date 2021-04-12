package com.cuongtd.hackernews.viewmodel

import Story
import Result
import android.util.Log
import androidx.lifecycle.*
import com.cuongtd.hackernews.repository.StoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

class StoryViewModel : ViewModel() {
    private val storyRepository = StoryRepository()

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

    init {
        viewModelScope.launch {
            storyRepository.getTopStories(::updateTopStories)
            storyRepository.getStories(::updateStories)
        }
    }
}