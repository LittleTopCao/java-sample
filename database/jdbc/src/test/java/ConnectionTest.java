import com.mysql.cj.jdbc.MysqlConnectionPoolDataSource;
import com.mysql.cj.jdbc.MysqlDataSource;
import com.mysql.cj.jdbc.MysqlXADataSource;
import com.mysql.cj.protocol.Resultset;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.sql.DataSource;
import javax.sql.PooledConnection;
import java.sql.*;

import static java.lang.System.out;

/**
 * Connection 代表 数据库的链接
 *
 *
 */
public class ConnectionTest {
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
     * 创建 sql 语句 执行 上下文
     */
    @Test
    public void test01() throws SQLException {
        Statement statement = connection.createStatement();
        Statement statement1 = connection.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
        Statement statement2 = connection.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY, ResultSet.CLOSE_CURSORS_AT_COMMIT);


    }


    /**
     * 创建预编译  sql 语句 执行 上下文
     */
    @Test
    public void test02() throws SQLException {

        PreparedStatement preparedStatement = connection.prepareStatement("select * from table where id = ?");

    }


    @Test
    public void test03() {

    }


    @Test
    public void test04() {

    }


    @Test
    public void test05() {

    }


}
