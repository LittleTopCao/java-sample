
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import static java.lang.System.out;

/**
 * 测试 结果集的 元数据  ResultSetMetaData 对象 接口
 */
public class ResultSetMetaDataTest {

    /**
     * 根据 ResultSetMetaData 打印 列名 和 类型
     */
    public static void printColumn(ResultSetMetaData metaData) throws SQLException {

        int columnCount = metaData.getColumnCount();

        for (int i = 0; i < columnCount; i++) {
            String columnName = metaData.getColumnName(i + 1);
            String columnTypeName = metaData.getColumnTypeName(i + 1);
            System.out.print(columnName + "(" + columnTypeName + ")" + "\t");
        }


    }

    /**
     * 打印 能获得 的 所有信息
     */
    public static void printInfo(ResultSetMetaData metaData) throws SQLException {
        //获得 列的 数量
        out.println("列的数量：" + metaData.getColumnCount());

        //获得列的 数据库（目录）名
        out.println("数据库名：" + metaData.getCatalogName(1));
        //获得列的 模式（ schema ）名
        out.println("模式名：" + metaData.getSchemaName(1));
        //获得列的 表 名
        out.println("表名：" + metaData.getTableName(1));

        //获得列名
        out.println("第一列名：：" + metaData.getColumnName(1));
        //获得列的 sql 类型
        out.println("第一列的sql类型：" + metaData.getColumnTypeName(1));
        //获得列的 sql 类型 标识，对应 java.sql.Types
        out.println("第一列的sql类型标识：" + metaData.getColumnTypeName(1));
        //获得列的 java 类型，ResultSet.getObject 对应的类型
        out.println("第一列的java类型：" + metaData.getColumnClassName(1));

        //指定的列 是否是 自增长
        out.println("第一列 是否自增长：" + metaData.isAutoIncrement(1));
    }


}
