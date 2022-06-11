package com.chickson.eyepaxnews.models

data class Article(
    var author: String? = null,
    var content: String? = null,
    var description: String? = null,
    var publishedAt: String? = null,
    var source: Source? = null,
    var title: String? = null,
    var url: String? = null,
    var urlToImage: String? = null
)