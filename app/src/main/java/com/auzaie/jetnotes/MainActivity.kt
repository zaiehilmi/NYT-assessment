package com.auzaie.jetnotes

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.auzaie.jetnotes.ui.theme.JetnotesTheme
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.auzaie.jetnotes.model.dto.LocationDetails
import com.auzaie.jetnotes.presentation.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainActivity : ComponentActivity() {

    private val applicationViewModel: ApplicationViewModel by viewModel<ApplicationViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val location by applicationViewModel.getLocationLiveData().observeAsState()

            JetnotesTheme {
                val navController = rememberNavController()
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val navController = rememberNavController()
                    NavigationAppHost(navController = navController, location = location)
                }
            }
        }
    }
    private @Composable
    fun GPS(location: LocationDetails?) {
        location?.let {
            Text(text = location.latitude)
            Text(text = location.longitude)
        }
    }
}

sealed class Destination(val route: String) {
    object Home: Destination("home")
    object Search: Destination("search")
    object Article: Destination("articlePage/{elementId}") {
        fun createRoute(elementId: Int) = "articlePage/$elementId"
    }
}

@Composable
fun NavigationAppHost(navController: NavHostController, location: LocationDetails?) {
    val context = LocalContext.current

    NavHost(navController = navController, startDestination = "home" ) {
        composable(Destination.Home.route) { Home(navController, location) }
        composable(Destination.Search.route) { ArticleSearch(navController)}
        composable(Destination.Article.route) {
            val elementId = it.arguments?.getString("elementId")?.toInt()

            if (elementId == null) {
                Toast.makeText(context, "Element Id is required", Toast.LENGTH_SHORT).show()
            } else {
                ArticlePage(elementId = elementId)
            }
        }
    }
}
