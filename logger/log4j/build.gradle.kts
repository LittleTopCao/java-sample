plugins {
    java
}

group = "com.test"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.apache.logging.log4j", "log4j-api", "2.14.1")
    implementation("org.apache.logging.log4j", "log4j-core", "2.14.1")

    testImplementation("junit", "junit", "4.12")
}
