package com.sachin.freedium.ui.screen.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.sachin.freedium.SharedTextHandler
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    navigateToArticleScreen: (String) -> Unit
) {
    var text by rememberSaveable { mutableStateOf("") }
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    HomeScreenContent(
        url = text,
        onUrlChanged = { text = it },
        onSubmitUrl = {
            try {
                val url = SharedTextHandler.validateMediumUrl(it)
                navigateToArticleScreen(url)
            } catch (e: NullPointerException) {
                coroutineScope.launch {
                    snackbarHostState.showSnackbar("Please enter a medium post link")
                }
            } catch (e: IllegalArgumentException) {
                coroutineScope.launch {
                    snackbarHostState.showSnackbar("Invalid medium post link")
                }
            }
        },
        snackbarHostState = snackbarHostState
    )
}

@Composable
private fun HomeScreenContent(
    url: String,
    onUrlChanged: (String) -> Unit,
    onSubmitUrl: (String) -> Unit,
    snackbarHostState: SnackbarHostState
) {
    Scaffold(
        scaffoldState = rememberScaffoldState(snackbarHostState = snackbarHostState),
        modifier = Modifier.imePadding()
    ) { innerPadding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(innerPadding).padding(16.dp)
        ) {
            OutlinedTextField(
                value = url,
                onValueChange = onUrlChanged,
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("Medium post link") },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Go,
                    keyboardType = KeyboardType.Uri,
                    autoCorrectEnabled = false
                ),
                keyboardActions = KeyboardActions(onGo = { onSubmitUrl(url) })
            )
        }
    }
}