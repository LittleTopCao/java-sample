[JSON最佳实践](http://kimmking.github.io/2017/06/06/json-best-practice/#9-fastjson_u7684_u6700_u4F73_u5B9E_u8DF5)

[JSON的一切](https://github.com/burningtree/awesome-json)

JSON规范
    1. 基本类型：string number boolean null object array
    2. object 中的 key 只能是 string
    3. string 用 双引号


三个流行库 
    1. gson
    2. jackson
    3. fastjson

与Java对应
    1. string -> 字符串
    2. number -> 整形和浮点
    3. boolean -> 布尔
    4. null -> null
    5. object -> map 或 bean
    6. array -> list

几种使用情景
    1. list 和 map 嵌套
    2. 自定义对象
    3. 自定义对象 和 list 和 map 嵌套
