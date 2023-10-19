import kotlinx.benchmark.gradle.BenchmarksExtension
import org.jetbrains.kotlin.allopen.gradle.AllOpenExtension
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

repositories {
    mavenCentral()
    maven(url = "https://maven.pkg.jetbrains.space/kotlin/p/kotlin/dev/")
}

plugins {
    kotlin("jvm") version "2.0.0-dev-5520"
    id("org.jetbrains.kotlinx.benchmark") version "0.4.9"
    id("org.jetbrains.kotlin.plugin.allopen") version "1.9.10"
}

group = "org.stitzl"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-benchmark-runtime:0.4.9")
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
            warmups = 5 // number of warmup iterations
            iterations = 10 // number of iterations
            iterationTime = 10 // time in seconds per iteration
            advanced("jvmForks", 5)
        }
    }
    targets {
        register("main")
    }
}
