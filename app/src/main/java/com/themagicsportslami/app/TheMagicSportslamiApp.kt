package com.themagicsportslami.app

import android.app.Application
import com.themagicsportslami.app.data.api.MultiSportHttpClient
import com.themagicsportslami.app.data.local.SportslamiPreferences
import com.themagicsportslami.app.data.repository.MultiSportRepository
import com.themagicsportslami.app.notifications.SportslamiNotificationManager
import com.themagicsportslami.app.notifications.SportslamiScoreWorker
import com.themagicsportslami.app.widget.MultiSportWidgetWorker

class TheMagicSportslamiApp : Application() {

    lateinit var preferences: SportslamiPreferences
        private set

    lateinit var repository: MultiSportRepository
        private set

    lateinit var notificationManager: SportslamiNotificationManager
        private set

    override fun onCreate() {
        super.onCreate()
        instance = this

        preferences = SportslamiPreferences(this)
        repository = MultiSportRepository(
            httpClient = MultiSportHttpClient(cacheDir),
            preferences = preferences
        )
        notificationManager = SportslamiNotificationManager(this)

        try {
            MultiSportWidgetWorker.enqueuePeriodicUpdate(this)
            SportslamiScoreWorker.schedule(this)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    companion object {
        lateinit var instance: TheMagicSportslamiApp
            private set
    }
}
