package com.sachin.freedium

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform