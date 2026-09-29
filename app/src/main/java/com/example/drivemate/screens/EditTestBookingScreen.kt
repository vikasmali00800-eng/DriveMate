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
import com.example.drivemate.notifications.ReminderScheduler
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditTestBookingScreen(
    navController: NavController,
    bookingId: String
) {

    val context = LocalContext.current
    val auth = FirebaseAuth.getInstance()
    val db = FirebaseFirestore.getInstance()

    var selectedRto by remember {
        mutableStateOf("")
    }

    var testDate by remember {
        mutableStateOf("")
    }

    var selectedTime by remember {
        mutableStateOf("")
    }

    var carSelected by remember {
        mutableStateOf(false)
    }

    var bikeSelected by remember {
        mutableStateOf(false)
    }

    var rtoExpanded by remember {
        mutableStateOf(false)
    }

    var loading by remember {
        mutableStateOf(true)
    }

    var updating by remember {
        mutableStateOf(false)
    }

    var errorMessage by remember {
        mutableStateOf<String?>(null)
    }

    // Same configuration as DrivingTestSlotScreen
    val rtoTestDays = linkedMapOf(
        "Vadodara RTO" to "Check with RTO",
        "Ahmedabad RTO" to "Check with RTO",
        "Surat RTO" to "Check with RTO",
        "Silvassa RTO" to "Wednesday"
    )

    val rtoOptions =
        rtoTestDays.keys.toList()

    val timeSlots = listOf(
        "09:00 AM",
        "10:00 AM",
        "11:00 AM",
        "02:00 PM",
        "03:00 PM"
    )

    val testDay =
        rtoTestDays[selectedRto] ?: ""

    // =========================
    // GET WEEKDAY FROM DATE
    // =========================

    fun getDayFromDate(
        date: String
    ): String? {

        if (date.length != 10) {
            return null
        }

        return try {

            val format = SimpleDateFormat(
                "dd/MM/yyyy",
                Locale.ENGLISH
            )

            format.isLenient = false

            val parsedDate =
                format.parse(date)
                    ?: return null

            val calendar =
                Calendar.getInstance()

            calendar.time =
                parsedDate

            SimpleDateFormat(
                "EEEE",
                Locale.ENGLISH
            ).format(calendar.time)

        } catch (e: Exception) {

            null
        }
    }

    // =========================
    // CHECK PAST DATE
    // =========================

    fun isPastDate(
        date: String
    ): Boolean {

        return try {

            val format = SimpleDateFormat(
                "dd/MM/yyyy",
                Locale.ENGLISH
            )

            format.isLenient = false

            val parsedDate =
                format.parse(date)
                    ?: return true

            val selectedCalendar =
                Calendar.getInstance()

            selectedCalendar.time =
                parsedDate

            selectedCalendar.set(
                Calendar.HOUR_OF_DAY,
                0
            )

            selectedCalendar.set(
                Calendar.MINUTE,
                0
            )

            selectedCalendar.set(
                Calendar.SECOND,
                0
            )

            selectedCalendar.set(
                Calendar.MILLISECOND,
                0
            )

            val today =
                Calendar.getInstance()

            today.set(
                Calendar.HOUR_OF_DAY,
                0
            )

            today.set(
                Calendar.MINUTE,
                0
            )

            today.set(
                Calendar.SECOND,
                0
            )

            today.set(
                Calendar.MILLISECOND,
                0
            )

            selectedCalendar.before(today)

        } catch (e: Exception) {

            true
        }
    }

    val enteredDateDay =
        getDayFromDate(testDate)

    val pastDate =
        if (
            testDate.length == 10 &&
            enteredDateDay != null
        ) {
            isPastDate(testDate)
        } else {
            false
        }

    val dateValid = when {

        testDate.length != 10 ->
            false

        enteredDateDay == null ->
            false

        pastDate ->
            false

        testDay.isBlank() ->
            false

        testDay == "Check with RTO" ->
            true

        else ->
            enteredDateDay.equals(
                testDay,
                ignoreCase = true
            )
    }

    // =========================
    // LOAD EXISTING BOOKING
    // =========================

    LaunchedEffect(bookingId) {

        val user =
            auth.currentUser

        if (user == null) {

            loading = false

            errorMessage =
                "Please login first."

            return@LaunchedEffect
        }

        db.collection(
            "drivingTestSlots"
        )
            .document(bookingId)
            .get()

            .addOnSuccessListener {
                    document ->

                if (!document.exists()) {

                    loading = false

                    errorMessage =
                        "Booking not found."

                    return@addOnSuccessListener
                }

                val ownerId =
                    document.getString(
                        "userId"
                    )

                if (ownerId != user.uid) {

                    loading = false

                    errorMessage =
                        "You cannot edit this booking."

                    return@addOnSuccessListener
                }

                selectedRto =
                    document.getString(
                        "rto"
                    ) ?: ""

                testDate =
                    document.getString(
                        "testDate"
                    ) ?: ""

                selectedTime =
                    document.getString(
                        "testTime"
                    ) ?: ""

                val vehicles =
                    (
                            document.get(
                                "vehicleClasses"
                            ) as? List<*>
                            )
                        ?.mapNotNull {
                            it?.toString()
                        }
                        ?: emptyList()

                carSelected =
                    vehicles.contains("LMV")

                bikeSelected =
                    vehicles.contains("MCWG")

                loading = false

                errorMessage = null
            }

            .addOnFailureListener {
                    exception ->

                loading = false

                errorMessage =
                    exception.message
                        ?: "Could not load booking."
            }
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
                        text = "Edit Test Booking",
                        fontWeight = FontWeight.Bold,
                        color = Color.White
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
                                Icons.AutoMirrored
                                    .Filled.ArrowBack,

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
                                Color(0xFF171719)
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
                            Color(0xFFF5F8FF)
                        )
                        .padding(padding),

                    contentAlignment =
                        Alignment.Center

                ) {

                    CircularProgressIndicator(
                        color =
                            Color(0xFF1565C0)
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
                            Color(0xFFF5F8FF)
                        )
                        .padding(padding)
                        .padding(20.dp)

                ) {

                    Text(
                        text =
                            "Unable to edit booking",

                        fontSize =
                            23.sp,

                        fontWeight =
                            FontWeight.Bold,

                        color =
                            Color.Black
                    )

                    Spacer(
                        modifier =
                            Modifier.height(10.dp)
                    )

                    Text(
                        text =
                            errorMessage ?: "",

                        color =
                            Color.DarkGray
                    )

                    Spacer(
                        modifier =
                            Modifier.height(24.dp)
                    )

                    Button(

                        onClick = {

                            navController
                                .popBackStack()
                        },

                        modifier =
                            Modifier.fillMaxWidth(),

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
                            text = "Go Back",
                            color = Color.White
                        )
                    }
                }
            }

            // =========================
            // EDIT FORM
            // =========================

            else -> {

                Column(

                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Color(0xFFF5F8FF)
                        )
                        .padding(padding)
                        .verticalScroll(
                            rememberScrollState()
                        )
                        .padding(20.dp)

                ) {

                    Text(
                        text =
                            "Update Test Details",

                        fontSize =
                            26.sp,

                        fontWeight =
                            FontWeight.Bold,

                        color =
                            Color.Black
                    )

                    Spacer(
                        modifier =
                            Modifier.height(5.dp)
                    )

                    Text(
                        text =
                            "Change your saved test preference below.",

                        color =
                            Color.DarkGray
                    )

                    Spacer(
                        modifier =
                            Modifier.height(24.dp)
                    )

                    // =========================
                    // RTO
                    // =========================

                    Text(
                        text =
                            "RTO / Testing Location",

                        fontSize =
                            17.sp,

                        fontWeight =
                            FontWeight.Bold,

                        color =
                            Color.Black
                    )

                    Spacer(
                        modifier =
                            Modifier.height(8.dp)
                    )

                    ExposedDropdownMenuBox(

                        expanded =
                            rtoExpanded,

                        onExpandedChange = {

                            if (!updating) {

                                rtoExpanded =
                                    !rtoExpanded
                            }
                        }

                    ) {

                        OutlinedTextField(

                            value =
                                selectedRto,

                            onValueChange = {},

                            readOnly = true,

                            enabled =
                                !updating,

                            label = {

                                Text(
                                    "Select RTO"
                                )
                            },

                            trailingIcon = {

                                ExposedDropdownMenuDefaults
                                    .TrailingIcon(

                                        expanded =
                                            rtoExpanded
                                    )
                            },

                            colors =
                                OutlinedTextFieldDefaults
                                    .colors(

                                        focusedTextColor =
                                            Color.Black,

                                        unfocusedTextColor =
                                            Color.Black,

                                        focusedLabelColor =
                                            Color(
                                                0xFF1565C0
                                            ),

                                        unfocusedLabelColor =
                                            Color.DarkGray,

                                        focusedContainerColor =
                                            Color.White,

                                        unfocusedContainerColor =
                                            Color.White
                                    ),

                            modifier =
                                Modifier
                                    .menuAnchor()
                                    .fillMaxWidth()
                        )

                        ExposedDropdownMenu(

                            expanded =
                                rtoExpanded,

                            onDismissRequest = {

                                rtoExpanded =
                                    false
                            },

                            containerColor =
                                Color.White

                        ) {

                            rtoOptions
                                .forEach { rto ->

                                    DropdownMenuItem(

                                        text = {

                                            Text(
                                                text = rto,
                                                color =
                                                    Color.Black
                                            )
                                        },

                                        onClick = {

                                            selectedRto =
                                                rto

                                            testDate =
                                                ""

                                            selectedTime =
                                                ""

                                            rtoExpanded =
                                                false
                                        }
                                    )
                                }
                        }
                    }

                    // =========================
                    // TEST DAY
                    // =========================

                    if (
                        selectedRto
                            .isNotBlank()
                    ) {

                        Spacer(
                            modifier =
                                Modifier.height(
                                    14.dp
                                )
                        )

                        Card(

                            modifier =
                                Modifier
                                    .fillMaxWidth(),

                            shape =
                                RoundedCornerShape(
                                    18.dp
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
                                        18.dp
                                    )

                            ) {

                                Text(
                                    text =
                                        "Driving Test Day",

                                    fontSize =
                                        13.sp,

                                    color =
                                        Color.DarkGray
                                )

                                Spacer(
                                    modifier =
                                        Modifier.height(
                                            4.dp
                                        )
                                )

                                Text(
                                    text =
                                        testDay,

                                    fontSize =
                                        21.sp,

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
                                            5.dp
                                        )
                                )

                                if (
                                    testDay ==
                                    "Check with RTO"
                                ) {

                                    Text(
                                        text =
                                            "Confirm the available driving-test day with the RTO.",

                                        fontSize =
                                            13.sp,

                                        color =
                                            Color.DarkGray
                                    )

                                } else {

                                    Text(
                                        text =
                                            "DriveMate is currently configured to show $testDay for this RTO.",

                                        fontSize =
                                            13.sp,

                                        color =
                                            Color.DarkGray
                                    )
                                }
                            }
                        }
                    }

                    Spacer(
                        modifier =
                            Modifier.height(22.dp)
                    )

                    // =========================
                    // DATE
                    // =========================

                    Text(
                        text =
                            "Test Date",

                        fontSize =
                            17.sp,

                        fontWeight =
                            FontWeight.Bold,

                        color =
                            Color.Black
                    )

                    Spacer(
                        modifier =
                            Modifier.height(8.dp)
                    )

                    OutlinedTextField(

                        value =
                            testDate,

                        onValueChange = {
                                input ->

                            val digits =
                                input
                                    .filter {
                                        it.isDigit()
                                    }
                                    .take(8)

                            testDate =
                                buildString {

                                    digits
                                        .forEachIndexed {
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

                            // Old time must be selected again
                            // when date changes.
                            selectedTime = ""
                        },

                        enabled =
                            !updating,

                        label = {

                            Text(
                                "DD/MM/YYYY"
                            )
                        },

                        keyboardOptions =
                            KeyboardOptions(

                                keyboardType =
                                    KeyboardType.Number
                            ),

                        singleLine =
                            true,

                        isError =
                            testDate.length == 10 &&
                                    !dateValid,

                        supportingText = {

                            when {

                                testDate.length == 10 &&
                                        enteredDateDay == null -> {

                                    Text(
                                        text =
                                            "Enter a valid date.",

                                        color =
                                            MaterialTheme
                                                .colorScheme
                                                .error
                                    )
                                }

                                testDate.length == 10 &&
                                        pastDate -> {

                                    Text(
                                        text =
                                            "Past date cannot be selected.",

                                        color =
                                            MaterialTheme
                                                .colorScheme
                                                .error
                                    )
                                }

                                testDate.length == 10 &&
                                        testDay !=
                                        "Check with RTO" &&
                                        enteredDateDay != null &&
                                        !dateValid -> {

                                    Text(
                                        text =
                                            "Selected date is $enteredDateDay. Please select a $testDay.",

                                        color =
                                            MaterialTheme
                                                .colorScheme
                                                .error
                                    )
                                }

                                testDate.length == 10 &&
                                        dateValid &&
                                        enteredDateDay != null -> {

                                    Text(
                                        text =
                                            "Selected day: $enteredDateDay",

                                        color =
                                            Color(
                                                0xFF2E7D32
                                            )
                                    )
                                }
                            }
                        },

                        colors =
                            OutlinedTextFieldDefaults
                                .colors(

                                    focusedTextColor =
                                        Color.Black,

                                    unfocusedTextColor =
                                        Color.Black,

                                    focusedLabelColor =
                                        Color(
                                            0xFF1565C0
                                        ),

                                    unfocusedLabelColor =
                                        Color.DarkGray,

                                    focusedContainerColor =
                                        Color.White,

                                    unfocusedContainerColor =
                                        Color.White,

                                    cursorColor =
                                        Color(
                                            0xFF1565C0
                                        )
                                ),

                        modifier =
                            Modifier.fillMaxWidth()
                    )

                    Spacer(
                        modifier =
                            Modifier.height(24.dp)
                    )

                    // =========================
                    // VEHICLE
                    // =========================

                    Text(
                        text =
                            "Vehicle Class",

                        fontSize =
                            17.sp,

                        fontWeight =
                            FontWeight.Bold,

                        color =
                            Color.Black
                    )

                    Text(
                        text =
                            "Select Car, Bike, or both",

                        fontSize =
                            13.sp,

                        color =
                            Color.DarkGray
                    )

                    Spacer(
                        modifier =
                            Modifier.height(8.dp)
                    )

                    Row(

                        modifier =
                            Modifier.fillMaxWidth(),

                        verticalAlignment =
                            Alignment.CenterVertically

                    ) {

                        Checkbox(

                            checked =
                                carSelected,

                            onCheckedChange = {

                                carSelected =
                                    it
                            },

                            enabled =
                                !updating
                        )

                        Text(
                            text =
                                "🚗 Car (LMV)",

                            color =
                                Color.Black
                        )
                    }

                    Row(

                        modifier =
                            Modifier.fillMaxWidth(),

                        verticalAlignment =
                            Alignment.CenterVertically

                    ) {

                        Checkbox(

                            checked =
                                bikeSelected,

                            onCheckedChange = {

                                bikeSelected =
                                    it
                            },

                            enabled =
                                !updating
                        )

                        Text(
                            text =
                                "🏍️ Bike (MCWG)",

                            color =
                                Color.Black
                        )
                    }

                    Spacer(
                        modifier =
                            Modifier.height(22.dp)
                    )

                    // =========================
                    // TIME
                    // =========================

                    Text(
                        text =
                            "Test Time",

                        fontSize =
                            17.sp,

                        fontWeight =
                            FontWeight.Bold,

                        color =
                            Color.Black
                    )

                    Spacer(
                        modifier =
                            Modifier.height(8.dp)
                    )

                    Card(

                        modifier =
                            Modifier.fillMaxWidth(),

                        shape =
                            RoundedCornerShape(
                                18.dp
                            ),

                        colors =
                            CardDefaults.cardColors(

                                containerColor =
                                    Color.White
                            )

                    ) {

                        Column(

                            modifier =
                                Modifier.padding(
                                    10.dp
                                )

                        ) {

                            timeSlots
                                .forEach { time ->

                                    Row(

                                        modifier =
                                            Modifier.fillMaxWidth(),

                                        verticalAlignment =
                                            Alignment
                                                .CenterVertically

                                    ) {

                                        RadioButton(

                                            selected =
                                                selectedTime ==
                                                        time,

                                            onClick = {

                                                selectedTime =
                                                    time
                                            },

                                            enabled =
                                                !updating &&
                                                        dateValid
                                        )

                                        Text(
                                            text =
                                                time,

                                            color =
                                                Color.Black
                                        )
                                    }
                                }
                        }
                    }

                    Spacer(
                        modifier =
                            Modifier.height(22.dp)
                    )

                    // =========================
                    // REMINDER INFO
                    // =========================

                    Card(

                        modifier =
                            Modifier.fillMaxWidth(),

                        shape =
                            RoundedCornerShape(
                                18.dp
                            ),

                        colors =
                            CardDefaults.cardColors(

                                containerColor =
                                    Color.White
                            )

                    ) {

                        Column(

                            modifier =
                                Modifier.padding(
                                    18.dp
                                )

                        ) {

                            Text(
                                text =
                                    "Reminder",

                                fontSize =
                                    17.sp,

                                fontWeight =
                                    FontWeight.Bold,

                                color =
                                    Color.Black
                            )

                            Spacer(
                                modifier =
                                    Modifier.height(
                                        6.dp
                                    )
                            )

                            Text(
                                text =
                                    "After updating, DriveMate will replace the old reminder with one based on your new test date and time.",

                                fontSize =
                                    13.sp,

                                color =
                                    Color.DarkGray
                            )
                        }
                    }

                    Spacer(
                        modifier =
                            Modifier.height(28.dp)
                    )

                    // =========================
                    // UPDATE BOOKING
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

                            val vehicles =
                                mutableListOf<String>()

                            if (carSelected) {

                                vehicles.add(
                                    "LMV"
                                )
                            }

                            if (bikeSelected) {

                                vehicles.add(
                                    "MCWG"
                                )
                            }

                            updating = true

                            val updates =
                                hashMapOf<String, Any>(

                                    "rto" to
                                            selectedRto,

                                    "testDay" to
                                            testDay,

                                    "testDate" to
                                            testDate,

                                    "testTime" to
                                            selectedTime,

                                    "vehicleClasses" to
                                            vehicles,

                                    "updatedAt" to
                                            FieldValue
                                                .serverTimestamp()
                                )

                            // Verify booking still belongs
                            // to current user.

                            db.collection(
                                "drivingTestSlots"
                            )
                                .document(
                                    bookingId
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

                                        updating = false

                                        Toast.makeText(
                                            context,
                                            "Booking could not be updated",
                                            Toast.LENGTH_SHORT
                                        ).show()

                                        return@addOnSuccessListener
                                    }

                                    document
                                        .reference
                                        .update(
                                            updates
                                        )

                                        .addOnSuccessListener {

                                            // =========================
                                            // RESCHEDULE REMINDER
                                            // =========================

                                            ReminderScheduler
                                                .scheduleTestReminder(

                                                    context =
                                                        context,

                                                    bookingId =
                                                        bookingId,

                                                    rto =
                                                        selectedRto,

                                                    testDate =
                                                        testDate,

                                                    testTime =
                                                        selectedTime
                                                )

                                            updating =
                                                false

                                            Toast.makeText(
                                                context,
                                                "Booking updated & reminder rescheduled",
                                                Toast.LENGTH_SHORT
                                            ).show()

                                            navController
                                                .popBackStack()
                                        }

                                        .addOnFailureListener {
                                                exception ->

                                            updating =
                                                false

                                            Toast.makeText(
                                                context,

                                                exception.message
                                                    ?: "Could not update booking",

                                                Toast.LENGTH_LONG
                                            ).show()
                                        }
                                }

                                .addOnFailureListener {
                                        exception ->

                                    updating =
                                        false

                                    Toast.makeText(
                                        context,

                                        exception.message
                                            ?: "Could not verify booking",

                                        Toast.LENGTH_LONG
                                    ).show()
                                }
                        },

                        enabled =
                            !updating &&
                                    selectedRto
                                        .isNotBlank() &&
                                    dateValid &&
                                    selectedTime
                                        .isNotBlank() &&
                                    (
                                            carSelected ||
                                                    bikeSelected
                                            ),

                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .height(
                                    56.dp
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
                                        ),

                                    contentColor =
                                        Color.White,

                                    disabledContainerColor =
                                        Color(
                                            0xFFBDBDBD
                                        ),

                                    disabledContentColor =
                                        Color.White
                                )

                    ) {

                        if (updating) {

                            CircularProgressIndicator(

                                modifier =
                                    Modifier.size(
                                        24.dp
                                    ),

                                color =
                                    Color.White,

                                strokeWidth =
                                    2.dp
                            )

                        } else {

                            Text(
                                text =
                                    "Update Booking",

                                fontSize =
                                    17.sp,

                                fontWeight =
                                    FontWeight.Bold,

                                color =
                                    Color.White
                            )
                        }
                    }

                    Spacer(
                        modifier =
                            Modifier.height(25.dp)
                    )
                }
            }
        }
    }
}