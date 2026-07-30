package com.example.drivemate.screens

import android.widget.Toast
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DrivingTestSlotScreen(
    navController: NavController
) {

    val context = LocalContext.current

    val auth = FirebaseAuth.getInstance()
    val db = FirebaseFirestore.getInstance()

    var selectedRto by remember { mutableStateOf("") }
    var testDate by remember { mutableStateOf("") }
    var selectedTime by remember { mutableStateOf("") }

    var carSelected by remember { mutableStateOf(false) }
    var bikeSelected by remember { mutableStateOf(false) }

    var rtoExpanded by remember { mutableStateOf(false) }
    var loading by remember { mutableStateOf(false) }

    val rtoTestDays = mapOf(
        "Vadodara RTO" to "Check with RTO",
        "Ahmedabad RTO" to "Check with RTO",
        "Surat RTO" to "Check with RTO",

        // Current demo/configured value
        "Silvassa RTO" to "Wednesday"
    )

    val rtoOptions = rtoTestDays.keys.toList()

    val timeSlots = listOf(
        "09:00 AM",
        "10:00 AM",
        "11:00 AM",
        "02:00 PM",
        "03:00 PM"
    )

    val testDay = rtoTestDays[selectedRto] ?: ""

    // Checks DD/MM/YYYY and returns weekday
    fun getDayFromDate(date: String): String? {

        if (date.length != 10) {
            return null
        }

        return try {

            val format = SimpleDateFormat(
                "dd/MM/yyyy",
                Locale.ENGLISH
            )

            format.isLenient = false

            val parsedDate = format.parse(date)
                ?: return null

            val calendar = Calendar.getInstance()

            calendar.time = parsedDate

            SimpleDateFormat(
                "EEEE",
                Locale.ENGLISH
            ).format(calendar.time)

        } catch (e: Exception) {
            null
        }
    }

    val enteredDateDay =
        getDayFromDate(testDate)

    val dateValid = when {

        testDate.length != 10 -> false

        enteredDateDay == null -> false

        testDay.isBlank() -> false

        testDay == "Check with RTO" -> true

        else ->
            enteredDateDay.equals(
                testDay,
                ignoreCase = true
            )
    }

    Scaffold(
        containerColor = Color(0xFFF5F8FF),

        topBar = {

            TopAppBar(
                title = {

                    Text(
                        text = "Driving Test Slot",
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

            Text(
                text = "Book Driving Test",
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            Spacer(
                modifier = Modifier.height(6.dp)
            )

            Text(
                text = "Select your preferred test details",
                fontSize = 14.sp,
                color = Color.DarkGray
            )

            Spacer(
                modifier = Modifier.height(24.dp)
            )

            // =========================
            // RTO
            // =========================

            Text(
                text = "RTO / Testing Location",
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            ExposedDropdownMenuBox(
                expanded = rtoExpanded,

                onExpandedChange = {

                    if (!loading) {
                        rtoExpanded =
                            !rtoExpanded
                    }
                }
            ) {

                OutlinedTextField(
                    value = selectedRto,

                    onValueChange = {},

                    readOnly = true,

                    label = {
                        Text(
                            text = "Select RTO"
                        )
                    },

                    trailingIcon = {

                        ExposedDropdownMenuDefaults
                            .TrailingIcon(
                                expanded = rtoExpanded
                            )
                    },

                    colors =
                        OutlinedTextFieldDefaults.colors(

                            focusedTextColor =
                                Color.Black,

                            unfocusedTextColor =
                                Color.Black,

                            focusedLabelColor =
                                Color(0xFF1565C0),

                            unfocusedLabelColor =
                                Color.DarkGray,

                            focusedBorderColor =
                                Color(0xFF1565C0),

                            unfocusedBorderColor =
                                Color.Gray,

                            focusedContainerColor =
                                Color.White,

                            unfocusedContainerColor =
                                Color.White
                        ),

                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth()
                )

                ExposedDropdownMenu(
                    expanded = rtoExpanded,

                    onDismissRequest = {
                        rtoExpanded = false
                    },

                    containerColor = Color.White
                ) {

                    rtoOptions.forEach { rto ->

                        DropdownMenuItem(

                            text = {

                                Text(
                                    text = rto,
                                    color = Color.Black
                                )
                            },

                            onClick = {

                                selectedRto = rto

                                // Clear previous selection
                                testDate = ""
                                selectedTime = ""

                                rtoExpanded = false
                            }
                        )
                    }
                }
            }

            // =========================
            // TEST DAY
            // =========================

            if (selectedRto.isNotBlank()) {

                Spacer(
                    modifier = Modifier.height(14.dp)
                )

                Card(
                    modifier = Modifier
                        .fillMaxWidth(),

                    shape = RoundedCornerShape(18.dp),

                    colors = CardDefaults.cardColors(
                        containerColor = Color.White
                    )
                ) {

                    Column(
                        modifier = Modifier.padding(18.dp)
                    ) {

                        Text(
                            text = "Driving Test Day",
                            fontSize = 14.sp,
                            color = Color.DarkGray
                        )

                        Spacer(
                            modifier = Modifier.height(4.dp)
                        )

                        Text(
                            text = testDay,
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF1565C0)
                        )

                        Spacer(
                            modifier = Modifier.height(5.dp)
                        )

                        if (
                            testDay ==
                            "Check with RTO"
                        ) {

                            Text(
                                text =
                                    "Test day has not been configured in DriveMate. Confirm the schedule with the RTO.",
                                fontSize = 13.sp,
                                color = Color.DarkGray
                            )

                        } else {

                            Text(
                                text =
                                    "DriveMate is currently configured to show $testDay as the test day for $selectedRto.",
                                fontSize = 13.sp,
                                color = Color.DarkGray
                            )
                        }
                    }
                }
            }

            Spacer(
                modifier = Modifier.height(22.dp)
            )

            // =========================
            // DATE
            // =========================

            Text(
                text = "Preferred Test Date",
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            OutlinedTextField(
                value = testDate,

                onValueChange = { input ->

                    val digits =
                        input
                            .filter {
                                it.isDigit()
                            }
                            .take(8)

                    testDate = buildString {

                        digits.forEachIndexed {
                                index,
                                char ->

                            append(char)

                            if (
                                index == 1 &&
                                digits.length > 2
                            ) {
                                append("/")
                            }

                            if (
                                index == 3 &&
                                digits.length > 4
                            ) {
                                append("/")
                            }
                        }
                    }
                },

                enabled =
                    !loading &&
                            selectedRto.isNotBlank(),

                label = {
                    Text(
                        text = "DD/MM/YYYY"
                    )
                },

                keyboardOptions =
                    KeyboardOptions(
                        keyboardType =
                            KeyboardType.Number
                    ),

                singleLine = true,

                isError =
                    testDate.length == 10 &&
                            !dateValid,

                supportingText = {

                    if (
                        testDate.length == 10 &&
                        enteredDateDay == null
                    ) {

                        Text(
                            text =
                                "Enter a valid date.",
                            color =
                                MaterialTheme
                                    .colorScheme
                                    .error
                        )

                    } else if (
                        testDate.length == 10 &&
                        testDay !=
                        "Check with RTO" &&
                        enteredDateDay != null &&
                        !dateValid
                    ) {

                        Text(
                            text =
                                "Selected date is $enteredDateDay. Please select a $testDay.",
                            color =
                                MaterialTheme
                                    .colorScheme
                                    .error
                        )

                    } else if (
                        testDate.length == 10 &&
                        dateValid &&
                        enteredDateDay != null
                    ) {

                        Text(
                            text =
                                "Selected day: $enteredDateDay",
                            color =
                                Color(0xFF2E7D32)
                        )
                    }
                },

                colors =
                    OutlinedTextFieldDefaults.colors(

                        focusedTextColor =
                            Color.Black,

                        unfocusedTextColor =
                            Color.Black,

                        focusedLabelColor =
                            Color(0xFF1565C0),

                        unfocusedLabelColor =
                            Color.DarkGray,

                        focusedContainerColor =
                            Color.White,

                        unfocusedContainerColor =
                            Color.White,

                        cursorColor =
                            Color(0xFF1565C0)
                    ),

                modifier = Modifier.fillMaxWidth()
            )

            Spacer(
                modifier = Modifier.height(24.dp)
            )

            // =========================
            // VEHICLE
            // =========================

            Text(
                text = "Vehicle Class",
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            Spacer(
                modifier = Modifier.height(4.dp)
            )

            Text(
                text =
                    "You can select Car, Bike, or both",
                fontSize = 13.sp,
                color = Color.DarkGray
            )

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),

                verticalAlignment =
                    Alignment.CenterVertically
            ) {

                Checkbox(
                    checked = carSelected,

                    onCheckedChange = {
                        carSelected = it
                    },

                    enabled = !loading
                )

                Text(
                    text = "🚗 Car (LMV)",
                    fontSize = 16.sp,
                    color = Color.Black
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),

                verticalAlignment =
                    Alignment.CenterVertically
            ) {

                Checkbox(
                    checked = bikeSelected,

                    onCheckedChange = {
                        bikeSelected = it
                    },

                    enabled = !loading
                )

                Text(
                    text = "🏍️ Bike (MCWG)",
                    fontSize = 16.sp,
                    color = Color.Black
                )
            }

            Spacer(
                modifier = Modifier.height(22.dp)
            )

            // =========================
            // TIME
            // =========================

            Text(
                text = "Preferred Time",
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            Card(
                modifier = Modifier.fillMaxWidth(),

                shape = RoundedCornerShape(18.dp),

                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                )
            ) {

                Column(
                    modifier = Modifier.padding(
                        horizontal = 12.dp,
                        vertical = 8.dp
                    )
                ) {

                    timeSlots.forEach { time ->

                        Row(
                            modifier =
                                Modifier.fillMaxWidth(),

                            verticalAlignment =
                                Alignment.CenterVertically
                        ) {

                            RadioButton(
                                selected =
                                    selectedTime == time,

                                onClick = {
                                    selectedTime = time
                                },

                                enabled =
                                    !loading &&
                                            dateValid
                            )

                            Text(
                                text = time,
                                fontSize = 16.sp,
                                color = Color.Black
                            )
                        }
                    }
                }
            }

            Spacer(
                modifier = Modifier.height(22.dp)
            )

            // =========================
            // IMPORTANT
            // =========================

            Card(
                modifier = Modifier.fillMaxWidth(),

                shape = RoundedCornerShape(18.dp),

                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                )
            ) {

                Column(
                    modifier = Modifier.padding(18.dp)
                ) {

                    Text(
                        text = "Important",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )

                    Spacer(
                        modifier = Modifier.height(8.dp)
                    )

                    Text(
                        text =
                            "The test day and preferred slot shown here are DriveMate information/preferences. Saving this does not reserve an official RTO driving-test appointment.",
                        fontSize = 13.sp,
                        color = Color.DarkGray
                    )
                }
            }

            Spacer(
                modifier = Modifier.height(25.dp)
            )

            // =========================
            // SAVE FIREBASE
            // =========================

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

                    val vehicleClasses =
                        mutableListOf<String>()

                    if (carSelected) {
                        vehicleClasses.add("LMV")
                    }

                    if (bikeSelected) {
                        vehicleClasses.add("MCWG")
                    }

                    loading = true

                    val testSlot = hashMapOf(

                        "userId" to user.uid,

                        "rto" to selectedRto,

                        "testDay" to testDay,

                        "testDate" to testDate,

                        "testTime" to selectedTime,

                        "vehicleClasses" to
                                vehicleClasses,

                        "status" to "Preferred",

                        "createdAt" to
                                FieldValue.serverTimestamp()
                    )

                    db.collection(
                        "drivingTestSlots"
                    )
                        .add(testSlot)

                        .addOnSuccessListener {

                            loading = false

                            Toast.makeText(
                                context,
                                "Preferred slot saved",
                                Toast.LENGTH_SHORT
                            ).show()

                            navController
                                .popBackStack()
                        }

                        .addOnFailureListener {
                                exception ->

                            loading = false

                            Toast.makeText(
                                context,

                                exception.message
                                    ?: "Could not save slot",

                                Toast.LENGTH_LONG
                            ).show()
                        }
                },

                enabled =
                    !loading &&
                            selectedRto.isNotBlank() &&
                            dateValid &&
                            selectedTime.isNotBlank() &&
                            (carSelected || bikeSelected),

                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),

                shape = RoundedCornerShape(16.dp),

                colors = ButtonDefaults.buttonColors(
                    containerColor =
                        Color(0xFF1565C0),

                    contentColor = Color.White,

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
                            "Save Preferred Slot",

                        fontSize = 17.sp,

                        fontWeight =
                            FontWeight.Bold,

                        color = Color.White
                    )
                }
            }

            Spacer(
                modifier = Modifier.height(25.dp)
            )
        }
    }
}
