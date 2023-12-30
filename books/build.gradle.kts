
val ktor_version: String by project
val kotlin_version: String by project
val logback_version: String by project

plugins {
    kotlin("jvm") version "1.9.22"
    id("io.ktor.plugin") version "2.3.7"
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
    implementation("io.ktor:ktor-server-content-negotiation-jvm")
    implementation("io.ktor:ktor-server-core-jvm")
    implementation("io.ktor:ktor-serialization-gson-jvm")
    implementation("io.ktor:ktor-server-call-logging-jvm")
    implementation("io.ktor:ktor-server-call-id-jvm")
    implementation("io.ktor:ktor-server-cors-jvm")
    implementation("io.ktor:ktor-server-host-common-jvm")
    implementation("io.ktor:ktor-server-jetty-jvm")
    implementation("ch.qos.logback:logback-classic:$logback_version")
    implementation("io.ktor:ktor-server-config-yaml:2.3.7")
    testImplementation("io.ktor:ktor-server-tests-jvm")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlin_version")

    //

    implementation("io.ktor:ktor-client-core:1.6.7")
    implementation("io.ktor:ktor-client-cio:1.6.7")
    implementation("com.github.jknack:handlebars:4.3.1")
}

tasks {

    val distTar by getting(Tar::class) {

        archiveBaseName.set("app")
        archiveVersion.set("")

        // Includi la cartella 'ws' al tar
        into("app/bin/ws") {
            from("ws")
        }
    }

    register("deploy") {
        group = "Custom"
        dependsOn("distTar")

        doLast { deploy() }
    }
}

fun deploy() {
    // Il tuo codice di distribuzione qui
}
