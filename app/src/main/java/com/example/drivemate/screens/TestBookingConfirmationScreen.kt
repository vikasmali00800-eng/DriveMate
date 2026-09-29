package com.example.drivemate.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun TestBookingConfirmationScreen(
    navController: NavController,
    rto: String,
    testDay: String,
    testDate: String,
    testTime: String,
    vehicle: String
) {

    Scaffold(
        containerColor = Color(0xFFF5F8FF)
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF5F8FF))
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(
                    start = 20.dp,
                    end = 20.dp,
                    top = 28.dp,
                    bottom = 35.dp
                ),

            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // =========================
            // SUCCESS ICON
            // =========================

            Surface(
                modifier = Modifier.size(90.dp),
                shape = RoundedCornerShape(45.dp),
                color = Color(0xFFE8F5E9)
            ) {

                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {

                    Text(
                        text = "✓",
                        fontSize = 50.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF2E7D32)
                    )
                }
            }

            Spacer(
                modifier = Modifier.height(20.dp)
            )

            // =========================
            // TITLE
            // =========================

            Text(
                text = "Test Preference Saved!",
                fontSize = 27.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                textAlign = TextAlign.Center
            )

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            Text(
                text = "Your driving test preference has been saved successfully.",
                fontSize = 14.sp,
                color = Color.DarkGray,
                textAlign = TextAlign.Center
            )

            Spacer(
                modifier = Modifier.height(28.dp)
            )

            // =========================
            // TEST DETAILS
            // =========================

            Card(
                modifier = Modifier.fillMaxWidth(),

                shape = RoundedCornerShape(20.dp),

                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                )
            ) {

                Column(
                    modifier = Modifier.padding(20.dp)
                ) {

                    Text(
                        text = "Test Details",
                        fontSize = 19.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )

                    Spacer(
                        modifier = Modifier.height(18.dp)
                    )

                    ConfirmationRow(
                        label = "RTO",
                        value = rto
                    )

                    HorizontalDivider()

                    ConfirmationRow(
                        label = "Test Day",
                        value = testDay
                    )

                    HorizontalDivider()

                    ConfirmationRow(
                        label = "Date",
                        value = testDate
                    )

                    HorizontalDivider()

                    ConfirmationRow(
                        label = "Time",
                        value = testTime
                    )

                    HorizontalDivider()

                    ConfirmationRow(
                        label = "Vehicle",
                        value = vehicle
                    )

                    HorizontalDivider()

                    ConfirmationRow(
                        label = "Status",
                        value = "Preferred"
                    )
                }
            }

            Spacer(
                modifier = Modifier.height(18.dp)
            )

            // =========================
            // IMPORTANT NOTICE
            // =========================

            Card(
                modifier = Modifier.fillMaxWidth(),

                shape = RoundedCornerShape(16.dp),

                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFFFFF8E1)
                )
            ) {

                Text(
                    text = "This is your saved DriveMate test preference. It is not a confirmed RTO appointment.",

                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),

                    color = Color(0xFF6D4C00),

                    fontSize = 13.sp,

                    textAlign = TextAlign.Center
                )
            }

            Spacer(
                modifier = Modifier.height(25.dp)
            )

            // =========================
            // VIEW MY TEST BOOKINGS
            // =========================

            Button(
                onClick = {

                    navController.navigate(
                        "myTestBookings"
                    ) {

                        popUpTo(
                            "drivingTestSlot"
                        ) {
                            inclusive = true
                        }

                        launchSingleTop = true
                    }
                },

                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),

                shape = RoundedCornerShape(16.dp),

                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF1565C0),
                    contentColor = Color.White
                )
            ) {

                Text(
                    text = "View My Test Bookings",
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(
                modifier = Modifier.height(10.dp)
            )

            // =========================
            // BACK HOME
            // =========================

            TextButton(
                onClick = {

                    navController.navigate(
                        "home"
                    ) {

                        popUpTo("home") {
                            inclusive = false
                        }

                        launchSingleTop = true
                    }
                }
            ) {

                Text(
                    text = "Back to Home",
                    color = Color(0xFF1565C0),
                    fontWeight = FontWeight.Bold
                )
            }

            // Extra bottom space
            Spacer(
                modifier = Modifier.height(20.dp)
            )
        }
    }
}


// =========================
// DETAIL ROW
// =========================

@Composable
private fun ConfirmationRow(
    label: String,
    value: String
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),

        verticalAlignment = Alignment.CenterVertically
    ) {

        Text(
            text = label,

            modifier = Modifier.weight(1f),

            fontSize = 14.sp,

            color = Color.DarkGray
        )

        Spacer(
            modifier = Modifier.width(12.dp)
        )

        Text(
            text = value.ifBlank {
                "Not available"
            },

            modifier = Modifier.weight(1.5f),

            fontSize = 14.sp,

            fontWeight = FontWeight.Bold,

            color = Color.Black,

            textAlign = TextAlign.End
        )
    }
}