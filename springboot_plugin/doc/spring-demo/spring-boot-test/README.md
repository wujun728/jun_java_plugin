# 单元测试样例

本样例包含了单元测试以及集成测试。

使用 Mockito 构建 mock 


源码路径： */src/main/*

测试代码路径： */src/test/*


---

执行单元测试：

    run  com.buxiaoxia.ApplicationTest

打包命令：

    mvn package

启动命令：

    java -jar spring-boot-test-1.0-SNAPSHOT.jar

swagger 界面：

    http://127.0.0.1:8080/swagger-ui.thml




样例提供以下接口：

---

POST 	/api/v1/staffs   （添加员工）

DELETE  /api/v1/staffs/{id} （获删除单个员工）

GET 	/api/v1/staffs/{id} （获取单个员工）

GET 	/api/v1/staffs/{staffId}/dep （获取员工部门信息）

--- 

POST    /api/v1/deps   （添加部门）

DELETE  /api/v1/deps/{id} （删除部门）

GET     /api/v1/deps/{id} （获取单个部门）

PUT     /api/v1/deps/{id} （修改单个部门）

---

GET     /api/v1/deps/{depId}/staffs   （获取部门员工）

DELETE  /api/v1/deps/{depId}/staffs/{staffId} （移除部门员工）

POST    /api/v1/deps/{depId}/staffs/{staffId} （添加部门员工）


> 一共写了46个单元测试用例