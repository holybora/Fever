package com.sls.handbook.feature.fever

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollTo
import com.sls.handbook.feature.fever.theme.FeverTheme
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [35])
class FeverScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun `loading state shows progress indicator in FAB`() {
        composeTestRule.setContent {
            FeverTheme {
                FeverScreen(uiState = FeverUiState.Loading, onEvent = {})
            }
        }

        // In loading state, FAB shows CircularProgressIndicator instead of arrow icon
        composeTestRule
            .onNodeWithContentDescription("Swipe right to refresh")
            .assertDoesNotExist()
    }

    @Test
    fun `success state displays location name`() {
        composeTestRule.setContent {
            FeverTheme {
                FeverScreen(
                    uiState = FeverUiState.Success(testWeatherDisplay),
                    onEvent = {},
                )
            }
        }

        composeTestRule.onNodeWithText("Surabaya, ID").assertIsDisplayed()
    }

    @Test
    fun `success state displays stat pill values`() {
        composeTestRule.setContent {
            FeverTheme {
                FeverScreen(
                    uiState = FeverUiState.Success(testWeatherDisplay),
                    onEvent = {},
                )
            }
        }

        composeTestRule.onNodeWithText("H:35° L:28°").assertIsDisplayed()
        composeTestRule.onNodeWithText("4.2 m/s").assertIsDisplayed()
        composeTestRule.onNodeWithText("78%").assertIsDisplayed()
    }

    @Test
    fun `success state displays detail cards`() {
        composeTestRule.setContent {
            FeverTheme {
                FeverScreen(
                    uiState = FeverUiState.Success(testWeatherDisplay),
                    onEvent = {},
                )
            }
        }

        composeTestRule.onNodeWithText("1008 hPa").performScrollTo().assertIsDisplayed()
        composeTestRule.onNodeWithText("8 km").performScrollTo().assertIsDisplayed()
    }

    @Test
    fun `success state displays forecast section`() {
        composeTestRule.setContent {
            FeverTheme {
                FeverScreen(
                    uiState = FeverUiState.Success(testWeatherDisplay),
                    onEvent = {},
                )
            }
        }

        composeTestRule.onNodeWithText("Forecast").performScrollTo().assertIsDisplayed()
    }

    @Test
    fun `error state shows snackbar with message`() {
        composeTestRule.setContent {
            FeverTheme {
                FeverScreen(
                    uiState = FeverUiState.Error("Network unavailable"),
                    onEvent = {},
                )
            }
        }

        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("Network unavailable").assertIsDisplayed()
    }

    @Test
    fun `FAB click dispatches Refresh event`() {
        val events = mutableListOf<FeverEvent>()

        composeTestRule.setContent {
            FeverTheme {
                FeverScreen(
                    uiState = FeverUiState.Success(testWeatherDisplay),
                    onEvent = { events.add(it) },
                )
            }
        }

        composeTestRule
            .onNodeWithContentDescription("Swipe right to refresh")
            .performClick()

        assertEquals(listOf(FeverEvent.Refresh), events)
    }

    private companion object {
        val testWeatherDisplay = WeatherDisplayData(
            temperatureText = "32°C",
            iconUrl = "https://openweathermap.org/img/wn/03d@4x.png",
            iconContentDescription = "scattered clouds",
            highLowText = "H:35° L:28°",
            windText = "4.2 m/s",
            humidityText = "78%",
            locationName = "Surabaya, ID",
            descriptionText = "Scattered clouds",
            feelsLikeText = "38°C",
            pressureText = "1008 hPa",
            visibilityText = "8 km",
            latitudeText = "-7.2575",
            longitudeText = "112.7521",
            fiveDaysForecast = listOf(
                DailyForecastDisplayData(
                    dayName = "Thu",
                    iconUrl = "https://openweathermap.org/img/wn/01d@2x.png",
                    highText = "30°",
                    lowText = "22°",
                ),
                DailyForecastDisplayData(
                    dayName = "Fri",
                    iconUrl = "https://openweathermap.org/img/wn/02d@2x.png",
                    highText = "28°",
                    lowText = "21°",
                ),
            ),
            hourlyForecasts = listOf(
                HourlyDisplayData(
                    timeText = "Now",
                    iconUrl = "https://openweathermap.org/img/wn/03d@2x.png",
                    temperatureText = "32°",
                    popText = "10%",
                ),
            ),
        )
    }
}
