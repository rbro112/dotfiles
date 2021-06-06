package deps

object Deps {

    object Kotlin {
        const val KOTLIN_VERSION = "1.5.10"
        val plugin_jvm = "org.jetbrains.kotlin.jvm" dep KOTLIN_VERSION
        val plugin_gradle = "org.jetbrains.kotlin:kotlin-gradle-plugin" dep KOTLIN_VERSION
    }

    object KotlinX {
        val cli = "org.jetbrains.kotlinx:kotlinx-cli" dep "0.3.2"
        val coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-core" dep "1.5.0"
        val plugin_serialization = "kotlinx-serialization" dep "1.5.0"
        val serialization = "org.jetbrains.kotlinx:kotlinx-serialization-json" dep "1.2.1"
    }

    object Ktor {
        private const val KTOR_VERSION = "1.6.0"
        val ktor = "io.ktor:ktor-client-core" dep KTOR_VERSION
        val ktor_serialization = "io.ktor:ktor-client-serialization" dep KTOR_VERSION
        val java_engine = "io.ktor:ktor-client-java" dep KTOR_VERSION
    }

    object NotionSdk {
        const val NOTION_SDK_VERSION = "1.0.4"
        val notion_sdk = "com.github.notionsdk:notion-sdk-kotlin" dep NOTION_SDK_VERSION
    }

    object Proguard {
        // TODO: Remove
        val gradle = "com.guardsquare:proguard-gradle" dep "7.1.0-beta5"
    }

    object Shadow {
        private const val SHADOW_VERSION = "6.1.0"
        val plugin = "com.github.johnrengelman.shadow" dep SHADOW_VERSION
        val shadow = "gradle.plugin.com.github.jengelman.gradle.plugins:shadow" dep SHADOW_VERSION
    }
}