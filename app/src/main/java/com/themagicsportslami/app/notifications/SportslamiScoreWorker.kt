package com.themagicsportslami.app.notifications

import android.content.Context
import androidx.work.*
import com.themagicsportslami.app.TheMagicSportslamiApp
import com.themagicsportslami.app.data.model.Sport
import kotlinx.coroutines.flow.first
import java.util.concurrent.TimeUnit

class SportslamiScoreWorker(
    appContext: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result {
        return try {
            val app = applicationContext as? TheMagicSportslamiApp ?: TheMagicSportslamiApp.instance
            val userPrefs = app.preferences.preferencesFlow.first()

            if (userPrefs.notificationsMasterEnabled && userPrefs.favoriteTeamIds.isNotEmpty()) {
                val result = app.repository.getGames(Sport.FAVORITES)
                app.notificationManager.processGames(result.games, userPrefs)
            }

            Result.success()
        } catch (e: Exception) {
            e.printStackTrace()
            Result.retry()
        }
    }

    companion object {
        private const val WORK_NAME = "sportslami_score_worker"

        fun schedule(context: Context) {
            val constraints = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build()

            val workRequest = PeriodicWorkRequestBuilder<SportslamiScoreWorker>(15, TimeUnit.MINUTES)
                .setConstraints(constraints)
                .build()

            WorkManager.getInstance(context).enqueueUniquePeriodicWork(
                WORK_NAME,
                ExistingPeriodicWorkPolicy.KEEP,
                workRequest
            )
        }
    }
}
