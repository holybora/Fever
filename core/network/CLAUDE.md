# :core:network

Network/API layer with HTTP client setup and DI.

## Module Info

- **Namespace:** `com.sls.handbook.core.network`
- **Type:** Android Library
- **Plugins:** `feverweather.android.library`, `feverweather.android.hilt`, `kotlin.serialization`

## Dependencies

- `kotlinx-serialization-json`, `retrofit`, `okhttp`, `gson`

## Key Files

- `api/WeatherApi.kt` — Retrofit interface: `getWeather(lat, lon, units, lang)` and `getForecast(lat, lon, units, lang)`
- `model/WeatherResponse.kt` — Gson-annotated response DTOs for current weather and forecast from OpenWeatherMap
- `interceptor/ApiKeyInterceptor.kt` — OkHttp interceptor that adds `appid` query parameter via `ApiKeyProvider`
- `ApiKeyProvider.kt` — Interface for supplying the API key (implemented in `:core:data`)
- `di/NetworkModule.kt` — Hilt `@Module` providing OkHttp (with 15s connect / 30s read/write timeouts), Retrofit, and `WeatherApi`

## Source

- `src/main/kotlin/com/sls/handbook/core/network/`

## Tests

- `src/test/` — JVM unit tests
  - `ApiKeyInterceptorTest.kt` — MockK-based chain mocking, query parameter injection, existing parameter preservation

## Notes

- All API service interfaces and network configuration live here
- Uses Gson for JSON parsing
- Hilt provides singleton network instances (HTTP client, API services)
