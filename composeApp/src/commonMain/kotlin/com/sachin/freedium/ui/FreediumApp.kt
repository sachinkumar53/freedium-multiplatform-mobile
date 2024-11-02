package com.sachin.freedium.ui

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import com.sachin.freedium.ui.navigation.FreediumNavHost
import com.sachin.freedium.ui.theme.FreediumTheme


@Composable
fun App() {
    FreediumTheme {
        Surface(color = MaterialTheme.colors.background) {
            FreediumNavHost()
        }
    }
}
