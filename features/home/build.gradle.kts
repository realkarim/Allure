import dependency.androidx
import dependency.hilt
import dependency.testDebugDeps
import dependency.testDeps
import dependency.testImplDeps

plugins {
    id(plugin.BuildPlugins.ANDROID_LIBRARY)
    id(plugin.BuildPlugins.KOTLIN_ANDROID)
    kotlin(plugin.BuildPlugins.KAPT)
}

android {
    namespace = "com.realkarim.home"
    compileSdk = 35

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {
    androidx()
    hilt()
    testDeps()
    testImplDeps()
    testDebugDeps()
}