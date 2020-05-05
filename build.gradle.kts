plugins {
    kotlin("jvm") version "1.3.72"
    application
}

group = "nl.marc.dominoes"
version = "1.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))

    testImplementation(kotlin("test-junit"))
}

application {
    mainClassName = "nl.marc.dominoes.MainKt"
}

tasks.jar {
    manifest {
        attributes(mapOf("Main-Class" to "nl.marc.dominoes.MainKt"))
    }
}
