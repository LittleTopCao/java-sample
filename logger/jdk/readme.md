
jdk 自带 日志 工具

java.util.logging

    * LogManager 用来管理所有 Logger

    * Logger 有层级关系
        可以设置输出级别
        有一个 root logger 作为顶级， 子 logger 默认继承 父级的 输出级别 、handler

    * Handler 执行日志的输出，与 logger 多对多
        可以设置输出级别

    * Formatter 日志的格式化，与 handler 一对一