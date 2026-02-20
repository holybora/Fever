package com.sls.handbook.feature.fever.entity

/**
 * Pre-formatted display data for a single hourly forecast entry.
 *
 * @property timeText formatted time string (e.g., "2 PM")
 * @property iconUrl full URL to the weather icon image
 * @property temperatureText formatted temperature with unit (e.g., "32°C")
 * @property popText formatted precipitation probability (e.g., "10%")
 */
data class HourlyDisplayData(
    val timeText: String,
    val iconUrl: String,
    val temperatureText: String,
    val popText: String,
)
