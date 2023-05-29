SELECT * FROM t_book WHERE id=1;

SELECT * FROM t_book t WHERE t.id=1;

SELECT t.bookName FROM t_book t WHERE t.id=1;

SELECT t.bookName bName FROM t_book t WHERE t.id=1;

SELECT t.bookName AS bName FROM t_book t WHERE t.id=1;