package com.example.movieexplorer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.movieexplorer.presentation.movies.MovieDetailsScreen
import com.example.movieexplorer.presentation.movies.MoviesScreen
import com.example.movieexplorer.ui.theme.MovieExplorerTheme
import dagger.hilt.android.AndroidEntryPoint
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MovieExplorerTheme {
                val nav = rememberNavController()
                NavHost(navController = nav, startDestination = "list") {

                    composable("list") {
                        MoviesScreen(
                            onMovieClick = { id -> nav.navigate("detail/$id") }
                        )
                    }

                    composable(
                        route = "detail/{id}",
                        arguments = listOf(
                            navArgument("id") { type = NavType.IntType }
                        )
                    ) { backStackEntry ->
                        val id = backStackEntry.arguments!!.getInt("id")
                        MovieDetailsScreen(
                            movieId = id,
                            onBack = { nav.popBackStack() }
                        )
                    }
                }
            }
        }
    }
}
