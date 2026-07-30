package com.example.drivemate.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

data class Booking(
    val date: String,
    val time: String,
    val instructor: String,
    val vehicle: String,
    val location: String,
    val status: String,
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyBookingsScreen(navController: NavController) {

    val bookings = listOf(
        Booking(
            "28 July 2026",
            "10:00 AM",
            "Rahul Sharma",
            "Manual",
            "Alkapuri, Vadodara",
            "Completed"
        ),
        Booking(
            "30 July 2026",
            "3:00 PM",
            "Amit Patel",
            "Automatic",
            "Manjalpur, Vadodara",
            "Upcoming"
        )
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("My Bookings")
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                        )
                    }
                },
            )
        }
    ) { padding ->

        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            items(bookings) { booking ->

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp)
                ) {

                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {

                        Text(
                            booking.date,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Text("🕒 ${booking.time}")
                        Text("👨‍🏫 ${booking.instructor}")
                        Text("🚗 ${booking.vehicle}")
                        Text("📍 ${booking.location}")

                        Spacer(modifier = Modifier.height(10.dp))

                        val statusColor =
                            if (booking.status == "Upcoming")
                                Color(0xFF2E7D32)
                            else
                                Color(0xFF1565C0)

                        Text(
                            text = booking.status,
                            color = statusColor,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}