package flavor

import buildutils.BuildDimensions
import com.android.build.api.dsl.ApplicationProductFlavor
import com.android.build.api.dsl.LibraryProductFlavor
import org.gradle.api.NamedDomainObjectContainer

sealed class BuildFlavor(val name: String) {
    // App level
    abstract fun create(
        namedDomainObjectContainer: NamedDomainObjectContainer<ApplicationProductFlavor>
    ): ApplicationProductFlavor

    // Module level
    abstract fun createLibrary(
        namedDomainObjectContainer: NamedDomainObjectContainer<LibraryProductFlavor>
    ): LibraryProductFlavor


    object Google : BuildFlavor(FlavorTypes.GOOGLE) {
        override fun create(namedDomainObjectContainer: NamedDomainObjectContainer<ApplicationProductFlavor>): ApplicationProductFlavor {
            return namedDomainObjectContainer.create(name) {
                dimension = BuildDimensions.STORE
                applicationIdSuffix = ".$name"
                versionNameSuffix = "-$name"
            }
        }

        override fun createLibrary(namedDomainObjectContainer: NamedDomainObjectContainer<LibraryProductFlavor>): LibraryProductFlavor {
            return namedDomainObjectContainer.create(name) {
                dimension = BuildDimensions.STORE
            }
        }
    }

    object Huawei : BuildFlavor(FlavorTypes.HUAWEI) {
        override fun create(namedDomainObjectContainer: NamedDomainObjectContainer<ApplicationProductFlavor>): ApplicationProductFlavor {
            return namedDomainObjectContainer.create(name) {
                dimension = BuildDimensions.STORE
                applicationIdSuffix = ".$name"
                versionNameSuffix = "-$name"
            }
        }

        override fun createLibrary(namedDomainObjectContainer: NamedDomainObjectContainer<LibraryProductFlavor>): LibraryProductFlavor {
            return namedDomainObjectContainer.create(name) {
                dimension = BuildDimensions.STORE
            }
        }
    }

    object Provider : BuildFlavor(FlavorTypes.PROVIDER) {
        override fun create(namedDomainObjectContainer: NamedDomainObjectContainer<ApplicationProductFlavor>): ApplicationProductFlavor {
            return namedDomainObjectContainer.create(name) {
                dimension = BuildDimensions.APP
                applicationIdSuffix = ".$name"
                versionNameSuffix = "-$name"
            }
        }

        override fun createLibrary(namedDomainObjectContainer: NamedDomainObjectContainer<LibraryProductFlavor>): LibraryProductFlavor {
            return namedDomainObjectContainer.create(name) {
                dimension = BuildDimensions.APP
            }
        }
    }

    object Consumer : BuildFlavor(FlavorTypes.CONSUMER) {
        override fun create(namedDomainObjectContainer: NamedDomainObjectContainer<ApplicationProductFlavor>): ApplicationProductFlavor {
            return namedDomainObjectContainer.create(name) {
                dimension = BuildDimensions.APP
                applicationIdSuffix = ".$name"
                versionNameSuffix = "-$name"
            }
        }

        override fun createLibrary(namedDomainObjectContainer: NamedDomainObjectContainer<LibraryProductFlavor>): LibraryProductFlavor {
            return namedDomainObjectContainer.create(name) {
                dimension = BuildDimensions.APP
            }
        }
    }
}