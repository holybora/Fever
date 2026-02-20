package com.sls.handbook.core.domain.usecase

import com.sls.handbook.core.model.ForecastData
import com.sls.handbook.core.model.ForecastItem
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneOffset
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
    fun `returns only items matching today in given timezone`() {
        val utcToday = LocalDate.now(ZoneOffset.UTC)
        val todayMorning = utcToday.atTime(9, 0).toEpochSecond(ZoneOffset.UTC)
        val todayAfternoon = utcToday.atTime(15, 0).toEpochSecond(ZoneOffset.UTC)
        val tomorrowMorning = utcToday.plusDays(1).atTime(9, 0).toEpochSecond(ZoneOffset.UTC)

        val data = ForecastData(
            items = listOf(
                forecastItem(dt = todayMorning),
                forecastItem(dt = todayAfternoon),
                forecastItem(dt = tomorrowMorning),
            ),
            timezoneOffsetSeconds = 0,
        )
        val result = useCase(data)
        assertEquals(2, result.size)
    }

    @Test
    fun `filters tomorrow items out`() {
        val utcToday = LocalDate.now(ZoneOffset.UTC)
        val tomorrowNoon = utcToday.plusDays(1)
            .atTime(LocalTime.NOON)
            .toEpochSecond(ZoneOffset.UTC)

        val data = ForecastData(
            items = listOf(forecastItem(dt = tomorrowNoon)),
            timezoneOffsetSeconds = 0,
        )
        val result = useCase(data)
        assertTrue(result.isEmpty())
    }

    @Test
    fun `positive timezone offset shifts today boundary`() {
        // Timezone UTC+5: an item at 22:00 UTC is actually 03:00 next day locally
        val utcToday = LocalDate.now(ZoneOffset.UTC)
        val lateEvening = utcToday.atTime(22, 0).toEpochSecond(ZoneOffset.UTC)
        val offsetSeconds = 5 * 3600 // UTC+5

        val data = ForecastData(
            items = listOf(forecastItem(dt = lateEvening)),
            timezoneOffsetSeconds = offsetSeconds,
        )
        val result = useCase(data)
        // Whether this is included depends on whether "tomorrow local" matches today UTC+5
        // At 22:00 UTC with +5h offset = 03:00 next day local time
        // So this item belongs to tomorrow in the local timezone
        val localDate = LocalDate.now(ZoneOffset.ofTotalSeconds(offsetSeconds))
        val itemLocalDate = utcToday.plusDays(1) // 22:00 UTC + 5h = next day
        if (localDate == itemLocalDate) {
            assertEquals(1, result.size)
        } else {
            assertTrue(result.isEmpty())
        }
    }

    @Test
    fun `maps fields correctly from ForecastItem to HourlyForecast`() {
        val utcToday = LocalDate.now(ZoneOffset.UTC)
        val dt = utcToday.atTime(12, 0).toEpochSecond(ZoneOffset.UTC)

        val data = ForecastData(
            items = listOf(
                forecastItem(
                    dt = dt,
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
        assertEquals(dt, hourly.dt)
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
