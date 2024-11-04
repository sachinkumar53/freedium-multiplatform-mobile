package com.sachin.freedium.ui.navigation

import kotlinx.serialization.Serializable

sealed interface Route {

    @Serializable
    data object Home : Route

    @Serializable
    data class Article(val url: String) : Route
}