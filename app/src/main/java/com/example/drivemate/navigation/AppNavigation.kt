package com.example.drivemate.navigation

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.drivemate.screens.*
import com.google.firebase.auth.FirebaseAuth

@SuppressLint("ComposableDestinationInComposeScope")
@Composable
fun AppNavigation() {

    val navController = rememberNavController()
    val auth = FirebaseAuth.getInstance()

    val startDestination = if (auth.currentUser != null) {
        "home"
    } else {
        "login"
    }

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {

        // =========================
        // LOGIN
        // =========================

        composable("login") {
            LoginScreen(
                navController = navController
            )
        }

        // =========================
        // SIGN UP
        // =========================

        composable("signup") {
            SignUpScreen(
                navController = navController
            )
        }

        // =========================
        // HOME
        // =========================

        composable("home") {
            HomeScreen(
                navController = navController
            )
        }

        // =========================
        // BOOK LESSON
        // =========================

        composable("bookLesson") {
            BookLessonScreen(
                navController = navController
            )
        }

        // =========================
        // LICENCE
        // =========================

        composable("licence") {
            LicenceScreen(
                navController = navController
            )
        }

        // LEARNER LICENCE

        composable("learnerLicence") {
            LearnerLicenceScreen(
                navController = navController
            )
        }

        // LL DOCUMENTS

        composable("llDocuments") {
            LLDocumentsScreen(
                navController = navController
            )
        }

        // DRIVING LICENCE

        composable("drivingLicence") {
            DrivingLicenceScreen(
                navController = navController
            )
        }

        // DL DOCUMENTS

        composable("dlDocuments") {
            DLDocumentsScreen(
                navController = navController
            )
        }

        // DRIVING TEST SLOT

        composable("drivingTestSlot") {
            DrivingTestSlotScreen(
                navController = navController
            )
        }

        // LL MOCK TEST

        composable("llMockTest") {
            LLMockTestScreen(
                navController = navController
            )
        }

        composable("llResult/{score}") { backStackEntry ->
            val score = backStackEntry.arguments?.getString("score")?.toIntOrNull() ?: 0
            LLResultScreen(
                navController = navController,
                score = score
            )
        }
        composable(
            route = "testBookingConfirmation/{rto}/{testDay}/{testDate}/{testTime}/{vehicle}"
        ) { backStackEntry ->

            val rto =
                backStackEntry.arguments?.getString("rto") ?: ""

            val testDay =
                backStackEntry.arguments?.getString("testDay") ?: ""

            val testDate =
                backStackEntry.arguments?.getString("testDate") ?: ""

            val testTime =
                backStackEntry.arguments?.getString("testTime") ?: ""

            val vehicle =
                backStackEntry.arguments?.getString("vehicle") ?: ""

            TestBookingConfirmationScreen(
                navController = navController,
                rto = rto,
                testDay = testDay,
                testDate = testDate,
                testTime = testTime,
                vehicle = vehicle
            )
        }

        // =========================
        // MY TEST BOOKINGS
        // =========================

        composable("myTestBookings") {
            MyTestBookingsScreen(
                navController = navController
            )
        }

        // =========================
        // EDIT TEST BOOKING
        // =========================

        composable(
            "editTestBooking/{bookingId}"
        ) { backStackEntry ->

            val bookingId =
                backStackEntry.arguments
                    ?.getString("bookingId")
                    ?: ""

            EditTestBookingScreen(
                navController = navController,
                bookingId = bookingId
            )
        }

        // =========================
        // INSTRUCTOR
        // =========================

        composable(
            "instructor/{date}/{time}/{vehicle}/{pickup}"
        ) { backStackEntry ->

            val date =
                backStackEntry.arguments
                    ?.getString("date")
                    ?: ""

            val time =
                backStackEntry.arguments
                    ?.getString("time")
                    ?: ""

            val vehicle =
                backStackEntry.arguments
                    ?.getString("vehicle")
                    ?: ""

            val pickup =
                backStackEntry.arguments
                    ?.getString("pickup")
                    ?: ""

            InstructorScreen(
                navController = navController,
                date = date,
                time = time,
                vehicle = vehicle,
                pickup = pickup
            )
        }

        // =========================
        // BOOKING SUCCESS
        // =========================

        composable("bookingSuccess") {
            BookingSuccessScreen(
                navController = navController
            )
        }

        // =========================
        // MY BOOKINGS
        // =========================

        composable("myBookings") {
            MyBookingsScreen(
                navController = navController
            )
        }

        // =========================
        // PROFILE
        // =========================

        composable("profile") {
            ProfileScreen(
                navController = navController
            )
        }

        // =========================
        // EDIT PROFILE
        // =========================

        composable("editProfile") {
            EditProfileScreen(
                navController = navController
            )
        }

        // =========================
        // PAYMENTS
        // =========================

        composable("payments") {
            PaymentScreen(
                navController = navController
            )
        }

        // =========================
        // PAYMENT CHECKOUT
        // =========================

        composable(
            "paymentCheckout/{instructorName}/{fee}/{date}/{time}/{vehicle}/{pickup}"
        ) { backStackEntry ->

            val instructorName =
                backStackEntry.arguments
                    ?.getString("instructorName")
                    ?: "Instructor"

            val fee =
                backStackEntry.arguments
                    ?.getString("fee")
                    ?: "0"

            val date =
                backStackEntry.arguments
                    ?.getString("date")
                    ?: ""

            val time =
                backStackEntry.arguments
                    ?.getString("time")
                    ?: ""

            val vehicle =
                backStackEntry.arguments
                    ?.getString("vehicle")
                    ?: ""

            val pickup =
                backStackEntry.arguments
                    ?.getString("pickup")
                    ?: ""

            PaymentCheckoutScreen(
                navController = navController,
                instructorName = instructorName,
                fee = fee,
                date = date,
                time = time,
                vehicle = vehicle,
                pickup = pickup
            )
        }
    }
}