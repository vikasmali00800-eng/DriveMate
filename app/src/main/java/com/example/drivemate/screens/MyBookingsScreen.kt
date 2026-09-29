package com.example.drivemate.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
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
    val status: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyBookingsScreen(
    navController: NavController
) {

    val bookings = listOf(

        Booking(
            date = "28 July 2026",
            time = "10:00 AM",
            instructor = "Rahul Sharma",
            vehicle = "Manual",
            location = "Alkapuri, Vadodara",
            status = "Completed"
        ),

        Booking(
            date = "30 July 2026",
            time = "3:00 PM",
            instructor = "Amit Patel",
            vehicle = "Automatic",
            location = "Manjalpur, Vadodara",
            status = "Upcoming"
        )
    )

    Scaffold(
        containerColor = Color(0xFFF5F8FF),

        topBar = {

            TopAppBar(

                title = {

                    Column {

                        Text(
                            text = "My Bookings",
                            color = Color.Black,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Text(
                            text = "${bookings.size} lessons",
                            color = Color.Gray,
                            fontSize = 12.sp
                        )
                    }
                },

                navigationIcon = {

                    IconButton(
                        onClick = {
                            navController.popBackStack()
                        }
                    ) {

                        Icon(
                            imageVector =
                                Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = Color(0xFF1565C0)
                        )
                    }
                },

                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White,
                    titleContentColor = Color.Black
                )
            )
        }

    ) { padding ->

        LazyColumn(

            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(
                    Color(0xFFF5F8FF)
                )
                .padding(20.dp),

            verticalArrangement =
                Arrangement.spacedBy(18.dp),

            contentPadding =
                PaddingValues(
                    bottom = 25.dp
                )
        ) {

            // =========================================
            // HEADER
            // =========================================

            item {

                Text(
                    text = "Your Driving Lessons",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )

                Spacer(
                    modifier =
                        Modifier.height(5.dp)
                )

                Text(
                    text =
                        "Track your upcoming and completed lessons.",
                    fontSize = 13.sp,
                    color = Color.Gray
                )
            }

            // =========================================
            // BOOKING CARDS
            // =========================================

            items(bookings) { booking ->

                BookingCard(
                    booking = booking
                )
            }
        }
    }
}


// =====================================================
// BOOKING CARD
// =====================================================

@Composable
private fun BookingCard(
    booking: Booking
) {

    val isUpcoming =
        booking.status == "Upcoming"

    val statusColor =
        if (isUpcoming)
            Color(0xFF2E7D32)
        else
            Color(0xFF1565C0)

    val statusBackground =
        if (isUpcoming)
            Color(0xFFE8F5E9)
        else
            Color(0xFFE3F2FD)

    Card(
        modifier =
            Modifier.fillMaxWidth(),

        shape =
            RoundedCornerShape(24.dp),

        colors =
            CardDefaults.cardColors(
                containerColor =
                    Color.White
            ),

        elevation =
            CardDefaults.cardElevation(
                defaultElevation = 5.dp
            )
    ) {

        Column(
            modifier =
                Modifier.padding(20.dp)
        ) {

            // =========================================
            // DATE + STATUS
            // =========================================

            Row(
                modifier =
                    Modifier.fillMaxWidth(),

                horizontalArrangement =
                    Arrangement.SpaceBetween,

                verticalAlignment =
                    Alignment.CenterVertically
            ) {

                Row(
                    verticalAlignment =
                        Alignment.CenterVertically
                ) {

                    Surface(
                        modifier =
                            Modifier.size(48.dp),

                        shape =
                            RoundedCornerShape(14.dp),

                        color =
                            Color(0xFFE3F2FD)
                    ) {

                        Box(
                            contentAlignment =
                                Alignment.Center
                        ) {

                            Icon(
                                imageVector =
                                    Icons.Default.CalendarMonth,

                                contentDescription =
                                    null,

                                tint =
                                    Color(0xFF1565C0)
                            )
                        }
                    }

                    Spacer(
                        modifier =
                            Modifier.width(12.dp)
                    )

                    Column {

                        Text(
                            text =
                                booking.date,

                            fontSize = 18.sp,

                            fontWeight =
                                FontWeight.Bold,

                            color =
                                Color.Black
                        )

                        Spacer(
                            modifier =
                                Modifier.height(3.dp)
                        )

                        Text(
                            text =
                                booking.time,

                            fontSize = 13.sp,

                            color =
                                Color.Gray
                        )
                    }
                }

                Surface(
                    color =
                        statusBackground,

                    shape =
                        RoundedCornerShape(50.dp)
                ) {

                    Row(
                        modifier =
                            Modifier.padding(
                                horizontal = 10.dp,
                                vertical = 7.dp
                            ),

                        verticalAlignment =
                            Alignment.CenterVertically
                    ) {

                        if (!isUpcoming) {

                            Icon(
                                imageVector =
                                    Icons.Default.CheckCircle,

                                contentDescription =
                                    null,

                                tint =
                                    statusColor,

                                modifier =
                                    Modifier.size(15.dp)
                            )

                            Spacer(
                                modifier =
                                    Modifier.width(4.dp)
                            )
                        }

                        Text(
                            text =
                                booking.status,

                            color =
                                statusColor,

                            fontSize = 11.sp,

                            fontWeight =
                                FontWeight.Bold
                        )
                    }
                }
            }

            Spacer(
                modifier =
                    Modifier.height(18.dp)
            )

            HorizontalDivider()

            Spacer(
                modifier =
                    Modifier.height(16.dp)
            )

            // =========================================
            // INSTRUCTOR
            // =========================================

            BookingInfoRow(
                icon = {
                    Icon(
                        imageVector =
                            Icons.Default.Person,
                        contentDescription = null,
                        tint =
                            Color(0xFF1565C0),
                        modifier =
                            Modifier.size(20.dp)
                    )
                },
                title = "Instructor",
                value = booking.instructor
            )

            Spacer(
                modifier =
                    Modifier.height(13.dp)
            )

            // =========================================
            // VEHICLE
            // =========================================

            BookingInfoRow(
                icon = {
                    Text(
                        text = "🚗",
                        fontSize = 18.sp
                    )
                },
                title = "Vehicle",
                value = booking.vehicle
            )

            Spacer(
                modifier =
                    Modifier.height(13.dp)
            )

            // =========================================
            // LOCATION
            // =========================================

            BookingInfoRow(
                icon = {
                    Icon(
                        imageVector =
                            Icons.Default.LocationOn,
                        contentDescription = null,
                        tint =
                            Color(0xFFE53935),
                        modifier =
                            Modifier.size(20.dp)
                    )
                },
                title = "Pickup Location",
                value = booking.location
            )

            Spacer(
                modifier =
                    Modifier.height(13.dp)
            )

            // =========================================
            // TIME
            // =========================================

            BookingInfoRow(
                icon = {
                    Icon(
                        imageVector =
                            Icons.Default.AccessTime,
                        contentDescription = null,
                        tint =
                            Color(0xFF1565C0),
                        modifier =
                            Modifier.size(20.dp)
                    )
                },
                title = "Lesson Time",
                value = booking.time
            )

            Spacer(
                modifier =
                    Modifier.height(18.dp)
            )

            // =========================================
            // STATUS MESSAGE
            // =========================================

            Surface(
                modifier =
                    Modifier.fillMaxWidth(),

                color =
                    if (isUpcoming)
                        Color(0xFFF1F8E9)
                    else
                        Color(0xFFF5F8FF),

                shape =
                    RoundedCornerShape(14.dp)
            ) {

                Text(
                    text =
                        if (isUpcoming)
                            "🔔 Your lesson is coming up. Don't forget your driving lesson!"
                        else
                            "✓ This driving lesson has been completed.",

                    modifier =
                        Modifier.padding(13.dp),

                    color =
                        if (isUpcoming)
                            Color(0xFF33691E)
                        else
                            Color(0xFF1565C0),

                    fontSize = 12.sp,

                    fontWeight =
                        FontWeight.Medium
                )
            }
        }
    }
}


// =====================================================
// INFO ROW
// =====================================================

@Composable
private fun BookingInfoRow(
    icon: @Composable () -> Unit,
    title: String,
    value: String
) {

    Row(
        modifier =
            Modifier.fillMaxWidth(),

        verticalAlignment =
            Alignment.CenterVertically
    ) {

        Surface(
            modifier =
                Modifier.size(38.dp),

            shape =
                RoundedCornerShape(11.dp),

            color =
                Color(0xFFF1F5F9)
        ) {

            Box(
                contentAlignment =
                    Alignment.Center
            ) {

                icon()
            }
        }

        Spacer(
            modifier =
                Modifier.width(12.dp)
        )

        Column {

            Text(
                text = title,

                fontSize = 11.sp,

                color =
                    Color.Gray
            )

            Spacer(
                modifier =
                    Modifier.height(2.dp)
            )

            Text(
                text = value,

                fontSize = 14.sp,

                color =
                    Color.Black,

                fontWeight =
                    FontWeight.SemiBold
            )
        }
    }
}