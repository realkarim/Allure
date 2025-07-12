import dependency.androidx
import dependency.hilt
import dependency.testDebugDeps
import dependency.testDeps
import dependency.testImplDeps
import plugin.SharedLibraryGradlePlugin

plugins {
    id(plugin.BuildPlugins.ANDROID_LIBRARY)
}
apply<SharedLibraryGradlePlugin>()

android {
    namespace = "com.realkarim.navigator"
}

dependencies {
    androidx()
    hilt()
    testDeps()
    testImplDeps()
    testDebugDeps()
}