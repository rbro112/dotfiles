package network

import io.ktor.client.*
import io.ktor.client.engine.java.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.request.*

fun newHttpClient() = HttpClient(Java) {
    install(JsonFeature) {
        serializer = KotlinxSerializer()
    }
}

suspend inline fun <reified T> get(
    url: String
) = newHttpClient().get<T>(url)