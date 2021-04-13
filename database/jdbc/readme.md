## 资料 

[官方文档](https://www.oracle.com/java/technologies/javase/javase-tech-database.html)

包含在两个包中
* java.sql
* javax.sql

[文档基础教程](https://docs.oracle.com/javase/tutorial/jdbc/basics/index.html)
1. 入门指南
2. 使用 JDBC 处理 SQL 语句
3. 使用 RowSet 对象
4. 使用高级数据类型
5. 使用存储过程
6. JDBC 与 Swing 集成

没想到这个还挺复杂，并不是 增删改查 那么简单

## 总结

1. 接口 与 驱动 
   jdbc 就是一个接口集合，咱们面向接口编程
   不同数据库厂商 提供 自己的 接口实现，叫做驱动
   至于连接的实现 对 咱们完全透明，不管是 tcp 链接， Unix domain socket ，还是 本地 文件（sqlite），咱们不用关心
   
2. DriverManager 类，用来注册、管理驱动，获得链接。在 jdbc 4.0 以后 可以用 Java SPI 机制自动注册驱动
   
3. CommonDataSource 接口，代表一个 数据源，他有三种子接口
   DataSource 获得基本的链接
   ConnectionPoolDataSource 获得池化的链接
   XADataSource 获得分布式事务的链接

4. Connection 接口，代表一个到数据库的链接，需要关闭


5. Statement 接口，代表一个SQL语句执行的上下文，可以重用，但是同时只能打开一个 ResultSet 接口，需要先关闭上一次的 ResultSet， 然后在获得下一个。需要关闭

6. PreparedStatement 接口，是 Statement 的子接口，用来预编译 SQL，提供占位符 功能

7. CallableStatement 接口，是 PreparedStatement 的子接口，用来执行 存储过程

8. ResultSet 接口，代表结果集合，查询语句的结果是一个关系（表），需要关闭
   他有三个特性
        类型
            TYPE_FORWARD_ONLY 光标只能向前
            TYPE_SCROLL_INSENSITIVE 光标可以向前和向后，并且能定位，当数据库变化时 ResultSet 内容不变
            TYPE_SCROLL_SENSITIVE 光标可以向前和向后，并且能定位，当数据库变化时 ResultSet 也跟着改变
        并发
            CONCUR_READ_ONLY 结果集是只读的
            CONCUR_UPDATETABLE 可以通过更改结果集来更改 数据库
        可保持性
            HOLD_CURSORS_OVER_COMMI 在进行事务提交 或 执行查询获得下一个 ResultSet 时 不  关闭 ResultSet
            CLOSE_CURSORS_AT_COMMIT 在进行事务提交 或 执行查询获得下一个 ResultSet 时 自动  关闭  当前 ResultSet

9. 异常，所有异常继承自 SQLException
    
    
10. SQL类型
    java.sql.Types



## 知识点

为什么要设计 Statement 这个接口，直接用 Connection 接口执行语句不好吗？
    是因为 语句执行 并不是一个 瞬时的事，它在 执行前 和 执行后 都与 数据库有联系
    第一点 PreparedStatement 预编译语句，在 新建 时 就会 把 语句 发送 到 数据库 进行编译，此时可以重用，所以 我们需要这个上下文
    第二点 ResultSet 结果集，它并不是 一次 性 返回 给 应用，在 ResultSet 很大时 它是在 执行 next() 才返回数据的，所以 我们需要这个 上下文