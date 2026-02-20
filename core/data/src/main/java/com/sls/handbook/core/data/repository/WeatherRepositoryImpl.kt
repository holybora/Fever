package com.sls.handbook.core.data.repository

import com.sls.handbook.core.domain.repository.WeatherRepository
import com.sls.handbook.core.model.ForecastData
import com.sls.handbook.core.model.ForecastItem
import com.sls.handbook.core.model.Weather
import com.sls.handbook.core.network.api.WeatherApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WeatherRepositoryImpl @Inject constructor(
    private val weatherApi: WeatherApi,
) : WeatherRepository {

    override suspend fun getWeather(lat: Double, lon: Double, lang: String): Weather {
        val response = weatherApi.getWeather(lat = lat, lon = lon, lang = lang)
        val condition = response.weather.firstOrNull()
        return Weather(
            cityName = response.name,
            country = response.sys.country.orEmpty(),
            latitude = response.coord.lat,
            longitude = response.coord.lon,
            temperature = response.main.temp,
            feelsLike = response.main.feelsLike,
            tempMin = response.main.tempMin,
            tempMax = response.main.tempMax,
            humidity = response.main.humidity,
            pressure = response.main.pressure,
            description = condition?.description.orEmpty(),
            icon = condition?.icon.orEmpty(),
            windSpeed = response.wind.speed,
            visibility = response.visibility,
        )
    }

    override suspend fun getForecastData(lat: Double, lon: Double, lang: String): ForecastData {
        val response = weatherApi.getForecast(lat = lat, lon = lon, lang = lang)
        return ForecastData(
            items = response.list.map { entry ->
                val condition = entry.weather.firstOrNull()
                ForecastItem(
                    dt = entry.dt,
                    temperature = entry.main.temp,
                    tempMin = entry.main.tempMin,
                    tempMax = entry.main.tempMax,
                    icon = condition?.icon.orEmpty(),
                    description = condition?.description.orEmpty(),
                    pop = entry.pop,
                )
            },
            timezoneOffsetSeconds = response.city.timezone,
        )
    }
}
