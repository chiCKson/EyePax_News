package com.chickson.eyepaxnews.network.services

import com.chickson.eyepaxnews.BuildConfig
import com.chickson.eyepaxnews.models.Article
import com.chickson.eyepaxnews.models.NewsResponse
import com.chickson.eyepaxnews.network.Config
import com.chickson.eyepaxnews.network.NewsResult
import io.ktor.client.request.*
import javax.inject.Inject

class NewsApi @Inject constructor(private val config: Config) {
    suspend fun getTopHeadlines(category: String? = null, country: String? = null): NewsResult<List<Article>> {
        val response:NewsResponse =  config.client.get(path = "${BuildConfig.NEWS_API_VERSION}/top-headlines"){
            parameter("category",category ?: "")
            parameter("country",country ?: "")
        }
        return if (response.status == "ok") {
            NewsResult.Success(response.articles)
        } else {
            NewsResult.Failure(response.message)
        }
    }
}