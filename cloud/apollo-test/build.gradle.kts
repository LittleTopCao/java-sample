plugins {
    id("org.springframework.boot") version "2.4.5"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    java
}

group = "com.test"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")

    implementation("com.ctrip.framework.apollo:apollo-client:1.8.0")

    testImplementation("org.springframework.boot:spring-boot-starter-test")

    testImplementation("junit", "junit", "4.12")
}


tasks.test {
    useJUnit()
}