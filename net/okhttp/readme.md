
okhttp 功能测试，版本 4.9

https://square.github.io/okhttp/

据说性能 很 强大

1.overview 概述

2.calls 调用
  Requests ，一个请求包含 URL 、method 、headers 、body（可选，特定类型的数据流）
  Responses ，一个响应包含 code、headers、body（可选）
  Rewriting Requests ，okhttp 在传输请求前 会 对其 进行重写，添加 一些 header
      Content-Length，Transfer-Encoding，User-Agent，Host，Connection，和Content-Type，
      Accept-Encoding 压缩
      Cookie
      If-Modified-Since和If-None-Match 缓存相关
  Rewriting Responses ，接收到响应后 okhttp 还会进行一些处理，例如 解压缩后 删除 Content-Encoding header， 并 改写 Content-Length 因为 body 的长度已经改变
      如果有条件的GET成功，则按照规范的指示将来自网络和缓存的响应合并.
  Follow-up Requests， 后续请求
      自动处理重定向， 自动处理 Authenticator 认证后 重新 请求
  Retrying Requests，请求重试
      自动重试，如果 链接池断开、服务器无响应 等
  Calls，调用
       okhttp 把一次请求抽象为 Call ，并且 请求重写 响应重写 请求重试 重定向 甚至使用备用ip地址 ，对于 Call 都是透明的，不需要用户判断
       同步：阻塞当前线程
       异步：请求在一个线程，响应在另一个线程回调，就是不确定呗
      可以在 任何 线程 取消 请求，线程安全呗
  Dispatch
      对于同步调用，需要自己管理同时 发出请求的 数量，链接过多 会浪费资源
      异步调用，有最大 同时 请求数量限制，一个 web 服务器 最大链接 5 个，整体最大链接 64 个

3.caching 缓存
  OkHttp实现了一个可选的，默认情况下关闭的 Cache.

4.connections 链接

5.events 事件
  通过事件 可以监听 请求的 频率 大小 时间 等

6.https
  https 链接是复杂的，各种 认证方式，加密方法，证书 等等

7.interceptors
  每个 拦截器 都要执行这个方法 Response response = chain.proceed(request);
  拦截器列表，按先后顺序 递归调用，最终 调用到 真正执行请求的那个
  还分为两种：
      应用拦截器
      网络拦截器

8.recipes 食谱（用法、用例）
  Synchronous Get
  Asynchronous Get
  Accessing Headers
  Posting a String
  Post Streaming
  Posting a File
  Posting form parameters
  Posting a multipart request
  Parse a JSON Response With Moshi
  Response Caching
  Canceling a Call
  Timeouts
  Per-call Configuration
  Handling authentication

9.security
  版本的安全更新支持

10.security providers
  http/2 和 tls1.3 的安全算法支持情况
  http/1.1 和 tls1.2 全部都支持

11.works with okhttp
  一些 和 okhttp 合作的很好的库