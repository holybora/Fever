package com.sls.handbook.feature.fever

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.sls.handbook.core.domain.repository.WeatherRepository
import com.sls.handbook.core.model.DailyForecast
import com.sls.handbook.core.model.HourlyForecast
import com.sls.handbook.core.model.Weather
import java.util.Locale
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(AndroidJUnit4::class)
class FeverViewModelLocaleTest {

    private val testDispatcher = StandardTestDispatcher()
    private val fakeRepository = FakeWeatherRepository()
    private val originalLocale = Locale.getDefault()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        Locale.setDefault(originalLocale)
    }

    @Test
    fun loadWeather_passesDeviceLocaleLanguageAsLangParameter() = runTest(testDispatcher) {
        Locale.setDefault(Locale.of("de"))

        createViewModel()
        advanceUntilIdle()

        assertEquals("de", fakeRepository.lastLangForWeather)
        assertEquals("de", fakeRepository.lastLangForHourly)
    }

    @Test
    fun loadWeather_passesSpanishLocale() = runTest(testDispatcher) {
        Locale.setDefault(Locale.of("es"))

        createViewModel()
        advanceUntilIdle()

        assertEquals("es", fakeRepository.lastLangForWeather)
        assertEquals("es", fakeRepository.lastLangForHourly)
    }

    @Test
    fun loadWeather_passesFrenchLocale() = runTest(testDispatcher) {
        Locale.setDefault(Locale.of("fr"))

        createViewModel()
        advanceUntilIdle()

        assertEquals("fr", fakeRepository.lastLangForWeather)
        assertEquals("fr", fakeRepository.lastLangForHourly)
    }

    @Test
    fun refresh_usesCurrentLocaleAtCallTime() = runTest(testDispatcher) {
        Locale.setDefault(Locale.of("de"))
        val viewModel = createViewModel()
        advanceUntilIdle()
        assertEquals("de", fakeRepository.lastLangForWeather)

        Locale.setDefault(Locale.of("fr"))
        viewModel.onEvent(FeverEvent.Refresh)
        advanceUntilIdle()

        assertEquals("fr", fakeRepository.lastLangForWeather)
        assertEquals("fr", fakeRepository.lastLangForHourly)
    }

    @Test
    fun loadWeather_reachesSuccessState() = runTest(testDispatcher) {
        Locale.setDefault(Locale.of("en"))
        val viewModel = createViewModel()
        advanceUntilIdle()

        assertTrue(viewModel.uiState.value is FeverUiState.Success)
    }

    private fun createViewModel() = FeverViewModel(
        stringResolver = FakeStringResolver(),
        weatherRepository = fakeRepository,
        coordinatesGenerator = RandomCoordinatesGenerator(),
        ioDispatcher = testDispatcher,
    )
}

private class FakeWeatherRepository : WeatherRepository {
    var lastLangForWeather: String? = null
    var lastLangForHourly: String? = null

    override suspend fun getWeatherWithForecast(
        lat: Double,
        lon: Double,
        lang: String,
    ): Pair<Weather, List<DailyForecast>> {
        lastLangForWeather = lang
        return testWeather to emptyList()
    }

    override suspend fun getHourlyForecast(
        lat: Double,
        lon: Double,
        lang: String,
    ): List<HourlyForecast> {
        lastLangForHourly = lang
        return emptyList()
    }
}

private class FakeStringResolver : StringResolver {
    override fun getString(resId: Int, vararg args: Any): String = "test"
}

private val testWeather = Weather(
    cityName = "Berlin",
    country = "DE",
    latitude = 52.52,
    longitude = 13.405,
    temperature = 20.0,
    feelsLike = 19.0,
    tempMin = 17.0,
    tempMax = 23.0,
    humidity = 60,
    pressure = 1013,
    description = "clear sky",
    icon = "01d",
    windSpeed = 3.0,
    visibility = 10000,
)
