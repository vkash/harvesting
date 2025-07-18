pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode = RepositoriesMode.FAIL_ON_PROJECT_REPOS
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
    }
}

rootProject.name = "harvesting"

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
include(":app")
include(":core:common")
include(":core:network")
include(":core:model")
include(":core:database")
include(":core:datastore")
include(":core:data")
include(":core:sync")
include(":core:ui")
include(":core:designsystem")
include(":core:domain")
include(":core:scanner")
include(":feature:sku")
include(":feature:settings")
include(":feature:calculator")
include(":feature:timesheet")
include(":feature:harvest")
include(":feature:brigade")
include(":feature:fields")
include(":feature:reports")
include(":feature:works")
include(":feature:appmenu")
