package com.dcy.utils;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.setting.dialect.Props;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.TemplateConfig;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.dcy.model.GenerModel;

public class GenUtils {
    private static final Props props;

    private GenUtils() {

    }

    static {
        props = new Props("db.properties", CharsetUtil.UTF_8);
    }

    public static GenerModel getGenerByProps() {
        GenerModel generModel = new GenerModel();
        generModel.setPack(props.getStr("db.pack"));
        generModel.setDbUrl(props.getStr("db.url"));
        generModel.setDriverName(props.getStr("db.driverName"));
        generModel.setUsername(props.getStr("db.username"));
        generModel.setPassword(props.getStr("db.password"));
        generModel.setTableName(props.getStr("table.tableName"));
        generModel.setModules(props.getStr("table.modules"));
        generModel.setPrefix(props.getStr("table.prefix"));
        return generModel;
    }

    /**
     * 设置模板配置
     * @return
     */
    public static TemplateConfig setTemplateConfig() {
        TemplateConfig tc = new TemplateConfig();
        tc.setController("/simple/controller.java");
        tc.setService("/simple/service.java");
        tc.setServiceImpl("/simple/serviceImpl.java");
        tc.setEntity("/simple/entity.java");
        tc.setMapper("/simple/mapper.java");
        tc.setXml("/simple/mapper.xml");
        return tc;
    }

    /**
     * 设置
     * @param generModel
     * @param mpg
     */
    public static StrategyConfig setStrategyConfig(GenerModel generModel, AutoGenerator mpg) {
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel)
                .setColumnNaming(NamingStrategy.underline_to_camel)
                .setEntityLombokModel(true)
                .setInclude(generModel.getTableName().split(","))
                .setControllerMappingHyphenStyle(true)
                .setEntityColumnConstant(true)
                .setRestControllerStyle(true)
                .setTablePrefix(generModel.getPrefix().split(","));
        return strategy;
    }

    public static void setDataSourceConfig(GenerModel generModel, AutoGenerator mpg) {
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl(generModel.getDbUrl())
                .setDriverName(generModel.getDriverName())
                .setUsername(generModel.getUsername())
                .setPassword(generModel.getPassword());
        mpg.setDataSource(dsc);
    }

    public static GlobalConfig setGlobalConfig(GenerModel generModel) {
        GlobalConfig gc = new GlobalConfig();
        gc.setAuthor("dcy")
                //开启 BaseResultMap
                .setBaseResultMap(true)
                //开启 baseColumnList
                .setBaseColumnList(true)
                // 生成文件的输出目录
                .setOutputDir(generModel.getPack())
                //是否覆盖已有文件
                .setFileOverride(true)
                // 是否打开输出目录
                .setOpen(false);
        return gc;
    }
}
