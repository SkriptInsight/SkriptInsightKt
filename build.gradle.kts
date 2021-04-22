plugins {
    kotlin("multiplatform") version "1.4.21"
    //id("com.github.johnrengelman.shadow") version "6.0.0"
    `maven-publish`
}

group = "io.github.skriptinsight"
version = "0.1-SNAPSHOT"

repositories {
    mavenCentral()
}

repositories {
    mavenCentral()
}

kotlin {
    jvm {
        compilations.all {
            kotlinOptions.jvmTarget = "11"
        }
    }
    js(BOTH) {
        nodejs {
        }
    }
    sourceSets {
        val commonMain by getting
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }
        val jvmMain by getting
        val jvmTest by getting
        val jsMain by getting
        val jsTest by getting
    }
}
