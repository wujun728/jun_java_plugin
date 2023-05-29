SELECT id,stuName,age,sex,gradeName FROM t_student ;

SELECT stuName,id,age,sex,gradeName FROM t_student ;

SELECT * FROM t_student;

SELECT stuName,gradeName FROM t_student;

SELECT * FROM t_student WHERE id=1;

SELECT * FROM t_student WHERE age>22;

SELECT * FROM t_student WHERE age IN (21,23);
SELECT * FROM t_student WHERE age NOT IN (21,23);

SELECT * FROM t_student WHERE age BETWEEN 21 AND 24;
SELECT * FROM t_student WHERE age NOT BETWEEN 21 AND 24;

SELECT * FROM t_student WHERE stuName LIKE '张三';
SELECT * FROM t_student WHERE stuName LIKE '张三%';
SELECT * FROM t_student WHERE stuName LIKE '张三__';
SELECT * FROM t_student WHERE stuName LIKE '%张三%';

SELECT * FROM t_student WHERE sex IS NULL;
SELECT * FROM t_student WHERE sex IS NOT NULL;

SELECT * FROM t_student WHERE gradeName='一年级' AND age=23
SELECT * FROM t_student WHERE gradeName='一年级' OR age=23

SELECT DISTINCT gradeName FROM t_student;

SELECT * FROM t_student ORDER BY age ASC;
SELECT * FROM t_student ORDER BY age DESC;

SELECT * FROM t_student GROUP BY gradeName;

SELECT gradeName,GROUP_CONCAT(stuName) FROM t_student GROUP BY gradeName;

SELECT gradeName,COUNT(stuName) FROM t_student GROUP BY gradeName;

SELECT gradeName,COUNT(stuName) FROM t_student GROUP BY gradeName HAVING COUNT(stuName)>3;

SELECT gradeName,COUNT(stuName) FROM t_student GROUP BY gradeName WITH ROLLUP;
SELECT gradeName,GROUP_CONCAT(stuName) FROM t_student GROUP BY gradeName WITH ROLLUP;

SELECT * FROM t_student LIMIT 0,5;
SELECT * FROM t_student LIMIT 5,5;
SELECT * FROM t_student LIMIT 10,5;