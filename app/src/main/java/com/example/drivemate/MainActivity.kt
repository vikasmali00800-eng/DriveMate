package com.example.drivemate

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.example.drivemate.navigation.AppNavigation
import com.example.drivemate.notifications.NotificationHelper
import com.example.drivemate.ui.theme.DriveMateTheme
import com.google.firebase.FirebaseApp

class MainActivity : ComponentActivity() {

    // Android 13+ notification permission launcher
    private val notificationPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted ->

            if (isGranted) {
                NotificationHelper.createNotificationChannel(this)
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize Firebase
        FirebaseApp.initializeApp(this)

        // Create notification channel
        NotificationHelper.createNotificationChannel(this)

        // Ask notification permission on Android 13+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {

            if (
                ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {

                notificationPermissionLauncher.launch(
                    Manifest.permission.POST_NOTIFICATIONS
                )
            }
        }

        enableEdgeToEdge()

        setContent {

            DriveMateTheme {

                AppNavigation()
            }
        }
    }
}