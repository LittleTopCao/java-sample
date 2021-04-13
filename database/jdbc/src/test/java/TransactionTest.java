import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Savepoint;

/**
 * 链接 默认 自动提交, 首先 我们关闭事务
 */
public class TransactionTest {

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
     * 关闭自动提交
     */
    @Test
    public void test01() throws SQLException {
        connection.setAutoCommit(false);
    }

    /**
     * 设置 事务 隔离等级
     */
    @Test
    public void test02() throws SQLException {
        connection.setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);
    }

    /**
     * 提交事务
     */
    @Test
    public void test03() throws SQLException {
        connection.commit();
    }

    /**
     * 回滚事务
     */
    @Test
    public void test04() throws SQLException {
        connection.rollback();
    }

    /**
     * 保存点
     */
    @Test
    public void test05() throws SQLException {
        Savepoint savepoint = connection.setSavepoint();
        Savepoint name = connection.setSavepoint("name");


        //回滚到保存点
        connection.rollback(savepoint);


        //删除保存点
        connection.releaseSavepoint(name);

    }

}
