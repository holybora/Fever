package com.sls.handbook.core.domain.usecase

import com.sls.handbook.core.domain.repository.WeatherRepository
import com.sls.handbook.core.model.Weather
import javax.inject.Inject

class GetCurrentWeatherUseCase @Inject constructor(
    private val weatherRepository: WeatherRepository,
) {
    suspend operator fun invoke(lat: Double, lon: Double, lang: String): Weather =
        weatherRepository.getWeather(lat, lon, lang)
}
