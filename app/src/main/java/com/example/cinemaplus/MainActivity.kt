package com.example.cinemaplus

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.cinemaplus.ui.theme.CinemaPlusTheme
import com.example.cinemaplus.home.Main
import com.example.cinemaplus.home.Movie
import com.example.cinemaplus.showpage.MoviePage

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CinemaPlusTheme {
                val nowPlayingMovies = listOf(
                    Movie("Movie 1", R.drawable.cinema_plus_logo),
                    Movie("Movie 2", R.drawable.cinema_plus_logo),
                    Movie("Movie 3", R.drawable.cinema_plus_logo)
                )
                val comingSoonMovies = listOf(
                    Movie("Movie 4", R.drawable.cinema_plus_logo),
                    Movie("Movie 5", R.drawable.cinema_plus_logo),
                    Movie("Movie 6", R.drawable.cinema_plus_logo)
                )
                val topMovies = listOf(
                    Movie("Movie 7", R.drawable.cinema_plus_logo),
                    Movie("Movie 8", R.drawable.cinema_plus_logo),
                    Movie("Movie 9", R.drawable.cinema_plus_logo)
                )

                val movieCategories = mapOf(
                    "Now Playing" to nowPlayingMovies,
                    "Coming Soon" to comingSoonMovies,
                    "Top Movies" to topMovies
                )

                Main(movieCategories = movieCategories) { movie ->
                    val intent = Intent(this, MoviePage::class.java)
                    intent.putExtra("MOVIE_TITLE", movie.title)
                    intent.putExtra("MOVIE_IMAGE_ID", movie.thumbnailResourceId)
                    startActivity(intent)
                }
            }
        }
    }
}