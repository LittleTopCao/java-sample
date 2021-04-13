package com.test;

import org.apache.ibatis.datasource.pooled.PooledDataSource;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.junit.Before;

import java.io.IOException;
import java.sql.SQLException;

public class SessionFactoryTest {

    SqlSessionFactory sqlSessionFactory;

    /**
     * 使用 java 代码 新建 SqlSessionFactory
     */
    @Before
    public void before02() throws IOException, SQLException {
        TransactionFactory transactionFactory = new JdbcTransactionFactory();
        PooledDataSource pooledDataSource = new PooledDataSource("com.mysql.cj.jdbc.Driver", "jdbc:mysql://127.0.0.1:3306/school", "root", "root");

        Environment environment = new Environment("development", transactionFactory, pooledDataSource);

        Configuration configuration = new Configuration(environment);

//        configuration.addMapper(BoundBlogMapper.class);

        SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
        sqlSessionFactory = builder.build(configuration);
    }
}
