plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.zhang.walmartassessment"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.zhang.walmartassessment"
        minSdk = 28
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    buildFeatures {
        buildConfig = true // Enabling BuildConfig feature
        viewBinding = true
    }
    buildTypes {
        release {
            buildConfigField ("String", "BASE_URL", "\"https://gist.githubusercontent.com/peymano-wmt/\"")
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        debug {
            buildConfigField ("String", "BASE_URL", "\"https://gist.githubusercontent.com/peymano-wmt/\"")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {
    // Core AndroidX libraries
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)

    // Lifecycle components
    implementation(libs.androidx.lifecycle.runtime)
    implementation(libs.androidx.lifecycle.viewmodel)

    // Retrofit for network calls
    implementation(libs.square.retrofit)
    implementation(libs.square.logging.interceptor)
    implementation(libs.square.retrofit.converter.gson)

    // Unit testing
    testImplementation(libs.junit)
    testImplementation(libs.io.mockk)
    testImplementation(libs.arch.core.testing)
    testImplementation(libs.jetbrains.coroutines.test)

    // Android instrumentation testing
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}