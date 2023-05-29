CREATE TABLE IF NOT EXISTS "public"."tag" (
    "id" int8 NOT NULL,
    "name" varchar(30) COLLATE "pg_catalog"."default" NOT NULL,
    "type" int2 DEFAULT 1
);
COMMENT ON COLUMN "public"."tag"."id" IS '主键 ID';
COMMENT ON COLUMN "public"."tag"."name" IS '名称';
COMMENT ON COLUMN "public"."tag"."type" IS '类别';
COMMENT ON TABLE "public"."tag" IS '标签';
