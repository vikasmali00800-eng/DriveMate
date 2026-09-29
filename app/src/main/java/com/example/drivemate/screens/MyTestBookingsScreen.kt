package com.example.drivemate.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration

data class DrivingTestBooking(
    val id: String = "",
    val rto: String = "",
    val testDay: String = "",
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

    val context = LocalContext.current
    val auth = FirebaseAuth.getInstance()
    val db = FirebaseFirestore.getInstance()

    var bookings by remember {
        mutableStateOf<List<DrivingTestBooking>>(
            emptyList()
        )
    }

    var loading by remember {
        mutableStateOf(true)
    }

    var errorMessage by remember {
        mutableStateOf<String?>(null)
    }

    var bookingToDelete by remember {
        mutableStateOf<DrivingTestBooking?>(null)
    }

    var deletingBookingId by remember {
        mutableStateOf<String?>(null)
    }

    // =========================
    // FIRESTORE REALTIME LISTENER
    // =========================

    DisposableEffect(Unit) {

        val user = auth.currentUser

        var listener: ListenerRegistration? = null

        if (user == null) {

            loading = false

            errorMessage =
                "Please login to view your test bookings."

        } else {

            listener = db
                .collection("drivingTestSlots")
                .whereEqualTo(
                    "userId",
                    user.uid
                )
                .addSnapshotListener {
                        snapshot,
                        error ->

                    if (error != null) {

                        loading = false

                        errorMessage =
                            error.message
                                ?: "Could not load bookings."

                        return@addSnapshotListener
                    }

                    bookings =
                        snapshot
                            ?.documents
                            ?.map { document ->

                                DrivingTestBooking(

                                    id =
                                        document.id,

                                    rto =
                                        document.getString(
                                            "rto"
                                        )
                                            ?: "Not available",

                                    testDay =
                                        document.getString(
                                            "testDay"
                                        )
                                            ?: "Not available",

                                    testDate =
                                        document.getString(
                                            "testDate"
                                        )
                                            ?: "Not available",

                                    testTime =
                                        document.getString(
                                            "testTime"
                                        )
                                            ?: "Not available",

                                    vehicleClasses =
                                        (
                                                document.get(
                                                    "vehicleClasses"
                                                ) as? List<*>
                                                )
                                            ?.mapNotNull {
                                                it?.toString()
                                            }
                                            ?: emptyList(),

                                    status =
                                        document.getString(
                                            "status"
                                        )
                                            ?: "Preferred"
                                )
                            }
                            ?: emptyList()

                    loading = false
                    errorMessage = null
                }
        }

        onDispose {

            listener?.remove()
        }
    }

    // =========================
    // DELETE CONFIRMATION
    // =========================

    bookingToDelete?.let { booking ->

        AlertDialog(

            onDismissRequest = {

                if (
                    deletingBookingId == null
                ) {

                    bookingToDelete = null
                }
            },

            title = {

                Text(
                    text =
                        "Delete Test Booking?",

                    fontWeight =
                        FontWeight.Bold
                )
            },

            text = {

                Column {

                    Text(
                        text =
                            "Are you sure you want to delete this test preference?"
                    )

                    Spacer(
                        modifier =
                            Modifier.height(
                                12.dp
                            )
                    )

                    Text(
                        text =
                            booking.rto,

                        fontWeight =
                            FontWeight.Bold
                    )

                    Spacer(
                        modifier =
                            Modifier.height(
                                4.dp
                            )
                    )

                    Text(
                        text =
                            "Test Day: ${booking.testDay}"
                    )

                    Text(
                        text =
                            "Date: ${booking.testDate}"
                    )

                    Text(
                        text =
                            "Time: ${booking.testTime}"
                    )

                    Spacer(
                        modifier =
                            Modifier.height(
                                10.dp
                            )
                    )

                    Text(
                        text =
                            "The scheduled DriveMate reminder for this preference will also be cancelled.",

                        fontSize =
                            13.sp,

                        color =
                            Color.DarkGray
                    )
                }
            },

            confirmButton = {

                TextButton(

                    enabled =
                        deletingBookingId == null,

                    onClick = {

                        val user =
                            auth.currentUser

                        if (user == null) {

                            Toast.makeText(
                                context,
                                "Please login first",
                                Toast.LENGTH_SHORT
                            ).show()

                            bookingToDelete = null

                            return@TextButton
                        }

                        deletingBookingId =
                            booking.id

                        // =========================
                        // VERIFY OWNER
                        // =========================

                        db
                            .collection(
                                "drivingTestSlots"
                            )
                            .document(
                                booking.id
                            )
                            .get()

                            .addOnSuccessListener {
                                    document ->

                                if (
                                    !document.exists() ||
                                    document.getString(
                                        "userId"
                                    ) != user.uid
                                ) {

                                    deletingBookingId =
                                        null

                                    bookingToDelete =
                                        null

                                    Toast.makeText(
                                        context,
                                        "Booking could not be deleted",
                                        Toast.LENGTH_SHORT
                                    ).show()

                                    return@addOnSuccessListener
                                }

                                // =========================
                                // DELETE FIRESTORE BOOKING
                                // =========================

                                document
                                    .reference
                                    .delete()

                                    .addOnSuccessListener {

                                        // =========================
                                        // CANCEL REMINDER
                                        // =========================

                                        ReminderScheduler
                                            .cancelTestReminder(

                                                context =
                                                    context,

                                                bookingId =
                                                    booking.id
                                            )

                                        deletingBookingId =
                                            null

                                        bookingToDelete =
                                            null

                                        Toast.makeText(
                                            context,
                                            "Test booking deleted",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }

                                    .addOnFailureListener {
                                            exception ->

                                        deletingBookingId =
                                            null

                                        Toast.makeText(
                                            context,

                                            exception.message
                                                ?: "Could not delete booking",

                                            Toast.LENGTH_LONG
                                        ).show()
                                    }
                            }

                            .addOnFailureListener {
                                    exception ->

                                deletingBookingId =
                                    null

                                Toast.makeText(
                                    context,

                                    exception.message
                                        ?: "Could not verify booking",

                                    Toast.LENGTH_LONG
                                ).show()
                            }
                    }

                ) {

                    if (
                        deletingBookingId ==
                        booking.id
                    ) {

                        CircularProgressIndicator(
                            modifier =
                                Modifier.size(
                                    20.dp
                                ),

                            strokeWidth =
                                2.dp
                        )

                    } else {

                        Text(
                            text =
                                "Delete",

                            color =
                                Color(
                                    0xFFD32F2F
                                ),

                            fontWeight =
                                FontWeight.Bold
                        )
                    }
                }
            },

            dismissButton = {

                TextButton(

                    enabled =
                        deletingBookingId == null,

                    onClick = {

                        bookingToDelete = null
                    }

                ) {

                    Text(
                        text =
                            "Cancel"
                    )
                }
            }
        )
    }

    // =========================
    // SCREEN
    // =========================

    Scaffold(

        containerColor =
            Color(0xFFF5F8FF),

        topBar = {

            TopAppBar(

                title = {

                    Text(
                        text =
                            "My Test Bookings",

                        fontWeight =
                            FontWeight.Bold,

                        color =
                            Color.White
                    )
                },

                navigationIcon = {

                    IconButton(

                        onClick = {

                            navController
                                .popBackStack()
                        }

                    ) {

                        Icon(

                            imageVector =
                                Icons
                                    .AutoMirrored
                                    .Filled
                                    .ArrowBack,

                            contentDescription =
                                "Back",

                            tint =
                                Color.White
                        )
                    }
                },

                colors =
                    TopAppBarDefaults
                        .topAppBarColors(

                            containerColor =
                                Color(
                                    0xFF171719
                                )
                        )
            )
        }

    ) { padding ->

        when {

            // =========================
            // LOADING
            // =========================

            loading -> {

                Box(

                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Color(
                                0xFFF5F8FF
                            )
                        )
                        .padding(padding),

                    contentAlignment =
                        Alignment.Center

                ) {

                    CircularProgressIndicator(
                        color =
                            Color(
                                0xFF1565C0
                            )
                    )
                }
            }

            // =========================
            // ERROR
            // =========================

            errorMessage != null -> {

                Column(

                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Color(
                                0xFFF5F8FF
                            )
                        )
                        .padding(padding)
                        .padding(20.dp)

                ) {

                    Text(
                        text =
                            "Unable to load bookings",

                        fontSize =
                            23.sp,

                        fontWeight =
                            FontWeight.Bold,

                        color =
                            Color.Black
                    )

                    Spacer(
                        modifier =
                            Modifier.height(
                                10.dp
                            )
                    )

                    Text(
                        text =
                            errorMessage
                                ?: "Unknown error",

                        color =
                            Color.DarkGray
                    )

                    Spacer(
                        modifier =
                            Modifier.height(
                                24.dp
                            )
                    )

                    Button(

                        onClick = {

                            navController
                                .popBackStack()
                        },

                        modifier =
                            Modifier
                                .fillMaxWidth(),

                        colors =
                            ButtonDefaults
                                .buttonColors(

                                    containerColor =
                                        Color(
                                            0xFF1565C0
                                        )
                                )

                    ) {

                        Text(
                            text =
                                "Go Back",

                            color =
                                Color.White
                        )
                    }
                }
            }

            // =========================
            // NO BOOKINGS
            // =========================

            bookings.isEmpty() -> {

                Column(

                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Color(
                                0xFFF5F8FF
                            )
                        )
                        .padding(padding)
                        .padding(20.dp)

                ) {

                    Text(
                        text =
                            "No Test Bookings",

                        fontSize =
                            25.sp,

                        fontWeight =
                            FontWeight.Bold,

                        color =
                            Color.Black
                    )

                    Spacer(
                        modifier =
                            Modifier.height(
                                8.dp
                            )
                    )

                    Text(
                        text =
                            "You have not saved a driving test preference yet.",

                        color =
                            Color.DarkGray
                    )

                    Spacer(
                        modifier =
                            Modifier.height(
                                25.dp
                            )
                    )

                    Button(

                        onClick = {

                            navController.navigate(
                                "drivingTestSlot"
                            )
                        },

                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .height(
                                    55.dp
                                ),

                        shape =
                            RoundedCornerShape(
                                16.dp
                            ),

                        colors =
                            ButtonDefaults
                                .buttonColors(

                                    containerColor =
                                        Color(
                                            0xFF1565C0
                                        )
                                )

                    ) {

                        Text(
                            text =
                                "Add Test Preference",

                            color =
                                Color.White,

                            fontWeight =
                                FontWeight.Bold
                        )
                    }
                }
            }

            // =========================
            // BOOKINGS
            // =========================

            else -> {

                LazyColumn(

                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Color(
                                0xFFF5F8FF
                            )
                        )
                        .padding(padding)
                        .padding(16.dp),

                    verticalArrangement =
                        Arrangement.spacedBy(
                            16.dp
                        )

                ) {

                    item {

                        Text(
                            text =
                                "Saved Test Preferences",

                            fontSize =
                                25.sp,

                            fontWeight =
                                FontWeight.Bold,

                            color =
                                Color.Black
                        )

                        Spacer(
                            modifier =
                                Modifier.height(
                                    5.dp
                                )
                        )

                        Text(
                            text =
                                "View, edit or delete your saved test preferences.",

                            color =
                                Color.DarkGray,

                            fontSize =
                                14.sp
                        )

                        Spacer(
                            modifier =
                                Modifier.height(
                                    5.dp
                                )
                        )

                        Text(
                            text =
                                "These are DriveMate preferences, not confirmed government appointments.",

                            color =
                                Color.Gray,

                            fontSize =
                                12.sp
                        )
                    }

                    // =========================
                    // BOOKING CARDS
                    // =========================

                    items(

                        items =
                            bookings,

                        key = {
                                booking ->

                            booking.id
                        }

                    ) { booking ->

                        Card(

                            modifier =
                                Modifier
                                    .fillMaxWidth(),

                            shape =
                                RoundedCornerShape(
                                    20.dp
                                ),

                            colors =
                                CardDefaults
                                    .cardColors(

                                        containerColor =
                                            Color.White
                                    )

                        ) {

                            Column(

                                modifier =
                                    Modifier.padding(
                                        20.dp
                                    )

                            ) {

                                // =========================
                                // RTO
                                // =========================

                                Text(
                                    text =
                                        booking.rto,

                                    fontSize =
                                        21.sp,

                                    fontWeight =
                                        FontWeight.Bold,

                                    color =
                                        Color.Black
                                )

                                Spacer(
                                    modifier =
                                        Modifier.height(
                                            14.dp
                                        )
                                )

                                // =========================
                                // TEST DAY
                                // =========================

                                Text(
                                    text =
                                        "📆 Test Day",

                                    fontSize =
                                        13.sp,

                                    color =
                                        Color.DarkGray
                                )

                                Text(
                                    text =
                                        booking.testDay,

                                    fontSize =
                                        18.sp,

                                    fontWeight =
                                        FontWeight.Bold,

                                    color =
                                        Color(
                                            0xFF1565C0
                                        )
                                )

                                Spacer(
                                    modifier =
                                        Modifier.height(
                                            12.dp
                                        )
                                )

                                // =========================
                                // DATE
                                // =========================

                                Text(
                                    text =
                                        "📅 Date: ${booking.testDate}",

                                    fontSize =
                                        15.sp,

                                    color =
                                        Color.Black
                                )

                                Spacer(
                                    modifier =
                                        Modifier.height(
                                            8.dp
                                        )
                                )

                                // =========================
                                // TIME
                                // =========================

                                Text(
                                    text =
                                        "🕒 Time: ${booking.testTime}",

                                    fontSize =
                                        15.sp,

                                    color =
                                        Color.Black
                                )

                                Spacer(
                                    modifier =
                                        Modifier.height(
                                            8.dp
                                        )
                                )

                                // =========================
                                // VEHICLE
                                // =========================

                                val vehicles =

                                    if (
                                        booking
                                            .vehicleClasses
                                            .isEmpty()
                                    ) {

                                        "Not selected"

                                    } else {

                                        booking
                                            .vehicleClasses
                                            .joinToString(
                                                ", "
                                            )
                                    }

                                Text(
                                    text =
                                        "🚗 Vehicle: $vehicles",

                                    fontSize =
                                        15.sp,

                                    color =
                                        Color.Black
                                )

                                Spacer(
                                    modifier =
                                        Modifier.height(
                                            16.dp
                                        )
                                )

                                // =========================
                                // STATUS
                                // =========================

                                Surface(

                                    shape =
                                        RoundedCornerShape(
                                            50.dp
                                        ),

                                    color =
                                        Color(
                                            0xFFE3F2FD
                                        )

                                ) {

                                    Text(
                                        text =
                                            booking.status,

                                        modifier =
                                            Modifier.padding(
                                                horizontal =
                                                    14.dp,

                                                vertical =
                                                    7.dp
                                            ),

                                        color =
                                            Color(
                                                0xFF1565C0
                                            ),

                                        fontWeight =
                                            FontWeight.Bold,

                                        fontSize =
                                            13.sp
                                    )
                                }

                                Spacer(
                                    modifier =
                                        Modifier.height(
                                            18.dp
                                        )
                                )

                                HorizontalDivider()

                                Spacer(
                                    modifier =
                                        Modifier.height(
                                            12.dp
                                        )
                                )

                                // =========================
                                // EDIT + DELETE
                                // =========================

                                Row(

                                    modifier =
                                        Modifier
                                            .fillMaxWidth(),

                                    horizontalArrangement =
                                        Arrangement
                                            .spacedBy(
                                                10.dp
                                            )

                                ) {

                                    // =========================
                                    // EDIT
                                    // =========================

                                    OutlinedButton(

                                        onClick = {

                                            navController
                                                .navigate(

                                                    "editTestBooking/${booking.id}"
                                                )
                                        },

                                        enabled =
                                            deletingBookingId ==
                                                    null,

                                        modifier =
                                            Modifier
                                                .weight(1f)
                                                .height(
                                                    48.dp
                                                ),

                                        shape =
                                            RoundedCornerShape(
                                                14.dp
                                            ),

                                        colors =
                                            ButtonDefaults
                                                .outlinedButtonColors(

                                                    containerColor =
                                                        Color.White,

                                                    contentColor =
                                                        Color(
                                                            0xFF1565C0
                                                        )
                                                )

                                    ) {

                                        Text(
                                            text =
                                                "Edit",

                                            color =
                                                Color(
                                                    0xFF1565C0
                                                ),

                                            fontWeight =
                                                FontWeight.Bold
                                        )
                                    }

                                    // =========================
                                    // DELETE
                                    // =========================

                                    Button(

                                        onClick = {

                                            bookingToDelete =
                                                booking
                                        },

                                        enabled =
                                            deletingBookingId ==
                                                    null,

                                        modifier =
                                            Modifier
                                                .weight(1f)
                                                .height(
                                                    48.dp
                                                ),

                                        shape =
                                            RoundedCornerShape(
                                                14.dp
                                            ),

                                        colors =
                                            ButtonDefaults
                                                .buttonColors(

                                                    containerColor =
                                                        Color(
                                                            0xFFD32F2F
                                                        ),

                                                    contentColor =
                                                        Color.White
                                                )

                                      ) {

                                        Text(
                                            text =
                                                "Delete",

                                            color =
                                                Color.White,

                                            fontWeight =
                                                FontWeight.Bold
                                        )
                                    }
                                }
                            }
                        }
                    }

                    // =========================
                    // ADD ANOTHER
                    // =========================

                    item {

                        Button(

                            onClick = {

                                navController
                                    .navigate(
                                        "drivingTestSlot"
                                    )
                            },

                            modifier =
                                Modifier
                                    .fillMaxWidth()
                                    .height(
                                        55.dp
                                    ),

                            shape =
                                RoundedCornerShape(
                                    16.dp
                                ),

                            colors =
                                ButtonDefaults
                                    .buttonColors(

                                        containerColor =
                                            Color(
                                                0xFF1565C0
                                            )
                                    )

                        ) {

                            Text(
                                text =
                                    "Add Another Test Preference",

                                color =
                                    Color.White,

                                fontWeight =
                                    FontWeight.Bold
                            )
                        }

                        Spacer(
                            modifier =
                                Modifier.height(
                                    20.dp
                                )
                        )
                    }
                }
            }
        }
    }
}