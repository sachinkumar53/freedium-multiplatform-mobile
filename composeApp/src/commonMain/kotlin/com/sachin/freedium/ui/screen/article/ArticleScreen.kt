package com.sachin.freedium.ui.screen.article

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import buildFreediumUrl
import com.multiplatform.webview.web.LoadingState
import com.multiplatform.webview.web.WebView
import com.multiplatform.webview.web.rememberWebViewNavigator
import com.multiplatform.webview.web.rememberWebViewState
import kotlinx.coroutines.flow.collectLatest

@Composable
fun ArticleScreen(
    mediumUrl: String,
    viewModel: ArticleViewModel = viewModel()
) {
    val webViewState = rememberWebViewState(buildFreediumUrl(mediumUrl))
    val webViewNavigator = rememberWebViewNavigator()

    LaunchedEffect(Unit) {
        webViewState.webSettings.apply {
            zoomLevel = 1.0
            supportZoom = false
            isJavaScriptEnabled = true
            androidWebSettings.apply {
                // isAlgorithmicDarkeningAllowed = true
                // domStorageEnabled = true
                safeBrowsingEnabled = true
            }
        }
    }

    LaunchedEffect(Unit) {
        snapshotFlow { webViewState.loadingState }.collectLatest {
            if (it is LoadingState.Finished) {
                webViewNavigator.evaluateJavaScript(
                    """
                    document.getElementById('header').style.display = 'none';
                    document.querySelector('.fixed.bottom-4.left-4').style.display = 'none';
                    """.trimIndent()
                )
            }
        }
    }
    Scaffold(
        modifier = Modifier.safeDrawingPadding()
    ) { innerPadding ->
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