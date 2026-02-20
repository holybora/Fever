package com.sls.handbook.core.model

import kotlinx.serialization.Serializable

/**
 * Aggregated weather forecast for a single calendar day.
 *
 * @property dateEpochSeconds representative UTC timestamp for the day, in epoch seconds
 * @property tempMin minimum temperature for the day, in Celsius
 * @property tempMax maximum temperature for the day, in Celsius
 * @property icon OpenWeatherMap icon code for the midday forecast entry
 */
@Serializable
data class DailyForecast(
    val dateEpochSeconds: Long,
    val tempMin: Double,
    val tempMax: Double,
    val icon: String,
)
