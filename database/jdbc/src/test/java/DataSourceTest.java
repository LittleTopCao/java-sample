import com.mysql.cj.jdbc.MysqlConnectionPoolDataSource;
import com.mysql.cj.jdbc.MysqlDataSource;
import org.junit.Test;

import javax.sql.PooledConnection;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static java.lang.System.out;

/**
 * 使用 DataSource 获得 链接
 *
 * jdbc 接口
 *      DataSource 代表数据源
 *      ConnectionPoolDataSource 代表链接池的数据源
 *      XADataSource 代表分布式事务的数据源
 *
 * mysql 驱动中 分别实现了
 *      MysqlDataSource 实现 DataSource 接口，我们可以用它，但是它和 DriverManager 并没有什么区别，只不过更符合语义
 *      MysqlConnectionPoolDataSource 它并没有实现链接池的效果，只是有这么一个类
 *      MysqlXADataSource 我没有尝试，这个应该也是没有实现吧，只是一个占位
 *ConnectionPoolDataSource
 *
 * 使用 驱动 自带 DataSource 对象
 *      获得 Connection
 *      获得 池化 Connection
 */
public class DataSourceTest {

    /**
     * 使用 MysqlDataSource
     */
    @Test
    public void test01() throws SQLException {

        MysqlDataSource mysqlDataSource = new MysqlDataSource();
        mysqlDataSource.setURL(Constant.JDBC_URL);
        mysqlDataSource.setUser(Constant.JDBC_USER);
        mysqlDataSource.setPassword(Constant.JDBC_PASSWORD);

        try (Connection connection = mysqlDataSource.getConnection()) {
            try (Statement statement = connection.createStatement()) {
                try (ResultSet resultSet = statement.executeQuery("select connection_id();")) {
                    resultSet.next();
                    String connection_id = resultSet.getString("connection_id()");
                    out.println(connection_id);
                }
            }
        }


    }

    /**
     * 使用 MysqlConnectionPoolDataSource
     * 不过 并没有链接池 效果
     */
    @Test
    public void test02() throws SQLException, InterruptedException {
        MysqlConnectionPoolDataSource mysqlConnectionPoolDataSource = new MysqlConnectionPoolDataSource();
        mysqlConnectionPoolDataSource.setURL(Constant.JDBC_URL);
        mysqlConnectionPoolDataSource.setUser(Constant.JDBC_USER);
        mysqlConnectionPoolDataSource.setPassword(Constant.JDBC_PASSWORD);

        while (true) {
            PooledConnection pooledConnection = mysqlConnectionPoolDataSource.getPooledConnection();

            Connection connection = pooledConnection.getConnection();
            try (Statement statement = connection.createStatement()) {
                try (ResultSet resultSet = statement.executeQuery("select connection_id();")) {
                    resultSet.next();
                    String connection_id = resultSet.getString("connection_id()");
                    out.println(connection_id);
                }
            }

            pooledConnection.close();
            Thread.sleep(1000);
        }
    }
}
