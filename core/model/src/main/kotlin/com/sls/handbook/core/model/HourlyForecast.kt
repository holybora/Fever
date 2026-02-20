package com.sls.handbook.core.model

import kotlinx.serialization.Serializable

/**
 * Hourly weather forecast entry for a single time slot within the current day.
 *
 * @property dt forecast timestamp in UTC epoch seconds
 * @property temperature predicted temperature in Celsius
 * @property icon OpenWeatherMap weather icon code
 * @property description human-readable weather condition text
 * @property pop probability of precipitation, from 0.0 to 1.0
 */
@Serializable
data class HourlyForecast(
    val dt: Long,
    val temperature: Double,
    val icon: String,
    val description: String,
    val pop: Double,
)
