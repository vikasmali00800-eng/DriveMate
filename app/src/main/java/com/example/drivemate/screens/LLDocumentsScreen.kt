package com.example.drivemate.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LLDocumentsScreen(
    navController: NavController
) {

    val context = LocalContext.current

    var ageProofReady by remember { mutableStateOf(false) }
    var addressProofReady by remember { mutableStateOf(false) }
    var photoReady by remember { mutableStateOf(false) }

    val allDocumentsReady =
        ageProofReady &&
                addressProofReady &&
                photoReady

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Documents & Review",
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
                .verticalScroll(rememberScrollState())
                .padding(20.dp)
        ) {

            Text(
                text = "Required Documents",
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "Confirm that you have the required documents ready.",
                color = Color.DarkGray
            )

            Spacer(modifier = Modifier.height(22.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),

                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                )
            ) {

                Column(
                    modifier = Modifier.padding(18.dp)
                ) {

                    // AGE PROOF

                    Row {

                        Checkbox(
                            checked = ageProofReady,

                            onCheckedChange = {
                                ageProofReady = it
                            }
                        )

                        Column(
                            modifier = Modifier
                                .weight(1f)
                                .padding(top = 8.dp)
                        ) {

                            Text(
                                text = "Proof of Age",
                                fontWeight = FontWeight.Bold,
                                color = Color.Black
                            )

                            Spacer(modifier = Modifier.height(3.dp))

                            Text(
                                text = "Keep an accepted age-proof document ready.",
                                fontSize = 13.sp,
                                color = Color.DarkGray
                            )

                            Spacer(modifier = Modifier.height(6.dp))

                            Text(
                                text = "Examples:",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black
                            )

                            Text(
                                text = "• Birth Certificate",
                                fontSize = 13.sp,
                                color = Color.DarkGray
                            )

                            Text(
                                text = "• Passport",
                                fontSize = 13.sp,
                                color = Color.DarkGray
                            )

                            Text(
                                text = "• School Certificate",
                                fontSize = 13.sp,
                                color = Color.DarkGray
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    HorizontalDivider()

                    Spacer(modifier = Modifier.height(12.dp))

                    // ADDRESS PROOF

                    Row {

                        Checkbox(
                            checked = addressProofReady,

                            onCheckedChange = {
                                addressProofReady = it
                            }
                        )

                        Column(
                            modifier = Modifier
                                .weight(1f)
                                .padding(top = 8.dp)
                        ) {

                            Text(
                                text = "Proof of Address",
                                fontWeight = FontWeight.Bold,
                                color = Color.Black
                            )

                            Spacer(modifier = Modifier.height(3.dp))

                            Text(
                                text = "Keep an accepted address-proof document ready.",
                                fontSize = 13.sp,
                                color = Color.DarkGray
                            )

                            Spacer(modifier = Modifier.height(6.dp))

                            Text(
                                text = "Examples:",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black
                            )

                            Text(
                                text = "• Aadhaar Card",
                                fontSize = 13.sp,
                                color = Color.DarkGray
                            )

                            Text(
                                text = "• Passport",
                                fontSize = 13.sp,
                                color = Color.DarkGray
                            )

                            Text(
                                text = "• Voter ID",
                                fontSize = 13.sp,
                                color = Color.DarkGray
                            )

                            Text(
                                text = "• Utility Bill, if accepted",
                                fontSize = 13.sp,
                                color = Color.DarkGray
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    HorizontalDivider()

                    Spacer(modifier = Modifier.height(12.dp))

                    // PHOTO

                    Row {

                        Checkbox(
                            checked = photoReady,

                            onCheckedChange = {
                                photoReady = it
                            }
                        )

                        Column(
                            modifier = Modifier
                                .weight(1f)
                                .padding(top = 8.dp)
                        ) {

                            Text(
                                text = "Photograph",
                                fontWeight = FontWeight.Bold,
                                color = Color.Black
                            )

                            Spacer(modifier = Modifier.height(3.dp))

                            Text(
                                text = "Recent passport-size photograph",
                                fontSize = 13.sp,
                                color = Color.DarkGray
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(22.dp))

            // APPLICATION PROCESS

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),

                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                )
            ) {

                Column(
                    modifier = Modifier.padding(18.dp)
                ) {

                    Text(
                        text = "Application Process",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "1. Confirm your details",
                        color = Color.DarkGray
                    )

                    Text(
                        text = "2. Keep the required documents ready",
                        color = Color.DarkGray
                    )

                    Text(
                        text = "3. Continue to the official application service",
                        color = Color.DarkGray
                    )

                    Text(
                        text = "4. Complete verification, fee and test steps as applicable",
                        color = Color.DarkGray
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Note: Accepted documents may vary by state/UT and application type. Verify the current requirements on the official transport portal.",
                fontSize = 12.sp,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(24.dp))

            // OFFICIAL SARATHI BUTTON

            Button(
                onClick = {

                    val intent = Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse(
                            "https://sarathi.parivahan.gov.in/sarathiservice/stateSelection.do"
                        )
                    )

                    context.startActivity(intent)
                },

                enabled = allDocumentsReady,

                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),

                shape = RoundedCornerShape(16.dp),

                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF1565C0)
                )
            ) {

                Text(
                    text = "Continue to Apply",
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}