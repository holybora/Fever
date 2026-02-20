package com.sls.handbook.core.domain.usecase

import com.sls.handbook.core.domain.repository.WeatherRepository
import com.sls.handbook.core.model.ForecastData
import javax.inject.Inject

class GetForecastDataUseCase @Inject constructor(
    private val weatherRepository: WeatherRepository,
) {
    suspend operator fun invoke(lat: Double, lon: Double, lang: String): ForecastData =
        weatherRepository.getForecastData(lat, lon, lang)
}
