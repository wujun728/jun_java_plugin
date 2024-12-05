package io.github.wujun728.code;

import io.github.wujun728.db.utils.DataSourcePool;
import io.github.wujun728.generator.CodeUtil;
import lombok.extern.slf4j.Slf4j;

import javax.sql.DataSource;

@Slf4j
public class CodeUtilTest {

    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/db_qixing_bk?useUnicode=true&characterEncoding=UTF-8&useSSL=true&serverTimezone=UTC&useInformationSchema=true";
        String username = "root";
        String password = "";
        String driver = "com.mysql.cj.jdbc.Driver";
        DataSource ds = DataSourcePool.init("main",url,username,password,driver);
//        CodeUtil.genCode(ds,"biz_mail",CodeUtil.MYBATIS_PLUG_VO_JAVA);
//        CodeUtil.genCode(ds,"biz_mail",CodeUtil.MYBATIS_PLUG_CONTROLLER_JAVA);
//        CodeUtil.genCode(ds,"biz_mail",CodeUtil.UTIL_SQL);
//        CodeUtil.genCodeFile(ds,"biz_mail","D:/jva/test", CodeUtil.BEETLSQL_BEETLCONTROLLER);
        CodeUtil.genCodeFile(ds,"biz_mail","D:/java1/test", CodeUtil.GROUP_BEETLSQL);
        CodeUtil.genCodeFile(ds,"biz_mail","D:/java1/test", CodeUtil.GROUP_JPA);
        CodeUtil.genCodeFile(ds,"biz_mail","D:/java1/test", CodeUtil.GROUP_JDBCTEMPLATE);
        CodeUtil.genCodeFile(ds,"biz_mail","D:/java1/test", CodeUtil.GROUP_MYBATIS);
        CodeUtil.genCodeFile(ds,"biz_mail","D:/java1/test", CodeUtil.GROUP_MYBATIS_PLUG_NO1);
        CodeUtil.genCodeFile(ds,"biz_mail","D:/java1/test", CodeUtil.GROUP_MYBATISPLUS);
        CodeUtil.genCodeFile(ds,"biz_mail","D:/java1/test", CodeUtil.GROUP_UI);
        CodeUtil.genCodeFile(ds,"biz_mail","D:/java1/test", CodeUtil.GROUP_UTIL);
//        CodeUtil.genCodeFile(ds,"biz_mail",CodeUtil.MYBATIS_PLUG_SINGLE_VO_JAVA,"D:/jva/test");



//        CodeUtil.genCode(ds,"biz_mail",CodeUtil.JDBCTEMPLATE_JTDAO);
//        CodeUtil.genCode(ds,"biz_mail",CodeUtil.JDBCTEMPLATE_JTDAOIMPL);
    }

}
