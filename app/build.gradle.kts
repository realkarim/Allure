import buildutils.BuildConfig
import buildutils.BuildCreator
import buildutils.BuildDimensions
import dependency.DependenciesVersions
import dependency.androidx
import dependency.dataModule
import dependency.dataStore
import dependency.dataStoreModule
import dependency.domainModule
import dependency.hilt
import dependency.loginModule
import dependency.okHttp
import dependency.presentationModule
import dependency.protoDataStore
import dependency.protoDataStoreModule
import dependency.retrofit
import dependency.room
import dependency.testDebugDeps
import dependency.testDeps
import dependency.testImplDeps
import flavor.BuildFlavor
import release.ReleaseConfig
import signing.BuildSigning
import signing.SigningTypes
import test.TestBuildConfig

plugins {
  id(plugin.BuildPlugins.ANDROID_APPLICATION)
  id(plugin.BuildPlugins.KOTLIN_ANDROID)
  id(plugin.BuildPlugins.ANDROID)
  id(plugin.BuildPlugins.ANDROID_COMPOSE) version dependency.DependenciesVersions.COMPOSE_COMPILER
  id(plugin.BuildPlugins.KAPT)
  id(plugin.BuildPlugins.KTLINT)
  id(plugin.BuildPlugins.SPOTLESS)
  id(plugin.BuildPlugins.DETEKT)
  id(plugin.BuildPlugins.UPDATE_DEPS_VERSIONS)
  id(plugin.BuildPlugins.DOKKA)
  id(plugin.BuildPlugins.HILT) version dependency.DependenciesVersions.HILT
}

android {
  namespace = BuildConfig.APP_ID
  compileSdk = BuildConfig.COMPILE_SDK_VERSION

  defaultConfig {
    applicationId = BuildConfig.APP_ID
    minSdk = BuildConfig.MIN_SDK_VERSION
    targetSdk = BuildConfig.TARGET_SDK_VERSION
    versionCode = ReleaseConfig.VERSION_CODE
    versionName = ReleaseConfig.VERSION_NAME

    testInstrumentationRunner = TestBuildConfig.TEST_INSTRUMENTATION_RUNNER
  }

  signingConfigs {
    BuildSigning.Release(project).create(this)
    BuildSigning.ReleaseExternalQa(project).create(this)
    BuildSigning.Debug(project).create(this)
  }

  buildTypes {
    BuildCreator.Release(project).create(this).apply {
      proguardFiles(
        getDefaultProguardFile("proguard-android-optimize.txt"),
        "proguard-rules.pro",
      )
      signingConfig = signingConfigs.getByName(SigningTypes.RELEASE)
    }
    BuildCreator.Debug(project).create(this).apply {
      signingConfig = signingConfigs.getByName(SigningTypes.DEBUG)
    }
    BuildCreator.ReleaseExternalQA(project).create(this).apply {
      signingConfig = signingConfigs.getByName(SigningTypes.RELEASE_EXTERNAL_QA)
    }
  }

  flavorDimensions.add(BuildDimensions.APP)
  flavorDimensions.add(BuildDimensions.STORE)

  productFlavors {
    BuildFlavor.Google.create(this)
    BuildFlavor.Huawei.create(this)
    BuildFlavor.Consumer.create(this)
    BuildFlavor.Provider.create(this)
  }

  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
  }
  kotlinOptions {
    jvmTarget = JavaVersion.VERSION_11.toString()
  }

  composeOptions {
    kotlinCompilerExtensionVersion = DependenciesVersions.KOTLIN_COMPILER
  }

  buildFeatures {
    compose = true
    buildConfig = true
  }

  hilt {
    // Fix issue with :app:hiltAggregateDepsConsumerGoogleDebug
    enableAggregatingTask = false
  }
}

dependencies {
  loginModule()
  dataModule()
  domainModule()
  dataStoreModule() // Ideally hide it behind domainModule
  protoDataStoreModule()
  presentationModule()
  dataStore() // Find a way to remove this dependency, maybe wrap it in a dataStoreModule
  protoDataStore()
  androidx()
  hilt()
  room()
  okHttp()
  retrofit()
  testDeps()
  testImplDeps()
  testDebugDeps()
}
