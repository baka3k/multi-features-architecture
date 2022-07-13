pluginManagement {
    includeBuild("build-logic")
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        maven(url = "https://androidx.dev/snapshots/builds/8455591/artifacts/repository") {
            content {
                // The AndroidX snapshot repository will only have androidx artifacts, don't
                // bother trying to find other ones
                includeGroupByRegex("androidx\\..*")
            }
        }
        google()
        mavenCentral()
    }
}
rootProject.name = "MultiFeatureApp"
include(":app")
//include(":core-ui")
include(":core:core-navigation")
include(":core:core-data")
include(":core:core-network")
include(":core:core-model")
include(":core:core-datastore")
include(":core:core-common")
include(":core:core-database")
include(":core:core-ui")

include(":features:feature-movie")

//include(":features:feature-movie")
//
//include(":sync")
//
//include(":feature-foryou")
//include(":feature-interests")
//include(":feature-topic")
//include(":feature-author")
//include(":feature-movies")

