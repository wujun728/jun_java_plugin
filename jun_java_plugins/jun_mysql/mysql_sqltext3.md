SELECT * FROM t_book,t_bookType;

SELECT * FROM t_book,t_bookType WHERE t_book.bookTypeId=t_bookType.id;

SELECT bookName,author,bookTypeName FROM t_book,t_bookType WHERE t_book.bookTypeId=t_bookType.id;

SELECT tb.bookName,tb.author,tby.bookTypeName FROM t_book tb,t_bookType tby WHERE tb.bookTypeId=tby.id;

SELECT * FROM t_book LEFT JOIN t_bookType ON t_book.bookTypeId=t_bookType.id;

SELECT tb.bookName,tb.author,tby.bookTypeName FROM t_book tb LEFT JOIN t_bookType tby ON tb.bookTypeId=tby.id;


SELECT * FROM t_book RIGHT JOIN t_bookType ON t_book.bookTypeId=t_bookType.id;

SELECT tb.bookName,tb.author,tby.bookTypeName FROM t_book tb RIGHT JOIN t_bookType tby ON tb.bookTypeId=tby.id;




SELECT tb.bookName,tb.author,tby.bookTypeName FROM t_book tb,t_bookType tby WHERE tb.bookTypeId=tby.id AND tb.price>70;