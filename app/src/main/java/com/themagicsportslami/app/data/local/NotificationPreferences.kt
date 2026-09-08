package com.themagicsportslami.app.data.local

import org.json.JSONArray
import org.json.JSONObject

enum class NotificationDetailLevel(val displayName: String, val description: String) {
    MINIMAL("Minimal", "Just scores & game clock (e.g. DAL 20, PHI 24 - 4th 2:00)"),
    STANDARD("Standard", "Scores, game clock & active situation (e.g. PHI 24, DAL 20 - 3rd & 4 at PHI 18)"),
    DETAILED("Detailed", "Scores, situation, betting odds & scoring leaders")
}

enum class OddsDisplayFormat(val displayName: String, val example: String) {
    AMERICAN("American", "-110, +150"),
    DECIMAL("Decimal", "1.91, 2.50"),
    FRACTIONAL("Fractional", "10/11, 3/2")
}

enum class CardDensityMode(val displayName: String, val description: String) {
    COMFORTABLE("Comfortable", "Full game cards with odds, situation, and broadcasts"),
    COMPACT("Compact", "Dense score strips for browsing lots of games rapidly")
}

data class TeamNotificationConfig(
    val teamId: String,
    val customSettingsEnabled: Boolean = false, // false = inherit global settings
    val notifyGameStart: Boolean = true,
    val notifyScoreChange: Boolean = true,
    val notifyLeadChangeOnly: Boolean = false,
    val notifyHalftime: Boolean = true,
    val notifyFinal: Boolean = true,
    val notifyRedZone: Boolean = true,
    val pinLiveScoreboard: Boolean = true,
    val detailLevel: NotificationDetailLevel = NotificationDetailLevel.STANDARD
) {
    fun toJson(): JSONObject {
        return JSONObject().apply {
            put("teamId", teamId)
            put("customSettingsEnabled", customSettingsEnabled)
            put("notifyGameStart", notifyGameStart)
            put("notifyScoreChange", notifyScoreChange)
            put("notifyLeadChangeOnly", notifyLeadChangeOnly)
            put("notifyHalftime", notifyHalftime)
            put("notifyFinal", notifyFinal)
            put("notifyRedZone", notifyRedZone)
            put("pinLiveScoreboard", pinLiveScoreboard)
            put("detailLevel", detailLevel.name)
        }
    }

    companion object {
        fun fromJson(obj: JSONObject): TeamNotificationConfig {
            val level = try {
                NotificationDetailLevel.valueOf(obj.optString("detailLevel", NotificationDetailLevel.STANDARD.name))
            } catch (_: Exception) {
                NotificationDetailLevel.STANDARD
            }

            return TeamNotificationConfig(
                teamId = obj.optString("teamId", ""),
                customSettingsEnabled = obj.optBoolean("customSettingsEnabled", false),
                notifyGameStart = obj.optBoolean("notifyGameStart", true),
                notifyScoreChange = obj.optBoolean("notifyScoreChange", true),
                notifyLeadChangeOnly = obj.optBoolean("notifyLeadChangeOnly", false),
                notifyHalftime = obj.optBoolean("notifyHalftime", true),
                notifyFinal = obj.optBoolean("notifyFinal", true),
                notifyRedZone = obj.optBoolean("notifyRedZone", true),
                pinLiveScoreboard = obj.optBoolean("pinLiveScoreboard", true),
                detailLevel = level
            )
        }
    }
}

object TeamNotificationConfigSerializer {
    fun serializeMap(configs: Map<String, TeamNotificationConfig>): String {
        val array = JSONArray()
        configs.values.forEach { config ->
            array.put(config.toJson())
        }
        return array.toString()
    }

    fun deserializeMap(jsonString: String?): Map<String, TeamNotificationConfig> {
        if (jsonString.isNullOrBlank()) return emptyMap()
        val map = mutableMapOf<String, TeamNotificationConfig>()
        try {
            val array = JSONArray(jsonString)
            for (i in 0 until array.length()) {
                val obj = array.optJSONObject(i) ?: continue
                val config = TeamNotificationConfig.fromJson(obj)
                if (config.teamId.isNotEmpty()) {
                    map[config.teamId] = config
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return map
    }
}
