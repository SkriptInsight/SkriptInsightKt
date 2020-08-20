plugins {
    kotlin("jvm") version "1.3.72" apply false
}


subprojects {
    apply {
        plugin("org.jetbrains.kotlin.jvm")
    }

    group = "io.github.skriptinsight"
    version = "0.1-SNAPSHOT"

    repositories {
        mavenCentral()
    }
    val implementation by configurations
    val testImplementation by configurations

    dependencies {
        implementation(kotlin("stdlib"))
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.9")
        implementation("com.github.akarnokd:kotlin-flow-extensions:0.0.5")
        testImplementation("org.junit.jupiter:junit-jupiter:5.6.2")
    }

    tasks.withType<Test> {
        useJUnitPlatform()
        testLogging {
            events("passed", "skipped", "failed")
        }
    }

    // config JVM target to 11 for kotlin compilation tasks
    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
        kotlinOptions.apply {
            jvmTarget = "11"
            freeCompilerArgs = freeCompilerArgs + listOf("-Xopt-in=kotlinx.coroutines.FlowPreview")
        }
    }

}
