package com.example.drivemate.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DrivingLicenceScreen(
    navController: NavController
) {

    var llNumber by remember { mutableStateOf("") }
    var dateOfBirth by remember { mutableStateOf("") }

    var carSelected by remember { mutableStateOf(false) }
    var bikeSelected by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Driving Licence",
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            navController.popBackStack()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF171719)
                )
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF5F8FF))
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(20.dp)
        ) {

            Text(
                text = "Apply for Driving Licence",
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "Enter your Learner Licence details",
                color = Color.DarkGray
            )

            Spacer(modifier = Modifier.height(24.dp))

            // LEARNER LICENCE NUMBER

            OutlinedTextField(
                value = llNumber,
                onValueChange = {
                    llNumber = it.uppercase()
                },
                label = {
                    Text("Learner Licence Number")
                },
                placeholder = {
                    Text("Enter LL Number")
                },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black,
                    focusedLabelColor = Color(0xFF1565C0),
                    unfocusedLabelColor = Color.DarkGray,
                    focusedBorderColor = Color(0xFF1565C0),
                    unfocusedBorderColor = Color.Gray,
                    cursorColor = Color(0xFF1565C0)
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            // DATE OF BIRTH
            // 27072006 -> 27/07/2006

            OutlinedTextField(
                value = dateOfBirth,

                onValueChange = { input ->

                    val digits = input
                        .filter { it.isDigit() }
                        .take(8)

                    dateOfBirth = buildString {

                        digits.forEachIndexed { index, char ->

                            append(char)

                            if (index == 1 && digits.length > 2) {
                                append("/")
                            }

                            if (index == 3 && digits.length > 4) {
                                append("/")
                            }
                        }
                    }
                },

                label = {
                    Text("Date of Birth")
                },

                placeholder = {
                    Text(
                        text = "DD/MM/YYYY",
                        color = Color.Gray
                    )
                },

                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                ),

                modifier = Modifier.fillMaxWidth(),
                singleLine = true,

                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black,
                    focusedLabelColor = Color(0xFF1565C0),
                    unfocusedLabelColor = Color.DarkGray,
                    focusedBorderColor = Color(0xFF1565C0),
                    unfocusedBorderColor = Color.Gray,
                    cursorColor = Color(0xFF1565C0)
                )
            )

            Spacer(modifier = Modifier.height(25.dp))

            // VEHICLE CLASS

            Text(
                text = "Vehicle Class",
                fontSize = 19.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Select the class you want to apply for",
                fontSize = 13.sp,
                color = Color.DarkGray
            )

            Spacer(modifier = Modifier.height(8.dp))

            // CAR

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Checkbox(
                    checked = carSelected,
                    onCheckedChange = {
                        carSelected = it
                    }
                )

                Text(
                    text = "🚗 Car (LMV)",
                    color = Color.Black,
                    fontSize = 16.sp
                )
            }

            // BIKE

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Checkbox(
                    checked = bikeSelected,
                    onCheckedChange = {
                        bikeSelected = it
                    }
                )

                Text(
                    text = "🏍️ Bike (MCWG)",
                    color = Color.Black,
                    fontSize = 16.sp
                )
            }

            Spacer(modifier = Modifier.height(22.dp))

            // INFORMATION

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
                        text = "Before you apply",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "✓ Keep your valid Learner Licence ready",
                        color = Color.DarkGray
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = "✓ Make sure your details match your LL",
                        color = Color.DarkGray
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = "✓ Select only the vehicle class applicable to your LL",
                        color = Color.DarkGray
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = "✓ Driving test and other requirements are handled by the applicable licensing authority",
                        color = Color.DarkGray
                    )
                }
            }

            Spacer(modifier = Modifier.height(25.dp))

            // CONTINUE

            Button(
                onClick = {

                    navController.navigate("dlDocuments")

                },

                enabled =
                    llNumber.isNotBlank() &&
                            dateOfBirth.length == 10 &&
                            (carSelected || bikeSelected),

                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),

                shape = RoundedCornerShape(16.dp),

                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF1565C0)
                )
            ) {

                Text(
                    text = "Continue",
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.height(25.dp))
        }
    }
}