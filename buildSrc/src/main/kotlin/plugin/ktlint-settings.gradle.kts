import com.android.build.gradle.internal.tasks.factory.dependsOn

val ktlint: Configuration by configurations.creating

dependencies {
    ktlint("com.pinterest:ktlint:0.49.0") {
        attributes {
            attribute(Bundling.BUNDLING_ATTRIBUTE, objects.named(Bundling.EXTERNAL))
        }
    }
}

tasks.register<JavaExec>("ktlintFormat") {
    group = LifecycleBasePlugin.VERIFICATION_GROUP
    description = "Check Kotlin code style and format"
    classpath = ktlint
    mainClass.set("com.pinterest.ktlint.Main")
    jvmArgs("--add-opens=java.base/java.lang=ALL-UNNAMED")
    args(
        "-F",
        "**/src/**/*.kt",
        "**.kts",
        "!**/build/**",
    )
}

tasks{
    // App wont build if ktlintFormat fails
    named("preBuild").dependsOn("ktlintFormat")
}