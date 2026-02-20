package com.sls.handbook.core.network.model

import com.google.gson.annotations.SerializedName

/** OpenWeatherMap current-weather API response. */
data class WeatherResponse(
    @SerializedName("coord") val coord: CoordResponse,
    @SerializedName("weather") val weather: List<WeatherConditionResponse>,
    @SerializedName("main") val main: MainResponse,
    @SerializedName("visibility") val visibility: Int,
    @SerializedName("wind") val wind: WindResponse,
    @SerializedName("sys") val sys: SysResponse,
    @SerializedName("name") val name: String,
)

/** Geographic coordinates from the API response. */
data class CoordResponse(
    @SerializedName("lat") val lat: Double,
    @SerializedName("lon") val lon: Double,
)

/** Weather condition entry containing icon, main category, and description. */
data class WeatherConditionResponse(
    @SerializedName("main") val main: String,
    @SerializedName("description") val description: String,
    @SerializedName("icon") val icon: String,
)

/** Temperature, pressure, and humidity fields from the API response. */
data class MainResponse(
    @SerializedName("temp") val temp: Double,
    @SerializedName("feels_like") val feelsLike: Double,
    @SerializedName("temp_min") val tempMin: Double,
    @SerializedName("temp_max") val tempMax: Double,
    @SerializedName("pressure") val pressure: Int,
    @SerializedName("humidity") val humidity: Int,
)

/** Wind speed data from the API response. */
data class WindResponse(
    @SerializedName("speed") val speed: Double,
)

/** System-level metadata from the API response, including the country code. */
data class SysResponse(
    @SerializedName("country") val country: String?,
)

/** OpenWeatherMap 5-day/3-hour forecast API response. */
data class ForecastResponse(
    @SerializedName("list") val list: List<ForecastItemResponse>,
    @SerializedName("city") val city: CityResponse,
)

/** Single 3-hourly entry within a [ForecastResponse]. */
data class ForecastItemResponse(
    @SerializedName("dt") val dt: Long,
    @SerializedName("main") val main: MainResponse,
    @SerializedName("weather") val weather: List<WeatherConditionResponse>,
    @SerializedName("pop") val pop: Double,
)

/** City-level metadata within a [ForecastResponse], providing the timezone offset. */
data class CityResponse(
    @SerializedName("timezone") val timezone: Int,
)
