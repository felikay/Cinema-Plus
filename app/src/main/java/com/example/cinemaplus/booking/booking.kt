package com.example.cinemaplus.booking

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cinemaplus.R
import com.example.cinemaplus.location.LocationActivity
import com.example.cinemaplus.summary.SummaryActivity

data class Seat(val id: String, var status: SeatStatus)
enum class SeatStatus { Available, Booked, Selected }

class BookingActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BookingScreen(
                onBackClicked = { finish() },
                onContinueClicked = { navigateToSummary() }
            )
        }
    }

    private fun navigateToSummary() {
        val intent = Intent(this, SummaryActivity::class.java)
        startActivity(intent)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookingScreen(onBackClicked: () -> Unit, onContinueClicked: () -> Unit) {
    val seats = remember { mutableStateListOf<Seat>().apply {
        for (i in 1..30) {
            add(Seat("Seat $i", if (i % 6 == 0) SeatStatus.Booked else SeatStatus.Available))
        }
    }}
    var selectedSeats by remember { mutableStateOf(listOf<Seat>()) }

    Scaffold(
        topBar = {
            SmallTopAppBar(
                title = { Text("Choose Seats") },
                navigationIcon = {
                    IconButton(onClick = onBackClicked) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = Color(0xFF133755),
                    titleContentColor = Color.White
                )
            )
        },
        modifier = Modifier.background(Color(0xFF133755))
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .background(Color(0xFF133755))
        ) {
            MovieInfoSection(moviePoster = R.drawable.cinema_plus_logo, movieTitle = "Barbie", cinemaInfo = "Cinema Plus - CBD - 15:30 PM")
            NumberOfSeatsSelector(selectedSeats.size) { number ->
                if (number <= seats.count { it.status == SeatStatus.Available }) {
                    selectedSeats = seats.filter { it.status == SeatStatus.Available }.take(number)
                }
            }
            SeatLayout(seats) { seat ->
                onSeatSelected(seat, seats, selectedSeats) { newSelectedSeats ->
                    selectedSeats = newSelectedSeats
                }
            }
            SeatLegend()
            BottomBar(selectedSeats.size * 1200, onContinueClicked)
        }
    }
}

@Composable
fun MovieInfoSection(moviePoster: Int, movieTitle: String, cinemaInfo: String) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Image(
            painter = painterResource(id = moviePoster),
            contentDescription = null, // Decorative image
            modifier = Modifier.size(100.dp)
        )
        Spacer(Modifier.width(8.dp))
        Column {
            Text(movieTitle, color = Color.White, style = MaterialTheme.typography.titleLarge)
            Text(cinemaInfo, color = Color.White, style = MaterialTheme.typography.bodyMedium)
        }
    }
}

@Composable
fun NumberOfSeatsSelector(numberOfSeats: Int, onNumberChanged: (Int) -> Unit) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text("Number of seats", color = Color.White, modifier = Modifier.weight(1f))
        CounterButton("-", { onNumberChanged(maxOf(1, numberOfSeats - 1)) })
        Text("$numberOfSeats", color = Color.White, modifier = Modifier.padding(horizontal = 8.dp))
        CounterButton("+", { onNumberChanged(numberOfSeats + 1) })
    }
}

@Composable
fun CounterButton(text: String, onClick: () -> Unit) {
    Button(onClick = onClick, colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF141D31))) {
        Text(text, color = Color.White)
    }
}

@Composable
fun SeatLayout(seats: List<Seat>, onSeatClicked: (Seat) -> Unit) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(6),
        contentPadding = PaddingValues(8.dp),
        modifier = Modifier.padding(8.dp)
    ) {
        items(seats) { seat ->
            SeatIcon(seat, onSeatClicked)
        }
    }
}

@Composable
fun SeatIcon(seat: Seat, onSeatClicked: (Seat) -> Unit) {
    val backgroundColor = when (seat.status) {
        SeatStatus.Available -> Color.Green
        SeatStatus.Booked -> Color.Gray
        SeatStatus.Selected -> Color.Blue
    }
    Box(
        Modifier
            .size(48.dp)
            .padding(4.dp)
            .background(backgroundColor, shape = CircleShape)
            .clickable { onSeatClicked(seat) },
        contentAlignment = Alignment.Center
    ) {
        Text(seat.id, color = Color.White, fontSize = 12.sp)
    }
}

@Composable
fun SeatLegend() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        SeatStatusIndicator("Available", Color(0xFF25DA4B))
        SeatStatusIndicator("Booked", Color.Gray)
        SeatStatusIndicator("Selected", Color.Blue)
    }
}


@Composable
fun SeatStatusIndicator(text: String, color: Color) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier
                .size(20.dp)
                .background(color, shape = CircleShape)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(text, color = Color.White, fontSize = 12.sp)
    }
}

@Composable
fun BottomBar(totalAmount: Int, onContinueClicked: () -> Unit) {
    Column {
        Text(
            "Total: Ksh. $totalAmount",
            color = Color.White,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            textAlign = TextAlign.Center
        )
        Button(
            onClick = onContinueClicked,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .height(48.dp),
            shape = RoundedCornerShape(50.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF141D31)) // Updated color for Continue button
        ) {
            Text("Continue", color = Color.White)
        }
    }
}


fun onSeatSelected(seat: Seat, seats: List<Seat>, selectedSeats: List<Seat>, updateSelectedSeats: (List<Seat>) -> Unit) {
    if (seat.status == SeatStatus.Available && seat !in selectedSeats) {
        updateSelectedSeats(selectedSeats + seat)
        seat.status = SeatStatus.Selected
    } else if (seat.status == SeatStatus.Selected) {
        updateSelectedSeats(selectedSeats - seat)
        seat.status = SeatStatus.Available
    }
}