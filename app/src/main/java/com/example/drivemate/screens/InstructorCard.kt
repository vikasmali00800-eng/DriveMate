package com.example.drivemate.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun InstructorCard(
    name: String,
    rating: String,
    experience: String,
    distance: String,
    fee: String,
    onSelect: () -> Unit,
) {

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp)
    ) {

        Column(
            modifier = Modifier.padding(20.dp)
        ) {

            Text(
                text = "👨‍🏫 $name",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text("⭐ Rating : $rating")
            Text("🚗 Experience : $experience")
            Text("📍 Distance : $distance")
            Text("💰 Fee : $fee")

            Spacer(modifier = Modifier.height(18.dp))

            Button(
                onClick = onSelect,
                modifier = Modifier.align(Alignment.End),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF1565C0)
                )
            ) {
                Text("Select")
            }
        }
    }
}