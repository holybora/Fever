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
//        val apiKey = localProps.getProperty("OPENWEATHER_API_KEY")
//            ?: providers.environmentVariable("OPENWEATHER_API_KEY").orNull
//            ?: ""
        //FIXME: This should ideally be placed in local.properties,
        // but I’m leaving it here so the app can be built and run without any additional changes
        buildConfigField("String", "OPENWEATHER_API_KEY", "\"ae103060692fe13422deb98285505dc6\"")
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
