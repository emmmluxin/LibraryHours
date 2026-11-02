pluginManagement {
    repositories {
        google()
        maven("https://repo1.maven.org/maven2/")
        maven("https://repo.huaweicloud.com/repository/maven/")
        mavenCentral()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        maven("https://repo1.maven.org/maven2/")
        maven("https://repo.huaweicloud.com/repository/maven/")
        mavenCentral()
    }
}
rootProject.name = "LibraryHours"
include(":app")
