package com.sachin.freedium.data.network

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse

object NetworkClient {
    private val client = HttpClient()

    suspend fun load(url: String): HttpResponse {
        return client.get(url)
    }
}