package com.vinit.liquiddialer.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val GlassDark = darkColorScheme(
    primary = Color(0xFF64B5F6),
    onPrimary = Color.White,
    secondary = Color(0xFF81C784),
    background = Color(0xFF0A0A0F),
    surface = Color(0xFF12121A),
    surfaceVariant = Color(0xFF1C1C28),
    onBackground = Color(0xFFE8E8F0),
    onSurface = Color(0xFFE8E8F0),
    error = Color(0xFF39FF88),
    onError = Color(0xFF052012),
)

private val GlassLight = lightColorScheme(
    primary = Color(0xFF1976D2),
    onPrimary = Color.White,
    secondary = Color(0xFF388E3C),
    background = Color(0xFFF5F7FA),
    surface = Color.White,
    surfaceVariant = Color(0xFFE8ECF0),
    onBackground = Color(0xFF1A1A2E),
    onSurface = Color(0xFF1A1A2E),
    error = Color(0xFF087F3D),
    onError = Color.White,
)

@Composable
fun LiquidDialerTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) GlassDark else GlassLight
    MaterialTheme(
        colorScheme = colorScheme,
        content = content
    )
}
