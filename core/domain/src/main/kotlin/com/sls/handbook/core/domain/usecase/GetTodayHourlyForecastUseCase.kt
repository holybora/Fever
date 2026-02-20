package com.sls.handbook.core.domain.usecase

import com.sls.handbook.core.model.ForecastData
import com.sls.handbook.core.model.HourlyForecast
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneOffset
import javax.inject.Inject

class GetTodayHourlyForecastUseCase @Inject constructor() {

    operator fun invoke(forecastData: ForecastData): List<HourlyForecast> {
        val zoneOffset = ZoneOffset.ofTotalSeconds(forecastData.timezoneOffsetSeconds)
        val today = LocalDate.now(zoneOffset)
        return forecastData.items
            .filter { item ->
                val itemDate = Instant.ofEpochSecond(item.dt).atOffset(zoneOffset).toLocalDate()
                itemDate == today
            }
            .map { item ->
                HourlyForecast(
                    dt = item.dt,
                    temperature = item.temperature,
                    icon = item.icon,
                    description = item.description,
                    pop = item.pop,
                )
            }
    }
}
