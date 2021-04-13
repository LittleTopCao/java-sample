## 类型对应关系

Java 类型 和 JDBC 类型 采用 类型处理器 来转换，默认的类型处理器
[也就是Java类型 和 数据库类型对应关系](https://mybatis.org/mybatis-3/zh/configuration.html#typeHandlers)
你还可以定义自己的 类型处理器 要注意 MyBatis 不会通过检测数据库元信息来决定使用哪种类型，所以你必须在参数和结果映射中指明字段是 VARCHAR 类型， 以使其能够绑定到正确的类型处理器上。这是因为 MyBatis
直到语句被执行时才清楚数据类型。