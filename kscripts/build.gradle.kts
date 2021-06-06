import deps.Deps
import deps.api
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

buildscript {

    repositories {
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
    }

    dependencies {
        classpath(kotlin("gradle-plugin", version = "1.5.10"))
        classpath(kotlin("serialization", version = "1.5.10"))
    }
}

allprojects {
    repositories {
        mavenCentral()
    }
}

subprojects {
    apply(plugin = Deps.Kotlin.plugin_jvm.artifact)

    dependencies {
        api(Deps.KotlinX.coroutines)
    }

    tasks.withType<KotlinCompile>().all {
        kotlinOptions {
            jvmTarget = JavaVersion.VERSION_11.toString()
        }
    }
}