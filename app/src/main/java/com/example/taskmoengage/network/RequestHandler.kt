package com.example.taskmoengage.network

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

object RequestHandler {

    const val GET: String = "GET"

    @Throws(Exception::class)
    suspend fun requestGet(url: String?): String {
        val httpUrlConnection = getHTTPConnection(url)

        httpUrlConnection.requestMethod = GET
        httpUrlConnection.connectTimeout = 3000
        httpUrlConnection.readTimeout = 3000
        val responseCode = httpUrlConnection.responseCode
        return if (responseCode == HttpURLConnection.HTTP_OK) { // connection ok
            val bufferedReader =
                BufferedReader(InputStreamReader(httpUrlConnection.inputStream))
            var inputLine: String?
            val response = StringBuffer()
            while (withContext(Dispatchers.IO) {
                    bufferedReader.readLine()
                }.also { inputLine = it } != null) {
                response.append(inputLine)
            }
            withContext(Dispatchers.IO) {
                bufferedReader.close()
            }
            response.toString()
        } else {
            "Some thing went Wrong!!!"
        }
    }


    private fun getHTTPConnection(urlString: String?): HttpURLConnection {
        var httpUrlConnection: HttpURLConnection? = null
        val url = URL(urlString)
        httpUrlConnection = url.openConnection() as HttpURLConnection
        return httpUrlConnection
    }
}