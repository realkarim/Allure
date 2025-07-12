import dependency.androidx
import dependency.dataModule
import dependency.domainModule
import dependency.hilt
import dependency.presentationModule
import dependency.retrofit
import dependency.room
import dependency.testDebugDeps
import dependency.testDeps
import dependency.testImplDeps
import plugin.SharedLibraryGradlePlugin

plugins {
    id(plugin.BuildPlugins.ANDROID_LIBRARY)
    /*
      Fix issue:
      Caused by: java.lang.RuntimeException: com.android.builder.errors.EvalIssueException: Starting in Kotlin 2.0, the Compose Compiler Gradle plugin is required
     */
    id(plugin.BuildPlugins.ANDROID_COMPOSE) version dependency.DependenciesVersions.COMPOSE_COMPILER
    id(plugin.BuildPlugins.HILT) version dependency.DependenciesVersions.HILT
}

android {
    namespace = "com.realkarim.signup"

    composeOptions {
        kotlinCompilerExtensionVersion = dependency.DependenciesVersions.KOTLIN_COMPILER
    }

    buildFeatures {
        compose = true
    }
}

apply<SharedLibraryGradlePlugin>()

dependencies {
    domainModule()
    dataModule()
    presentationModule()
    androidx()
    retrofit()
    hilt()
    room()
    testDeps()
    testImplDeps()
    testDebugDeps()
}
