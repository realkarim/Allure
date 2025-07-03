import dependency.hilt
import dependency.okHttp
import dependency.retrofit
import dependency.testDebugDeps
import dependency.testDeps
import dependency.testImplDeps
import plugin.SharedLibraryGradlePlugin

plugins {
  id(plugin.BuildPlugins.ANDROID_LIBRARY)
}

android {
  namespace = "com.realkarim.data"
}

apply<SharedLibraryGradlePlugin>()

dependencies {
  okHttp()
  retrofit()
  hilt()
  testDeps()
  testImplDeps()
  testDebugDeps()
}
