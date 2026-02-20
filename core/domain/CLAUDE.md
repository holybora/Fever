# :core:domain

Business logic and use cases layer. **JVM-only** — no Android dependencies.

## Module Info

- **Type:** JVM Library (no Android)
- **Plugin:** `feverweather.jvm.library`

## Dependencies

- `:core:model`
- `javax.inject` — JSR-330 `@Inject` for Hilt auto-discovery of use cases

## Key Files

- `repository/WeatherRepository.kt` — Repository interface with `getWeather(lat, lon)` and `getForecastData(lat, lon)` for raw weather data access
- `usecase/GetCurrentWeatherUseCase.kt` — Suspend use case wrapping `WeatherRepository.getWeather()`
- `usecase/GetForecastDataUseCase.kt` — Suspend use case wrapping `WeatherRepository.getForecastData()`
- `usecase/GetFiveDayForecastUseCase.kt` — Pure use case: groups forecast items by UTC date, filters future-only, aggregates daily (midday selection + min/max temps)
- `usecase/GetTodayHourlyForecastUseCase.kt` — Pure use case: timezone-aware today filtering, maps `ForecastItem` to `HourlyForecast`
- `usecase/GenerateRandomCoordinatesUseCase.kt` — Pure use case: generates random lat/lon within valid geographic bounds

## Source

- `src/main/kotlin/com/sls/handbook/core/domain/`

## Tests

- `src/test/` — JVM unit tests
  - `usecase/GetFiveDayForecastUseCaseTest.kt` — Date grouping, future filtering, midday selection, min/max aggregation
  - `usecase/GetTodayHourlyForecastUseCaseTest.kt` — Timezone-aware filtering, field mapping
  - `usecase/GenerateRandomCoordinatesUseCaseTest.kt` — Bounds validation

## Notes

- Place use case classes and repository interfaces here
- Use cases should be single-responsibility (`operator fun invoke()` pattern)
- Repository interfaces defined here, implementations in `:core:data`
- No Hilt, Compose, or Android framework references allowed
