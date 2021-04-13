import com.mysql.cj.jdbc.MysqlDataSource;
import org.junit.Before;
import org.junit.Test;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static java.lang.System.out;



public class ExecuteTest {

    DataSource dataSource;


    @Before
    public void before() {
        MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setURL(Constant.JDBC_URL);
        dataSource.setUser(Constant.JDBC_USER);
        dataSource.setPassword(Constant.JDBC_PASSWORD);
    }

    @Test
    public void createTable() throws SQLException {
        String createString =
                "create table COFFEES " + "(COF_NAME varchar(32) NOT NULL, " +
                        "SUP_ID int NOT NULL, " + "PRICE numeric(10,2) NOT NULL, " +
                        "SALES integer NOT NULL, " + "TOTAL integer NOT NULL, " +
                        "PRIMARY KEY (COF_NAME), " +
                        "FOREIGN KEY (SUP_ID) REFERENCES SUPPLIERS (SUP_ID))";


        try (Connection connection = dataSource.getConnection()) {
            try (Statement statement = connection.createStatement()) {
                statement.executeUpdate(createString);
            }
        }
    }
}
