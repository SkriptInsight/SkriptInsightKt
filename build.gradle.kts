plugins {
    kotlin("jvm") version "1.3.72" apply false
    id("com.github.johnrengelman.shadow") version "6.0.0"
    `maven-publish`
}


subprojects {
    apply {
        plugin("org.jetbrains.kotlin.jvm")
        plugin("com.github.johnrengelman.shadow")
        plugin("org.gradle.maven-publish")
    }

    group = "io.github.skriptinsight"
    version = "0.1-SNAPSHOT"

    repositories {
        mavenCentral()
    }
    val shadow by configurations
    val testImplementation by configurations

    dependencies {
        shadow(kotlin("stdlib"))
        testImplementation(kotlin("stdlib"))
        testImplementation("org.junit.jupiter:junit-jupiter:5.6.2")
    }

    tasks.withType<Test> {
        useJUnitPlatform()
        testLogging {
            events("passed", "skipped", "failed")
        }
    }

    publishing {
        publications {
            create("shadow", MavenPublication::class.java) {
                project.shadow.component(this)
            }
        }
    }

    // config JVM target to 11 for kotlin compilation tasks
    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
        kotlinOptions.jvmTarget = "11"
    }

    tasks.withType<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar> {
        classifier = null
    }

}

