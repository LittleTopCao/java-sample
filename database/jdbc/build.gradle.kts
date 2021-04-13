plugins {
    java
}

group = "com.test"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("mysql", "mysql-connector-java", "8.0.23")

    testImplementation("junit", "junit", "4.12")
}
