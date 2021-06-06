import deps.Deps
import deps.implementation

dependencies {
    implementation(
        Deps.KotlinX.serialization,
        Deps.Ktor.ktor,
        Deps.Ktor.ktor_serialization,
        Deps.Ktor.java_engine
    )
}