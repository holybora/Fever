package com.sls.handbook.feature.fever

import com.sls.handbook.feature.fever.entity.WeatherDisplayData

/**
 * Represents the UI state of the Fever weather screen.
 *
 * @property weatherDisplay pre-formatted display data for the weather screen
 */
sealed class FeverUiState(open val weatherDisplay: WeatherDisplayData) {
    /** Initial state while weather data is being fetched. */
    data object Loading : FeverUiState(weatherDisplay = WeatherDisplayData.empty())

    /** Weather data loaded successfully. */
    data class Success(override val weatherDisplay: WeatherDisplayData) : FeverUiState(weatherDisplay = weatherDisplay)

    /**
     * Weather data loading failed.
     *
     * @property message user-facing localized error description
     */
    data class Error(val message: String) : FeverUiState(weatherDisplay = WeatherDisplayData.empty())
}
