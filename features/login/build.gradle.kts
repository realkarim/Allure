import dependency.androidx
import dependency.dataModule
import dependency.domainModule
import dependency.hilt
import dependency.retrofit
import dependency.room
import dependency.testDebugDeps
import dependency.testDeps
import dependency.testImplDeps
import plugin.SharedLibraryGradlePlugin

plugins {
  id(plugin.BuildPlugins.ANDROID_LIBRARY)
}

android {
  namespace = "com.realkarim.login"
}

apply<SharedLibraryGradlePlugin>()

dependencies {
  domainModule()
  dataModule()
  androidx()
  retrofit()
  hilt()
  room()
  testDeps()
  testImplDeps()
  testDebugDeps()
}
