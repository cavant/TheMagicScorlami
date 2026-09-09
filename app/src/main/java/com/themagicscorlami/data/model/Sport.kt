package com.themagicscorlami.data.model

enum class Sport(
    val id: String,
    val displayName: String,
    val shortName: String,
    val endpointPath: String?,
    val emoji: String
) {
    ALL("all", "All Scores", "ALL", null, "🏆"),
    FAVORITES("fav", "Favorites", "FAV", null, "⭐"),
    NFL("nfl", "NFL", "NFL", "football/nfl", "🏈"),
    CFB("cfb", "College Football (FBS)", "CFB", "football/college-football", "🏈"),
    CFB_FCS("cfb-fcs", "FCS Football", "FCS", "football/college-football?groups=81", "🏈"),
    CFB_D2("cfb-d2", "Division II Football", "D-II", "football/college-football?groups=104,107,108,110,112,116,118,127,129,135,136,139,144,146,156,157", "🏈"),
    CFB_D3("cfb-d3", "Division III Football", "D-III", "football/college-football?groups=100,102,103,106,111,113,114,115,117,119,120,121,122,123,124,126,128,130,131,138,143,147,148", "🏈"),
    NBA("nba", "NBA", "NBA", "basketball/nba", "🏀"),
    CBB("cbb", "College Basketball", "CBB", "basketball/mens-college-basketball", "🏀"),
    WNBA("wnba", "WNBA", "WNBA", "basketball/wnba", "🏀"),
    MLB("mlb", "MLB Baseball", "MLB", "baseball/mlb", "⚾"),
    NHL("nhl", "NHL Hockey", "NHL", "hockey/nhl", "🏒"),
    SOCCER_EPL("epl", "Premier League", "EPL", "soccer/eng.1", "⚽"),
    SOCCER_MLS("mls", "MLS Soccer", "MLS", "soccer/usa.1", "⚽"),
    SOCCER_UCL("ucl", "Champions League", "UCL", "soccer/uefa.champions", "🏆"),
    SOCCER_LALIGA("laliga", "La Liga", "ESP", "soccer/esp.1", "⚽"),
    MMA_UFC("ufc", "UFC / MMA", "UFC", "mma/ufc", "🥊"),
    COLLEGE_BASEBALL("college-baseball", "College Baseball", "NCAAB", "baseball/college-baseball", "⚾"),
    WCBB("wcbb", "Women's Basketball", "WCBB", "basketball/womens-college-basketball", "🏀"),
    F1("f1", "Formula 1", "F1", "racing/f1", "🏎️");

    val isLeague: Boolean
        get() = endpointPath != null

    val isFootball: Boolean
        get() = this == NFL || this == CFB || this == CFB_FCS || this == CFB_D2 || this == CFB_D3
}
