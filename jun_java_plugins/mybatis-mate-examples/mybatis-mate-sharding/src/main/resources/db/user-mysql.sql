DROP TABLE IF EXISTS user;

CREATE TABLE user
(
    id BIGINT(20) NOT NULL COMMENT '主键ID',
    username VARCHAR(30) NULL DEFAULT NULL COMMENT '用户名',
    password VARCHAR(1000) NULL DEFAULT NULL COMMENT '密码',
    sex INT(2) NULL DEFAULT NULL COMMENT '性别',
    email VARCHAR(1000) NULL DEFAULT NULL COMMENT '邮箱',
    PRIMARY KEY (id)
);

DELETE FROM user;

INSERT INTO user (id, username, password, sex, email) VALUES
(1, 'Jone', '123456', 1, 'test1@baomidou.com'),
(2, 'Jack', '123456', 0, 'test2@baomidou.com');