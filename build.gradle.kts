plugins {
    kotlin("jvm") version "1.4.10"
}

group = "nl.dricus"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))

    testImplementation(kotlin("test-junit5"))

    testImplementation("org.junit.jupiter:junit-jupiter-api:5.2.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.2.0")
    testRuntimeOnly("org.junit.platform:junit-platform-console:1.2.0")
}

tasks {
    "test"(Test::class) {
        useJUnitPlatform()
    }
}
