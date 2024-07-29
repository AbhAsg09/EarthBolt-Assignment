// settings.gradle.kts

rootProject.name = "20MIS0158"
include() ":app"


pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
    }
}
include(":20mis0158")
include("::app")
