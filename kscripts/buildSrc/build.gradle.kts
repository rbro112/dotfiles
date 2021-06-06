plugins {
    `kotlin-dsl`
}

repositories {
    mavenCentral()
    gradlePluginPortal()
}

dependencies {
    // Cannot use Deps.kt as this build is responsible for compiling that class!
    implementation("com.github.jengelman.gradle.plugins:shadow:6.1.0")
    implementation("com.guardsquare:proguard-gradle:7.1.0-beta5")
}
