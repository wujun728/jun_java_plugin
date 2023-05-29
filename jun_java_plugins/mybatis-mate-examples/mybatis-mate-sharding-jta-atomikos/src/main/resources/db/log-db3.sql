DROP TABLE IF EXISTS "public"."log";
CREATE TABLE "public"."log" (
                                "id" int8 NOT NULL,
                                "content" varchar(500) COLLATE "pg_catalog"."default"
)
;
COMMENT ON COLUMN "public"."log"."id" IS '主键';
COMMENT ON COLUMN "public"."log"."content" IS '内容';

ALTER TABLE "public"."log" ADD CONSTRAINT "log_pkey" PRIMARY KEY ("id");
