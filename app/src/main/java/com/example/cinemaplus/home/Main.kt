package com.example.cinemaplus.home

import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.cinemaplus.MainActivity
import com.example.cinemaplus.R
import com.google.firebase.auth.FirebaseAuth
import com.example.cinemaplus.showpage.MoviePage
class Main {
    companion object {
        @Composable
        fun MainUI(fullName: String, userName: String, location: String) {
            val context = LocalContext.current
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp) // Add padding
                    .wrapContentSize(Alignment.Center)
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "Welcome, $fullName",
                        color = MaterialTheme.colorScheme.onSurface // Adapts to theme
                    )
                    // Add more UI components as needed
                    // Example:
                    Text(
                        text = "Username: $userName",
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = "Location: $location",
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Button(
                        onClick = {
                            FirebaseAuth.getInstance().signOut()
                            val intent = Intent(context, MainActivity::class.java)
                            context.startActivity(intent)
                        },
                        modifier = Modifier
                            .fillMaxWidth(1f)
                            .clip(RoundedCornerShape(10.dp))
                            .height(40.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center,
                            modifier = Modifier.fillMaxWidth(0.8f),
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.logo_250dp),
                                contentDescription = "sign-up",
                                tint = Color.Unspecified // Removing tint in order to keep the original icon color
                            )
                            Spacer(modifier = Modifier.width(8.dp)) // Space between icon and text
                            Text(
                                text = "Log Out",
                                fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                                color = MaterialTheme.colorScheme.onBackground
                            )
                        }
                    }
                    Spacer(modifier = Modifier.padding(10.dp))

                    Button(
                        onClick = {
                            val intent = Intent(context, MoviePage::class.java)
                            context.startActivity(intent)
                        },
                        modifier = Modifier
                            .fillMaxWidth(1f)
                            .clip(RoundedCornerShape(10.dp))
                            .height(40.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)
                    ) {
                        Text(
                            text = "Go to Movie Page",
                            fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    }
                }
            }
        }
    }
}

