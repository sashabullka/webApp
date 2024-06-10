import org.gradle.api.JavaVersion.VERSION_11
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.9.23"
    id("org.jlleitschuh.gradle.ktlint") version "12.1.0"
    id("io.gitlab.arturbosch.detekt") version "1.23.3"
    application
}

buildscript {
    repositories {
        mavenCentral()
        gradlePluginPortal()
    }

    dependencies {
    }
}

val ktlintVersion: String by project

ktlint {
    version.set(ktlintVersion)
}

val http4kVersion: String by project
val http4kConnectVersion: String by project
val junitVersion: String by project
val kotlinVersion: String by project

application {
    mainClass = "ru.yarsu.WebApplicationKt"
}

tasks.withType<JavaExec>() {
    standardInput = System.`in`
}

repositories {
    mavenCentral()
}

apply(plugin = "kotlin")

tasks {
    withType<KotlinCompile> {
        kotlinOptions {
            allWarningsAsErrors = false
            jvmTarget = "11"
            freeCompilerArgs += "-Xjvm-default=all"
        }
    }

    withType<Test> {
        useJUnitPlatform()
    }

    java {
        sourceCompatibility = VERSION_11
        targetCompatibility = VERSION_11
    }
}
val kotestVersion: String by project
dependencies {
    implementation("org.http4k:http4k-client-okhttp:${http4kVersion}")
    implementation("org.http4k:http4k-cloudnative:${http4kVersion}")
    implementation("org.http4k:http4k-core:${http4kVersion}")
    implementation("org.http4k:http4k-format-jackson:${http4kVersion}")
    implementation("org.http4k:http4k-multipart:${http4kVersion}")
    implementation("org.http4k:http4k-server-netty:${http4kVersion}")
    implementation("org.http4k:http4k-template-pebble:${http4kVersion}")
    implementation("org.jetbrains.kotlin:kotlin-stdlib:${kotlinVersion}")
    testImplementation("org.http4k:http4k-testing-approval:${http4kVersion}")
    testImplementation("org.http4k:http4k-testing-hamkrest:${http4kVersion}")
    testImplementation("org.http4k:http4k-testing-kotest:${http4kVersion}")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.10.2")
    testImplementation("org.junit.jupiter:junit-jupiter-engine:5.10.2")
    testImplementation("io.kotest:kotest-assertions-core:${kotestVersion}")
    testImplementation("io.kotest:kotest-runner-junit5:${kotestVersion}")

}

detekt {
    allRules = true
    buildUponDefaultConfig = true
}



