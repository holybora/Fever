package com.sls.handbook.core.model

import kotlinx.serialization.Serializable

@Serializable
data class ForecastItem(
    val dt: Long,
    val temperature: Double,
    val tempMin: Double,
    val tempMax: Double,
    val icon: String,
    val description: String,
    val pop: Double,
)
