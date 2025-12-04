import cn.hutool.core.lang.Console;
import cn.hutool.extra.spring.SpringUtil;
import com.alibaba.druid.pool.DruidDataSource;
import com.jfinal.plugin.activerecord.dialect.MysqlDialect;
import com.jfinal.plugin.activerecord.generator.Generator;

import javax.sql.DataSource;

public class Test {

    public static void main(String[] args) {
        // model 所使用的包名 (MappingKit 默认使用的包名)
        String modelPackageName = "io.github.wujun728.model";

        // base model 所使用的包名
        String baseModelPackageName = modelPackageName + ".base";

        // base model 文件保存路径
        String baseModelOutputDir = System.getProperty("user.dir") + "/src/main/java/" + baseModelPackageName.replace('.', '/');

        // model 文件保存路径 (MappingKit 与 DataDictionary 文件默认保存路径)
        String modelOutputDir = baseModelOutputDir + "/..";

        // 创建生成器
        Generator gen = new Generator(getDS(), baseModelPackageName, baseModelOutputDir, modelPackageName, modelOutputDir);

        // 设置数据库方言
        gen.setDialect(new MysqlDialect());

        // 在 getter、setter 方法上生成字段备注内容
        gen.setGenerateRemarks(true);
        // 添加多个表名到黑名单
        gen.addBlacklist("sys_role", "role_permission", "user_role");

        // 添加多个表名白名单
        gen.addWhitelist("sys_user");

        // 开始生成代码
        gen.generate();
    }

    	public static DataSource getDS(){
            DruidDataSource ds = new DruidDataSource();
            ds.setUrl("jdbc:mysql://localhost:3306/snowy-layui-pub?autoReconnect=true&useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=CONVERT_TO_NULL&useSSL=false&nullCatalogMeansCurrent=true");
            ds.setUsername("root");
            ds.setPassword("");
            return ds;
        }
    	public void test(){
            DruidDataSource ds = new DruidDataSource();
            ds.setUrl(SpringUtil.getProperty("spring.datasource.url"));
            Console.log("url = " + SpringUtil.getProperty("spring.datasource.url"));
            ds.setUsername(SpringUtil.getProperty("spring.datasource.username"));
            ds.setPassword(SpringUtil.getProperty("spring.datasource.password"));
        }
}
