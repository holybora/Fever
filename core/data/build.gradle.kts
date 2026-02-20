import java.util.Properties

plugins {
    id("feverweather.android.library")
    id("feverweather.android.hilt")
}

android {
    namespace = "com.sls.handbook.core.data"

    buildFeatures {
        buildConfig = true
    }

    defaultConfig {
        val localProps = Properties()
        val localPropsFile = rootProject.file("local.properties")
        if (localPropsFile.exists()) {
            localProps.load(localPropsFile.inputStream())
        }
        val apiKey = localProps.getProperty("OPENWEATHER_API_KEY")
            ?: providers.environmentVariable("OPENWEATHER_API_KEY").orNull
            ?: ""
        buildConfigField("String", "OPENWEATHER_API_KEY", "\"$apiKey\"")
    }
}

dependencies {
    implementation(project(":core:domain"))
    implementation(project(":core:model"))
    implementation(project(":core:common"))
    implementation(project(":core:network"))

    implementation(libs.androidx.core.ktx)
    testImplementation(libs.junit)
    testImplementation(libs.mockk)
    testImplementation(libs.kotlinx.coroutines.test)
}
