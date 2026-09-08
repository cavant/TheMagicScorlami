package com.themagicsportslami.app.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.themagicsportslami.app.data.local.SportThemeMode

// 1. Classic Dark (Stadium Night Mode)
private val DarkColorScheme = darkColorScheme(
    primary = SalamiCoral,
    onPrimary = DarkTextPrimary,
    secondary = MagicGold,
    onSecondary = DarkBackground,
    background = DarkBackground,
    surface = DarkSurface,
    surfaceVariant = DarkSurfaceVariant,
    onBackground = DarkTextPrimary,
    onSurface = DarkTextPrimary,
    onSurfaceVariant = DarkTextSecondary,
    outline = DarkOutline
)

// 2. Light (Day Game)
private val LightColorScheme = lightColorScheme(
    primary = SalamiCrimson,
    onPrimary = LightSurface,
    secondary = MagicGold,
    onSecondary = LightTextPrimary,
    background = LightBackground,
    surface = LightSurface,
    surfaceVariant = LightSurfaceVariant,
    onBackground = LightTextPrimary,
    onSurface = LightTextPrimary,
    onSurfaceVariant = LightTextSecondary,
    outline = LightOutline
)

// 3. Scorlami Special (Crimson & Gold Mascot)
private val SalamiSpecialScheme = darkColorScheme(
    primary = MagicAmber,
    onPrimary = Color(0xFF140808),
    secondary = SalamiCoral,
    onSecondary = Color(0xFFFFFFFF),
    background = Color(0xFF140808),
    surface = Color(0xFF220E0E),
    surfaceVariant = Color(0xFF331515),
    onBackground = DarkTextPrimary,
    onSurface = DarkTextPrimary,
    onSurfaceVariant = Color(0xFFD3A4A4),
    outline = Color(0xFF4A1F1F)
)

// 4. Pure Black AMOLED (Pitch Black #000000 for OLED Battery Saver)
private val AmoledPureBlackScheme = darkColorScheme(
    primary = Color(0xFFFF5252),
    onPrimary = Color(0xFF000000),
    secondary = Color(0xFFFFD700),
    onSecondary = Color(0xFF000000),
    background = Color(0xFF000000),
    surface = Color(0xFF0A0A0A),
    surfaceVariant = Color(0xFF141414),
    onBackground = Color(0xFFFFFFFF),
    onSurface = Color(0xFFFFFFFF),
    onSurfaceVariant = Color(0xFFAAAAAA),
    outline = Color(0xFF262626)
)

// 5. Emerald Turf (Gridiron / Football field)
private val EmeraldTurfScheme = darkColorScheme(
    primary = Color(0xFF00E676),
    onPrimary = Color(0xFF05140B),
    secondary = Color(0xFFFFEB3B),
    onSecondary = Color(0xFF05140B),
    background = Color(0xFF06120B),
    surface = Color(0xFF0D2216),
    surfaceVariant = Color(0xFF143422),
    onBackground = Color(0xFFE8F5E9),
    onSurface = Color(0xFFE8F5E9),
    onSurfaceVariant = Color(0xFFA5D6A7),
    outline = Color(0xFF1E482F)
)

// 6. Midnight Navy (Ice Rink / Hockey)
private val MidnightNavyScheme = darkColorScheme(
    primary = Color(0xFF00E5FF),
    onPrimary = Color(0xFF030914),
    secondary = Color(0xFF82B1FF),
    onSecondary = Color(0xFF030914),
    background = Color(0xFF040A14),
    surface = Color(0xFF0A162B),
    surfaceVariant = Color(0xFF122442),
    onBackground = Color(0xFFE1F5FE),
    onSurface = Color(0xFFE1F5FE),
    onSurfaceVariant = Color(0xFF90CAF9),
    outline = Color(0xFF1B3864)
)

// 7. Sunset Court (Hardwood / Basketball)
private val SunsetCourtScheme = darkColorScheme(
    primary = Color(0xFFFF6D00),
    onPrimary = Color(0xFF0D0517),
    secondary = Color(0xFFFFAB00),
    onSecondary = Color(0xFF0D0517),
    background = Color(0xFF0E0716),
    surface = Color(0xFF1A0E2A),
    surfaceVariant = Color(0xFF291742),
    onBackground = Color(0xFFF3E5F5),
    onSurface = Color(0xFFF3E5F5),
    onSurfaceVariant = Color(0xFFCE93D8),
    outline = Color(0xFF3E2363)
)

@Composable
fun TheMagicSportslamiTheme(
    themeMode: SportThemeMode = SportThemeMode.SYSTEM,
    content: @Composable () -> Unit
) {
    val context = LocalContext.current
    val systemInDark = isSystemInDarkTheme()

    val colorScheme = when (themeMode) {
        SportThemeMode.DYNAMIC -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                if (systemInDark) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
            } else {
                if (systemInDark) DarkColorScheme else LightColorScheme
            }
        }
        SportThemeMode.AMOLED -> AmoledPureBlackScheme
        SportThemeMode.SALAMI -> SalamiSpecialScheme
        SportThemeMode.GRIDIRON -> EmeraldTurfScheme
        SportThemeMode.ICE_RINK -> MidnightNavyScheme
        SportThemeMode.HARDWOOD -> SunsetCourtScheme
        SportThemeMode.DARK -> DarkColorScheme
        SportThemeMode.LIGHT -> LightColorScheme
        SportThemeMode.SYSTEM -> if (systemInDark) DarkColorScheme else LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        content = content
    )
}
