package com.sachin.freedium.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val primaryColor = Color(0xFF22c55e)

private val lightColors = lightColors(
    primary = primaryColor,
    background = Color.White
)

private val darkColors = darkColors(
    primary = primaryColor,
    background = Color(0xFF1f2937),
    surface = Color(0xFF4b5563)
)

@Composable
fun FreediumTheme(
    isDarkTheme: Boolean = false, //isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {

    MaterialTheme(
        colors = if (isDarkTheme) darkColors else lightColors,
        content = content
    )
}