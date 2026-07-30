package com.example.drivemate.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.Query

data class DrivingTestBooking(
    val id: String = "",
    val rto: String = "",
    val testDate: String = "",
    val testTime: String = "",
    val vehicleClasses: List<String> = emptyList(),
    val status: String = "Preferred"
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTestBookingsScreen(
    navController: NavController
) {

    val auth = FirebaseAuth.getInstance()
    val db = FirebaseFirestore.getInstance()

    var bookings by remember {
        mutableStateOf<List<DrivingTestBooking>>(emptyList())
    }

    var loading by remember {
        mutableStateOf(true)
    }

    var errorMessage by remember {
        mutableStateOf<String?>(null)
    }

    DisposableEffect(Unit) {

        val user = auth.currentUser
        var listener: ListenerRegistration? = null

        if (user == null) {

            loading = false
            errorMessage = "Please login to view your test bookings."

        } else {

            listener = db
                .collection("drivingTestSlots")
                .whereEqualTo("userId", user.uid)
                .orderBy(
                    "createdAt",
                    Query.Direction.DESCENDING
                )
                .addSnapshotListener { snapshot, error ->

                    if (error != null) {
                        loading = false
                        errorMessage =
                            error.message ?: "Could not load bookings."
                        return@addSnapshotListener
                    }

                    bookings = snapshot?.documents?.map { document ->

                        DrivingTestBooking(
                            id = document.id,

                            rto = document.getString("rto")
                                ?: "Not available",

                            testDate = document.getString("testDate")
                                ?: "Not available",

                            testTime = document.getString("testTime")
                                ?: "Not available",

                            vehicleClasses =
                                (document.get("vehicleClasses")
                                        as? List<*>)
                                    ?.mapNotNull {
                                        it?.toString()
                                    }
                                    ?: emptyList(),

                            status = document.getString("status")
                                ?: "Preferred"
                        )

                    } ?: emptyList()

                    loading = false
                    errorMessage = null
                }
        }

        onDispose {
            listener?.remove()
        }
    }

    Scaffold(
        containerColor = Color(0xFFF5F8FF),

        topBar = {

            TopAppBar(
                title = {

                    Text(
                        text = "My Test Bookings",
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

                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF171719)
                )
            )
        }
    ) { padding ->

        when {

            loading -> {

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(0xFFF5F8FF))
                        .padding(padding)
                ) {

                    CircularProgressIndicator(
                        modifier = Modifier
                            .padding(30.dp)
                    )
                }
            }

            errorMessage != null -> {

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(0xFFF5F8FF))
                        .padding(padding)
                        .padding(20.dp)
                ) {

                    Text(
                        text = "Unable to load bookings",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )

                    Spacer(
                        modifier = Modifier.height(8.dp)
                    )

                    Text(
                        text = errorMessage ?: "",
                        color = Color.DarkGray
                    )
                }
            }

            bookings.isEmpty() -> {

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(0xFFF5F8FF))
                        .padding(padding)
                        .padding(20.dp)
                ) {

                    Text(
                        text = "No Test Bookings",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )

                    Spacer(
                        modifier = Modifier.height(8.dp)
                    )

                    Text(
                        text = "Your saved driving-test preferences will appear here.",
                        color = Color.DarkGray
                    )

                    Spacer(
                        modifier = Modifier.height(24.dp)
                    )

                    Button(
                        onClick = {
                            navController.navigate(
                                "drivingTestSlot"
                            )
                        },

                        modifier = Modifier
                            .fillMaxWidth()
                            .height(54.dp),

                        shape = RoundedCornerShape(16.dp),

                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF1565C0)
                        )
                    ) {

                        Text(
                            text = "Add Test Preference",
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            else -> {

                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(0xFFF5F8FF))
                        .padding(padding)
                        .padding(16.dp),

                    verticalArrangement =
                        Arrangement.spacedBy(16.dp)
                ) {

                    item {

                        Text(
                            text = "Saved Test Preferences",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )

                        Spacer(
                            modifier = Modifier.height(4.dp)
                        )

                        Text(
                            text = "These are DriveMate preferences, not confirmed government RTO appointments.",
                            fontSize = 13.sp,
                            color = Color.DarkGray
                        )
                    }

                    items(
                        items = bookings,
                        key = { it.id }
                    ) { booking ->

                        Card(
                            modifier = Modifier
                                .fillMaxWidth(),

                            shape = RoundedCornerShape(20.dp),

                            colors = CardDefaults.cardColors(
                                containerColor = Color.White
                            )
                        ) {

                            Column(
                                modifier = Modifier
                                    .padding(18.dp)
                            ) {

                                Text(
                                    text = booking.rto,
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.Black
                                )

                                Spacer(
                                    modifier = Modifier.height(12.dp)
                                )

                                Text(
                                    text = "📅 ${booking.testDate}",
                                    color = Color.Black
                                )

                                Spacer(
                                    modifier = Modifier.height(5.dp)
                                )

                                Text(
                                    text = "🕒 ${booking.testTime}",
                                    color = Color.Black
                                )

                                Spacer(
                                    modifier = Modifier.height(5.dp)
                                )

                                val vehicles =
                                    if (
                                        booking.vehicleClasses.isEmpty()
                                    ) {
                                        "Not selected"
                                    } else {
                                        booking.vehicleClasses
                                            .joinToString(", ")
                                    }

                                Text(
                                    text = "🚗 Vehicle: $vehicles",
                                    color = Color.Black
                                )

                                Spacer(
                                    modifier = Modifier.height(14.dp)
                                )

                                Surface(
                                    shape =
                                        RoundedCornerShape(50.dp),

                                    color = Color(0xFFE3F2FD)
                                ) {

                                    Text(
                                        text = booking.status,
                                        modifier = Modifier
                                            .padding(
                                                horizontal = 14.dp,
                                                vertical = 7.dp
                                            ),
                                        color = Color(0xFF1565C0),
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 13.sp
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}