package com.auzaie.jetnotes.model

import com.auzaie.jetnotes.model.dto.NewsArticle

class Statez {
    companion object {
        var searchResult: List<NewsArticle>? = null
        var mostViewedResult: List<NewsArticle>? = null
    }
}