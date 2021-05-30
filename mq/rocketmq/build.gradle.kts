plugins {
    java
}

group = "com.test"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    jcenter()
}

dependencies {

    implementation("org.apache.rocketmq:rocketmq-client:4.3.0")

    testImplementation("junit", "junit", "4.12")
}
