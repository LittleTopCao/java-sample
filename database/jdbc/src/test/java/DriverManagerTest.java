import org.junit.Test;

import java.sql.*;
import java.util.Enumeration;

import static java.lang.System.out;

/**
 * DriverManager 测试
 *      获取所有驱动
 *      找到 url 匹配的驱动
 *      获得链接
 *
 */
public class DriverManagerTest {
    /**
     * 获得当前所有的驱动
     */
    @Test
    public void test01() {
        Enumeration<Driver> drivers = DriverManager.getDrivers();

        while (drivers.hasMoreElements()) {
            Driver driver = drivers.nextElement();
            System.out.println(driver.getClass().getName());
        }
    }


    /**
     * 找到 Url 匹配的驱动
     */
    @Test
    public void test02() throws SQLException {
        Driver driver = DriverManager.getDriver(Constant.JDBC_URL);
        System.out.println(driver.getClass().getName());
    }


    /**
     * 获得链接
     */
    @Test
    public void test03() throws SQLException {
        try (Connection connection = DriverManager.getConnection(Constant.JDBC_URL, Constant.JDBC_USER, Constant.JDBC_PASSWORD)) {

            try (Statement statement = connection.createStatement()) {
                try (ResultSet resultSet = statement.executeQuery("select connection_id();")) {
                    resultSet.next();
                    String connection_id = resultSet.getString("connection_id()");
                    out.println(connection_id);
                }
            }
        }
    }


}
