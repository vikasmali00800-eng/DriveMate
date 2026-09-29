package com.example.drivemate.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.drivemate.components.InstructorCard
import com.example.drivemate.components.WeatherWidget
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.example.drivemate.components.UpcomingTestCard


@Composable
fun HomeScreen(
    navController: NavController
) {

    val auth = FirebaseAuth.getInstance()
    val db = FirebaseFirestore.getInstance()
    val user = auth.currentUser

    var userName by remember {
        mutableStateOf("User")
    }

    var completedLessons by remember {
        mutableIntStateOf(0)
    }

    val totalLessons = 15

    val progress =
        (completedLessons.toFloat() / totalLessons.toFloat())
            .coerceIn(0f, 1f)

    val progressPercent =
        (progress * 100).toInt()

    var showContent by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(Unit) {
        showContent = true
    }

    // LOAD USER NAME
    LaunchedEffect(user?.uid) {

        if (user != null) {

            db.collection("users")
                .document(user.uid)
                .get()
                .addOnSuccessListener { document ->

                    userName =
                        document.getString("name") ?: "User"
                }
                .addOnFailureListener {

                    userName = "User"
                }
        }
    }

    // COMPLETED LESSONS
    DisposableEffect(user?.uid) {

        if (user == null) {

            completedLessons = 0

            onDispose { }

        } else {

            val listener =
                db.collection("bookings")
                    .whereEqualTo(
                        "userId",
                        user.uid
                    )
                    .addSnapshotListener { snapshot, error ->

                        if (
                            error == null &&
                            snapshot != null
                        ) {

                            completedLessons =
                                snapshot.documents.count { document ->

                                    document
                                        .getString("status")
                                        ?.equals(
                                            "Completed",
                                            ignoreCase = true
                                        ) == true
                                }
                        }
                    }

            onDispose {
                listener.remove()
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Color(0xFFF5F8FF)
            )
    ) {

        // =========================================
        // SCROLLABLE CONTENT
        // =========================================

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(
                    rememberScrollState()
                )
                .padding(20.dp)
        ) {

            // =========================================
            // TOP BAR
            // =========================================

            AnimatedVisibility(
                visible = showContent,
                enter =
                    fadeIn() +
                            slideInVertically()
            ) {

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement =
                        Arrangement.SpaceBetween,
                    verticalAlignment =
                        Alignment.CenterVertically
                ) {

                    IconButton(
                        onClick = { }
                    ) {

                        Icon(
                            imageVector =
                                Icons.Default.Menu,
                            contentDescription = "Menu",
                            tint =
                                Color(0xFF1565C0)
                        )
                    }

                    Text(
                        text = "DriveMate",
                        fontSize = 24.sp,
                        fontWeight =
                            FontWeight.Bold,
                        color =
                            Color(0xFF1565C0)
                    )

                    Row {

                        IconButton(
                            onClick = { }
                        ) {

                            Icon(
                                imageVector =
                                    Icons.Default.Notifications,
                                contentDescription =
                                    "Notifications",
                                tint =
                                    Color(0xFF1565C0)
                            )
                        }

                        IconButton(
                            onClick = {
                                navController.navigate(
                                    "profile"
                                )
                            }
                        ) {

                            Icon(
                                imageVector =
                                    Icons.Default.Person,
                                contentDescription =
                                    "Profile",
                                tint =
                                    Color(0xFF1565C0),
                                modifier =
                                    Modifier.size(42.dp)
                            )
                        }
                    }
                }
            }

            Spacer(
                modifier =
                    Modifier.height(25.dp)
            )

            // =========================================
            // WELCOME
            // =========================================

            Text(
                text = "Welcome Back 👋",
                fontSize = 18.sp,
                color = Color.Gray
            )

            Text(
                text = userName,
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            Text(
                text =
                    "Let's continue your driving journey today 🚗",
                color = Color.Gray
            )

            Spacer(
                modifier =
                    Modifier.height(25.dp)
            )

            // =========================================
            // PREMIUM BOOK LESSON
            // =========================================

            Card(
                modifier =
                    Modifier.fillMaxWidth(),
                shape =
                    RoundedCornerShape(28.dp),
                colors =
                    CardDefaults.cardColors(
                        containerColor =
                            Color.Transparent
                    )
            ) {

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            Brush.horizontalGradient(
                                listOf(
                                    Color(0xFF1565C0),
                                    Color(0xFF42A5F5),
                                    Color(0xFF64B5F6)
                                )
                            )
                        )
                        .padding(24.dp)
                ) {

                    Column {

                        Text(
                            text =
                                "🚗 DriveMate Premium",
                            color = Color.White,
                            fontSize = 14.sp
                        )

                        Spacer(
                            modifier =
                                Modifier.height(8.dp)
                        )

                        Text(
                            text =
                                "Book Your Driving Lesson",
                            color = Color.White,
                            fontSize = 24.sp,
                            fontWeight =
                                FontWeight.Bold
                        )

                        Spacer(
                            modifier =
                                Modifier.height(6.dp)
                        )

                        Text(
                            text =
                                "Professional instructors • Easy booking • Live tracking",
                            color =
                                Color.White.copy(
                                    alpha = 0.9f
                                )
                        )

                        Spacer(
                            modifier =
                                Modifier.height(20.dp)
                        )

                        Button(
                            onClick = {

                                navController.navigate(
                                    "bookLesson"
                                )
                            },
                            colors =
                                ButtonDefaults.buttonColors(
                                    containerColor =
                                        Color.White
                                ),
                            shape =
                                RoundedCornerShape(15.dp)
                        ) {

                            Text(
                                text = "Book Now",
                                color =
                                    Color(0xFF1565C0),
                                fontWeight =
                                    FontWeight.Bold
                            )
                        }
                    }
                }
            }

            Spacer(
                modifier =
                    Modifier.height(25.dp)
            )

            // =========================================
            // PROGRESS
            // =========================================

            Text(
                text = "My Progress",
                fontSize = 22.sp,
                fontWeight =
                    FontWeight.Bold,
                color = Color.Black
            )

            Spacer(
                modifier =
                    Modifier.height(12.dp)
            )

            LinearProgressIndicator(
                progress = {
                    progress
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(10.dp)
            )

            Spacer(
                modifier =
                    Modifier.height(8.dp)
            )

            Text(
                text =
                    "$progressPercent% Course Completed",
                color = Color.Gray
            )

            Spacer(
                modifier =
                    Modifier.height(25.dp)
            )

            // =========================================
            // QUICK ACTIONS
            // =========================================

            Text(
                text = "Quick Actions",
                fontSize = 22.sp,
                fontWeight =
                    FontWeight.Bold,
                color = Color.Black
            )

            Spacer(
                modifier =
                    Modifier.height(15.dp)
            )

            Row(
                modifier =
                    Modifier.fillMaxWidth(),
                horizontalArrangement =
                    Arrangement.spacedBy(12.dp)
            ) {

                QuickActionCard(
                    modifier =
                        Modifier.weight(1f),
                    emoji = "📅",
                    title = "Lessons",
                    subTitle =
                        "$completedLessons Completed"
                ) {

                    navController.navigate(
                        "myBookings"
                    )
                }

                QuickActionCard(
                    modifier =
                        Modifier.weight(1f),
                    emoji = "💳",
                    title = "Payments",
                    subTitle = "History"
                ) {

                    navController.navigate(
                        "payments"
                    )
                }
            }

            Spacer(
                modifier =
                    Modifier.height(12.dp)
            )

            Row(
                modifier =
                    Modifier.fillMaxWidth(),
                horizontalArrangement =
                    Arrangement.spacedBy(12.dp)
            ) {

                QuickActionCard(
                    modifier =
                        Modifier.weight(1f),
                    emoji = "🚦",
                    title = "LL Test",
                    subTitle =
                        "Mock Test"
                ) {

                    navController.navigate(
                        "llMockTest"
                    )
                }

                QuickActionCard(
                    modifier =
                        Modifier.weight(1f),
                    emoji = "🪪",
                    title = "Licence",
                    subTitle =
                        "Apply Now"
                ) {

                    navController.navigate(
                        "licence"
                    )
                }
            }

            Spacer(
                modifier =
                    Modifier.height(25.dp)
            )

            // =========================================
            // NEARBY INSTRUCTOR
            // =========================================

            InstructorCard(
                navController =
                    navController
            )

            Spacer(
                modifier =
                    Modifier.height(25.dp)
            )

            // =========================================
            // LIVE WEATHER
            // =========================================

            WeatherWidget()

            Spacer(
                modifier = Modifier.height(25.dp)
            )

            UpcomingTestCard(
                navController = navController
            )

            Spacer(
                modifier = Modifier.height(25.dp)
            )
        }

        // =========================================
        // BOTTOM NAVIGATION
        // =========================================

        NavigationBar {

            NavigationBarItem(
                selected = true,
                onClick = {},
                icon = {
                    Text("🏠")
                },
                label = {
                    Text("Home")
                }
            )

            NavigationBarItem(
                selected = false,
                onClick = {

                    navController.navigate(
                        "myBookings"
                    )
                },
                icon = {
                    Text("📅")
                },
                label = {
                    Text("Lessons")
                }
            )

            NavigationBarItem(
                selected = false,
                onClick = {},
                icon = {
                    Text("💬")
                },
                label = {
                    Text("Chat")
                }
            )

            NavigationBarItem(
                selected = false,
                onClick = {

                    navController.navigate(
                        "profile"
                    )
                },
                icon = {
                    Text("👤")
                },
                label = {
                    Text("Profile")
                }
            )
        }
    }
}

// =========================================
// QUICK ACTION CARD
// =========================================

@Composable
fun QuickActionCard(
    modifier: Modifier = Modifier,
    emoji: String,
    title: String,
    subTitle: String,
    onClick: () -> Unit
) {

    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable {
                onClick()
            },
        shape =
            RoundedCornerShape(20.dp),
        colors =
            CardDefaults.cardColors(
                containerColor =
                    Color.White
            ),
        elevation =
            CardDefaults.cardElevation(
                defaultElevation = 2.dp
            )
    ) {

        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalAlignment =
                Alignment.CenterHorizontally
        ) {

            Text(
                text = emoji,
                fontSize = 28.sp
            )

            Spacer(
                modifier =
                    Modifier.height(8.dp)
            )

            Text(
                text = title,
                fontWeight =
                    FontWeight.Bold,
                color = Color.Black,
                fontSize = 15.sp
            )

            Text(
                text = subTitle,
                color = Color.Gray,
                fontSize = 12.sp
            )
        }
    }
}