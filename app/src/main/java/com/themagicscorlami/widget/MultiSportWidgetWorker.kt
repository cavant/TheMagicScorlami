package com.themagicscorlami.widget

import android.content.Context
import androidx.glance.appwidget.updateAll
import androidx.work.*
import java.util.concurrent.TimeUnit

class MultiSportWidgetWorker(
    private val context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        return try {
            MultiSportWidget().updateAll(context)
            Result.success()
        } catch (_: Exception) {
            Result.retry()
        }
    }

    companion object {
        private const val WORK_NAME = "SportslamiWidgetPeriodicSync"

        fun enqueuePeriodicUpdate(context: Context) {
            val request = PeriodicWorkRequestBuilder<MultiSportWidgetWorker>(
                15, TimeUnit.MINUTES
            ).setConstraints(
                Constraints.Builder()
                    .setRequiredNetworkType(NetworkType.CONNECTED)
                    .build()
            ).build()

            WorkManager.getInstance(context).enqueueUniquePeriodicWork(
                WORK_NAME,
                ExistingPeriodicWorkPolicy.KEEP,
                request
            )
        }
    }
}
