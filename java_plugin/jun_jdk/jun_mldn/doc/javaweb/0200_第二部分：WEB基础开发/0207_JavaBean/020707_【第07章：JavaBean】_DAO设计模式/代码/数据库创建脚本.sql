/*======================= 删除数据库 =======================*/
DROP DATABASE IF EXISTS mldn ;
/*======================= 创建数据库 =======================*/
CREATE DATABASE mldn ;
/*======================= 使用数据库 =======================*/
USE mldn ;
/*======================= 删除数据表 =======================*/
DROP TABLE IF EXISTS emp ;
/*======================= 创建数据表 =======================*/
CREATE TABLE emp(
   empno			INT(4)			PRIMARY KEY,
   ename			VARCHAR(10),
   job				VARCHAR(9),
   hiredate			DATE,
   sal				FLOAT(7,2)
) ;
/*======================= 插入测试数据 =======================*/
INSERT INTO emp (empno,ename,job,hiredate,sal) VALUES (6060,'李兴华','经理','2001-09-16',2000.30) ;
INSERT INTO emp (empno,ename,job,hiredate,sal) VALUES (7369,'董鸣楠','销售','2003-10-09',1500.90) ;
INSERT INTO emp (empno,ename,job,hiredate,sal) VALUES (8964,'李祺','分析员','2003-10-01',3000) ;
INSERT INTO emp (empno,ename,job,hiredate,sal) VALUES (7698,'张惠','销售','2005-03-12',800) ;
INSERT INTO emp (empno,ename,job,hiredate,sal) VALUES (7782,'杨军','分析员','2005-01-12',2500) ;
INSERT INTO emp (empno,ename,job,hiredate,sal) VALUES (7762,'刘明','销售','2005-03-09',1000) ;
INSERT INTO emp (empno,ename,job,hiredate,sal) VALUES (7839,'王月','经理','2006-09-01',2500) ;
