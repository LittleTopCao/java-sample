plugins {
    java
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.alibaba:fastjson:1.2.73")

    testCompile("junit:junit:4.12")
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}