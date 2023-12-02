package com.example.cinemaplus.showpage

import androidx.annotation.DrawableRes

data class Movie(
    val title: String,
    val genre: String,
    val duration: String,
    val description: String,
    @DrawableRes val imageUrl: Int
)