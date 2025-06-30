package plugin

import buildutils.BuildConfig
import buildutils.BuildCreator
import buildutils.BuildDimensions
import com.android.build.api.dsl.LibraryExtension
import dependency.DependenciesVersions
import flavor.BuildFlavor
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import signing.BuildSigning
import signing.SigningTypes
import test.TestBuildConfig

class SharedLibraryGradlePlugin : Plugin<Project> {
    override fun apply(project: Project) {
        project.addPluginConfigurations()
        project.addAndroidConfigurations()
        project.applyKotlinOptions()
    }

    private fun Project.addPluginConfigurations() {
        plugins.apply(BuildPlugins.KOTLIN_ANDROID)
        plugins.apply(BuildPlugins.KAPT)
        plugins.apply(BuildPlugins.KTLINT)
    }

    private fun Project.addAndroidConfigurations() {
        extensions.getByType(LibraryExtension::class.java).apply {
            compileSdk = BuildConfig.COMPILE_SDK_VERSION

            defaultConfig {
                minSdk = BuildConfig.MIN_SDK_VERSION
                testInstrumentationRunner = TestBuildConfig.TEST_INSTRUMENTATION_RUNNER
            }

            signingConfigs {
                BuildSigning.Release(project).create(this)
                BuildSigning.ReleaseExternalQa(project).create(this)
                BuildSigning.Debug(project).create(this)
            }

            buildTypes {
                BuildCreator.Release(project).createLibrary(this).apply {
                    proguardFiles(
                        getDefaultProguardFile("proguard-android-optimize.txt"),
                        "proguard-rules.pro"
                    )
                    signingConfig = signingConfigs.getByName(SigningTypes.RELEASE)
                }
                BuildCreator.Debug(project).createLibrary(this).apply {
                    signingConfig = signingConfigs.getByName(SigningTypes.DEBUG)
                }
                BuildCreator.ReleaseExternalQA(project).createLibrary(this).apply {
                    signingConfig = signingConfigs.getByName(SigningTypes.RELEASE_EXTERNAL_QA)
                }
            }

            flavorDimensions.add(BuildDimensions.APP)
            flavorDimensions.add(BuildDimensions.STORE)

            productFlavors {
                BuildFlavor.Google.createLibrary(this)
                BuildFlavor.Huawei.createLibrary(this)
                BuildFlavor.Consumer.createLibrary(this)
                BuildFlavor.Provider.createLibrary(this)
            }

            composeOptions {
                kotlinCompilerExtensionVersion = DependenciesVersions.KOTLIN_COMPILER
            }

            buildFeatures {
                // TODO: Switch compose on and find a way to apply BuildPlugins.ANDROID_COMPOSE
                // compose = true
                buildConfig = true
            }

            compileOptions {
                sourceCompatibility = JavaVersion.VERSION_11
                targetCompatibility = JavaVersion.VERSION_11
            }
        }
    }

    private fun Project.applyKotlinOptions() {
        tasks.withType<KotlinCompile>().configureEach {
            compilerOptions {
                jvmTarget.set(JvmTarget.JVM_11)
            }
        }
    }
}