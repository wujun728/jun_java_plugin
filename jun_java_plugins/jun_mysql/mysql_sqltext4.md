SELECT * FROM t_book WHERE booktypeId IN (SELECT id FROM t_booktype);

SELECT * FROM t_book WHERE booktypeId NOT IN (SELECT id FROM t_booktype);



SELECT * FROM t_book WHERE price>=(SELECT price FROM t_pricelevel WHERE priceLevel=1);


SELECT * FROM t_book WHERE EXISTS (SELECT * FROM t_booktype);

SELECT * FROM t_book WHERE NOT EXISTS (SELECT * FROM t_booktype);

SELECT * FROM t_book WHERE price>= ANY (SELECT price FROM t_pricelevel);

SELECT * FROM t_book WHERE price>= ALL (SELECT price FROM t_pricelevel);