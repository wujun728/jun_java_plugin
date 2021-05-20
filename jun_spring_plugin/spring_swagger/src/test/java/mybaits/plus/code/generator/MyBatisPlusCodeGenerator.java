package mybaits.plus.code.generator;

import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;


/**
 * @description
 * @auther: CDHong
 * @date: 2019/6/24-10:18
 **/
public class MyBatisPlusCodeGenerator {

    //属性配置文件
    private ResourceBundle rb = ResourceBundle.getBundle("druid");

    @Test
    public void codeGenerator() {
        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();

        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        String projectPath = System.getProperty("user.dir");
        gc.setOutputDir(projectPath + "/src/main/java");
        gc.setAuthor("CDHong");
        gc.setOpen(false);
        gc.setSwagger2(true); //实体属性 Swagger2 注解
        mpg.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl(rb.getString("url"));
        // dsc.setSchemaName("public");
        dsc.setDriverName(rb.getString("driverClassName"));
        dsc.setUsername(rb.getString("username"));
        dsc.setPassword(rb.getString("password"));
        mpg.setDataSource(dsc);

        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setParent("org.itcast.demo");  //父级公用包名，就是自动生成的文件放在项目路径下的那个包中
        mpg.setPackageInfo(pc);

        // 自定义配置
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing
            }
        };
        String templatePath = "/templates/mapper.xml.vm";
        // 自定义输出配置
        List<FileOutConfig> focList = new ArrayList<>();
        // 自定义配置会被优先输出
        focList.add(new FileOutConfig(templatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
                return projectPath + "/src/main/resources/mappers/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
            }
        });

        cfg.setFileOutConfigList(focList);
        mpg.setCfg(cfg);

        // 配置模板
        TemplateConfig templateConfig = new TemplateConfig();
        templateConfig.setXml(null); //是否在mapper接口处生成xml文件
        mpg.setTemplate(templateConfig);


        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);  //Entity文件名称命名规范
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);  //Entity字段名称命名规范
        strategy.setEntityLombokModel(true);  //是否使用lombok完成Entity实体标注Getting Setting ToString 方法
        strategy.setRestControllerStyle(true);  //Controller注解使用是否RestController标注,否则是否开启使用Controller标注
        strategy.setControllerMappingHyphenStyle(true);  //Controller注解名称，不使用驼峰，使用连字符
        strategy.setTablePrefix("tbl_");  //表前缀，添加该表示，则生成的实体，不会有表前缀，比如sys_dept 生成就是Dept
        //strategy.setFieldPrefix("sys_");  //字段前缀
        mpg.setStrategy(strategy);
        mpg.execute();
    }
}
