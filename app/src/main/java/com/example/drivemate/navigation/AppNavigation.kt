package com.example.drivemate.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.drivemate.screens.*
import com.google.firebase.auth.FirebaseAuth

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val auth = FirebaseAuth.getInstance()
    
    // Determine start destination based on login status
    val startDestination = if (auth.currentUser != null) "home" else "login"

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable("login") { LoginScreen(navController) }
        composable("signup") { SignUpScreen(navController) }
        composable("home") { HomeScreen(navController) }
        composable("profile") { ProfileScreen(navController) }
        composable("editProfile") { EditProfileScreen(navController) }
        composable("bookLesson") { BookLessonScreen(navController) }
        composable("myBookings") { MyBookingsScreen(navController) }
        composable("payments") { PaymentScreen(navController) }
        composable("licence") { LicenceScreen(navController) }
        composable("learnerLicence") { LearnerLicenceScreen(navController) }
        composable("drivingLicence") { DrivingLicenceScreen(navController) }
        composable("llDocuments") { LLDocumentsScreen(navController) }
        composable("dlDocuments") { DLDocumentsScreen(navController) }
        composable("drivingTestSlot") { DrivingTestSlotScreen(navController) }
        composable("bookingSuccess") { BookingSuccessScreen(navController) }
        composable("myTestBookings") { MyTestBookingsScreen(navController) }

        composable(
            route = "instructor/{date}/{time}/{vehicle}/{pickup}",
            arguments = listOf(
                navArgument("date") { type = NavType.StringType },
                navArgument("time") { type = NavType.StringType },
                navArgument("vehicle") { type = NavType.StringType },
                navArgument("pickup") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            InstructorScreen(
                navController = navController,
                date = backStackEntry.arguments?.getString("date") ?: "",
                time = backStackEntry.arguments?.getString("time") ?: "",
                vehicle = backStackEntry.arguments?.getString("vehicle") ?: "",
                pickup = backStackEntry.arguments?.getString("pickup") ?: ""
            )
        }

        composable(
            route = "paymentCheckout/{instructor}/{fee}/{date}/{time}/{vehicle}/{pickup}",
            arguments = listOf(
                navArgument("instructor") { type = NavType.StringType },
                navArgument("fee") { type = NavType.StringType },
                navArgument("date") { type = NavType.StringType },
                navArgument("time") { type = NavType.StringType },
                navArgument("vehicle") { type = NavType.StringType },
                navArgument("pickup") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            PaymentCheckoutScreen(
                navController = navController,
                instructorName = backStackEntry.arguments?.getString("instructor") ?: "",
                fee = backStackEntry.arguments?.getString("fee") ?: "",
                date = backStackEntry.arguments?.getString("date") ?: "",
                time = backStackEntry.arguments?.getString("time") ?: "",
                vehicle = backStackEntry.arguments?.getString("vehicle") ?: "",
                pickup = backStackEntry.arguments?.getString("pickup") ?: ""
            )
        }
    }
}
