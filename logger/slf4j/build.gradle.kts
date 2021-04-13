plugins {
    java
}

group = "com.test"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.slf4j", "slf4j-api", "1.7.30")

    implementation("org.slf4j", "slf4j-log4j12", "1.7.30")

    testImplementation("junit", "junit", "4.12")
}
