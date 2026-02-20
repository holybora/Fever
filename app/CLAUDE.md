# :app

Single-screen application entry point showing the Fever weather feature. Single-activity Compose app with type-safe navigation and Hilt DI. Enables edge-to-edge display with transparent system navigation bar.

## Module Info

- **Namespace:** `com.sls.handbook`
- **Application ID:** `com.sls.handbook`
- **Version:** 1.0 (versionCode 1)
- **Plugins:** `feverweather.android.application`, `feverweather.android.hilt`, `kotlin.compose`, `kotlin.serialization`

## Dependencies

- `:core:designsystem`, `:core:domain`, `:core:data`, `:core:model`, `:core:network`
- `:navigation`
- `:feature:fever`
- AndroidX Core, Lifecycle, Activity Compose
- Compose BOM + Material3
- Navigation Compose, Hilt Navigation Compose

## Key Files

### Main

- `MainActivity.kt` — `@AndroidEntryPoint` single Activity, enables edge-to-edge with transparent system navigation
  bar (via `SystemBarStyle.auto(TRANSPARENT, TRANSPARENT)` and `isNavigationBarContrastEnforced = false`), sets content
  to `FeverWeatherApp`
- `FeverWeatherApplication.kt` — `@HiltAndroidApp` Application class
- `ui/FeverWeatherApp.kt` — Root composable with `BottomSearchBarViewModel`, manages search state and destination-based
  visibility. Conditionally applies Scaffold padding: edge-to-edge for `FeverDestination` (line 98-101), standard
  padding for other destinations
- `ui/FeverWeatherNavHost.kt` — NavHost routes: `WelcomeDestination` → `HomeDestination` (pops Welcome),
  `HomeDestination` → `CategoryDestination`, `CategoryDestination` → `TtlCacheDestination`, `GalleryDestination`, or
  `FeverDestination` via `Topic.ID_*`
- `ui/BottomSearchBarViewModel.kt` — `@HiltViewModel` managing search query, breadcrumb segments, bar visibility, and navigation events via Channel

### Debug-only: E2E Test Recording (src/debug/)

- `recording/RecordingActivity.kt` — Debug Activity that wraps `FeverWeatherApp()` with `RecordingOverlay`; launches via
  intent action `com.sls.handbook.E2E_RECORD`
- `recording/RecordingOverlay.kt` — Composable overlay: intercepts touch events via `pointerInput(PointerEventPass.Initial)`, classifies taps/swipes, shows draggable red stop button, pulsing recording indicator
- `recording/RecordingController.kt` — Thread-safe event collector with relative timestamps
- `recording/RecordedEvent.kt` — Event data classes (TAP, SWIPE, BACK_PRESS, TEXT_INPUT) and session metadata; serializes to JSON via `org.json`

## Source

- `src/main/java/com/sls/handbook/` — Production code (single Activity + navigation)

## Build Commands

```bash
./gradlew :app:assembleDebug
./gradlew :app:assembleRelease
./gradlew :app:testDebugUnitTest
./gradlew :app:connectedAndroidTest
./gradlew :app:installAndRun  # Installs debug APK and launches MainActivity
```

## Notes

- **Single screen**: App only shows `FeverRoute` via `FeverDestination`. No multi-screen navigation.
- **Edge-to-edge display**: System navigation bar is transparent, allowing full-screen content.
