fun buildFreediumUrl(mediumUrl: String): String {
    return "$FREEDIUM_URL_PREFIX/$mediumUrl"
}

private const val FREEDIUM_URL_PREFIX = "https://freedium.cfd"