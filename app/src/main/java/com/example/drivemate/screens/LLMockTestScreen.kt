package com.example.drivemate.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.drivemate.screens.model.QuestionRepository
import com.example.drivemate.screens.model.Question

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LLMockTestScreen(
    navController: NavController
) {
    val questions = remember { QuestionRepository.getQuestions() }
    var currentQuestionIndex by remember { mutableIntStateOf(0) }
    var score by remember { mutableIntStateOf(0) }
    var selectedOption by remember { mutableStateOf<Int?>(null) }

    val currentQuestion = questions[currentQuestionIndex]

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Learner Licence Mock Test",
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
                    containerColor = Color(0xFF1565C0)
                )
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF5F8FF))
                .padding(padding)
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // Progress
            LinearProgressIndicator(
                progress = { (currentQuestionIndex + 1).toFloat() / questions.size },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp),
                color = Color(0xFF1565C0),
                trackColor = Color(0xFFE3F2FD)
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "Question ${currentQuestionIndex + 1} of ${questions.size}",
                fontSize = 14.sp,
                color = Color.Gray,
                fontWeight = FontWeight.Medium
            )

            Spacer(modifier = Modifier.height(30.dp))

            // Question Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp)
                ) {
                    Text(
                        text = currentQuestion.question,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Options
            currentQuestion.options.forEachIndexed { index, option ->
                val isSelected = selectedOption == index

                OutlinedButton(
                    onClick = {
                        selectedOption = index
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = if (isSelected) Color(0xFFE3F2FD) else Color.White,
                        contentColor = if (isSelected) Color(0xFF1565C0) else Color.Black
                    ),
                    border = if (isSelected) {
                        ButtonDefaults.outlinedButtonBorder.copy(
                            width = 2.dp
                        )
                    } else {
                        ButtonDefaults.outlinedButtonBorder
                    }
                ) {
                    Text(
                        text = option,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 12.dp, horizontal = 4.dp),
                        fontSize = 16.sp,
                        textAlign = androidx.compose.ui.text.style.TextAlign.Start
                    )
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            // Next / Finish Button
            Button(
                onClick = {
                    if (selectedOption == currentQuestion.correctAnswer) {
                        score++
                    }

                    if (currentQuestionIndex < questions.size - 1) {
                        currentQuestionIndex++
                        selectedOption = null
                    } else {
                        navController.navigate("llResult/$score") {
                            popUpTo("llMockTest") {
                                inclusive = true
                            }
                        }
                    }
                },
                enabled = selectedOption != null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF1565C0)
                )
            ) {
                Text(
                    text = if (currentQuestionIndex == questions.size - 1) "Finish Test" else "Next Question",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}
