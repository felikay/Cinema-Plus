package com.example.cinemaplus.payment

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cinemaplus.R
import com.example.cinemaplus.home.Main
import com.example.cinemaplus.showpage.MoviePage

class Payment : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PaymentScreen(
                navigateToMain = { navigateToMain() }
            )
        }
    }

    private fun navigateToMain() {
        val intent = Intent(this, Main::class.java)
        startActivity(intent)
    }
}


@Composable
fun PaymentScreen(navigateToMain: () -> Unit) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(0xFF133755)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            TicketCard()
            BackToHomeButton(onBack = navigateToMain)
        }
    }
}

@Composable
fun TicketCard() {
    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .padding(horizontal = 24.dp, vertical = 64.dp)
            .fillMaxWidth(),
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .background(Color.White)
                .padding(16.dp)
        ) {
            Text("BARBIE", fontSize = 24.sp, color = Color.Black, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center)
            Spacer(modifier = Modifier.height(8.dp))
            Text("Ksh. 1200", fontSize = 18.sp, color = Color.Gray, textAlign = TextAlign.Center)
            Spacer(modifier = Modifier.height(8.dp))
            Text("Monday 18:30", fontSize = 18.sp, color = Color.Gray, textAlign = TextAlign.Center)
            Spacer(modifier = Modifier.height(8.dp))
            Text("17/11/2023", fontSize = 18.sp, color = Color.Gray, textAlign = TextAlign.Center)
            Spacer(modifier = Modifier.height(24.dp))
            Divider(color = Color.Black, thickness = 2.dp)
            Spacer(modifier = Modifier.height(24.dp))
            BarcodeView()
        }
    }
}

@Composable
fun BarcodeView() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        Image(
            painter = painterResource(id = R.drawable.barcode),
            contentDescription = "Barcode",
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp)
                .height(100.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            "9876437459963",
            color = Color.Black,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun BackToHomeButton(onBack: () -> Unit) {
    Button(
        onClick = onBack,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .height(48.dp),
        shape = RoundedCornerShape(50),
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1f2a44))
    ) {
        Text("Back to home", color = Color.White, fontSize = 18.sp)
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewPaymentScreen() {
    PaymentScreen(
        navigateToMain = {}
    )
}
