import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.sql.*;

/**
 * 预处理 语句 上下文， 预编译， 支持 占位符 ，防止 sql 注入， 其他接口与 Statement 一样
 *
 *
 */
public class PreparedStatementTest {
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
     * 执行方法, 纯演示
     *
     * 与 Statement 接口一样，只是不带 Sting 参数，因为 它是预编译的
     *
     */
    @Test
    public void test01() throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("select * from actor");

        //如果是 select 语句
        //或 返回结果的 语句，例如 select connection_id();
        ResultSet resultSet = preparedStatement.executeQuery();

        //如果是 insert update delete 语句， 返回影响的行数
        //或者是 ddl 语句，返回 0
        int executeUpdate = preparedStatement.executeUpdate();

        //如果 sql 语句 返回多个 结果 就需要这个
        // 返回 true 代表 第一个结果是 ResultSet
        boolean execute = preparedStatement.execute();

    }


    /**
     * 设置参数
     *      参数位置 是 从 1 开始的
     *      要根据类型 设置
     *
     */
    @Test
    public void test02() throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("select * from actor where actor_id > ? and first_name like ?");

        preparedStatement.setInt(1, 2);
        preparedStatement.setString(2, "%A%");

        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            System.out.println(resultSet.getString(1));
        }
    }


    /**
     * 变量类型
     */
    @Test
    public void test03() throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("select * from actor where actor_id > ? and first_name like ?");

        //设置 占位 符
        preparedStatement.setByte(1, (byte) 1); //TINYINT
        preparedStatement.setShort(1, (short) 1); //SMALLINT
        preparedStatement.setInt(1, 1); //INTEGER
        preparedStatement.setLong(1, 1L); //BIGINT

        preparedStatement.setBoolean(1, true); //BIT

        preparedStatement.setFloat(1, 1.1F); //REAL
        preparedStatement.setDouble(1, 1); //DOUBLE

        preparedStatement.setDate(1, new Date(System.currentTimeMillis())); //DATE
        preparedStatement.setTime(1, new Time(System.currentTimeMillis())); //TIME
        preparedStatement.setTimestamp(1, new Timestamp(System.currentTimeMillis())); //TIMESTAMP

        preparedStatement.setNull(1, Types.VARCHAR);

        preparedStatement.setBigDecimal(1, new BigDecimal(1)); //NUMERIC

        preparedStatement.setString(2, "%A%"); //VARCHAR 或 LONGVARCHAR

        preparedStatement.setBytes(1, new byte[]{}); //VARBINARY 或 LONGVARBINARY


//        preparedStatement.setArray(1, new Array); //ARRAY

//        preparedStatement.setObject();


        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            System.out.println(resultSet.getString(1));
        }
    }


}
