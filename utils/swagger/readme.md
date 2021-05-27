参考 https://www.cnblogs.com/jpfss/p/11439560.html

Swagger 是一个规范和完整的框架，用于生成、描述、调用和可视化 RESTful 风格的 Web 服务。

Swagger是一组开源项目，其中主要有项目如下：
1.   Swagger-tools: 提供各种与Swagger进行集成和交互的工具。例如模式检验、Swagger 1.2文档转换成Swagger 2.0文档等功能。
2.   Swagger-core: 用于Java/Scala的的Swagger实现。与JAX-RS(Jersey、Resteasy、CXF...)、Servlets和Play框架进行集成。
3.   Swagger-js: 用于JavaScript的Swagger实现。
4.   Swagger-node-express: Swagger模块，用于node.js的Express web应用框架。
5.   Swagger-ui：一个无依赖的HTML、JS和CSS集合，可以为Swagger兼容API动态生成优雅文档。
6.   Swagger-codegen：一个模板驱动引擎，通过分析用户Swagger资源声明以各种语言生成客户端代码。


和 Spring 配合是 我们要使用 io.springfox 。springfox-swagger2 项目
    这会根据 咱们的 注解 生成 api，但是是以文件的形式

我们配合  io.springfox  springfox-swagger-ui 项目 使用 ，以网页的形式展现
    地址是 swagger-ui


<dependency>
    <groupId></groupId>
    <artifactId></artifactId>
    <version>2.2.2</version>
</dependency>
<dependency>
    <groupId></groupId>
    <artifactId></artifactId>
    <version>2.2.2</version>
</dependency>




