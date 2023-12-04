package com.example.cinemaplus.summary

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cinemaplus.R
import com.example.cinemaplus.payment.PaymentActivity

class SummaryActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SummaryScreen(onConfirmClicked = { navigateToPayment() })
        }
    }

    private fun navigateToPayment() {
        val intent = Intent(this, PaymentActivity::class.java)
        startActivity(intent)
    }
}

@Composable
fun SummaryScreen(onConfirmClicked: () -> Unit) {
    Surface(modifier = Modifier.fillMaxSize(), color = Color(0xFF133755)) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Spacer(modifier = Modifier.height(32.dp))
            Image(
                painter = painterResource(id = R.drawable.barbie),
                contentDescription = "Movie Poster",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(MaterialTheme.shapes.medium)
            )
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "Barbie",
                color = Color.White,
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Text(
                text = "195 mins",
                color = Color.White,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Spacer(modifier = Modifier.height(16.dp))
            TicketDetails()
            Spacer(modifier = Modifier.height(24.dp))
            Spacer(modifier = Modifier.weight(1f))
            ConfirmButton(onConfirmClicked)
        }
    }
}

@Composable
fun TicketDetails() {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = "Tickets @: Kshs 1200",
            color = Color.White,
            fontSize = 18.sp,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            text = "Seats: Seat 1, Seat 2, Seat 3",
            color = Color.White,
            fontSize = 18.sp,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            text = "Snacks: Kshs. 0",
            color = Color.White,
            fontSize = 18.sp,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "TOTAL: Kshs. 3600",
            color = Color.White,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(vertical = 8.dp)
        )
    }
}

@Composable
fun ConfirmButton(onConfirmClicked: () -> Unit) {
    Button(
        onClick = onConfirmClicked,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .height(48.dp),
        shape = RoundedCornerShape(50),
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0B1F30))
    ) {
        Text("Confirm Order Kshs 3600", color = Color.White, fontSize = 18.sp)
    }
}