
Retrofit 功能测试

扩展 okhttp ，使用 接口 方法 定义 api ，便捷的 指定参数 和 返回值类型，提供自定义 参数类型 和 返回值类型 的方式

其中的 Call 和 Callback 和 okhttp 很类似，但是又是自己的类型

https://square.github.io/retrofit/

1.introduction 介绍

2.api declaration 声明
  以接口方法的 方式 声明 api
  request method 请求方法
      不同的 注解 表明 接口的请求方式，HTTP, GET, POST, PUT, PATCH, DELETE, OPTIONS and HEAD

  url manipulation 操纵url
      接口的 URL 作为 注解的参数 ， URL 可以携带查询参数
      动态路径参数
          在 URL 字符串中使用 {} 表示替换块，在方法参数上使用 @Path 注解相同 名称的 参数
      动态查询参数
          在 方法参数上使用 @Query 注解
          还可以使用  @QueryMap 注解 放在 Map<String, String> 参数上

  request body 请求body
      可以使用 @Body 注解 RequestBody 参数 来作为 请求的 body
      也可以将 @Body 注解 放到 任意 对象上， 但是 必须添加了相应的 converter 转换器

  form encoded and multipart 表单编码和多部分请求
      指定 body 的编码方式为 form ，需要在方法上添加  @FormUrlEncoded 注解
          在参数上使用 @Field 指定body
          还有 @FieldMap
      指定 body 的编码方式为 Multipart，需要在方法上添加  @Multipart 注解
          在参数上使用 @Part 指定 body
          参数类型 是 RequestBody ，或者  可以使用 转换器 能转换的
          还有 @PartMap

  header manipulation 操纵header
      静态指定， 在方法上使用 @Headers  注解，这样指定的 header 不会 覆盖旧的
      动态指定，在 参数上使用 @Header 注解，如果为 null 将省略，否则调用参数的 toString 方法 作为 值
          也可以使用 @HeaderMap Map<String, String> headers


  synchronous vs asynchronous 同步和异步
      Call 实例 可以 同步 或 异步调用
      Call 实例 只能使用一次，使用 clone 方法 可以 复制一个
      在 android 回调将在 主线程运行，在 jvm 中 回调在 执行 http 请求的 线程上进行

3.retrofit configuration 配置
  默认情况下，Retrofit只能将HTTP正文反序列化为 OkHttp 的 ResponseBody 类型，并且只能接受的 RequestBody 类型@Body.

  响应的body 和 请求的@Body参数 和 请求的@Part参数 都只能使用 RequestBody 类型，其他类型就需要 添加 转换器

  其他参数 @Header 和 @Field 都是调用 toString 方法 转换

  可以添加转换器以支持其他类型.官方提供了 6 个 转换器
      Gson: com.squareup.retrofit2:converter-gson
      Jackson: com.squareup.retrofit2:converter-jackson
      Moshi: com.squareup.retrofit2:converter-moshi
      Protobuf: com.squareup.retrofit2:converter-protobuf
      Wire: com.squareup.retrofit2:converter-wire
      Simple XML: com.squareup.retrofit2:converter-simplexml
      JAXB: com.squareup.retrofit2:converter-jaxb
      Scalars (primitives, boxed, and String): com.squareup.retrofit2:converter-scalars
  也可以创建自己的 转换器
     实现  Converter.Factory 类
     例如 ，如果你要 直接 @Part File，就需要自定义 File 的转换器

4.download 下载