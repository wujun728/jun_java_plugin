DROP TABLE IF EXISTS sensitive_words;

CREATE TABLE sensitive_words
(
    id BIGINT(20) NOT NULL COMMENT '主键ID',
    word VARCHAR(30) NULL DEFAULT NULL COMMENT '敏感词',
    PRIMARY KEY (id)
);