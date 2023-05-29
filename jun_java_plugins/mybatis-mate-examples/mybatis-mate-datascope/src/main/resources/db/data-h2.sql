DELETE FROM user;
DELETE FROM department;

INSERT INTO user (id, department_id, username, mobile) VALUES
(1, 1, 'Jack', '15315355555'),
(2, 1, 'Jone', '15315399999'),
(3, 3, 'Duo', '15315338888'),
(4, 5, 'mali', '15315377777'),
(5, 1, 'Tom', '15315336666');

INSERT INTO department (id, pid, name, sort) VALUES
(1, 0, '研发中心', 0),
(2, 1, '项目部', 3),
(3, 2, '后端开发组', 2),
(4, 2, '前端开发组', 2),
(5, 1, '产品部', 1),
(6, 1, '运维部', 0);
