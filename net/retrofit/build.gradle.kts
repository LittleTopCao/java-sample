plugins {
    java
}

group = "com.test"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    // define a BOM and its version
    implementation(platform("com.squareup.okhttp3:okhttp-bom:4.9.0"))//和 spring 类似，使用依赖传递 来 规范版本

    // define any required OkHttp artifacts without version ， 不带版本了
    implementation("com.squareup.okhttp3:okhttp") //主程序
    implementation("com.squareup.okhttp3:logging-interceptor") //日志
    testImplementation("com.squareup.okhttp3:mockwebserver") //作为一个服务器， 用来测试库

    implementation("com.squareup.moshi:moshi:1.11.0") //Moshi 库 也是 square 开发的，一个 json 库，能利用 okhttp 的缓冲区

    implementation("com.squareup.retrofit2:retrofit:2.9.0") // square 开发的，使用 注解 来 声明 网络结构
    implementation("com.squareup.retrofit2:converter-moshi:2.9.0") // 和 retrofit 配合使用的转换器，指明了 如何将  响应的 body（json 格式） 转换为 对象
    implementation("com.squareup.retrofit2:adapter-rxjava2:2.9.0") // 和 retrofit 配合使用的转换器，可以把 ok http 的 call 转换为你要 返回的类型


    testCompile("junit", "junit", "4.12")
}
