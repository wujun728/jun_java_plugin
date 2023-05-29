DROP TABLE IF EXISTS sku;

CREATE TABLE sku
(
    id BIGINT(20) NOT NULL COMMENT '主键 ID',
    product VARCHAR(30) NULL DEFAULT NULL COMMENT '商品',
    color VARCHAR(30) NULL DEFAULT NULL COMMENT '颜色',
    stock INT(10) NULL DEFAULT NULL COMMENT '库存',
    PRIMARY KEY (id)
);

DELETE FROM sku;

INSERT INTO sku (id, product, color, stock) VALUES (3, 'huawei mate30', '银白', 100);