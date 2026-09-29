package com.example.drivemate.notifications

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters

class TestReminderWorker(
    appContext: Context,
    workerParams: WorkerParameters
) : Worker(appContext, workerParams) {

    override fun doWork(): Result {

        val rto =
            inputData.getString("rto")
                ?: "RTO"

        val date =
            inputData.getString("date")
                ?: ""

        val time =
            inputData.getString("time")
                ?: ""

        NotificationHelper.showTestReminder(
            context = applicationContext,
            rto = rto,
            date = date,
            time = time
        )

        return Result.success()
    }
}