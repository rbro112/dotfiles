import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import proguard.gradle.ProGuardTask

plugins {
    // Ran into an issue trying to use pluginDep from Dependencies, so reverted back to this usage.
    // TODO: Ryan fix in future to use Dep utils.
    id("com.github.johnrengelman.shadow")
}

abstract class ExecutableExtension {
    var enableProguard: Boolean = false
}

val executableExtension = project.extensions.create<ExecutableExtension>("executable")

tasks {
    named<ShadowJar>("shadowJar") {
        manifest {
            attributes(mapOf("Main-Class" to "\$wrapperClassName"))
        }
        isZip64 = true
        minimize {
            exclude(dependency("org.jetbrains.kotlin:kotlin-reflect"))
        }
    }
    register<ProGuardTask>("proguard") {
        logger.info("Processing ${project.name} with proguard...")
        dependsOn("shadowJar")

        // Set configuration
        configuration(File(project.rootProject.projectDir, "proguard.pro"))
        val projectProguardFile = File(project.projectDir, "proguard.pro")
        if (projectProguardFile.exists()) {
            configuration(projectProguardFile)
        }

        // Define input/output
        injars(File(project.buildDir, "libs/${project.name}-all.jar"))
        outjars(File(project.buildDir, "libs/${project.name}-proguarded.jar"))

        // Automatically handle the Java version of this build.
        if (System.getProperty("java.version").startsWith("1.")) {
            // Before Java 9, the runtime classes were packaged in a single jar file.
            libraryjars(File(System.getProperty("java.home"), "lib/rt.jar"))
        } else {
            // As of Java 9, the runtime classes are packaged in modular jmod files.
            libraryjars(
                mapOf("!**.jar" to "!module-info.class"),
                File(System.getProperty("java.home"), "jmods/java.base.jmod")
            )
        }
    }
    register("nativeImage") {
        logger.info("Packaging ${project.name} into a GraalVM native image...")

        dependsOn("proguard")

        doLast {
            val graalVmBin = System.getenv("GRAALVM_BIN")


        }
        // TODO: Take Jar and run native-image using GraalVM env variable

        // TODO: Run GraalVM native-image
    }
    register("executable") {
        logger.info("Packaging ${project.name} into an executable binary...")
        val isProguardEnabled = executableExtension.enableProguard
        if (isProguardEnabled) dependsOn("proguard") else dependsOn("shadowJar")

        doLast {
            val jarFile = File(
                project.buildDir,
                if (isProguardEnabled) "libs/${project.name}-proguarded.jar" else "libs/${project.name}-all.jar"
            )
            require(jarFile.exists()) { "shadowJar output file at ${jarFile.canonicalPath} does not exist!" }
            val executableFile = File(project.buildDir, "libs/${project.name}")

            executableFile.apply {
                writeText("#!/usr/bin/env bash\nexec java -jar \$0 \"\$@\"\n")
                appendBytes(jarFile.readBytes())
                setExecutable(true)
            }
        }
    }
}
