val snakeRegex = "[-_][a-zA-Z]".toRegex()

fun String.toLowerCamelCase(): String {
    return snakeRegex.replace(this) {
        it.value.replace("_", "")
            .replace("-", "")
            .toUpperCase()
    }
}

fun String.toUpperCamelCase(): String = toLowerCamelCase().capitalize()

// Configure each sub project with the application plugin to avoid the boilerplate of having to do it in each module
// individually.
subprojects {

    apply<ApplicationPlugin>()

    // this is defined in buildSrc/src/main/kotlin/packaging.gradle.kts
    apply<PackagingPlugin>()

    configure<ApplicationPluginConvention> {
        // Automatically configure the main class of the application by using the convention that the main class
        // file should be a kotlin file matching the camelcased version of the module name with no package.
        // Individual applications can override this in their build.gradle via:
        // application {
        //    mainClass = 'your.MainClassKt'
        // }
        val mainClass = this@subprojects.name.toUpperCamelCase()
        val expectedMainFile = File(projectDir, "src/main/kotlin/$mainClass.kt")
        check(expectedMainFile.exists()) {
            "Expected the application $name to have a file ${expectedMainFile.relativeTo(projectDir)}"
        }

        mainClassName = mainClass + "Kt"
    }

    val run by tasks.getting(JavaExec::class) {
        standardInput = System.`in`
    }
}