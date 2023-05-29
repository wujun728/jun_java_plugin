DELIMITER &&
CREATE PROCEDURE pro_book ( IN bT INT,OUT count_num INT)
   READS SQL DATA
   BEGIN 
     SELECT COUNT(*) FROM t_book WHERE bookTypeId=bT;
   END 
   &&
DELIMITER ;

CALL pro_book(1,@total);


DELIMITER &&
CREATE FUNCTION func_book (bookId INT)
 RETURNS VARCHAR(20)
 BEGIN 
  RETURN ( SELECT bookName FROM t_book WHERE id=bookId );
 END 
    &&
DELIMITER ;

SELECT func_book(2);