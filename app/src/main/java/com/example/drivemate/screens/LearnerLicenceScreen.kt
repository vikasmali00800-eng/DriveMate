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
fun LearnerLicenceScreen(
    navController: NavController
) {

    var fullName by remember { mutableStateOf("") }
    var dateOfBirth by remember { mutableStateOf("") }
    var mobileNumber by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }

    var carSelected by remember { mutableStateOf(false) }
    var bikeSelected by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Learner Licence",
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
                text = "Apply for Learner Licence",
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "Enter your details to continue",
                color = Color.DarkGray
            )

            Spacer(modifier = Modifier.height(24.dp))

            // FULL NAME
            OutlinedTextField(
                value = fullName,
                onValueChange = {
                    fullName = it
                },
                label = {
                    Text("Full Name")
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
            // Example: 27072006 -> 27/07/2006
            OutlinedTextField(
                value = dateOfBirth,

                onValueChange = { input ->

                    val digits = input
                        .filter { it.isDigit() }
                        .take(8)

                    val formatted = buildString {

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

                    dateOfBirth = formatted
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

            Spacer(modifier = Modifier.height(16.dp))

            // MOBILE NUMBER
            OutlinedTextField(
                value = mobileNumber,

                onValueChange = { value ->

                    if (
                        value.length <= 10 &&
                        value.all { it.isDigit() }
                    ) {
                        mobileNumber = value
                    }
                },

                label = {
                    Text("Mobile Number")
                },

                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Phone
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

            Spacer(modifier = Modifier.height(16.dp))

            // ADDRESS
            OutlinedTextField(
                value = address,

                onValueChange = {
                    address = it
                },

                label = {
                    Text("Address")
                },

                modifier = Modifier.fillMaxWidth(),
                minLines = 3,

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

            Text(
                text = "You can select one or both",
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
                    fontSize = 16.sp,
                    color = Color.Black
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
                    fontSize = 16.sp,
                    color = Color.Black
                )
            }

            Spacer(modifier = Modifier.height(22.dp))

            // DOCUMENTS
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
                        text = "Documents Required",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "✓ Proof of age",
                        color = Color.DarkGray
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = "✓ Proof of address",
                        color = Color.DarkGray
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = "✓ Passport-size photograph",
                        color = Color.DarkGray
                    )
                }
            }

            Spacer(modifier = Modifier.height(25.dp))

            // CONTINUE
            Button(
                onClick = {
                    navController.navigate("llDocuments")
                },

                enabled =
                    fullName.isNotBlank() &&
                            dateOfBirth.length == 10 &&
                            mobileNumber.length == 10 &&
                            address.isNotBlank() &&
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