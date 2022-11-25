import kotlinx.benchmark.gradle.BenchmarksExtension
import org.jetbrains.kotlin.allopen.gradle.AllOpenExtension
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.7.21"
    id("org.jetbrains.kotlinx.benchmark") version "0.4.4"
    id("org.jetbrains.kotlin.plugin.allopen") version "1.7.21"
}

group = "org.stitzl"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-benchmark-runtime:0.4.4")
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "17"
}

configure<AllOpenExtension> {
    annotation("org.openjdk.jmh.annotations.State")
}

configure<BenchmarksExtension> {
    configurations {
        named("main") { // main configuration is created automatically, but you can change its defaults
            warmups = 20 // number of warmup iterations
            iterations = 10 // number of iterations
            iterationTime = 10 // time in seconds per iteration
        }
    }
    targets {
        register("main")
    }
}