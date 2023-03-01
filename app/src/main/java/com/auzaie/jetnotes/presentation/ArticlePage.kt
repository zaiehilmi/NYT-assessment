package com.auzaie.jetnotes.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.auzaie.jetnotes.R
import com.auzaie.jetnotes.model.NewsApiClient
import com.auzaie.jetnotes.model.Statez.Companion.mostViewedResult
import com.auzaie.jetnotes.model.Statez.Companion.searchResult
import com.auzaie.jetnotes.model.dto.NewsArticle

@Composable
fun ArticlePage(elementId: Int) {

    Scaffold(
        topBar = { TopAppBar(
            title = { Text(text = stringResource(id = R.string.label_articles)) },
            elevation = 12.dp
        )},
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp, 30.dp)
        ) {
            if (elementId == 1) {
                items(searchResult!!) { article ->
                    ArticleCard(article)
                }
            } else if (elementId == 2) {
                items(mostViewedResult!!) {article ->
                    ArticleCard(article)
                }
            }
        }
    }
}

@Composable
fun ArticleCard(article: NewsArticle) {
    Card(modifier = Modifier
        .height(150.dp)
        .fillMaxWidth()
        .padding(0.dp, 7.dp)
    ) {
        Column(modifier = Modifier.padding(5.dp)) {
            Text(text = article.headline, fontWeight = FontWeight.SemiBold)

            if (article.snippet != "") Text(text = article.snippet,  fontWeight = FontWeight.ExtraLight)
            if (article.abstract != "")Text(text = article.abstract,  fontWeight = FontWeight.ExtraLight)
        }
    }
}