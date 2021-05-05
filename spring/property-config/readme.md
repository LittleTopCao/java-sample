# Spring boot 属性配置

Spring Boot 提供了配置属性的方法。

其实就是注入 bean 的属性，不过注入的是基本类型，而且值可以通过多个源进行设置

Spring 本身 也使用这种 方式 配置，例如：
    数据源
        spring.datasource.url
        spring.datasource.username
        spring.datasource.password
    嵌入式服务器
        server.port
    配置日志
        logging.level
        logging.path
        logging.file
        logging.


属性源，属性可以有多个来源
    1. JVM 系统属性
    2. 操作系统环境变量
    3. 命令行参数
    4. 应用属性配置文件：application.properties、application.yml


在属性文件本身使用：${name}

## 定义自己的属性注入

在 Spring 托管的 bean 上使用 @ConfigurationProperties 注解，在 bean 生成时就能够自动注入属性值

注入是根据属性名称，在 @ConfigurationProperties 注解上可以设置 前缀

我们常常专门新建一个类用来注入属性值，就像用接口保存配置一样

对于配置属性还可以添加说明，作为 ide 的提示 或者 生成文档用，需要编写 META-INF/additional-spring-configuration-metadata.json 文件

## 不同的 profile

我们需要根据不同的环境有不同的配置

通过激活不同的 profile 可以实现：选择bean、配置类、配置属性

### 根据 profile 选择 属性文件

1. application-name.properties 或者 application-name.yml 定义不同的 profile
2. 在 application.yml 中通过 --- 分割

属性文件 application.yml 是永远都激活的，配置通用的属性，然后再通过不同的 profile 配置不同的属性值


### 根据 profile 创建 bean

可以选择某个 profile 激活创建 bean，某个 profile 不激活创建 bean

在 @Bean 上添加 @Profile 注解

还可以在 @Configuration 注解的类上添加 @Profile


### 激活 profile

其实就是配置 spring.profiles.active 属性
    1. 可以在属性文件 application.yml 中配置
    2. 可以环境变量，SPRING_PROFILES_ACTIVE
    3. 可以命令行，java -jar app.jar --spring.profiles.active=xxx


# 运行时值注入（旧版，非 spring boot）

## 注入外部的值

1. 解析 properties 文件，声明属性源，然后通过 Environment 代码获得属性
    在类上使用 @PropertySource 注解声明注入的 properties 文件
    在类中注入 Environment 属性
    然后就可以用 Environment 属性 获得 properties 文件中的 值了

2. 解析 properties 文件，使用 占位符 在 java代码 或 xml 文件中
    使用java代码 或 xml 配置 PropertySourcesfPlaceholderConfigurer bean（或者 PropertyPlaceholderConfigurer bean）
    然后在 xml 中直接使用 ${name} 占位符
    或者在 java 代码中使用 @Value("${name}") 注解把值注入到属性中
   
## 使用 Spring 表达式语言

使用 #{} 这种格式，和 ${} 差不多，只不过里边可以进行复杂的 运算 

这里主要讲的是 #{} 中表达式的语法


