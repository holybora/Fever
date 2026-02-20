package com.sls.handbook.core.model

import kotlinx.serialization.Serializable

/**
 * Current weather conditions for a specific location.
 *
 * All temperature values are in Celsius and wind speed in meters per second,
 * matching the "metric" unit system from OpenWeatherMap.
 *
 * @property cityName name of the city or locality
 * @property country ISO 3166-1 alpha-2 country code (e.g., "US", "DE")
 * @property latitude location latitude in decimal degrees
 * @property longitude location longitude in decimal degrees
 * @property temperature current temperature in Celsius
 * @property feelsLike apparent ("feels like") temperature in Celsius
 * @property tempMin minimum observed temperature in Celsius
 * @property tempMax maximum observed temperature in Celsius
 * @property humidity relative humidity percentage (0-100)
 * @property pressure atmospheric pressure in hPa
 * @property description human-readable weather condition text
 * @property icon OpenWeatherMap weather icon code
 * @property windSpeed wind speed in meters per second
 * @property visibility visibility distance in meters
 */
@Serializable
data class Weather(
    val cityName: String,
    val country: String,
    val latitude: Double,
    val longitude: Double,
    val temperature: Double,
    val feelsLike: Double,
    val tempMin: Double,
    val tempMax: Double,
    val humidity: Int,
    val pressure: Int,
    val description: String,
    val icon: String,
    val windSpeed: Double,
    val visibility: Int,
)
