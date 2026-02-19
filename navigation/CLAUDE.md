# :navigation

Type-safe navigation route definitions. Minimal module providing Fever destination for the app.

## Module Info

- **Namespace:** `com.sls.handbook.navigation`
- **Type:** Android Library
- **Plugins:** `feverweather.android.library`, `kotlin.serialization`

## Dependencies (exposed as `api`)

- `androidx.navigation:navigation-compose`
- `kotlinx-serialization-json`

## Key Files

- `FeverDestination.kt` — `@Serializable` destination object for Fever weather screen

## Source

- `src/main/kotlin/com/sls/handbook/navigation/`

## Tests

- `src/test/` — JVM unit tests
  - `FeverDestinationTest.kt` — Navigation destination instantiation test

## Notes

- All navigation destinations are `@Serializable` objects/data classes
- Currently only defines `FeverDestination` since the app is single-screen
- Feature modules depend on this module to reference destinations
