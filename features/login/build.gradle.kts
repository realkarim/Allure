import dependency.androidx
import dependency.hilt
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
    androidx()
    hilt()
    room()
    testDeps()
    testImplDeps()
    testDebugDeps()
}
