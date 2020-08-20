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
        kotlinOptions.jvmTarget = "11"
    }

}
