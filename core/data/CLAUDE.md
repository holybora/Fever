# :core:data

Repository implementations and data sources.

## Module Info

- **Namespace:** `com.sls.handbook.core.data`
- **Type:** Android Library
- **Plugins:** `feverweather.android.library`, `feverweather.android.hilt`

## Dependencies

- `:core:domain` — repository interfaces
- `:core:model` — data models
- `:core:common` — utilities
- `:core:network` — API clients

## Key Files

- `repository/WeatherRepositoryImpl.kt` — `@Singleton` thin implementation: fetches current weather and raw forecast data via OpenWeatherMap API, maps DTOs to domain models (no business logic — filtering/aggregation handled by use cases in `:core:domain`)
- `di/DataModule.kt` — Hilt `@Binds` mapping repository implementations to interfaces
- `di/ApiKeyModule.kt` — Provides `ApiKeyProvider` implementation from BuildConfig

## Source

- `src/main/java/com/sls/handbook/core/data/`

## Tests

- `src/test/` — JVM unit tests
  - `WeatherRepositoryImplTest.kt` — MockK-based API mocking, response mapping, null handling, error propagation tests

## Notes

- Implements repository interfaces from `:core:domain`
- Uses Hilt `@Module` + `@Binds` to provide repository implementations
- API key read from `local.properties` or `OPENWEATHER_API_KEY` env var via BuildConfig
