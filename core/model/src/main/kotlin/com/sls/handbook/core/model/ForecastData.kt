package com.sls.handbook.core.model

import kotlinx.serialization.Serializable

@Serializable
data class ForecastData(
    val items: List<ForecastItem>,
    val timezoneOffsetSeconds: Int,
)
