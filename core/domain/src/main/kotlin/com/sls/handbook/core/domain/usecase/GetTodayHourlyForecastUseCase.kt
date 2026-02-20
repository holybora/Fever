package com.sls.handbook.core.domain.usecase

import com.sls.handbook.core.model.ForecastData
import com.sls.handbook.core.model.HourlyForecast
import javax.inject.Inject

class GetTodayHourlyForecastUseCase @Inject constructor() {

    operator fun invoke(forecastData: ForecastData): List<HourlyForecast> {
        return forecastData.items
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
