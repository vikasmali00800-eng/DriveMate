package com.example.drivemate.screens.model

object QuestionRepository {

    fun getQuestions(): List<Question> {

        return listOf(

            Question(
                question = "What does a RED traffic light mean?",
                options = listOf(
                    "Go",
                    "Stop",
                    "Turn Left",
                    "Speed Up"
                ),
                correctAnswer = 1
            ),

            Question(
                question = "Which side should you overtake from?",
                options = listOf(
                    "Left",
                    "Right",
                    "Any Side",
                    "Shoulder"
                ),
                correctAnswer = 1
            ),

            Question(
                question = "Seat belt is compulsory for?",
                options = listOf(
                    "Driver Only",
                    "Front Passenger Only",
                    "Everyone",
                    "Nobody"
                ),
                correctAnswer = 2
            ),

            Question(
                question = "Using a mobile phone while driving is?",
                options = listOf(
                    "Allowed",
                    "Safe",
                    "Illegal",
                    "Recommended"
                ),
                correctAnswer = 2
            ),

            Question(
                question = "What should you do before changing lanes?",
                options = listOf(
                    "Blow Horn",
                    "Speed Up",
                    "Check Mirrors & Indicator",
                    "Close Eyes"
                ),
                correctAnswer = 2
            ),

            Question(
                question = "What does a yellow traffic signal indicate?",
                options = listOf(
                    "Stop Immediately",
                    "Slow Down & Prepare to Stop",
                    "Go Fast",
                    "Parking Allowed"
                ),
                correctAnswer = 1
            ),

            Question(
                question = "What is the maximum speed in a school zone?",
                options = listOf(
                    "20 km/h",
                    "25 km/h",
                    "40 km/h",
                    "80 km/h"
                ),
                correctAnswer = 1
            ),

            Question(
                question = "Before starting your vehicle you should?",
                options = listOf(
                    "Check Mirrors",
                    "Wear Seat Belt",
                    "Start Engine",
                    "A & B Both"
                ),
                correctAnswer = 3
            ),

            Question(
                question = "Driving under the influence of alcohol is?",
                options = listOf(
                    "Allowed",
                    "Illegal",
                    "Recommended",
                    "Safe"
                ),
                correctAnswer = 1
            ),

            Question(
                question = "What does a STOP sign mean?",
                options = listOf(
                    "Slow Down",
                    "Complete Stop",
                    "Turn Right",
                    "Overtake"
                ),
                correctAnswer = 1
            ),

            Question(
                question = "You should use indicators when?",
                options = listOf(
                    "Turning",
                    "Changing Lane",
                    "Overtaking",
                    "All of the Above"
                ),
                correctAnswer = 3
            ),

            Question(
                question = "Which document is compulsory while driving?",
                options = listOf(
                    "Driving Licence",
                    "PAN Card",
                    "Passport",
                    "Voter ID"
                ),
                correctAnswer = 0
            ),

            Question(
                question = "Horn should NOT be used?",
                options = listOf(
                    "Near Hospital",
                    "Highway",
                    "Village",
                    "Market"
                ),
                correctAnswer = 0
            ),

            Question(
                question = "When should headlights be used?",
                options = listOf(
                    "Night",
                    "Heavy Rain",
                    "Fog",
                    "All of the Above"
                ),
                correctAnswer = 3
            ),

            Question(
                question = "A broken white line means?",
                options = listOf(
                    "No Overtaking",
                    "Lane Change Allowed",
                    "Parking Only",
                    "Stop"
                ),
                correctAnswer = 1
            ),

            Question(
                question = "If an ambulance approaches with siren?",
                options = listOf(
                    "Ignore",
                    "Give Way",
                    "Race",
                    "Block Road"
                ),
                correctAnswer = 1
            ),

            Question(
                question = "What should you do at a zebra crossing?",
                options = listOf(
                    "Increase Speed",
                    "Give Way to Pedestrians",
                    "Blow Horn",
                    "Overtake"
                ),
                correctAnswer = 1
            ),

            Question(
                question = "What is the purpose of rear-view mirrors?",
                options = listOf(
                    "Decoration",
                    "Check Traffic Behind",
                    "Increase Speed",
                    "Parking Only"
                ),
                correctAnswer = 1
            ),

            Question(
                question = "If your vehicle skids, you should?",
                options = listOf(
                    "Brake Hard",
                    "Steer Carefully",
                    "Close Eyes",
                    "Accelerate"
                ),
                correctAnswer = 1
            ),

            Question(
                question = "Who has priority at an uncontrolled intersection?",
                options = listOf(
                    "Largest Vehicle",
                    "Vehicle Already in Intersection",
                    "Fastest Vehicle",
                    "Bike Only"
                ),
                correctAnswer = 1
            )
        )
    }
}