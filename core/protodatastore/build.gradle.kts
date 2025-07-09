import dependency.Dependencies.protoBufArtifact
import dependency.hilt
import dependency.protoDataStore
import dependency.testDebugDeps
import dependency.testDeps
import dependency.testImplDeps
import plugin.SharedLibraryGradlePlugin

plugins {
  id(plugin.BuildPlugins.ANDROID_LIBRARY)
  id(plugin.BuildPlugins.GOOGLE_PROTOBUF)
}
apply<SharedLibraryGradlePlugin>()

android {
  namespace = "com.realkarim.protodatastore"
}

protobuf {
  protoc {
    artifact = protoBufArtifact
  }
  generateProtoTasks {
    all().forEach { task ->
      task.plugins {
        create("kotlin").apply {
          option("lite")
        }
      }
      task.plugins {
        create("java").apply {
          option("lite")
        }
      }
    }
  }
}

dependencies {
  protoDataStore()
  hilt()
  testDeps()
  testImplDeps()
  testDebugDeps()
}
