package com.example.cinemaplus.showpage

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cinemaplus.R
import com.example.cinemaplus.location.LocationActivity

class MoviePage : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val movie = Movie(
                title = "Barbie",
                genre = "Comedy, Adventure, Romance",
                duration = "195 min",
                description = "The film stars Margot Robbie as the titular character and Ryan Gosling as Ken, and follows the pair on a journey of self-discovery following an existential crisis.",
                imageUrl = R.drawable.cinema_plus_logo
            )

            MoviePageContent(movie)
        }
    }
}

@Composable
fun MoviePageContent(movie: Movie) {

    val context = LocalContext.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF133755))
    ) {
        Column {
            Image(
                painter = painterResource(id = movie.imageUrl),
                contentDescription = movie.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(3f)
            )
            Spacer(modifier = Modifier.weight(1f))
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .background(
                    Brush.verticalGradient(
                        colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.7f)),
                        startY = 300f
                    )
                )
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(top = 16.dp)
            ) {
                Text(
                    text = movie.title,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )

                Text(
                    text = "Genre: ${movie.genre}",
                    fontSize = 16.sp,
                    color = Color.White
                )

                Text(
                    text = "Duration: ${movie.duration}",
                    fontSize = 16.sp,
                    color = Color.White
                )

                Spacer(modifier = Modifier.height(24.dp))


                Text(
                    text = movie.description,
                    fontSize = 14.sp,
                    color = Color.White,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .align(Alignment.CenterHorizontally)
                )

                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp)
                ) {
                    // Buttons for movie timings
                    ShowtimeButton("Mon 17")
                    ShowtimeButton("Tue 18")
                    ShowtimeButton("Wed 19")
                    ShowtimeButton("Thu 20")
                }
            }
        }

        Button(
            onClick = { /* TODO: Handle click */ },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 16.dp),
            shape = MaterialTheme.shapes.large
        ) {
            Text(text = "Continue")
        }
    }

    // Dummy Movie class to represent the movie data
    data class Movie(
        val title: String,
        val genre: String,
        val duration: String,
        val description: String,
        val imageUrl: Int
    )

    Button(
        onClick = {
            context.startActivity(Intent(context, LocationActivity::class.java))
        },
        modifier = Modifier
            .padding(bottom = 16.dp),
        shape = MaterialTheme.shapes.large
    ) {
        Text(text = "Continue")
    }

}

@Composable
fun ShowtimeButton(text: String) {
    Button(
        onClick = { /* TODO: Handle click */ },
        modifier = Modifier.padding(horizontal = 8.dp),
        shape = MaterialTheme.shapes.medium
    ) {
        Text(text = text)
    }
}

@Composable
fun BackButton(movie: Movie, onBack: () -> Unit) {
    Box(modifier = Modifier.fillMaxSize()) {
        // Image with back button
        Image(
            painter = painterResource(id = movie.imageUrl),
            contentDescription = movie.title,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxWidth()
        )
        IconButton(
            onClick = { onBack() },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp)
                .size(48.dp)
                .background(
                    color = Color(0xFF333333).copy(alpha = 0.5f),
                    shape = CircleShape // Circular shape
                ),
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back",
                tint = Color.White
            )
        }
    }
}