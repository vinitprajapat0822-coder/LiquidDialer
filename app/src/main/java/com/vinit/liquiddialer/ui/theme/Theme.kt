package com.vinit.liquiddialer.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val GlassDark = darkColorScheme(
    primary = Color(0xFF39FF88),
    onPrimary = Color(0xFF052012),
    secondary = Color(0xFF00E676),
    onSecondary = Color(0xFF032014),
    tertiary = Color(0xFF69F0AE),
    background = Color(0xFF07110C),
    surface = Color(0xFF0D1B13),
    surfaceVariant = Color(0xFF14281C),
    onBackground = Color(0xFFE4F7EA),
    onSurface = Color(0xFFE4F7EA),
    error = Color(0xFF39FF88),
    onError = Color(0xFF052012)
)

private val GlassLight = lightColorScheme(
    primary = Color(0xFF087F3D),
    onPrimary = Color.White,
    secondary = Color(0xFF168A4A),
    onSecondary = Color.White,
    tertiary = Color(0xFF1B9A55),
    background = Color(0xFFF2FBF5),
    surface = Color.White,
    surfaceVariant = Color(0xFFE0F3E6),
    onBackground = Color(0xFF102318),
    onSurface = Color(0xFF102318),
    error = Color(0xFF087F3D),
    onError = Color.White
)

@Composable
fun LiquidDialerTheme(
    darkTheme: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) GlassDark else GlassLight
    MaterialTheme(
        colorScheme = colorScheme,
        content = content
    )
}
