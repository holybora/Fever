plugins {
    id("feverweather.android.library")
    id("feverweather.android.hilt")
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.sls.handbook.core.network"

    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    implementation(project(":core:common"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.retrofit.core)
    implementation(libs.retrofit.converter.gson)
    implementation(libs.okhttp.core)
    implementation(libs.okhttp.logging)
    implementation(libs.gson)

    testImplementation(libs.junit)
}
