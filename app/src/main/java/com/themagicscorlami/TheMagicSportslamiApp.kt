package com.themagicscorlami

import android.app.Application
import com.themagicscorlami.data.api.MultiSportHttpClient
import com.themagicscorlami.data.local.SportslamiPreferences
import com.themagicscorlami.data.repository.MultiSportRepository
import com.themagicscorlami.notifications.SportslamiNotificationManager
import com.themagicscorlami.notifications.SportslamiScoreWorker
import com.themagicscorlami.widget.MultiSportWidgetWorker

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
