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
fun DLDocumentsScreen(
    navController: NavController
) {

    val context = LocalContext.current

    var learnerLicenceReady by remember { mutableStateOf(false) }
    var ageAddressProofReady by remember { mutableStateOf(false) }
    var photoReady by remember { mutableStateOf(false) }

    val allReady =
        learnerLicenceReady &&
                ageAddressProofReady &&
                photoReady

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "DL Documents & Test",
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
                text = "Keep the required documents ready before continuing.",
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

                    // LEARNER LICENCE

                    Row {

                        Checkbox(
                            checked = learnerLicenceReady,
                            onCheckedChange = {
                                learnerLicenceReady = it
                            }
                        )

                        Column(
                            modifier = Modifier
                                .weight(1f)
                                .padding(top = 8.dp)
                        ) {

                            Text(
                                text = "Learner Licence",
                                fontWeight = FontWeight.Bold,
                                color = Color.Black
                            )

                            Text(
                                text = "Keep your valid Learner Licence details ready.",
                                fontSize = 13.sp,
                                color = Color.DarkGray
                            )
                        }
                    }

                    HorizontalDivider()

                    // IDENTITY / ADDRESS

                    Row {

                        Checkbox(
                            checked = ageAddressProofReady,
                            onCheckedChange = {
                                ageAddressProofReady = it
                            }
                        )

                        Column(
                            modifier = Modifier
                                .weight(1f)
                                .padding(top = 8.dp)
                        ) {

                            Text(
                                text = "Identity / Address Documents",
                                fontWeight = FontWeight.Bold,
                                color = Color.Black
                            )

                            Spacer(modifier = Modifier.height(4.dp))

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

                            Spacer(modifier = Modifier.height(4.dp))

                            Text(
                                text = "Accepted documents can vary by state/UT.",
                                fontSize = 12.sp,
                                color = Color.Gray
                            )
                        }
                    }

                    HorizontalDivider()

                    // PHOTOGRAPH

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

                            Text(
                                text = "Keep a recent photograph ready if required.",
                                fontSize = 13.sp,
                                color = Color.DarkGray
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(22.dp))

            // DRIVING TEST INFORMATION

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
                        text = "🚗 Driving Test",
                        fontSize = 19.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "1. Complete the DL application process.",
                        color = Color.DarkGray
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = "2. Select your preferred driving test details.",
                        color = Color.DarkGray
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = "3. Confirm the official test slot with the applicable licensing authority.",
                        color = Color.DarkGray
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = "4. Visit the applicable testing location for your driving test.",
                        color = Color.DarkGray
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = "5. Licence issuance is handled by the licensing authority after completion of the required process.",
                        color = Color.DarkGray
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "DriveMate can save your preferred test details. This does not itself reserve an official government driving-test slot.",
                fontSize = 12.sp,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(24.dp))

            // BOOK TEST SLOT

            Button(
                onClick = {
                    navController.navigate("drivingTestSlot")
                },

                enabled = allReady,

                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),

                shape = RoundedCornerShape(16.dp),

                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF1565C0)
                )
            ) {

                Text(
                    text = "Book Driving Test Slot",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // OFFICIAL DL APPLICATION

            OutlinedButton(
                onClick = {

                    val intent = Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse(
                            "https://sarathi.parivahan.gov.in/sarathiservice/stateSelection.do"
                        )
                    )

                    context.startActivity(intent)
                },

                enabled = allReady,

                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),

                shape = RoundedCornerShape(16.dp)
            ) {

                Text(
                    text = "Continue to Official DL Application",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(25.dp))
        }
    }
}
