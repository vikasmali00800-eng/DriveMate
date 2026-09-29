package com.example.drivemate.notifications

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters

class LessonReminderWorker(
    appContext: Context,
    workerParams: WorkerParameters
) : Worker(
    appContext,
    workerParams
) {

    override fun doWork(): Result {

        return try {

            val instructorName =
                inputData.getString("instructorName")
                    ?: "Instructor"

            val date =
                inputData.getString("date")
                    ?: ""

            val time =
                inputData.getString("time")
                    ?: ""

            NotificationHelper.showLessonReminder(
                context = applicationContext,
                instructorName = instructorName,
                date = date,
                time = time
            )

            Result.success()

        } catch (e: Exception) {

            e.printStackTrace()

            Result.failure()
        }
    }
}