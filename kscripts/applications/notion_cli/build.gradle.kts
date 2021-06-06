import deps.implementation

dependencies {
    implementation(project(":utils:general"))
    implementation(project(":utils:notion"))

    implementation(
        deps.Deps.KotlinX.cli
    )
}