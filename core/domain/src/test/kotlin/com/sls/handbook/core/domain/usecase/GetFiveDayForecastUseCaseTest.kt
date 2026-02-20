package com.sls.handbook.core.domain.usecase

import com.sls.handbook.core.model.ForecastData
import com.sls.handbook.core.model.ForecastItem
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneOffset
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class GetFiveDayForecastUseCaseTest {

    private val useCase = GetFiveDayForecastUseCase()

    @Test
    fun `empty items returns empty list`() {
        val result = useCase(ForecastData(emptyList(), 0))
        assertTrue(result.isEmpty())
    }

    @Test
    fun `filters out items for today`() {
        val todayNoon = LocalDate.now(ZoneOffset.UTC)
            .atTime(LocalTime.NOON)
            .toEpochSecond(ZoneOffset.UTC)

        val data = ForecastData(
            items = listOf(forecastItem(dt = todayNoon)),
            timezoneOffsetSeconds = 0,
        )
        val result = useCase(data)
        assertTrue(result.isEmpty())
    }

    @Test
    fun `returns items for future dates only`() {
        val tomorrow = LocalDate.now(ZoneOffset.UTC).plusDays(1)
        val tomorrowNoon = tomorrow.atTime(LocalTime.NOON).toEpochSecond(ZoneOffset.UTC)

        val data = ForecastData(
            items = listOf(forecastItem(dt = tomorrowNoon)),
            timezoneOffsetSeconds = 0,
        )
        val result = useCase(data)
        assertEquals(1, result.size)
    }

    @Test
    fun `groups items by UTC date`() {
        val tomorrow = LocalDate.now(ZoneOffset.UTC).plusDays(1)
        val tomorrowMorning = tomorrow.atTime(9, 0).toEpochSecond(ZoneOffset.UTC)
        val tomorrowAfternoon = tomorrow.atTime(15, 0).toEpochSecond(ZoneOffset.UTC)

        val data = ForecastData(
            items = listOf(
                forecastItem(dt = tomorrowMorning, tempMin = 10.0, tempMax = 15.0),
                forecastItem(dt = tomorrowAfternoon, tempMin = 12.0, tempMax = 20.0),
            ),
            timezoneOffsetSeconds = 0,
        )
        val result = useCase(data)
        assertEquals(1, result.size)
    }

    @Test
    fun `results are sorted chronologically`() {
        val tomorrow = LocalDate.now(ZoneOffset.UTC).plusDays(1)
        val dayAfter = LocalDate.now(ZoneOffset.UTC).plusDays(2)

        val data = ForecastData(
            items = listOf(
                forecastItem(
                    dt = dayAfter.atTime(LocalTime.NOON).toEpochSecond(ZoneOffset.UTC),
                ),
                forecastItem(
                    dt = tomorrow.atTime(LocalTime.NOON).toEpochSecond(ZoneOffset.UTC),
                ),
            ),
            timezoneOffsetSeconds = 0,
        )
        val result = useCase(data)
        assertEquals(2, result.size)
        assertTrue(result[0].dateEpochSeconds < result[1].dateEpochSeconds)
    }

    @Test
    fun `selects midday item for icon`() {
        val tomorrow = LocalDate.now(ZoneOffset.UTC).plusDays(1)
        val morning = tomorrow.atTime(6, 0).toEpochSecond(ZoneOffset.UTC)
        val noon = tomorrow.atTime(12, 0).toEpochSecond(ZoneOffset.UTC)
        val evening = tomorrow.atTime(18, 0).toEpochSecond(ZoneOffset.UTC)

        val data = ForecastData(
            items = listOf(
                forecastItem(dt = morning, icon = "morning_icon"),
                forecastItem(dt = noon, icon = "noon_icon"),
                forecastItem(dt = evening, icon = "evening_icon"),
            ),
            timezoneOffsetSeconds = 0,
        )
        val result = useCase(data)
        assertEquals("noon_icon", result[0].icon)
    }

    @Test
    fun `aggregates min and max temps across all items in a day`() {
        val tomorrow = LocalDate.now(ZoneOffset.UTC).plusDays(1)
        val morning = tomorrow.atTime(6, 0).toEpochSecond(ZoneOffset.UTC)
        val noon = tomorrow.atTime(12, 0).toEpochSecond(ZoneOffset.UTC)
        val evening = tomorrow.atTime(18, 0).toEpochSecond(ZoneOffset.UTC)

        val data = ForecastData(
            items = listOf(
                forecastItem(dt = morning, tempMin = 5.0, tempMax = 10.0),
                forecastItem(dt = noon, tempMin = 8.0, tempMax = 18.0),
                forecastItem(dt = evening, tempMin = 3.0, tempMax = 12.0),
            ),
            timezoneOffsetSeconds = 0,
        )
        val result = useCase(data)
        assertEquals(3.0, result[0].tempMin, 0.001)
        assertEquals(18.0, result[0].tempMax, 0.001)
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
