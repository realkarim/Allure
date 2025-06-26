plugins {
    id(BuildPlugins.ANDROID_APPLICATION)
    id(BuildPlugins.KOTLIN_ANDROID)
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
        getByName(BuildTypes.RELEASE) {
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            isMinifyEnabled = Build.Release.isMinifyEnabled
            enableUnitTestCoverage = Build.Release.enableUnitTestCoverage
            isDebuggable = Build.Release.isDebuggable
        }
        getByName(BuildTypes.DEBUG) {
            isMinifyEnabled = Build.Debug.isMinifyEnabled
            enableUnitTestCoverage = Build.Debug.enableUnitTestCoverage
            isDebuggable = Build.Debug.isDebuggable
            versionNameSuffix = Build.Debug.versionNameSuffix
            applicationIdSuffix = Build.Debug.applicationIdSuffix
        }
        create(BuildTypes.RELEASE_EXTERNAL_QA) {
            isMinifyEnabled = Build.ReleaseExternalQa.isMinifyEnabled
            enableUnitTestCoverage = Build.ReleaseExternalQa.enableUnitTestCoverage
            isDebuggable = Build.ReleaseExternalQa.isDebuggable
            versionNameSuffix = Build.ReleaseExternalQa.versionNameSuffix
            applicationIdSuffix = Build.ReleaseExternalQa.applicationIdSuffix
        }
    }

    flavorDimensions.add(BuildDimensions.APP)
    flavorDimensions.add(BuildDimensions.STORE)

    productFlavors {
        BuildFlavor.Google.create(this)
        BuildFlavor.Huawei.create(this)
        BuildFlavor.Consumer.create(this)
        BuildFlavor.Provider.create(this)
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
