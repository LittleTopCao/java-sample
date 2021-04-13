package com.test;

/**
 * mabatis 的 java api 文档 https://mybatis.org/mybatis-3/zh/java-api.html
 *
 *
 * 1. 目录结构
 *
 * 2。SqlSession
 *      1。SqlSessionFactoryBuilder
 *          5个重载的静态 build 方法，不同方式创建 SqlSessionFactory
 *      2。SqlSessionFactory
 *          6个重载的 openSession 方法，得到不同配置的 SqlSession
 *          无参的 openSession 方法得到 不自动提交、默认链接、默认事务隔离级别、不复用预处理语句 的 SqlSession 实例
 *      3。SqlSession
 *          1。语句执行方法
 *              参数支持 原始类型（支持自动装箱或包装类）、JavaBean、POJO 或 Map
 *              有参数的一套 ，还有执行无参 sql 的一套语句
 *                  <T> T selectOne(String statement, Object parameter)
 *                  <E> List<E> selectList(String statement, Object parameter)
 *                  <T> Cursor<T> selectCursor(String statement, Object parameter)
 *                  <K,V> Map<K,V> selectMap(String statement, Object parameter, String mapKey)
 *                  int insert(String statement, Object parameter)
 *                  int update(String statement, Object parameter)
 *                  int delete(String statement, Object parameter)
 *              还有几个高级 select 方法
 *                  <E> List<E> selectList (String statement, Object parameter, RowBounds rowBounds)
 *                  <T> Cursor<T> selectCursor(String statement, Object parameter, RowBounds rowBounds)
 *                  <K,V> Map<K,V> selectMap(String statement, Object parameter, String mapKey, RowBounds rowbounds)
 *                  void select (String statement, Object parameter, ResultHandler<T> handler)
 *                  void select (String statement, Object parameter, RowBounds rowBounds, ResultHandler<T> handler)
 *          2。立即批量更新方法
 *              List<BatchResult> flushStatements() 立即执行 还未执行的 缓存的 批量 语句
 *          3。事务控制方法
 *              void commit()
 *              void commit(boolean force)
 *              void rollback()
 *              void rollback(boolean force)
 *          4。本地缓存
 *              void clearCache() 清空本地缓存
 *          5。确保 SqlSession 被关闭
 *              void close() 关闭方法
 *          6。使用映射器
 *              <T> T getMapper(Class<T> type) 获取对应映射器实例
 *              方法要求
 *                  方法名
 *                      要和 sql 中的 id 相同
 *                  返回值
 *                      必须匹配，支持 原始类型、Map、POJO 和 JavaBean。多个则支持 数组 或 集合 或 游标（Cursor）
 *                  参数
 *                      如果方法有多个参数，在 sql 中 根据顺序 #{param1}、#{param2} 引用
 *                      可以使用 @Param("paramName")  注解参数，在 sql 中使用 #{paramName} 引用
 *                      RowBounds 参数 来限制查询结果
 *          7。映射器注解
 *              一开始基于 xml ，在 mybatis 3 支持 注解，但是 java 注解表达能力有限
 *              @CacheNamespace 注解类，用来配置缓存，等价于 <cache></cache>
 *              @Property 属性值的占位符，等价于 <property></property>
 *              @CacheNamespaceRef 注解类，引用另一个命名空间的缓存，等价于 <cacheRef></cacheRef>
 *
 *              @ConstructorArgs 注解类，
 *
 *              @Arg
 *
 *              @TypeDiscriminator
 *              @Case
 *              @Results
 *              @Result
 *
 *              @One
 *              @Many
 *              @MapKey
 *
 *              @Options 注解方法，映射语句的属性
 *
 *              @Insert 注解方法， 用来设置 sql 语句
 *              @Update
 *              @Delete
 *              @Select
 *
 *              @InsertProvider 注解方法，构建动态 sql
 *              @UpdateProvider
 *              @DeleteProvider
 *              @SelectProvider
 *
 *              @Param 注解参数，在多个参数的情况下，为了给 参数命名，然后在 sql 中引用，否则就是 param1 param2
 *              @SelectKey 注解方法，为了获取 id ，等价于 <selectKey></selectKey>
 *              @ResultMap 注解方法，引用 xml 中的 <resultMap></resultMap>，相当于属性 resultMap
 *              @ResultType 注解方法
 *              @Flush 注解方法，调用 resultMap
 *
 *
 *
 *          8。映射注解示例
 */
public class JavaAPITest {






















}
