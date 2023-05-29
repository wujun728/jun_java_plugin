-- ----------------------------
-- Table structure for "ZHGJ"."TAG"
-- ----------------------------
-- DROP TABLE "ZHGJ"."TAG";
CREATE TABLE "ZHGJ"."TAG" (
                               "id" NUMBER(20,0) VISIBLE NOT NULL,
                               "name" NVARCHAR2(30) VISIBLE NOT NULL,
                               "type" NUMBER(6,0) VISIBLE
)
LOGGING
NOCOMPRESS
PCTFREE 10
INITRANS 1
STORAGE (
  INITIAL 65536
  NEXT 1048576
  MINEXTENTS 1
  MAXEXTENTS 2147483645
  BUFFER_POOL DEFAULT
)
PARALLEL 1
NOCACHE
DISABLE ROW MOVEMENT
;
COMMENT ON COLUMN "ZHGJ"."TAG"."id" IS '主键 ID';
COMMENT ON COLUMN "ZHGJ"."TAG"."name" IS '名称';
COMMENT ON COLUMN "ZHGJ"."TAG"."type" IS '类别';
COMMENT ON TABLE "ZHGJ"."TAG" IS '标签';
