
java.net 包 功能 测试

https://docs.oracle.com/javase/8/docs/api/java/net/package-summary.html

1.包说明
  提供用于实现联网应用程序的类.
  java.net包可以大致分为两部分：
       低级API，它处理以下抽象：
           Addresses 是网络标识符，例如IP地址.
           Sockets 这是基本的双向数据通信机制.
           Interfaces 描述网络接口.
       高级API，处理以下抽象：
           URIs 代表通用资源标识符.
           URLs 表示通用资源定位符.
           Connections 表示与URL指向的资源的连接.

2.Addresses
  InetAddress类是表示IP（互联网协议）地址的抽象.它有两个子类：
      Inet4Address 用于IPv4地址.
      Inet6Address 用于IPv6地址.

3.Sockets
  套接字是在网络上的机器之间建立通信链接的手段.java.net包提供了四种套接字：
       Socket是TCP客户端API，通常用于连接到远程主机.
       ServerSocket是TCP服务器API，通常会接受 来自客户端套接字的连接.
       DatagramSocket是UDP端点API，用于发送和 接收 数据报包.
       MulticastSocket是DatagramSocket处理多播组时使用的子类 .
  使用TCP套接字发送和接收是通过InputStreams和OutputStreams完成的，可以通过Socket.getInputStream()和 Socket.getOutputStream()方法获得它们 .

4.Interfaces
  NetworkInterface 类提供API以浏览和查询所有的网络接口在本地机器（例如以太网连接或PPP端点）.

5.高级API
  java.net包中的许多类确实提供了更高级别的抽象，并允许轻松访问网络上的资源.这些类是：
       URI 是RFC 2396中指定的表示通用资源标识符的类.顾名思义，这只是一个标识符，并不直接提供访问资源的方式.
       URL 是表示通用资源定位器的类，它既是URI以前的概念，也是访问资源的一种手段.
       URLConnection 是从URL创建的，是用于访问URL指向的资源的通信链接.这个抽象类将把大部分工作委托给诸如HTTP或https之类的底层协议处理程序.
       HttpURLConnection 是 URLConnection 的子类，并提供一些特定于 HTTP协议 的附加功能，例如 cookie 等

  一般操作，新建 URI ，转换为 URL，通过 URL 拿 URLConnection，或者也可以直接拿 InputStream 来获取资源
       URI uri = new URI("http://java.sun.com/");
       URL url = uri.toURL();
       InputStream in = url.openStream();

6.协议处理程序 Handler
  URL 和 URLConnection 依赖与 特定的 协议处理程序

  uri 就不需要，uri 不去验证 协议 处理程序是否存在，但是如果 转换成 URL 时，协议处理程序 不存在 那么将 引发异常

  协议处理程序 从 java.protocol.handler.pkgs 系统属性指定的位置查找
       例如：如果设置成 myapp.protocols 那么使用 http 时，将查找 myapp.protocols.http.Handler 是否存在，不存在 再使用默认的

  Handler 类必须是抽象类 URLStreamHandler 的子类 .

7.附加规格
有些系统属性 可以配置 java.net 包
  默认可以动态更改， 可以使用 System.setProperty() 设置
  但是有些属性 只有在 启动时 检查一边， 需要在使用 java 命令运行时 指定 -D 选项

  1.ipv4 和 ipv6，怎么选择 4 和 6，通过配置 java.net.preferIPv4Stack 和 java.net.preferIPv6Addresses，只在启动时检查一次

  2.代理，可以设置代理
      HTTP，如果设置，http 协议的 handler 将使用
          http.proxyHost
          http.proxyPort 默认 80
          http.nonProxyHosts (默认: localhost|127.|[::1])， 不使用代理 访问的 主机，例如 -Dhttp.nonProxyHosts=”.foo.com|localhost”
      HTTPS，如果设置，https 协议的 handler 将使用
          https.proxyHost
          https.proxyPort 默认 443
      FTP，如果设置，那么 ftp 协议的 handler 将使用
          ftp.proxyHost
          ftp.proxyPort
          ftp.nonProxyHosts
      SOCKS，如果设置 java 平台中的 tcp 都通过这个代理
          socksProxyHost
          socksProxyPort
          socksProxyVersion
          java.net.socks.username
          java.net.socks.password
      java.net.useSystemProxies 是否跟随 系统代理 设置，默认为 false ，只在 启动时 检查一次

  3.其他http属性
      http.agent 设置 agent
      http.keepalive 是否长链接，默认 true，一个 socket 将被多次 http 使用
      http.maxConnections 如果 启用长链接，那么 一个 服务器的 最大 tcp 链接数量
      http.maxRedirects 最大重定向次数
      http.auth.digest.validateServer 摘要式身份验证
      http.auth.digest.validateProxy 摘要式身份验证
      http.auth.digest.cnonceRepeat 摘要式身份验证
      http.auth.ntlm.domain NTLM是另一种身份验证方案.它使用java.net.Authenticator类在需要时获取用户名和密码.

  4.DNS缓存
      java.net 在 进行 域名解析 或 反向解析 时，无论成功与否 都会 缓存结果
      networkaddress.cache.ttl 成功结果缓存时间
      networkaddress.cache.negative.ttl 失败结果缓存事件
