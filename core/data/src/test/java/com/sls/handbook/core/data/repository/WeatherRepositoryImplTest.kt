package com.sls.handbook.core.data.repository

import com.sls.handbook.core.domain.exception.WeatherException
import com.sls.handbook.core.network.api.WeatherApi
import com.sls.handbook.core.network.model.CityResponse
import com.sls.handbook.core.network.model.CoordResponse
import com.sls.handbook.core.network.model.ForecastItemResponse
import com.sls.handbook.core.network.model.ForecastResponse
import com.sls.handbook.core.network.model.MainResponse
import com.sls.handbook.core.network.model.SysResponse
import com.sls.handbook.core.network.model.WeatherConditionResponse
import com.sls.handbook.core.network.model.WeatherResponse
import com.sls.handbook.core.network.model.WindResponse
import io.mockk.coEvery
import io.mockk.mockk
import java.io.IOException
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response

class WeatherRepositoryImplTest {

    private val weatherApi: WeatherApi = mockk()
    private val repository = WeatherRepositoryImpl(weatherApi)

    @Test
    fun `getWeather maps response fields correctly`() = runTest {
        coEvery { weatherApi.getWeather(any(), any(), any(), any()) } returns weatherResponse()

        val result = repository.getWeather(10.0, 20.0, "en")

        assertEquals("London", result.cityName)
        assertEquals("GB", result.country)
        assertEquals(51.5, result.latitude, 0.001)
        assertEquals(-0.1, result.longitude, 0.001)
        assertEquals(20.0, result.temperature, 0.001)
        assertEquals(18.5, result.feelsLike, 0.001)
        assertEquals(17.0, result.tempMin, 0.001)
        assertEquals(23.0, result.tempMax, 0.001)
        assertEquals(65, result.humidity)
        assertEquals(1013, result.pressure)
        assertEquals("clear sky", result.description)
        assertEquals("01d", result.icon)
        assertEquals(3.5, result.windSpeed, 0.001)
        assertEquals(10000, result.visibility)
    }

    @Test
    fun `getWeather handles null country`() = runTest {
        coEvery { weatherApi.getWeather(any(), any(), any(), any()) } returns weatherResponse(
            sys = SysResponse(country = null),
        )

        val result = repository.getWeather(10.0, 20.0, "en")

        assertEquals("", result.country)
    }

    @Test
    fun `getWeather handles empty weather conditions list`() = runTest {
        coEvery { weatherApi.getWeather(any(), any(), any(), any()) } returns weatherResponse(
            conditions = emptyList(),
        )

        val result = repository.getWeather(10.0, 20.0, "en")

        assertEquals("", result.description)
        assertEquals("", result.icon)
    }

    @Test
    fun `getForecastData maps list items correctly`() = runTest {
        coEvery { weatherApi.getForecast(any(), any(), any(), any()) } returns forecastResponse()

        val result = repository.getForecastData(10.0, 20.0, "en")

        assertEquals(1, result.items.size)
        val item = result.items.first()
        assertEquals(1700000000L, item.dt)
        assertEquals(22.0, item.temperature, 0.001)
        assertEquals(19.0, item.tempMin, 0.001)
        assertEquals(25.0, item.tempMax, 0.001)
        assertEquals("02d", item.icon)
        assertEquals("few clouds", item.description)
        assertEquals(0.3, item.pop, 0.001)
        assertEquals(3600, result.timezoneOffsetSeconds)
    }

    @Test
    fun `getForecastData handles empty weather conditions in items`() = runTest {
        coEvery { weatherApi.getForecast(any(), any(), any(), any()) } returns forecastResponse(
            itemConditions = emptyList(),
        )

        val result = repository.getForecastData(10.0, 20.0, "en")

        val item = result.items.first()
        assertEquals("", item.icon)
        assertEquals("", item.description)
    }

    @Test(expected = WeatherException.Network::class)
    fun `getWeather wraps IOException in WeatherException Network`() = runTest {
        coEvery { weatherApi.getWeather(any(), any(), any(), any()) } throws IOException("timeout")

        repository.getWeather(10.0, 20.0, "en")
    }

    @Test
    fun `getWeather wraps HttpException in WeatherException Server`() = runTest {
        val httpException = HttpException(
            Response.error<Any>(500, "Internal Server Error".toResponseBody()),
        )
        coEvery { weatherApi.getWeather(any(), any(), any(), any()) } throws httpException

        try {
            repository.getWeather(10.0, 20.0, "en")
            @Suppress("UNREACHABLE_CODE")
            assertTrue("Expected WeatherException.Server", false)
        } catch (e: WeatherException.Server) {
            assertEquals(500, e.code)
        }
    }

    @Test(expected = WeatherException.DataParsing::class)
    fun `getWeather wraps unexpected exception in WeatherException DataParsing`() = runTest {
        coEvery {
            weatherApi.getWeather(any(), any(), any(), any())
        } throws RuntimeException("parse error")

        repository.getWeather(10.0, 20.0, "en")
    }

    @Test(expected = WeatherException.Network::class)
    fun `getForecastData wraps IOException in WeatherException Network`() = runTest {
        coEvery {
            weatherApi.getForecast(any(), any(), any(), any())
        } throws IOException("timeout")

        repository.getForecastData(10.0, 20.0, "en")
    }

    @Test
    fun `getForecastData wraps HttpException in WeatherException Server`() = runTest {
        val httpException = HttpException(
            Response.error<Any>(404, "Not Found".toResponseBody()),
        )
        coEvery { weatherApi.getForecast(any(), any(), any(), any()) } throws httpException

        try {
            repository.getForecastData(10.0, 20.0, "en")
            @Suppress("UNREACHABLE_CODE")
            assertTrue("Expected WeatherException.Server", false)
        } catch (e: WeatherException.Server) {
            assertEquals(404, e.code)
        }
    }

    private fun weatherResponse(
        conditions: List<WeatherConditionResponse> = listOf(
            WeatherConditionResponse(main = "Clear", description = "clear sky", icon = "01d"),
        ),
        sys: SysResponse = SysResponse(country = "GB"),
    ) = WeatherResponse(
        coord = CoordResponse(lat = 51.5, lon = -0.1),
        weather = conditions,
        main = MainResponse(
            temp = 20.0,
            feelsLike = 18.5,
            tempMin = 17.0,
            tempMax = 23.0,
            pressure = 1013,
            humidity = 65,
        ),
        visibility = 10000,
        wind = WindResponse(speed = 3.5),
        sys = sys,
        name = "London",
    )

    private fun forecastResponse(
        itemConditions: List<WeatherConditionResponse> = listOf(
            WeatherConditionResponse(main = "Clouds", description = "few clouds", icon = "02d"),
        ),
    ) = ForecastResponse(
        list = listOf(
            ForecastItemResponse(
                dt = 1700000000L,
                main = MainResponse(
                    temp = 22.0,
                    feelsLike = 21.0,
                    tempMin = 19.0,
                    tempMax = 25.0,
                    pressure = 1010,
                    humidity = 70,
                ),
                weather = itemConditions,
                pop = 0.3,
            ),
        ),
        city = CityResponse(timezone = 3600),
    )
}
