DELIMITER &&
CREATE PROCEDURE pro_getBookNameById(IN bookId INT,OUT bN VARCHAR(20))
 BEGIN
	SELECT bookName INTO bn FROM t_book WHERE id=bookId;
 END 
&&
DELIMITER ;

CALL pro_getBookNameById(10,@bookName);
SELECT @bookName;