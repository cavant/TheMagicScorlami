package com.themagicsportslami.app.data.model

data class FavoriteTeamEntity(
    val id: String,
    val name: String,
    val abbreviation: String,
    val sport: Sport,
    val logoUrl: String? = null,
    val colorHex: String? = null,
    val aliases: List<String> = emptyList()
) {
    val rawId: String
        get() = if (id.contains('_')) id.substringAfter('_') else id

    val scopedId: String
        get() = if (id.startsWith("${sport.id}_")) id else "${sport.id}_$id"

    fun ensureScoped(): FavoriteTeamEntity {
        return if (id.startsWith("${sport.id}_")) this else copy(id = "${sport.id}_$id")
    }

    fun matches(query: String): Boolean {
        val q = query.trim().lowercase()
        if (q.isEmpty()) return true
        return name.lowercase().contains(q) ||
                abbreviation.lowercase().contains(q) ||
                aliases.any { it.lowercase().contains(q) }
    }
}

data class FavoriteLeagueEntity(
    val sport: Sport,
    val name: String
)

data class ConferenceEntity(
    val id: String,
    val name: String,
    val shortName: String,
    val sport: Sport,
    val division: String? = null,
    val teamIds: Set<String> = emptySet(),
    val logoUrl: String? = null
) {
    fun matches(query: String): Boolean {
        val q = query.trim().lowercase()
        if (q.isEmpty()) return true
        return name.lowercase().contains(q) || shortName.lowercase().contains(q)
    }
}
