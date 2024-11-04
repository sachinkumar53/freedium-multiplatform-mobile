package com.sachin.freedium

import com.sachin.freedium.util.extractUrlFromText
import com.sachin.freedium.util.validateUrl
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow

object SharedTextHandler {

    private val _sharedUrlChannel = Channel<Result<String>>(capacity = Channel.BUFFERED)
    internal val sharedUrlResult = _sharedUrlChannel.receiveAsFlow()

    fun handleText(text: String?) {
        val url = text?.let { extractUrlFromText(it) }
        try {
            val validUrl = validateUrl(url)
            _sharedUrlChannel.trySend(Result.success(validUrl))
        } catch (e: Exception) {
            _sharedUrlChannel.trySend(Result.failure(e))
        }
    }

}

