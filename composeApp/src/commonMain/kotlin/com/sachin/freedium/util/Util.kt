package com.sachin.freedium.util


fun extractUrlFromText(text: String): String? {
    val urlRegex = Regex(URL_REGEX)
    val matchResult = urlRegex.find(text)
    return matchResult?.value
}


fun validateUrl(url: String?): String {
    if (url.isNullOrEmpty()) {
        throw NullPointerException("No URL found in text: $url")
    }

    val urlRegex = Regex(URL_REGEX)

    if (!urlRegex.matches(url)) {
        throw IllegalArgumentException("Invalid URL: $url")
    }

    return url
}

fun buildFreediumUrl(mediumUrl: String): String {
    return "$FREEDIUM_URL_PREFIX/$mediumUrl"
}

private const val URL_REGEX = "https://\\S+"
private const val FREEDIUM_URL_PREFIX = "https://freedium.cfd"