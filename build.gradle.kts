plugins {
    kotlin("jvm") version "1.5.21"
}

group = "nl.dricus"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation("org.jetbrains.kotlinx:kotlinx-collections-immutable:0.3.4")

    testImplementation(kotlin("test-junit5"))

    testImplementation("org.junit.jupiter:junit-jupiter-api:5.7.2")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.7.2")
    testRuntimeOnly("org.junit.platform:junit-platform-console:1.7.2")
}

tasks {
    "test"(Test::class) {
        useJUnitPlatform()
    }
}
