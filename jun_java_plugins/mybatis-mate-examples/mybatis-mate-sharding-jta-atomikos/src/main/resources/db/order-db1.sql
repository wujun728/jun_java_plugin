DROP TABLE IF EXISTS t_order;

CREATE TABLE t_order
(
    id BIGINT(20) NOT NULL COMMENT '主键 ID',
    sku_id BIGINT(20) NOT NULL COMMENT 'SKU ID',
    quantity INT(2) NULL DEFAULT NULL COMMENT '数量',
    price decimal(20, 2) NULL DEFAULT NULL COMMENT '价格',
    PRIMARY KEY (id)
);

DELETE FROM t_order;

INSERT INTO t_order (id, sku_id, quantity, price) VALUES (1, 3, 1, 5999.9);