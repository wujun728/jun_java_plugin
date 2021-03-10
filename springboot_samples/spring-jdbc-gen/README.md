###spring-jdbc-gen

生成基于Spring JdbcTemplate 的DAO层代码，model代码，service层代码，让你兼具灵活性又免去了繁杂的重复劳动

生成使用条件：<br/>
1. jdk7以上<br/>
2. 只支持mysql<br/>
3. 数据表的主键名为id<br/>

生成使用指南：<br/>
1. 修改conf.properties数据连接以及其他配置文件<br/>
2. 修改beans.xml中的数据库连接<br/>
3. 运行Generate.java的main方法即可<br/>
