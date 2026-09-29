package com.example.drivemate.components

import android.Manifest
import android.location.Geocoder
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.drivemate.utils.LocationHelper
import com.example.drivemate.utils.PermissionHelper
import com.example.drivemate.viewmodel.WeatherViewModel
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.launch
import java.util.Locale

@Composable
fun WeatherWidget(
    weatherViewModel: WeatherViewModel = viewModel()
) {

    val context = LocalContext.current
    val weather by weatherViewModel.weather.collectAsState()
    val isLoading by weatherViewModel.isLoading.collectAsState()
    val error by weatherViewModel.error.collectAsState()

    var cityName by remember {
        mutableStateOf("Getting location...")
    }

    val scope = rememberCoroutineScope()

    fun loadLiveWeather() {

        scope.launch {

            val locationHelper =
                LocationHelper(context)

            val location =
                locationHelper.getCurrentLocation()

            if (location != null) {

                // Load live weather
                weatherViewModel.loadWeather(
                    latitude = location.latitude,
                    longitude = location.longitude
                )

                // Get city name
                try {

                    val geocoder =
                        Geocoder(
                            context,
                            Locale.getDefault()
                        )

                    @Suppress("DEPRECATION")
                    val addresses =
                        geocoder.getFromLocation(
                            location.latitude,
                            location.longitude,
                            1
                        )

                    if (!addresses.isNullOrEmpty()) {

                        cityName =
                            addresses[0]
                                .locality
                                ?: addresses[0]
                                    .subAdminArea
                                        ?: "Current Location"
                    }

                } catch (e: Exception) {

                    cityName = "Current Location"
                }

            } else {

                cityName = "Location unavailable"
            }
        }
    }

    val locationPermissionLauncher =
        rememberLauncherForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->

            val granted =
                permissions[
                    Manifest.permission.ACCESS_FINE_LOCATION
                ] == true ||
                        permissions[
                            Manifest.permission.ACCESS_COARSE_LOCATION
                        ] == true

            if (granted) {
                loadLiveWeather()
            }
        }

    LaunchedEffect(Unit) {

        if (
            PermissionHelper.hasLocationPermission(
                context
            )
        ) {

            loadLiveWeather()

        } else {

            locationPermissionLauncher.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )
        }
    }

    val currentWeather = weather?.current

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF2196F3)
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp
        )
    ) {

        Column(
            modifier = Modifier.padding(20.dp)
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement =
                    Arrangement.SpaceBetween,
                verticalAlignment =
                    Alignment.CenterVertically
            ) {

                Column {

                    Text(
                        text = "Live Weather",
                        color = Color.White,
                        fontSize = 14.sp
                    )

                    Spacer(
                        modifier = Modifier.height(5.dp)
                    )

                    Text(
                        text = "📍 $cityName",
                        color = Color.White,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Medium
                    )
                }

                Text(
                    text = "🌤️",
                    fontSize = 52.sp
                )
            }

            Spacer(
                modifier = Modifier.height(16.dp)
            )

            if (isLoading) {

                CircularProgressIndicator(
                    color = Color.White,
                    modifier = Modifier.size(28.dp)
                )

            } else if (currentWeather != null) {

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment =
                        Alignment.CenterVertically
                ) {

                    Text(
                        text =
                            "${currentWeather.temperature.toInt()}°C",
                        color = Color.White,
                        fontSize = 34.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(
                        modifier = Modifier.width(18.dp)
                    )

                    Column {

                        Text(
                            text =
                                "💧 ${currentWeather.humidity.toInt()}%",
                            color = Color.White
                        )

                        Text(
                            text =
                                "🌬 ${currentWeather.windSpeed.toInt()} km/h",
                            color = Color.White
                        )
                    }
                }

            } else {

                Text(
                    text =
                        error ?: "Weather unavailable",
                    color = Color.White
                )
            }
        }
    }
}