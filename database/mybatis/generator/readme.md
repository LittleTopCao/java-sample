http://mybatis.org/generator/index.html

mybatis 官方提供了从数据库生成 entity 和 dao 的工具

简单理解：它就是一个java编写的工具，根据数据库元信息生成java代码

## 简单入门

下载 jar 包后执行， 它会根据配置信息 读取数据库 元信息 生成 代码文件
    java -jar mybatis-generator-core-x.x.x.jar -configfile \temp\generatorConfig.xml -overwrite

generatorConfig.xml 文件
```xml
<!DOCTYPE generatorConfiguration PUBLIC
 "-//mybatis.org//DTD MyBatis Generator Configuration 1.0//EN"
 "http://mybatis.org/dtd/mybatis-generator-config_1_0.dtd">
<generatorConfiguration>
  <context id="dsql" targetRuntime="MyBatis3DynamicSql">
    <jdbcConnection driverClass="org.hsqldb.jdbcDriver"
        connectionURL="jdbc:hsqldb:mem:aname" />

    <javaModelGenerator targetPackage="example.model" targetProject="src/main/java"/>

    <javaClientGenerator targetPackage="example.mapper" targetProject="src/main/java"/>

    <table tableName="FooTable" />
  </context>
</generatorConfiguration>
```

## 运行方式

1. 作为 jar 从命令行运行
2. ant 配置
3. maven 插件
4. 在java程序中启动，使用 xml 配置
5. 在java程序中启动，使用 java 配置
6. 通过 eclipse 扩展

### 作为 maven 插件运行

引入插件
```xml
<plugin>
  <groupId>org.mybatis.generator</groupId>
  <artifactId>mybatis-generator-maven-plugin</artifactId>
  <version>1.4.0</version>
</plugin>
```

插件包含一个目标 mybatis-generator:generate

可以直接运行 mvn mybatis-generator:generate
    添加参数 mvn -Dmybatis.generator.overwrite=true mybatis-generator:generate

也可以配置绑定到 maven 的 generate-sources 阶段（默认）

它的配置可以直接使用 pom 的 properties，
    例如 mybatis.generator.configurationFile 指定配置文件目录 等等

## 配置文件详解

http://mybatis.org/generator/configreference/xmlconfig.html

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE generatorConfiguration
        PUBLIC "-//mybatis.org//DTD MyBatis Generator Configuration 1.0//EN"
        "http://mybatis.org/dtd/mybatis-generator-config_1_0.dtd">

<generatorConfiguration>
    <classPathEntry location="/Program Files/IBM/SQLLIB/java/db2java.zip" />

    <context id="DB2Tables" targetRuntime="MyBatis3">
        <jdbcConnection driverClass="COM.ibm.db2.jdbc.app.DB2Driver"
                        connectionURL="jdbc:db2:TEST"
                        userId="db2admin"
                        password="db2admin">
        </jdbcConnection>

        <javaTypeResolver >
            <property name="forceBigDecimals" value="false" />
        </javaTypeResolver>

        <javaModelGenerator targetPackage="test.model" targetProject="\MBGTestProject\src">
            <property name="enableSubPackages" value="true" />
            <property name="trimStrings" value="true" />
        </javaModelGenerator>

        <sqlMapGenerator targetPackage="test.xml"  targetProject="\MBGTestProject\src">
            <property name="enableSubPackages" value="true" />
        </sqlMapGenerator>

        <javaClientGenerator type="XMLMAPPER" targetPackage="test.dao"  targetProject="\MBGTestProject\src">
            <property name="enableSubPackages" value="true" />
        </javaClientGenerator>

        <table schema="DB2ADMIN" tableName="ALLTYPES" domainObjectName="Customer" >
            <property name="useActualColumnNames" value="true"/>
            <generatedKey column="ID" sqlStatement="DB2" identity="true" />
            <columnOverride column="DATE_FIELD" property="startDate" />
            <ignoreColumn column="FRED" />
            <columnOverride column="LONG_VARCHAR_FIELD" jdbcType="VARCHAR" />
        </table>

    </context>
</generatorConfiguration>
```