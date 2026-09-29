package com.example.drivemate.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavController
import coil.compose.AsyncImage

@Composable
fun InstructorCard(
    navController: NavController
) {

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(28.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp
        )
    ) {

        Column(
            modifier = Modifier.padding(20.dp)
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    text = "Nearby Instructor",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1565C0)
                )

                Surface(
                    color = Color(0xFFEAF4FF),
                    shape = RoundedCornerShape(50.dp)
                ) {

                    Text(
                        text = "📍 2 km",
                        modifier = Modifier.padding(
                            horizontal = 12.dp,
                            vertical = 7.dp
                        ),
                        color = Color(0xFF1565C0),
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(
                modifier = Modifier.height(20.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {

                AsyncImage(
                    model = "https://i.pravatar.cc/300?img=12",
                    contentDescription = "Instructor",
                    modifier = Modifier
                        .size(82.dp)
                        .clip(CircleShape)
                )

                Spacer(
                    modifier = Modifier.width(16.dp)
                )

                Column(
                    modifier = Modifier.weight(1f)
                ) {

                    Text(
                        text = "Rajesh Patel",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )

                    Spacer(
                        modifier = Modifier.height(4.dp)
                    )

                    Text(
                        text = "Professional Instructor",
                        fontSize = 14.sp,
                        color = Color.Gray
                    )

                    Spacer(
                        modifier = Modifier.height(10.dp)
                    )

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = null,
                            tint = Color(0xFFFFB300),
                            modifier = Modifier.size(18.dp)
                        )

                        Spacer(
                            modifier = Modifier.width(4.dp)
                        )

                        Text(
                            text = "4.9",
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF1565C0)
                        )

                        Spacer(
                            modifier = Modifier.width(12.dp)
                        )

                        Icon(
                            imageVector = Icons.Default.Groups,
                            contentDescription = null,
                            tint = Color.Gray,
                            modifier = Modifier.size(18.dp)
                        )

                        Spacer(
                            modifier = Modifier.width(4.dp)
                        )

                        Text(
                            text = "125+ Students",
                            color = Color.Gray,
                            fontSize = 12.sp
                        )
                    }
                }
            }

            Spacer(
                modifier = Modifier.height(16.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                OutlinedButton(
                    modifier = Modifier
                        .weight(1f)
                        .height(50.dp),
                    onClick = {
                        // WhatsApp functionality later
                    },
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = Color(0xFF25D366)
                    )
                ) {

                    Text(
                        text = "💬 WhatsApp",
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp
                    )
                }

                Button(
                    modifier = Modifier
                        .weight(1f)
                        .height(50.dp),
                    onClick = {
                        // Call functionality later
                    },
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF1565C0)
                    )
                ) {

                    Icon(
                        imageVector = Icons.Default.Call,
                        contentDescription = "Call Instructor",
                        tint = Color.White,
                        modifier = Modifier.size(18.dp)
                    )

                    Spacer(
                        modifier = Modifier.width(6.dp)
                    )

                    Text(
                        text = "Call",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp
                    )
                }
            }

            Spacer(
                modifier = Modifier.height(12.dp)
            )

            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                onClick = {
                    navController.navigate("bookLesson")
                },
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF1565C0)
                )
            ) {

                Text(
                    text = "Book Instructor",
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}