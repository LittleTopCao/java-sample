
现在 log4j 的第一版 已经 停止更新，第二版 进行了 重写， 与 第一版 并不兼容

https://logging.apache.org/log4j/2.x/javadoc.html

第二版 不支持 properties 形式的 配置

log4j2 现在是性能最强的日志框架了，它也分成了两个： core 和 api，我们可以把它和 slf4j 一起使用
    也可以直接使用 log4j api， 主要还是要和你的其他库 统一，例如 spring 、mybatis 等