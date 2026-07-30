package com.example.drivemate.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Payment
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.Timestamp
import java.text.SimpleDateFormat
import java.util.Locale

data class PaymentHistoryItem(
    val instructorName: String = "",
    val fee: String = "",
    val paymentMethod: String = "",
    val status: String = "",
    val createdAt: Timestamp? = null
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PaymentScreen(navController: NavController) {

    val auth = FirebaseAuth.getInstance()
    val db = FirebaseFirestore.getInstance()
    val user = auth.currentUser

    var payments by remember {
        mutableStateOf<List<PaymentHistoryItem>>(emptyList())
    }

    var loading by remember {
        mutableStateOf(true)
    }

    var errorMessage by remember {
        mutableStateOf<String?>(null)
    }

    LaunchedEffect(user?.uid) {

        if (user == null) {
            loading = false
            errorMessage = "Please login to view payments."
            return@LaunchedEffect
        }

        db.collection("payments")
            .whereEqualTo("userId", user.uid)
            .orderBy("createdAt", Query.Direction.DESCENDING)
            .addSnapshotListener { snapshot, error ->

                if (error != null) {
                    loading = false
                    errorMessage = error.message
                    return@addSnapshotListener
                }

                payments = snapshot?.documents?.map { document ->

                    PaymentHistoryItem(
                        instructorName =
                            document.getString("instructorName") ?: "Instructor",

                        fee =
                            document.getString("fee") ?: "0",

                        paymentMethod =
                            document.getString("paymentMethod") ?: "-",

                        status =
                            document.getString("status") ?: "Pending",

                        createdAt =
                            document.getTimestamp("createdAt")
                    )

                } ?: emptyList()

                loading = false
                errorMessage = null
            }
    }

    Scaffold(
        topBar = {

            TopAppBar(
                title = {
                    Text(
                        text = "Payment History",
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

        when {

            loading -> {

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(0xFFF5F8FF))
                        .padding(padding),

                    contentAlignment = Alignment.Center
                ) {

                    CircularProgressIndicator()
                }
            }

            errorMessage != null -> {

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(0xFFF5F8FF))
                        .padding(padding)
                        .padding(20.dp),

                    contentAlignment = Alignment.Center
                ) {

                    Text(
                        text = errorMessage ?: "Something went wrong",
                        color = Color.Red
                    )
                }
            }

            payments.isEmpty() -> {

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(0xFFF5F8FF))
                        .padding(padding),

                    contentAlignment = Alignment.Center
                ) {

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        Text(
                            text = "💳",
                            fontSize = 50.sp
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        Text(
                            text = "No payments yet",
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(modifier = Modifier.height(6.dp))

                        Text(
                            text = "Your lesson payments will appear here.",
                            color = Color.Gray
                        )
                    }
                }
            }

            else -> {

                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(0xFFF5F8FF))
                        .padding(padding),

                    contentPadding = PaddingValues(20.dp),

                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {

                    item {

                        Text(
                            text = "Your Payments",
                            fontSize = 25.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = "${payments.size} transaction(s)",
                            color = Color.Gray
                        )

                        Spacer(modifier = Modifier.height(10.dp))
                    }

                    items(payments) { payment ->

                        PaymentHistoryCard(payment)
                    }
                }
            }
        }
    }
}

@Composable
fun PaymentHistoryCard(
    payment: PaymentHistoryItem
) {

    val dateText = payment.createdAt?.toDate()?.let { date ->

        SimpleDateFormat(
            "dd MMM yyyy, hh:mm a",
            Locale.getDefault()
        ).format(date)

    } ?: "Processing"

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),

        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {

        Column(
            modifier = Modifier.padding(20.dp)
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Icon(
                    imageVector = Icons.Default.Payment,
                    contentDescription = null,
                    tint = Color(0xFF1565C0)
                )

                Spacer(modifier = Modifier.width(12.dp))

                Column(
                    modifier = Modifier.weight(1f)
                ) {

                    Text(
                        text = payment.instructorName,
                        fontWeight = FontWeight.Bold,
                        fontSize = 17.sp,
                        color = Color.Black
                    )

                    Text(
                        text = payment.paymentMethod,
                        color = Color.Gray,
                        fontSize = 13.sp
                    )
                }

                Text(
                    text = "₹${payment.fee}",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            HorizontalDivider()

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    text = payment.status,
                    color = if (payment.status == "Paid") {
                        Color(0xFF2E7D32)
                    } else {
                        Color(0xFFF57C00)
                    },
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.weight(1f))

                Text(
                    text = dateText,
                    color = Color.Gray,
                    fontSize = 12.sp
                )
            }
        }
    }
}