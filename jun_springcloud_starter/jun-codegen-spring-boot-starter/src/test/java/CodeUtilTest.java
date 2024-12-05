//import io.github.wujun728.db.DataSourcePool;
//import io.github.wujun728.generator.CodeUtil;
//import lombok.extern.slf4j.Slf4j;
//
//import javax.sql.DataSource;
//
//@Slf4j
//public class CodeUtilTest {
//
//    public static void main(String[] args) {
//        String url = "jdbc:mysql://localhost:3306/db_qixing_bk?useUnicode=true&characterEncoding=UTF-8&useSSL=true&serverTimezone=UTC&useInformationSchema=true";
//        String username = "root";
//        String password = "";
//        String driver = "com.mysql.cj.jdbc.Driver";
//        DataSource ds = DataSourcePool.init("main",url,username,password,driver);
//        CodeUtil.genCode(ds,"biz_mail",CodeUtil.mybatis_plug_vo_java);
//    }
//
//}
