
[有官方中文文档](https://mybatis.org/mybatis-3/zh/getting-started.html)


它是一个 靠 xml 配置 来 简化jdbc  设置参数 和 结果赋值 样板代码 的 一个 库

把 sql 写到 xml 中，一个 sql 对应 一个 dao 的方法， jdbc 的 参数 和 结果 分别对应 dao 方法的 参数 和 返回值

整个方法体 其实就是 
    1。 新建 PreparedStatement 
    2。通过 setXXX 方法设置参数 
    3。执行 SQL  获取 ResultSet
    4。通过 getXXX 解析 ResultSet 
    5。新建 模型 对象 并 返回

mybatis 把这一切变成了 xml 配置




概要：
    1。全局配置（配置 SqlSessionFactory 对象）两种方式
        1。xml 文件
        2。java 代码
    2。SQL语句配置
        1。只 配置 xml
        2。新建 xml 相应的 java 接口（mapper）
        3。在 mapper 方法上 使用 注解 配置 
    3。对象 与 生命周期
        1。SqlSessionFactory 对象，全局共享
        2。SqlSession 对象，不是线程安全，每次 http 请求 一个，需要关闭
        3。mapper 对象，与 sqlSession 的作用域相同， 但是 最好 一个方法 一个，不需要关闭


日志配置
    它默认按 SLF4J 、Apache Commons Logging 、Log4j 2 、Log4j 、JDK logging 这个顺序查找日志的实现
        如果找不到就不打印日志，还可以使用在 setting 中配置特定的日志实现
    日志的配置 其实决定于你使用 那个 日志实现
    例如使用 log4j 时，可以这么配置日志
```properties

# 以下才是 MyBatis 日志配置, TRACE 级别会打印 结果，DEBUG 级别只打印SQL语句
# 配置某个 xml 或 映射器 的日志
log4j.logger.org.mybatis.example.BlogMapper=TRACE
# 配置某个 方法 的日志
log4j.logger.org.mybatis.example.BlogMapper.selectBlog=TRACE
# 配置某个包的 日志
log4j.logger.org.mybatis.example=TRACE

# 以下是 log4j 日志框架的配置
# 全局日志配置
log4j.rootLogger=ERROR, stdout
# 控制台输出
log4j.appender.stdout=org.apache.log4j.ConsoleAppender
log4j.appender.stdout.layout=org.apache.log4j.PatternLayout
log4j.appender.stdout.layout.ConversionPattern=%5p [%t] - %m%n
```
