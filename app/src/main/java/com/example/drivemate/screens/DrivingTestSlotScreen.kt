package com.example.drivemate.screens

import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
fun DrivingTestSlotScreen(
    navController: NavController
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
        mutableStateOf(false)
    }

    // =========================
    // RTO TEST DAYS
    // =========================

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
    // GET DAY FROM DATE
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
            ).apply {
                isLenient = false
            }

            val parsedDate =
                format.parse(date)
                    ?: return null

            SimpleDateFormat(
                "EEEE",
                Locale.ENGLISH
            ).format(parsedDate)

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
            ).apply {
                isLenient = false
            }

            val parsedDate =
                format.parse(date)
                    ?: return true

            val selectedCalendar =
                Calendar.getInstance().apply {

                    time = parsedDate

                    set(
                        Calendar.HOUR_OF_DAY,
                        0
                    )

                    set(
                        Calendar.MINUTE,
                        0
                    )

                    set(
                        Calendar.SECOND,
                        0
                    )

                    set(
                        Calendar.MILLISECOND,
                        0
                    )
                }

            val today =
                Calendar.getInstance().apply {

                    set(
                        Calendar.HOUR_OF_DAY,
                        0
                    )

                    set(
                        Calendar.MINUTE,
                        0
                    )

                    set(
                        Calendar.SECOND,
                        0
                    )

                    set(
                        Calendar.MILLISECOND,
                        0
                    )
                }

            selectedCalendar.before(today)

        } catch (e: Exception) {

            true
        }
    }

    val enteredDateDay =
        getDayFromDate(testDate)

    val pastDate =
        testDate.length == 10 &&
                enteredDateDay != null &&
                isPastDate(testDate)

    // =========================
    // DATE VALIDATION
    // =========================

    val dateValid = when {

        testDate.length != 10 ->
            false

        enteredDateDay == null ->
            false

        pastDate ->
            false

        selectedRto.isBlank() ->
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

    val timeSelectionEnabled =
        !loading &&
                selectedRto.isNotBlank() &&
                testDate.length == 10 &&
                enteredDateDay != null &&
                !pastDate

    // =========================
    // SCREEN
    // =========================

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

                colors =
                    TopAppBarDefaults.topAppBarColors(
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

            // =========================
            // TITLE
            // =========================

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

                    enabled = !loading,

                    label = {
                        Text("Select RTO")
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
                    modifier = Modifier.fillMaxWidth(),

                    shape =
                        RoundedCornerShape(18.dp),

                    colors =
                        CardDefaults.cardColors(
                            containerColor = Color.White
                        )
                ) {

                    Column(
                        modifier =
                            Modifier.padding(18.dp)
                    ) {

                        Text(
                            text = "Driving Test Day",
                            fontSize = 14.sp,
                            color = Color.DarkGray
                        )

                        Spacer(
                            modifier =
                                Modifier.height(4.dp)
                        )

                        Text(
                            text = testDay,
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF1565C0)
                        )

                        Spacer(
                            modifier =
                                Modifier.height(5.dp)
                        )

                        if (
                            testDay ==
                            "Check with RTO"
                        ) {

                            Text(
                                text =
                                    "Confirm the available driving test day with the RTO before saving.",

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

                    testDate =
                        buildString {

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

                    selectedTime = ""
                },

                enabled =
                    !loading &&
                            selectedRto.isNotBlank(),

                label = {
                    Text("DD/MM/YYYY")
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
                                    Color(0xFF2E7D32)
                            )
                        }
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

                modifier =
                    Modifier.fillMaxWidth()
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
                modifier =
                    Modifier.fillMaxWidth(),

                verticalAlignment =
                    Alignment.CenterVertically
            ) {

                Checkbox(
                    checked = carSelected,

                    onCheckedChange = {
                        carSelected = it
                    },

                    enabled = !loading,

                    colors =
                        CheckboxDefaults.colors(
                            checkedColor =
                                Color(0xFF1565C0),

                            uncheckedColor =
                                Color.DarkGray,

                            checkmarkColor =
                                Color.White
                        )
                )

                Text(
                    text = "🚗 Car (LMV)",
                    fontSize = 16.sp,
                    color = Color.Black
                )
            }

            Row(
                modifier =
                    Modifier.fillMaxWidth(),

                verticalAlignment =
                    Alignment.CenterVertically
            ) {

                Checkbox(
                    checked = bikeSelected,

                    onCheckedChange = {
                        bikeSelected = it
                    },

                    enabled = !loading,

                    colors =
                        CheckboxDefaults.colors(
                            checkedColor =
                                Color(0xFF1565C0),

                            uncheckedColor =
                                Color.DarkGray,

                            checkmarkColor =
                                Color.White
                        )
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
                modifier = Modifier.height(10.dp)
            )

            Column(
                verticalArrangement =
                    Arrangement.spacedBy(10.dp)
            ) {

                timeSlots.forEach { time ->

                    val isSelected =
                        selectedTime == time

                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable(
                                enabled =
                                    timeSelectionEnabled
                            ) {

                                selectedTime =
                                    time
                            },

                        shape =
                            RoundedCornerShape(14.dp),

                        color = when {

                            isSelected ->
                                Color(0xFF1565C0)

                            timeSelectionEnabled ->
                                Color.White

                            else ->
                                Color(0xFFE0E0E0)
                        },

                        tonalElevation =
                            if (isSelected) {
                                3.dp
                            } else {
                                1.dp
                            },

                        shadowElevation = 1.dp
                    ) {

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(
                                    horizontal = 16.dp,
                                    vertical = 12.dp
                                ),

                            verticalAlignment =
                                Alignment.CenterVertically
                        ) {

                            RadioButton(
                                selected = isSelected,

                                onClick = {

                                    selectedTime =
                                        time
                                },

                                enabled =
                                    timeSelectionEnabled,

                                colors =
                                    RadioButtonDefaults.colors(

                                        selectedColor =
                                            Color.White,

                                        unselectedColor =
                                            Color(0xFF1565C0),

                                        disabledSelectedColor =
                                            Color.DarkGray,

                                        disabledUnselectedColor =
                                            Color.DarkGray
                                    )
                            )

                            Spacer(
                                modifier =
                                    Modifier.width(10.dp)
                            )

                            Text(
                                text = time,

                                fontSize = 17.sp,

                                fontWeight =
                                    if (isSelected) {
                                        FontWeight.Bold
                                    } else {
                                        FontWeight.Medium
                                    },

                                color = when {

                                    isSelected ->
                                        Color.White

                                    timeSelectionEnabled ->
                                        Color.Black

                                    else ->
                                        Color.DarkGray
                                }
                            )

                            Spacer(
                                modifier =
                                    Modifier.weight(1f)
                            )

                            if (isSelected) {

                                Text(
                                    text = "✓",
                                    fontSize = 21.sp,
                                    fontWeight =
                                        FontWeight.Bold,
                                    color = Color.White
                                )
                            }
                        }
                    }
                }
            }

            if (!timeSelectionEnabled) {

                Spacer(
                    modifier = Modifier.height(8.dp)
                )

                Text(
                    text =
                        "Select an RTO and enter a valid future date to choose a time.",

                    fontSize = 12.sp,
                    color = Color.DarkGray
                )
            }

            Spacer(
                modifier = Modifier.height(22.dp)
            )

            // =========================
            // IMPORTANT
            // =========================

            Card(
                modifier =
                    Modifier.fillMaxWidth(),

                shape =
                    RoundedCornerShape(18.dp),

                colors =
                    CardDefaults.cardColors(
                        containerColor = Color.White
                    )
            ) {

                Column(
                    modifier =
                        Modifier.padding(18.dp)
                ) {

                    Text(
                        text = "Important",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )

                    Spacer(
                        modifier =
                            Modifier.height(8.dp)
                    )

                    Text(
                        text =
                            "The test day and preferred slot shown here are DriveMate information/preferences. Saving this does not reserve an official RTO driving-test appointment.",

                        fontSize = 13.sp,
                        color = Color.DarkGray
                    )

                    Spacer(
                        modifier =
                            Modifier.height(6.dp)
                    )

                    Text(
                        text =
                            "DriveMate will schedule a reminder for one day before your saved test preference.",

                        fontSize = 13.sp,
                        color = Color.DarkGray
                    )
                }
            }

            Spacer(
                modifier = Modifier.height(25.dp)
            )

            // =========================
            // SAVE
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

                    val vehicleClasses =
                        mutableListOf<String>()

                    if (carSelected) {
                        vehicleClasses.add("LMV")
                    }

                    if (bikeSelected) {
                        vehicleClasses.add("MCWG")
                    }

                    val vehicleDisplay =
                        when {

                            carSelected &&
                                    bikeSelected ->

                                "Car (LMV) & Bike (MCWG)"

                            carSelected ->
                                "Car (LMV)"

                            bikeSelected ->
                                "Bike (MCWG)"

                            else ->
                                ""
                        }

                    loading = true

                    val testSlot =
                        hashMapOf<String, Any>(

                            "userId" to
                                    user.uid,

                            "rto" to
                                    selectedRto,

                            "testDay" to
                                    testDay,

                            "testDate" to
                                    testDate,

                            "testTime" to
                                    selectedTime,

                            "vehicleClasses" to
                                    vehicleClasses,

                            "status" to
                                    "Preferred",

                            "createdAt" to
                                    FieldValue
                                        .serverTimestamp()
                        )

                    db
                        .collection(
                            "drivingTestSlots"
                        )
                        .add(testSlot)

                        .addOnSuccessListener {
                                documentReference ->

                            // =========================
                            // REMINDER
                            // =========================

                            ReminderScheduler
                                .scheduleTestReminder(

                                    context = context,

                                    bookingId =
                                        documentReference.id,

                                    rto =
                                        selectedRto,

                                    testDate =
                                        testDate,

                                    testTime =
                                        selectedTime
                                )

                            loading = false

                            Toast.makeText(
                                context,
                                "Test preference saved",
                                Toast.LENGTH_SHORT
                            ).show()

                            // =========================
                            // CONFIRMATION
                            // =========================

                            val encodedRto =
                                Uri.encode(
                                    selectedRto
                                )

                            val encodedDay =
                                Uri.encode(
                                    testDay
                                )

                            val encodedDate =
                                Uri.encode(
                                    testDate
                                )

                            val encodedTime =
                                Uri.encode(
                                    selectedTime
                                )

                            val encodedVehicle =
                                Uri.encode(
                                    vehicleDisplay
                                )

                            navController.navigate(

                                "testBookingConfirmation/" +
                                        "$encodedRto/" +
                                        "$encodedDay/" +
                                        "$encodedDate/" +
                                        "$encodedTime/" +
                                        encodedVehicle
                            )
                        }

                        .addOnFailureListener {
                                exception ->

                            loading = false

                            Toast.makeText(
                                context,

                                exception.message
                                    ?: "Could not save test preference",

                                Toast.LENGTH_LONG
                            ).show()
                        }
                },

                enabled =
                    !loading &&
                            selectedRto.isNotBlank() &&
                            dateValid &&
                            selectedTime.isNotBlank() &&
                            (
                                    carSelected ||
                                            bikeSelected
                                    ),

                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),

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
                        text = "Save Preferred Slot",
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }

            Spacer(
                modifier = Modifier.height(30.dp)
            )
        }
    }
}