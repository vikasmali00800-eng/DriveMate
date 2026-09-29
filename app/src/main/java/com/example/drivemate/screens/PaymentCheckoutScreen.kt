package com.example.drivemate.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
import com.example.drivemate.notifications.ReminderScheduler
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

    var paymentMethod by remember {
        mutableStateOf("UPI")
    }

    var loading by remember {
        mutableStateOf(false)
    }

    Scaffold(
        containerColor = Color(0xFFF5F8FF),

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
                            imageVector =
                                Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                },

                colors =
                    TopAppBarDefaults.topAppBarColors(
                        containerColor =
                            Color(0xFF171719)
                    )
            )
        }

    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF5F8FF))
                .padding(padding)
                .verticalScroll(
                    rememberScrollState()
                )
                .padding(20.dp)
        ) {

            // =========================
            // TITLE
            // =========================

            Text(
                text = "Complete Payment",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            Text(
                text =
                    "Driving lesson with $instructorName",
                color = Color.Gray,
                fontSize = 16.sp
            )

            Spacer(
                modifier = Modifier.height(25.dp)
            )

            // =========================
            // BOOKING SUMMARY
            // =========================

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
                        text = "Amount to Pay",
                        color = Color.Gray,
                        fontSize = 16.sp
                    )

                    Spacer(
                        modifier =
                            Modifier.height(5.dp)
                    )

                    Text(
                        text = "₹$fee",
                        fontSize = 34.sp,
                        fontWeight =
                            FontWeight.Bold,
                        color =
                            Color(0xFF1565C0)
                    )

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
                        text = "📅 $date",
                        color = Color.Black,
                        fontSize = 16.sp
                    )

                    Spacer(
                        modifier =
                            Modifier.height(8.dp)
                    )

                    Text(
                        text = "🕒 $time",
                        color = Color.Black,
                        fontSize = 16.sp
                    )

                    Spacer(
                        modifier =
                            Modifier.height(8.dp)
                    )

                    Text(
                        text = "🚗 $vehicle",
                        color = Color.Black,
                        fontSize = 16.sp
                    )

                    Spacer(
                        modifier =
                            Modifier.height(8.dp)
                    )

                    Text(
                        text = "📍 $pickup",
                        color = Color.Black,
                        fontSize = 16.sp
                    )
                }
            }

            Spacer(
                modifier =
                    Modifier.height(25.dp)
            )

            // =========================
            // PAYMENT METHOD
            // =========================

            Text(
                text = "Payment Method",
                fontSize = 21.sp,
                fontWeight =
                    FontWeight.Bold,
                color = Color.Black
            )

            Spacer(
                modifier =
                    Modifier.height(12.dp)
            )

            listOf(
                "UPI",
                "Card",
                "Cash"
            ).forEach { method ->

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            vertical = 4.dp
                        ),
                    shape =
                        RoundedCornerShape(14.dp),
                    colors =
                        CardDefaults.cardColors(
                            containerColor =
                                Color.White
                        )
                ) {

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                horizontal = 8.dp,
                                vertical = 4.dp
                            ),
                        verticalAlignment =
                            Alignment.CenterVertically
                    ) {

                        RadioButton(
                            selected =
                                paymentMethod ==
                                        method,

                            onClick = {
                                paymentMethod =
                                    method
                            },

                            enabled = !loading
                        )

                        Text(
                            text = method,
                            color = Color.Black,
                            fontSize = 16.sp
                        )
                    }
                }
            }

            Spacer(
                modifier =
                    Modifier.height(18.dp)
            )

            // =========================
            // REMINDER INFO
            // =========================

            Card(
                modifier =
                    Modifier.fillMaxWidth(),
                shape =
                    RoundedCornerShape(18.dp),
                colors =
                    CardDefaults.cardColors(
                        containerColor =
                            Color.White
                    )
            ) {

                Column(
                    modifier =
                        Modifier.padding(18.dp)
                ) {

                    Text(
                        text =
                            "🔔 Lesson Reminder",
                        fontSize = 17.sp,
                        fontWeight =
                            FontWeight.Bold,
                        color = Color.Black
                    )

                    Spacer(
                        modifier =
                            Modifier.height(7.dp)
                    )

                    Text(
                        text =
                            "DriveMate will schedule a reminder 1 hour before your driving lesson.",
                        fontSize = 14.sp,
                        color =
                            Color.DarkGray
                    )
                }
            }

            Spacer(
                modifier =
                    Modifier.height(25.dp)
            )

            // =========================
            // PAY / CONFIRM
            // =========================

            Button(

                onClick = {

                    val user =
                        auth.currentUser

                    if (user == null) {

                        Toast.makeText(
                            context,
                            "Please login first",
                            Toast.LENGTH_SHORT
                        ).show()

                        return@Button
                    }

                    loading = true

                    // =========================
                    // PAYMENT STATUS
                    // =========================

                    val paymentStatus =
                        if (
                            paymentMethod == "Cash"
                        ) {
                            "Pending"
                        } else {
                            "Paid"
                        }

                    // =========================
                    // PAYMENT DATA
                    // =========================

                    val payment =
                        hashMapOf<String, Any>(

                            "userId" to
                                    user.uid,

                            "instructorName" to
                                    instructorName,

                            "fee" to
                                    fee,

                            "paymentMethod" to
                                    paymentMethod,

                            "status" to
                                    paymentStatus,

                            "createdAt" to
                                    FieldValue.serverTimestamp()
                        )

                    // =========================
                    // BOOKING DATA
                    // =========================

                    val booking =
                        hashMapOf<String, Any>(

                            "userId" to
                                    user.uid,

                            "instructorName" to
                                    instructorName,

                            "fee" to
                                    fee,

                            "date" to
                                    date,

                            "time" to
                                    time,

                            "vehicle" to
                                    vehicle,

                            "location" to
                                    pickup,

                            "paymentMethod" to
                                    paymentMethod,

                            "paymentStatus" to
                                    paymentStatus,

                            "status" to
                                    "Upcoming",

                            "createdAt" to
                                    FieldValue.serverTimestamp()
                        )

                    // =========================
                    // SAVE PAYMENT
                    // =========================

                    db.collection("payments")
                        .add(payment)

                        .addOnSuccessListener {

                            // =========================
                            // SAVE BOOKING
                            // =========================

                            db.collection("bookings")
                                .add(booking)

                                .addOnSuccessListener {
                                        bookingReference ->

                                    // =========================
                                    // REMINDER
                                    // =========================

                                    ReminderScheduler
                                        .scheduleLessonReminder(

                                            context =
                                                context,

                                            bookingId =
                                                bookingReference.id,

                                            instructorName =
                                                instructorName,

                                            lessonDate =
                                                date,

                                            lessonTime =
                                                time
                                        )

                                    loading = false

                                    Toast.makeText(

                                        context,

                                        if (
                                            paymentMethod ==
                                            "Cash"
                                        ) {
                                            "Booking confirmed"
                                        } else {
                                            "Payment successful"
                                        },

                                        Toast.LENGTH_SHORT

                                    ).show()

                                    // =========================
                                    // SUCCESS
                                    // =========================

                                    navController.navigate(
                                        "bookingSuccess"
                                    ) {

                                        popUpTo("home") {
                                            inclusive = false
                                        }

                                        launchSingleTop = true
                                    }
                                }

                                .addOnFailureListener {
                                        exception ->

                                    loading = false

                                    Toast.makeText(
                                        context,
                                        exception.message
                                            ?: "Booking could not be saved",
                                        Toast.LENGTH_LONG
                                    ).show()
                                }
                        }

                        .addOnFailureListener {
                                exception ->

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
                    .height(58.dp),

                shape =
                    RoundedCornerShape(16.dp),

                colors =
                    ButtonDefaults.buttonColors(

                        containerColor =
                            Color(0xFF1565C0),

                        contentColor =
                            Color.White,

                        disabledContainerColor =
                            Color(0xFFBDBDBD),

                        disabledContentColor =
                            Color.White
                    )

            ) {

                if (loading) {

                    CircularProgressIndicator(
                        modifier =
                            Modifier.size(24.dp),
                        color = Color.White,
                        strokeWidth = 2.dp
                    )

                } else {

                    Text(
                        text =
                            if (
                                paymentMethod ==
                                "Cash"
                            ) {
                                "Confirm Booking"
                            } else {
                                "Pay ₹$fee"
                            },

                        fontSize = 17.sp,

                        fontWeight =
                            FontWeight.Bold,

                        color = Color.White
                    )
                }
            }

            // Extra bottom space so button
            // is never hidden behind navigation bar
            Spacer(
                modifier =
                    Modifier.height(30.dp)
            )
        }
    }
}