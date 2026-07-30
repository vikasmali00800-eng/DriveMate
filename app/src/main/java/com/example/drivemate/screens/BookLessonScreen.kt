package com.example.drivemate.screens

import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookLessonScreen(
    navController: NavController
) {

    val context = LocalContext.current

    var date by remember { mutableStateOf("28 July 2026") }
    var time by remember { mutableStateOf("10:00 AM") }
    var pickup by remember { mutableStateOf("") }
    var vehicle by remember { mutableStateOf("Manual") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Book Driving Lesson")
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            navController.popBackStack()
                        }
                    ) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .padding(20.dp)
                .verticalScroll(rememberScrollState())
        ) {

            OutlinedTextField(
                value = date,
                onValueChange = { date = it },
                label = { Text("Select Date") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = time,
                onValueChange = { time = it },
                label = { Text("Select Time") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Vehicle Type",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {

                RadioButton(
                    selected = vehicle == "Manual",
                    onClick = {
                        vehicle = "Manual"
                    }
                )

                Text("Manual")

                Spacer(modifier = Modifier.width(20.dp))

                RadioButton(
                    selected = vehicle == "Automatic",
                    onClick = {
                        vehicle = "Automatic"
                    }
                )

                Text("Automatic")
            }

            Spacer(modifier = Modifier.height(20.dp))

            OutlinedTextField(
                value = "Select on next screen",
                onValueChange = {},
                readOnly = true,
                label = {
                    Text("Instructor")
                },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(20.dp))

            OutlinedTextField(
                value = pickup,
                onValueChange = {
                    pickup = it
                },
                label = {
                    Text("Pickup Location")
                },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(25.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp)
            ) {

                Column(
                    modifier = Modifier.padding(20.dp)
                ) {

                    Text(
                        text = "Lesson Fee",
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Starting from ₹450",
                        fontSize = 25.sp,
                        color = Color(0xFF1565C0),
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(30.dp))

            Button(
                onClick = {

                    if (date.isBlank()) {

                        Toast.makeText(
                            context,
                            "Enter lesson date",
                            Toast.LENGTH_SHORT
                        ).show()

                    } else if (time.isBlank()) {

                        Toast.makeText(
                            context,
                            "Enter lesson time",
                            Toast.LENGTH_SHORT
                        ).show()

                    } else if (pickup.isBlank()) {

                        Toast.makeText(
                            context,
                            "Enter pickup location",
                            Toast.LENGTH_SHORT
                        ).show()

                    } else {

                        val encodedDate = Uri.encode(date)
                        val encodedTime = Uri.encode(time)
                        val encodedVehicle = Uri.encode(vehicle)
                        val encodedPickup = Uri.encode(pickup)

                        navController.navigate(
                            "instructor/$encodedDate/$encodedTime/$encodedVehicle/$encodedPickup"
                        )
                    }
                },

                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp),

                shape = RoundedCornerShape(16.dp)
            ) {

                Text("Choose Instructor")
            }
        }
    }
}