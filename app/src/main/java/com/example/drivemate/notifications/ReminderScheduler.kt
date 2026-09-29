package com.example.drivemate.notifications

import android.content.Context
import androidx.work.Data
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.concurrent.TimeUnit

object ReminderScheduler {

    // =========================
    // TEST REMINDER
    // =========================

    fun scheduleTestReminder(
        context: Context,
        bookingId: String,
        rto: String,
        testDate: String,
        testTime: String
    ) {

        try {

            val dateTimeFormat = SimpleDateFormat(
                "dd/MM/yyyy hh:mm a",
                Locale.ENGLISH
            ).apply {
                isLenient = false
            }

            val testDateTime = dateTimeFormat.parse(
                "$testDate $testTime"
            ) ?: return

            val reminderCalendar =
                Calendar.getInstance().apply {

                    time = testDateTime

                    // Test reminder = 1 day before
                    add(
                        Calendar.DAY_OF_YEAR,
                        -1
                    )
                }

            val delay =
                reminderCalendar.timeInMillis -
                        System.currentTimeMillis()

            // Reminder time already passed
            if (delay <= 0) {
                return
            }

            val inputData =
                Data.Builder()
                    .putString(
                        "rto",
                        rto
                    )
                    .putString(
                        "date",
                        testDate
                    )
                    .putString(
                        "time",
                        testTime
                    )
                    .build()

            val reminderRequest =
                OneTimeWorkRequestBuilder<TestReminderWorker>()
                    .setInitialDelay(
                        delay,
                        TimeUnit.MILLISECONDS
                    )
                    .setInputData(
                        inputData
                    )
                    .build()

            WorkManager
                .getInstance(context)
                .enqueueUniqueWork(

                    "test_reminder_$bookingId",

                    ExistingWorkPolicy.REPLACE,

                    reminderRequest
                )

        } catch (e: Exception) {

            e.printStackTrace()
        }
    }

    // =========================
    // CANCEL TEST REMINDER
    // =========================

    fun cancelTestReminder(
        context: Context,
        bookingId: String
    ) {

        WorkManager
            .getInstance(context)
            .cancelUniqueWork(
                "test_reminder_$bookingId"
            )
    }

    // =========================
    // LESSON REMINDER
    // =========================

    fun scheduleLessonReminder(
        context: Context,
        bookingId: String,
        instructorName: String,
        lessonDate: String,
        lessonTime: String
    ) {

        try {

            val dateTimeFormat = SimpleDateFormat(
                "dd/MM/yyyy hh:mm a",
                Locale.ENGLISH
            ).apply {
                isLenient = false
            }

            val lessonDateTime =
                dateTimeFormat.parse(
                    "$lessonDate $lessonTime"
                ) ?: return

            val reminderCalendar =
                Calendar.getInstance().apply {

                    time = lessonDateTime

                    // Lesson reminder = 1 hour before
                    add(
                        Calendar.HOUR_OF_DAY,
                        -1
                    )
                }

            val delay =
                reminderCalendar.timeInMillis -
                        System.currentTimeMillis()

            // If 1-hour-before time has already passed,
            // don't schedule an outdated reminder.
            if (delay <= 0) {
                return
            }

            val inputData =
                Data.Builder()
                    .putString(
                        "instructorName",
                        instructorName
                    )
                    .putString(
                        "date",
                        lessonDate
                    )
                    .putString(
                        "time",
                        lessonTime
                    )
                    .build()

            val reminderRequest =
                OneTimeWorkRequestBuilder<LessonReminderWorker>()
                    .setInitialDelay(
                        delay,
                        TimeUnit.MILLISECONDS
                    )
                    .setInputData(
                        inputData
                    )
                    .build()

            WorkManager
                .getInstance(context)
                .enqueueUniqueWork(

                    "lesson_reminder_$bookingId",

                    ExistingWorkPolicy.REPLACE,

                    reminderRequest
                )

        } catch (e: Exception) {

            e.printStackTrace()
        }
    }

    // =========================
    // CANCEL LESSON REMINDER
    // =========================

    fun cancelLessonReminder(
        context: Context,
        bookingId: String
    ) {

        WorkManager
            .getInstance(context)
            .cancelUniqueWork(
                "lesson_reminder_$bookingId"
            )
    }
}