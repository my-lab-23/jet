import java.io.BufferedReader
import java.io.InputStreamReader

plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.ktor)
    alias(libs.plugins.kotlin.plugin.serialization)
}

group = "com.example"
version = "0.0.1"

application {
    mainClass.set("io.ktor.server.netty.EngineMain")

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(libs.ktor.server.content.negotiation)
    implementation(libs.ktor.server.core)
    implementation(libs.ktor.serialization.kotlinx.json)
    implementation(libs.ktor.server.call.logging)
    implementation(libs.ktor.server.call.id)
    implementation(libs.ktor.server.cors)
    implementation(libs.ktor.server.host.common)
    implementation(libs.ktor.server.netty)
    implementation(libs.logback.classic)
    implementation(libs.ktor.server.config.yaml)
    implementation("io.ktor:ktor-serialization-gson-jvm")
    implementation("io.ktor:ktor-client-core-jvm")
    implementation("io.ktor:ktor-client-cio-jvm")
    testImplementation(libs.ktor.server.test.host)
    testImplementation(libs.kotlin.test.junit)

    //

    implementation("com.github.kittinunf.fuel:fuel:2.3.1")
    implementation("com.github.kittinunf.fuel:fuel-coroutines:2.3.1")
}

//

tasks {

    register("deploy") {

        group = "Custom"
        dependsOn("distTar")

        doLast { deploy() }
    }
}

fun deploy() {

    val cmd = "/home/ema/Scrivania/vue/kv/vuea/ktor-sample/ansible/run.sh"

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
