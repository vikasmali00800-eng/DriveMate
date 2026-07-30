package com.example.drivemate.screens

import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
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
        topBar = {
            TopAppBar(
                title = {
                    Text("Select Instructor")
                },

                navigationIcon = {
                    IconButton(
                        onClick = {
                            navController.popBackStack()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {

            // Rahul
            InstructorCard(
                name = "Rahul Sharma",
                rating = "4.9",
                experience = "10 Years",
                distance = "2.5 km",
                fee = "₹499/hr"
            ) {

                val instructor = Uri.encode("Rahul Sharma")
                val encodedDate = Uri.encode(date)
                val encodedTime = Uri.encode(time)
                val encodedVehicle = Uri.encode(vehicle)
                val encodedPickup = Uri.encode(pickup)

                navController.navigate(
                    "paymentCheckout/$instructor/499/" +
                            "$encodedDate/$encodedTime/" +
                            "$encodedVehicle/$encodedPickup"
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Sonia
            InstructorCard(
                name = "Sonia Verma",
                rating = "4.7",
                experience = "7 Years",
                distance = "3.1 km",
                fee = "₹450/hr"
            ) {

                val instructor = Uri.encode("Sonia Verma")
                val encodedDate = Uri.encode(date)
                val encodedTime = Uri.encode(time)
                val encodedVehicle = Uri.encode(vehicle)
                val encodedPickup = Uri.encode(pickup)

                navController.navigate(
                    "paymentCheckout/$instructor/450/" +
                            "$encodedDate/$encodedTime/" +
                            "$encodedVehicle/$encodedPickup"
                )
            }
        }
    }
}