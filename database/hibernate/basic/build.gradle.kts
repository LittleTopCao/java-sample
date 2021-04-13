plugins {
    java
}

group = "com.test"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.h2database", "h2", "1.4.196")
    implementation("org.hibernate", "hibernate-core", "5.4.3.Final")
    implementation("org.slf4j", "slf4j-simple", "1.7.5")

    testImplementation("junit", "junit", "4.12")
}
