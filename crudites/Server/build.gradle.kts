import java.io.BufferedReader
import java.io.InputStreamReader

val ktor_version: String by project
val kotlin_version: String by project
val logback_version: String by project

val postgres_version: String by project
val h2_version: String by project
val exposed_version: String by project

plugins {
    kotlin("jvm") version "1.9.0"
    id("io.ktor.plugin") version "2.3.3"
    id("org.jetbrains.kotlin.plugin.serialization") version "1.9.0"
}

group = "com.example"
version = "0.0.1"

application {
    mainClass.set("io.ktor.server.jetty.EngineMain")

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.ktor:ktor-server-core-jvm")
    implementation("io.ktor:ktor-serialization-kotlinx-json-jvm")
    implementation("io.ktor:ktor-server-content-negotiation-jvm")
    implementation("org.postgresql:postgresql:$postgres_version")
    implementation("org.jetbrains.exposed:exposed-core:$exposed_version")
    implementation("org.jetbrains.exposed:exposed-jdbc:$exposed_version")
    implementation("org.jetbrains.exposed:exposed-dao:$exposed_version")
    implementation("io.ktor:ktor-server-call-logging-jvm")
    implementation("io.ktor:ktor-server-cors-jvm")
    implementation("io.ktor:ktor-server-auth-jvm")
    implementation("io.ktor:ktor-server-jetty-jvm")
    implementation("ch.qos.logback:logback-classic:$logback_version")
    implementation("io.ktor:ktor-server-config-yaml:2.3.3")
    implementation("dev.forst", "ktor-api-key", "2.2.4")
    testImplementation("io.ktor:ktor-server-tests-jvm")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlin_version")
}

//

kotlin { jvmToolchain(11) }

//

tasks {

    register("deploy") {

        group = "Custom"
        dependsOn("distTar")

        doLast { deploy() }
    }
}

fun deploy() {

    val cmd = "./run.sh"

    // Specifica il percorso della cartella in cui il processo dovrebbe essere eseguito
    val workingDirectory = File("/home/ema/desperado/crudites/Ansible/")

    val processBuilder = ProcessBuilder(cmd)
        .directory(workingDirectory) // Imposta la cartella di lavoro del processo

    processBuilder.redirectErrorStream(true)

    val process = processBuilder.start()

    val inputStream = process.inputStream
    val reader = BufferedReader(InputStreamReader(inputStream))

    var line: String?
    while (process.isAlive) {
        line = reader.readLine()
        if (line != null) {
            println(line)
        }
    }

    val exitCode = process.waitFor()

    println("Process exited with code: $exitCode")
}
