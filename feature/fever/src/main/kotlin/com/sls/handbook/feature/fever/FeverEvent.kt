package com.sls.handbook.feature.fever

/** UI events emitted by the Fever screen and processed by [FeverViewModel]. */
sealed interface FeverEvent {
    /** Requests a fresh weather data load for a new random location. */
    data object Refresh : FeverEvent
}
