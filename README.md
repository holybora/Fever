# FeverWeather

Android weather app that shows current conditions and a 5-day forecast for random locations worldwide, built with Jetpack Compose and Material 3.

## Architecture

Multi-module clean architecture with unidirectional data flow. Single-activity app using Hilt for DI, ViewModel + StateFlow with sealed UiState for state management, and type-safe Compose Navigation.

```
UI (:app, :feature:fever)
  → Domain (:core:domain)
    → Data (:core:data)
      → Network (:core:network)
        → Model (:core:model)
```

## Modules

| Module | Purpose |
|--------|---------|
| `:app` | Single-activity entry point, NavHost, Hilt setup, edge-to-edge display |
| `:feature:fever` | Weather screen with glassmorphic UI, organized into `ui`, `entity`, and root packages — ViewModel orchestrating 5 use cases concurrently, localized error handling |
| `:core:domain` | Use cases, repository interfaces, and `WeatherException` sealed hierarchy (Network, Server, DataParsing) for typed error handling |
| `:core:data` | Repository implementations mapping network DTOs to domain models and Retrofit/IO exceptions to domain `WeatherException` types |
| `:core:network` | Retrofit client targeting OpenWeatherMap API with OkHttp interceptor for API key injection and configured timeouts (15s connect, 30s read/write) |
| `:core:model` | `@Serializable` data classes — Weather, ForecastData, Coordinates, DailyForecast, HourlyForecast |
| `:core:common` | Shared caching utilities (dynamic-TTL cache, cached network properties) |
| `:core:designsystem` | Material 3 theme, typography, and color palette with dynamic color support |
| `:navigation` | Type-safe `@Serializable` route definitions |
| `:build-logic` | Gradle convention plugins for consistent module configuration (50% minimum coverage threshold via Kover) |
