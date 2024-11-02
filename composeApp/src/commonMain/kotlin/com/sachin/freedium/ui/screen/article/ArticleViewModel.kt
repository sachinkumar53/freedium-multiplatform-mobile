package com.sachin.freedium.ui.screen.article

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.sachin.freedium.data.network.NetworkClient
import com.sachin.freedium.ui.navigation.Route
import io.ktor.client.statement.bodyAsText
import kotlinx.coroutines.launch

class ArticleViewModel(
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val arg = savedStateHandle.toRoute<Route.Article>()

    init {
        viewModelScope.launch {
            val response = NetworkClient.load(arg.url)
            println(response.bodyAsText())
        }
    }

}