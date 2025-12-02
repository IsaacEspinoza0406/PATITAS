plugins {
    kotlin("jvm") version "2.0.20"
    id("io.ktor.plugin") version "2.3.12"
    id("com.gradleup.shadow") version "8.3.8"
}

group = "com.patitas_web"
version = "0.0.1"

application {
    mainClass.set("com.patitas_web.ApplicationKt")
    
    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.ktor:ktor-server-core-jvm")
    implementation("io.ktor:ktor-server-netty-jvm")
    implementation("io.ktor:ktor-server-content-negotiation-jvm")
    implementation("io.ktor:ktor-serialization-kotlinx-json-jvm")
    implementation("io.ktor:ktor-server-cors-jvm")
    implementation("io.ktor:ktor-server-auth-jvm")
    implementation("io.ktor:ktor-server-auth-jwt-jvm")
    implementation("ch.qos.logback:logback-classic:1.4.14")
    implementation("org.jetbrains.exposed:exposed-core:0.41.1")
    implementation("org.jetbrains.exposed:exposed-jdbc:0.41.1")
    implementation("com.h2database:h2:2.2.224")
    implementation("org.postgresql:postgresql:42.6.0")
    implementation("com.zaxxer:HikariCP:5.0.1")
    implementation("org.mindrot:jbcrypt:0.4")
    testImplementation("io.ktor:ktor-server-tests-jvm")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit")
}

tasks.named<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar>("shadowJar") {
    archiveBaseName.set("app_chida")
    archiveVersion.set("1.0.0")
    mergeServiceFiles()
    manifest {
        attributes(mapOf("Main-Class" to "com.patitas_web.ApplicationKt"))
    }
}
