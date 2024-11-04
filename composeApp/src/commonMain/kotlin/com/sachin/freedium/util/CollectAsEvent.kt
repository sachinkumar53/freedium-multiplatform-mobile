package com.sachin.freedium.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

@Composable
fun <T> CollectAsEvent(
    eventFlow: Flow<T>,
    onEvent: suspend (T) -> Unit
) {
    LaunchedEffect(Unit) {
        withContext(Dispatchers.Main.immediate) {
            eventFlow.collect(onEvent)
        }
    }
}