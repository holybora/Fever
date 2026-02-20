plugins {
    id("feverweather.jvm.library")
}

dependencies {
    implementation(project(":core:model"))
    implementation(libs.javax.inject)

    testImplementation(libs.junit)
}
