package dependency

object Dependencies {
    // AndroidX Core & Lifecycle
    const val ANDROIDX_CORE = "androidx.core:core-ktx:${DependenciesVersions.CORE_KTX}"
    const val ANDROIDX_LIFECYCLE_RUNTIME_KTX =
        "androidx.lifecycle:lifecycle-runtime-ktx:${DependenciesVersions.LIFE_CYCLE_RUNTIME_KTX}"

    // Jetpack Compose
    const val ANDROIDX_ACTIVITY_COMPOSE =
        "androidx.activity:activity-compose:${DependenciesVersions.ACTIVITY_COMPOSE}"
    const val ANDROIDX_UI = "androidx.compose.ui:ui:${DependenciesVersions.COMPOSE_UI}"
    const val ANDROIDX_UI_GRAPHICS =
        "androidx.compose.ui:ui-graphics:${DependenciesVersions.COMPOSE_UI}"
    const val ANDROIDX_UI_TOOLING_PREVIEW =
        "androidx.compose.ui:ui-tooling-preview:${DependenciesVersions.COMPOSE_UI}"
    const val ANDROIDX_MATERIAL3 =
        "androidx.compose.material3:material3:${DependenciesVersions.MATERIAL_3}"
    const val WORK_RUNTIME = "androidx.work:work-runtime-ktx:${DependenciesVersions.RUN_TIME}"

    const val APP_COMPAT = "androidx.appcompat:appcompat:${DependenciesVersions.APP_COMPAT}"
    const val MATERIAL = "com.google.android.material:material:${DependenciesVersions.MATERIAL}"
    const val ANDROIDX_ACTIVITY = "androidx.activity:activity-ktx:${DependenciesVersions.ANDROIDX_ACTIVITY}"
    const val COMPOSE_MATERIAL =
        "androidx.compose.material:material:${DependenciesVersions.COMPOSE_MATERIAL}"
    const val COMPOSE_RUNTIME = "androidx.lifecycle:lifecycle-runtime-compose:${DependenciesVersions.COMPOSE_RUNTIME}"
    const val navigationFragmentKtx =
        "androidx.navigation:navigation-fragment-ktx:${DependenciesVersions.NAVIGATION}"
    const val navigation =
        "androidx.navigation:navigation-ui-ktx:${DependenciesVersions.NAVIGATION}"
    const val navigation2 = "androidx.navigation:navigation-compose:${DependenciesVersions.NAVIGATION}"
    const val googleJson = "com.google.code.gson:gson:${DependenciesVersions.GOOGLE_GSON}"

    // Dependency Injection (Hilt)
    const val HILT_ANDROID = "com.google.dagger:hilt-android:${DependenciesVersions.HILT}"
    const val HILT_COMPILER = "com.google.dagger:hilt-compiler:${DependenciesVersions.HILT}"
    const val HILT_AGP = "com.google.dagger:hilt-android-gradle-plugin:${DependenciesVersions.HILT}"
    const val HILT_COMPOSE = "androidx.hilt:hilt-work:${DependenciesVersions.HILT_COMPOSE}"
    const val HILT_COMPILER_KAPT =
        "androidx.hilt:hilt-compiler:${DependenciesVersions.HILT_COMPOSE}"
    const val HILT_NAVIGATION =
        "androidx.hilt:hilt-navigation-compose:${DependenciesVersions.HILT_COMPOSE}"

    // Networking (Retrofit & OkHttp)
    const val RETROFIT = "com.squareup.retrofit2:retrofit:${DependenciesVersions.RETROFIT}"
    const val RETROFIT_CONVERTER_GSON =
        "com.squareup.retrofit2:converter-gson:${DependenciesVersions.RETROFIT}"
    const val RETROFIT_KOTLIN_COROUTINES_ADAPTER =
        "com.jakewharton.retrofit:retrofit2-kotlin-coroutines-adapter:${DependenciesVersions.RETROFIT_COROUTINE_ADAPTER_VERSION}"

    const val OKHTTP = "com.squareup.okhttp3:okhttp:${DependenciesVersions.OKHTTP}"
    const val OKHTTP_LOGGING_INTERCEPTOR =
        "com.squareup.okhttp3:logging-interceptor:${DependenciesVersions.OKHTTP}"

    // Database (Room)
    const val ROOM_RUNTIME = "androidx.room:room-runtime:${DependenciesVersions.ROOM}"
    const val ROOM_COMPILER = "androidx.room:room-compiler:${DependenciesVersions.ROOM}"
    const val ROOM_KTX = "androidx.room:room-ktx:${DependenciesVersions.ROOM}"

    // Other
    const val datastore = "androidx.datastore:datastore:${DependenciesVersions.DATA_STORE}"
    const val kotlinCollections = "org.jetbrains.kotlinx:kotlinx-collections-immutable:${DependenciesVersions.KOTLIN_COLLECTIONS}"
    const val kotlinSerilaizations = "org.jetbrains.kotlinx:kotlinx-serialization-json:${DependenciesVersions.KOTLIN_SERIALIZATIONS}"
    const val chuckerDebug = "com.github.chuckerteam.chucker:library:${DependenciesVersions.CHUCKER}"
    const val chuckerRelease = "com.github.chuckerteam.chucker:library-no-op:${DependenciesVersions.CHUCKER}"
    const val protoBufJavaLite = "com.google.protobuf:protobuf-javalite:${DependenciesVersions.PROTO_BUF_JAVA}"
    const val protoBufKotlinLite = "com.google.protobuf:protobuf-kotlin-lite:${DependenciesVersions.PROTO_BUF_KOTLIN}"
    const val protoBufArtifact = "com.google.protobuf:protoc:${DependenciesVersions.PROTO_BUF_KOTLIN}"
}



