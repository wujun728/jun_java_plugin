CREATE VIEW v1 AS SELECT * FROM t_book;

CREATE VIEW v2 AS SELECT bookName,price FROM t_book;

CREATE VIEW v3(b,p) AS SELECT bookName,price FROM t_book;


SELECT * FROM v1;

SELECT * FROM v2;

SELECT * FROM v3;

CREATE VIEW v4 AS SELECT bookName,bookTypeName FROM t_book,t_booktype WHERE t_book.bookTypeId=t_booktype.id;

CREATE VIEW v5 AS SELECT tb.bookName,tby.bookTypeName FROM t_book tb,t_booktype tby WHERE tb.bookTypeId=tby.id;

SELECT * FROM v4;

SELECT * FROM v5;

DESC v5;

SHOW TABLE STATUS LIKE 'v5';

SHOW TABLE STATUS LIKE 't_book';

SHOW CREATE VIEW v5;