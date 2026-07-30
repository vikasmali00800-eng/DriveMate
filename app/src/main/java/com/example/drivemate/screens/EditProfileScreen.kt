package com.example.drivemate.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProfileScreen(
    navController: NavController
) {

    val context = LocalContext.current
    val auth = FirebaseAuth.getInstance()
    val db = FirebaseFirestore.getInstance()
    val user = auth.currentUser

    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf(user?.email ?: "") }
    var mobile by remember { mutableStateOf("") }

    var loading by remember { mutableStateOf(true) }
    var saving by remember { mutableStateOf(false) }

    // Firestore se existing profile load karo
    LaunchedEffect(user?.uid) {

        if (user != null) {

            db.collection("users")
                .document(user.uid)
                .get()
                .addOnSuccessListener { document ->

                    name = document.getString("name") ?: ""
                    email = document.getString("email")
                        ?: user.email
                                ?: ""

                    mobile = document.getString("mobile") ?: ""

                    loading = false
                }
                .addOnFailureListener { exception ->

                    loading = false

                    Toast.makeText(
                        context,
                        "Profile load failed: ${exception.message}",
                        Toast.LENGTH_LONG
                    ).show()
                }

        } else {
            loading = false
        }
    }

    Scaffold(
        topBar = {

            TopAppBar(
                title = {
                    Text(
                        text = "Edit Profile",
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
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
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
                .padding(20.dp)
                .verticalScroll(rememberScrollState()),

            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Surface(
                modifier = Modifier.size(110.dp),
                shape = CircleShape,
                color = Color(0xFFE3F2FD)
            ) {

                Icon(
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = "Profile",
                    tint = Color(0xFF1565C0),
                    modifier = Modifier.padding(10.dp)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Edit your information",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(25.dp))

            if (loading) {

                CircularProgressIndicator()

            } else {

                // NAME
                OutlinedTextField(
                    value = name,
                    onValueChange = {
                        name = it
                    },

                    modifier = Modifier.fillMaxWidth(),

                    label = {
                        Text("Full Name")
                    },

                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = null
                        )
                    },

                    singleLine = true,
                    shape = RoundedCornerShape(16.dp),

                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = Color.Black,
                        unfocusedTextColor = Color.Black,
                        focusedLabelColor = Color(0xFF1565C0),
                        unfocusedLabelColor = Color.Gray,
                        focusedLeadingIconColor = Color(0xFF1565C0),
                        unfocusedLeadingIconColor = Color.Gray,
                        cursorColor = Color(0xFF1565C0),
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))

                // EMAIL
                OutlinedTextField(
                    value = email,

                    // Email abhi Firebase Auth wala hi rakhenge
                    onValueChange = {},

                    readOnly = true,

                    modifier = Modifier.fillMaxWidth(),

                    label = {
                        Text("Email Address")
                    },

                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Email,
                            contentDescription = null
                        )
                    },

                    singleLine = true,
                    shape = RoundedCornerShape(16.dp),

                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = Color.Black,
                        unfocusedTextColor = Color.Black,
                        focusedLabelColor = Color(0xFF1565C0),
                        unfocusedLabelColor = Color.Gray,
                        focusedLeadingIconColor = Color(0xFF1565C0),
                        unfocusedLeadingIconColor = Color.Gray,
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))

                // MOBILE
                OutlinedTextField(
                    value = mobile,

                    onValueChange = {
                        mobile = it
                    },

                    modifier = Modifier.fillMaxWidth(),

                    label = {
                        Text("Mobile Number")
                    },

                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Phone,
                            contentDescription = null
                        )
                    },

                    singleLine = true,
                    shape = RoundedCornerShape(16.dp),

                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = Color.Black,
                        unfocusedTextColor = Color.Black,
                        focusedLabelColor = Color(0xFF1565C0),
                        unfocusedLabelColor = Color.Gray,
                        focusedLeadingIconColor = Color(0xFF1565C0),
                        unfocusedLeadingIconColor = Color.Gray,
                        cursorColor = Color(0xFF1565C0),
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White
                    )
                )

                Spacer(modifier = Modifier.height(30.dp))

                Button(
                    onClick = {

                        if (name.isBlank()) {

                            Toast.makeText(
                                context,
                                "Enter your name",
                                Toast.LENGTH_SHORT
                            ).show()

                            return@Button
                        }

                        if (user == null) {

                            Toast.makeText(
                                context,
                                "Please login again",
                                Toast.LENGTH_SHORT
                            ).show()

                            return@Button
                        }

                        saving = true

                        val updates = mapOf(
                            "name" to name.trim(),
                            "email" to email,
                            "mobile" to mobile.trim()
                        )

                        db.collection("users")
                            .document(user.uid)
                            .set(updates)
                            .addOnSuccessListener {

                                saving = false

                                Toast.makeText(
                                    context,
                                    "Profile updated successfully",
                                    Toast.LENGTH_SHORT
                                ).show()

                                navController.popBackStack()
                            }
                            .addOnFailureListener { exception ->

                                saving = false

                                Toast.makeText(
                                    context,
                                    "Update failed: ${exception.message}",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                    },

                    enabled = !saving,

                    modifier = Modifier
                        .fillMaxWidth()
                        .height(55.dp),

                    shape = RoundedCornerShape(16.dp),

                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF1565C0)
                    )
                ) {

                    if (saving) {

                        CircularProgressIndicator(
                            modifier = Modifier.size(24.dp),
                            color = Color.White,
                            strokeWidth = 2.dp
                        )

                    } else {

                        Text(
                            text = "Save Changes",
                            fontSize = 17.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                }
            }
        }
    }
}