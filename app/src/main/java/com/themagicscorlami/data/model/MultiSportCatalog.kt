package com.themagicscorlami.data.model

object MultiSportCatalog {
    private val BASE_TEAMS = listOf(
        // NFL (All 32 teams)
        FavoriteTeamEntity("22", "Arizona Cardinals", "ARI", Sport.NFL, "https://a.espncdn.com/i/teamlogos/nfl/500/ari.png", "#a40227", listOf("ARI", "Arizona", "Cardinals")),
        FavoriteTeamEntity("1", "Atlanta Falcons", "ATL", Sport.NFL, "https://a.espncdn.com/i/teamlogos/nfl/500/atl.png", "#a71930", listOf("ATL", "Atlanta", "Falcons")),
        FavoriteTeamEntity("33", "Baltimore Ravens", "BAL", Sport.NFL, "https://a.espncdn.com/i/teamlogos/nfl/500/bal.png", "#29126f", listOf("BAL", "Baltimore", "Ravens")),
        FavoriteTeamEntity("2", "Buffalo Bills", "BUF", Sport.NFL, "https://a.espncdn.com/i/teamlogos/nfl/500/buf.png", "#00338d", listOf("BUF", "Bills", "Buffalo")),
        FavoriteTeamEntity("29", "Carolina Panthers", "CAR", Sport.NFL, "https://a.espncdn.com/i/teamlogos/nfl/500/car.png", "#0085ca", listOf("CAR", "Carolina", "Panthers")),
        FavoriteTeamEntity("3", "Chicago Bears", "CHI", Sport.NFL, "https://a.espncdn.com/i/teamlogos/nfl/500/chi.png", "#0b1c3a", listOf("Bears", "CHI", "Chicago")),
        FavoriteTeamEntity("4", "Cincinnati Bengals", "CIN", Sport.NFL, "https://a.espncdn.com/i/teamlogos/nfl/500/cin.png", "#fb4f14", listOf("Bengals", "CIN", "Cincinnati")),
        FavoriteTeamEntity("5", "Cleveland Browns", "CLE", Sport.NFL, "https://a.espncdn.com/i/teamlogos/nfl/500/cle.png", "#472a08", listOf("Browns", "CLE", "Cleveland")),
        FavoriteTeamEntity("6", "Dallas Cowboys", "DAL", Sport.NFL, "https://a.espncdn.com/i/teamlogos/nfl/500/dal.png", "#002a5c", listOf("Cowboys", "DAL", "Dallas")),
        FavoriteTeamEntity("7", "Denver Broncos", "DEN", Sport.NFL, "https://a.espncdn.com/i/teamlogos/nfl/500/den.png", "#0a2343", listOf("Broncos", "DEN", "Denver")),
        FavoriteTeamEntity("8", "Detroit Lions", "DET", Sport.NFL, "https://a.espncdn.com/i/teamlogos/nfl/500/det.png", "#0076b6", listOf("DET", "Detroit", "Lions")),
        FavoriteTeamEntity("9", "Green Bay Packers", "GB", Sport.NFL, "https://a.espncdn.com/i/teamlogos/nfl/500/gb.png", "#204e32", listOf("GB", "Green Bay", "Packers")),
        FavoriteTeamEntity("34", "Houston Texans", "HOU", Sport.NFL, "https://a.espncdn.com/i/teamlogos/nfl/500/hou.png", "#021018", listOf("HOU", "Houston", "Texans")),
        FavoriteTeamEntity("11", "Indianapolis Colts", "IND", Sport.NFL, "https://a.espncdn.com/i/teamlogos/nfl/500/ind.png", "#003b75", listOf("Colts", "IND", "Indianapolis")),
        FavoriteTeamEntity("30", "Jacksonville Jaguars", "JAX", Sport.NFL, "https://a.espncdn.com/i/teamlogos/nfl/500/jax.png", "#007487", listOf("JAX", "Jacksonville", "Jaguars")),
        FavoriteTeamEntity("12", "Kansas City Chiefs", "KC", Sport.NFL, "https://a.espncdn.com/i/teamlogos/nfl/500/kc.png", "#e31837", listOf("Chiefs", "KC", "Kansas City")),
        FavoriteTeamEntity("13", "Las Vegas Raiders", "LV", Sport.NFL, "https://a.espncdn.com/i/teamlogos/nfl/500/lv.png", "#000000", listOf("LV", "Las Vegas", "Raiders")),
        FavoriteTeamEntity("24", "Los Angeles Chargers", "LAC", Sport.NFL, "https://a.espncdn.com/i/teamlogos/nfl/500/lac.png", "#0080c6", listOf("Chargers", "LAC", "Los Angeles")),
        FavoriteTeamEntity("14", "Los Angeles Rams", "LAR", Sport.NFL, "https://a.espncdn.com/i/teamlogos/nfl/500/lar.png", "#003594", listOf("LAR", "Los Angeles", "Rams")),
        FavoriteTeamEntity("15", "Miami Dolphins", "MIA", Sport.NFL, "https://a.espncdn.com/i/teamlogos/nfl/500/mia.png", "#008e97", listOf("Canes", "Dolphins", "Hurricanes", "MIA", "Miami")),
        FavoriteTeamEntity("16", "Minnesota Vikings", "MIN", Sport.NFL, "https://a.espncdn.com/i/teamlogos/nfl/500/min.png", "#4f2683", listOf("MIN", "Minnesota", "Vikings")),
        FavoriteTeamEntity("17", "New England Patriots", "NE", Sport.NFL, "https://a.espncdn.com/i/teamlogos/nfl/500/ne.png", "#002a5c", listOf("NE", "New England", "Patriots")),
        FavoriteTeamEntity("18", "New Orleans Saints", "NO", Sport.NFL, "https://a.espncdn.com/i/teamlogos/nfl/500/no.png", "#d3bc8d", listOf("NO", "New Orleans", "Saints")),
        FavoriteTeamEntity("19", "New York Giants", "NYG", Sport.NFL, "https://a.espncdn.com/i/teamlogos/nfl/500/nyg.png", "#003c7f", listOf("Giants", "NYG", "New York")),
        FavoriteTeamEntity("20", "New York Jets", "NYJ", Sport.NFL, "https://a.espncdn.com/i/teamlogos/nfl/500/nyj.png", "#115740", listOf("Jets", "NYJ", "New York")),
        FavoriteTeamEntity("21", "Philadelphia Eagles", "PHI", Sport.NFL, "https://a.espncdn.com/i/teamlogos/nfl/500/phi.png", "#06424d", listOf("Eagles", "PHI", "Philadelphia")),
        FavoriteTeamEntity("23", "Pittsburgh Steelers", "PIT", Sport.NFL, "https://a.espncdn.com/i/teamlogos/nfl/500/pit.png", "#000000", listOf("PIT", "Panthers", "Pittsburgh", "Steelers")),
        FavoriteTeamEntity("25", "San Francisco 49ers", "SF", Sport.NFL, "https://a.espncdn.com/i/teamlogos/nfl/500/sf.png", "#aa0000", listOf("49ers", "SF", "San Francisco")),
        FavoriteTeamEntity("26", "Seattle Seahawks", "SEA", Sport.NFL, "https://a.espncdn.com/i/teamlogos/nfl/500/sea.png", "#002a5c", listOf("SEA", "Seahawks", "Seattle")),
        FavoriteTeamEntity("27", "Tampa Bay Buccaneers", "TB", Sport.NFL, "https://a.espncdn.com/i/teamlogos/nfl/500/tb.png", "#bd1c36", listOf("Buccaneers", "TB", "Tampa Bay")),
        FavoriteTeamEntity("10", "Tennessee Titans", "TEN", Sport.NFL, "https://a.espncdn.com/i/teamlogos/nfl/500/ten.png", "#4495d2", listOf("TEN", "Tennessee", "Titans")),
        FavoriteTeamEntity("28", "Washington Commanders", "WSH", Sport.NFL, "https://a.espncdn.com/i/teamlogos/nfl/500/wsh.png", "#5a1414", listOf("Commanders", "WSH", "Washington")),

        // College Football (FBS & Major Programs)
        FavoriteTeamEntity("2005", "Air Force Falcons", "AF", Sport.CFB, "https://a.espncdn.com/i/teamlogos/ncaa/500/2005.png", "#003594", listOf("AF", "Air Force")),
        FavoriteTeamEntity("2006", "Akron Zips", "AKR", Sport.CFB, "https://a.espncdn.com/i/teamlogos/ncaa/500/2006.png", "#041e42", listOf("AKR", "Akron")),
        FavoriteTeamEntity("2010", "Alabama A&M Bulldogs", "AAMU", Sport.CFB, "https://a.espncdn.com/i/teamlogos/ncaa/500/2010.png", "#790000", listOf("AAMU", "Alabama A&M")),
        FavoriteTeamEntity("333", "Alabama Crimson Tide", "ALA", Sport.CFB, "https://a.espncdn.com/i/teamlogos/ncaa/500/333.png", "#9e1b32", listOf("ALA", "Alabama")),
        FavoriteTeamEntity("2011", "Alabama State Hornets", "ALST", Sport.CFB, "https://a.espncdn.com/i/teamlogos/ncaa/500/2011.png", "#e9a900", listOf("ALST", "Alabama St", "Alabama State")),
        FavoriteTeamEntity("108358", "Arizona Christian Firestorm", "AZCH", Sport.CFB, "", "#333333", listOf("AZ Christian", "AZCH", "Arizona Christian")),
        FavoriteTeamEntity("9", "Arizona State Sun Devils", "ASU", Sport.CFB, "https://a.espncdn.com/i/teamlogos/ncaa/500/9.png", "#ffc627", listOf("ASU", "Arizona St", "Arizona State")),
        FavoriteTeamEntity("12", "Arizona Wildcats", "ARIZ", Sport.CFB, "https://a.espncdn.com/i/teamlogos/ncaa/500/12.png", "#cc0033", listOf("ARIZ", "Arizona")),
        FavoriteTeamEntity("124386", "Arkansas Baptist Buffaloes", "ARBA", Sport.CFB, "", "#333333", listOf("ARBA", "Ark Baptist", "Arkansas Baptist")),
        FavoriteTeamEntity("2028", "Arkansas Monticello Boll Weevils", "UAM", Sport.CFB, "https://a.espncdn.com/i/teamlogos/ncaa/500/2028.png", "#000000", listOf("Arkansas Monticello", "UA Monticello", "UAM")),
        FavoriteTeamEntity("8", "Arkansas Razorbacks", "ARK", Sport.CFB, "https://a.espncdn.com/i/teamlogos/ncaa/500/8.png", "#a32136", listOf("ARK", "Arkansas")),
        FavoriteTeamEntity("2032", "Arkansas State Red Wolves", "ARST", Sport.CFB, "https://a.espncdn.com/i/teamlogos/ncaa/500/2032.png", "#cc092f", listOf("ARST", "Arkansas St", "Arkansas State")),
        FavoriteTeamEntity("2033", "Arkansas Tech Wonder Boys", "ARTE", Sport.CFB, "https://a.espncdn.com/i/teamlogos/ncaa/500/2033.png", "#000000", listOf("ARTE", "Arkansas Tech")),
        FavoriteTeamEntity("2029", "Arkansas-Pine Bluff Golden Lions", "UAPB", Sport.CFB, "https://a.espncdn.com/i/teamlogos/ncaa/500/2029.png", "#e0aa0f", listOf("AR-Pine Bluff", "Arkansas-Pine Bluff", "UAPB")),
        FavoriteTeamEntity("349", "Army Black Knights", "ARMY", Sport.CFB, "https://a.espncdn.com/i/teamlogos/ncaa/500/349.png", "#000000", listOf("ARMY", "Army")),
        FavoriteTeamEntity("2", "Auburn Tigers", "AUB", Sport.CFB, "https://a.espncdn.com/i/teamlogos/ncaa/500/2.png", "#002b5c", listOf("AUB", "Auburn")),
        FavoriteTeamEntity("252", "BYU Cougars", "BYU", Sport.CFB, "https://a.espncdn.com/i/teamlogos/ncaa/500/252.png", "#0047ba", listOf("BYU", "Brigham Young", "Cougars")),
        FavoriteTeamEntity("2050", "Ball State Cardinals", "BALL", Sport.CFB, "https://a.espncdn.com/i/teamlogos/ncaa/500/2050.png", "#ba0c2f", listOf("BALL", "Ball State")),
        FavoriteTeamEntity("239", "Baylor Bears", "BAY", Sport.CFB, "https://a.espncdn.com/i/teamlogos/ncaa/500/239.png", "#154734", listOf("BAY", "Baylor")),
        FavoriteTeamEntity("2064", "Bethel University Tennessee Wildcats", "BETHTN", Sport.CFB, "https://a.espncdn.com/i/teamlogos/ncaa/500/2064.png", "#000000", listOf("BETHTN", "Bethel TN", "Bethel University Tennessee")),
        FavoriteTeamEntity("68", "Boise State Broncos", "BOIS", Sport.CFB, "https://a.espncdn.com/i/teamlogos/ncaa/500/68.png", "#0033a0", listOf("BOIS", "Boise St", "Boise State")),
        FavoriteTeamEntity("103", "Boston College Eagles", "BC", Sport.CFB, "https://a.espncdn.com/i/teamlogos/ncaa/500/103.png", "#8c2232", listOf("BC", "Boston College")),
        FavoriteTeamEntity("189", "Bowling Green Falcons", "BGSU", Sport.CFB, "https://a.espncdn.com/i/teamlogos/ncaa/500/189.png", "#fd5000", listOf("BGSU", "Bowling Green")),
        FavoriteTeamEntity("2084", "Buffalo Bulls", "BUF", Sport.CFB, "https://a.espncdn.com/i/teamlogos/ncaa/500/2084.png", "#005bbb", listOf("BUF", "Buffalo")),
        FavoriteTeamEntity("2085", "Buffalo State Bengals", "BSU", Sport.CFB, "https://a.espncdn.com/i/teamlogos/ncaa/500/2085.png", "#000000", listOf("BSU", "Buffalo St", "Buffalo State")),
        FavoriteTeamEntity("2858", "California (PA) Vulcans", "CAPA", Sport.CFB, "https://a.espncdn.com/i/teamlogos/ncaa/500/2858.png", "#000000", listOf("CAPA", "Cal (PA)", "California (PA)")),
        FavoriteTeamEntity("25", "California Golden Bears", "CAL", Sport.CFB, "https://a.espncdn.com/i/teamlogos/ncaa/500/25.png", "#041e42", listOf("CAL", "California")),
        FavoriteTeamEntity("2110", "Central Arkansas Bears", "CARK", Sport.CFB, "https://a.espncdn.com/i/teamlogos/ncaa/500/2110.png", "#a7a9ac", listOf("C Arkansas", "CARK", "Central Arkansas")),
        FavoriteTeamEntity("2117", "Central Michigan Chippewas", "CMU", Sport.CFB, "https://a.espncdn.com/i/teamlogos/ncaa/500/2117.png", "#4c0027", listOf("C Michigan", "CMU", "Central Michigan")),
        FavoriteTeamEntity("2118", "Central Missouri Mules", "UCM", Sport.CFB, "https://a.espncdn.com/i/teamlogos/ncaa/500/2118.png", "#000000", listOf("C Missouri", "Central Missouri", "UCM")),
        FavoriteTeamEntity("2122", "Central Oklahoma Bronchos", "UCO", Sport.CFB, "https://a.espncdn.com/i/teamlogos/ncaa/500/2122.png", "#000000", listOf("C Oklahoma", "Central Oklahoma", "UCO")),
        FavoriteTeamEntity("2120", "Central Washington Wildcats", "CWAU", Sport.CFB, "https://a.espncdn.com/i/teamlogos/ncaa/500/2120.png", "#000000", listOf("C Washington", "CWAU", "Central Washington")),
        FavoriteTeamEntity("2429", "Charlotte 49ers", "CLT", Sport.CFB, "https://a.espncdn.com/i/teamlogos/ncaa/500/2429.png", "#005035", listOf("CLT", "Charlotte")),
        FavoriteTeamEntity("3253", "Charlotte Saints", "COLLE", Sport.CFB, "", "#333333", listOf("COLLE", "Charlotte", "Faith NC")),
        FavoriteTeamEntity("2132", "Cincinnati Bearcats", "CIN", Sport.CFB, "https://a.espncdn.com/i/teamlogos/ncaa/500/2132.png", "#000000", listOf("CIN", "Cincinnati")),
        FavoriteTeamEntity("228", "Clemson Tigers", "CLEM", Sport.CFB, "https://a.espncdn.com/i/teamlogos/ncaa/500/228.png", "#f56600", listOf("CLEM", "Clemson")),
        FavoriteTeamEntity("324", "Coastal Carolina Chanticleers", "CCU", Sport.CFB, "https://a.espncdn.com/i/teamlogos/ncaa/500/324.png", "#006f71", listOf("CCU", "Coastal", "Coastal Carolina")),
        FavoriteTeamEntity("108382", "College of Idaho Yotes", "COI", Sport.CFB, "", "#333333", listOf("COI", "Coll. of Idaho", "College of Idaho")),
        FavoriteTeamEntity("38", "Colorado Buffaloes", "COLO", Sport.CFB, "https://a.espncdn.com/i/teamlogos/ncaa/500/38.png", "#cfb87c", listOf("COLO", "Colorado")),
        FavoriteTeamEntity("11", "Colorado Mesa Mavericks", "COMU", Sport.CFB, "https://a.espncdn.com/i/teamlogos/ncaa/500/11.png", "#000000", listOf("COMU", "Colorado Mesa")),
        FavoriteTeamEntity("2146", "Colorado School of Mines Orediggers", "CMIN", Sport.CFB, "https://a.espncdn.com/i/teamlogos/ncaa/500/2146.png", "#000000", listOf("CMIN", "Colorado Mines", "Colorado School of Mines")),
        FavoriteTeamEntity("36", "Colorado State Rams", "CSU", Sport.CFB, "https://a.espncdn.com/i/teamlogos/ncaa/500/36.png", "#004c23", listOf("CSU", "Colorado St", "Colorado State")),
        FavoriteTeamEntity("507", "Concordia University Nebraska Clippers", "CONCONE", Sport.CFB, "", "#333333", listOf("CONCONE", "Cncrdia NE", "Concordia University Nebraska")),
        FavoriteTeamEntity("2985", "Concordia-Michigan Cardinals", "CONCMI", Sport.CFB, "", "#000000", listOf("CONCMI", "Cncrdia MI", "Concordia-Michigan")),
        FavoriteTeamEntity("150", "Duke Blue Devils", "DUKE", Sport.CFB, "https://a.espncdn.com/i/teamlogos/ncaa/500/150.png", "#00539b", listOf("DUKE", "Duke")),
        FavoriteTeamEntity("2184", "Duquesne Dukes", "DUQ", Sport.CFB, "https://a.espncdn.com/i/teamlogos/ncaa/500/2184.png", "#002D62", listOf("DUQ", "Duquesne")),
        FavoriteTeamEntity("151", "East Carolina Pirates", "ECU", Sport.CFB, "https://a.espncdn.com/i/teamlogos/ncaa/500/151.png", "#582c83", listOf("ECU", "East Carolina")),
        FavoriteTeamEntity("2193", "East Tennessee State Buccaneers", "ETSU", Sport.CFB, "https://a.espncdn.com/i/teamlogos/ncaa/500/2193.png", "#002d61", listOf("ETSU", "East Tennessee State")),
        FavoriteTeamEntity("2837", "East Texas A&M Lions", "ETAM", Sport.CFB, "https://a.espncdn.com/i/teamlogos/ncaa/500/2837.png", "#000000", listOf("ETAM", "East Texas A&M")),
        FavoriteTeamEntity("2194", "East Texas Baptist Tigers", "ETBU", Sport.CFB, "https://a.espncdn.com/i/teamlogos/ncaa/500/2194.png", "#002b44", listOf("E TX Baptist", "ETBU", "East Texas Baptist")),
        FavoriteTeamEntity("2197", "Eastern Illinois Panthers", "EIU", Sport.CFB, "https://a.espncdn.com/i/teamlogos/ncaa/500/2197.png", "#000000", listOf("E Illinois", "EIU", "Eastern Illinois")),
        FavoriteTeamEntity("2198", "Eastern Kentucky Colonels", "EKU", Sport.CFB, "https://a.espncdn.com/i/teamlogos/ncaa/500/2198.png", "#660819", listOf("E Kentucky", "EKU", "Eastern Kentucky")),
        FavoriteTeamEntity("2199", "Eastern Michigan Eagles", "EMU", Sport.CFB, "https://a.espncdn.com/i/teamlogos/ncaa/500/2199.png", "#006938", listOf("E Michigan", "EMU", "Eastern Michigan")),
        FavoriteTeamEntity("2201", "Eastern New Mexico Greyhounds", "ENMU", Sport.CFB, "https://a.espncdn.com/i/teamlogos/ncaa/500/2201.png", "#000000", listOf("ENMU", "Eastern NM", "Eastern New Mexico")),
        FavoriteTeamEntity("2202", "Eastern Oregon", "EORE", Sport.CFB, "", "#333333", listOf("E Oregon", "EORE", "Eastern Oregon")),
        FavoriteTeamEntity("331", "Eastern Washington Eagles", "EWU", Sport.CFB, "https://a.espncdn.com/i/teamlogos/ncaa/500/331.png", "#a10022", listOf("E Washington", "EWU", "Eastern Washington")),
        FavoriteTeamEntity("50", "Florida A&M Rattlers", "FAMU", Sport.CFB, "https://a.espncdn.com/i/teamlogos/ncaa/500/50.png", "#F89728", listOf("FAMU", "Florida A&M")),
        FavoriteTeamEntity("2226", "Florida Atlantic Owls", "FAU", Sport.CFB, "https://a.espncdn.com/i/teamlogos/ncaa/500/2226.png", "#003366", listOf("FAU", "Florida Atlantic")),
        FavoriteTeamEntity("57", "Florida Gators", "FLA", Sport.CFB, "https://a.espncdn.com/i/teamlogos/ncaa/500/57.png", "#0021a5", listOf("FLA", "Florida")),
        FavoriteTeamEntity("2229", "Florida International Panthers", "FIU", Sport.CFB, "https://a.espncdn.com/i/teamlogos/ncaa/500/2229.png", "#091f3f", listOf("FIU", "Florida International")),
        FavoriteTeamEntity("125762", "Florida Memorial Lions", "FMU", Sport.CFB, "", "#333333", listOf("FL Memorial", "FMU", "Florida Memorial")),
        FavoriteTeamEntity("52", "Florida State Seminoles", "FSU", Sport.CFB, "https://a.espncdn.com/i/teamlogos/ncaa/500/52.png", "#782f40", listOf("FSU", "Florida St", "Florida State")),
        FavoriteTeamEntity("2234", "Franklin & Marshall Diplomats", "FMC", Sport.CFB, "https://a.espncdn.com/i/teamlogos/ncaa/500/2234.png", "#25377d", listOf("FMC", "Franklin & Marshall", "Franklin Marsh")),
        FavoriteTeamEntity("278", "Fresno State Bulldogs", "FRES", Sport.CFB, "https://a.espncdn.com/i/teamlogos/ncaa/500/278.png", "#b1102b", listOf("FRES", "Fresno St", "Fresno State")),
        FavoriteTeamEntity("61", "Georgia Bulldogs", "UGA", Sport.CFB, "https://a.espncdn.com/i/teamlogos/ncaa/500/61.png", "#ba0c2f", listOf("Georgia", "UGA")),
        FavoriteTeamEntity("290", "Georgia Southern Eagles", "GASO", Sport.CFB, "https://a.espncdn.com/i/teamlogos/ncaa/500/290.png", "#041e42", listOf("GA Southern", "GASO", "Georgia Southern")),
        FavoriteTeamEntity("2247", "Georgia State Panthers", "GAST", Sport.CFB, "https://a.espncdn.com/i/teamlogos/ncaa/500/2247.png", "#0039a6", listOf("GAST", "Georgia St", "Georgia State")),
        FavoriteTeamEntity("59", "Georgia Tech Yellow Jackets", "GT", Sport.CFB, "https://a.espncdn.com/i/teamlogos/ncaa/500/59.png", "#b3a369", listOf("GT", "Georgia Tech")),
        FavoriteTeamEntity("108", "Harvard Crimson", "HARV", Sport.CFB, "https://a.espncdn.com/i/teamlogos/ncaa/500/108.png", "#990000", listOf("HARV", "Harvard")),
        FavoriteTeamEntity("62", "Hawai'i Rainbow Warriors", "HAW", Sport.CFB, "https://a.espncdn.com/i/teamlogos/ncaa/500/62.png", "#005737", listOf("HAW", "Hawai'i")),
        FavoriteTeamEntity("2277", "Houston Christian Huskies", "HCU", Sport.CFB, "https://a.espncdn.com/i/teamlogos/ncaa/500/2277.png", "#00539c", listOf("HCU", "Hou Christian", "Houston Christian")),
        FavoriteTeamEntity("248", "Houston Cougars", "HOU", Sport.CFB, "https://a.espncdn.com/i/teamlogos/ncaa/500/248.png", "#c8102e", listOf("HOU", "Houston")),
        FavoriteTeamEntity("304", "Idaho State Bengals", "IDST", Sport.CFB, "https://a.espncdn.com/i/teamlogos/ncaa/500/304.png", "#ef8c00", listOf("IDST", "Idaho St", "Idaho State")),
        FavoriteTeamEntity("70", "Idaho Vandals", "IDHO", Sport.CFB, "https://a.espncdn.com/i/teamlogos/ncaa/500/70.png", "#000000", listOf("IDHO", "Idaho")),
        FavoriteTeamEntity("2286", "Illinois College Blueboys", "ILLC", Sport.CFB, "https://a.espncdn.com/i/teamlogos/ncaa/500/2286.png", "#000000", listOf("ILLC", "Illinois Col", "Illinois College")),
        FavoriteTeamEntity("356", "Illinois Fighting Illini", "ILL", Sport.CFB, "https://a.espncdn.com/i/teamlogos/ncaa/500/356.png", "#ff5f05", listOf("ILL", "Illinois")),
        FavoriteTeamEntity("2287", "Illinois State Redbirds", "ILST", Sport.CFB, "https://a.espncdn.com/i/teamlogos/ncaa/500/2287.png", "#CE1126", listOf("ILST", "Illinois St", "Illinois State")),
        FavoriteTeamEntity("306", "Illinois Wesleyan Titans", "ILWU", Sport.CFB, "https://a.espncdn.com/i/teamlogos/ncaa/500/306.png", "#000000", listOf("IL Wesleyan", "ILWU", "Illinois Wesleyan")),
        FavoriteTeamEntity("84", "Indiana Hoosiers", "IU", Sport.CFB, "https://a.espncdn.com/i/teamlogos/ncaa/500/84.png", "#970310", listOf("IU", "Indiana")),
        FavoriteTeamEntity("282", "Indiana State Sycamores", "INST", Sport.CFB, "https://a.espncdn.com/i/teamlogos/ncaa/500/282.png", "#00669a", listOf("INST", "Indiana St", "Indiana State")),
        FavoriteTeamEntity("111756", "Indiana Wesleyan Wildcats", "INWESL", Sport.CFB, "", "#333333", listOf("INDWESLYAN", "INWESL", "Indiana Wesleyan")),
        FavoriteTeamEntity("2292", "Indianapolis Greyhounds", "INDY", Sport.CFB, "https://a.espncdn.com/i/teamlogos/ncaa/500/2292.png", "#000000", listOf("INDY", "Indianapolis")),
        FavoriteTeamEntity("2294", "Iowa Hawkeyes", "IOWA", Sport.CFB, "https://a.espncdn.com/i/teamlogos/ncaa/500/2294.png", "#231f20", listOf("IOWA", "Iowa")),
        FavoriteTeamEntity("66", "Iowa State Cyclones", "ISU", Sport.CFB, "https://a.espncdn.com/i/teamlogos/ncaa/500/66.png", "#ae192d", listOf("ISU", "Iowa State")),
        FavoriteTeamEntity("2296", "Jackson State Tigers", "JKST", Sport.CFB, "https://a.espncdn.com/i/teamlogos/ncaa/500/2296.png", "#123297", listOf("JKST", "Jackson St", "Jackson State")),
        FavoriteTeamEntity("55", "Jacksonville State Gamecocks", "JXST", Sport.CFB, "https://a.espncdn.com/i/teamlogos/ncaa/500/55.png", "#cc0000", listOf("JXST", "Jacksonville State", "Jax State")),
        FavoriteTeamEntity("256", "James Madison Dukes", "JMU", Sport.CFB, "https://a.espncdn.com/i/teamlogos/ncaa/500/256.png", "#450084", listOf("JMU", "James Madison")),
        FavoriteTeamEntity("2305", "Kansas Jayhawks", "KU", Sport.CFB, "https://a.espncdn.com/i/teamlogos/ncaa/500/2305.png", "#0051ba", listOf("KU", "Kansas")),
        FavoriteTeamEntity("2306", "Kansas State Wildcats", "KSU", Sport.CFB, "https://a.espncdn.com/i/teamlogos/ncaa/500/2306.png", "#330a57", listOf("KSU", "Kansas St", "Kansas State")),
        FavoriteTeamEntity("547", "Kansas Wesleyan Ks Wesleyan", "KANSA", Sport.CFB, "", "#000000", listOf("KANSA", "KS Weslyan", "Kansas Wesleyan")),
        FavoriteTeamEntity("338", "Kennesaw State Owls", "KENN", Sport.CFB, "https://a.espncdn.com/i/teamlogos/ncaa/500/338.png", "#fdbb30", listOf("KENN", "Kennesaw St", "Kennesaw State")),
        FavoriteTeamEntity("2309", "Kent State Golden Flashes", "KENT", Sport.CFB, "https://a.espncdn.com/i/teamlogos/ncaa/500/2309.png", "#002664", listOf("KENT", "Kent State")),
        FavoriteTeamEntity("3077", "Kentucky Christian Knights", "KYCHR", Sport.CFB, "", "#000000", listOf("KCU", "KYCHR", "Kentucky Christian")),
        FavoriteTeamEntity("2310", "Kentucky State Thorobreds", "KYSU", Sport.CFB, "https://a.espncdn.com/i/teamlogos/ncaa/500/2310.png", "#000000", listOf("KYSU", "Kentucky St", "Kentucky State")),
        FavoriteTeamEntity("2316", "Kentucky Wesleyan Panthers", "KWC", Sport.CFB, "https://a.espncdn.com/i/teamlogos/ncaa/500/2316.png", "#000000", listOf("KWC", "KY Wesleyan", "Kentucky Wesleyan")),
        FavoriteTeamEntity("96", "Kentucky Wildcats", "UK", Sport.CFB, "https://a.espncdn.com/i/teamlogos/ncaa/500/96.png", "#0033a0", listOf("Kentucky", "UK")),
        FavoriteTeamEntity("99", "LSU Tigers", "LSU", Sport.CFB, "https://a.espncdn.com/i/teamlogos/ncaa/500/99.png", "#461d76", listOf("Baton Rouge", "LSU", "Louisiana State", "Tigers")),
        FavoriteTeamEntity("2335", "Liberty Flames", "LIB", Sport.CFB, "https://a.espncdn.com/i/teamlogos/ncaa/500/2335.png", "#0a254e", listOf("LIB", "Liberty")),
        FavoriteTeamEntity("2347", "Louisiana Christian Wildcats", "LCHR", Sport.CFB, "", "#000000", listOf("LA Christian", "LCHR", "Louisiana Christian")),
        FavoriteTeamEntity("309", "Louisiana Ragin' Cajuns", "UL", Sport.CFB, "https://a.espncdn.com/i/teamlogos/ncaa/500/309.png", "#ce181e", listOf("Louisiana", "UL")),
        FavoriteTeamEntity("2348", "Louisiana Tech Bulldogs", "LT", Sport.CFB, "https://a.espncdn.com/i/teamlogos/ncaa/500/2348.png", "#003087", listOf("LT", "Louisiana Tech")),
        FavoriteTeamEntity("97", "Louisville Cardinals", "LOU", Sport.CFB, "https://a.espncdn.com/i/teamlogos/ncaa/500/97.png", "#c9001f", listOf("LOU", "Louisville")),
        FavoriteTeamEntity("276", "Marshall Thundering Herd", "MRSH", Sport.CFB, "https://a.espncdn.com/i/teamlogos/ncaa/500/276.png", "#00b140", listOf("MRSH", "Marshall")),
        FavoriteTeamEntity("2371", "Mary Hardin Baylor Crusaders", "MHB", Sport.CFB, "https://a.espncdn.com/i/teamlogos/ncaa/500/2371.png", "#492f92", listOf("MHB", "Mary Hardin", "Mary Hardin Baylor")),
        FavoriteTeamEntity("120", "Maryland Terrapins", "MD", Sport.CFB, "https://a.espncdn.com/i/teamlogos/ncaa/500/120.png", "#ce1126", listOf("MD", "Maryland")),
        FavoriteTeamEntity("113", "Massachusetts Minutemen", "MASS", Sport.CFB, "https://a.espncdn.com/i/teamlogos/ncaa/500/113.png", "#881c1c", listOf("MASS", "Massachusetts", "UMass")),
        FavoriteTeamEntity("235", "Memphis Tigers", "MEM", Sport.CFB, "https://a.espncdn.com/i/teamlogos/ncaa/500/235.png", "#004991", listOf("MEM", "Memphis")),
        FavoriteTeamEntity("193", "Miami (OH) RedHawks", "M-OH", Sport.CFB, "https://a.espncdn.com/i/teamlogos/ncaa/500/193.png", "#c41230", listOf("Canes", "Hurricanes", "M-OH", "Miami (OH)", "Miami OH")),
        FavoriteTeamEntity("2390", "Miami Hurricanes", "MIA", Sport.CFB, "https://a.espncdn.com/i/teamlogos/ncaa/500/2390.png", "#f47423", listOf("Canes", "Hurricanes", "MIA", "Miami")),
        FavoriteTeamEntity("127", "Michigan State Spartans", "MSU", Sport.CFB, "https://a.espncdn.com/i/teamlogos/ncaa/500/127.png", "#173f35", listOf("MSU", "Michigan St", "Michigan State")),
        FavoriteTeamEntity("2392", "Michigan Tech Huskies", "MTU", Sport.CFB, "https://a.espncdn.com/i/teamlogos/ncaa/500/2392.png", "#000000", listOf("MTU", "Michigan Tech")),
        FavoriteTeamEntity("130", "Michigan Wolverines", "MICH", Sport.CFB, "https://a.espncdn.com/i/teamlogos/ncaa/500/130.png", "#00274c", listOf("MICH", "Michigan")),
        FavoriteTeamEntity("2393", "Middle Tennessee Blue Raiders", "MTSU", Sport.CFB, "https://a.espncdn.com/i/teamlogos/ncaa/500/2393.png", "#036eb7", listOf("MTSU", "Middle Tennessee")),
        FavoriteTeamEntity("134", "Minnesota Duluth Bulldogs", "UMD", Sport.CFB, "https://a.espncdn.com/i/teamlogos/ncaa/500/134.png", "#000000", listOf("Minn Duluth", "Minnesota Duluth", "UMD")),
        FavoriteTeamEntity("135", "Minnesota Golden Gophers", "MINN", Sport.CFB, "https://a.espncdn.com/i/teamlogos/ncaa/500/135.png", "#5e0a2f", listOf("MINN", "Minnesota")),
        FavoriteTeamEntity("2399", "Minnesota Morris Cougars", "MNMO", Sport.CFB, "https://a.espncdn.com/i/teamlogos/ncaa/500/2399.png", "#8d191b", listOf("MNMO", "Minn Morris", "Minnesota Morris")),
        FavoriteTeamEntity("2817", "Minnesota St Moorhead Dragons", "MSUM", Sport.CFB, "https://a.espncdn.com/i/teamlogos/ncaa/500/2817.png", "#000000", listOf("MSUM", "Minn Moorhead", "Minnesota St Moorhead")),
        FavoriteTeamEntity("2364", "Minnesota State Mavericks", "MNST", Sport.CFB, "https://a.espncdn.com/i/teamlogos/ncaa/500/2364.png", "#000000", listOf("MNST", "Minnesota St", "Minnesota State")),
        FavoriteTeamEntity("344", "Mississippi State Bulldogs", "MSST", Sport.CFB, "https://a.espncdn.com/i/teamlogos/ncaa/500/344.png", "#5d1725", listOf("Bulldogs", "MSST", "Mississippi St", "Mississippi State", "Starkville")),
        FavoriteTeamEntity("2880", "Missouri Baptist Spartans", "MOBU", Sport.CFB, "https://a.espncdn.com/i/teamlogos/ncaa/500/2880.png", "#333333", listOf("MOBU", "Missouri Bapt", "Missouri Baptist")),
        FavoriteTeamEntity("2402", "Missouri S&T Miners", "MS&T", Sport.CFB, "https://a.espncdn.com/i/teamlogos/ncaa/500/2402.png", "#000000", listOf("MS&T", "Missouri S&T")),
        FavoriteTeamEntity("2403", "Missouri Southern State Lions", "MSSU", Sport.CFB, "https://a.espncdn.com/i/teamlogos/ncaa/500/2403.png", "#000000", listOf("MSSU", "Missouri So St", "Missouri Southern State")),
        FavoriteTeamEntity("2623", "Missouri State Bears", "MOST", Sport.CFB, "https://a.espncdn.com/i/teamlogos/ncaa/500/2623.png", "#5e0009", listOf("MOST", "Missouri St", "Missouri State")),
        FavoriteTeamEntity("142", "Missouri Tigers", "MIZ", Sport.CFB, "https://a.espncdn.com/i/teamlogos/ncaa/500/142.png", "#f1b82d", listOf("MIZ", "Missouri")),
        FavoriteTeamEntity("137", "Missouri Western Griffons", "MOWE", Sport.CFB, "https://a.espncdn.com/i/teamlogos/ncaa/500/137.png", "#000000", listOf("MO Western", "MOWE", "Missouri Western")),
        FavoriteTeamEntity("149", "Montana Grizzlies", "MONT", Sport.CFB, "https://a.espncdn.com/i/teamlogos/ncaa/500/149.png", "#751D4A", listOf("MONT", "Montana")),
        FavoriteTeamEntity("147", "Montana State Bobcats", "MTST", Sport.CFB, "https://a.espncdn.com/i/teamlogos/ncaa/500/147.png", "#00205c", listOf("MTST", "Montana St", "Montana State")),
        FavoriteTeamEntity("2701", "Montana Western Bulldogs", "UMW", Sport.CFB, "https://a.espncdn.com/i/teamlogos/ncaa/500/2701.png", "#000000", listOf("MT Western", "Montana Western", "UMW")),
        FavoriteTeamEntity("152", "NC State Wolfpack", "NCSU", Sport.CFB, "https://a.espncdn.com/i/teamlogos/ncaa/500/152.png", "#cc0000", listOf("NC State", "NCSU")),
        FavoriteTeamEntity("2426", "Navy Midshipmen", "NAVY", Sport.CFB, "https://a.espncdn.com/i/teamlogos/ncaa/500/2426.png", "#00225b", listOf("NAVY", "Navy")),
        FavoriteTeamEntity("158", "Nebraska Cornhuskers", "NEB", Sport.CFB, "https://a.espncdn.com/i/teamlogos/ncaa/500/158.png", "#e31937", listOf("NEB", "Nebraska")),
        FavoriteTeamEntity("2438", "Nebraska Kearney Lopers", "NEBK", Sport.CFB, "https://a.espncdn.com/i/teamlogos/ncaa/500/2438.png", "#000000", listOf("NE Kearney", "NEBK", "Nebraska Kearney")),
        FavoriteTeamEntity("6845", "Nebraska Wesleyan Prairie Wolves", "NWU", Sport.CFB, "https://a.espncdn.com/i/teamlogos/ncaa/500/6845.png", "#333333", listOf("NE Wesleyan", "NWU", "Nebraska Wesleyan")),
        FavoriteTeamEntity("2440", "Nevada Wolf Pack", "NEV", Sport.CFB, "https://a.espncdn.com/i/teamlogos/ncaa/500/2440.png", "#041e42", listOf("NEV", "Nevada")),
        FavoriteTeamEntity("2424", "New Mexico Highlands Cowboys", "NMHU", Sport.CFB, "https://a.espncdn.com/i/teamlogos/ncaa/500/2424.png", "#000000", listOf("NM Highlands", "NMHU", "New Mexico Highlands")),
        FavoriteTeamEntity("167", "New Mexico Lobos", "UNM", Sport.CFB, "https://a.espncdn.com/i/teamlogos/ncaa/500/167.png", "#ba0c2f", listOf("New Mexico", "UNM")),
        FavoriteTeamEntity("166", "New Mexico State Aggies", "NMSU", Sport.CFB, "https://a.espncdn.com/i/teamlogos/ncaa/500/166.png", "#7e141b", listOf("NMSU", "New Mexico St", "New Mexico State")),
        FavoriteTeamEntity("2453", "North Alabama Lions", "UNA", Sport.CFB, "https://a.espncdn.com/i/teamlogos/ncaa/500/2453.png", "#000000", listOf("North Alabama", "UNA")),
        FavoriteTeamEntity("2448", "North Carolina A&T Aggies", "NCAT", Sport.CFB, "https://a.espncdn.com/i/teamlogos/ncaa/500/2448.png", "#0505aa", listOf("NC A&T", "NCAT", "North Carolina A&T")),
        FavoriteTeamEntity("2428", "North Carolina Central Eagles", "NCCU", Sport.CFB, "https://a.espncdn.com/i/teamlogos/ncaa/500/2428.png", "#880023", listOf("NC Central", "NCCU", "North Carolina Central")),
        FavoriteTeamEntity("153", "North Carolina Tar Heels", "UNC", Sport.CFB, "https://a.espncdn.com/i/teamlogos/ncaa/500/153.png", "#7bafd4", listOf("North Carolina", "Tar Heels", "UNC")),
        FavoriteTeamEntity("286", "North Carolina Wesleyan Battling Bishops", "NCW", Sport.CFB, "https://a.espncdn.com/i/teamlogos/ncaa/500/286.png", "#000000", listOf("NC Wesleyan", "NCW", "North Carolina Wesleyan")),
        FavoriteTeamEntity("2449", "North Dakota State Bison", "NDSU", Sport.CFB, "https://a.espncdn.com/i/teamlogos/ncaa/500/2449.png", "#01402A", listOf("N Dakota St", "NDSU", "North Dakota State")),
        FavoriteTeamEntity("249", "North Texas Mean Green", "UNT", Sport.CFB, "https://a.espncdn.com/i/teamlogos/ncaa/500/249.png", "#068f33", listOf("North Texas", "UNT")),
        FavoriteTeamEntity("2464", "Northern Arizona Lumberjacks", "NAU", Sport.CFB, "https://a.espncdn.com/i/teamlogos/ncaa/500/2464.png", "#003976", listOf("N Arizona", "NAU", "Northern Arizona")),
        FavoriteTeamEntity("2458", "Northern Colorado Bears", "UNCO", Sport.CFB, "https://a.espncdn.com/i/teamlogos/ncaa/500/2458.png", "#13558D", listOf("N Colorado", "Northern Colorado", "UNCO")),
        FavoriteTeamEntity("2459", "Northern Illinois Huskies", "NIU", Sport.CFB, "https://a.espncdn.com/i/teamlogos/ncaa/500/2459.png", "#c8102e", listOf("N Illinois", "NIU", "Northern Illinois")),
        FavoriteTeamEntity("2460", "Northern Iowa Panthers", "UNI", Sport.CFB, "https://a.espncdn.com/i/teamlogos/ncaa/500/2460.png", "#473282", listOf("Northern Iowa", "UNI")),
        FavoriteTeamEntity("128", "Northern Michigan Wildcats", "NMI", Sport.CFB, "https://a.espncdn.com/i/teamlogos/ncaa/500/128.png", "#000000", listOf("N Michigan", "NMI", "Northern Michigan")),
        FavoriteTeamEntity("138", "Northwest Missouri State Bearcats", "MWMO", Sport.CFB, "https://a.espncdn.com/i/teamlogos/ncaa/500/138.png", "#000000", listOf("MWMO", "NW Missouri St", "Northwest Missouri State")),
        FavoriteTeamEntity("583", "Northwestern (MN) Eagles", "UNW", Sport.CFB, "https://a.espncdn.com/i/teamlogos/ncaa/500/583.png", "#000000", listOf("N'Western (MN)", "Northwestern (MN)", "UNW")),
        FavoriteTeamEntity("2823", "Northwestern (OK) Rangers", "NWOK", Sport.CFB, "https://a.espncdn.com/i/teamlogos/ncaa/500/2823.png", "#000000", listOf("N'Western (OK)", "NWOK", "Northwestern (OK)")),
        FavoriteTeamEntity("2466", "Northwestern State Demons", "NWST", Sport.CFB, "https://a.espncdn.com/i/teamlogos/ncaa/500/2466.png", "#492F91", listOf("N'Western St", "NWST", "Northwestern State")),
        FavoriteTeamEntity("77", "Northwestern Wildcats", "NU", Sport.CFB, "https://a.espncdn.com/i/teamlogos/ncaa/500/77.png", "#492f92", listOf("NU", "Northwestern")),
        FavoriteTeamEntity("87", "Notre Dame Fighting Irish", "ND", Sport.CFB, "https://a.espncdn.com/i/teamlogos/ncaa/500/87.png", "#062340", listOf("ND", "Notre Dame")),
        FavoriteTeamEntity("195", "Ohio Bobcats", "OHIO", Sport.CFB, "https://a.espncdn.com/i/teamlogos/ncaa/500/195.png", "#154734", listOf("OHIO", "Ohio")),
        FavoriteTeamEntity("2477", "Ohio Dominican Panthers", "OHDU", Sport.CFB, "https://a.espncdn.com/i/teamlogos/ncaa/500/2477.png", "#000000", listOf("OHDU", "Ohio Dominican")),
        FavoriteTeamEntity("427", "Ohio Northern Polar Bears", "OHNU", Sport.CFB, "https://a.espncdn.com/i/teamlogos/ncaa/500/427.png", "#000000", listOf("OHNU", "Ohio Northern")),
        FavoriteTeamEntity("194", "Ohio State Buckeyes", "OSU", Sport.CFB, "https://a.espncdn.com/i/teamlogos/ncaa/500/194.png", "#ba0c2f", listOf("OSU", "Ohio State")),
        FavoriteTeamEntity("3161", "Ohio State Newark Titans", "OSU", Sport.CFB, "", "#000000", listOf("OSU", "Ohio State Newark", "Osunewark")),
        FavoriteTeamEntity("2980", "Ohio Wesleyan Battling Bishops", "OWU", Sport.CFB, "https://a.espncdn.com/i/teamlogos/ncaa/500/2980.png", "#000000", listOf("OWU", "Ohio Wesleyan")),
        FavoriteTeamEntity("319", "Oklahoma Baptist Bison", "OKBU", Sport.CFB, "https://a.espncdn.com/i/teamlogos/ncaa/500/319.png", "#333333", listOf("OK Baptist", "OKBU", "Oklahoma Baptist")),
        FavoriteTeamEntity("2824", "Oklahoma Panhandle OK PANHANDLE ST", "OPSU", Sport.CFB, "", "#000000", listOf("OK Panhandle", "OPSU", "Oklahoma Panhandle")),
        FavoriteTeamEntity("201", "Oklahoma Sooners", "OU", Sport.CFB, "https://a.espncdn.com/i/teamlogos/ncaa/500/201.png", "#990000", listOf("OU", "Oklahoma")),
        FavoriteTeamEntity("197", "Oklahoma State Cowboys", "OKST", Sport.CFB, "https://a.espncdn.com/i/teamlogos/ncaa/500/197.png", "#fe5c00", listOf("OKST", "Oklahoma St", "Oklahoma State")),
        FavoriteTeamEntity("295", "Old Dominion Monarchs", "ODU", Sport.CFB, "https://a.espncdn.com/i/teamlogos/ncaa/500/295.png", "#003768", listOf("ODU", "Old Dominion")),
        FavoriteTeamEntity("145", "Ole Miss Rebels", "MISS", Sport.CFB, "https://a.espncdn.com/i/teamlogos/ncaa/500/145.png", "#13294b", listOf("MISS", "Mississippi", "OM", "Ole Miss", "Oxford", "Rebels")),
        FavoriteTeamEntity("2483", "Oregon Ducks", "ORE", Sport.CFB, "https://a.espncdn.com/i/teamlogos/ncaa/500/2483.png", "#00934b", listOf("ORE", "Oregon")),
        FavoriteTeamEntity("204", "Oregon State Beavers", "ORST", Sport.CFB, "https://a.espncdn.com/i/teamlogos/ncaa/500/204.png", "#dc4405", listOf("ORST", "Oregon St", "Oregon State")),
        FavoriteTeamEntity("213", "Penn State Nittany Lions", "PSU", Sport.CFB, "https://a.espncdn.com/i/teamlogos/ncaa/500/213.png", "#061440", listOf("PSU", "Penn State")),
        FavoriteTeamEntity("221", "Pittsburgh Panthers", "PITT", Sport.CFB, "https://a.espncdn.com/i/teamlogos/ncaa/500/221.png", "#003594", listOf("PITT", "Panthers", "Pitt", "Pittsburgh")),
        FavoriteTeamEntity("163", "Princeton Tigers", "PRIN", Sport.CFB, "https://a.espncdn.com/i/teamlogos/ncaa/500/163.png", "#000000", listOf("PRIN", "Princeton")),
        FavoriteTeamEntity("2509", "Purdue Boilermakers", "PUR", Sport.CFB, "https://a.espncdn.com/i/teamlogos/ncaa/500/2509.png", "#ceb888", listOf("PUR", "Purdue")),
        FavoriteTeamEntity("242", "Rice Owls", "RICE", Sport.CFB, "https://a.espncdn.com/i/teamlogos/ncaa/500/242.png", "#00205b", listOf("RICE", "Rice")),
        FavoriteTeamEntity("164", "Rutgers Scarlet Knights", "RUTG", Sport.CFB, "https://a.espncdn.com/i/teamlogos/ncaa/500/164.png", "#ce0e2d", listOf("RUTG", "Rutgers")),
        FavoriteTeamEntity("2545", "SE Louisiana Lions", "SELA", Sport.CFB, "https://a.espncdn.com/i/teamlogos/ncaa/500/2545.png", "#215732", listOf("SE Louisiana", "SELA")),
        FavoriteTeamEntity("2567", "SMU Mustangs", "SMU", Sport.CFB, "https://a.espncdn.com/i/teamlogos/ncaa/500/2567.png", "#a80000", listOf("Mustangs", "SMU", "Southern Methodist")),
        FavoriteTeamEntity("2534", "Sam Houston Bearkats", "SHSU", Sport.CFB, "https://a.espncdn.com/i/teamlogos/ncaa/500/2534.png", "#f56423", listOf("SHSU", "Sam Houston")),
        FavoriteTeamEntity("21", "San Diego State Aztecs", "SDSU", Sport.CFB, "https://a.espncdn.com/i/teamlogos/ncaa/500/21.png", "#a6192e", listOf("SDSU", "San Diego St", "San Diego State")),
        FavoriteTeamEntity("23", "San José State Spartans", "SJSU", Sport.CFB, "https://a.espncdn.com/i/teamlogos/ncaa/500/23.png", "#0038a8", listOf("SJSU", "San José St", "San José State")),
        FavoriteTeamEntity("6", "South Alabama Jaguars", "USA", Sport.CFB, "https://a.espncdn.com/i/teamlogos/ncaa/500/6.png", "#00205b", listOf("South Alabama", "USA")),
        FavoriteTeamEntity("2579", "South Carolina Gamecocks", "SC", Sport.CFB, "https://a.espncdn.com/i/teamlogos/ncaa/500/2579.png", "#73000a", listOf("SC", "South Carolina")),
        FavoriteTeamEntity("2569", "South Carolina State Bulldogs", "SCST", Sport.CFB, "https://a.espncdn.com/i/teamlogos/ncaa/500/2569.png", "#7d1315", listOf("SC State", "SCST", "South Carolina State")),
        FavoriteTeamEntity("2571", "South Dakota State Jackrabbits", "SDST", Sport.CFB, "https://a.espncdn.com/i/teamlogos/ncaa/500/2571.png", "#0033a0", listOf("S Dakota St", "SDST", "South Dakota State")),
        FavoriteTeamEntity("58", "South Florida Bulls", "USF", Sport.CFB, "https://a.espncdn.com/i/teamlogos/ncaa/500/58.png", "#006747", listOf("South Florida", "USF")),
        FavoriteTeamEntity("2546", "Southeast Missouri State Redhawks", "SEMO", Sport.CFB, "https://a.espncdn.com/i/teamlogos/ncaa/500/2546.png", "#c8102e", listOf("SE Missouri", "SEMO", "Southeast Missouri State")),
        FavoriteTeamEntity("199", "Southeastern Oklahoma State Savage Storm", "SEOK", Sport.CFB, "https://a.espncdn.com/i/teamlogos/ncaa/500/199.png", "#000000", listOf("SE Oklahoma St", "SEOK", "Southeastern Oklahoma State")),
        FavoriteTeamEntity("2568", "Southern Arkansas Muleriders", "SAR", Sport.CFB, "https://a.espncdn.com/i/teamlogos/ncaa/500/2568.png", "#000000", listOf("S Arkansas", "SAR", "Southern Arkansas")),
        FavoriteTeamEntity("79", "Southern Illinois Salukis", "SIU", Sport.CFB, "https://a.espncdn.com/i/teamlogos/ncaa/500/79.png", "#85283D", listOf("S Illinois", "SIU", "Southern Illinois")),
        FavoriteTeamEntity("2572", "Southern Miss Golden Eagles", "USM", Sport.CFB, "https://a.espncdn.com/i/teamlogos/ncaa/500/2572.png", "#ffc72c", listOf("Golden Eagles", "Hattiesburg", "Southern Miss", "Southern Mississippi", "USM")),
        FavoriteTeamEntity("2584", "Southern Oregon Raiders", "SOR", Sport.CFB, "https://a.espncdn.com/i/teamlogos/ncaa/500/2584.png", "#333333", listOf("S Oregon", "SOR", "Southern Oregon")),
        FavoriteTeamEntity("253", "Southern Utah Thunderbirds", "SUU", Sport.CFB, "https://a.espncdn.com/i/teamlogos/ncaa/500/253.png", "#c72026", listOf("SUU", "Southern Utah")),
        FavoriteTeamEntity("2896", "Southern Virginia Knights", "SOVA", Sport.CFB, "https://a.espncdn.com/i/teamlogos/ncaa/500/2896.png", "#000000", listOf("S Virginia", "SOVA", "Southern Virginia")),
        FavoriteTeamEntity("2587", "Southwest Minnesota State Mustangs", "SWMS", Sport.CFB, "https://a.espncdn.com/i/teamlogos/ncaa/500/2587.png", "#000000", listOf("SW Minn St", "SWMS", "Southwest Minnesota State")),
        FavoriteTeamEntity("2927", "Southwestern Oklahoma State Bulldogs", "SOSU", Sport.CFB, "https://a.espncdn.com/i/teamlogos/ncaa/500/2927.png", "#000000", listOf("SOSU", "SW Oklahoma St", "Southwestern Oklahoma State")),
        FavoriteTeamEntity("24", "Stanford Cardinal", "STAN", Sport.CFB, "https://a.espncdn.com/i/teamlogos/ncaa/500/24.png", "#8c1515", listOf("STAN", "Stanford")),
        FavoriteTeamEntity("183", "Syracuse Orange", "SYR", Sport.CFB, "https://a.espncdn.com/i/teamlogos/ncaa/500/183.png", "#000e54", listOf("SYR", "Syracuse")),
        FavoriteTeamEntity("2628", "TCU Horned Frogs", "TCU", Sport.CFB, "https://a.espncdn.com/i/teamlogos/ncaa/500/2628.png", "#4d1979", listOf("Horned Frogs", "TCU", "Texas Christian")),
        FavoriteTeamEntity("218", "Temple Owls", "TEM", Sport.CFB, "https://a.espncdn.com/i/teamlogos/ncaa/500/218.png", "#a41e35", listOf("TEM", "Temple")),
        FavoriteTeamEntity("2634", "Tennessee State Tigers", "TNST", Sport.CFB, "https://a.espncdn.com/i/teamlogos/ncaa/500/2634.png", "#171796", listOf("TNST", "Tennessee St", "Tennessee State")),
        FavoriteTeamEntity("2635", "Tennessee Tech Golden Eagles", "TNTC", Sport.CFB, "https://a.espncdn.com/i/teamlogos/ncaa/500/2635.png", "#5A4099", listOf("TNTC", "Tennessee Tech")),
        FavoriteTeamEntity("2633", "Tennessee Volunteers", "TENN", Sport.CFB, "https://a.espncdn.com/i/teamlogos/ncaa/500/2633.png", "#ff8200", listOf("TENN", "Tennessee")),
        FavoriteTeamEntity("245", "Texas A&M Aggies", "TA&M", Sport.CFB, "https://a.espncdn.com/i/teamlogos/ncaa/500/245.png", "#500000", listOf("TA&M", "Texas A&M")),
        FavoriteTeamEntity("2658", "Texas A&M-Kingsville Javelinas", "TAMK", Sport.CFB, "https://a.espncdn.com/i/teamlogos/ncaa/500/2658.png", "#000000", listOf("TAMK", "TAMU-Kingsvlle", "Texas A&M-Kingsville")),
        FavoriteTeamEntity("2637", "Texas College Steers", "TXCL", Sport.CFB, "", "#333333", listOf("TXCL", "Texas College")),
        FavoriteTeamEntity("251", "Texas Longhorns", "TEX", Sport.CFB, "https://a.espncdn.com/i/teamlogos/ncaa/500/251.png", "#af5c37", listOf("TEX", "Texas")),
        FavoriteTeamEntity("2639", "Texas Lutheran Bulldogs", "TXLU", Sport.CFB, "https://a.espncdn.com/i/teamlogos/ncaa/500/2639.png", "#fdbe10", listOf("TXLU", "Texas Lutheran")),
        FavoriteTeamEntity("2640", "Texas Southern Tigers", "TXSO", Sport.CFB, "https://a.espncdn.com/i/teamlogos/ncaa/500/2640.png", "#860038", listOf("TXSO", "Texas Southern")),
        FavoriteTeamEntity("326", "Texas State Bobcats", "TXST", Sport.CFB, "https://a.espncdn.com/i/teamlogos/ncaa/500/326.png", "#501214", listOf("TXST", "Texas St", "Texas State")),
        FavoriteTeamEntity("2641", "Texas Tech Red Raiders", "TTU", Sport.CFB, "https://a.espncdn.com/i/teamlogos/ncaa/500/2641.png", "#da291c", listOf("TTU", "Texas Tech")),
        FavoriteTeamEntity("2649", "Toledo Rockets", "TOL", Sport.CFB, "https://a.espncdn.com/i/teamlogos/ncaa/500/2649.png", "#0b2240", listOf("TOL", "Toledo")),
        FavoriteTeamEntity("2653", "Troy Trojans", "TROY", Sport.CFB, "https://a.espncdn.com/i/teamlogos/ncaa/500/2653.png", "#862633", listOf("TROY", "Troy")),
        FavoriteTeamEntity("3237", "Troy Vikings", "HVCC", Sport.CFB, "", "#333333", listOf("HVCC", "Hudson", "Troy")),
        FavoriteTeamEntity("2655", "Tulane Green Wave", "TULN", Sport.CFB, "https://a.espncdn.com/i/teamlogos/ncaa/500/2655.png", "#006747", listOf("TULN", "Tulane")),
        FavoriteTeamEntity("202", "Tulsa Golden Hurricane", "TLSA", Sport.CFB, "https://a.espncdn.com/i/teamlogos/ncaa/500/202.png", "#003595", listOf("TLSA", "Tulsa")),
        FavoriteTeamEntity("2839", "Tusculum Pioneers", "TUSC", Sport.CFB, "https://a.espncdn.com/i/teamlogos/ncaa/500/2839.png", "#f2682a", listOf("TUSC", "Tusculum")),
        FavoriteTeamEntity("5", "UAB Blazers", "UAB", Sport.CFB, "https://a.espncdn.com/i/teamlogos/ncaa/500/5.png", "#1a5632", listOf("UAB")),
        FavoriteTeamEntity("2116", "UCF Knights", "UCF", Sport.CFB, "https://a.espncdn.com/i/teamlogos/ncaa/500/2116.png", "#000000", listOf("Central Florida", "Knights", "UCF")),
        FavoriteTeamEntity("26", "UCLA Bruins", "UCLA", Sport.CFB, "https://a.espncdn.com/i/teamlogos/ncaa/500/26.png", "#2774ae", listOf("UCLA")),
        FavoriteTeamEntity("41", "UConn Huskies", "CONN", Sport.CFB, "https://a.espncdn.com/i/teamlogos/ncaa/500/41.png", "#0c2340", listOf("CONN", "UConn")),
        FavoriteTeamEntity("2433", "UL Monroe Warhawks", "ULM", Sport.CFB, "https://a.espncdn.com/i/teamlogos/ncaa/500/2433.png", "#840029", listOf("UL Monroe", "ULM")),
        FavoriteTeamEntity("379", "UMass Dartmouth Corsairs", "MDAR", Sport.CFB, "https://a.espncdn.com/i/teamlogos/ncaa/500/379.png", "#000000", listOf("MDAR", "UMass Dartmouth", "UMass Dartmth")),
        FavoriteTeamEntity("2439", "UNLV Rebels", "UNLV", Sport.CFB, "https://a.espncdn.com/i/teamlogos/ncaa/500/2439.png", "#cf0a2c", listOf("UNLV")),
        FavoriteTeamEntity("30", "USC Trojans", "USC", Sport.CFB, "https://a.espncdn.com/i/teamlogos/ncaa/500/30.png", "#9d2235", listOf("Southern California", "Trojans", "USC")),
        FavoriteTeamEntity("2638", "UTEP Miners", "UTEP", Sport.CFB, "https://a.espncdn.com/i/teamlogos/ncaa/500/2638.png", "#ff8200", listOf("UTEP")),
        FavoriteTeamEntity("2636", "UTSA Roadrunners", "UTSA", Sport.CFB, "https://a.espncdn.com/i/teamlogos/ncaa/500/2636.png", "#0c2340", listOf("UTSA")),
        FavoriteTeamEntity("389", "Upper Iowa Peacocks", "UIU", Sport.CFB, "https://a.espncdn.com/i/teamlogos/ncaa/500/389.png", "#000000", listOf("UIU", "Upper Iowa")),
        FavoriteTeamEntity("328", "Utah State Aggies", "USU", Sport.CFB, "https://a.espncdn.com/i/teamlogos/ncaa/500/328.png", "#0f2439", listOf("USU", "Utah State")),
        FavoriteTeamEntity("3101", "Utah Tech Trailblazers", "UTU", Sport.CFB, "https://a.espncdn.com/i/teamlogos/ncaa/500/3101.png", "#000000", listOf("UTU", "Utah Tech")),
        FavoriteTeamEntity("254", "Utah Utes", "UTAH", Sport.CFB, "https://a.espncdn.com/i/teamlogos/ncaa/500/254.png", "#be0000", listOf("UTAH", "Utah")),
        FavoriteTeamEntity("238", "Vanderbilt Commodores", "VAN", Sport.CFB, "https://a.espncdn.com/i/teamlogos/ncaa/500/238.png", "#000000", listOf("VAN", "Vanderbilt")),
        FavoriteTeamEntity("222", "Villanova Wildcats", "VILL", Sport.CFB, "https://a.espncdn.com/i/teamlogos/ncaa/500/222.png", "#00205b", listOf("VILL", "Villanova")),
        FavoriteTeamEntity("258", "Virginia Cavaliers", "UVA", Sport.CFB, "https://a.espncdn.com/i/teamlogos/ncaa/500/258.png", "#232d4b", listOf("UVA", "Virginia")),
        FavoriteTeamEntity("2355", "Virginia Lynchburg Dragons", "VUL", Sport.CFB, "", "#333333", listOf("VA Lynchburg", "VUL", "Virginia Lynchburg")),
        FavoriteTeamEntity("330", "Virginia State Trojans", "VSU", Sport.CFB, "https://a.espncdn.com/i/teamlogos/ncaa/500/330.png", "#000000", listOf("VSU", "Virginia St", "Virginia State")),
        FavoriteTeamEntity("259", "Virginia Tech Hokies", "VT", Sport.CFB, "https://a.espncdn.com/i/teamlogos/ncaa/500/259.png", "#6a2c3e", listOf("VT", "Virginia Tech")),
        FavoriteTeamEntity("2676", "Virginia Union Panthers", "VUU", Sport.CFB, "https://a.espncdn.com/i/teamlogos/ncaa/500/2676.png", "#000000", listOf("VUU", "Virginia Union")),
        FavoriteTeamEntity("154", "Wake Forest Demon Deacons", "WAKE", Sport.CFB, "https://a.espncdn.com/i/teamlogos/ncaa/500/154.png", "#ceb888", listOf("WAKE", "Wake Forest")),
        FavoriteTeamEntity("2686", "Washington & Jefferson Presidents", "W&J", Sport.CFB, "https://a.espncdn.com/i/teamlogos/ncaa/500/2686.png", "#000000", listOf("W&J", "Wash & Jeff", "Washington & Jefferson")),
        FavoriteTeamEntity("264", "Washington Huskies", "WASH", Sport.CFB, "https://a.espncdn.com/i/teamlogos/ncaa/500/264.png", "#33006f", listOf("WASH", "Washington")),
        FavoriteTeamEntity("143", "Washington St. Louis Bears", "WUMO", Sport.CFB, "https://a.espncdn.com/i/teamlogos/ncaa/500/143.png", "#000000", listOf("WUMO", "Wash St Louis", "Washington St. Louis")),
        FavoriteTeamEntity("265", "Washington State Cougars", "WSU", Sport.CFB, "https://a.espncdn.com/i/teamlogos/ncaa/500/265.png", "#a60f2d", listOf("WSU", "Washington St", "Washington State")),
        FavoriteTeamEntity("2688", "Washington and Lee Generals", "W&L", Sport.CFB, "https://a.espncdn.com/i/teamlogos/ncaa/500/2688.png", "#000399", listOf("W&L", "Wash and Lee", "Washington and Lee")),
        FavoriteTeamEntity("2695", "West Alabama Tigers", "UWA", Sport.CFB, "https://a.espncdn.com/i/teamlogos/ncaa/500/2695.png", "#d31d33", listOf("UWA", "West Alabama")),
        FavoriteTeamEntity("110242", "West Florida Argonauts", "UWF", Sport.CFB, "https://a.espncdn.com/i/teamlogos/ncaa/500/110242.png", "#333333", listOf("UWF", "West Florida")),
        FavoriteTeamEntity("2698", "West Georgia Wolves", "WGA", Sport.CFB, "https://a.espncdn.com/i/teamlogos/ncaa/500/2698.png", "#0033a1", listOf("WGA", "West Georgia")),
        FavoriteTeamEntity("2699", "West Liberty Hilltoppers", "WLU", Sport.CFB, "https://a.espncdn.com/i/teamlogos/ncaa/500/2699.png", "#000000", listOf("WLU", "West Liberty")),
        FavoriteTeamEntity("3211", "West Memphis Crusaders", "FAI", Sport.CFB, "", "#333333", listOf("C of Faith", "FAI", "West Memphis")),
        FavoriteTeamEntity("2704", "West Texas Buffaloes", "WTAM", Sport.CFB, "https://a.espncdn.com/i/teamlogos/ncaa/500/2704.png", "#000000", listOf("WTAM", "West Texas")),
        FavoriteTeamEntity("277", "West Virginia Mountaineers", "WVU", Sport.CFB, "https://a.espncdn.com/i/teamlogos/ncaa/500/277.png", "#eaaa00", listOf("WVU", "West Virginia")),
        FavoriteTeamEntity("2707", "West Virginia State Yellow Jackets", "WVSU", Sport.CFB, "https://a.espncdn.com/i/teamlogos/ncaa/500/2707.png", "#000000", listOf("W Virginia St", "WVSU", "West Virginia State")),
        FavoriteTeamEntity("455", "West Virginia Wesleyan Bobcats", "WVWC", Sport.CFB, "https://a.espncdn.com/i/teamlogos/ncaa/500/455.png", "#000000", listOf("WV Wesleyan", "WVWC", "West Virginia Wesleyan")),
        FavoriteTeamEntity("2714", "Western Colorado Mountaineers", "WCOL", Sport.CFB, "https://a.espncdn.com/i/teamlogos/ncaa/500/2714.png", "#000000", listOf("W Colorado", "WCOL", "Western Colorado")),
        FavoriteTeamEntity("2710", "Western Illinois Leathernecks", "WIU", Sport.CFB, "https://a.espncdn.com/i/teamlogos/ncaa/500/2710.png", "#4e1e8a", listOf("W Illinois", "WIU", "Western Illinois")),
        FavoriteTeamEntity("98", "Western Kentucky Hilltoppers", "WKU", Sport.CFB, "https://a.espncdn.com/i/teamlogos/ncaa/500/98.png", "#e13a3e", listOf("WKU", "Western KY", "Western Kentucky")),
        FavoriteTeamEntity("2711", "Western Michigan Broncos", "WMU", Sport.CFB, "https://a.espncdn.com/i/teamlogos/ncaa/500/2711.png", "#532e1f", listOf("W Michigan", "WMU", "Western Michigan")),
        FavoriteTeamEntity("2703", "Western New Mexico Mustangs", "WNMU", Sport.CFB, "https://a.espncdn.com/i/teamlogos/ncaa/500/2703.png", "#000000", listOf("WNMU", "Western NM", "Western New Mexico")),
        FavoriteTeamEntity("2848", "Western Oregon Wolves", "WORU", Sport.CFB, "https://a.espncdn.com/i/teamlogos/ncaa/500/2848.png", "#000000", listOf("WORU", "West Oregon", "Western Oregon")),
        FavoriteTeamEntity("2912", "William Penn Statesmen", "WPEN", Sport.CFB, "", "#000000", listOf("WPEN", "William Penn")),
        FavoriteTeamEntity("275", "Wisconsin Badgers", "WIS", Sport.CFB, "https://a.espncdn.com/i/teamlogos/ncaa/500/275.png", "#a00000", listOf("WIS", "Wisconsin")),
        FavoriteTeamEntity("2738", "Wisconsin Eau Claire Blugolds", "UWEC", Sport.CFB, "https://a.espncdn.com/i/teamlogos/ncaa/500/2738.png", "#000000", listOf("UW-Eau Claire", "UWEC", "Wisconsin Eau Claire")),
        FavoriteTeamEntity("2740", "Wisconsin La Crosse Eagles", "UWL", Sport.CFB, "https://a.espncdn.com/i/teamlogos/ncaa/500/2740.png", "#000000", listOf("UW-La Crosse", "UWL", "Wisconsin La Crosse")),
        FavoriteTeamEntity("2741", "Wisconsin Lutheran Warriors", "WLC", Sport.CFB, "https://a.espncdn.com/i/teamlogos/ncaa/500/2741.png", "#006241", listOf("WLC", "Wisc Lutheran", "Wisconsin Lutheran")),
        FavoriteTeamEntity("271", "Wisconsin Oshkosh Titans", "UWO", Sport.CFB, "https://a.espncdn.com/i/teamlogos/ncaa/500/271.png", "#000000", listOf("UW-Oshkosh", "UWO", "Wisconsin Oshkosh")),
        FavoriteTeamEntity("272", "Wisconsin Platteville Pioneers", "UWP", Sport.CFB, "https://a.espncdn.com/i/teamlogos/ncaa/500/272.png", "#000000", listOf("UW-Platteville", "UWP", "Wisconsin Platteville")),
        FavoriteTeamEntity("2723", "Wisconsin River Falls Falcons", "UWRF", Sport.CFB, "https://a.espncdn.com/i/teamlogos/ncaa/500/2723.png", "#000000", listOf("UW-River Falls", "UWRF", "Wisconsin River Falls")),
        FavoriteTeamEntity("2743", "Wisconsin Stevens Point Pointers", "UWSP", Sport.CFB, "https://a.espncdn.com/i/teamlogos/ncaa/500/2743.png", "#000000", listOf("UW-Stevens Pt", "UWSP", "Wisconsin Stevens Point")),
        FavoriteTeamEntity("2744", "Wisconsin Stout Blue Devils", "UWST", Sport.CFB, "https://a.espncdn.com/i/teamlogos/ncaa/500/2744.png", "#000000", listOf("UW-Stout", "UWST", "Wisconsin Stout")),
        FavoriteTeamEntity("2745", "Wisconsin Whitewater Warhawks", "UWW", Sport.CFB, "https://a.espncdn.com/i/teamlogos/ncaa/500/2745.png", "#000000", listOf("UW-Whitewater", "UWW", "Wisconsin Whitewater")),
        FavoriteTeamEntity("2751", "Wyoming Cowboys", "WYO", Sport.CFB, "https://a.espncdn.com/i/teamlogos/ncaa/500/2751.png", "#492f24", listOf("WYO", "Wyoming")),
        FavoriteTeamEntity("43", "Yale Bulldogs", "YALE", Sport.CFB, "https://a.espncdn.com/i/teamlogos/ncaa/500/43.png", "#004a81", listOf("YALE", "Yale")),

        // NBA (All 30 teams)
        FavoriteTeamEntity("1", "Atlanta Hawks", "ATL", Sport.NBA, "https://a.espncdn.com/i/teamlogos/nba/500/atl.png", "#c8102e", listOf("ATL", "Atlanta", "Hawks")),
        FavoriteTeamEntity("2", "Boston Celtics", "BOS", Sport.NBA, "https://a.espncdn.com/i/teamlogos/nba/500/bos.png", "#008348", listOf("BOS", "Boston", "Celtics")),
        FavoriteTeamEntity("17", "Brooklyn Nets", "BKN", Sport.NBA, "https://a.espncdn.com/i/teamlogos/nba/500/bkn.png", "#000000", listOf("BKN", "Brooklyn", "Nets")),
        FavoriteTeamEntity("30", "Charlotte Hornets", "CHA", Sport.NBA, "https://a.espncdn.com/i/teamlogos/nba/500/cha.png", "#008ca8", listOf("CHA", "Charlotte", "Hornets")),
        FavoriteTeamEntity("4", "Chicago Bulls", "CHI", Sport.NBA, "https://a.espncdn.com/i/teamlogos/nba/500/chi.png", "#ce1141", listOf("Bulls", "CHI", "Chicago")),
        FavoriteTeamEntity("5", "Cleveland Cavaliers", "CLE", Sport.NBA, "https://a.espncdn.com/i/teamlogos/nba/500/cle.png", "#860038", listOf("CLE", "Cavaliers", "Cleveland")),
        FavoriteTeamEntity("6", "Dallas Mavericks", "DAL", Sport.NBA, "https://a.espncdn.com/i/teamlogos/nba/500/dal.png", "#0064b1", listOf("DAL", "Dallas", "Mavericks")),
        FavoriteTeamEntity("7", "Denver Nuggets", "DEN", Sport.NBA, "https://a.espncdn.com/i/teamlogos/nba/500/den.png", "#0e2240", listOf("DEN", "Denver", "Nuggets")),
        FavoriteTeamEntity("8", "Detroit Pistons", "DET", Sport.NBA, "https://a.espncdn.com/i/teamlogos/nba/500/det.png", "#1d428a", listOf("DET", "Detroit", "Pistons")),
        FavoriteTeamEntity("9", "Golden State Warriors", "GS", Sport.NBA, "https://a.espncdn.com/i/teamlogos/nba/500/gs.png", "#fdb927", listOf("GS", "Golden State", "Warriors")),
        FavoriteTeamEntity("10", "Houston Rockets", "HOU", Sport.NBA, "https://a.espncdn.com/i/teamlogos/nba/500/hou.png", "#ce0e2d", listOf("HOU", "Houston", "Rockets")),
        FavoriteTeamEntity("11", "Indiana Pacers", "IND", Sport.NBA, "https://a.espncdn.com/i/teamlogos/nba/500/ind.png", "#0c2340", listOf("IND", "Indiana", "Pacers")),
        FavoriteTeamEntity("12", "LA Clippers", "LAC", Sport.NBA, "https://a.espncdn.com/i/teamlogos/nba/500/lac.png", "#12173f", listOf("Clippers", "LA", "LAC")),
        FavoriteTeamEntity("13", "Los Angeles Lakers", "LAL", Sport.NBA, "https://a.espncdn.com/i/teamlogos/nba/500/lal.png", "#552583", listOf("LAL", "Lakers", "Los Angeles")),
        FavoriteTeamEntity("29", "Memphis Grizzlies", "MEM", Sport.NBA, "https://a.espncdn.com/i/teamlogos/nba/500/mem.png", "#5d76a9", listOf("Grizzlies", "MEM", "Memphis")),
        FavoriteTeamEntity("14", "Miami Heat", "MIA", Sport.NBA, "https://a.espncdn.com/i/teamlogos/nba/500/mia.png", "#98002e", listOf("Canes", "Heat", "Hurricanes", "MIA", "Miami")),
        FavoriteTeamEntity("15", "Milwaukee Bucks", "MIL", Sport.NBA, "https://a.espncdn.com/i/teamlogos/nba/500/mil.png", "#00471b", listOf("Bucks", "MIL", "Milwaukee")),
        FavoriteTeamEntity("16", "Minnesota Timberwolves", "MIN", Sport.NBA, "https://a.espncdn.com/i/teamlogos/nba/500/min.png", "#266092", listOf("MIN", "Minnesota", "Timberwolves")),
        FavoriteTeamEntity("3", "New Orleans Pelicans", "NO", Sport.NBA, "https://a.espncdn.com/i/teamlogos/nba/500/no.png", "#0a2240", listOf("NO", "New Orleans", "Pelicans")),
        FavoriteTeamEntity("18", "New York Knicks", "NY", Sport.NBA, "https://a.espncdn.com/i/teamlogos/nba/500/ny.png", "#1d428a", listOf("Knicks", "NY", "New York")),
        FavoriteTeamEntity("25", "Oklahoma City Thunder", "OKC", Sport.NBA, "https://a.espncdn.com/i/teamlogos/nba/500/okc.png", "#007ac1", listOf("OKC", "Oklahoma City", "Thunder")),
        FavoriteTeamEntity("19", "Orlando Magic", "ORL", Sport.NBA, "https://a.espncdn.com/i/teamlogos/nba/500/orl.png", "#0150b5", listOf("Magic", "ORL", "Orlando")),
        FavoriteTeamEntity("20", "Philadelphia 76ers", "PHI", Sport.NBA, "https://a.espncdn.com/i/teamlogos/nba/500/phi.png", "#1d428a", listOf("76ers", "PHI", "Philadelphia")),
        FavoriteTeamEntity("21", "Phoenix Suns", "PHX", Sport.NBA, "https://a.espncdn.com/i/teamlogos/nba/500/phx.png", "#29127a", listOf("PHX", "Phoenix", "Suns")),
        FavoriteTeamEntity("22", "Portland Trail Blazers", "POR", Sport.NBA, "https://a.espncdn.com/i/teamlogos/nba/500/por.png", "#e03a3e", listOf("POR", "Portland", "Trail Blazers")),
        FavoriteTeamEntity("23", "Sacramento Kings", "SAC", Sport.NBA, "https://a.espncdn.com/i/teamlogos/nba/500/sac.png", "#5a2d81", listOf("Kings", "SAC", "Sacramento")),
        FavoriteTeamEntity("24", "San Antonio Spurs", "SA", Sport.NBA, "https://a.espncdn.com/i/teamlogos/nba/500/sa.png", "#000000", listOf("SA", "San Antonio", "Spurs")),
        FavoriteTeamEntity("28", "Toronto Raptors", "TOR", Sport.NBA, "https://a.espncdn.com/i/teamlogos/nba/500/tor.png", "#d91244", listOf("Raptors", "TOR", "Toronto")),
        FavoriteTeamEntity("26", "Utah Jazz", "UTAH", Sport.NBA, "https://a.espncdn.com/i/teamlogos/nba/500/utah.png", "#4e008e", listOf("Jazz", "UTAH", "Utah")),
        FavoriteTeamEntity("27", "Washington Wizards", "WSH", Sport.NBA, "https://a.espncdn.com/i/teamlogos/nba/500/wsh.png", "#e31837", listOf("WSH", "Washington", "Wizards")),

        // MLB (All 30 teams)
        FavoriteTeamEntity("29", "Arizona Diamondbacks", "ARI", Sport.MLB, "https://a.espncdn.com/i/teamlogos/mlb/500/ari.png", "#aa182c", listOf("ARI", "Arizona", "Diamondbacks")),
        FavoriteTeamEntity("11", "Athletics", "ATH", Sport.MLB, "https://a.espncdn.com/i/teamlogos/mlb/500/ath.png", "#003831", listOf("ATH", "Athletics")),
        FavoriteTeamEntity("15", "Atlanta Braves", "ATL", Sport.MLB, "https://a.espncdn.com/i/teamlogos/mlb/500/atl.png", "#0c2340", listOf("ATL", "Atlanta", "Braves")),
        FavoriteTeamEntity("1", "Baltimore Orioles", "BAL", Sport.MLB, "https://a.espncdn.com/i/teamlogos/mlb/500/bal.png", "#df4601", listOf("BAL", "Baltimore", "Orioles")),
        FavoriteTeamEntity("2", "Boston Red Sox", "BOS", Sport.MLB, "https://a.espncdn.com/i/teamlogos/mlb/500/bos.png", "#0d2b56", listOf("BOS", "Boston", "Red Sox")),
        FavoriteTeamEntity("16", "Chicago Cubs", "CHC", Sport.MLB, "https://a.espncdn.com/i/teamlogos/mlb/500/chc.png", "#0e3386", listOf("CHC", "Chicago", "Cubs")),
        FavoriteTeamEntity("4", "Chicago White Sox", "CHW", Sport.MLB, "https://a.espncdn.com/i/teamlogos/mlb/500/chw.png", "#000000", listOf("CHW", "Chicago", "White Sox")),
        FavoriteTeamEntity("17", "Cincinnati Reds", "CIN", Sport.MLB, "https://a.espncdn.com/i/teamlogos/mlb/500/cin.png", "#c6011f", listOf("CIN", "Cincinnati", "Reds")),
        FavoriteTeamEntity("5", "Cleveland Guardians", "CLE", Sport.MLB, "https://a.espncdn.com/i/teamlogos/mlb/500/cle.png", "#002b5c", listOf("CLE", "Cleveland", "Guardians")),
        FavoriteTeamEntity("27", "Colorado Rockies", "COL", Sport.MLB, "https://a.espncdn.com/i/teamlogos/mlb/500/col.png", "#33006f", listOf("COL", "Colorado", "Rockies")),
        FavoriteTeamEntity("6", "Detroit Tigers", "DET", Sport.MLB, "https://a.espncdn.com/i/teamlogos/mlb/500/det.png", "#0a2240", listOf("DET", "Detroit", "Tigers")),
        FavoriteTeamEntity("18", "Houston Astros", "HOU", Sport.MLB, "https://a.espncdn.com/i/teamlogos/mlb/500/hou.png", "#002d62", listOf("Astros", "HOU", "Houston")),
        FavoriteTeamEntity("7", "Kansas City Royals", "KC", Sport.MLB, "https://a.espncdn.com/i/teamlogos/mlb/500/kc.png", "#004687", listOf("KC", "Kansas City", "Royals")),
        FavoriteTeamEntity("3", "Los Angeles Angels", "LAA", Sport.MLB, "https://a.espncdn.com/i/teamlogos/mlb/500/laa.png", "#ba0021", listOf("Angels", "LAA", "Los Angeles")),
        FavoriteTeamEntity("19", "Los Angeles Dodgers", "LAD", Sport.MLB, "https://a.espncdn.com/i/teamlogos/mlb/500/lad.png", "#005a9c", listOf("Dodgers", "LAD", "Los Angeles")),
        FavoriteTeamEntity("28", "Miami Marlins", "MIA", Sport.MLB, "https://a.espncdn.com/i/teamlogos/mlb/500/mia.png", "#00a3e0", listOf("Canes", "Hurricanes", "MIA", "Marlins", "Miami")),
        FavoriteTeamEntity("8", "Milwaukee Brewers", "MIL", Sport.MLB, "https://a.espncdn.com/i/teamlogos/mlb/500/mil.png", "#13294b", listOf("Brewers", "MIL", "Milwaukee")),
        FavoriteTeamEntity("9", "Minnesota Twins", "MIN", Sport.MLB, "https://a.espncdn.com/i/teamlogos/mlb/500/min.png", "#031f40", listOf("MIN", "Minnesota", "Twins")),
        FavoriteTeamEntity("21", "New York Mets", "NYM", Sport.MLB, "https://a.espncdn.com/i/teamlogos/mlb/500/nym.png", "#002d72", listOf("Mets", "NYM", "New York")),
        FavoriteTeamEntity("10", "New York Yankees", "NYY", Sport.MLB, "https://a.espncdn.com/i/teamlogos/mlb/500/nyy.png", "#132448", listOf("NYY", "New York", "Yankees")),
        FavoriteTeamEntity("22", "Philadelphia Phillies", "PHI", Sport.MLB, "https://a.espncdn.com/i/teamlogos/mlb/500/phi.png", "#e81828", listOf("PHI", "Philadelphia", "Phillies")),
        FavoriteTeamEntity("23", "Pittsburgh Pirates", "PIT", Sport.MLB, "https://a.espncdn.com/i/teamlogos/mlb/500/pit.png", "#000000", listOf("PIT", "Panthers", "Pirates", "Pittsburgh")),
        FavoriteTeamEntity("25", "San Diego Padres", "SD", Sport.MLB, "https://a.espncdn.com/i/teamlogos/mlb/500/sd.png", "#2f241d", listOf("Padres", "SD", "San Diego")),
        FavoriteTeamEntity("26", "San Francisco Giants", "SF", Sport.MLB, "https://a.espncdn.com/i/teamlogos/mlb/500/sf.png", "#000000", listOf("Giants", "SF", "San Francisco")),
        FavoriteTeamEntity("12", "Seattle Mariners", "SEA", Sport.MLB, "https://a.espncdn.com/i/teamlogos/mlb/500/sea.png", "#005c5c", listOf("Mariners", "SEA", "Seattle")),
        FavoriteTeamEntity("24", "St. Louis Cardinals", "STL", Sport.MLB, "https://a.espncdn.com/i/teamlogos/mlb/500/stl.png", "#be0a14", listOf("Cardinals", "STL", "St. Louis")),
        FavoriteTeamEntity("30", "Tampa Bay Rays", "TB", Sport.MLB, "https://a.espncdn.com/i/teamlogos/mlb/500/tb.png", "#092c5c", listOf("Rays", "TB", "Tampa Bay")),
        FavoriteTeamEntity("13", "Texas Rangers", "TEX", Sport.MLB, "https://a.espncdn.com/i/teamlogos/mlb/500/tex.png", "#003278", listOf("Rangers", "TEX", "Texas")),
        FavoriteTeamEntity("14", "Toronto Blue Jays", "TOR", Sport.MLB, "https://a.espncdn.com/i/teamlogos/mlb/500/tor.png", "#134a8e", listOf("Blue Jays", "TOR", "Toronto")),
        FavoriteTeamEntity("20", "Washington Nationals", "WSH", Sport.MLB, "https://a.espncdn.com/i/teamlogos/mlb/500/wsh.png", "#ab0003", listOf("Nationals", "WSH", "Washington")),

        // NHL (All 32 teams)
        FavoriteTeamEntity("25", "Anaheim Ducks", "ANA", Sport.NHL, "https://a.espncdn.com/i/teamlogos/nhl/500/ana.png", "#fc4c02", listOf("ANA", "Anaheim", "Ducks")),
        FavoriteTeamEntity("1", "Boston Bruins", "BOS", Sport.NHL, "https://a.espncdn.com/i/teamlogos/nhl/500/bos.png", "#231f20", listOf("BOS", "Boston", "Bruins")),
        FavoriteTeamEntity("2", "Buffalo Sabres", "BUF", Sport.NHL, "https://a.espncdn.com/i/teamlogos/nhl/500/buf.png", "#00468b", listOf("BUF", "Buffalo", "Sabres")),
        FavoriteTeamEntity("3", "Calgary Flames", "CGY", Sport.NHL, "https://a.espncdn.com/i/teamlogos/nhl/500/cgy.png", "#dd1a32", listOf("CGY", "Calgary", "Flames")),
        FavoriteTeamEntity("7", "Carolina Hurricanes", "CAR", Sport.NHL, "https://a.espncdn.com/i/teamlogos/nhl/500/car.png", "#e30426", listOf("CAR", "Carolina", "Hurricanes")),
        FavoriteTeamEntity("4", "Chicago Blackhawks", "CHI", Sport.NHL, "https://a.espncdn.com/i/teamlogos/nhl/500/chi.png", "#e31937", listOf("Blackhawks", "CHI", "Chicago")),
        FavoriteTeamEntity("17", "Colorado Avalanche", "COL", Sport.NHL, "https://a.espncdn.com/i/teamlogos/nhl/500/col.png", "#860038", listOf("Avalanche", "COL", "Colorado")),
        FavoriteTeamEntity("29", "Columbus Blue Jackets", "CBJ", Sport.NHL, "https://a.espncdn.com/i/teamlogos/nhl/500/cbj.png", "#002d62", listOf("Blue Jackets", "CBJ", "Columbus")),
        FavoriteTeamEntity("9", "Dallas Stars", "DAL", Sport.NHL, "https://a.espncdn.com/i/teamlogos/nhl/500/dal.png", "#20864c", listOf("DAL", "Dallas", "Stars")),
        FavoriteTeamEntity("5", "Detroit Red Wings", "DET", Sport.NHL, "https://a.espncdn.com/i/teamlogos/nhl/500/det.png", "#e30526", listOf("DET", "Detroit", "Red Wings")),
        FavoriteTeamEntity("6", "Edmonton Oilers", "EDM", Sport.NHL, "https://a.espncdn.com/i/teamlogos/nhl/500/edm.png", "#00205b", listOf("EDM", "Edmonton", "Oilers")),
        FavoriteTeamEntity("26", "Florida Panthers", "FLA", Sport.NHL, "https://a.espncdn.com/i/teamlogos/nhl/500/fla.png", "#e51937", listOf("FLA", "Florida", "Panthers")),
        FavoriteTeamEntity("8", "Los Angeles Kings", "LA", Sport.NHL, "https://a.espncdn.com/i/teamlogos/nhl/500/la.png", "#121212", listOf("Kings", "LA", "Los Angeles")),
        FavoriteTeamEntity("30", "Minnesota Wild", "MIN", Sport.NHL, "https://a.espncdn.com/i/teamlogos/nhl/500/min.png", "#124734", listOf("MIN", "Minnesota", "Wild")),
        FavoriteTeamEntity("10", "Montreal Canadiens", "MTL", Sport.NHL, "https://a.espncdn.com/i/teamlogos/nhl/500/mtl.png", "#c41230", listOf("Canadiens", "MTL", "Montreal")),
        FavoriteTeamEntity("27", "Nashville Predators", "NSH", Sport.NHL, "https://a.espncdn.com/i/teamlogos/nhl/500/nsh.png", "#fdba31", listOf("NSH", "Nashville", "Predators")),
        FavoriteTeamEntity("11", "New Jersey Devils", "NJ", Sport.NHL, "https://a.espncdn.com/i/teamlogos/nhl/500/nj.png", "#e30b2b", listOf("Devils", "NJ", "New Jersey")),
        FavoriteTeamEntity("12", "New York Islanders", "NYI", Sport.NHL, "https://a.espncdn.com/i/teamlogos/nhl/500/nyi.png", "#00529b", listOf("Islanders", "NYI", "New York")),
        FavoriteTeamEntity("13", "New York Rangers", "NYR", Sport.NHL, "https://a.espncdn.com/i/teamlogos/nhl/500/nyr.png", "#0056ae", listOf("NYR", "New York", "Rangers")),
        FavoriteTeamEntity("14", "Ottawa Senators", "OTT", Sport.NHL, "https://a.espncdn.com/i/teamlogos/nhl/500/ott.png", "#dd1a32", listOf("OTT", "Ottawa", "Senators")),
        FavoriteTeamEntity("15", "Philadelphia Flyers", "PHI", Sport.NHL, "https://a.espncdn.com/i/teamlogos/nhl/500/phi.png", "#fe5823", listOf("Flyers", "PHI", "Philadelphia")),
        FavoriteTeamEntity("16", "Pittsburgh Penguins", "PIT", Sport.NHL, "https://a.espncdn.com/i/teamlogos/nhl/500/pit.png", "#000000", listOf("PIT", "Panthers", "Penguins", "Pittsburgh")),
        FavoriteTeamEntity("18", "San Jose Sharks", "SJ", Sport.NHL, "https://a.espncdn.com/i/teamlogos/nhl/500/sj.png", "#00788a", listOf("SJ", "San Jose", "Sharks")),
        FavoriteTeamEntity("124292", "Seattle Kraken", "SEA", Sport.NHL, "https://a.espncdn.com/i/teamlogos/nhl/500/sea.png", "#000d33", listOf("Kraken", "SEA", "Seattle")),
        FavoriteTeamEntity("19", "St. Louis Blues", "STL", Sport.NHL, "https://a.espncdn.com/i/teamlogos/nhl/500/stl.png", "#0070b9", listOf("Blues", "STL", "St. Louis")),
        FavoriteTeamEntity("20", "Tampa Bay Lightning", "TB", Sport.NHL, "https://a.espncdn.com/i/teamlogos/nhl/500/tb.png", "#003e7e", listOf("Lightning", "TB", "Tampa Bay")),
        FavoriteTeamEntity("21", "Toronto Maple Leafs", "TOR", Sport.NHL, "https://a.espncdn.com/i/teamlogos/nhl/500/tor.png", "#003e7e", listOf("Maple Leafs", "TOR", "Toronto")),
        FavoriteTeamEntity("129764", "Utah Mammoth", "UTAH", Sport.NHL, "https://a.espncdn.com/i/teamlogos/nhl/500/utah.png", "#000000", listOf("Mammoth", "UTAH", "Utah")),
        FavoriteTeamEntity("22", "Vancouver Canucks", "VAN", Sport.NHL, "https://a.espncdn.com/i/teamlogos/nhl/500/van.png", "#003e7e", listOf("Canucks", "VAN", "Vancouver")),
        FavoriteTeamEntity("37", "Vegas Golden Knights", "VGK", Sport.NHL, "https://a.espncdn.com/i/teamlogos/nhl/500/vgk.png", "#344043", listOf("Golden Knights", "VGK", "Vegas")),
        FavoriteTeamEntity("23", "Washington Capitals", "WSH", Sport.NHL, "https://a.espncdn.com/i/teamlogos/nhl/500/wsh.png", "#d71830", listOf("Capitals", "WSH", "Washington")),
        FavoriteTeamEntity("28", "Winnipeg Jets", "WPG", Sport.NHL, "https://a.espncdn.com/i/teamlogos/nhl/500/wpg.png", "#002d62", listOf("Jets", "WPG", "Winnipeg")),

        // Soccer EPL (All 20 teams)
        FavoriteTeamEntity("349", "AFC Bournemouth", "BOU", Sport.SOCCER_EPL, "https://a.espncdn.com/i/teamlogos/soccer/500/349.png", "#f42727", listOf("AFC Bournemouth", "BOU", "Bournemouth")),
        FavoriteTeamEntity("359", "Arsenal", "ARS", Sport.SOCCER_EPL, "https://a.espncdn.com/i/teamlogos/soccer/500/359.png", "#e20520", listOf("ARS", "Arsenal", "Gunners")),
        FavoriteTeamEntity("362", "Aston Villa", "AVL", Sport.SOCCER_EPL, "https://a.espncdn.com/i/teamlogos/soccer/500/362.png", "#660e36", listOf("AVL", "Aston Villa")),
        FavoriteTeamEntity("337", "Brentford", "BRE", Sport.SOCCER_EPL, "https://a.espncdn.com/i/teamlogos/soccer/500/337.png", "#f42727", listOf("BRE", "Brentford")),
        FavoriteTeamEntity("331", "Brighton & Hove Albion", "BHA", Sport.SOCCER_EPL, "https://a.espncdn.com/i/teamlogos/soccer/500/331.png", "#0606fa", listOf("BHA", "Brighton", "Brighton & Hove Albion", "Seagulls")),
        FavoriteTeamEntity("363", "Chelsea", "CHE", Sport.SOCCER_EPL, "https://a.espncdn.com/i/teamlogos/soccer/500/363.png", "#144992", listOf("Blues", "CHE", "Chelsea")),
        FavoriteTeamEntity("388", "Coventry City", "COV", Sport.SOCCER_EPL, "https://a.espncdn.com/i/teamlogos/soccer/500/388.png", "#87cced", listOf("COV", "Coventry", "Coventry City", "Sky Blues")),
        FavoriteTeamEntity("384", "Crystal Palace", "CRY", Sport.SOCCER_EPL, "https://a.espncdn.com/i/teamlogos/soccer/500/384.png", "#0202fb", listOf("C Palace", "CRY", "Crystal Palace", "Eagles")),
        FavoriteTeamEntity("368", "Everton", "EVE", Sport.SOCCER_EPL, "https://a.espncdn.com/i/teamlogos/soccer/500/368.png", "#0606fa", listOf("EVE", "Everton", "Toffees")),
        FavoriteTeamEntity("370", "Fulham", "FUL", Sport.SOCCER_EPL, "https://a.espncdn.com/i/teamlogos/soccer/500/370.png", "#ffffff", listOf("FUL", "Fulham")),
        FavoriteTeamEntity("306", "Hull City", "HUL", Sport.SOCCER_EPL, "https://a.espncdn.com/i/teamlogos/soccer/500/306.png", "#f28800", listOf("HUL", "Hull", "Hull City", "Tigers")),
        FavoriteTeamEntity("373", "Ipswich Town", "IPS", Sport.SOCCER_EPL, "https://a.espncdn.com/i/teamlogos/soccer/500/373.png", "#0000fa", listOf("IPS", "Ipswich", "Ipswich Town", "Tractor Boys")),
        FavoriteTeamEntity("357", "Leeds United", "LEE", Sport.SOCCER_EPL, "https://a.espncdn.com/i/teamlogos/soccer/500/357.png", "#ffffff", listOf("LEE", "Leeds", "Leeds United")),
        FavoriteTeamEntity("364", "Liverpool", "LIV", Sport.SOCCER_EPL, "https://a.espncdn.com/i/teamlogos/soccer/500/364.png", "#d11317", listOf("LIV", "Liverpool", "Reds")),
        FavoriteTeamEntity("382", "Manchester City", "MNC", Sport.SOCCER_EPL, "https://a.espncdn.com/i/teamlogos/soccer/500/382.png", "#99c5ea", listOf("MNC", "Man City", "Manchester City", "Sky Blues")),
        FavoriteTeamEntity("360", "Manchester United", "MAN", Sport.SOCCER_EPL, "https://a.espncdn.com/i/teamlogos/soccer/500/360.png", "#da020e", listOf("MAN", "Man United", "Manchester United", "Red Devils")),
        FavoriteTeamEntity("361", "Newcastle United", "NEW", Sport.SOCCER_EPL, "https://a.espncdn.com/i/teamlogos/soccer/500/361.png", "#000000", listOf("Magpies/Toon", "NEW", "Newcastle", "Newcastle United")),
        FavoriteTeamEntity("393", "Nottingham Forest", "NFO", Sport.SOCCER_EPL, "https://a.espncdn.com/i/teamlogos/soccer/500/393.png", "#c8102e", listOf("NFO", "Nottingham F.", "Nottingham Forest", "Nottm Forest")),
        FavoriteTeamEntity("366", "Sunderland", "SUN", Sport.SOCCER_EPL, "https://a.espncdn.com/i/teamlogos/soccer/500/366.png", "#EB172B", listOf("Black Cats/Wearsiders", "SUN", "Sunderland")),
        FavoriteTeamEntity("367", "Tottenham Hotspur", "TOT", Sport.SOCCER_EPL, "https://a.espncdn.com/i/teamlogos/soccer/500/367.png", "#ffffff", listOf("Spurs", "TOT", "Tottenham Hotspur")),

        // Soccer MLS (All 30 teams)
        FavoriteTeamEntity("18418", "Atlanta United FC", "ATL", Sport.SOCCER_MLS, "https://a.espncdn.com/i/teamlogos/soccer/500/18418.png", "#9d2235", listOf("ATL", "Atlanta", "Atlanta United FC")),
        FavoriteTeamEntity("20906", "Austin FC", "ATX", Sport.SOCCER_MLS, "https://a.espncdn.com/i/teamlogos/soccer/500/20906.png", "#00b140", listOf("ATX", "Austin", "Austin FC")),
        FavoriteTeamEntity("9720", "CF Montréal", "MTL", Sport.SOCCER_MLS, "https://a.espncdn.com/i/teamlogos/soccer/500/9720.png", "#003da6", listOf("CF Montréal", "Impact", "MTL")),
        FavoriteTeamEntity("21300", "Charlotte FC", "CLT", Sport.SOCCER_MLS, "https://a.espncdn.com/i/teamlogos/soccer/500/21300.png", "#0085ca", listOf("CLT", "Charlotte", "Charlotte FC")),
        FavoriteTeamEntity("182", "Chicago Fire FC", "CHI", Sport.SOCCER_MLS, "https://a.espncdn.com/i/teamlogos/soccer/500/182.png", "#7ccdef", listOf("CHI", "Chicago", "Chicago Fire FC", "Fire")),
        FavoriteTeamEntity("184", "Colorado Rapids", "COL", Sport.SOCCER_MLS, "https://a.espncdn.com/i/teamlogos/soccer/500/184.png", "#8a2432", listOf("COL", "Colorado", "Colorado Rapids", "Rapids")),
        FavoriteTeamEntity("183", "Columbus Crew", "CLB", Sport.SOCCER_MLS, "https://a.espncdn.com/i/teamlogos/soccer/500/183.png", "#000000", listOf("CLB", "Columbus", "Columbus Crew", "Crew")),
        FavoriteTeamEntity("193", "D.C. United", "DC", Sport.SOCCER_MLS, "https://a.espncdn.com/i/teamlogos/soccer/500/193.png", "#000000", listOf("D.C. United", "DC", "United")),
        FavoriteTeamEntity("18267", "FC Cincinnati", "CIN", Sport.SOCCER_MLS, "https://a.espncdn.com/i/teamlogos/soccer/500/18267.png", "#003087", listOf("CIN", "Cincinnati", "FC Cincinnati")),
        FavoriteTeamEntity("185", "FC Dallas", "DAL", Sport.SOCCER_MLS, "https://a.espncdn.com/i/teamlogos/soccer/500/185.png", "#c6093b", listOf("DAL", "Dallas", "FC Dallas")),
        FavoriteTeamEntity("6077", "Houston Dynamo FC", "HOU", Sport.SOCCER_MLS, "https://a.espncdn.com/i/teamlogos/soccer/500/6077.png", "#ff6b00", listOf("Dynamo", "HOU", "Houston", "Houston Dynamo FC")),
        FavoriteTeamEntity("20232", "Inter Miami CF", "MIA", Sport.SOCCER_MLS, "https://a.espncdn.com/i/teamlogos/soccer/500/20232.png", "#231f20", listOf("Canes", "Hurricanes", "Inter Miami CF", "MIA", "Miami")),
        FavoriteTeamEntity("187", "LA Galaxy", "LA", Sport.SOCCER_MLS, "https://a.espncdn.com/i/teamlogos/soccer/500/187.png", "#00235d", listOf("Galaxy", "LA", "LA Galaxy")),
        FavoriteTeamEntity("18966", "LAFC", "LAFC", Sport.SOCCER_MLS, "https://a.espncdn.com/i/teamlogos/soccer/500/18966.png", "#000000", listOf("LAFC")),
        FavoriteTeamEntity("17362", "Minnesota United FC", "MIN", Sport.SOCCER_MLS, "https://a.espncdn.com/i/teamlogos/soccer/500/17362.png", "#000000", listOf("MIN", "Minnesota", "Minnesota United FC")),
        FavoriteTeamEntity("18986", "Nashville SC", "NSH", Sport.SOCCER_MLS, "https://a.espncdn.com/i/teamlogos/soccer/500/18986.png", "#ece83a", listOf("NSH", "Nashville", "Nashville SC")),
        FavoriteTeamEntity("189", "New England Revolution", "NE", Sport.SOCCER_MLS, "https://a.espncdn.com/i/teamlogos/soccer/500/189.png", "#022166", listOf("NE", "New England", "New England Revolution", "Revolution")),
        FavoriteTeamEntity("17606", "New York City FC", "NYC", Sport.SOCCER_MLS, "https://a.espncdn.com/i/teamlogos/soccer/500/17606.png", "#9fd2ff", listOf("NYC", "NYC FC", "NYCFC", "New York City FC")),
        FavoriteTeamEntity("12011", "Orlando City SC", "ORL", Sport.SOCCER_MLS, "https://a.espncdn.com/i/teamlogos/soccer/500/12011.png", "#60269e", listOf("ORL", "Orlando", "Orlando City SC")),
        FavoriteTeamEntity("10739", "Philadelphia Union", "PHI", Sport.SOCCER_MLS, "https://a.espncdn.com/i/teamlogos/soccer/500/10739.png", "#051f31", listOf("PHI", "Philadelphia", "Philadelphia Union", "Union")),
        FavoriteTeamEntity("9723", "Portland Timbers", "POR", Sport.SOCCER_MLS, "https://a.espncdn.com/i/teamlogos/soccer/500/9723.png", "#2c5234", listOf("POR", "Portland", "Portland Timbers", "Timbers")),
        FavoriteTeamEntity("4771", "Real Salt Lake", "RSL", Sport.SOCCER_MLS, "https://a.espncdn.com/i/teamlogos/soccer/500/4771.png", "#a32035", listOf("RSL", "Real Salt Lake", "Salt Lake")),
        FavoriteTeamEntity("190", "Red Bull New York", "RBNY", Sport.SOCCER_MLS, "https://a.espncdn.com/i/teamlogos/soccer/500/190.png", "#ba0c2f", listOf("RBNY", "Red Bull NY", "Red Bull New York", "Red Bulls")),
        FavoriteTeamEntity("22529", "San Diego FC", "SD", Sport.SOCCER_MLS, "https://a.espncdn.com/i/teamlogos/soccer/500/22529.png", "#697a7C", listOf("SD", "San Diego", "San Diego FC")),
        FavoriteTeamEntity("191", "San Jose Earthquakes", "SJ", Sport.SOCCER_MLS, "https://a.espncdn.com/i/teamlogos/soccer/500/191.png", "#003da6", listOf("Earthquakes", "SJ", "San Jose", "San Jose Earthquakes")),
        FavoriteTeamEntity("9726", "Seattle Sounders FC", "SEA", Sport.SOCCER_MLS, "https://a.espncdn.com/i/teamlogos/soccer/500/9726.png", "#2dc84d", listOf("SEA", "Seattle", "Seattle Sounders FC", "Sounders")),
        FavoriteTeamEntity("186", "Sporting Kansas City", "SKC", Sport.SOCCER_MLS, "https://a.espncdn.com/i/teamlogos/soccer/500/186.png", "#a7c6ed", listOf("Kansas City", "SKC", "Sporting", "Sporting Kansas City")),
        FavoriteTeamEntity("21812", "St. Louis CITY SC", "STL", Sport.SOCCER_MLS, "https://a.espncdn.com/i/teamlogos/soccer/500/21812.png", "#ec1458", listOf("STL", "St. Louis", "St. Louis CITY SC")),
        FavoriteTeamEntity("7318", "Toronto FC", "TOR", Sport.SOCCER_MLS, "https://a.espncdn.com/i/teamlogos/soccer/500/7318.png", "#aa182c", listOf("TOR", "Toronto", "Toronto FC")),
        FavoriteTeamEntity("9727", "Vancouver Whitecaps", "VAN", Sport.SOCCER_MLS, "https://a.espncdn.com/i/teamlogos/soccer/500/9727.png", "#ffffff", listOf("VAN", "Vancouver", "Vancouver Whitecaps", "Whitecaps")),

        // WNBA (All 15 teams)
        FavoriteTeamEntity("20", "Atlanta Dream", "ATL", Sport.WNBA, "https://a.espncdn.com/i/teamlogos/wnba/500/atl.png", "#e31837", listOf("ATL", "Atlanta", "Dream")),
        FavoriteTeamEntity("19", "Chicago Sky", "CHI", Sport.WNBA, "https://a.espncdn.com/i/teamlogos/wnba/500/chi.png", "#5091cd", listOf("CHI", "Chicago", "Sky")),
        FavoriteTeamEntity("18", "Connecticut Sun", "CON", Sport.WNBA, "https://a.espncdn.com/i/teamlogos/wnba/500/con.png", "#f05023", listOf("CON", "Connecticut", "Sun")),
        FavoriteTeamEntity("3", "Dallas Wings", "DAL", Sport.WNBA, "https://a.espncdn.com/i/teamlogos/wnba/500/dal.png", "#002b5c", listOf("DAL", "Dallas", "Wings")),
        FavoriteTeamEntity("129689", "Golden State Valkyries", "GS", Sport.WNBA, "https://a.espncdn.com/i/teamlogos/wnba/500/gs.png", "#b38fcf", listOf("GS", "Golden State", "Valkyries")),
        FavoriteTeamEntity("5", "Indiana Fever", "IND", Sport.WNBA, "https://a.espncdn.com/i/teamlogos/wnba/500/ind.png", "#002d62", listOf("Fever", "IND", "Indiana")),
        FavoriteTeamEntity("17", "Las Vegas Aces", "LV", Sport.WNBA, "https://a.espncdn.com/i/teamlogos/wnba/500/lv.png", "#a7a8aa", listOf("Aces", "LV", "Las Vegas")),
        FavoriteTeamEntity("6", "Los Angeles Sparks", "LA", Sport.WNBA, "https://a.espncdn.com/i/teamlogos/wnba/500/la.png", "#552583", listOf("LA", "Los Angeles", "Sparks")),
        FavoriteTeamEntity("8", "Minnesota Lynx", "MIN", Sport.WNBA, "https://a.espncdn.com/i/teamlogos/wnba/500/min.png", "#266092", listOf("Lynx", "MIN", "Minnesota")),
        FavoriteTeamEntity("9", "New York Liberty", "NY", Sport.WNBA, "https://a.espncdn.com/i/teamlogos/wnba/500/ny.png", "#86cebc", listOf("Liberty", "NY", "New York")),
        FavoriteTeamEntity("11", "Phoenix Mercury", "PHX", Sport.WNBA, "https://a.espncdn.com/i/teamlogos/wnba/500/phx.png", "#3c286e", listOf("Mercury", "PHX", "Phoenix")),
        FavoriteTeamEntity("132052", "Portland Fire", "POR", Sport.WNBA, "https://a.espncdn.com/i/teamlogos/wnba/500/por.png", "#cee5eb", listOf("Fire", "POR", "Portland")),
        FavoriteTeamEntity("14", "Seattle Storm", "SEA", Sport.WNBA, "https://a.espncdn.com/i/teamlogos/wnba/500/sea.png", "#2c5235", listOf("SEA", "Seattle", "Storm")),
        FavoriteTeamEntity("131935", "Toronto Tempo", "TOR", Sport.WNBA, "https://a.espncdn.com/i/teamlogos/wnba/500/tor.png", "#33476D", listOf("TOR", "Tempo", "Toronto")),
        FavoriteTeamEntity("16", "Washington Mystics", "WSH", Sport.WNBA, "https://a.espncdn.com/i/teamlogos/wnba/500/wsh.png", "#e03a3e", listOf("Mystics", "WSH", "Washington")),
    )

    val CBB_SPECIFIC_TEAMS = listOf(
        FavoriteTeamEntity("2250", "Gonzaga Bulldogs", "GONZ", Sport.CBB, "https://a.espncdn.com/i/teamlogos/ncaa/500/2250.png", "#041E42", listOf("Gonzaga", "Zags", "Bulldogs", "GONZ")),
        FavoriteTeamEntity("222", "Villanova Wildcats", "NOVA", Sport.CBB, "https://a.espncdn.com/i/teamlogos/ncaa/500/222.png", "#00205B", listOf("Villanova", "Nova", "Wildcats", "NOVA")),
        FavoriteTeamEntity("269", "Marquette Golden Eagles", "MARQ", Sport.CBB, "https://a.espncdn.com/i/teamlogos/ncaa/500/269.png", "#003366", listOf("Marquette", "Golden Eagles", "MARQ")),
        FavoriteTeamEntity("156", "Creighton Bluejays", "CREI", Sport.CBB, "https://a.espncdn.com/i/teamlogos/ncaa/500/156.png", "#005CA9", listOf("Creighton", "Bluejays", "CREI")),
        FavoriteTeamEntity("46", "Georgetown Hoyas", "GTWN", Sport.CBB, "https://a.espncdn.com/i/teamlogos/ncaa/500/46.png", "#041E42", listOf("Georgetown", "Hoyas", "GTWN")),
        FavoriteTeamEntity("2599", "St. John's Red Storm", "SJU", Sport.CBB, "https://a.espncdn.com/i/teamlogos/ncaa/500/2599.png", "#BA0C2F", listOf("St. John's", "St Johns", "Red Storm", "SJU")),
        FavoriteTeamEntity("2752", "Xavier Musketeers", "XAV", Sport.CBB, "https://a.espncdn.com/i/teamlogos/ncaa/500/2752.png", "#0C2340", listOf("Xavier", "Musketeers", "XAV")),
        FavoriteTeamEntity("2550", "Seton Hall Pirates", "HALL", Sport.CBB, "https://a.espncdn.com/i/teamlogos/ncaa/500/2550.png", "#00447C", listOf("Seton Hall", "Pirates", "HALL")),
        FavoriteTeamEntity("2507", "Providence Friars", "PROV", Sport.CBB, "https://a.espncdn.com/i/teamlogos/ncaa/500/2507.png", "#000000", listOf("Providence", "Friars", "PROV")),
        FavoriteTeamEntity("2086", "Butler Bulldogs", "BUT", Sport.CBB, "https://a.espncdn.com/i/teamlogos/ncaa/500/2086.png", "#0C2340", listOf("Butler", "Bulldogs", "BUT")),
        FavoriteTeamEntity("2168", "Dayton Flyers", "DAY", Sport.CBB, "https://a.espncdn.com/i/teamlogos/ncaa/500/2168.png", "#004B87", listOf("Dayton", "Flyers", "DAY")),
        FavoriteTeamEntity("2526", "Saint Mary's Gaels", "SMC", Sport.CBB, "https://a.espncdn.com/i/teamlogos/ncaa/500/2526.png", "#062340", listOf("Saint Mary's", "St Marys", "Gaels", "SMC")),
        FavoriteTeamEntity("2670", "VCU Rams", "VCU", Sport.CBB, "https://a.espncdn.com/i/teamlogos/ncaa/500/2670.png", "#000000", listOf("VCU", "Rams")),
        FavoriteTeamEntity("21", "San Diego State Aztecs", "SDSU", Sport.CBB, "https://a.espncdn.com/i/teamlogos/ncaa/500/21.png", "#A6192E", listOf("San Diego State", "SDSU", "Aztecs"))
    )

    val SOCCER_LALIGA_TEAMS = listOf(
        FavoriteTeamEntity("86", "Real Madrid", "RMA", Sport.SOCCER_LALIGA, "https://a.espncdn.com/i/teamlogos/soccer/500/86.png", "#ffffff", listOf("Real Madrid", "RMA", "Madrid", "Los Blancos")),
        FavoriteTeamEntity("83", "Barcelona", "BAR", Sport.SOCCER_LALIGA, "https://a.espncdn.com/i/teamlogos/soccer/500/83.png", "#990000", listOf("Barcelona", "BAR", "Barca", "Blaugrana")),
        FavoriteTeamEntity("1068", "Atlético Madrid", "ATM", Sport.SOCCER_LALIGA, "https://a.espncdn.com/i/teamlogos/soccer/500/1068.png", "#ca3624", listOf("Atlético Madrid", "Atletico", "ATM", "Colchoneros")),
        FavoriteTeamEntity("93", "Athletic Club", "ATH", Sport.SOCCER_LALIGA, "https://a.espncdn.com/i/teamlogos/soccer/500/93.png", "#C8142F", listOf("Athletic Club", "ATH", "Athletic Bilbao", "Bilbao")),
        FavoriteTeamEntity("89", "Real Sociedad", "RSO", Sport.SOCCER_LALIGA, "https://a.espncdn.com/i/teamlogos/soccer/500/89.png", "#3366CC", listOf("Real Sociedad", "RSO", "La Real")),
        FavoriteTeamEntity("244", "Real Betis", "BET", Sport.SOCCER_LALIGA, "https://a.espncdn.com/i/teamlogos/soccer/500/244.png", "#288A00", listOf("Real Betis", "BET", "Betis", "Verdiblancos")),
        FavoriteTeamEntity("243", "Sevilla", "SEV", Sport.SOCCER_LALIGA, "https://a.espncdn.com/i/teamlogos/soccer/500/243.png", "#ffffff", listOf("Sevilla", "SEV", "Sevillistas")),
        FavoriteTeamEntity("102", "Villarreal", "VIL", Sport.SOCCER_LALIGA, "https://a.espncdn.com/i/teamlogos/soccer/500/102.png", "#ffff00", listOf("Villarreal", "VIL", "Yellow Submarine")),
        FavoriteTeamEntity("94", "Valencia", "VAL", Sport.SOCCER_LALIGA, "https://a.espncdn.com/i/teamlogos/soccer/500/94.png", "#ffffff", listOf("Valencia", "VAL", "Los Che")),
        FavoriteTeamEntity("85", "Celta Vigo", "CEL", Sport.SOCCER_LALIGA, "https://a.espncdn.com/i/teamlogos/soccer/500/85.png", "#6cace4", listOf("Celta Vigo", "CEL", "Celta")),
        FavoriteTeamEntity("97", "Osasuna", "OSA", Sport.SOCCER_LALIGA, "https://a.espncdn.com/i/teamlogos/soccer/500/97.png", "#cd0000", listOf("Osasuna", "OSA")),
        FavoriteTeamEntity("101", "Rayo Vallecano", "RAY", Sport.SOCCER_LALIGA, "https://a.espncdn.com/i/teamlogos/soccer/500/101.png", "#ffffff", listOf("Rayo Vallecano", "RAY", "Rayo")),
        FavoriteTeamEntity("2922", "Getafe", "GET", Sport.SOCCER_LALIGA, "https://a.espncdn.com/i/teamlogos/soccer/500/2922.png", "#0000ff", listOf("Getafe", "GET")),
        FavoriteTeamEntity("88", "Espanyol", "ESP", Sport.SOCCER_LALIGA, "https://a.espncdn.com/i/teamlogos/soccer/500/88.png", "#3366CC", listOf("Espanyol", "ESP")),
        FavoriteTeamEntity("96", "Alavés", "ALA", Sport.SOCCER_LALIGA, "https://a.espncdn.com/i/teamlogos/soccer/500/96.png", "#0000ff", listOf("Alaves", "Alavés", "ALA")),
        FavoriteTeamEntity("84", "Mallorca", "MAL", Sport.SOCCER_LALIGA, "https://a.espncdn.com/i/teamlogos/soccer/500/84.png", "#E20613", listOf("Mallorca", "MAL")),
        FavoriteTeamEntity("98", "Las Palmas", "LPA", Sport.SOCCER_LALIGA, "https://a.espncdn.com/i/teamlogos/soccer/500/98.png", "#FFD700", listOf("Las Palmas", "LPA")),
        FavoriteTeamEntity("9812", "Girona", "GIR", Sport.SOCCER_LALIGA, "https://a.espncdn.com/i/teamlogos/soccer/500/9812.png", "#CD2027", listOf("Girona", "GIR")),
        FavoriteTeamEntity("95", "Real Valladolid", "VLL", Sport.SOCCER_LALIGA, "https://a.espncdn.com/i/teamlogos/soccer/500/95.png", "#7B1FA2", listOf("Valladolid", "Real Valladolid", "VLL")),
        FavoriteTeamEntity("2926", "Leganés", "LEG", Sport.SOCCER_LALIGA, "https://a.espncdn.com/i/teamlogos/soccer/500/2926.png", "#0055A5", listOf("Leganes", "Leganés", "LEG"))
    )

    val SOCCER_UCL_TEAMS = listOf(
        FavoriteTeamEntity("86", "Real Madrid", "RMA", Sport.SOCCER_UCL, "https://a.espncdn.com/i/teamlogos/soccer/500/86.png", "#ffffff", listOf("Real Madrid", "RMA", "Madrid", "Los Blancos")),
        FavoriteTeamEntity("83", "Barcelona", "BAR", Sport.SOCCER_UCL, "https://a.espncdn.com/i/teamlogos/soccer/500/83.png", "#990000", listOf("Barcelona", "BAR", "Barca")),
        FavoriteTeamEntity("382", "Manchester City", "MNC", Sport.SOCCER_UCL, "https://a.espncdn.com/i/teamlogos/soccer/500/382.png", "#99c5ea", listOf("MNC", "Man City", "Manchester City")),
        FavoriteTeamEntity("359", "Arsenal", "ARS", Sport.SOCCER_UCL, "https://a.espncdn.com/i/teamlogos/soccer/500/359.png", "#e20520", listOf("ARS", "Arsenal", "Gunners")),
        FavoriteTeamEntity("364", "Liverpool", "LIV", Sport.SOCCER_UCL, "https://a.espncdn.com/i/teamlogos/soccer/500/364.png", "#d11317", listOf("LIV", "Liverpool", "Reds")),
        FavoriteTeamEntity("132", "Bayern Munich", "MUN", Sport.SOCCER_UCL, "https://a.espncdn.com/i/teamlogos/soccer/500/132.png", "#dc052d", listOf("Bayern Munich", "Bayern", "MUN")),
        FavoriteTeamEntity("160", "Paris Saint-Germain", "PSG", Sport.SOCCER_UCL, "https://a.espncdn.com/i/teamlogos/soccer/500/160.png", "#011F68", listOf("PSG", "Paris Saint-Germain", "Paris")),
        FavoriteTeamEntity("110", "Internazionale", "INT", Sport.SOCCER_UCL, "https://a.espncdn.com/i/teamlogos/soccer/500/110.png", "#00239c", listOf("Inter", "Internazionale", "Inter Milan", "INT")),
        FavoriteTeamEntity("131", "Bayer Leverkusen", "LEV", Sport.SOCCER_UCL, "https://a.espncdn.com/i/teamlogos/soccer/500/131.png", "#E32221", listOf("Leverkusen", "Bayer Leverkusen", "LEV")),
        FavoriteTeamEntity("124", "Borussia Dortmund", "DOR", Sport.SOCCER_UCL, "https://a.espncdn.com/i/teamlogos/soccer/500/124.png", "#ffee00", listOf("Dortmund", "Borussia Dortmund", "BVB", "DOR")),
        FavoriteTeamEntity("1068", "Atlético Madrid", "ATM", Sport.SOCCER_UCL, "https://a.espncdn.com/i/teamlogos/soccer/500/1068.png", "#ca3624", listOf("Atlético Madrid", "ATM")),
        FavoriteTeamEntity("111", "Juventus", "JUV", Sport.SOCCER_UCL, "https://a.espncdn.com/i/teamlogos/soccer/500/111.png", "#000000", listOf("Juventus", "Juve", "JUV")),
        FavoriteTeamEntity("103", "AC Milan", "MIL", Sport.SOCCER_UCL, "https://a.espncdn.com/i/teamlogos/soccer/500/103.png", "#FB090B", listOf("Milan", "AC Milan", "MIL")),
        FavoriteTeamEntity("362", "Aston Villa", "AVL", Sport.SOCCER_UCL, "https://a.espncdn.com/i/teamlogos/soccer/500/362.png", "#660e36", listOf("AVL", "Aston Villa")),
        FavoriteTeamEntity("2250", "Sporting CP", "SCP", Sport.SOCCER_UCL, "https://a.espncdn.com/i/teamlogos/soccer/500/2250.png", "#008127", listOf("Sporting", "Sporting CP", "SCP")),
        FavoriteTeamEntity("238", "Benfica", "BEN", Sport.SOCCER_UCL, "https://a.espncdn.com/i/teamlogos/soccer/500/238.png", "#FF0000", listOf("Benfica", "BEN")),
        FavoriteTeamEntity("254", "Celtic", "CEL", Sport.SOCCER_UCL, "https://a.espncdn.com/i/teamlogos/soccer/500/254.png", "#018749", listOf("Celtic", "CEL")),
        FavoriteTeamEntity("142", "Feyenoord", "FEY", Sport.SOCCER_UCL, "https://a.espncdn.com/i/teamlogos/soccer/500/142.png", "#ef2f24", listOf("Feyenoord", "FEY")),
        FavoriteTeamEntity("148", "PSV Eindhoven", "PSV", Sport.SOCCER_UCL, "https://a.espncdn.com/i/teamlogos/soccer/500/148.png", "#ef2f24", listOf("PSV", "PSV Eindhoven")),
        FavoriteTeamEntity("107", "Atalanta", "ATA", Sport.SOCCER_UCL, "https://a.espncdn.com/i/teamlogos/soccer/500/107.png", "#1E71B8", listOf("Atalanta", "ATA")),
        FavoriteTeamEntity("11420", "RB Leipzig", "RBL", Sport.SOCCER_UCL, "https://a.espncdn.com/i/teamlogos/soccer/500/11420.png", "#ffffff", listOf("RB Leipzig", "Leipzig", "RBL")),
        FavoriteTeamEntity("174", "Monaco", "MON", Sport.SOCCER_UCL, "https://a.espncdn.com/i/teamlogos/soccer/500/174.png", "#E2001A", listOf("Monaco", "AS Monaco", "MON"))
    )

    val MMA_UFC_ENTITIES = listOf(
        FavoriteTeamEntity("2335639", "Jon Jones", "JONES", Sport.MMA_UFC, "https://a.espncdn.com/combiner/i?img=/i/headshots/mma/players/full/2335639.png", "#000000", listOf("Jon Jones", "Jones", "Bones")),
        FavoriteTeamEntity("3902098", "Islam Makhachev", "ISLAM", Sport.MMA_UFC, "https://a.espncdn.com/combiner/i?img=/i/headshots/mma/players/full/3902098.png", "#C8102E", listOf("Islam Makhachev", "Islam", "Makhachev")),
        FavoriteTeamEntity("4897258", "Alex Pereira", "PEREIRA", Sport.MMA_UFC, "https://a.espncdn.com/combiner/i?img=/i/headshots/mma/players/full/4897258.png", "#FFD700", listOf("Alex Pereira", "Poatan", "Pereira")),
        FavoriteTeamEntity("4285642", "Sean O'Malley", "SUGA", Sport.MMA_UFC, "https://a.espncdn.com/combiner/i?img=/i/headshots/mma/players/full/4285642.png", "#FF69B4", listOf("Sean O'Malley", "Suga", "O'Malley")),
        FavoriteTeamEntity("4684789", "Ilia Topuria", "TOPURIA", Sport.MMA_UFC, "https://a.espncdn.com/combiner/i?img=/i/headshots/mma/players/full/4684789.png", "#C8102E", listOf("Ilia Topuria", "Topuria", "El Matador")),
        FavoriteTeamEntity("4239857", "Dricus Du Plessis", "DDP", Sport.MMA_UFC, "https://a.espncdn.com/combiner/i?img=/i/headshots/mma/players/full/4239857.png", "#007A3D", listOf("Dricus Du Plessis", "DDP", "Du Plessis")),
        FavoriteTeamEntity("3902089", "Belal Muhammad", "BELAL", Sport.MMA_UFC, "https://a.espncdn.com/combiner/i?img=/i/headshots/mma/players/full/3902089.png", "#000000", listOf("Belal Muhammad", "Belal")),
        FavoriteTeamEntity("4285633", "Merab Dvalishvili", "MERAB", Sport.MMA_UFC, "https://a.espncdn.com/combiner/i?img=/i/headshots/mma/players/full/4285633.png", "#C8102E", listOf("Merab Dvalishvili", "Merab", "The Machine")),
        FavoriteTeamEntity("3012", "Alexandre Pantoja", "PANTOJA", Sport.MMA_UFC, "https://a.espncdn.com/combiner/i?img=/i/headshots/mma/players/full/3012.png", "#009B3A", listOf("Alexandre Pantoja", "Pantoja")),
        FavoriteTeamEntity("3155737", "Tom Aspinall", "ASPINALL", Sport.MMA_UFC, "https://a.espncdn.com/combiner/i?img=/i/headshots/mma/players/full/3155737.png", "#00247D", listOf("Tom Aspinall", "Aspinall")),
        FavoriteTeamEntity("3022677", "Conor McGregor", "MCGREGOR", Sport.MMA_UFC, "https://a.espncdn.com/combiner/i?img=/i/headshots/mma/players/full/3022677.png", "#169B62", listOf("Conor McGregor", "McGregor", "The Notorious")),
        FavoriteTeamEntity("2614933", "Max Holloway", "HOLLOWAY", Sport.MMA_UFC, "https://a.espncdn.com/combiner/i?img=/i/headshots/mma/players/full/2614933.png", "#000000", listOf("Max Holloway", "Holloway", "Blessed")),
        FavoriteTeamEntity("2552683", "Dustin Poirier", "POIRIER", Sport.MMA_UFC, "https://a.espncdn.com/combiner/i?img=/i/headshots/mma/players/full/2552683.png", "#000000", listOf("Dustin Poirier", "Poirier", "The Diamond")),
        FavoriteTeamEntity("2563743", "Justin Gaethje", "GAETHJE", Sport.MMA_UFC, "https://a.espncdn.com/combiner/i?img=/i/headshots/mma/players/full/2563743.png", "#000000", listOf("Justin Gaethje", "Gaethje")),
        FavoriteTeamEntity("2511419", "Charles Oliveira", "OLIVEIRA", Sport.MMA_UFC, "https://a.espncdn.com/combiner/i?img=/i/headshots/mma/players/full/2511419.png", "#009B3A", listOf("Charles Oliveira", "Oliveira", "Do Bronx")),
        FavoriteTeamEntity("4417743", "Khamzat Chimaev", "CHIMAEV", Sport.MMA_UFC, "https://a.espncdn.com/combiner/i?img=/i/headshots/mma/players/full/4417743.png", "#C8102E", listOf("Khamzat Chimaev", "Khamzat", "Borz")),
        FavoriteTeamEntity("4684784", "Shavkat Rakhmonov", "SHAVKAT", Sport.MMA_UFC, "https://a.espncdn.com/combiner/i?img=/i/headshots/mma/players/full/4684784.png", "#00AFCA", listOf("Shavkat Rakhmonov", "Shavkat")),
        FavoriteTeamEntity("4353507", "Zhang Weili", "WEILI", Sport.MMA_UFC, "https://a.espncdn.com/combiner/i?img=/i/headshots/mma/players/full/4353507.png", "#DE2910", listOf("Zhang Weili", "Weili")),
        FavoriteTeamEntity("3954752", "Valentina Shevchenko", "BULLET", Sport.MMA_UFC, "https://a.espncdn.com/combiner/i?img=/i/headshots/mma/players/full/3954752.png", "#C8102E", listOf("Valentina Shevchenko", "Shevchenko", "Bullet"))
    )

    val F1_ENTITIES = listOf(
        FavoriteTeamEntity("106842", "Ferrari", "FER", Sport.F1, "https://a.espncdn.com/i/teamlogos/racing/500/106842.png", "#DC0000", listOf("Ferrari", "Scuderia Ferrari", "FER")),
        FavoriteTeamEntity("106921", "Red Bull Racing", "RBR", Sport.F1, "https://a.espncdn.com/i/teamlogos/racing/500/106921.png", "#00327D", listOf("Red Bull", "Red Bull Racing", "RBR")),
        FavoriteTeamEntity("106892", "McLaren", "MCL", Sport.F1, "https://a.espncdn.com/i/teamlogos/racing/500/106892.png", "#FF8700", listOf("McLaren", "MCL", "Papaya")),
        FavoriteTeamEntity("106893", "Mercedes", "MER", Sport.F1, "https://a.espncdn.com/i/teamlogos/racing/500/106893.png", "#00D2BE", listOf("Mercedes", "Mercedes-AMG", "MER", "Silver Arrows")),
        FavoriteTeamEntity("123986", "Aston Martin", "AST", Sport.F1, "https://a.espncdn.com/i/teamlogos/racing/500/123986.png", "#006F62", listOf("Aston Martin", "AST")),
        FavoriteTeamEntity("106922", "Alpine", "ALP", Sport.F1, "https://a.espncdn.com/i/teamlogos/racing/500/106922.png", "#FFF500", listOf("Alpine", "ALP")),
        FavoriteTeamEntity("106967", "Williams", "WIL", Sport.F1, "https://a.espncdn.com/i/teamlogos/racing/500/106967.png", "#005AFF", listOf("Williams", "WIL")),
        FavoriteTeamEntity("111427", "Haas", "HAA", Sport.F1, "https://a.espncdn.com/i/teamlogos/racing/500/111427.png", "#B6BABD", listOf("Haas", "HAA")),
        FavoriteTeamEntity("123988", "Racing Bulls", "RB", Sport.F1, "https://a.espncdn.com/i/teamlogos/racing/500/123988.png", "#6692FF", listOf("Racing Bulls", "VCARB", "RB")),
        FavoriteTeamEntity("132212", "Audi", "AUD", Sport.F1, "https://a.espncdn.com/i/teamlogos/racing/500/132212.png", "#FF2D00", listOf("Audi", "Sauber", "AUD")),
        FavoriteTeamEntity("132211", "Cadillac", "CAD", Sport.F1, "https://a.espncdn.com/i/teamlogos/racing/500/132211.png", "#A2AAAD", listOf("Cadillac", "CAD")),
        FavoriteTeamEntity("4520", "Max Verstappen", "VER", Sport.F1, "https://a.espncdn.com/combiner/i?img=/i/headshots/racing/drivers/full/4520.png", "#00327D", listOf("Max Verstappen", "Verstappen", "VER")),
        FavoriteTeamEntity("868", "Lewis Hamilton", "HAM", Sport.F1, "https://a.espncdn.com/combiner/i?img=/i/headshots/racing/drivers/full/868.png", "#DC0000", listOf("Lewis Hamilton", "Hamilton", "HAM")),
        FavoriteTeamEntity("5498", "Charles Leclerc", "LEC", Sport.F1, "https://a.espncdn.com/combiner/i?img=/i/headshots/racing/drivers/full/5498.png", "#DC0000", listOf("Charles Leclerc", "Leclerc", "LEC")),
        FavoriteTeamEntity("5585", "Lando Norris", "NOR", Sport.F1, "https://a.espncdn.com/combiner/i?img=/i/headshots/racing/drivers/full/5585.png", "#FF8700", listOf("Lando Norris", "Norris", "NOR")),
        FavoriteTeamEntity("5693", "Oscar Piastri", "PIA", Sport.F1, "https://a.espncdn.com/combiner/i?img=/i/headshots/racing/drivers/full/5693.png", "#FF8700", listOf("Oscar Piastri", "Piastri", "PIA")),
        FavoriteTeamEntity("5534", "George Russell", "RUS", Sport.F1, "https://a.espncdn.com/combiner/i?img=/i/headshots/racing/drivers/full/5534.png", "#00D2BE", listOf("George Russell", "Russell", "RUS")),
        FavoriteTeamEntity("4738", "Carlos Sainz", "SAI", Sport.F1, "https://a.espncdn.com/combiner/i?img=/i/headshots/racing/drivers/full/4738.png", "#005AFF", listOf("Carlos Sainz", "Sainz", "SAI")),
        FavoriteTeamEntity("32", "Fernando Alonso", "ALO", Sport.F1, "https://a.espncdn.com/combiner/i?img=/i/headshots/racing/drivers/full/32.png", "#006F62", listOf("Fernando Alonso", "Alonso", "ALO"))
    )

    private val dynamicTeams = java.util.concurrent.ConcurrentHashMap<String, FavoriteTeamEntity>()

    fun registerTeam(entity: FavoriteTeamEntity) {
        val scoped = entity.ensureScoped()
        dynamicTeams[scoped.id] = scoped
        dynamicTeams[scoped.scopedId] = scoped
        dynamicTeams[scoped.rawId] = scoped
    }

    fun findById(teamId: String): FavoriteTeamEntity? {
        return ALL_TEAMS.find { it.id == teamId }
            ?: ALL_TEAMS.find { it.scopedId == teamId }
            ?: ALL_TEAMS.find { it.rawId == teamId }
            ?: dynamicTeams[teamId]
    }

    val FCS_TEAMS = listOf(
        FavoriteTeamEntity("2449", "North Dakota State Bison", "NDSU", Sport.CFB_FCS, "https://a.espncdn.com/i/teamlogos/ncaa/500/2449.png", "#0A5640", listOf("NDSU", "Bison", "North Dakota State")),
        FavoriteTeamEntity("2571", "South Dakota State Jackrabbits", "SDST", Sport.CFB_FCS, "https://a.espncdn.com/i/teamlogos/ncaa/500/2571.png", "#0033A0", listOf("SDST", "Jackrabbits", "South Dakota State")),
        FavoriteTeamEntity("149", "Montana Grizzlies", "MONT", Sport.CFB_FCS, "https://a.espncdn.com/i/teamlogos/ncaa/500/149.png", "#70002A", listOf("MONT", "Grizzlies", "Montana", "Griz")),
        FavoriteTeamEntity("147", "Montana State Bobcats", "MTST", Sport.CFB_FCS, "https://a.espncdn.com/i/teamlogos/ncaa/500/147.png", "#00205B", listOf("MTST", "Bobcats", "Montana State")),
        FavoriteTeamEntity("70", "Idaho Vandals", "IDHO", Sport.CFB_FCS, "https://a.espncdn.com/i/teamlogos/ncaa/500/70.png", "#B3995D", listOf("IDHO", "Vandals", "Idaho")),
        FavoriteTeamEntity("222", "Villanova Wildcats", "NOVA", Sport.CFB_FCS, "https://a.espncdn.com/i/teamlogos/ncaa/500/222.png", "#00205B", listOf("NOVA", "Wildcats", "Villanova")),
        FavoriteTeamEntity("48", "Delaware Blue Hens", "DEL", Sport.CFB_FCS, "https://a.espncdn.com/i/teamlogos/ncaa/500/48.png", "#00539F", listOf("DEL", "Blue Hens", "Delaware")),
        FavoriteTeamEntity("50", "Florida A&M Rattlers", "FAMU", Sport.CFB_FCS, "https://a.espncdn.com/i/teamlogos/ncaa/500/50.png", "#F89728", listOf("FAMU", "Rattlers", "Florida A&M")),
        FavoriteTeamEntity("2296", "Jackson State Tigers", "JKST", Sport.CFB_FCS, "https://a.espncdn.com/i/teamlogos/ncaa/500/2296.png", "#002147", listOf("JKST", "JSU", "Tigers", "Jackson State")),
        FavoriteTeamEntity("257", "Richmond Spiders", "RICH", Sport.CFB_FCS, "https://a.espncdn.com/i/teamlogos/ncaa/500/257.png", "#9E0712", listOf("RICH", "Spiders", "Richmond")),
        FavoriteTeamEntity("231", "Furman Paladins", "FUR", Sport.CFB_FCS, "https://a.espncdn.com/i/teamlogos/ncaa/500/231.png", "#582C83", listOf("FUR", "Paladins", "Furman")),
        FavoriteTeamEntity("107", "Holy Cross Crusaders", "HC", Sport.CFB_FCS, "https://a.espncdn.com/i/teamlogos/ncaa/500/107.png", "#582C83", listOf("HC", "Crusaders", "Holy Cross")),
        FavoriteTeamEntity("108", "Harvard Crimson", "HARV", Sport.CFB_FCS, "https://a.espncdn.com/i/teamlogos/ncaa/500/108.png", "#A51C30", listOf("HARV", "Crimson", "Harvard")),
        FavoriteTeamEntity("43", "Yale Bulldogs", "YALE", Sport.CFB_FCS, "https://a.espncdn.com/i/teamlogos/ncaa/500/43.png", "#00356B", listOf("YALE", "Bulldogs", "Yale")),
        FavoriteTeamEntity("163", "Princeton Tigers", "PRIN", Sport.CFB_FCS, "https://a.espncdn.com/i/teamlogos/ncaa/500/163.png", "#FF671F", listOf("PRIN", "Tigers", "Princeton")),
        FavoriteTeamEntity("236", "Chattanooga Mocs", "UTC", Sport.CFB_FCS, "https://a.espncdn.com/i/teamlogos/ncaa/500/236.png", "#002D62", listOf("UTC", "Mocs", "Chattanooga")),
        FavoriteTeamEntity("331", "Eastern Washington Eagles", "EWU", Sport.CFB_FCS, "https://a.espncdn.com/i/teamlogos/ncaa/500/331.png", "#A10022", listOf("EWU", "Eagles", "Eastern Washington")),
        FavoriteTeamEntity("2692", "Weber State Wildcats", "WEB", Sport.CFB_FCS, "https://a.espncdn.com/i/teamlogos/ncaa/500/2692.png", "#4B2682", listOf("WEB", "Wildcats", "Weber State")),
        FavoriteTeamEntity("302", "UC Davis Aggies", "UCD", Sport.CFB_FCS, "https://a.espncdn.com/i/teamlogos/ncaa/500/302.png", "#002855", listOf("UCD", "Aggies", "UC Davis")),
        FavoriteTeamEntity("16", "Sacramento State Hornets", "SAC", Sport.CFB_FCS, "https://a.espncdn.com/i/teamlogos/ncaa/500/16.png", "#00563F", listOf("SAC", "Hornets", "Sacramento State")),
        FavoriteTeamEntity("79", "Southern Illinois Salukis", "SIU", Sport.CFB_FCS, "https://a.espncdn.com/i/teamlogos/ncaa/500/79.png", "#720000", listOf("SIU", "Salukis", "Southern Illinois")),
        FavoriteTeamEntity("2460", "Northern Iowa Panthers", "UNI", Sport.CFB_FCS, "https://a.espncdn.com/i/teamlogos/ncaa/500/2460.png", "#4B116F", listOf("UNI", "Panthers", "Northern Iowa")),
        FavoriteTeamEntity("2754", "Youngstown State Penguins", "YSU", Sport.CFB_FCS, "https://a.espncdn.com/i/teamlogos/ncaa/500/2754.png", "#C8102E", listOf("YSU", "Penguins", "Youngstown State")),
        FavoriteTeamEntity("2624", "Tarleton State Texans", "TAR", Sport.CFB_FCS, "https://a.espncdn.com/i/teamlogos/ncaa/500/2624.png", "#4F2D7F", listOf("TAR", "Texans", "Tarleton State")),
        FavoriteTeamEntity("2382", "Mercer Bears", "MER", Sport.CFB_FCS, "https://a.espncdn.com/i/teamlogos/ncaa/500/2382.png", "#F37021", listOf("MER", "Bears", "Mercer"))
    )

    val D2_TEAMS = listOf(
        FavoriteTeamEntity("125", "Grand Valley State Lakers", "GVSU", Sport.CFB_D2, "https://a.espncdn.com/i/teamlogos/ncaa/500/125.png", "#0065A4", listOf("GVSU", "Lakers", "Grand Valley State")),
        FavoriteTeamEntity("2222", "Ferris State Bulldogs", "FRST", Sport.CFB_D2, "https://a.espncdn.com/i/teamlogos/ncaa/500/2222.png", "#BA0C2F", listOf("FRST", "Bulldogs", "Ferris State")),
        FavoriteTeamEntity("2673", "Valdosta State Blazers", "VALD", Sport.CFB_D2, "https://a.espncdn.com/i/teamlogos/ncaa/500/2673.png", "#CC0000", listOf("VALD", "Blazers", "Valdosta State")),
        FavoriteTeamEntity("90", "Pittsburg State Gorillas", "PITTST", Sport.CFB_D2, "https://a.espncdn.com/i/teamlogos/ncaa/500/90.png", "#990000", listOf("PITTST", "Gorillas", "Pittsburg State")),
        FavoriteTeamEntity("2264", "Harding Bisons", "HARD", Sport.CFB_D2, "https://a.espncdn.com/i/teamlogos/ncaa/500/2264.png", "#000000", listOf("HARD", "Bisons", "Harding")),
        FavoriteTeamEntity("2146", "Colorado School of Mines Orediggers", "CMIN", Sport.CFB_D2, "https://a.espncdn.com/i/teamlogos/ncaa/500/2146.png", "#002A5C", listOf("CMIN", "Orediggers", "Colorado Mines")),
        FavoriteTeamEntity("2566", "Slippery Rock The Rock", "ROCK", Sport.CFB_D2, "https://a.espncdn.com/i/teamlogos/ncaa/500/2566.png", "#00483A", listOf("ROCK", "The Rock", "Slippery Rock")),
        FavoriteTeamEntity("2396", "Minnesota Duluth Bulldogs", "UMD", Sport.CFB_D2, "https://a.espncdn.com/i/teamlogos/ncaa/500/2396.png", "#7A0019", listOf("UMD", "Bulldogs", "Minnesota Duluth")),
        FavoriteTeamEntity("2288", "Indianapolis Greyhounds", "UINDY", Sport.CFB_D2, "https://a.espncdn.com/i/teamlogos/ncaa/500/2288.png", "#860038", listOf("UINDY", "Greyhounds", "Indianapolis")),
        FavoriteTeamEntity("2173", "Delta State Statesmen", "DSU", Sport.CFB_D2, "https://a.espncdn.com/i/teamlogos/ncaa/500/2173.png", "#005A36", listOf("DSU", "Statesmen", "Delta State")),
        FavoriteTeamEntity("2944", "West Florida Argonauts", "UWF", Sport.CFB_D2, "https://a.espncdn.com/i/teamlogos/ncaa/500/2944.png", "#004C97", listOf("UWF", "Argos", "Argonauts", "West Florida")),
        FavoriteTeamEntity("2337", "Lenoir-Rhyne Bears", "LR", Sport.CFB_D2, "https://a.espncdn.com/i/teamlogos/ncaa/500/2337.png", "#000000", listOf("LR", "Bears", "Lenoir-Rhyne")),
        FavoriteTeamEntity("2118", "Central Missouri Mules", "UCM", Sport.CFB_D2, "https://a.espncdn.com/i/teamlogos/ncaa/500/2118.png", "#C41230", listOf("UCM", "Mules", "Central Missouri")),
        FavoriteTeamEntity("2122", "Central Oklahoma Bronchos", "UCO", Sport.CFB_D2, "https://a.espncdn.com/i/teamlogos/ncaa/500/2122.png", "#003366", listOf("UCO", "Bronchos", "Central Oklahoma")),
        FavoriteTeamEntity("2322", "Kutztown Golden Bears", "KUTZ", Sport.CFB_D2, "https://a.espncdn.com/i/teamlogos/ncaa/500/2322.png", "#5C068C", listOf("KUTZ", "Golden Bears", "Kutztown")),
        FavoriteTeamEntity("2702", "West Chester Golden Rams", "WCU", Sport.CFB_D2, "https://a.espncdn.com/i/teamlogos/ncaa/500/2702.png", "#5C068C", listOf("WCU", "Golden Rams", "West Chester")),
        FavoriteTeamEntity("2458", "Northwest Missouri State Bearcats", "NWMS", Sport.CFB_D2, "https://a.espncdn.com/i/teamlogos/ncaa/500/2458.png", "#006747", listOf("NWMS", "Bearcats", "Northwest Missouri State")),
        FavoriteTeamEntity("2395", "Minnesota State Mavericks", "MNSU", Sport.CFB_D2, "https://a.espncdn.com/i/teamlogos/ncaa/500/2395.png", "#4E2683", listOf("MNSU", "Mavericks", "Minnesota State")),
        FavoriteTeamEntity("2042", "Augustana (SD) Vikings", "AUGSD", Sport.CFB_D2, "https://a.espncdn.com/i/teamlogos/ncaa/500/2042.png", "#002D62", listOf("AUGSD", "Vikings", "Augustana")),
        FavoriteTeamEntity("2619", "Texas A&M-Kingsville Javelinas", "TAMUK", Sport.CFB_D2, "https://a.espncdn.com/i/teamlogos/ncaa/500/2619.png", "#00205B", listOf("TAMUK", "Javelinas", "Texas A&M-Kingsville")),
        FavoriteTeamEntity("2019", "Angelo State Rams", "ANG", Sport.CFB_D2, "https://a.espncdn.com/i/teamlogos/ncaa/500/2019.png", "#00205B", listOf("ANG", "Rams", "Angelo State")),
        FavoriteTeamEntity("2716", "Wingate Bulldogs", "WING", Sport.CFB_D2, "https://a.espncdn.com/i/teamlogos/ncaa/500/2716.png", "#00205B", listOf("WING", "Bulldogs", "Wingate")),
        FavoriteTeamEntity("2681", "Virginia State Trojans", "VSU", Sport.CFB_D2, "https://a.espncdn.com/i/teamlogos/ncaa/500/2681.png", "#F47920", listOf("VSU", "Trojans", "Virginia State")),
        FavoriteTeamEntity("2662", "Tuskegee Golden Tigers", "TUSK", Sport.CFB_D2, "https://a.espncdn.com/i/teamlogos/ncaa/500/2662.png", "#990000", listOf("TUSK", "Golden Tigers", "Tuskegee")),
        FavoriteTeamEntity("2389", "Miles Golden Bears", "MILES", Sport.CFB_D2, "https://a.espncdn.com/i/teamlogos/ncaa/500/2389.png", "#582C83", listOf("MILES", "Golden Bears", "Miles"))
    )

    val D3_TEAMS = listOf(
        FavoriteTeamEntity("426", "Mount Union Purple Raiders", "UMU", Sport.CFB_D3, "https://a.espncdn.com/i/teamlogos/ncaa/500/426.png", "#4F2683", listOf("UMU", "Purple Raiders", "Mount Union")),
        FavoriteTeamEntity("2445", "North Central (IL) Cardinals", "NCC", Sport.CFB_D3, "https://a.espncdn.com/i/teamlogos/ncaa/500/2445.png", "#CC0000", listOf("NCC", "Cardinals", "North Central")),
        FavoriteTeamEntity("2371", "Mary Hardin-Baylor Crusaders", "UMHB", Sport.CFB_D3, "https://a.espncdn.com/i/teamlogos/ncaa/500/2371.png", "#4E2582", listOf("UMHB", "Cru", "Crusaders", "Mary Hardin-Baylor")),
        FavoriteTeamEntity("2727", "Wisconsin-Whitewater Warhawks", "UWW", Sport.CFB_D3, "https://a.espncdn.com/i/teamlogos/ncaa/500/2727.png", "#5B2C82", listOf("UWW", "Warhawks", "Wisconsin-Whitewater")),
        FavoriteTeamEntity("2686", "Wartburg Knights", "WART", Sport.CFB_D3, "https://a.espncdn.com/i/teamlogos/ncaa/500/2686.png", "#FA4616", listOf("WART", "Knights", "Wartburg")),
        FavoriteTeamEntity("130", "Johns Hopkins Blue Jays", "JHU", Sport.CFB_D3, "https://a.espncdn.com/i/teamlogos/ncaa/500/130.png", "#68ACE5", listOf("JHU", "Blue Jays", "Johns Hopkins")),
        FavoriteTeamEntity("2340", "Linfield Wildcats", "LINF", Sport.CFB_D3, "https://a.espncdn.com/i/teamlogos/ncaa/500/2340.png", "#68132B", listOf("LINF", "Wildcats", "Linfield")),
        FavoriteTeamEntity("2156", "Cortland Red Dragons", "CORT", Sport.CFB_D3, "https://a.espncdn.com/i/teamlogos/ncaa/500/2156.png", "#B30838", listOf("CORT", "Red Dragons", "Cortland")),
        FavoriteTeamEntity("2295", "Ithaca Bombers", "ITH", Sport.CFB_D3, "https://a.espncdn.com/i/teamlogos/ncaa/500/2295.png", "#00205B", listOf("ITH", "Bombers", "Ithaca")),
        FavoriteTeamEntity("2709", "Wheaton (IL) Thunder", "WHEIL", Sport.CFB_D3, "https://a.espncdn.com/i/teamlogos/ncaa/500/2709.png", "#F47920", listOf("WHEIL", "Thunder", "Wheaton")),
        FavoriteTeamEntity("2720", "Wisconsin-La Crosse Eagles", "UWL", Sport.CFB_D3, "https://a.espncdn.com/i/teamlogos/ncaa/500/2720.png", "#7A0019", listOf("UWL", "Eagles", "Wisconsin-La Crosse")),
        FavoriteTeamEntity("2723", "Wisconsin-River Falls Falcons", "UWRF", Sport.CFB_D3, "https://a.espncdn.com/i/teamlogos/ncaa/500/2723.png", "#CC0000", listOf("UWRF", "Falcons", "Wisconsin-River Falls")),
        FavoriteTeamEntity("2063", "Bethel (MN) Royals", "BTHMN", Sport.CFB_D3, "https://a.espncdn.com/i/teamlogos/ncaa/500/2063.png", "#002855", listOf("BTHMN", "Royals", "Bethel")),
        FavoriteTeamEntity("2540", "Saint John's (MN) Johnnies", "SJU", Sport.CFB_D3, "https://a.espncdn.com/i/teamlogos/ncaa/500/2540.png", "#C8102E", listOf("SJU", "Johnnies", "Saint John's")),
        FavoriteTeamEntity("2265", "Hardin-Simmons Cowboys", "HSU", Sport.CFB_D3, "https://a.espncdn.com/i/teamlogos/ncaa/500/2265.png", "#5C068C", listOf("HSU", "Cowboys", "Hardin-Simmons")),
        FavoriteTeamEntity("2515", "Randolph-Macon Yellow Jackets", "RMC", Sport.CFB_D3, "https://a.espncdn.com/i/teamlogos/ncaa/500/2515.png", "#000000", listOf("RMC", "Yellow Jackets", "Randolph-Macon")),
        FavoriteTeamEntity("2607", "Susquehanna River Hawks", "SUSQ", Sport.CFB_D3, "https://a.espncdn.com/i/teamlogos/ncaa/500/2607.png", "#F47920", listOf("SUSQ", "River Hawks", "Susquehanna")),
        FavoriteTeamEntity("2655", "Trinity (TX) Tigers", "TRIN", Sport.CFB_D3, "https://a.espncdn.com/i/teamlogos/ncaa/500/2655.png", "#990000", listOf("TRIN", "Tigers", "Trinity")),
        FavoriteTeamEntity("2175", "DePauw Tigers", "DEPAUW", Sport.CFB_D3, "https://a.espncdn.com/i/teamlogos/ncaa/500/2175.png", "#000000", listOf("DEPAUW", "Tigers", "DePauw")),
        FavoriteTeamEntity("2682", "Wabash Little Giants", "WAB", Sport.CFB_D3, "https://a.espncdn.com/i/teamlogos/ncaa/500/2682.png", "#CC0000", listOf("WAB", "Little Giants", "Wabash")),
        FavoriteTeamEntity("2827", "Rowan Profs", "ROW", Sport.CFB_D3, "https://a.espncdn.com/i/teamlogos/ncaa/500/2827.png", "#4A154B", listOf("ROW", "Profs", "Rowan")),
        FavoriteTeamEntity("2078", "Brockport Golden Eagles", "BROCK", Sport.CFB_D3, "https://a.espncdn.com/i/teamlogos/ncaa/500/2078.png", "#00471B", listOf("BROCK", "Golden Eagles", "Brockport")),
        FavoriteTeamEntity("2545", "Salisbury Sea Gulls", "SALIS", Sport.CFB_D3, "https://a.espncdn.com/i/teamlogos/ncaa/500/2545.png", "#990000", listOf("SALIS", "Sea Gulls", "Salisbury")),
        FavoriteTeamEntity("2130", "Christopher Newport Captains", "CNU", Sport.CFB_D3, "https://a.espncdn.com/i/teamlogos/ncaa/500/2130.png", "#00205B", listOf("CNU", "Captains", "Christopher Newport"))
    )

    val ALL_TEAMS: List<FavoriteTeamEntity> by lazy {
        val baseScoped = BASE_TEAMS.map { it.ensureScoped() }
        val cbbSpecificScoped = CBB_SPECIFIC_TEAMS.map { it.ensureScoped() }
        val collegeBase = baseScoped.filter { it.sport == Sport.CFB } + cbbSpecificScoped
        val cbbFromCollege = collegeBase.map { it.copy(id = "cbb_${it.rawId}", sport = Sport.CBB) }
        val wcbbFromCollege = collegeBase.map { it.copy(id = "wcbb_${it.rawId}", sport = Sport.WCBB) }
        val collegeBaseballFromCollege = collegeBase.map { it.copy(id = "college-baseball_${it.rawId}", sport = Sport.COLLEGE_BASEBALL) }

        val otherScoped = (SOCCER_LALIGA_TEAMS + SOCCER_UCL_TEAMS + MMA_UFC_ENTITIES + F1_ENTITIES).map { it.ensureScoped() }
        val fcsScoped = FCS_TEAMS.map { it.ensureScoped() }
        val d2Scoped = D2_TEAMS.map { it.ensureScoped() }
        val d3Scoped = D3_TEAMS.map { it.ensureScoped() }

        baseScoped +
            cbbSpecificScoped +
            cbbFromCollege +
            wcbbFromCollege +
            collegeBaseballFromCollege +
            otherScoped +
            fcsScoped +
            d2Scoped +
            d3Scoped
    }

    val POPULAR_TEAMS: List<FavoriteTeamEntity> by lazy {
        val featuredCbb = listOf(
            FavoriteTeamEntity("150", "Duke Blue Devils", "DUKE", Sport.CBB, "https://a.espncdn.com/i/teamlogos/ncaa/500/150.png", "#003087", listOf("Duke", "Blue Devils", "DUKE")),
            FavoriteTeamEntity("153", "North Carolina Tar Heels", "UNC", Sport.CBB, "https://a.espncdn.com/i/teamlogos/ncaa/500/153.png", "#7BAFD4", listOf("North Carolina", "UNC", "Tar Heels")),
            FavoriteTeamEntity("2305", "Kansas Jayhawks", "KU", Sport.CBB, "https://a.espncdn.com/i/teamlogos/ncaa/500/2305.png", "#0051BA", listOf("Kansas", "KU", "Jayhawks")),
            FavoriteTeamEntity("96", "Kentucky Wildcats", "UK", Sport.CBB, "https://a.espncdn.com/i/teamlogos/ncaa/500/96.png", "#0033A0", listOf("Kentucky", "UK", "Wildcats")),
            FavoriteTeamEntity("41", "UConn Huskies", "CONN", Sport.CBB, "https://a.espncdn.com/i/teamlogos/ncaa/500/41.png", "#000E2F", listOf("UConn", "Connecticut", "Huskies")),
            FavoriteTeamEntity("2250", "Gonzaga Bulldogs", "GONZ", Sport.CBB, "https://a.espncdn.com/i/teamlogos/ncaa/500/2250.png", "#041E42", listOf("Gonzaga", "Zags", "Bulldogs")),
            FavoriteTeamEntity("2509", "Purdue Boilermakers", "PUR", Sport.CBB, "https://a.espncdn.com/i/teamlogos/ncaa/500/2509.png", "#CEB888", listOf("Purdue", "Boilermakers")),
            FavoriteTeamEntity("248", "Houston Cougars", "HOU", Sport.CBB, "https://a.espncdn.com/i/teamlogos/ncaa/500/248.png", "#C8102E", listOf("Houston", "Cougars")),
            FavoriteTeamEntity("145", "Ole Miss Rebels", "MISS", Sport.CBB, "https://a.espncdn.com/i/teamlogos/ncaa/500/145.png", "#14213D", listOf("Ole Miss", "Mississippi", "Rebels")),
            FavoriteTeamEntity("12", "Arizona Wildcats", "ARIZ", Sport.CBB, "https://a.espncdn.com/i/teamlogos/ncaa/500/12.png", "#CC0033", listOf("Arizona", "Wildcats")),
            FavoriteTeamEntity("2", "Auburn Tigers", "AUB", Sport.CBB, "https://a.espncdn.com/i/teamlogos/ncaa/500/2.png", "#002B5C", listOf("Auburn", "Tigers")),
            FavoriteTeamEntity("333", "Alabama Crimson Tide", "ALA", Sport.CBB, "https://a.espncdn.com/i/teamlogos/ncaa/500/333.png", "#9E1B32", listOf("Alabama", "Crimson Tide")),
            FavoriteTeamEntity("2633", "Tennessee Volunteers", "TENN", Sport.CBB, "https://a.espncdn.com/i/teamlogos/ncaa/500/2633.png", "#FF8200", listOf("Tennessee", "Vols")),
            FavoriteTeamEntity("269", "Marquette Golden Eagles", "MARQ", Sport.CBB, "https://a.espncdn.com/i/teamlogos/ncaa/500/269.png", "#003366", listOf("Marquette", "Golden Eagles")),
            FavoriteTeamEntity("222", "Villanova Wildcats", "NOVA", Sport.CBB, "https://a.espncdn.com/i/teamlogos/ncaa/500/222.png", "#00205B", listOf("Villanova", "Wildcats")),
            FavoriteTeamEntity("156", "Creighton Bluejays", "CREI", Sport.CBB, "https://a.espncdn.com/i/teamlogos/ncaa/500/156.png", "#005CA9", listOf("Creighton", "Bluejays"))
        ).map { it.ensureScoped() }

        val featuredWcbb = listOf(
            FavoriteTeamEntity("2579", "South Carolina Gamecocks", "SC", Sport.WCBB, "https://a.espncdn.com/i/teamlogos/ncaa/500/2579.png", "#73000a", listOf("South Carolina", "Gamecocks", "SC")),
            FavoriteTeamEntity("2294", "Iowa Hawkeyes", "IOWA", Sport.WCBB, "https://a.espncdn.com/i/teamlogos/ncaa/500/2294.png", "#000000", listOf("Iowa", "Hawkeyes", "Caitlin")),
            FavoriteTeamEntity("41", "UConn Huskies", "CONN", Sport.WCBB, "https://a.espncdn.com/i/teamlogos/ncaa/500/41.png", "#000E2F", listOf("UConn", "Connecticut", "Huskies")),
            FavoriteTeamEntity("99", "LSU Tigers", "LSU", Sport.WCBB, "https://a.espncdn.com/i/teamlogos/ncaa/500/99.png", "#461d7c", listOf("LSU", "Tigers")),
            FavoriteTeamEntity("251", "Texas Longhorns", "TEX", Sport.WCBB, "https://a.espncdn.com/i/teamlogos/ncaa/500/251.png", "#c05a11", listOf("Texas", "Longhorns", "TEX")),
            FavoriteTeamEntity("24", "Stanford Cardinal", "STAN", Sport.WCBB, "https://a.espncdn.com/i/teamlogos/ncaa/500/24.png", "#8c1515", listOf("Stanford", "Cardinal", "STAN")),
            FavoriteTeamEntity("145", "Ole Miss Rebels", "MISS", Sport.WCBB, "https://a.espncdn.com/i/teamlogos/ncaa/500/145.png", "#14213D", listOf("Ole Miss", "Mississippi", "Rebels")),
            FavoriteTeamEntity("30", "USC Trojans", "USC", Sport.WCBB, "https://a.espncdn.com/i/teamlogos/ncaa/500/30.png", "#990000", listOf("USC", "Trojans", "JuJu")),
            FavoriteTeamEntity("87", "Notre Dame Fighting Irish", "ND", Sport.WCBB, "https://a.espncdn.com/i/teamlogos/ncaa/500/87.png", "#0c2340", listOf("Notre Dame", "Irish", "ND")),
            FavoriteTeamEntity("2633", "Tennessee Lady Vols", "TENN", Sport.WCBB, "https://a.espncdn.com/i/teamlogos/ncaa/500/2633.png", "#FF8200", listOf("Tennessee", "Lady Vols", "TENN"))
        ).map { it.ensureScoped() }

        val featuredSoccer = listOf(
            FavoriteTeamEntity("86", "Real Madrid", "RMA", Sport.SOCCER_LALIGA, "https://a.espncdn.com/i/teamlogos/soccer/500/86.png", "#ffffff", listOf("Real Madrid", "RMA")),
            FavoriteTeamEntity("83", "Barcelona", "BAR", Sport.SOCCER_LALIGA, "https://a.espncdn.com/i/teamlogos/soccer/500/83.png", "#990000", listOf("Barcelona", "BAR")),
            FavoriteTeamEntity("382", "Manchester City", "MNC", Sport.SOCCER_UCL, "https://a.espncdn.com/i/teamlogos/soccer/500/382.png", "#99c5ea", listOf("Man City", "MNC")),
            FavoriteTeamEntity("132", "Bayern Munich", "MUN", Sport.SOCCER_UCL, "https://a.espncdn.com/i/teamlogos/soccer/500/132.png", "#dc052d", listOf("Bayern Munich", "MUN")),
            FavoriteTeamEntity("20232", "Inter Miami CF", "MIA", Sport.SOCCER_MLS, "https://a.espncdn.com/i/teamlogos/soccer/500/20232.png", "#231f20", listOf("Inter Miami", "Messi", "MIA"))
        ).map { it.ensureScoped() }

        val featuredUfc = listOf(
            FavoriteTeamEntity("2335639", "Jon Jones", "JONES", Sport.MMA_UFC, "https://a.espncdn.com/combiner/i?img=/i/headshots/mma/players/full/2335639.png", "#000000", listOf("Jon Jones", "Jones")),
            FavoriteTeamEntity("4897258", "Alex Pereira", "PEREIRA", Sport.MMA_UFC, "https://a.espncdn.com/combiner/i?img=/i/headshots/mma/players/full/4897258.png", "#FFD700", listOf("Alex Pereira", "Poatan")),
            FavoriteTeamEntity("3902098", "Islam Makhachev", "ISLAM", Sport.MMA_UFC, "https://a.espncdn.com/combiner/i?img=/i/headshots/mma/players/full/3902098.png", "#C8102E", listOf("Islam Makhachev", "Islam")),
            FavoriteTeamEntity("3022677", "Conor McGregor", "MCGREGOR", Sport.MMA_UFC, "https://a.espncdn.com/combiner/i?img=/i/headshots/mma/players/full/3022677.png", "#169B62", listOf("Conor McGregor", "McGregor"))
        ).map { it.ensureScoped() }

        val featuredF1 = listOf(
            FavoriteTeamEntity("106842", "Ferrari", "FER", Sport.F1, "https://a.espncdn.com/i/teamlogos/racing/500/106842.png", "#DC0000", listOf("Ferrari", "FER")),
            FavoriteTeamEntity("106921", "Red Bull Racing", "RBR", Sport.F1, "https://a.espncdn.com/i/teamlogos/racing/500/106921.png", "#00327D", listOf("Red Bull", "RBR")),
            FavoriteTeamEntity("4520", "Max Verstappen", "VER", Sport.F1, "https://a.espncdn.com/combiner/i?img=/i/headshots/racing/drivers/full/4520.png", "#00327D", listOf("Max Verstappen", "VER")),
            FavoriteTeamEntity("868", "Lewis Hamilton", "HAM", Sport.F1, "https://a.espncdn.com/combiner/i?img=/i/headshots/racing/drivers/full/868.png", "#DC0000", listOf("Lewis Hamilton", "HAM"))
        ).map { it.ensureScoped() }

        featuredCbb + featuredWcbb + featuredSoccer + featuredUfc + featuredF1 +
            FCS_TEAMS.take(10).map { it.ensureScoped() } +
            D2_TEAMS.take(10).map { it.ensureScoped() } +
            D3_TEAMS.take(10).map { it.ensureScoped() } +
            BASE_TEAMS.take(25).map { it.ensureScoped() }
    }

    val CONFERENCES: List<ConferenceEntity> = listOf(
        // College Football & Basketball Major Conferences (1-A)
        ConferenceEntity(
            id = "cfb_sec",
            name = "Southeastern Conference (SEC)",
            shortName = "SEC",
            sport = Sport.CFB,
            division = "FBS / Division I",
            teamIds = setOf("333", "8", "2", "57", "61", "96", "99", "344", "142", "201", "145", "2579", "2633", "251", "245", "238")
        ),
        ConferenceEntity(
            id = "cfb_bigten",
            name = "Big Ten Conference",
            shortName = "Big Ten",
            sport = Sport.CFB,
            division = "FBS / Division I",
            teamIds = setOf("356", "84", "2294", "120", "130", "127", "135", "158", "77", "194", "2483", "213", "2509", "164", "26", "30", "264", "275")
        ),
        ConferenceEntity(
            id = "cfb_big12",
            name = "Big 12 Conference",
            shortName = "Big 12",
            sport = Sport.CFB,
            division = "FBS / Division I",
            teamIds = setOf("12", "9", "239", "252", "2132", "38", "248", "66", "2305", "2306", "197", "2628", "2641", "2116", "254", "277")
        ),
        ConferenceEntity(
            id = "cfb_acc",
            name = "Atlantic Coast Conference (ACC)",
            shortName = "ACC",
            sport = Sport.CFB,
            division = "FBS / Division I",
            teamIds = setOf("103", "25", "228", "150", "52", "59", "97", "2390", "152", "153", "221", "2567", "24", "183", "258", "259", "154")
        ),
        ConferenceEntity(
            id = "cbb_bigeast",
            name = "Big East Conference",
            shortName = "Big East",
            sport = Sport.CBB,
            division = "Division I",
            teamIds = setOf("41", "222", "269", "156", "46", "2507", "2599", "2550", "2086", "2752", "305")
        ),
        ConferenceEntity(
            id = "cfb_mwc",
            name = "Mountain West Conference (MWC)",
            shortName = "Mountain West",
            sport = Sport.CFB,
            division = "FBS / Division I",
            teamIds = setOf("2005", "68", "36", "278", "62", "2440", "167", "21", "23", "2439", "328", "2751")
        ),
        ConferenceEntity(
            id = "cfb_aac",
            name = "American Athletic Conference (AAC)",
            shortName = "American",
            sport = Sport.CFB,
            division = "FBS / Division I",
            teamIds = setOf("2429", "151", "2226", "235", "2426", "249", "242", "58", "218", "2655", "202", "5", "2636")
        ),
        ConferenceEntity(
            id = "cfb_sunbelt",
            name = "Sun Belt Conference",
            shortName = "Sun Belt",
            sport = Sport.CFB,
            division = "FBS / Division I",
            teamIds = setOf("2026", "2032", "324", "290", "2247", "256", "309", "2433", "276", "295", "6", "2572", "326", "2653")
        ),
        ConferenceEntity(
            id = "cfb_mac",
            name = "Mid-American Conference (MAC)",
            shortName = "MAC",
            sport = Sport.CFB,
            division = "FBS / Division I",
            teamIds = setOf("2006", "2050", "189", "2084", "2117", "2199", "2309", "193", "2459", "195", "2649", "2711")
        ),
        ConferenceEntity(
            id = "cfb_cusa",
            name = "Conference USA (C-USA)",
            shortName = "C-USA",
            sport = Sport.CFB,
            division = "FBS / Division I",
            teamIds = setOf("2229", "55", "338", "2335", "2348", "2393", "166", "2534", "2638", "98")
        ),

        // NFL Divisions (Professional)
        ConferenceEntity(id = "nfl_afc_east", name = "AFC East", shortName = "AFC East", sport = Sport.NFL, division = "AFC", teamIds = setOf("2", "15", "17", "20")),
        ConferenceEntity(id = "nfl_afc_north", name = "AFC North", shortName = "AFC North", sport = Sport.NFL, division = "AFC", teamIds = setOf("33", "4", "5", "23")),
        ConferenceEntity(id = "nfl_afc_south", name = "AFC South", shortName = "AFC South", sport = Sport.NFL, division = "AFC", teamIds = setOf("34", "11", "30", "10")),
        ConferenceEntity(id = "nfl_afc_west", name = "AFC West", shortName = "AFC West", sport = Sport.NFL, division = "AFC", teamIds = setOf("7", "12", "13", "24")),
        ConferenceEntity(id = "nfl_nfc_east", name = "NFC East", shortName = "NFC East", sport = Sport.NFL, division = "NFC", teamIds = setOf("6", "19", "21", "28")),
        ConferenceEntity(id = "nfl_nfc_north", name = "NFC North", shortName = "NFC North", sport = Sport.NFL, division = "NFC", teamIds = setOf("3", "8", "9", "16")),
        ConferenceEntity(id = "nfl_nfc_south", name = "NFC South", shortName = "NFC South", sport = Sport.NFL, division = "NFC", teamIds = setOf("1", "29", "18", "27")),
        ConferenceEntity(id = "nfl_nfc_west", name = "NFC West", shortName = "NFC West", sport = Sport.NFL, division = "NFC", teamIds = setOf("22", "14", "25", "26")),

        // NBA Divisions (Professional)
        ConferenceEntity(id = "nba_east_atlantic", name = "NBA Atlantic", shortName = "Atlantic", sport = Sport.NBA, division = "Eastern", teamIds = setOf("2", "17", "18", "20", "28")),
        ConferenceEntity(id = "nba_east_central", name = "NBA Central", shortName = "Central", sport = Sport.NBA, division = "Eastern", teamIds = setOf("4", "5", "8", "11", "15")),
        ConferenceEntity(id = "nba_east_southeast", name = "NBA Southeast", shortName = "Southeast", sport = Sport.NBA, division = "Eastern", teamIds = setOf("1", "30", "14", "19", "27")),
        ConferenceEntity(id = "nba_west_northwest", name = "NBA Northwest", shortName = "Northwest", sport = Sport.NBA, division = "Western", teamIds = setOf("7", "16", "25", "22", "26")),
        ConferenceEntity(id = "nba_west_pacific", name = "NBA Pacific", shortName = "Pacific", sport = Sport.NBA, division = "Western", teamIds = setOf("9", "12", "13", "21", "23")),
        ConferenceEntity(id = "nba_west_southwest", name = "NBA Southwest", shortName = "Southwest", sport = Sport.NBA, division = "Western", teamIds = setOf("6", "10", "29", "3", "24")),

        // MLB Divisions (Professional)
        ConferenceEntity(id = "mlb_al_east", name = "AL East", shortName = "AL East", sport = Sport.MLB, division = "American League", teamIds = setOf("1", "2", "10", "30", "14")),
        ConferenceEntity(id = "mlb_al_central", name = "AL Central", shortName = "AL Central", sport = Sport.MLB, division = "American League", teamIds = setOf("4", "5", "6", "7", "9")),
        ConferenceEntity(id = "mlb_al_west", name = "AL West", shortName = "AL West", sport = Sport.MLB, division = "American League", teamIds = setOf("18", "3", "11", "12", "13")),
        ConferenceEntity(id = "mlb_nl_east", name = "NL East", shortName = "NL East", sport = Sport.MLB, division = "National League", teamIds = setOf("15", "28", "21", "22", "20")),
        ConferenceEntity(id = "mlb_nl_central", name = "NL Central", shortName = "NL Central", sport = Sport.MLB, division = "National League", teamIds = setOf("16", "17", "8", "23", "24")),
        ConferenceEntity(id = "mlb_nl_west", name = "NL West", shortName = "NL West", sport = Sport.MLB, division = "National League", teamIds = setOf("29", "27", "19", "25", "26")),

        // NHL Divisions (Professional)
        ConferenceEntity(id = "nhl_atlantic", name = "Atlantic Division", shortName = "Atlantic", sport = Sport.NHL, division = "Eastern", teamIds = setOf("1", "2", "5", "26", "10", "14", "20", "21")),
        ConferenceEntity(id = "nhl_metropolitan", name = "Metropolitan Division", shortName = "Metropolitan", sport = Sport.NHL, division = "Eastern", teamIds = setOf("7", "29", "11", "12", "13", "15", "16", "23")),
        ConferenceEntity(id = "nhl_central", name = "Central Division", shortName = "Central", sport = Sport.NHL, division = "Western", teamIds = setOf("4", "6", "9", "30", "27", "19", "129714", "28")),
        ConferenceEntity(id = "nhl_pacific", name = "Pacific Division", shortName = "Pacific", sport = Sport.NHL, division = "Western", teamIds = setOf("25", "3", "8", "22", "124292", "18", "22", "37"))
    )

    fun findConferenceById(id: String): ConferenceEntity? {
        return CONFERENCES.find { it.id == id }
    }

    fun getConferencesForSport(sport: Sport): List<ConferenceEntity> {
        return CONFERENCES.filter {
            it.sport == sport || (sport in listOf(Sport.CBB, Sport.WCBB, Sport.COLLEGE_BASEBALL) && (it.sport == Sport.CFB || it.sport == Sport.CBB))
        }
    }

    fun getTeamIdsForFavoritedConferences(favoriteConferenceIds: Set<String>): Set<String> {
        if (favoriteConferenceIds.isEmpty()) return emptySet()
        val result = mutableSetOf<String>()
        for (confId in favoriteConferenceIds) {
            val conf = CONFERENCES.find { it.id == confId } ?: continue
            for (raw in conf.teamIds) {
                result.add(raw)
                result.add("cfb_$raw")
                result.add("cbb_$raw")
                result.add("wcbb_$raw")
                result.add("college-baseball_$raw")
                result.add("nfl_$raw")
                result.add("nba_$raw")
                result.add("mlb_$raw")
                result.add("nhl_$raw")
            }
        }
        return result
    }
}
