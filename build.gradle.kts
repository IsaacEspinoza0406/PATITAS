// build.gradle.kts

plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.ktor)
    alias(libs.plugins.kotlin.plugin.serialization)
}

group = "com.patitas_web"
version = "0.0.1"

application {
    mainClass = "io.ktor.server.netty.EngineMain"
}

dependencies {
    // Ktor (esto ya lo tenías)
    implementation(libs.ktor.server.content.negotiation)
    implementation(libs.ktor.server.core)
    implementation(libs.ktor.serialization.kotlinx.json)
    implementation(libs.ktor.server.netty)
    implementation(libs.logback.classic)
    implementation(libs.ktor.server.config.yaml)
    testImplementation(libs.ktor.server.test.host)
    testImplementation(libs.kotlin.test.junit)

    // --- LÍNEAS QUE NECESITAS AÑADIR ---

    // 1. Dependencias de Exposed
    implementation("org.jetbrains.exposed:exposed-core:0.41.1")
    implementation("org.jetbrains.exposed:exposed-dao:0.41.1")
    implementation("org.jetbrains.exposed:exposed-jdbc:0.41.1")

    // 2. Dependencia para manejar fechas y horas con Exposed
    implementation("org.jetbrains.exposed:exposed-java-time:0.41.1")

    // 3. Driver de PostgreSQL
    implementation("org.postgresql:postgresql:42.6.0")

    // --- FIN DE LAS LÍNEAS A AÑADIR ---
}