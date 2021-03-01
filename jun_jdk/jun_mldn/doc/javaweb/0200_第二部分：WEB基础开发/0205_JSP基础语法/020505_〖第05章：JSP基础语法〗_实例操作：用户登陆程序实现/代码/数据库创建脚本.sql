/*======================= 使用MLDN数据库 =======================*/
USE mldn ;
/*======================= 删除user数据表 =======================*/
DROP TABLE IF EXISTS user ;
/*======================= 创建user数据表 =======================*/
CREATE TABLE user(
	userid			VARCHAR(30)		PRIMARY KEY ,
	name			VARCHAR(30)		NOT NULL ,
	password		VARCHAR(32)		NOT NULL
) ;
/*======================= 插入测试数据 =======================*/
INSERT INTO user (userid,name,password) VALUES ('admin','administrator','admin') ;
