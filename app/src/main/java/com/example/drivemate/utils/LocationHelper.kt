package com.example.drivemate.utils

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

class LocationHelper(
    private val context: Context
) {

    private val client =
        LocationServices.getFusedLocationProviderClient(context)

    @SuppressLint("MissingPermission")
    suspend fun getCurrentLocation(): Location? {

        return suspendCancellableCoroutine { continuation ->

            client.lastLocation
                .addOnSuccessListener { location ->

                    if (continuation.isActive) {
                        continuation.resume(location)
                    }
                }
                .addOnFailureListener {

                    if (continuation.isActive) {
                        continuation.resume(null)
                    }
                }
        }
    }
}