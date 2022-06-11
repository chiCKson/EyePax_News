package com.chickson.eyepaxnews

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chickson.eyepaxnews.models.Article
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

    var breakingNews = mutableStateOf(listOf(Article()))
    var selectedCategory = mutableStateOf("")
    var news = mutableStateOf(listOf(Article()))

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
                        breakingNews.value = response.result
                    }
                    is NewsResult.Failure-> {
                        println(response.message)
                    }
                }
            }
    }

    fun getNewsByCategories() = viewModelScope.launch {
        newsRepository.getTopHeadlines(category = selectedCategory.value)
            .onStart {
                //isLoading.value = true
            }
            .catch { e ->
                //isLoading.value = false
            }
            .collect { response ->
                when (response){
                    is NewsResult.Success-> {
                        news.value = response.result
                    }
                    is NewsResult.Failure-> {
                        println(response.message)
                    }
                }
            }
    }

    fun searchNews() = viewModelScope.launch {
        newsRepository.searchNews(query = "Apple")
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

    fun onSelectedCategoryChange(category: String){
        selectedCategory.value = category
        getNewsByCategories()
    }
}