package com.example.drivemate.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun LLResultScreen(
    navController: NavController,
    score: Int
) {

    val totalQuestions = 20

    val percentage =
        ((score.toFloat() / totalQuestions) * 100).toInt()

    val passed = score >= 12

    val performance = when {

        percentage >= 90 -> "Excellent"

        percentage >= 75 -> "Very Good"

        percentage >= 60 -> "Good"

        percentage >= 40 -> "Average"

        else -> "Needs Improvement"
    }

    val bgColor =

        if (passed)

            Brush.verticalGradient(

                listOf(
                    Color(0xFF1565C0),
                    Color(0xFF42A5F5)
                )
            )

        else

            Brush.verticalGradient(

                listOf(
                    Color(0xFFD32F2F),
                    Color(0xFFE57373)
                )
            )

    Scaffold { paddingValues ->

        Column(

            modifier = Modifier
                .fillMaxSize()
                .background(bgColor)
                .padding(paddingValues)
                .padding(20.dp),

            horizontalAlignment =
                Alignment.CenterHorizontally

        ) {

            Spacer(
                modifier = Modifier.height(25.dp)
            )

            Surface(

                modifier =
                    Modifier.size(120.dp),

                shape = CircleShape,

                color = Color.White

            ) {

                Box(

                    contentAlignment =
                        Alignment.Center

                ) {

                    Text(

                        text =

                            if (passed)

                                "🏆"

                            else

                                "😔",

                        fontSize = 60.sp
                    )
                }
            }

            Spacer(
                modifier = Modifier.height(20.dp)
            )

            Text(

                text =

                    if (passed)

                        "CONGRATULATIONS"

                    else

                        "TEST FAILED",

                color = Color.White,

                fontWeight = FontWeight.Bold,

                fontSize = 30.sp,

                textAlign = TextAlign.Center
            )

            Spacer(
                modifier = Modifier.height(10.dp)
            )

            Text(

                text = performance,

                color = Color.White,

                fontSize = 18.sp
            )

            Spacer(
                modifier = Modifier.height(30.dp)
            )

            Card(

                modifier =
                    Modifier.fillMaxWidth(),

                shape =
                    RoundedCornerShape(24.dp)

            ) {

                Column(

                    modifier =
                        Modifier.padding(24.dp),

                    horizontalAlignment =
                        Alignment.CenterHorizontally

                ) {

                    Text(

                        text = "YOUR SCORE",

                        color = Color.Gray,

                        fontWeight =
                            FontWeight.Bold
                    )

                    Spacer(
                        modifier =
                            Modifier.height(15.dp)
                    )

                    Text(

                        text =
                            "$score / $totalQuestions",

                        fontSize = 42.sp,

                        fontWeight =
                            FontWeight.Bold,

                        color =
                            Color(0xFF1565C0)
                    )

                    Spacer(
                        modifier =
                            Modifier.height(10.dp)
                    )

                    LinearProgressIndicator(

                        progress = {
                            percentage / 100f
                        },

                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .height(10.dp)
                                .clip(
                                    RoundedCornerShape(50)
                                )
                    )

                    Spacer(
                        modifier =
                            Modifier.height(15.dp)
                    )

                    Text(

                        text =

                            "$percentage%",

                        fontSize = 24.sp,

                        fontWeight =
                            FontWeight.Bold
                    )
                    Spacer(
                        modifier = Modifier.height(25.dp)
                    )

                    Surface(

                        color =
                            if (passed)
                                Color(0xFF4CAF50)
                            else
                                Color(0xFFE53935),

                        shape =
                            RoundedCornerShape(50)
                    ) {

                        Text(

                            text =
                                if (passed)
                                    "PASS ✅"
                                else
                                    "FAIL ❌",

                            modifier =
                                Modifier.padding(
                                    horizontal = 24.dp,
                                    vertical = 10.dp
                                ),

                            color = Color.White,

                            fontWeight =
                                FontWeight.Bold,

                            fontSize = 18.sp
                        )
                    }

                    Spacer(
                        modifier = Modifier.height(20.dp)
                    )

                    Text(

                        text = "Performance",

                        fontWeight =
                            FontWeight.Bold,

                        fontSize = 18.sp
                    )

                    Spacer(
                        modifier = Modifier.height(10.dp)
                    )

                    Text(

                        text = when {

                            percentage >= 90 ->
                                "⭐⭐⭐⭐⭐"

                            percentage >= 75 ->
                                "⭐⭐⭐⭐"

                            percentage >= 60 ->
                                "⭐⭐⭐"

                            percentage >= 40 ->
                                "⭐⭐"

                            else ->
                                "⭐"
                        },

                        fontSize = 34.sp
                    )
                }
            }

            Spacer(
                modifier = Modifier.weight(1f)
            )

            Button(

                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),

                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White
                ),

                onClick = {

                    navController.navigate(
                        "llMockTest"
                    ) {

                        popUpTo(
                            "llMockTest"
                        ) {
                            inclusive = true
                        }
                    }
                }

            ) {

                Icon(
                    imageVector = Icons.Default.Refresh,
                    contentDescription = null,
                    tint = Color(0xFF1565C0)
                )

                Spacer(
                    modifier = Modifier.width(8.dp)
                )

                Text(

                    text = "Retry Test",

                    color = Color(0xFF1565C0),

                    fontWeight = FontWeight.Bold,

                    fontSize = 17.sp
                )
            }

            Spacer(
                modifier = Modifier.height(14.dp)
            )

            OutlinedButton(

                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),

                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = Color.White
                ),

                onClick = {

                    navController.navigate(
                        "home"
                    ) {

                        popUpTo("home") {
                            inclusive = false
                        }

                        launchSingleTop = true
                    }
                }

            ) {

                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = null
                )

                Spacer(
                    modifier = Modifier.width(8.dp)
                )

                Text(

                    text = "Back To Home",

                    fontWeight = FontWeight.Bold,

                    fontSize = 17.sp
                )
            }

            Spacer(
                modifier = Modifier.height(25.dp)
            )
        }
    }
}
