package com.jun.plugin.generatesql.service;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.jun.plugin.generatesql.entity.GenerateProperties;

import org.apache.commons.lang3.StringUtils;

/**
 * 构建Java文件
 */
public class BuildJavaProject {

    private static final String DB_URL = "jdbc:mysql://%s:3306/%s?useUnicode=true&characterEncoding=UTF-8&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";

    public static void build(GenerateProperties properties) {
        System.out.println("------开始构建Java项目------");
        String path = properties.getExportSqlDir();
        if (StringUtils.isBlank(path)) {
            throw new RuntimeException("请配置输出路径");
        }

        if (properties.getDataSource() == null) {
            throw new RuntimeException("请配置DataSource");
        }

        GlobalConfig config = new GlobalConfig();
        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        dataSourceConfig.setDbType(DbType.MYSQL)
                .setUrl(String.format(DB_URL, properties.getDataSource().getHost(), properties.getDataSource().getDatabase()))
                .setUsername(properties.getDataSource().getUser())
                .setPassword(properties.getDataSource().getPassword())
                .setDriverName("com.mysql.cj.jdbc.Driver");
        StrategyConfig strategyConfig = new StrategyConfig();
        strategyConfig
                .setCapitalMode(true)
                .setEntityLombokModel(true)
                .setDbColumnUnderline(true)
                .setNaming(NamingStrategy.underline_to_camel)
                //需要忽略的表名
                .setExclude(properties.getBuildConf().getExcludeTable().split(","))
                .setInclude();//修改替换成你需要的表名，多个表名传数组
        config.setActiveRecord(false)
                //作者
                .setAuthor(properties.getBuildConf().getAuthor())
                .setOutputDir(path)
                .setFileOverride(true)
                .setDateType(DateType.ONLY_DATE)
                .setServiceName("%sService");

        new AutoGenerator().setGlobalConfig(config)
                .setDataSource(dataSourceConfig)
                .setStrategy(strategyConfig)
                .setPackageInfo(
                        new PackageConfig()
                                .setParent(properties.getBuildConf().getPackageName())
                                .setMapper("dao")
                                .setController("controller")
                                .setEntity("entity")
                ).execute();
        System.out.println("------构建Java项目完成------");
    }

}
