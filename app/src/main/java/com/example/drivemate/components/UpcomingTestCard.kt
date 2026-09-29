package com.example.drivemate.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun UpcomingTestCard(
    navController: NavController
) {

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        )
    ) {

        Column(
            modifier = Modifier.padding(20.dp)
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement =
                    Arrangement.SpaceBetween,
                verticalAlignment =
                    Alignment.CenterVertically
            ) {

                Text(
                    text = "Upcoming Driving Test",
                    fontSize = 21.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1565C0)
                )

                Text(
                    text = "🚦",
                    fontSize = 32.sp
                )
            }

            Spacer(
                modifier = Modifier.height(18.dp)
            )

            Text(
                text = "Your next test is scheduled",
                color = Color.Gray,
                fontSize = 14.sp
            )

            Spacer(
                modifier = Modifier.height(12.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment =
                    Alignment.CenterVertically
            ) {

                Surface(
                    color = Color(0xFFE3F2FD),
                    shape = RoundedCornerShape(16.dp)
                ) {

                    Text(
                        text = "📅",
                        modifier = Modifier.padding(
                            horizontal = 14.dp,
                            vertical = 12.dp
                        ),
                        fontSize = 28.sp
                    )
                }

                Spacer(
                    modifier = Modifier.width(14.dp)
                )

                Column {

                    Text(
                        text = "15 August 2026",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )

                    Text(
                        text = "10:30 AM",
                        color = Color.Gray,
                        fontSize = 14.sp
                    )
                }
            }

            Spacer(
                modifier = Modifier.height(16.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    text = "📍 RTO Test Ground",
                    color = Color.Gray,
                    fontSize = 14.sp,
                    modifier = Modifier.weight(1f)
                )

                Surface(
                    color = Color(0xFFE8F5E9),
                    shape = RoundedCornerShape(50.dp)
                ) {

                    Text(
                        text = "Confirmed",
                        modifier = Modifier.padding(
                            horizontal = 12.dp,
                            vertical = 6.dp
                        ),
                        color = Color(0xFF2E7D32),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(
                modifier = Modifier.height(18.dp)
            )

            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                onClick = {
                    navController.navigate(
                        "myTestBookings"
                    )
                },
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF1565C0)
                )
            ) {

                Text(
                    text = "View Test Details",
                    color = Color.White,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}