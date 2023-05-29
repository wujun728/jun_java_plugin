SELECT id FROM t_book;

SELECT id FROM t_booktype;

SELECT id FROM t_book UNION SELECT id FROM t_booktype;

SELECT id FROM t_book UNION ALL SELECT id FROM t_booktype;