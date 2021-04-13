import com.mysql.cj.protocol.Resultset;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.*;

/**
 * 语句 上下文 ，可以多次使用 ，主要用来 执行语句
 */
public class StatementTest {
    Connection connection;

    @Before
    public void before() throws SQLException {
        connection = DriverManager.getConnection(Constant.JDBC_URL, Constant.JDBC_USER, Constant.JDBC_PASSWORD);
    }

    @After
    public void after() throws SQLException {
        connection.close();
    }

    /**
     * 使用 executeUpdate 、executeLargeUpdate 方法 执行 sql 语句
     *      执行 insert update delete 语句 ，返回被影响的 行数
     *      执行 ddl 语句 ， 返回 0
     */
    @Test
    public void test01() throws SQLException {
        try (Statement statement = connection.createStatement()) {

            //返回被影响的行数
            int i = statement.executeUpdate("insert into actor (first_name, last_name) values ('xiao', 'ming')");
            System.out.println(i);

            //可以返回 自动生成的键 ，Statement.RETURN_GENERATED_KEYS 、Statement.NO_GENERATED_KEYS
            // 执行完成 用 getGeneratedKeys 方法获得
            i = statement.executeUpdate("insert into actor (first_name, last_name) values ('xiao', 'ming')", Statement.RETURN_GENERATED_KEYS);
            System.out.println(i);

            //在执行上边的方法后 获得 自动生成的列
            ResultSet generatedKeys = statement.getGeneratedKeys();
            generatedKeys.next();
            System.out.println(generatedKeys.getInt(1));


            //指定自动生成的 列 的 位置
//            statement.executeUpdate("", new int[]{1, 2});

            //指定自动生成的 列 的 名字
//            statement.executeUpdate("", new String[]{});


            //当你返回的 值 超过   Integer.MAX_VALUE 时 ，就使用 这个
//            long delete_from_table = statement.executeLargeUpdate("delete from table");

        }
    }


    /**
     * 使用 executeQuery 方法 执行 select 语句
     *      所有返回 结果集 的语句 都用这个，比如生活 select connenct_id(); 也得用这个
     */
    @Test
    public void test02() throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.executeQuery("select * from table");
        }
    }


    /**
     * 使用 execute 方法 执行 sql 语句
     * 什么语句 返回多个结果呢？ 没遇到过，存储过程？
     * 当你的 语句 返回多个 结果时 使用，返回 true 代表 第一个结果是 ResultSet 对象
     * 然后配合 方法  getResultSet, getUpdateCount, getMoreResults 获取结果
     */
    @Test
    public void test03() throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.execute("select * ");
        }
    }

    /**
     * 批量执行 executeBatch、executeLargeU
     */
    @Test
    public void test04() throws SQLException {
        Statement statement = connection.createStatement();

        //添加 批量执行的 语句，可以是 insert update delete
        statement.addBatch("insert ");
        statement.addBatch("");
        statement.addBatch("");

        //执行 批量更新的语句
        //返回 一个 受影响行数的 数组
        int[] ints = statement.executeBatch();

        //当 有语句 返回的值 大于 Integer.MAX_VALUE 时 ，就使用 这个
        //和 executeLargeUpdate 对应
        long[] longs = statement.executeLargeBatch();

        //清空 批量更新的语句
        statement.clearBatch();
    }

    /**
     * 设置 属性 变量
     */
    @Test
    public void test05() throws SQLException {
        Statement statement = connection.createStatement();

        //设置超时的 时间
        statement.setQueryTimeout(10);
        //设置 当前对象 被 池化，默认情况下 PreparedStatement和CallableStatement 在创建时可池化。
        statement.setPoolable(true);
        //生成的 ResultSet 对象的 最大 行数，多余的将被丢弃
        statement.setMaxRows(10);
        //返回的 ResultSet 中 列的 最大 字节数，限制以下类型：  BINARY，VARBINARY， LONGVARBINARY，CHAR，VARCHAR， NCHAR，NVARCHAR，LONGNVARCHAR和 LONGVARCHAR
        //多出来的字节将被丢弃
        statement.setMaxFieldSize(256);
        //返回的 ResultSet 的默认 行数
        statement.setFetchSize(10);
        //生成的 ResultSet 的行 处理的方向
        statement.setFetchDirection(ResultSet.FETCH_FORWARD);
        //其中转义 处理
        statement.setEscapeProcessing(true);
        //设置 游标 的 名字，以供 在 以后 的 update 和 delete 中使用
        statement.setCursorName("");

        statement.isClosed();
        statement.isCloseOnCompletion();

    }

}
