package com.mybatisplus.generator;

import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.mybatisplus.generator.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * select GROUP_CONCAT(table_name) from information_schema.tables where table_schema='vtis_ycxc' and TABLE_NAME like 'jc_%'
 * 生成工具
 *
 * @author Administrator
 * @Description com.ycxc.vtis - vtis
 * Created by hack2003 on 2019/4/17
 **/
public class CodeGenerator {
    /**
     * <p>
     * 读取控制台内容
     * </p>
     */
    public static String scanner(String tip) {
        Scanner scanner = new Scanner(System.in);
        StringBuilder help = new StringBuilder();
        help.append("请输入" + tip + "：");
        System.out.println(help.toString());
        if (scanner.hasNext()) {
            String ipt = scanner.next();
            if (StringUtils.isNotEmpty(ipt)) {
                return ipt;
            }
        }
        throw new MybatisPlusException("请输入正确的" + tip + "！");
    }

    public static void main(String[] args) {
        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();
        String packageCompany = "ycxc";
        String packageProject = "immv";
        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        String projectPath = System.getProperty("user.dir");
        gc.setOutputDir(projectPath + "/src/main/java");
        gc.setAuthor("gjd");
        //是否覆盖已有文件
        gc.setFileOverride(false);
        gc.setOpen(false);
        gc.setFileOverride(true);
        gc.setActiveRecord(true);
        // XML 二级缓存
        gc.setEnableCache(false);
        // XML ResultMap
        gc.setBaseResultMap(true);
        // XML columList
        gc.setBaseColumnList(false);
        //实体属性 Swagger2 注解
        gc.setSwagger2(true);
        mpg.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl("jdbc:mysql://223.247.212.219:3366/cjl_ycxc_20190809?useUnicode=true&characterEncoding=utf-8&serverTimezone=GMT%2B8");
        // dsc.setSchemaName("public");
        dsc.setDriverName("com.mysql.jdbc.Driver");
        dsc.setUsername("root");
        dsc.setPassword("rootBussa$");
        mpg.setDataSource(dsc);

        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setParent("com." + packageCompany + "");
        pc.setModuleName(packageProject);
        String packagefunction = scanner("功能目录:");
        pc.setController("controller." + packagefunction);
        pc.setEntity("entity." + packagefunction);
        pc.setService("service." + packagefunction);
        pc.setServiceImpl("service." + packagefunction + ".impl");
        pc.setMapper("dao." + packagefunction);
        pc.setXml("mapper");
        mpg.setPackageInfo(pc);

        // 自定义配置
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing
            }
        };

        // 如果模板引擎是 freemarker
        //String templatePath = "/templates/mapper.xml.ftl";
        // 如果模板引擎是 velocity
        String templatePath = "/templates/mapper.xml.vm";
        // 自定义输出配置
        List<FileOutConfig> focList = new ArrayList<>();
        // 自定义配置会被优先输出
        String finalPackagefunction = packagefunction;
        focList.add(new FileOutConfig(templatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
                return projectPath + "/src/main/resources/mapper/" + finalPackagefunction
                        + "/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
            }
        });
        cfg.setFileOutConfigList(focList);
        mpg.setCfg(cfg);
        mpg.setTemplate(new TemplateConfig().setXml(null));
        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        //strategy.setSuperEntityClass("com." + packageCompany + "." + pc.getModuleName() + ".common.base.BaseEntity");
        //strategy.setSuperServiceImplClass("com."+packageCompany+"."+pc.getModuleName()+".common.base.BaseServiceImpl");
        //是否为lombok模型
        strategy.setEntityLombokModel(false);
        strategy.setRestControllerStyle(true);

        //是否生成实体时，生成字段注解
//        strategy.setEntityTableFieldAnnotationEnable(true);
        strategy.setSuperControllerClass("com." + packageCompany + "." + pc.getModuleName() + ".common.base.BaseController");
        strategy.setInclude(scanner("表名用逗号分割").split(","));
        strategy.setSuperEntityColumns("id");
        //驼峰转连字符
        strategy.setControllerMappingHyphenStyle(true);
        strategy.setTablePrefix(pc.getModuleName() + "_");
        mpg.setStrategy(strategy);
        mpg.execute();
    }

}
