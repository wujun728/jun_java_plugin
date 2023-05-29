CREATE TABLE t_user1(id INT ,
                     userName VARCHAR(20),
                     PASSWORD VARCHAR(20),
                     INDEX (userName)
	             );
	             
CREATE TABLE t_user2(id INT ,
                     userName VARCHAR(20),
                     PASSWORD VARCHAR(20),
                     UNIQUE INDEX index_userName(userName)
	             );
	           
CREATE TABLE t_user3(id INT ,
                     userName VARCHAR(20),
                     PASSWORD VARCHAR(20),
                     INDEX index_userName_password(userName,PASSWORD)
	             );
	             
CREATE 	INDEX index_userName ON t_user4(userName);


CREATE 	UNIQUE INDEX index_userName ON t_user4(userName);

CREATE  INDEX index_userName_password ON t_user4(userName,PASSWORD);

ALTER TABLE t_user5 ADD INDEX index_userName(userName);

ALTER TABLE t_user5 ADD UNIQUE INDEX index_userName(userName);

ALTER TABLE t_user5 ADD INDEX index_userName_password(userName,PASSWORD);

DROP INDEX index_userName ON t_user5;

DROP INDEX index_userName_password ON t_user5;