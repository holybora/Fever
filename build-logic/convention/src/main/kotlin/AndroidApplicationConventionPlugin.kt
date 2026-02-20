import com.android.build.api.dsl.ApplicationExtension
import com.sls.handbook.configureKotlinAndroid
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

/** Convention plugin for Android application modules. Applies Kotlin/Android config, detekt, kover, and lint. */
class AndroidApplicationConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply("com.android.application")
            pluginManager.apply("feverweather.detekt")
            pluginManager.apply("feverweather.kover")
            pluginManager.apply("feverweather.android.lint")

            extensions.configure<ApplicationExtension> {
                configureKotlinAndroid(this)
                defaultConfig.targetSdk = 36
            }
        }
    }
}
