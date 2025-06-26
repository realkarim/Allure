plugins {
    id(BuildPlugins.ANDROID_APPLICATION) version DependenciesVersions.AGP
    id(BuildPlugins.KOTLIN_ANDROID) version DependenciesVersions.KOTLIN
    id(BuildPlugins.ANDROID_COMPOSE) version DependenciesVersions.KOTLIN
}

android {
    namespace = BuildConfig.APP_ID
    compileSdk = BuildConfig.COMPILE_SDK_VERSION

    defaultConfig {
        applicationId = BuildConfig.APP_ID
        minSdk = BuildConfig.MIN_SDK_VERSION
        targetSdk = BuildConfig.TARGET_SDK_VERSION
        versionCode = ReleaseConfig.VERSION_CODE
        versionName = ReleaseConfig.VERSION_NAME

        testInstrumentationRunner = TestBuildConfig.TEST_INSTRUMENTATION_RUNNER
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
    buildFeatures {
        compose = true
    }
}

dependencies {
    // --- Core & Lifecycle ---
    implementation(Dependencies.ANDROIDX_CORE_KTX)
    implementation(Dependencies.ANDROIDX_LIFECYCLE_RUNTIME_KTX)

    // --- Jetpack Compose ---
    implementation(Dependencies.ANDROIDX_ACTIVITY_COMPOSE)
    implementation(platform(Dependencies.ANDROIDX_COMPOSE_BOM))
    implementation(Dependencies.ANDROIDX_UI)
    implementation(Dependencies.ANDROIDX_UI_GRAPHICS)
    implementation(Dependencies.ANDROIDX_UI_TOOLING_PREVIEW)
    implementation(Dependencies.ANDROIDX_MATERIAL3)

    // --- Unit Testing ---
    testImplementation(TestDependencies.JUNIT)

    // --- Android Instrumented Testing ---
    androidTestImplementation(TestDependencies.ANDROIDX_JUNIT)
    androidTestImplementation(TestDependencies.ANDROIDX_ESPRESSO_CORE)
    androidTestImplementation(platform(Dependencies.ANDROIDX_COMPOSE_BOM))
    androidTestImplementation(TestDependencies.ANDROIDX_UI_TEST_JUNIT4)

    // --- Debug-Only Dependencies ---
    debugImplementation(Dependencies.ANDROIDX_UI_TOOLING)
    debugImplementation(TestDependencies.ANDROIDX_UI_TEST_MANIFEST)
}
