import dependency.DependenciesVersions
import dependency.androidx
import dependency.domainModule
import dependency.testDebugDeps
import dependency.testDeps
import dependency.testImplDeps
import plugin.SharedLibraryGradlePlugin

plugins {
  id(plugin.BuildPlugins.ANDROID_LIBRARY)
  id(plugin.BuildPlugins.ANDROID_COMPOSE) version dependency.DependenciesVersions.COMPOSE_COMPILER
}

android {
  namespace = "com.realkarim.presentation"

  composeOptions {
    kotlinCompilerExtensionVersion = DependenciesVersions.KOTLIN_COMPILER
  }

  buildFeatures {
    compose = true
  }
}

apply<SharedLibraryGradlePlugin>()

dependencies {
  androidx()
  domainModule()
  testDeps()
  testImplDeps()
  testDebugDeps()
}
