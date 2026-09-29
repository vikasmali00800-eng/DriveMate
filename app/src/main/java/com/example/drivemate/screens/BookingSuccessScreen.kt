package com.example.drivemate.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.CalendarMonth
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
fun BookingSuccessScreen(
    navController: NavController
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F8FF))
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(
            modifier = Modifier.height(70.dp)
        )

        // =========================================
        // SUCCESS ICON
        // =========================================

        Surface(
            modifier = Modifier.size(105.dp),
            shape = RoundedCornerShape(52.dp),
            color = Color(0xFFE8F5E9)
        ) {

            Box(
                contentAlignment = Alignment.Center
            ) {

                Icon(
                    imageVector =
                        Icons.Default.CheckCircle,
                    contentDescription =
                        "Booking Confirmed",
                    modifier =
                        Modifier.size(78.dp),
                    tint =
                        Color(0xFF2E7D32)
                )
            }
        }

        Spacer(
            modifier = Modifier.height(25.dp)
        )

        Text(
            text = "Booking Confirmed!",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            textAlign = TextAlign.Center
        )

        Spacer(
            modifier = Modifier.height(10.dp)
        )

        Text(
            text =
                "Your driving lesson has been successfully booked.",
            fontSize = 15.sp,
            color = Color.Gray,
            textAlign = TextAlign.Center,
            lineHeight = 23.sp
        )

        Spacer(
            modifier = Modifier.height(25.dp)
        )

        // =========================================
        // SUCCESS CARD
        // =========================================

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 4.dp
            )
        ) {

            Column(
                modifier = Modifier.padding(20.dp)
            ) {

                Text(
                    text = "What's next?",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )

                Spacer(
                    modifier =
                        Modifier.height(14.dp)
                )

                Row(
                    verticalAlignment =
                        Alignment.CenterVertically
                ) {

                    Surface(
                        modifier =
                            Modifier.size(45.dp),
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
                                contentDescription = null,
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
                                "Lesson Reminder",
                            fontWeight =
                                FontWeight.Bold,
                            color =
                                Color.Black
                        )

                        Text(
                            text =
                                "You'll receive a reminder before your lesson.",
                            fontSize = 12.sp,
                            color =
                                Color.Gray
                        )
                    }
                }

                Spacer(
                    modifier =
                        Modifier.height(16.dp)
                )

                HorizontalDivider()

                Spacer(
                    modifier =
                        Modifier.height(16.dp)
                )

                Text(
                    text =
                        "🔔 DriveMate has scheduled your lesson reminder.",
                    color =
                        Color(0xFF2E7D32),
                    fontSize = 13.sp
                )
            }
        }

        Spacer(
            modifier =
                Modifier.height(30.dp)
        )

        // =========================================
        // VIEW BOOKINGS
        // =========================================

        Button(
            onClick = {

                navController.navigate(
                    "myBookings"
                )
            },

            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),

            shape =
                RoundedCornerShape(16.dp),

            colors =
                ButtonDefaults.buttonColors(
                    containerColor =
                        Color(0xFF1565C0),
                    contentColor =
                        Color.White
                )
        ) {

            Icon(
                imageVector =
                    Icons.Default.CalendarMonth,
                contentDescription = null,
                tint = Color.White
            )

            Spacer(
                modifier =
                    Modifier.width(8.dp)
            )

            Text(
                text = "View My Bookings",
                color = Color.White,
                fontSize = 16.sp,
                fontWeight =
                    FontWeight.Bold
            )
        }

        Spacer(
            modifier =
                Modifier.height(12.dp)
        )

        // =========================================
        // HOME
        // =========================================

        OutlinedButton(
            onClick = {

                navController.navigate(
                    "home"
                ) {

                    popUpTo("home") {
                        inclusive = true
                    }
                }
            },

            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),

            shape =
                RoundedCornerShape(16.dp),

            colors =
                ButtonDefaults.outlinedButtonColors(
                    contentColor =
                        Color(0xFF1565C0)
                )
        ) {

            Icon(
                imageVector =
                    Icons.Default.Home,
                contentDescription = null,
                tint =
                    Color(0xFF1565C0)
            )

            Spacer(
                modifier =
                    Modifier.width(8.dp)
            )

            Text(
                text = "Back to Home",
                color =
                    Color(0xFF1565C0),
                fontSize = 16.sp,
                fontWeight =
                    FontWeight.Bold
            )
        }

        Spacer(
            modifier =
                Modifier.height(20.dp)
        )
    }
}