plugins {
    id("feverweather.jvm.library")
}

dependencies {
    implementation(project(":core:model"))

    testImplementation(libs.junit)
}
