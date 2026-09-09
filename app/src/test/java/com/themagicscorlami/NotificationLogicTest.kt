package com.themagicscorlami

import com.themagicscorlami.data.local.*
import com.themagicscorlami.data.model.*
import org.junit.Assert.*
import org.junit.Test

class NotificationLogicTest {

    @Test
    fun testTeamNotificationConfigSerialization() {
        val config = TeamNotificationConfig(
            teamId = "145", // Ole Miss
            customSettingsEnabled = true,
            notifyGameStart = false,
            notifyScoreChange = true,
            notifyLeadChangeOnly = true,
            notifyHalftime = false,
            notifyFinal = true,
            notifyRedZone = true,
            pinLiveScoreboard = true,
            detailLevel = NotificationDetailLevel.DETAILED
        )

        val json = config.toJson()
        val parsed = TeamNotificationConfig.fromJson(json)

        assertEquals("145", parsed.teamId)
        assertTrue(parsed.customSettingsEnabled)
        assertFalse(parsed.notifyGameStart)
        assertTrue(parsed.notifyScoreChange)
        assertTrue(parsed.notifyLeadChangeOnly)
        assertFalse(parsed.notifyHalftime)
        assertTrue(parsed.notifyFinal)
        assertTrue(parsed.notifyRedZone)
        assertTrue(parsed.pinLiveScoreboard)
        assertEquals(NotificationDetailLevel.DETAILED, parsed.detailLevel)

        // Test map serializer
        val map = mapOf("145" to config)
        val serializedStr = TeamNotificationConfigSerializer.serializeMap(map)
        val deserializedMap = TeamNotificationConfigSerializer.deserializeMap(serializedStr)

        assertEquals(1, deserializedMap.size)
        assertEquals(config, deserializedMap["145"])
    }

    @Test
    fun testLeadChangeDetectionLogic() {
        // Trailing to Leading
        assertTrue(isLeadChange(prevFav = 14, prevOpp = 17, newFav = 21, newOpp = 17))
        // Trailing to Tied
        assertTrue(isLeadChange(prevFav = 14, prevOpp = 17, newFav = 17, newOpp = 17))
        // Leading to Trailing
        assertTrue(isLeadChange(prevFav = 21, prevOpp = 17, newFav = 21, newOpp = 24))
        // Leading to Tied
        assertTrue(isLeadChange(prevFav = 21, prevOpp = 17, newFav = 21, newOpp = 21))

        // Same leader extending lead (NOT a lead change)
        assertFalse(isLeadChange(prevFav = 21, prevOpp = 14, newFav = 28, newOpp = 14))
        // Trailing team scoring but still trailing (NOT a lead change)
        assertFalse(isLeadChange(prevFav = 7, prevOpp = 21, newFav = 14, newOpp = 21))
    }

    private fun isLeadChange(prevFav: Int, prevOpp: Int, newFav: Int, newOpp: Int): Boolean {
        return (prevFav <= prevOpp && newFav > newOpp) ||
               (prevFav >= prevOpp && newFav < newOpp) ||
               (prevFav != prevOpp && newFav == newOpp)
    }

    @Test
    fun testPerTeamConfigOverridesGlobal() {
        val globalPrefs = SportslamiUserPreferences(
            favoriteTeamIds = setOf("145", "96"),
            favoriteSportIds = setOf("cfb", "cbb"),
            themeMode = SportThemeMode.SALAMI,
            refreshIntervalSeconds = 20,
            stadiumDataSaver = false,
            globalGameStart = true,
            globalScoreChange = true,
            globalLeadChangeOnly = false,
            globalDetailLevel = NotificationDetailLevel.STANDARD
        )

        // Team 145 has custom override (wants Minimal detail, lead changes only)
        val customOleMiss = TeamNotificationConfig(
            teamId = "145",
            customSettingsEnabled = true,
            notifyLeadChangeOnly = true,
            detailLevel = NotificationDetailLevel.MINIMAL
        )

        // Resolve for 145 (custom override)
        val resolved145 = if (customOleMiss.customSettingsEnabled) customOleMiss else TeamNotificationConfig(
            teamId = "145",
            detailLevel = globalPrefs.globalDetailLevel
        )
        assertTrue(resolved145.notifyLeadChangeOnly)
        assertEquals(NotificationDetailLevel.MINIMAL, resolved145.detailLevel)

        // Team 96 has no custom override (inherits global)
        val config96 = TeamNotificationConfig(teamId = "96", customSettingsEnabled = false)
        val resolved96 = if (config96.customSettingsEnabled) config96 else TeamNotificationConfig(
            teamId = "96",
            notifyLeadChangeOnly = globalPrefs.globalLeadChangeOnly,
            detailLevel = globalPrefs.globalDetailLevel
        )
        assertFalse(resolved96.notifyLeadChangeOnly)
        assertEquals(NotificationDetailLevel.STANDARD, resolved96.detailLevel)
    }
}
