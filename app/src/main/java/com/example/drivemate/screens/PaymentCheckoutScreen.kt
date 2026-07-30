package com.example.drivemate.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PaymentCheckoutScreen(
    navController: NavController,
    instructorName: String,
    fee: String,
    date: String,
    time: String,
    vehicle: String,
    pickup: String
) {

    val context = LocalContext.current
    val auth = FirebaseAuth.getInstance()
    val db = FirebaseFirestore.getInstance()

    var paymentMethod by remember { mutableStateOf("UPI") }
    var loading by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Payment",
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
                .padding(20.dp)
        ) {

            Text(
                text = "Complete Payment",
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Driving lesson with $instructorName",
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(25.dp))

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
                        text = "Amount to Pay",
                        color = Color.Gray
                    )

                    Spacer(modifier = Modifier.height(5.dp))

                    Text(
                        text = "₹$fee",
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF1565C0)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    HorizontalDivider()

                    Spacer(modifier = Modifier.height(16.dp))

                    Text("📅 $date")
                    Text("🕒 $time")
                    Text("🚗 $vehicle")
                    Text("📍 $pickup")
                }
            }

            Spacer(modifier = Modifier.height(25.dp))

            Text(
                text = "Payment Method",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(12.dp))

            listOf("UPI", "Card", "Cash").forEach { method ->

                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {

                    RadioButton(
                        selected = paymentMethod == method,
                        onClick = {
                            paymentMethod = method
                        },
                        enabled = !loading
                    )

                    Text(
                        text = method,
                        modifier = Modifier.padding(top = 12.dp),
                        color = Color.Black
                    )
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = {

                    val user = auth.currentUser

                    if (user == null) {

                        Toast.makeText(
                            context,
                            "Please login first",
                            Toast.LENGTH_SHORT
                        ).show()

                        return@Button
                    }

                    loading = true

                    val paymentStatus =
                        if (paymentMethod == "Cash") {
                            "Pending"
                        } else {
                            "Paid"
                        }

                    val payment = hashMapOf(
                        "userId" to user.uid,
                        "instructorName" to instructorName,
                        "fee" to fee,
                        "paymentMethod" to paymentMethod,
                        "status" to paymentStatus,
                        "createdAt" to FieldValue.serverTimestamp()
                    )

                    val booking = hashMapOf(
                        "userId" to user.uid,
                        "instructorName" to instructorName,
                        "fee" to fee,
                        "date" to date,
                        "time" to time,
                        "vehicle" to vehicle,
                        "location" to pickup,
                        "paymentMethod" to paymentMethod,
                        "paymentStatus" to paymentStatus,
                        "status" to "Upcoming",
                        "createdAt" to FieldValue.serverTimestamp()
                    )

                    // Save payment first
                    db.collection("payments")
                        .add(payment)
                        .addOnSuccessListener {

                            // Then save booking
                            db.collection("bookings")
                                .add(booking)
                                .addOnSuccessListener {

                                    loading = false

                                    Toast.makeText(
                                        context,
                                        if (paymentMethod == "Cash") {
                                            "Booking confirmed"
                                        } else {
                                            "Payment successful"
                                        },
                                        Toast.LENGTH_SHORT
                                    ).show()

                                    navController.navigate("bookingSuccess") {

                                        popUpTo("home") {
                                            inclusive = false
                                        }

                                        launchSingleTop = true
                                    }
                                }
                                .addOnFailureListener { exception ->

                                    loading = false

                                    Toast.makeText(
                                        context,
                                        exception.message
                                            ?: "Booking could not be saved",
                                        Toast.LENGTH_LONG
                                    ).show()
                                }
                        }
                        .addOnFailureListener { exception ->

                            loading = false

                            Toast.makeText(
                                context,
                                exception.message
                                    ?: "Payment could not be saved",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                },

                enabled = !loading,

                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),

                shape = RoundedCornerShape(16.dp),

                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF1565C0)
                )
            ) {

                if (loading) {

                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        color = Color.White,
                        strokeWidth = 2.dp
                    )

                } else {

                    Text(
                        text = if (paymentMethod == "Cash") {
                            "Confirm Booking"
                        } else {
                            "Pay ₹$fee"
                        },
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }
        }
    }
}