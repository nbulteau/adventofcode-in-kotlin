plugins {
    kotlin("jvm") version "1.9.20"
    application
}

group = "me.nicolas.adventofcode"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("tools.aqua:z3-turnkey:4.12.2.1") // use Z3 to solve 2023 day24 part two

    testImplementation(kotlin("test-junit5"))
    testImplementation ("org.assertj:assertj-core:3.8.0")
}

tasks.test {
    useJUnitPlatform()
}

