package com.example.drivemate.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun HomeScreen(navController: NavController) {

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

    val progress = if (totalLessons > 0) {
        (completedLessons.toFloat() / totalLessons.toFloat())
            .coerceIn(0f, 1f)
    } else {
        0f
    }

    val progressPercent = (progress * 100).toInt()

    // Load user name
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

    // Listen for completed lessons
    DisposableEffect(user?.uid) {

        if (user == null) {

            completedLessons = 0

            onDispose { }

        } else {

            val listener = db.collection("bookings")
                .whereEqualTo("userId", user.uid)
                .addSnapshotListener { snapshot, error ->

                    if (error == null && snapshot != null) {

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
            .background(Color(0xFFF5F8FF))
    ) {

        // Scrollable Home Content
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(20.dp)
        ) {

            // TOP BAR
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = "Menu"
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Icon(
                        imageVector = Icons.Default.Notifications,
                        contentDescription = "Notifications"
                    )

                    Spacer(
                        modifier = Modifier.width(16.dp)
                    )

                    IconButton(
                        onClick = {
                            navController.navigate("profile")
                        }
                    ) {

                        Icon(
                            imageVector = Icons.Default.AccountCircle,
                            contentDescription = "Profile"
                        )
                    }
                }
            }

            Spacer(
                modifier = Modifier.height(25.dp)
            )

            // USER NAME
            Text(
                text = "Hi, $userName 👋",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            Text(
                text = "Ready for today's lesson?",
                color = Color.Gray
            )

            Spacer(
                modifier = Modifier.height(25.dp)
            )

            // BOOK LESSON
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(25.dp)
            ) {

                Box(
                    modifier = Modifier
                        .background(
                            Brush.horizontalGradient(
                                listOf(
                                    Color(0xFF1565C0),
                                    Color(0xFF42A5F5)
                                )
                            )
                        )
                        .padding(25.dp)
                ) {

                    Column {

                        Icon(
                            imageVector = Icons.Default.DirectionsCar,
                            contentDescription = null,
                            tint = Color.White
                        )

                        Spacer(
                            modifier = Modifier.height(12.dp)
                        )

                        Text(
                            text = "Book Driving Lesson",
                            color = Color.White,
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(
                            modifier = Modifier.height(6.dp)
                        )

                        Text(
                            text = "Find the best instructor near you",
                            color = Color.White.copy(alpha = 0.8f)
                        )

                        Spacer(
                            modifier = Modifier.height(18.dp)
                        )

                        Button(
                            onClick = {
                                navController.navigate("bookLesson")
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.White
                            ),
                            shape = RoundedCornerShape(12.dp)
                        ) {

                            Text(
                                text = "Book Now",
                                color = Color(0xFF1565C0),
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }

            Spacer(
                modifier = Modifier.height(25.dp)
            )

            // PROGRESS
            Text(
                text = "My Progress",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            Spacer(
                modifier = Modifier.height(12.dp)
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
                modifier = Modifier.height(8.dp)
            )

            Text(
                text = "$progressPercent% Course Completed",
                color = Color.Gray
            )

            Spacer(
                modifier = Modifier.height(25.dp)
            )

            // LESSONS + PAYMENTS
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {

                Card(
                    modifier = Modifier
                        .weight(1f)
                        .clickable {
                            navController.navigate("myBookings")
                        },
                    shape = RoundedCornerShape(20.dp)
                ) {

                    Column(
                        modifier = Modifier.padding(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        Text(
                            text = "📅",
                            fontSize = 30.sp
                        )

                        Spacer(
                            modifier = Modifier.height(8.dp)
                        )

                        Text(
                            text = "Lessons",
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )

                        Text(
                            text = "$completedLessons Completed",
                            color = Color.Gray,
                            fontSize = 13.sp
                        )
                    }
                }

                Card(
                    modifier = Modifier
                        .weight(1f)
                        .clickable {
                            navController.navigate("payments")
                        },
                    shape = RoundedCornerShape(20.dp)
                ) {

                    Column(
                        modifier = Modifier.padding(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        Text(
                            text = "💳",
                            fontSize = 30.sp
                        )

                        Spacer(
                            modifier = Modifier.height(8.dp)
                        )

                        Text(
                            text = "Payments",
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )

                        Text(
                            text = "View History",
                            color = Color.Gray,
                            fontSize = 13.sp
                        )
                    }
                }
            }

            Spacer(
                modifier = Modifier.height(25.dp)
            )

            // NEARBY INSTRUCTOR
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp)
            ) {

                Column(
                    modifier = Modifier.padding(20.dp)
                ) {

                    Text(
                        text = "Nearby Instructor",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        color = Color.Black
                    )

                    Spacer(
                        modifier = Modifier.height(10.dp)
                    )

                    Text(
                        text = "⭐ 4.9 Rating",
                        color = Color.Black
                    )

                    Text(
                        text = "Available Today",
                        color = Color.Black
                    )

                    Text(
                        text = "2 km Away",
                        color = Color.Black
                    )

                    Spacer(
                        modifier = Modifier.height(15.dp)
                    )

                    Button(
                        onClick = {
                            navController.navigate("bookLesson")
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {

                        Text("Book Instructor")
                    }
                }
            }

            Spacer(
                modifier = Modifier.height(16.dp)
            )

            // DRIVING LICENCE
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        navController.navigate("licence")
                    },
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                )
            ) {

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Text(
                        text = "🪪",
                        fontSize = 38.sp
                    )

                    Spacer(
                        modifier = Modifier.width(16.dp)
                    )

                    Column(
                        modifier = Modifier.weight(1f)
                    ) {

                        Text(
                            text = "Driving Licence",
                            fontSize = 19.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )

                        Spacer(
                            modifier = Modifier.height(3.dp)
                        )

                        Text(
                            text = "Apply for LL or Driving Licence",
                            fontSize = 13.sp,
                            color = Color.Gray
                        )
                    }

                    Text(
                        text = "›",
                        fontSize = 30.sp,
                        color = Color(0xFF1565C0)
                    )
                }
            }

            Spacer(
                modifier = Modifier.height(20.dp)
            )
        }

        // BOTTOM NAVIGATION
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
                    navController.navigate("myBookings")
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
                    navController.navigate("profile")
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