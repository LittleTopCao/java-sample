import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.*;

import static java.lang.System.out;

/**
 * 可以通过 数据库的元数据 获得一些 数据库 信息
 *
 * 例如：所有库，所有表，表的结构
 *
 * 数据库 对一些特性的支持
 *
 *
 */
public class DatabaseMetaDataTest {

    Connection connection;
    DatabaseMetaData metaData;


    @Before
    public void before() throws SQLException {
        connection = DriverManager.getConnection(Constant.JDBC_URL, Constant.JDBC_USER, Constant.JDBC_PASSWORD);
        //数据库元数据
        metaData = connection.getMetaData();
    }

    @After
    public void after() throws SQLException {
        connection.close();
    }

    /**
     * 打印所有的库  \ 表  \ 列
     */
    @Test
    public void test01() throws SQLException {
        ResultSet catalogs = metaData.getCatalogs();
        while (catalogs.next()) {
            String table_cat = catalogs.getString("TABLE_CAT");
            out.println(table_cat + "----------------------------");

            ResultSet tables = metaData.getTables(table_cat, "%", "%", null);
//            ResultSetMetaDataTest.printColumn(tables.getMetaData());

            while (tables.next()) {
                String tableName = tables.getString(3);
                out.print(tableName + ":");

                ResultSet columns = metaData.getColumns(table_cat, "%", tableName, "%");

                while (columns.next()) {
                    String columnName = columns.getString(4);
                    out.print(columnName + " ");
                }
                out.println();
            }


            //根据条件 查找 列
//            metaData.getAttributes(String catalog, String schemaPattern, String typeNamePattern, String attributeNamePattern)


        }
    }


    /**
     * 对事务的支持
     */
    @Test
    public void test02() throws SQLException {
        //是否支持事务
        metaData.supportsTransactions();

        //是否支持
        metaData.supportsTransactionIsolationLevel(Connection.TRANSACTION_SERIALIZABLE);

    }

    /**
     * 此数据库对 列 类型的支持
     *
     * 此数据库 对 表格 类型的 支持（临时表、表格、视图）
     */
    @Test
    public void test03() throws SQLException {
        ResultSet typeInfo = metaData.getTypeInfo();
        while (typeInfo.next()) {
            String string = typeInfo.getString(1);
            out.println("数据库支持的数据类型：" + string);
        }

        out.println();

        ResultSet tableTypes = metaData.getTableTypes();
//        ResultSetMetaDataTest.printInfo(tableTypes.getMetaData());
        while (tableTypes.next()) {
            String string = tableTypes.getString(1);
            out.println("数据库支持的表格类型：" + string);
        }
    }


    /**
     * 数据库 对特性的支持， 还有很多呀
     */
    @Test
    public void test04() throws SQLException {


        out.println("当前用户是否可以调用 getProcedures 方法：" + metaData.allProceduresAreCallable());
        out.println("当前用户是否可以调用 getTables 方法：" + metaData.allTablesAreSelectable());
        out.println("当 auto commit 是 true ，发生异常后如何操作：" + metaData.autoCommitFailureClosesAllResultSets());
        out.println("事务中的数据定义语句 是否 强制 事务 提交：" + metaData.dataDefinitionCausesTransactionCommit());
        out.println("事务中的数据定义语句 是否 被忽略：" + metaData.dataDefinitionIgnoredInTransactions());
        out.println("是否能从 这种类型的 结果集 中 调用 ResultSet.rowDeleted 方法：" + metaData.deletesAreDetected(ResultSet.TYPE_SCROLL_INSENSITIVE));
        out.println("方法 getMaxRowSize 的返回值 是否 包含 LONGVARCHAR 和 LONGVARBINARY：" + metaData.doesMaxRowSizeIncludeBlobs());
        out.println("自动生成的 键 成功后 是否 返回 生成的键：" + metaData.generatedKeyAlwaysReturned());

        //是否支持批量更新
        out.println("是否支持 批量更新：" + metaData.supportsBatchUpdates());

        //查看此数据库 在 select 语句中 允许的最大表数
        //int maxTablesInSelect = metaData.getMaxTablesInSelect();
    }
}
