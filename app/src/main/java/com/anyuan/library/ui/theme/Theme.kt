package com.anyuan.library.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.anyuan.library.data.AccentPalette

private val OceanDark = darkColorScheme(
    primary = Color(0xFFA0C9FF),
    onPrimary = Color(0xFF00315A),
    primaryContainer = Color(0xFF2F6BA6),
    onPrimaryContainer = Color(0xFFD3E4FF),
    secondary = Color(0xFF9FBEDC),
    onSecondary = Color(0xFF17293D),
    secondaryContainer = Color(0xFF2E4A66),
    onSecondaryContainer = Color(0xFFC9DDF2),
    tertiary = Color(0xFFB4BEF2),
    onTertiary = Color(0xFF232859),
    tertiaryContainer = Color(0xFF3A3F6B),
    onTertiaryContainer = Color(0xFFDDE2FF),
    background = Color(0xFF0D1117),
    onBackground = Color(0xFFE1E5EC),
    surface = Color(0xFF11161D),
    onSurface = Color(0xFFE1E5EC),
    surfaceVariant = Color(0xFF2A313B),
    onSurfaceVariant = Color(0xFFA6B0BF),
    outline = Color(0xFF5A6473),
    outlineVariant = Color(0xFF333B45),
)

private val OceanLight = lightColorScheme(
    primary = Color(0xFF2C6396),
    onPrimary = Color(0xFFFFFFFF),
    primaryContainer = Color(0xFFD3E4FF),
    onPrimaryContainer = Color(0xFF001C33),
    secondary = Color(0xFF3D5C7A),
    onSecondary = Color(0xFFFFFFFF),
    secondaryContainer = Color(0xFFC2D9F0),
    onSecondaryContainer = Color(0xFF12263B),
    tertiary = Color(0xFF5A5FA0),
    onTertiary = Color(0xFFFFFFFF),
    tertiaryContainer = Color(0xFFD9DCFF),
    onTertiaryContainer = Color(0xFF0B0E42),
    background = Color(0xFFF8FAFD),
    onBackground = Color(0xFF191C21),
    surface = Color(0xFFF8FAFD),
    onSurface = Color(0xFF191C21),
    surfaceVariant = Color(0xFFDFE2EA),
    onSurfaceVariant = Color(0xFF43474E),
    outline = Color(0xFF73777F),
    outlineVariant = Color(0xFFC3C6CF),
)

private val BlushDark = darkColorScheme(
    primary = Color(0xFFFFB1C3),
    onPrimary = Color(0xFF5E1130),
    primaryContainer = Color(0xFFA84560),
    onPrimaryContainer = Color(0xFFFFD9E1),
    secondary = Color(0xFFF0A8B8),
    onSecondary = Color(0xFF4A1524),
    secondaryContainer = Color(0xFF6B3540),
    onSecondaryContainer = Color(0xFFFFD9E1),
    tertiary = Color(0xFFFFC0AD),
    onTertiary = Color(0xFF4A2010),
    tertiaryContainer = Color(0xFF6B4038),
    onTertiaryContainer = Color(0xFFFFDCCF),
    background = Color(0xFF1C1416),
    onBackground = Color(0xFFEAE0E0),
    surface = Color(0xFF1C1416),
    onSurface = Color(0xFFEAE0E0),
    surfaceVariant = Color(0xFF524345),
    onSurfaceVariant = Color(0xFFD7C1C3),
    outline = Color(0xFF9F8C8D),
    outlineVariant = Color(0xFF524345),
)

private val BlushLight = lightColorScheme(
    primary = Color(0xFF9C4F63),
    onPrimary = Color(0xFFFFFFFF),
    primaryContainer = Color(0xFFFFD9E1),
    onPrimaryContainer = Color(0xFF3C0716),
    secondary = Color(0xFFA24D5F),
    onSecondary = Color(0xFFFFFFFF),
    secondaryContainer = Color(0xFFFFD2DC),
    onSecondaryContainer = Color(0xFF35121C),
    tertiary = Color(0xFFB06A55),
    onTertiary = Color(0xFFFFFFFF),
    tertiaryContainer = Color(0xFFFFDCCF),
    onTertiaryContainer = Color(0xFF42180A),
    background = Color(0xFFFFF8F7),
    onBackground = Color(0xFF22191A),
    surface = Color(0xFFFFF8F7),
    onSurface = Color(0xFF22191A),
    surfaceVariant = Color(0xFFF3DDE0),
    onSurfaceVariant = Color(0xFF524345),
    outline = Color(0xFF857374),
    outlineVariant = Color(0xFFD7C1C3),
)

fun swatchColor(palette: AccentPalette): Color = when (palette) {
    AccentPalette.OCEAN -> Color(0xFF2C6396)
    AccentPalette.BLUSH -> Color(0xFF9C4F63)
}

@Composable
fun LibraryTheme(
    palette: AccentPalette,
    content: @Composable () -> Unit,
) {
    val dark = isSystemInDarkTheme()
    val scheme = when (palette) {
        AccentPalette.OCEAN -> if (dark) OceanDark else OceanLight
        AccentPalette.BLUSH -> if (dark) BlushDark else BlushLight
    }
    MaterialTheme(colorScheme = scheme, content = content)
}
