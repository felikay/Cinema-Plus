package com.example.cinemaplus.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cinemaplus.R

data class Movie(
    val title: String,
    val thumbnailResourceId: Int
)

@Composable
fun MovieThumbnail(movie: Movie, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .width(140.dp)
            .height(200.dp)
            .clip(RoundedCornerShape(8.dp))
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Image(
            painter = painterResource(id = movie.thumbnailResourceId),
            contentDescription = movie.title,
            contentScale = ContentScale.Crop
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Main(movieCategories: Map<String, List<Movie>>, navigateToMoviePage: (Movie) -> Unit) {
    val colors = if (isSystemInDarkTheme()) darkColorScheme() else lightColorScheme()

    MaterialTheme(colorScheme = colors) {
        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    title = { Text("Choose Movie") },
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                        containerColor = Color(0xFF133755),
                        titleContentColor = Color.White
                    ),
                )
            }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .background(Color(0xFF133755))
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                SearchBar()
                Spacer(modifier = Modifier.height(16.dp))
                movieCategories.forEach { (categoryName, movies) ->
                    CategorySection(categoryName, movies, navigateToMoviePage)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar() {
    var text by remember { mutableStateOf("") }
    OutlinedTextField(
        value = text,
        onValueChange = { newText -> text = newText },
        placeholder = { Text("Search", color = Color.Gray) },
        modifier = Modifier
            .fillMaxWidth(1.0f)
            .padding(16.dp)
            //.align(Alignment.CenterHorizontally)
            .clip(CircleShape),
        textStyle = TextStyle(color = Color.Black),
        singleLine = true,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            containerColor = Color(0xFFBDBDBD),
            textColor = Color.Black,
            placeholderColor = Color.Gray
        )
    )
}

@Composable
fun CategorySection(categoryName: String, movies: List<Movie>, navigateToMoviePage: (Movie) -> Unit) {
    Column {
        Text(
            text = categoryName,
            style = MaterialTheme.typography.titleMedium.copy(fontSize = 18.sp),
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 8.dp),
            color = Color.White
        )
        MovieCategoryRow(movies, navigateToMoviePage)
    }
}

@Composable
fun MovieCategoryRow(movies: List<Movie>, navigateToMoviePage: (Movie) -> Unit) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(horizontal = 16.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        items(movies) { movie ->
            MovieThumbnail(movie) {
                navigateToMoviePage(movie)
            }
        }
    }
}
