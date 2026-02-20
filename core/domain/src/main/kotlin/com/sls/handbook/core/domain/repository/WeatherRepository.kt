package com.sls.handbook.core.domain.repository

import com.sls.handbook.core.model.DailyForecast
import com.sls.handbook.core.model.HourlyForecast
import com.sls.handbook.core.model.Weather

interface WeatherRepository {
    suspend fun getWeatherWithForecast(lat: Double, lon: Double, lang: String): Pair<Weather, List<DailyForecast>>
    suspend fun getHourlyForecast(lat: Double, lon: Double, lang: String): List<HourlyForecast>
}
