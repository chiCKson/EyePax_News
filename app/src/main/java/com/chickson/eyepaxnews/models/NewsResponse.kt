package com.chickson.eyepaxnews.models

data class NewsResponse(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int,
    var code: String = "",
    var message: String = ""
)