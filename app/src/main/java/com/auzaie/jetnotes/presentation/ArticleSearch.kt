package com.auzaie.jetnotes.presentation

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.auzaie.jetnotes.Destination
import com.auzaie.jetnotes.R
import com.auzaie.jetnotes.model.NewsApiClient.Companion.query
import com.auzaie.jetnotes.model.NewsApiClient.Companion.searchArticles
import com.auzaie.jetnotes.model.Statez.Companion.searchResult

@Composable
fun ArticleSearch(navController: NavHostController) {
    var queryArticle by remember { mutableStateOf("") }

    Scaffold(
        topBar = { TopAppBar(
            title = { Text(stringResource(id = R.string.button_label_search)) },
            elevation = 12.dp
        )},

    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            TextField(
                value = queryArticle,
                onValueChange = {queryArticle = it},
                label = { Text(text = stringResource(
                    id = R.string.textfield_search_hint
            ))})
            Button(
                enabled = queryArticle != "",
                onClick = {
                    query = queryArticle
                    Log.d("RESULT", queryArticle)

                    searchArticles(queryArticle)

                    Thread.sleep(3_000)
                    Log.d("RESULT", searchResult?.size.toString())

                    if (query != null && searchResult != null)
                        navController.navigate(Destination.Article.createRoute(1))
                    else
                        Log.e("RESULT", "carian sifar")
                }) {
                Text(stringResource(id = R.string.label_search))
            }
        }
    }
}