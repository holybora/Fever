package com.sls.handbook.core.model

import kotlinx.serialization.Serializable

@Serializable
data class Coordinates(
    val latitude: Double,
    val longitude: Double,
)
