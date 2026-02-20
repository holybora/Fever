package com.sls.handbook.core.domain.exception

import java.io.IOException

sealed class WeatherException(message: String, cause: Throwable?) : Exception(message, cause) {
    class Network(cause: IOException) : WeatherException("Network error", cause)
    class Server(val code: Int, message: String, cause: Throwable) :
        WeatherException("HTTP $code: $message", cause)
    class DataParsing(cause: Throwable) : WeatherException("Failed to parse response", cause)
}
