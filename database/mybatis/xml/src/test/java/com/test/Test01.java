package com.test;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.sql.Timestamp;

import static java.lang.System.out;

public class Test01 {

    SqlSessionFactory sqlSessionFactory;

    /**
     * 根据 xml 配置文件 新建 SqlSessionFactory
     */
    @Before
    public void before01() throws IOException, SQLException {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);

        //SqlSessionFactory 一旦被创建就应该在应用的运行期间一直存在，没有任何理由丢弃它或重新创建另一个实例。
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);

        //每个线程都应该有它自己的 SqlSession 实例。SqlSession 的实例不是线程安全的，因此是不能被共享的，所以它的最佳的作用域是请求或方法作用域。
        // 这个关闭操作很重要，为了确保每次都能执行关闭操作，你应该把这个关闭操作放到 finally 块中。
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            boolean autoCommit = sqlSession.getConnection().getAutoCommit();
            out.println(autoCommit);
        }
    }

    @Before
    public void before02() {
//        LoggerFactory

    }


    /**
     * 获取 sqlSession
     *      ExecutorType 设置预处理类型
     *          ExecutorType.SIMPLE：该类型的执行器没有特别的行为。它为每个语句的执行创建一个新的预处理语句。
     *          ExecutorType.REUSE：该类型的执行器会复用预处理语句。
     *          ExecutorType.BATCH：该类型的执行器会批量执行所有更新语句，如果 SELECT 在多个更新中间执行，将在必要时将多条更新语句分隔开来，以方便理解。
     *
     *      SqlSession openSession() 默认设置
     *      SqlSession openSession(boolean autoCommit) 自定提交事务
     *      SqlSession openSession(Connection connection) 指定数据库连接
     *      SqlSession openSession(TransactionIsolationLevel level) 事务隔离级别
     *      SqlSession openSession(ExecutorType execType, TransactionIsolationLevel level) 复用预处理语句
     *      SqlSession openSession(ExecutorType execType)
     *      SqlSession openSession(ExecutorType execType, boolean autoCommit)
     *      SqlSession openSession(ExecutorType execType, Connection connection)
     */
    @Test
    public void test01() {
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            Actor teacher = sqlSession.selectOne("com.test.Actor.selectById", 1);
            out.println(teacher);
        }
    }


    /**
     * 执行 定义在 xml 中的语句，不需要有对应的接口，参数可以是原始类型（支持自动装箱或包装类）、JavaBean、POJO 或 Map。
     *      如果你的sql不需要参数，下边的方法每一个都有 不需要参数的 重载方法
     *
     *      <T> T selectOne(String statement, Object parameter) 返回一个对象或null，如果有多个报错
     *      <E> List<E> selectList(String statement, Object parameter) 查找多个对象
     *      <T> Cursor<T> selectCursor(String statement, Object parameter) 游标（Cursor）与列表（List）返回的结果相同，不同的是，游标借助迭代器实现了数据的惰性加载。
     *      <K,V> Map<K,V> selectMap(String statement, Object parameter, String mapKey) 它会将返回对象的其中一个属性作为 key 值，将对象作为 value 值，从而将多个结果集转为 Map 类型值。
     *      int insert(String statement, Object parameter)
     *      int update(String statement, Object parameter)
     *      int delete(String statement, Object parameter)
     *
     *
     * 还有 select 方法的三个高级版本，它们允许你限制返回行数的范围，或是提供自定义结果处理逻辑，通常在数据集非常庞大的情形下使用。
     *      RowBounds 参数会告诉 MyBatis 略过指定数量的记录，并限制返回结果的数量。
     *          RowBounds rowBounds = new RowBounds(offset, limit);
     *      ResultHandler 参数允许自定义每行结果的处理过程。你可以将它添加到 List 中、创建 Map 和 Set，甚至丢弃每个返回值，只保留计算后的统计结果。
     *
     *      <E> List<E> selectList (String statement, Object parameter, RowBounds rowBounds)
     *      <T> Cursor<T> selectCursor(String statement, Object parameter, RowBounds rowBounds)
     *      <K,V> Map<K,V> selectMap(String statement, Object parameter, String mapKey, RowBounds rowbounds)
     *      void select (String statement, Object parameter, ResultHandler<T> handler)
     *      void select (String statement, Object parameter, RowBounds rowBounds, ResultHandler<T> handler)
     *
     * 如果 选择 ExecutorType.BATCH 批量更新，可以用 以下方法 立即 执行 缓存的 语句
     *      List<BatchResult> flushStatements()
     *
     */
    @Test
    public void test02() {
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {

            Actor actor = new Actor("xiao", "ming", new Timestamp(System.currentTimeMillis()));

            //增
            int insert = sqlSession.insert("com.test.ActorMapper.insert", actor);
            sqlSession.commit();
            out.println("插入影响的行数：" + insert);
            out.println("新行的Id：" + actor.actorId);

            //查
            actor = sqlSession.selectOne("com.test.ActorMapper.selectById", actor.actorId);
            sqlSession.commit();
            out.println("查找插入的新行：" + actor);

            //改
            actor.firstName = "x";
            int update = sqlSession.update("com.test.ActorMapper.updateById", actor);
            sqlSession.commit();
            out.println("修改影响的行数：" + update);

            //删
            int delete = sqlSession.delete("com.test.ActorMapper.deleteById", actor.actorId);
            sqlSession.commit();
            out.println("删除影响的行数：" + delete);
        }
    }


    /**
     * 事务控制方法
     *      有四个方法用来控制事务作用域。如果设置了自动提交 和 外部事务管理器，这些方法就没作用了。
     *      使用 jdbc 事务管理器 才有用
     *      void commit()
     *      void commit(boolean force)
     *      void rollback()
     *      void rollback(boolean force)
     */
    @Test
    public void test03() {

    }


    /**
     * 本地缓存
     *      Mybatis 使用到了两种缓存：本地缓存（local cache）和二级缓存（second level cache）。
     *
     *  本地缓存级别 可以 设置
     *  本地缓存在 做出修改、事务提交或回滚，以及关闭 session 时清空。
     *
     *  可以通过设置 localCacheScope=STATEMENT 来只在语句执行时使用缓存。
     *  如果 localCacheScope 被设置为 SESSION，对于某个对象，MyBatis 将返回在本地缓存中唯一对象的引用。
     *      对返回的对象（例如 list）做出的任何修改将会影响本地缓存的内容，进而将会影响到在本次 session 中从缓存返回的值。
     *      因此，不要对 MyBatis 所返回的对象作出更改，以防后患。
     *
     * 可以手动清空
     *      void clearCache()
     */
    @Test
    public void test04() {

    }

    /**
     * 使用 映射器
     *      一个映射器就是与 xml 中 命名空间 和 方法 相匹配的接口
     *
     * 命名空间  与 接口类 同名， 方法同名 ，返回值 类型 匹配
     *
     * 如果传递多个参数
     *
     *
     */
    @Test
    public void test05() {
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {

            ActorMapper mapper = sqlSession.getMapper(ActorMapper.class);

            //增
            Actor actor = mapper.selectById(1);
            out.println(actor);

            //查

            //改

            //删

        }
    }


    /**
     * 使用映射器的参数
     */
    @Test
    public void test06() {

    }
}
