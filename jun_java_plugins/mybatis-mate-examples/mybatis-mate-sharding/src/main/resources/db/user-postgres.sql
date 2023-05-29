DROP TABLE IF EXISTS "public"."user";

CREATE TABLE "public"."user" (
    "id" int8 NOT NULL,
    "username" varchar(255) NOT NULL,
    "password" varchar(255) NOT NULL,
    "sex" int2 NOT NULL,
    "email" varchar(255) NOT NULL,
    PRIMARY KEY ("id")
);

COMMENT ON COLUMN "public"."user"."id" IS '主键 ID';
COMMENT ON COLUMN "public"."user"."username" IS '用户名';
COMMENT ON COLUMN "public"."user"."password" IS '密码';
COMMENT ON COLUMN "public"."user"."sex" IS '性别';
COMMENT ON COLUMN "public"."user"."email" IS '电子邮箱';

DELETE FROM "public"."user";

INSERT INTO "public"."user" (id, username, password, sex, email) VALUES
(3, 'Tom', '123456', 1, 'test3@baomidou.com');
