
[Simple Logging Facade for Java (SLF4J)](http://www.slf4j.org/)

它只是对上提供接口

它并未提供日志的实现，日志的实现可选 commons logging，log4j，java.util.logging 

问题的关键是，当你引用别的类库时，它们也要打印日志，如果 大家 依赖的 日志 框架不一样 就要 维护多个 日志框架，但是如果大家都依赖 slf4j，就不需要 引入多个了，而且你还能自己选择 底层实现

## 配置

1. 首先引入 slf4j-api
2. 然后 绑定 实现
    1. 引入 slf4j-log4j 来绑定 log4j， 还需要 log4j 的实现，这个 只是针对 log4j 的第一版
    2. 引入 slf4j-jdk ，使用 jdk 的 logging 打印
    3. 引入 slf4j-nop ， 不打印 任何 日志
    4. 引入 slf4j-simple ，将 日志 打印到 System.err 
    5. 引入 slf4j-jcl 来绑定 commons-logging
    6. 引入 logback-classic 来绑定 logback， 还需要 logback 的实现
    
如果与 log4j2 绑定，需要引入 org.apache.logging.log4j: log4j-slf4j-impl 包 

spring boot 选择的是 slf4j + logback 

## 配置

配置 要看具体的 底层实现

