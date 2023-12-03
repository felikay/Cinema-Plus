@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.cinemaplus.booking

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cinemaplus.R

class BookingActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BookingScreen()
        }
    }
}

@Composable
fun BookingScreen() {
    var numberOfSeats by remember { mutableStateOf(2) }
    val seatMatrix = List(5) { List(8) { mutableStateOf(SeatStatus.Available) } }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Choose seats") },
                navigationIcon = {
                    IconButton(onClick = { /* Handle back press */ }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = Color(0xFF133755),
                    titleContentColor = Color.White
                )
            )
        },
        modifier = Modifier.fillMaxSize()
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(Color(0xFF133755))
                .padding(16.dp)
        ) {
            MovieSessionInfo()
            Spacer(modifier = Modifier.height(16.dp))
            NumberOfSeatsSelector(numberOfSeats) { newCount -> numberOfSeats = newCount }
            Spacer(modifier = Modifier.height(16.dp))
            Text("SCREEN", modifier = Modifier.align(Alignment.CenterHorizontally), color = Color.White)
            Spacer(modifier = Modifier.height(16.dp))
            SeatSelectionGrid(seatMatrix)
            Spacer(modifier = Modifier.height(24.dp))
            SeatStatusLegend()
            Spacer(modifier = Modifier.height(24.dp))
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Total: Kshs.1200", color = Color.White, fontWeight = FontWeight.Bold)
                Button(
                    onClick = { /* Handle continue */ },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF141D31))
                ) {
                    Text("Continue", color = Color.White)
                }
            }
        }
    }
}

@Composable
fun MovieSessionInfo() {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.cinema_plus_logo),
            contentDescription = "Movie Poster",
            modifier = Modifier.size(48.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column {
            Text("Barbie", color = Color.White)
            Text("Cinema X - CBD", color = Color.White)
            Text("Mon 18 2023 15:30", color = Color.White)
        }
    }
}

@Composable
fun NumberOfSeatsSelector(count: Int, updateCount: (Int) -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = { if (count > 1) updateCount(count - 1) }) {
            Icon(Icons.Default.Delete, contentDescription = "Remove", tint = Color.White)
        }
        Text("$count", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
        IconButton(onClick = { updateCount(count + 1) }) {
            Icon(Icons.Default.Add, contentDescription = "Add", tint = Color.White)
        }
    }
}

@Composable
fun SeatSelectionGrid(seatMatrix: List<List<MutableState<SeatStatus>>>) {
    Column {
        seatMatrix.forEach { row ->
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                row.forEach { seat ->
                    SeatButton(seat.value) { newStatus -> seat.value = newStatus }
                }
            }
        }
    }
}

@Composable
fun SeatButton(status: SeatStatus, updateStatus: (SeatStatus) -> Unit) {
    val color = when (status) {
        SeatStatus.Available -> Color.Gray
        SeatStatus.Reserved -> Color.Red
        SeatStatus.Selected -> Color.Green
    }
    IconButton(onClick = { updateStatus(SeatStatus.Selected) }) {
        Box(
            modifier = Modifier
                .size(24.dp)
                .background(color, CircleShape)
        )
    }
}

@Composable
fun SeatStatusLegend() {
    Row {
        LegendItem(color = Color.Gray, text = "Available")
        LegendItem(color = Color.Red, text = "Reserved")
        LegendItem(color = Color.Green, text = "Selected")
    }
}

@Composable
fun LegendItem(color: Color, text: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier
                .size(16.dp)
                .background(color, CircleShape)
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(text, color = Color.White, fontSize = 12.sp)
    }
}

enum class SeatStatus {
    Available, Reserved, Selected
}
