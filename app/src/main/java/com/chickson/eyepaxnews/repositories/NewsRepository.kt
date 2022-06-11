package com.chickson.eyepaxnews.repositories

import com.chickson.eyepaxnews.models.Article
import com.chickson.eyepaxnews.network.NewsResult
import com.chickson.eyepaxnews.network.services.NewsApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class NewsRepository @Inject constructor(private val newsSource: NewsApi) {
    fun getTopHeadlines(category: String? = null, country: String? = null) : Flow<NewsResult<List<Article>>> = flow {
        emit(newsSource.getTopHeadlines(category = category, country = country))
    }.flowOn(Dispatchers.IO)

    fun searchNews(query: String? = null, from: String? = null, sortBy: String? = null) : Flow<NewsResult<List<Article>>> = flow {
        emit(newsSource.searchNews(query = query, from = from, sortBy = sortBy))
    }.flowOn(Dispatchers.IO)
}