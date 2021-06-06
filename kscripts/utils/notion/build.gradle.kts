import deps.Deps
import deps.implementation


apply(plugin = Deps.KotlinX.plugin_serialization.artifact)

// TODO
dependencies {
    implementation(project(":utils:general"))

    implementation(
        Deps.KotlinX.serialization,
        Deps.NotionSdk.notion_sdk
    )
}