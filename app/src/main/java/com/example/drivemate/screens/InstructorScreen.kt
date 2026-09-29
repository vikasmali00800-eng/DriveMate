package com.example.drivemate.screens

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InstructorScreen(
    navController: NavController,
    date: String,
    time: String,
    vehicle: String,
    pickup: String
) {

    Scaffold(
        containerColor = Color(0xFFF5F8FF),

        topBar = {

            TopAppBar(

                title = {

                    Column {

                        Text(
                            text = "Select Instructor",
                            color = Color.Black,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Text(
                            text = "Choose your preferred instructor",
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

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(
                    rememberScrollState()
                )
                .padding(20.dp)
        ) {

            // ==========================================
            // BOOKING SUMMARY
            // ==========================================

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFFE3F2FD)
                )
            ) {

                Column(
                    modifier = Modifier.padding(18.dp)
                ) {

                    Text(
                        text = "Lesson Details",
                        color = Color(0xFF1565C0),
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(
                        modifier = Modifier.height(10.dp)
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement =
                            Arrangement.SpaceBetween
                    ) {

                        Text(
                            text = "📅 $date",
                            color = Color.Black,
                            fontSize = 13.sp
                        )

                        Text(
                            text = "🕐 $time",
                            color = Color.Black,
                            fontSize = 13.sp
                        )
                    }

                    Spacer(
                        modifier = Modifier.height(7.dp)
                    )

                    Text(
                        text = "🚗 $vehicle  •  📍 $pickup",
                        color = Color.Gray,
                        fontSize = 13.sp
                    )
                }
            }

            Spacer(
                modifier = Modifier.height(25.dp)
            )

            Text(
                text = "Available Instructors",
                color = Color.Black,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "Select an instructor to continue",
                color = Color.Gray,
                fontSize = 13.sp
            )

            Spacer(
                modifier = Modifier.height(16.dp)
            )

            // ==========================================
            // RAHUL
            // ==========================================

            InstructorSelectionCard(
                name = "Rahul Sharma",
                rating = "4.9",
                experience = "10 Years Experience",
                distance = "2.5 km away",
                fee = "₹499 / hr",
                initials = "RS",
                isAvailable = true
            ) {

                val instructor =
                    Uri.encode("Rahul Sharma")

                val encodedDate =
                    Uri.encode(date)

                val encodedTime =
                    Uri.encode(time)

                val encodedVehicle =
                    Uri.encode(vehicle)

                val encodedPickup =
                    Uri.encode(pickup)

                navController.navigate(
                    "paymentCheckout/$instructor/499/" +
                            "$encodedDate/$encodedTime/" +
                            "$encodedVehicle/$encodedPickup"
                )
            }

            Spacer(
                modifier = Modifier.height(16.dp)
            )

            // ==========================================
            // SONIA
            // ==========================================

            InstructorSelectionCard(
                name = "Sonia Verma",
                rating = "4.7",
                experience = "7 Years Experience",
                distance = "3.1 km away",
                fee = "₹450 / hr",
                initials = "SV",
                isAvailable = true
            ) {

                val instructor =
                    Uri.encode("Sonia Verma")

                val encodedDate =
                    Uri.encode(date)

                val encodedTime =
                    Uri.encode(time)

                val encodedVehicle =
                    Uri.encode(vehicle)

                val encodedPickup =
                    Uri.encode(pickup)

                navController.navigate(
                    "paymentCheckout/$instructor/450/" +
                            "$encodedDate/$encodedTime/" +
                            "$encodedVehicle/$encodedPickup"
                )
            }

            Spacer(
                modifier = Modifier.height(20.dp)
            )
        }
    }
}


// ======================================================
// INSTRUCTOR SELECTION CARD
// ======================================================

@Composable
private fun InstructorSelectionCard(
    name: String,
    rating: String,
    experience: String,
    distance: String,
    fee: String,
    initials: String,
    isAvailable: Boolean,
    onSelect: () -> Unit
) {

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 5.dp
        )
    ) {

        Column(
            modifier = Modifier.padding(18.dp)
        ) {

            // ==========================================
            // PROFILE
            // ==========================================

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment =
                    Alignment.CenterVertically
            ) {

                Surface(
                    modifier = Modifier.size(68.dp),
                    shape = CircleShape,
                    color = Color(0xFFE3F2FD)
                ) {

                    Box(
                        contentAlignment =
                            Alignment.Center
                    ) {

                        Text(
                            text = initials,
                            color = Color(0xFF1565C0),
                            fontSize = 21.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                Spacer(
                    modifier = Modifier.width(14.dp)
                )

                Column(
                    modifier = Modifier.weight(1f)
                ) {

                    Text(
                        text = name,
                        color = Color.Black,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(
                        modifier = Modifier.height(3.dp)
                    )

                    Text(
                        text = "Professional Driving Instructor",
                        color = Color.Gray,
                        fontSize = 12.sp
                    )

                    Spacer(
                        modifier = Modifier.height(7.dp)
                    )

                    Row(
                        verticalAlignment =
                            Alignment.CenterVertically
                    ) {

                        Icon(
                            imageVector =
                                Icons.Default.Star,
                            contentDescription = null,
                            tint = Color(0xFFFFB300),
                            modifier = Modifier.size(17.dp)
                        )

                        Spacer(
                            modifier = Modifier.width(4.dp)
                        )

                        Text(
                            text = rating,
                            color = Color.Black,
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp
                        )

                        Spacer(
                            modifier = Modifier.width(12.dp)
                        )

                        Icon(
                            imageVector =
                                Icons.Default.LocationOn,
                            contentDescription = null,
                            tint = Color.Gray,
                            modifier = Modifier.size(16.dp)
                        )

                        Spacer(
                            modifier = Modifier.width(3.dp)
                        )

                        Text(
                            text = distance,
                            color = Color.Gray,
                            fontSize = 12.sp
                        )
                    }
                }

                Surface(
                    color =
                        if (isAvailable)
                            Color(0xFFE8F5E9)
                        else
                            Color(0xFFFFEBEE),
                    shape =
                        RoundedCornerShape(50.dp)
                ) {

                    Text(
                        text =
                            if (isAvailable)
                                "Available"
                            else
                                "Busy",
                        modifier = Modifier.padding(
                            horizontal = 9.dp,
                            vertical = 6.dp
                        ),
                        color =
                            if (isAvailable)
                                Color(0xFF2E7D32)
                            else
                                Color(0xFFC62828),
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(
                modifier = Modifier.height(18.dp)
            )

            HorizontalDivider(
                color = Color(0xFFE5E7EB)
            )

            Spacer(
                modifier = Modifier.height(14.dp)
            )

            // ==========================================
            // DETAILS
            // ==========================================

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement =
                    Arrangement.SpaceBetween,
                verticalAlignment =
                    Alignment.CenterVertically
            ) {

                Column {

                    Text(
                        text = "Experience",
                        color = Color.Gray,
                        fontSize = 11.sp
                    )

                    Spacer(
                        modifier = Modifier.height(3.dp)
                    )

                    Text(
                        text = experience,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp
                    )
                }

                Column(
                    horizontalAlignment =
                        Alignment.End
                ) {

                    Text(
                        text = "Lesson Fee",
                        color = Color.Gray,
                        fontSize = 11.sp
                    )

                    Spacer(
                        modifier = Modifier.height(3.dp)
                    )

                    Text(
                        text = fee,
                        color = Color(0xFF1565C0),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(
                modifier = Modifier.height(16.dp)
            )

            // ==========================================
            // SELECT BUTTON
            // ==========================================

            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),

                onClick = onSelect,

                shape = RoundedCornerShape(14.dp),

                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF1565C0),
                    contentColor = Color.White
                )
            ) {

                Text(
                    text = "Select Instructor  →",
                    color = Color.White,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}