package com.example.taskmoengage.model.newsModel

data class NewsResponse(
    val articles: List<Article>,
    val status: String
)