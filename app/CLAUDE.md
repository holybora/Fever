# :app

Single-screen application entry point showing the Fever weather feature. Single-activity Compose app with type-safe navigation and Hilt DI. Enables edge-to-edge display with transparent system navigation bar.

## Module Info

- **Namespace:** `com.sls.handbook`
- **Application ID:** `com.sls.handbook`
- **Version:** 1.0 (versionCode 1)
- **Plugins:** `handyplay.android.application`, `handyplay.android.hilt`, `kotlin.compose`, `kotlin.serialization`

## Dependencies

- `:core:common`, `:core:ui`, `:core:designsystem`, `:core:domain`, `:core:data`, `:core:model`, `:core:network`
- `:navigation`
- `:feature:fever`
- AndroidX Core, Lifecycle, Activity Compose
- Compose BOM + Material3
- Navigation Compose, Hilt Navigation Compose

## Key Files

### Main

- `MainActivity.kt` — `@AndroidEntryPoint` single Activity, enables edge-to-edge with transparent system navigation bar, sets content to `HandyPlayApp`
- `HandyPlayApplication.kt` — `@HiltAndroidApp` Application class with Rebugger debug logging
- `ui/HandyPlayApp.kt` — Root composable with single `FeverRoute` destination, full-screen Fever weather display

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
