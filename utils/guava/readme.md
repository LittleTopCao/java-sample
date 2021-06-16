[github](https://github.com/google/guava)
[中文文档](https://wizardforcel.gitbooks.io/guava-tutorial/content/1.html)

1. 基本工具(Basic utilities)
    1. 可选空指针(Java8 已经内置了)
    2. 条件检查(不局限于参数)Preconditions
    3. 常见Object方法: 简化Object方法实现，如hashCode()和toString(), (Java7 引入了 Objects)
    4. 排序: Guava强大的”流畅风格Comparator”
    5. Throwables：简化了异常和错误的传播与检查
2. 集合(Collections), 最成熟和最受欢迎的部分
   1 不可变集合: 用不变的集合进行防御性编程和性能提升。
   2 新集合类型: multisets, multimaps, tables, bidirectional maps等
   3 强大的集合工具类: 提供java.util.Collections中没有的集合工具
   4 扩展工具类：让实现和扩展集合类变得更容易，比如创建Collection的装饰器，或实现迭代器
3. 图数据结构(Graphs)
4. 缓存(Caches)
5. 函数式风格(Functional idioms)
6. 并发(Concurrency)
7. 字符串处理(Strings)
8. 原生类型(Primitives)
9. 区间(Ranges)
10. IO
11. 散列(Hashing)
12. 事件总线(EventBus)
13. 数学运算(Math)
14. 反射(Reflection)