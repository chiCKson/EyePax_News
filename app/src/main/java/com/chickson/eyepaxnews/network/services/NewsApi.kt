package com.chickson.eyepaxnews.network.services

import com.chickson.eyepaxnews.BuildConfig
import com.chickson.eyepaxnews.models.Article
import com.chickson.eyepaxnews.models.NewsResponse
import com.chickson.eyepaxnews.network.Config
import com.chickson.eyepaxnews.network.NewsResult
import io.ktor.client.request.*
import javax.inject.Inject

class NewsApi @Inject constructor(private val config: Config) {
    suspend fun getTopHeadlines(category: String? = null, country: String? = null,page: Int? = null): NewsResult<List<Article>> {
        val response:NewsResponse =  config.client.get(path = "${BuildConfig.NEWS_API_VERSION}/top-headlines"){
            parameter("category",category ?: "")
            parameter("country",country ?: "")
            parameter("pageSize", 20)
            parameter("page", page ?: 1)
        }
        return if (response.status == "ok") {
            NewsResult.Success(response.articles)
        } else {
            NewsResult.Failure(response.message)
        }
    }

    suspend fun searchNews(query: String? = null, from: String? = null, sortBy: String? = null, page: Int? = null): NewsResult<List<Article>> {
        val response:NewsResponse =  config.client.get(path = "${BuildConfig.NEWS_API_VERSION}/everything"){
            parameter("q",query ?: "")
            parameter("from",from ?: "")
            parameter("sortBy",sortBy ?: "")
            parameter("pageSize", 20)
            parameter("page", page ?: 1)
        }
        return if (response.status == "ok") {
            NewsResult.Success(response.articles)
        } else {
            NewsResult.Failure(response.message)
        }
    }
}