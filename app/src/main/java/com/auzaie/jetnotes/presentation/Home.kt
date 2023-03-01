package com.auzaie.jetnotes.presentation

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource

import androidx.compose.ui.unit.dp

import androidx.navigation.NavHostController
import com.auzaie.jetnotes.Destination
import com.auzaie.jetnotes.R
import com.auzaie.jetnotes.model.NewsApiClient
import com.auzaie.jetnotes.model.NewsApiClient.Companion.mostViewedArticle
import com.auzaie.jetnotes.model.Statez
import com.auzaie.jetnotes.model.dto.LocationDetails

@Composable
fun Home(navController: NavHostController, location: LocationDetails?) {

    Scaffold(
        topBar = { TopAppBar(
            title = {Text(stringResource(id = R.string.app_name))},
            elevation = 12.dp
        )},
        bottomBar = { BottomAppBar {
            Text(text = "Location:  ")
            location?.latitude?.let { Text(text = it) }
            Spacer(modifier = Modifier.width(10.dp))
            location?.longitude?.let { Text(text = it) }
        }},
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp, 30.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = stringResource(id = R.string.label_search),
                    )
                ButtonMain(
                    label = stringResource(id = R.string.button_label_search),
                    onClick = {
                        navController.navigate(Destination.Search.route)
                    })

                Spacer(modifier = Modifier.height(30.dp))

                Text(
                    text = stringResource(id = R.string.label_popular),
                    )

                ButtonMain(
                    label = stringResource(id = R.string.button_label_mostViewed),
                    onClick = {
                        mostViewedArticle()

                        Thread.sleep(3_000)
                        Log.d("RESULT", Statez.mostViewedResult?.size.toString())

                        if (Statez.mostViewedResult != null)
                            navController.navigate(Destination.Article.createRoute(2))
                        else
                            Log.e("RESULT", "most viewed sifar")
                    })
                ButtonMain(
                    label = stringResource(id = R.string.button_label_mostShared),
                    onClick = { /*TODO*/ })
                ButtonMain(
                    label = stringResource(id = R.string.button_label_mostEmailed),
                    onClick = { /*TODO*/ })
            }
        }
    )
}

@Composable
fun ButtonMain(label: String, onClick: () -> Unit) {
    Button(
        modifier = Modifier
            .fillMaxWidth(),
        colors = ButtonDefaults.outlinedButtonColors(),
        onClick = onClick) {
        Text(
            text = label,
            color = Color.DarkGray
        )
        Spacer(modifier = Modifier.fillMaxWidth())
        Icon(
            imageVector = Icons.Filled.ChevronRight,
            contentDescription = stringResource(id = R.string.label_search),
            tint = Color.Black)
    }
}