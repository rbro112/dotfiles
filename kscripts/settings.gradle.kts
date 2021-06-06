rootProject.name = "Ryan's Kscripts"

// The two top level modules are "applications" and "utils", which are the two types of projects we support
// Those are added manually here, but then we programmatically register all of their subprojects to avoid needing
// to manually list each subproject here as they are created.

fun includeParentModule(parentModule: String) {
    include(parentModule)

    File(rootProject.projectDir, parentModule)
        .listFiles()
        ?.filter { it.isDirectory && File(it, "build.gradle.kts").exists() }
        ?.forEach { subProjectDirectory ->
            include(":$parentModule:${subProjectDirectory.name}")
        }
}

includeParentModule("applications")
includeParentModule("utils")