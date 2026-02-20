package com.sls.handbook.feature.fever

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sls.handbook.core.domain.usecase.GenerateRandomCoordinatesUseCase
import com.sls.handbook.core.domain.usecase.GetCurrentWeatherUseCase
import com.sls.handbook.core.domain.usecase.GetFiveDayForecastUseCase
import com.sls.handbook.core.domain.usecase.GetForecastDataUseCase
import com.sls.handbook.core.domain.usecase.GetTodayHourlyForecastUseCase
import com.sls.handbook.feature.fever.di.IoDispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.Locale
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class FeverViewModel @Inject constructor(
    private val stringResolver: StringResolver,
    private val getCurrentWeather: GetCurrentWeatherUseCase,
    private val getForecastData: GetForecastDataUseCase,
    private val getFiveDayForecast: GetFiveDayForecastUseCase,
    private val getTodayHourlyForecast: GetTodayHourlyForecastUseCase,
    private val generateRandomCoordinates: GenerateRandomCoordinatesUseCase,
    @param:IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) : ViewModel() {

    private val _uiState = MutableStateFlow<FeverUiState>(FeverUiState.Loading)
    val uiState: StateFlow<FeverUiState> = _uiState.asStateFlow()

    init {
        loadWeather()
    }

    fun onEvent(event: FeverEvent) {
        when (event) {
            FeverEvent.Refresh -> {
                if (_uiState.value !is FeverUiState.Loading) {
                    loadWeather()
                }
            }
        }
    }

    private fun loadWeather() {
        _uiState.value = FeverUiState.Loading
        viewModelScope.launch(ioDispatcher) {
            try {
                val coordinates = generateRandomCoordinates()
                val lang = Locale.getDefault().language
                val (weather, forecastData) = coroutineScope {
                    val weatherDeferred = async {
                        getCurrentWeather(coordinates.latitude, coordinates.longitude, lang)
                    }
                    val forecastDataDeferred = async {
                        getForecastData(coordinates.latitude, coordinates.longitude, lang)
                    }
                    weatherDeferred.await() to forecastDataDeferred.await()
                }
                val dailyForecast = getFiveDayForecast(forecastData)
                val hourlyForecasts = getTodayHourlyForecast(forecastData)
                // fake delay to make animation transition smooth
                delay(FadeDurationMs.toLong())
                _uiState.value = FeverUiState.Success(
                    weather.toDisplayData(
                        stringResolver = stringResolver,
                        dailyForecast = dailyForecast,
                        hourlyForecasts = hourlyForecasts,
                    ),
                )
            } catch (e: CancellationException) {
                throw e
            } catch (@Suppress("TooGenericExceptionCaught") e: Exception) {
                _uiState.value = FeverUiState.Error(
                    e.message ?: stringResolver.getString(R.string.fever_unknown_error),
                )
            }
        }
    }
}
