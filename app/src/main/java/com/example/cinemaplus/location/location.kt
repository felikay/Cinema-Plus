package com.example.cinemaplus.location

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Brush
import com.example.cinemaplus.booking.BookingActivity
import com.example.cinemaplus.booking.BookingScreen
import com.example.cinemaplus.payment.PaymentActivity
import com.example.cinemaplus.showpage.MoviePage

val gradientColors = listOf(Color(0xFF133755), Color(0xFF1f2a44))
val gradientBrush = Brush.linearGradient(gradientColors)

class LocationActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ChooseTimeAndPlace(
                onBack = { navigateToMoviePage() },
                onContinue = { navigateToBooking() }
            )
        }
    }

    private fun navigateToMoviePage() {
        val intent = Intent(this, MoviePage::class.java)
        startActivity(intent)
    }

    private fun navigateToBooking() {
        val intent = Intent(this, BookingActivity::class.java)
        startActivity(intent)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChooseTimeAndPlace(onBack: () -> Unit, onContinue: () -> Unit) {
    var selectedCinemaAndTime by remember { mutableStateOf<Pair<String, String>?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Choose place and time") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = Color(0xFF133755),
                    titleContentColor = Color.White
                )
            )
        },
        containerColor = Color.Transparent,
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(brush = gradientBrush)
                .padding(padding)
        ) {
            Column {
                val cinemas = listOf(
                    "Cinema Plus - CBD" to listOf("15:30", "18:30", "21:30"),
                    "Cinema Plus - Panari" to listOf("08:40", "12:40", "20:40"),
                    "Cinema Plus - Sarit" to listOf("09:30", "13:30", "17:30"),
                    "Cinema Plus - Prestige" to listOf("09:50", "12:50", "16:50")
                )

                cinemas.forEach { (cinema, times) ->
                    Card(
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFF1f2a44)),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                        ) {
                            Text(
                                text = cinema,
                                color = Color.White,
                                style = MaterialTheme.typography.headlineSmall,
                                modifier = Modifier.padding(bottom = 8.dp)
                            )

                            Row(
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                times.forEach { time ->
                                    TimeButton(
                                        time = time,
                                        isSelected = selectedCinemaAndTime == cinema to time,
                                        onClick = { selectedCinemaAndTime = cinema to time }
                                    )
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = onContinue,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 32.dp),
                    shape = RoundedCornerShape(50),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF141D31))
                ) {
                    Text("Continue", color = Color.White)
                }
            }
        }
    }
}

@Composable
fun TimeButton(time: String, isSelected: Boolean, onClick: () -> Unit) {
    val backgroundColor = if (isSelected) Color(0xFF133755) else Color.Gray
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(50),
        colors = ButtonDefaults.buttonColors(backgroundColor),
        modifier = Modifier
    ) {
        Text(time, color = Color.White)
    }
}
