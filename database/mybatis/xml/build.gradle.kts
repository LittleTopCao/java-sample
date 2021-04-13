plugins {
    java
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.mybatis", "mybatis", "3.5.6")
    runtime("mysql", "mysql-connector-java", "8.0.23")

    implementation("org.slf4j", "slf4j-api", "1.7.30")

    implementation("org.slf4j", "slf4j-simple", "1.7.30")

    testCompile("junit", "junit", "4.12")
}
