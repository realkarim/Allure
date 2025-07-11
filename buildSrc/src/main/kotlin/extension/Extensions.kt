package extension

import com.android.build.api.dsl.ApplicationBuildType
import com.android.build.api.dsl.LibraryBuildType
import org.gradle.api.Project
import java.util.Properties

private const val LOCAL_PROPERTIES_FILE_NAME = "credentials.properties"

fun Project.getLocalProperty(propertyName: String): String {
    val localProperties = Properties().apply {
        val localPropertiesFile = project.rootProject.file(LOCAL_PROPERTIES_FILE_NAME)

        if (localPropertiesFile.exists()) {
            load(localPropertiesFile.inputStream())
        }
    }
    return localProperties.getProperty(propertyName)
        ?: throw NoSuchFieldException("Property does not exist: $propertyName")
}

fun ApplicationBuildType.buildConfigStringField(name: String, value: String) {
    this.buildConfigField("String", name, value)
}

fun ApplicationBuildType.buildConfigIntField(name: String, value: Int) {
    this.buildConfigField("Integer", name, value.toString())
}

fun ApplicationBuildType.buildConfigBooleanField(name: String, value: Boolean) {
    this.buildConfigField("Boolean", name, value.toString())
}

fun LibraryBuildType.buildConfigStringField(name: String, value: String) {
    this.buildConfigField("String", name, value)
}

fun LibraryBuildType.buildConfigIntField(name: String, value: Int) {
    this.buildConfigField("Integer", name, value.toString())
}

fun LibraryBuildType.buildConfigBooleanField(name: String, value: Boolean) {
    this.buildConfigField("Boolean", name, value.toString())
}