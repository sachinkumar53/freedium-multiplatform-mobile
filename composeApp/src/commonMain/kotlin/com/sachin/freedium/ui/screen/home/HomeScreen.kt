package com.sachin.freedium.ui.screen.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sachin.freedium.util.CollectAsEvent
import freedium.composeapp.generated.resources.Res
import freedium.composeapp.generated.resources.ic_add_link
import freedium.composeapp.generated.resources.ic_close
import org.jetbrains.compose.resources.painterResource

@Composable
fun HomeScreen(
    navigateToArticleScreen: (String) -> Unit,
    viewModel: HomeViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }

    CollectAsEvent(viewModel.uiEvent) { uiEvent ->
        when (uiEvent) {
            is HomeUiEvent.NavigateToArticle -> navigateToArticleScreen(uiEvent.url)
            is HomeUiEvent.ShowSnackbar -> snackbarHostState.showSnackbar(uiEvent.message)
        }
    }

    HomeScreenContent(
        url = uiState.url,
        onUrlChanged = viewModel::onUrlChange,
        onSubmitUrl = viewModel::submitUrl,
        snackbarHostState = snackbarHostState
    )
}

@Composable
private fun HomeScreenContent(
    url: String,
    onUrlChanged: (String) -> Unit,
    onSubmitUrl: () -> Unit,
    snackbarHostState: SnackbarHostState
) {
    Scaffold(
        scaffoldState = rememberScaffoldState(snackbarHostState = snackbarHostState),
        modifier = Modifier.safeDrawingPadding(),
        topBar = {
            Column(
                modifier = Modifier.padding(
                    start = 16.dp,
                    end = 16.dp,
                    top = 24.dp,
                    bottom = 16.dp
                )
            ) {
                Text(
                    text = "Freedium",
                    color = MaterialTheme.colors.primary,
                    style = MaterialTheme.typography.h5,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = "Your paywall breakthrough for Medium!",
                    modifier = Modifier.padding(top = 4.dp).alpha(0.7f),
                )
            }
        }
    ) { innerPadding ->
        Column(modifier = Modifier.fillMaxSize().padding(innerPadding).padding(16.dp)) {
            LinkField(
                url = url,
                onUrlChanged = onUrlChanged,
                onSubmitUrl = onSubmitUrl,
                modifier = Modifier.fillMaxWidth()
            )

            /*Button(
                onClick = { onSubmitUrl(url) },
                modifier = Modifier.padding(top = 16.dp)
                    .align(alignment = Alignment.End)
            ) {
                Text(text = "Go")
                Spacer(modifier = Modifier.width(16.dp))
                Icon(
                    painter = painterResource(Res.drawable.ic_go),
                    contentDescription = "Go"
                )
            }*/
            /*Text(
                text = "Recent posts",
                style = MaterialTheme.typography.h6,
                modifier = Modifier.padding(top = 24.dp)
            )*/
        }
    }
}

@Composable
private fun LinkField(
    url: String,
    onUrlChanged: (String) -> Unit,
    onSubmitUrl: () -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = url,
        onValueChange = onUrlChanged,
        modifier = modifier,
        placeholder = { Text("Enter medium post link") },
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Go,
            keyboardType = KeyboardType.Uri,
            autoCorrectEnabled = false
        ),
        keyboardActions = KeyboardActions(onGo = { onSubmitUrl() }),
        maxLines = 1,
        leadingIcon = {
            Icon(
                painter = painterResource(Res.drawable.ic_add_link),
                contentDescription = null
            )
        },
        trailingIcon = {
            if (url.isNotEmpty()) {
                IconButton(
                    onClick = { onUrlChanged("") }
                ) {
                    Icon(
                        painter = painterResource(Res.drawable.ic_close),
                        contentDescription = "Clear"
                    )
                }
            }
        }
    )
}