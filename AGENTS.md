# 🤖 AGENTS.md: Developer & Agent Guidelines for TheMagicScorlami

Welcome to **TheMagicScorlami**. This document provides essential architecture context, data flow diagrams, engineering conventions, and testing protocols for AI coding agents and human developers maintaining or extending this codebase.

---

## 🏛️ System Architecture & Codebase Map

The project is structured under the package root `com.themagicsportslami.app`:

```
app/src/main/java/com/themagicsportslami/app/
├── MainActivity.kt                  # Single Activity host with root backstack & screen routing
├── TheMagicSportslamiApp.kt         # Application singleton initializing repos, prefs & channels
├── data/
│   ├── model/
│   │   ├── Sport.kt                 # Enum defining 17 supported sports, endpoints, and emojis
│   │   ├── MultiSportGame.kt        # Game entity, competitor models, situations, box score schemas
│   │   ├── SportFavorites.kt       # FavoriteTeamEntity, FavoriteLeagueEntity, ConferenceEntity
│   │   └── MultiSportCatalog.kt     # Hardcoded & dynamic catalog of teams, conferences & divisions
│   ├── api/
│   │   ├── MultiSportHttpClient.kt  # OkHttp client with disk-backed cache control
│   │   └── MultiSportParser.kt      # Robust JSON parser for ESPN scoreboards & summary box scores
│   ├── local/
│   │   ├── SportslamiPreferences.kt # Jetpack DataStore preferences (favorites, themes, refresh)
│   │   └── NotificationPreferences.kt# Notification configs, delay buffers, and alert filters
│   └── repository/
│       └── MultiSportRepository.kt  # Scores engine, Top Events 3-week window, in-memory cache
├── notifications/
│   ├── SportslamiNotificationManager.kt # Pinned scoreboard & granular game alert dispatcher
│   └── SportslamiScoreWorker.kt     # WorkManager background worker for score polling
├── widget/
│   ├── MultiSportWidget.kt          # Jetpack Glance home screen widget
│   ├── MultiSportWidgetReceiver.kt  # GlanceAppWidgetReceiver broadcast handler
│   └── MultiSportWidgetWorker.kt    # Periodic Glance widget updater
└── ui/
    ├── components/
    │   ├── MultiSportGameCard.kt    # Expandable live game card with situation & scores
    │   ├── SportTabsBar.kt          # Horizontally scrollable sport filter pill chips
    │   ├── DateCarouselBar.kt       # Top Events / Day calendar carousel bar
    │   ├── BaseballDiamond.kt       # Dynamic vector diamond for baseball base runners
    │   └── TeamNotificationSheet.kt # Modal bottom sheet for team-specific alert toggles
    ├── screens/
    │   ├── MultiSportHomeScreen.kt  # Main scores board with Top Events hero card section
    │   ├── MultiSportDetailSheet.kt # ESPN-style Excel grid box scores & situation modal
    │   ├── FavoritesManagerScreen.kt# Search & toggle favorite teams and conferences
    │   └── SettingsScreen.kt        # Notification toggles, theme switcher, widget settings & legal
    └── theme/
        ├── Color.kt                 # Color palette definitions for 8 custom themes
        └── Theme.kt                 # Material 3 dynamic theme composer
```

---

## ⚡ Core Rules & Invariants

When adding features, modifying code, or fixing bugs, you **MUST** adhere to the following rules:

### 1. Top Events Engine (`selectedDateString == null`)
- When the date is `null`, the app is in **Top Events mode**.
- The repository must query an ESPN date range (`dates=YYYYMMDD-YYYYMMDD`), looking back **7 days** for recent final scores and forward **14 days** for upcoming scheduled matchups for user favorites.
- The home screen separates Top Events with a hero card deck at the top, followed by a `⚡ LIVE & TODAY'S ACTION` divider and today's games sorted with **Football (`CFB`, `NFL`)** and **Basketball (`CBB`, `WCBB`, `NBA`, `WNBA`)** prioritized first.

### 2. Conference Favoriting & Widget/Notification Isolation
- Users can favorite entire college conferences (SEC, Big Ten, Big 12, etc.) or pro divisions (AFC East, AL West, etc.).
- Favorited conferences automatically elevate all member teams into Top Events and daily feeds.
- **WIDGET ISOLATION**: Favorited conferences **MUST NOT** appear in the Glance home screen widget unless `userPreferences.widgetIncludeConferences == true`.
- **NOTIFICATION ISOLATION**: Favorited conferences **MUST NEVER** trigger automated game push notifications. Push notifications are strictly reserved for individually favorited teams (`favoriteTeamIds`) to prevent notification spam.

### 3. ESPN-Style Excel Grid Box Scores
- The Box Score tab in `MultiSportDetailSheet.kt` must follow the ESPN app design philosophy:
  - **Tappable Team Headers**: Away vs Home team tabs with logos and records to load the selected team's player statistics.
  - **Sticky First Column**: Athlete name, jersey badge (e.g. `#4`), and position abbreviation (`QB`, `RB`, etc.) stay pinned on the left during horizontal scrolling.
  - **Uniform 54dp Stat Columns**: Every stat column header and numerical row must be constrained to a uniform width (54dp) with centered alignment for a clean spreadsheet appearance.
  - **Category Totals Row**: Every category concludes with a bold, distinct `TOTALS` row summarizing completions/attempts, yards, touchdowns, etc.

### 4. Lower Division Football Isolation
- Lower division college football (`CFB_FCS`, `CFB_D2`, `CFB_D3`) games must **NEVER** be returned in the global `ALL` tab or the daily default feed.
- They must only be queried when the user explicitly taps their individual sport chips (`Sport.CFB_FCS`, `Sport.CFB_D2`, `Sport.CFB_D3`).

### 5. Jetpack Glance Widget Restrictions
- Glance AppWidgets do **NOT** support all standard Jetpack Compose modifiers or layouts (e.g., standard `Modifier.clickable` does not work in Glance; you must use `GlanceModifier.clickable(actionStartActivity<MainActivity>())`).
- Do not import standard Compose UI packages into `MultiSportWidget.kt`. Use `androidx.glance.*` and `androidx.glance.layout.*`.

---

## 🧪 Testing & Quality Assurance

Before committing any changes or preparing a release, always run the automated unit test suite:

```bash
# Run unit tests
./gradlew testDebugUnitTest
```

Key test files:
- `MultiSportParserTest.kt`: Tests JSON parsing across NFL, MLB, NHL, MLS, EPL, CBB, WCBB, WNBA, UFC, F1, FCS, D-II, ESPN box score extraction, and conference favoriting logic.
- `NotificationLogicTest.kt`: Tests alert trigger criteria, anti-spoiler delay buffers, and lead change filters.

To build signed release artifacts:
```bash
# Assemble Release APK and Play Store App Bundle (.aab)
./gradlew assembleRelease bundleRelease
```

Output paths:
- APK: `app/build/outputs/apk/release/app-release.apk`
- AAB: `app/build/outputs/bundle/release/app-release.aab`

---

## 📝 Commit & PR Conventions

- **Commit Messages**: Follow conventional commits (e.g., `feat: add conference favoriting`, `fix: box score horizontal scrolling alignment`, `docs: update AGENTS.md`).
- **Documentation**: Keep `README.md` and `AGENTS.md` synchronized whenever new sport categories, preferences, or architecture components are introduced.
