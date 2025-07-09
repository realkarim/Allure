import dependency.chucker
import dependency.dataStore
import dependency.domainModule
import dependency.hilt
import dependency.okHttp
import dependency.protoDataStoreModule
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
  domainModule()
  okHttp()
  protoDataStoreModule()
  retrofit()
  hilt()
  dataStore()
  chucker()
  testDeps()
  testImplDeps()
  testDebugDeps()
}
