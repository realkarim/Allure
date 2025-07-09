import dependency.dataStore
import dependency.testDebugDeps
import dependency.testDeps
import dependency.testImplDeps
import plugin.SharedLibraryGradlePlugin

plugins {
  id(plugin.BuildPlugins.ANDROID_LIBRARY)
}
apply<SharedLibraryGradlePlugin>()

android {
  namespace = "com.realkarim.datastore"
}

dependencies {
  dataStore()
  testDeps()
  testImplDeps()
  testDebugDeps()
  testImplementation(kotlin("test"))
}
