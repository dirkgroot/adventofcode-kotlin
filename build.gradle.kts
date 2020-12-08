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
}
