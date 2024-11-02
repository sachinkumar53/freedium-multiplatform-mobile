package com.sachin.freedium.ui.screen.article

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import buildFreediumUrl
import com.multiplatform.webview.web.LoadingState
import com.multiplatform.webview.web.WebView
import com.multiplatform.webview.web.rememberWebViewNavigator
import com.multiplatform.webview.web.rememberWebViewState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest

@Composable
fun ArticleScreen(mediumUrl: String) {
    val webViewState = rememberWebViewState(buildFreediumUrl(mediumUrl))
    val webViewNavigator = rememberWebViewNavigator()
    var forceRefresh by rememberSaveable {
        mutableStateOf(false)
    }
    LaunchedEffect(Unit) {
        webViewState.webSettings.apply {
            zoomLevel = 1.0
            supportZoom = false
            isJavaScriptEnabled = true
            androidWebSettings.apply {
                isAlgorithmicDarkeningAllowed = true
                // safeBrowsingEnabled = true
            }
        }
    }

    LaunchedEffect(Unit) {
        snapshotFlow { webViewState.loadingState }.collectLatest {
            println(it)
            if (it is LoadingState.Finished) {
                // delay(2000)
                webViewNavigator.evaluateJavaScript(
                    """
                    document.getElementById('header').style.display = 'none';;
                    document.querySelector('.fixed.bottom-4.left-4').style.display = 'none';
                """.trimIndent()
                ) { s ->
                    println(s)
                }
            }
        }
    }
    Scaffold { innerPadding ->
        Box(
            modifier = Modifier.fillMaxSize().padding(innerPadding),
            contentAlignment = Alignment.Center
        ) {
            WebView(
                state = webViewState,
                navigator = webViewNavigator
            )
            if (webViewState.isLoading) {
                CircularProgressIndicator()
            }
        }
    }
}