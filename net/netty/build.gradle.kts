plugins {
    java
}

group = "com.test"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.netty:netty-all:4.1.10.Final")

    testCompile("junit", "junit", "4.12")
}
