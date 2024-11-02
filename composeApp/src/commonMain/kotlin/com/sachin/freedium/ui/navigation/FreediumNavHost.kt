package com.sachin.freedium.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.sachin.freedium.SharedTextHandler
import com.sachin.freedium.ui.screen.article.ArticleScreen
import com.sachin.freedium.ui.screen.home.HomeScreen
import com.sachin.freedium.util.CollectAsEvent

@Composable
fun FreediumNavHost() {
    val navController = rememberNavController()

    CollectAsEvent(SharedTextHandler.sharedUrlFlow) { url ->
        navController.navigate(Route.Article(url)) {
            popUpTo(Route.Home) {
                inclusive = true
            }
        }
    }

    NavHost(
        navController = navController,
        startDestination = Route.Home
    ) {

        composable<Route.Home> {
            HomeScreen(
                navigateToArticleScreen = { url ->
                    navController.navigate(Route.Article(url))
                }
            )
        }

        composable<Route.Article> {
            val url = it.toRoute<Route.Article>().url
            ArticleScreen(mediumUrl = url)
        }
    }
}