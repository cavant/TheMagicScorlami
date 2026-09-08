package com.themagicsportslami.app.data.api

import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.File
import java.util.concurrent.TimeUnit

class MultiSportHttpClient(cacheDir: File? = null) {

    private val client: OkHttpClient = OkHttpClient.Builder()
        .connectTimeout(8, TimeUnit.SECONDS)
        .readTimeout(8, TimeUnit.SECONDS)
        .apply {
            if (cacheDir != null) {
                cache(Cache(File(cacheDir, "sportslami_http_cache"), 25L * 1024 * 1024))
            }
        }
        .build()

    fun get(url: String): String {
        val request = Request.Builder()
            .url(url)
            .header("User-Agent", "Mozilla/5.0 (Linux; Android 14) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/128.0.0.0 Mobile Safari/537.36")
            .header("Accept", "application/json")
            .build()

        client.newCall(request).execute().use { response: Response ->
            if (!response.isSuccessful) {
                throw IllegalStateException("HTTP ${response.code} fetching $url")
            }
            return response.body?.string() ?: throw IllegalStateException("Empty response body from $url")
        }
    }
}
