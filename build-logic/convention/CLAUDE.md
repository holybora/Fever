# build-logic/convention

Convention plugins for standardised Gradle module configuration.

## Plugin Catalog

| Plugin ID                              | Class                                   | What it does                                                                                    |
|----------------------------------------|-----------------------------------------|-------------------------------------------------------------------------------------------------|
| `feverweather.android.application`     | `AndroidApplicationConventionPlugin`    | Android app defaults (SDK 36/33, Kotlin, Java 11, auto-applies detekt + kover + lint)           |
| `feverweather.android.library`         | `AndroidLibraryConventionPlugin`        | Android library defaults (SDK, Kotlin, Java 11, auto-applies detekt + kover + lint)             |
| `feverweather.android.library.compose` | `AndroidLibraryComposeConventionPlugin` | Adds Compose compiler plugin + Compose BOM                                                      |
| `feverweather.android.feature`         | `AndroidFeatureConventionPlugin`        | Library + Compose + Hilt + core module deps + Lifecycle                                         |
| `feverweather.android.hilt`            | `AndroidHiltConventionPlugin`           | KSP + Dagger Hilt (android + compiler)                                                          |
| `feverweather.android.test`            | `AndroidTestConventionPlugin`           | Common test dependencies (JUnit, MockK, Turbine, Coroutines Test, Robolectric, Compose UI test) |
| `feverweather.jvm.library`             | `JvmLibraryConventionPlugin`            | Pure JVM Kotlin (Java 11, no Android, auto-applies detekt + kover)                              |
| `feverweather.detekt`                  | `DetektConventionPlugin`                | Static analysis with detekt + formatting + Compose rules                                        |
| `feverweather.kover`                   | `KoverConventionPlugin`                 | Code coverage (50% min) with exclusions for generated code, Hilt, and Compose (`@Composable`)   |
| `feverweather.android.lint`            | `AndroidLintConventionPlugin`           | Android Lint with `warningsAsErrors`, HTML/XML reports, `config/lint/lint.xml`                  |

## Key Files

- `AndroidFeatureConventionPlugin.kt` — Most complex; auto-adds `:core:designsystem`, `:core:domain`, `:core:model`, `:navigation` + Compose/Lifecycle/Hilt deps
- `KoverConventionPlugin.kt` — Enforces 50% min coverage; excludes `@Composable`, `@Preview`, Hilt, and generated Android classes
- `DetektConventionPlugin.kt` — Configures detekt with custom rules from `config/detekt/detekt.yml`
- `KotlinAndroid.kt` — Shared Kotlin/Android configuration (SDK levels, JVM target)
- `ProjectExtensions.kt` — `libs` extension for accessing version catalog

## Build Config

- `build.gradle.kts` — Uses `java-gradle-plugin` + `kotlin-dsl`
- Compiles with Java 17 / Kotlin JVM 17 (build tooling only, app targets Java 11)

## Notes

- Plugin IDs are registered in `build.gradle.kts` via `gradlePlugin { plugins { ... } }`
- All convention plugins read versions from `gradle/libs.versions.toml` via the `libs` accessor
- When adding a new module, pick the appropriate convention plugin to avoid manual dependency setup
