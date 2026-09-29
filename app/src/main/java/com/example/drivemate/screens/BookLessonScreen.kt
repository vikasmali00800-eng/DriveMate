package com.example.drivemate.screens

import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
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

    var date by remember {
        mutableStateOf("28 July 2026")
    }

    var time by remember {
        mutableStateOf("10:00 AM")
    }

    var pickup by remember {
        mutableStateOf("")
    }

    var vehicle by remember {
        mutableStateOf("Manual")
    }

    Scaffold(
        containerColor = Color(0xFFF5F8FF),

        topBar = {

            TopAppBar(
                title = {

                    Column {

                        Text(
                            text = "Book Driving Lesson",
                            color = Color.Black,
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        )

                        Text(
                            text = "Choose your lesson details",
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

            // ================================
            // HEADER
            // ================================

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFF1565C0)
                ),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 6.dp
                )
            ) {

                Column(
                    modifier = Modifier.padding(22.dp)
                ) {

                    Text(
                        text = "🚗 Let's get you driving!",
                        color = Color.White,
                        fontSize = 21.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(
                        modifier = Modifier.height(6.dp)
                    )

                    Text(
                        text =
                            "Select your preferred date, time and pickup location.",
                        color = Color.White.copy(alpha = 0.9f),
                        fontSize = 13.sp
                    )
                }
            }

            Spacer(
                modifier = Modifier.height(24.dp)
            )

            // ================================
            // LESSON SCHEDULE
            // ================================

            Text(
                text = "Lesson Schedule",
                color = Color.Black,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(
                modifier = Modifier.height(12.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement =
                    Arrangement.spacedBy(12.dp)
            ) {

                OutlinedTextField(
                    value = date,
                    onValueChange = {
                        date = it
                    },
                    modifier = Modifier.weight(1f),
                    label = {
                        Text(
                            text = "Date",
                            color = Color.Gray
                        )
                    },
                    leadingIcon = {

                        Icon(
                            imageVector =
                                Icons.Default.CalendarMonth,
                            contentDescription = null,
                            tint = Color(0xFF1565C0)
                        )
                    },
                    singleLine = true,
                    shape = RoundedCornerShape(16.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = Color.Black,
                        unfocusedTextColor = Color.Black,
                        focusedBorderColor =
                            Color(0xFF1565C0),
                        unfocusedBorderColor =
                            Color(0xFFB0BEC5),
                        focusedLabelColor =
                            Color(0xFF1565C0),
                        unfocusedLabelColor =
                            Color.Gray
                    )
                )

                OutlinedTextField(
                    value = time,
                    onValueChange = {
                        time = it
                    },
                    modifier = Modifier.weight(1f),
                    label = {
                        Text(
                            text = "Time",
                            color = Color.Gray
                        )
                    },
                    leadingIcon = {

                        Icon(
                            imageVector =
                                Icons.Default.AccessTime,
                            contentDescription = null,
                            tint = Color(0xFF1565C0)
                        )
                    },
                    singleLine = true,
                    shape = RoundedCornerShape(16.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = Color.Black,
                        unfocusedTextColor = Color.Black,
                        focusedBorderColor =
                            Color(0xFF1565C0),
                        unfocusedBorderColor =
                            Color(0xFFB0BEC5),
                        focusedLabelColor =
                            Color(0xFF1565C0),
                        unfocusedLabelColor =
                            Color.Gray
                    )
                )
            }

            Spacer(
                modifier = Modifier.height(24.dp)
            )

            // ================================
            // VEHICLE
            // ================================

            Text(
                text = "Vehicle Type",
                color = Color.Black,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(
                modifier = Modifier.height(12.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement =
                    Arrangement.spacedBy(12.dp)
            ) {

                VehicleOption(
                    title = "Manual",
                    icon = "🚗",
                    selected = vehicle == "Manual",
                    modifier = Modifier.weight(1f),
                    onClick = {
                        vehicle = "Manual"
                    }
                )

                VehicleOption(
                    title = "Automatic",
                    icon = "🚘",
                    selected = vehicle == "Automatic",
                    modifier = Modifier.weight(1f),
                    onClick = {
                        vehicle = "Automatic"
                    }
                )
            }

            Spacer(
                modifier = Modifier.height(24.dp)
            )

            // ================================
            // INSTRUCTOR
            // ================================

            Text(
                text = "Instructor",
                color = Color.Black,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(
                modifier = Modifier.height(12.dp)
            )

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                ),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 2.dp
                )
            ) {

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment =
                        Alignment.CenterVertically
                ) {

                    Surface(
                        modifier = Modifier.size(52.dp),
                        shape = RoundedCornerShape(16.dp),
                        color = Color(0xFFE3F2FD)
                    ) {

                        Box(
                            contentAlignment =
                                Alignment.Center
                        ) {

                            Icon(
                                imageVector =
                                    Icons.Default.Person,
                                contentDescription = null,
                                tint =
                                    Color(0xFF1565C0),
                                modifier =
                                    Modifier.size(28.dp)
                            )
                        }
                    }

                    Spacer(
                        modifier = Modifier.width(14.dp)
                    )

                    Column(
                        modifier =
                            Modifier.weight(1f)
                    ) {

                        Text(
                            text = "Choose Instructor",
                            color = Color.Black,
                            fontWeight =
                                FontWeight.Bold,
                            fontSize = 16.sp
                        )

                        Text(
                            text =
                                "Select an instructor on next screen",
                            color = Color.Gray,
                            fontSize = 12.sp
                        )
                    }

                    Text(
                        text = "›",
                        color =
                            Color(0xFF1565C0),
                        fontSize = 30.sp,
                        fontWeight =
                            FontWeight.Bold
                    )
                }
            }

            Spacer(
                modifier = Modifier.height(24.dp)
            )

            // ================================
            // PICKUP LOCATION
            // ================================

            Text(
                text = "Pickup Location",
                color = Color.Black,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(
                modifier = Modifier.height(12.dp)
            )

            OutlinedTextField(
                value = pickup,
                onValueChange = {
                    pickup = it
                },
                modifier =
                    Modifier.fillMaxWidth(),
                label = {
                    Text(
                        text = "Enter pickup location",
                        color = Color.Gray
                    )
                },
                placeholder = {
                    Text(
                        text =
                            "e.g. Hostel, Home, College...",
                        color = Color.Gray
                    )
                },
                leadingIcon = {

                    Icon(
                        imageVector =
                            Icons.Default.LocationOn,
                        contentDescription = null,
                        tint =
                            Color(0xFF1565C0)
                    )
                },
                singleLine = true,
                shape = RoundedCornerShape(16.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black,
                    focusedBorderColor =
                        Color(0xFF1565C0),
                    unfocusedBorderColor =
                        Color(0xFFB0BEC5),
                    focusedLabelColor =
                        Color(0xFF1565C0),
                    unfocusedLabelColor =
                        Color.Gray
                )
            )

            Spacer(
                modifier = Modifier.height(24.dp)
            )

            // ================================
            // PRICE SUMMARY
            // ================================

            Card(
                modifier =
                    Modifier.fillMaxWidth(),
                shape =
                    RoundedCornerShape(22.dp),
                colors =
                    CardDefaults.cardColors(
                        containerColor =
                            Color.White
                    ),
                elevation =
                    CardDefaults.cardElevation(
                        defaultElevation = 3.dp
                    )
            ) {

                Column(
                    modifier =
                        Modifier.padding(20.dp)
                ) {

                    Text(
                        text = "Price Summary",
                        color = Color.Black,
                        fontSize = 18.sp,
                        fontWeight =
                            FontWeight.Bold
                    )

                    Spacer(
                        modifier =
                            Modifier.height(14.dp)
                    )

                    Row(
                        modifier =
                            Modifier.fillMaxWidth(),
                        horizontalArrangement =
                            Arrangement.SpaceBetween
                    ) {

                        Text(
                            text = "Driving Lesson",
                            color = Color.Gray
                        )

                        Text(
                            text = "₹450",
                            color = Color.Black,
                            fontWeight =
                                FontWeight.Bold
                        )
                    }

                    Spacer(
                        modifier =
                            Modifier.height(10.dp)
                    )

                    HorizontalDivider()

                    Spacer(
                        modifier =
                            Modifier.height(10.dp)
                    )

                    Row(
                        modifier =
                            Modifier.fillMaxWidth(),
                        horizontalArrangement =
                            Arrangement.SpaceBetween
                    ) {

                        Text(
                            text = "Total",
                            color = Color.Black,
                            fontSize = 17.sp,
                            fontWeight =
                                FontWeight.Bold
                        )

                        Text(
                            text = "₹450",
                            color =
                                Color(0xFF1565C0),
                            fontSize = 22.sp,
                            fontWeight =
                                FontWeight.Bold
                        )
                    }
                }
            }

            Spacer(
                modifier =
                    Modifier.height(28.dp)
            )

            // ================================
            // CHOOSE INSTRUCTOR BUTTON
            // ================================

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

                        val encodedDate =
                            Uri.encode(date)

                        val encodedTime =
                            Uri.encode(time)

                        val encodedVehicle =
                            Uri.encode(vehicle)

                        val encodedPickup =
                            Uri.encode(pickup)

                        navController.navigate(
                            "instructor/$encodedDate/$encodedTime/$encodedVehicle/$encodedPickup"
                        )
                    }
                },

                modifier = Modifier
                    .fillMaxWidth()
                    .height(58.dp),

                shape =
                    RoundedCornerShape(17.dp),

                colors =
                    ButtonDefaults.buttonColors(
                        containerColor =
                            Color(0xFF1565C0),
                        contentColor =
                            Color.White
                    )
            ) {

                Text(
                    text = "Choose Instructor  →",
                    color = Color.White,
                    fontSize = 17.sp,
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
}

// =============================================
// VEHICLE OPTION
// =============================================

@Composable
private fun VehicleOption(
    title: String,
    icon: String,
    selected: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {

    Card(
        modifier = modifier
            .height(90.dp)
            .clickable {
                onClick()
            },
        shape =
            RoundedCornerShape(18.dp),
        colors =
            CardDefaults.cardColors(
                containerColor =
                    if (selected)
                        Color(0xFFE3F2FD)
                    else
                        Color.White
            ),
        border =
            if (selected)
                BorderStroke(
                    2.dp,
                    Color(0xFF1565C0)
                )
            else
                null,
        elevation =
            CardDefaults.cardElevation(
                defaultElevation =
                    if (selected)
                        4.dp
                    else
                        1.dp
            )
    ) {

        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(14.dp),
            verticalAlignment =
                Alignment.CenterVertically
        ) {

            Text(
                text = icon,
                fontSize = 28.sp
            )

            Spacer(
                modifier =
                    Modifier.width(10.dp)
            )

            Column {

                Text(
                    text = title,
                    color =
                        if (selected)
                            Color(0xFF1565C0)
                        else
                            Color.Black,
                    fontWeight =
                        FontWeight.Bold
                )

                Text(
                    text =
                        if (selected)
                            "Selected"
                        else
                            "Tap to select",
                    color = Color.Gray,
                    fontSize = 11.sp
                )
            }
        }
    }
}