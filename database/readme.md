1. 这里使用 mysql 数据库
2. 数据库样例：[Sakila(DVD租赁商店)](https://dev.mysql.com/doc/sakila/en/)
    * 16个表
        * actor Table 演员表
        * address Table 地址表
        * category Table 电影类别表
        * city Table 城市表
        * country Table 国家表
        * customer Table 客户表
        * film Table 电影表
        * film_actor Table 电影和演员中间对应表
        * film_category Table 电影和类别的中间对应表
        * film_text Table 只包含title和description两列的电影表子表，用以快速检索
        * inventory Table 库存表
        * language Table 语言表
        * payment Table 付款表
        * rental Table 租金表
        * staff Table 工作人员表
        * store Table 商店表
    * 7个视图
    * 3个存储过程
    * 3个函数
    * 6个触发器