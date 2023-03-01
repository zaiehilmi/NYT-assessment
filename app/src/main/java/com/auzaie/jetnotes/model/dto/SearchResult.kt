package com.auzaie.jetnotes.model.dto

data class ArticleSearchResponse(
    val status: String,
    val results: List<NewsArticle>
)

data class NewsArticle(
    val headline: String,
    val snippet: String = "",
    val abstract: String = "",
)