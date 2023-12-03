package com.example.cinemaplus

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.cinemaplus.home.Main
import com.example.cinemaplus.ui.theme.CinemaPlusTheme
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlin.system.exitProcess

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CinemaPlusTheme {
                MyApp()
            }
        }
    }
}

@Composable
fun MyApp() {
    val context = LocalContext.current
    val user by remember { mutableStateOf(FirebaseAuth.getInstance().currentUser) }
    var showMainUI by remember { mutableStateOf(false) }
    var userData by remember { mutableStateOf(UserData()) }
    var isLoading by remember { mutableStateOf(user != null) } // Initially true if user is not null

    LaunchedEffect(user) {
        if (user != null) {
            FirebaseFirestore.getInstance().collection("users").document(user!!.uid).get()
                .addOnSuccessListener { document ->
                    if (document != null) {
                        userData = UserData(
                            fullName = document.getString("fullName") ?: "",
                            username = document.getString("username") ?: "",
                            location = document.getString("location") ?: ""
                        )
                        showMainUI = true // Set to true on successful data fetch
                    } else {
                        Toast.makeText(context, "Your record does not exist", Toast.LENGTH_SHORT)
                            .show()
                    }
                }.addOnFailureListener { e ->
                    Toast.makeText(context, "Error ${e.message} loading data", Toast.LENGTH_SHORT)
                        .show()
                }.addOnCompleteListener {
                    isLoading = false
                }
        } else {
            isLoading = false // Set to false if there is no user
        }
    }

    if (user != null && showMainUI) {
        BackHandler {
            // Define what should happen when the back button is pressed
            // For example, you could simply do nothing to disable back navigation:
            // or you could close the app or move it to background:
            exitProcess(0)
        }
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.primary
    ) {
        when {
            isLoading -> LoadingUI()
            user != null && showMainUI -> Main.MainUI(
                fullName = userData.fullName,
                userName = userData.username,
                location = userData.location
            )

            //else -> Login.LoginUI()
        }
    }
}


data class UserData(
    val fullName: String = "",
    val username: String = "",
    val location: String = ""
)

@Composable
fun LoadingUI() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center)
    ) {
        CircularProgressIndicator(
            modifier = Modifier
                .width(64.dp),
            color = MaterialTheme.colorScheme.onSurface,
        )
    }
}
