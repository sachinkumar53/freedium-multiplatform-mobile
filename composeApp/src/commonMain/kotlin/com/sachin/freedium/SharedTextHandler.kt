package com.sachin.freedium

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow

object SharedTextHandler {

    private val _sharedUrlChannel = Channel<String>(capacity = Channel.BUFFERED)
    internal val sharedUrlFlow = _sharedUrlChannel.receiveAsFlow()

    fun handleText(text: String?) {
        val url = text?.let { extractMediumUrlFromText(it) }
        try {
            val validUrl = validateMediumUrl(url)
            _sharedUrlChannel.trySend(validUrl)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    internal fun validateMediumUrl(url: String?): String {
        if (url.isNullOrEmpty()) {
            throw NullPointerException("No URL found in text: $url")
        }

        val urlRegex = Regex(MEDIUM_URL_REGEX)

        if (!urlRegex.matches(url)) {
            throw IllegalArgumentException("URL is not from Medium: $url")
        }
        return url
    }

    private fun extractMediumUrlFromText(text: String): String? {
        val urlRegex = Regex(MEDIUM_URL_REGEX)
        val matchResult = urlRegex.find(text)
        return matchResult?.value
    }

    private const val MEDIUM_URL_REGEX = "https?://(?:www\\.)?medium\\.com/\\S+"
}