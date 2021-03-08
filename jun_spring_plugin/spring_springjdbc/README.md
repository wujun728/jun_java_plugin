#SpringMvc-SpringJdbc-Demo

使用SpringJdbc作为数据库访问持久层的SpringMvc框架

 **1. 技术点** 

- maven3
- spring3.0.5RELEASE
- jstl1.2
- dbcp1.4
- mysql-connector-java5.1.6

- mysql5.5.34
- phpMyAdmin4.0.9

 **2. 导入工程到eclipse** 

```
git clone https://git.oschina.net/chenjintaox/springjdbc.git
```
选择eclipse File->Import->Existing Maven Projects

 **3. 导入表结构到数据库** 

- phpMyAdmin导入 ~\springjdbc\src\main\resources\cjtdb-Jan-03.sql
- 配置jdbc ~\springjdbc\src\main\resources\cjtdb-Jan-03.sql

 **4. 运行项目** 

- 启动tomcat
- 访问```
http://localhost:8080/springjdbc
```