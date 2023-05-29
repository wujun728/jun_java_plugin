DELIMITER &&
CREATE PROCEDURE pro_user()
	BEGIN
	 DECLARE a,b VARCHAR(20) ;
	 INSERT INTO t_user VALUES(NULL,a,b);
	END 
&&
DELIMITER ;

DELIMITER &&
CREATE PROCEDURE pro_user2()
	BEGIN
	 DECLARE a,b VARCHAR(20) ;
	 SET a='java1234',b='123456';
	 INSERT INTO t_user VALUES(NULL,a,b);
	END 
&&
DELIMITER ;

DELIMITER &&
CREATE PROCEDURE pro_user3()
	BEGIN
	 DECLARE a,b VARCHAR(20) ;
	 SELECT userName2,password2 INTO a,b FROM t_user2 WHERE id2=1;
	 INSERT INTO t_user VALUES(NULL,a,b);
	END 
&&
DELIMITER ;


DELIMITER &&
CREATE PROCEDURE pro_user4()
	BEGIN
	 DECLARE a,b VARCHAR(20) ;
	 DECLARE cur_t_user2 CURSOR FOR SELECT userName2,password2 FROM t_user2;
	 OPEN cur_t_user2;
	 FETCH cur_t_user2 INTO a,b;
	 INSERT INTO t_user VALUES(NULL,a,b);
	 CLOSE cur_t_user2;
	END 
&&
DELIMITER ;

DELIMITER &&
CREATE PROCEDURE pro_user5(IN bookId INT)
	BEGIN
	 SELECT COUNT(*) INTO @num FROM t_user WHERE id=bookId;
	 IF @num>0 THEN UPDATE t_user SET userName='java12345' WHERE id=bookId;
	 ELSE
	   INSERT INTO t_user VALUES(NULL,'2312312','2321312');
	 END IF ;
	END 
&&
DELIMITER ;

DELIMITER &&
CREATE PROCEDURE pro_user6(IN bookId INT)
	BEGIN
	 SELECT COUNT(*) INTO @num FROM t_user WHERE id=bookId;
	 CASE @num
	  WHEN 1 THEN UPDATE t_user SET userName='java12345' WHERE id=bookId;
	  WHEN 2 THEN INSERT INTO t_user VALUES(NULL,'2312312','2321312');
	  ELSE INSERT INTO t_user VALUES(NULL,'231231221321312','2321312321312');
	 END CASE ;
	END 
&&
DELIMITER ;


DELIMITER &&
CREATE PROCEDURE pro_user7(IN totalNum INT)
	BEGIN
	  aaa:LOOP
	    SET totalNum=totalNum-1;
	    IF totalNum=0 THEN LEAVE aaa ;
	    ELSE INSERT INTO t_user VALUES(totalNum,'2312312','2321312');
	    END IF ;
	  END LOOP aaa ;
	END 
&&
DELIMITER ;



DELIMITER &&
CREATE PROCEDURE pro_user8(IN totalNum INT)
	BEGIN
	  aaa:LOOP
	    SET totalNum=totalNum-1;
	    IF totalNum=0 THEN LEAVE aaa ;
	    ELSEIF totalNum=3 THEN ITERATE aaa ;
	    END IF ;
	    INSERT INTO t_user VALUES(totalNum,'2312312','2321312');
	  END LOOP aaa ;
	END 
&&
DELIMITER ;

DELIMITER &&
CREATE PROCEDURE pro_user9(IN totalNum INT)
	BEGIN
	  REPEAT
	     SET totalNum=totalNum-1;
	     INSERT INTO t_user VALUES(totalNum,'2312312','2321312');
	     UNTIL totalNum=1 
	  END REPEAT;
	END 
&&
DELIMITER ;

DELIMITER &&
CREATE PROCEDURE pro_user10(IN totalNum INT)
	BEGIN
	 WHILE totalNum>0 DO
	  INSERT INTO t_user VALUES(totalNum,'2312312','2321312');
	  SET totalNum=totalNum-1;
	 END WHILE ;
	END 
&&
DELIMITER ;

CALL pro_user();

CALL pro_user2();

CALL pro_user3();

CALL pro_user4();

CALL pro_user5(5);

CALL pro_user6(6);

CALL pro_user7(11);

CALL pro_user8(11);

CALL pro_user9(11);

CALL pro_user10(10);


DELETE FROM t_user;