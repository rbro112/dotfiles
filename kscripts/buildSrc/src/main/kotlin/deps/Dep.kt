package deps

import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.kotlin.dsl.PluginDependenciesSpecScope
import org.gradle.kotlin.dsl.version

/**
 * TODO: Doc
 */
data class Dep(
    val artifact: String,
    val version: String
) {
    override fun toString() = "$artifact:$version"
}

/**
 * TODO: Doc
 */
infix fun String.dep(version: String) = Dep(this, version)

/**
 * TODO: Doc
 */
fun PluginDependenciesSpecScope.pluginDep(
    vararg dependencies: Dep
) = dependencies.forEach {
    id(it.artifact).version(it.version)
}

fun DependencyHandler.implementation(vararg dependencies: Dep) = add(dependencies, "implementation")

fun DependencyHandler.api(vararg dependencies: Dep) = add(dependencies, "api")

fun DependencyHandler.classpath(vararg dependencies: Dep) = add(dependencies, "classpath")

fun DependencyHandler.add(
    dependencies: Array<out Dep>,
    configurationName: String
) = dependencies.forEach {
    add(configurationName, it.toString())
}