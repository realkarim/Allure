plugins {
    `kotlin-dsl`
}

repositories {
    google()
    mavenCentral()
    gradlePluginPortal()
}

dependencies {
    api(kotlin("gradle-plugin:2.2.0"))
    implementation("com.android.tools.build:gradle:8.11.0")
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:2.2.0")
    implementation("com.diffplug.spotless:spotless-plugin-gradle:7.0.4")
}