https://github.com/ctripcorp/apollo

微服务 配置框架

## 设计&架构

它本身的实现就是一个微服务架构，采用 Eureka 注册中心，很方便实现多实例的高可用
然后再配上 域名 的动态解析，妥妥的 7 * 24

分为以下几个服务
    1. config service，提供配置的读取和推送功能，服务对象是 apollo 客户端
    2. admin service，提供配置的修改、发布等功能，服务对象是 Apollo Portal（管理界面）
    3. Meta Server 构建在 Eureka 之上，config service 和 admin service 都将自己注册到这里，
        client 和 Portal 都通过域名访问 meta server 来获取 config service 和 admin servcie 的服务地址
    4. Portal service，提供管理页面

Config Service、Eureka和Meta Server三个逻辑角色部署在同一个JVM进程中

Meta Server 主要是封装 Eureka 的服务发现细节，对 client 和 Portal 提供一个简单的 http 接口，来获取 servcie 的地址列表

数据库设计：
    app 对应多个 appNamespace，appNamespace 对应多个 namespace
    app 对应多个 cluster（集群）
    cluster 对应多个 namespace
    namespace 对应多个 item
    一个 item 就是一个 key-value 配置

和 Spring 集成
    首先Spring属性配置 有组装多个属性源的概念
        其中 ApplicationContext 有多个 Environment 环境
        每个 Environment 又有多个 PropertySource 属性源
    apollo 在启动时从远端获取配置
    然后组装成一个 PropertySource 属性源 提供给 Spring
    Spring 使用时 就像在 使用本地 application.yml 中的配置一样

四个维度管理 key-value 格式的配置
    1. 应用
    2. 环境
    3. 集群
    4. 命名空间

环境这个需要说明一下，不同环境是完全隔离开的，我们可以理解为多个 apollo 服务
    虽然我们在管理页面上可以看到多个环境，那是因为 apollo 允许多个 环境共用一个 Portal service
    不同环境的 config service 是不同的，他们存储数据的 mysql 数据库也不同
    那么 meta server 是不是一个呢？

这个 namespace 命名空间的 概念多少有点混淆
    命名空间就可以理解为一个单独的文件，它里边放着多个 key-value 配置，从上边数据库结构也能看出来
    根据数据库结构我们理解：
        一个应用 可以对应 多个配置文件
        一个应用 对应多个集群 ，一个集群 再 对应多个 配置文件
   
## Quick Start Docker 方式

1. git clone https://github.com/ctripcorp/apollo.git
2. 进入到项目的 scripts/docker-quick-start
3. docker-compose up 启动项目

Eureka http://localhost:8080/
Apollo 管理页面 http://localhost:8070
    用户名 apollo 密码 admin

使用 docker 后，因为 config service 注册到 meta server 的地址是 内部地址，所以我们不能使用 meta server 服务发现
我们只能直接使用 config service 地址，下面有些，config service 的地址是 http://localhost:8080

## Quick Start 手动方式

文档中有一个 quick start 项目，可以快速的在本地启动一个
1. Clone 这个 start 项目，需要 java 和 mysql 支持
2. 初始化数据库, 两个库 ApolloPortalDB 和 ApolloConfigDB
3. 修改 demo.sh 中的数据库连接信息
4. 使用 ./demo.sh start 开始服务
5. 访问 http://localhost:8070/ 管理页面，用户名 apollo 密码 admin
6. 占用 8070 8080 8090 三个端口

## 分布式部署

需要 mysql 

支持以下环境
    1. DEV 开发环境
    2. FAT 测试环境，相当于alpha环境(功能测试)
    3. UAT 集成环境，相当于beta环境（回归测试）
    4. PRO 生产环境

quick start 不支持多环境，因为只有一个 config service 实例

1. Portal 部署在生产环境的机房，通过它管理 FAT UAT PRO 环境的 配置
2. meta server、config service、admin service 在每个环境单独部署，使用独立的数据库
3. meta server、config service、admin service 在生产环境中部署在两个机房，实现双活
4. Meta Server和Config Service部署在同一个JVM进程内，Admin Service部署在同一台服务器的另一个JVM进程内


## 客户端配置

AppId
    1. Java系统属性，-Dapp.id=YOUR-APP-ID
    2. 操作系统环境变量，APP_ID=YOUR-APP-ID
    3. Spring Boot的配置文件 application.properties 中配置，app.id=YOUR-APP-ID
    4. META-INF/app.properties 配置，app.id=YOUR-APP-ID

Meta Server 地址
    1. Java属性，-Dapollo.meta=http://config-service-url
    2. Spring Boot的配置文件，apollo.meta=http://config-service-url
    3. 操作系统环境变量，APOLLO_META
    4. 通过 server.properties 配置文件
    5. 通过 app.properties 配置文件
    6. 还可以直接配置所有环境的地址，然后根据环境激活使用哪一个
        1. 操作系统环境变量配置，例如 DEV_META=http://config-service-url 
        2. 通过 apollo-env.properties 文件配置
            dev.meta=http://1.1.1.1:8080
            fat.meta=http://apollo.fat.xxx.com
            uat.meta=http://apollo.uat.xxx.com
            pro.meta=http://apollo.xxx.com

meta server 推荐使用域名 SLB （Software Load Balancer）做动态负载均衡

还可以自己编写定位 meta server 的逻辑，它采用 Java SPI 发现模式

跳过meta server 服务发现，直接连接 config service
    原因：
        1. Config Service部署在公有云上，注册到Meta Server的是内网地址，本地开发环境无法直接连接 
            如果通过公网 SLB 对外暴露 Config Service的话，记得要设置 IP 白名单，避免数据泄露
        2. Config Service部署在docker环境中，注册到Meta Server的是docker内网地址，本地开发环境无法直接连接
        3. Config Service部署在kubernetes中，希望使用kubernetes自带的服务发现能力（Service）
    配置，直接指定 config service 地址：
        1. java系统属性，-Dapollo.configService=http://config-service-url:port
        2. 操作系统环境变量，APOLLO_CONFIGSERVICE
        3. 通过server.properties配置文件

Environment 环境
    1. java系统属性，-Denv=YOUR-ENVIRONMENT
    2. 操作系统环境变量
    3. 配置文件 server.properties

cluster 集群
    1. Java系统属性，-Dapollo.cluster=SomeCluster
    2. Spring 配置文件 application.properties，apollo.cluster=SomeCluster
    3. java系统属性，-Didc=xxx
    4. 操作系统环境变量
    5. 通过server.properties配置文件

配置访问密钥
    1. Java系统属性，-Dapollo.accesskey.secret=1cf998c4e2ad4704b45a98a509d15719
    2. Spring 配置文件 application.properties，apollo.accesskey.secret=1cf998c4e2ad4704b45a98a509d15719
    4. 操作系统环境变量，APOLLO_ACCESSKEY_SECRET
    5. 通过app.properties配置文件

## 使用方式

1. api 方式
    通过 ConfigService 获得一个代表命名空间的 Config 对象，再通过 Config 对象获取 key 对应的 value
    Config 对象还支持 设置 监听器，当 命名空间 的配置有变化时 调用

2. Spring 集成使用方式
    1. 配置
       1. xml 方式配置
       2. java 方式配置
       3. Spring Boot集成方式（推荐）
           在 application.properties 中放入 apollo.bootstrap.enabled = true
           配置多个 命名空间（可选）apollo.bootstrap.namespaces = application,FX.apollo,application.yml
           如果日志的配置也放到 apollo 中，需要把初始化提前：apollo.bootstrap.eagerLoad.enabled=true
    2. Spring Placeholder 的方式使用
       1. xml 使用方式
```xml
<bean class="com.ctrip.framework.apollo.spring.TestXmlBean">
    <property name="timeout" value="${timeout:100}"/>
    <property name="batch" value="${batch:200}"/>
</bean>
```
       2. Java Config使用方式
```java
public class TestJavaConfigBean {
    @Value("${timeout:100}")
    private int timeout;
}
```
       3. ConfigurationProperties 使用方式
```java
@ConfigurationProperties(prefix = "redis.cache")
public class SampleRedisConfig {
    private int expireSeconds;
    private int commandTimeout;
}
```
    3. Spring Annotation 支持
       1. @ApolloConfig 用来自动注入Config对象
```java
public class TestApolloAnnotationBean {
    @ApolloConfig
    private Config config; //inject config for namespace application
    @ApolloConfig("application")
    private Config anotherConfig; //inject config for namespace application
}
```
       2. @ApolloConfigChangeListener 用来自动注册ConfigChangeListener
```java
public class TestApolloAnnotationBean {
  @ApolloConfigChangeListener
  private void someOnChange(ConfigChangeEvent changeEvent) {
    if (changeEvent.isChanged("batch")) {
      batch = config.getIntProperty("batch", 100);
    }
  }
}
```
       3. @ApolloJsonValue 用来把配置的json字符串自动注入为对象
```java
public class TestApolloAnnotationBean {
    @ApolloJsonValue("${jsonBeanProperty:[]}")
    private List<JsonBean> anotherJsonBeans;
}
```


3. 本地开发模式
    支持不连接 apollo 服务器
    1. 把环境改为 Local，例如： 修改/opt/settings/server.properties（Mac/Linux）或C:\opt\settings\server.properties（Windows）文件，设置env为Local：
    2. 把配置文件放在 Mac/Linux: /opt/data/{appId}/config-cache  Windows: C:\opt\data\{appId}\config-cache
        * 推荐的方式是先在普通模式下使用Apollo，这样Apollo会自动创建该目录并在目录下生成配置文件。
    3.  本地配置文件需要按照一定的文件名格式放置于本地配置目录下，文件名格式如下： {appId}+{cluster}+{namespace}.properties
        例如：appId+default+application.properties
