package com.sls.handbook.feature.fever.entity

/**
 * Pre-formatted display data for a single day in the 5-day forecast.
 *
 * @property dayName abbreviated day-of-week name (e.g., "Mon")
 * @property iconUrl full URL to the weather icon image
 * @property highText formatted high temperature (e.g., "H:35°")
 * @property lowText formatted low temperature (e.g., "L:28°")
 */
data class DailyForecastDisplayData(
    val dayName: String,
    val iconUrl: String,
    val highText: String,
    val lowText: String,
)

/**
 * Presentation model containing all pre-formatted strings for the Fever weather screen.
 *
 * Created by mapping domain models through extension functions in WeatherMapper.kt.
 * The [empty] companion factory produces a blank instance used during loading and error states.
 */
data class WeatherDisplayData(
    val temperatureText: String,
    val iconUrl: String,
    val iconContentDescription: String,
    val highLowText: String,
    val windText: String,
    val humidityText: String,
    val locationName: String,
    val descriptionText: String,
    val feelsLikeText: String,
    val pressureText: String,
    val visibilityText: String,
    val latitudeText: String,
    val longitudeText: String,
    val fiveDaysForecast: List<DailyForecastDisplayData>,
    val hourlyForecasts: List<HourlyDisplayData>,
) {
    companion object {
        fun empty() = WeatherDisplayData(
            temperatureText = "",
            iconUrl = "",
            iconContentDescription = "",
            highLowText = "",
            windText = "",
            humidityText = "",
            locationName = "",
            descriptionText = "",
            feelsLikeText = "",
            pressureText = "",
            visibilityText = "",
            latitudeText = "",
            longitudeText = "",
            fiveDaysForecast = emptyList(),
            hourlyForecasts = emptyList(),
        )
    }
}
