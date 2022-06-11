package com.chickson.eyepaxnews

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chickson.eyepaxnews.network.NewsResult
import com.chickson.eyepaxnews.repositories.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel
@Inject
constructor(
    private val newsRepository: NewsRepository
    ): ViewModel( ){

    fun getTopHeadlines() = viewModelScope.launch {
        newsRepository.getTopHeadlines(category = "sports")
            .onStart {
                //isLoading.value = true
            }
            .catch { e ->
                //isLoading.value = false
            }
            .collect { response ->
                when (response){
                    is NewsResult.Success-> {
                        println(response.result.size)
                    }
                    is NewsResult.Failure-> {
                        println(response.message)
                    }
                }
            }
    }
}