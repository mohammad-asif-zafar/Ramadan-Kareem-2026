 import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.baselineprofile)
    kotlin("kapt")
}

val localProperties = Properties()
val localPropertiesFile = rootProject.file("local.properties")
if (localPropertiesFile.exists()) {
    localPropertiesFile.inputStream().use { stream ->
        localProperties.load(stream)
    }
}

android {
    namespace = "com.hathway.ramadankareem2026"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.hathway.ramadankareem2026"
        minSdk = 29
        targetSdk = 36
        versionCode = 3
        versionName = "1.0.2"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        val mapsApiKey: String =
            (project.findProperty("MAPS_API_KEY") as String?)
                ?.takeIf { it.isNotBlank() }
                ?: (System.getenv("MAPS_API_KEY") as String?)
                    ?.takeIf { it.isNotBlank() }
                ?: localProperties.getProperty("MAPS_API_KEY")?.takeIf { it.isNotBlank() }
                ?: ""

        buildConfigField(
            "String",
            "MAPS_API_KEY",
            "\"$mapsApiKey\""
        )


        manifestPlaceholders["MAPS_API_KEY"] = mapsApiKey

    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }

        packaging {
            jniLibs {
                keepDebugSymbols += "**/*.so"
            }
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
}

dependencies {
    // Core
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)

    // Compose BOM
    implementation(platform(libs.androidx.compose.bom))

    // Compose UI
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    
    // Material icons
    implementation(libs.androidx.material.icons.extended)

    // Navigation (needed for Splash → Onboarding → Home)
    implementation(libs.androidx.navigation.compose)

    // DataStore (for first launch / onboarding)
    implementation(libs.androidx.datastore.preferences)

    // Splash Screen API
    implementation(libs.androidx.core.splashscreen)
    implementation(libs.androidx.room.runtime.android)
    implementation(libs.androidx.room.ktx)
    
    // Room compiler
    kapt(libs.androidx.room.compiler)

    // Testing
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)

    "baselineProfile"(project(":baselineprofile"))

    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)

    // GPS
    implementation(libs.play.services.location)
    
    // adhan
    implementation(libs.adhan)

    // glance for widget
    implementation(libs.glance.appwidget)
    implementation(libs.glance.material3)
    testImplementation(kotlin("test"))

    // profiler
    implementation(libs.androidx.profileinstaller)

    // GSON
    implementation(libs.gson)

    // coroutines
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.kotlinx.coroutines.play.services)

    // Network
    implementation(libs.retrofit.core)
    implementation(libs.retrofit.gson)
    implementation(libs.okhttp.logging)


    // ViewModel
    implementation(libs.lifecycle.viewmodel)

    // Google Maps
    implementation(libs.google.maps)

    // Maps Compose
    implementation(libs.maps.compose)
    implementation(libs.maps.compose.utils)
    implementation(libs.maps.compose.widgets)


    // Google Places (Nearby mosques)
    implementation(libs.google.places)

    // Test

    testImplementation(libs.okhttp.mockwebserver)
    testImplementation(libs.kotlinx.coroutines.test)

    //Media
    implementation(libs.media3.exoplayer)
    implementation(libs.media3.ui)
    implementation(libs.media3.session)

}