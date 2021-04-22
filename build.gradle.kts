import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.4.32"
    id("com.github.johnrengelman.shadow") version "6.1.0"
    `maven-publish`
}

allprojects {
    group = "io.github.skriptinsight"
    version = "0.1-SNAPSHOT"
}

subprojects {
    apply {
        plugin("org.jetbrains.kotlin.jvm")
        plugin("com.github.johnrengelman.shadow")
        plugin("org.gradle.maven-publish")
    }

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

    // config JVM target to 11 for kotlin compilation tasks
    tasks.withType<KotlinCompile>().configureEach {
        kotlinOptions.jvmTarget = "11"
    }

    tasks.withType<ShadowJar> {
        archiveClassifier.set(null as? String?)
    }

    publishing {
        publications {
            create("shadow", MavenPublication::class.java) {
                project.shadow.component(this)
            }
        }
    }
}

repositories {
    mavenCentral()
}

tasks.withType<Test> {
    outputs.upToDateWhen { false }
}

tasks.withType<ShadowJar> {
    archiveClassifier.set(null as? String?)

    relocate("kotlin", "${project.group}.depends.kotlin")
    relocate("org.intellij", "${project.group}.depends.intellij")
    relocate("org.jetbrains", "${project.group}.depends.jetbrains")
}

dependencies {
    implementation(kotlin("stdlib"))
    subprojects.forEach {
        implementation(it)
    }
}
