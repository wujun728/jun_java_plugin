CREATE TRIGGER trig_book AFTER INSERT 
     ON t_book FOR EACH ROW
        UPDATE t_bookType SET bookNum=bookNum+1 WHERE new.bookTypeId=t_booktype.id;
        
        
INSERT INTO t_book VALUES(NULL,'java好',100,'ke',1);



DELIMITER |
CREATE TRIGGER trig_book2 AFTER DELETE 
    ON t_book FOR EACH ROW
    BEGIN
       UPDATE t_bookType SET bookNum=bookNum-1 WHERE old.bookTypeId=t_booktype.id;
       INSERT INTO t_log VALUES(NULL,NOW(),'在book表里删除了一条数据');
       DELETE FROM t_test WHERE old.bookTypeId=t_test.id;
    END 
|
DELIMITER ;

DELETE FROM t_book WHERE id=5;

SHOW TRIGGERS;

DROP TRIGGER trig_book2 ;