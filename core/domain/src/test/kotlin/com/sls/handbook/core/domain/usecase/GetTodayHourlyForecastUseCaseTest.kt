package com.sls.handbook.core.domain.usecase

import com.sls.handbook.core.model.ForecastData
import com.sls.handbook.core.model.ForecastItem
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class GetTodayHourlyForecastUseCaseTest {

    private val useCase = GetTodayHourlyForecastUseCase()

    @Test
    fun `empty items returns empty list`() {
        val result = useCase(ForecastData(emptyList(), 0))
        assertTrue(result.isEmpty())
    }

    @Test
    fun `returns all items mapped to HourlyForecast`() {
        val data = ForecastData(
            items = listOf(
                forecastItem(dt = 1000L),
                forecastItem(dt = 2000L),
                forecastItem(dt = 3000L),
            ),
            timezoneOffsetSeconds = 0,
        )
        val result = useCase(data)
        assertEquals(3, result.size)
    }

    @Test
    fun `maps fields correctly from ForecastItem to HourlyForecast`() {
        val data = ForecastData(
            items = listOf(
                forecastItem(
                    dt = 1_700_000_000L,
                    temperature = 25.5,
                    icon = "02d",
                    description = "few clouds",
                    pop = 0.3,
                ),
            ),
            timezoneOffsetSeconds = 0,
        )
        val result = useCase(data)
        assertEquals(1, result.size)
        val hourly = result[0]
        assertEquals(1_700_000_000L, hourly.dt)
        assertEquals(25.5, hourly.temperature, 0.001)
        assertEquals("02d", hourly.icon)
        assertEquals("few clouds", hourly.description)
        assertEquals(0.3, hourly.pop, 0.001)
    }

    private fun forecastItem(
        dt: Long = 0,
        temperature: Double = 20.0,
        tempMin: Double = 15.0,
        tempMax: Double = 25.0,
        icon: String = "01d",
        description: String = "clear",
        pop: Double = 0.0,
    ) = ForecastItem(
        dt = dt,
        temperature = temperature,
        tempMin = tempMin,
        tempMax = tempMax,
        icon = icon,
        description = description,
        pop = pop,
    )
}
