package com.dcy.generator;

import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.dcy.model.GenerModel;
import com.dcy.utils.GenUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author：dcy
 * @Description: mybatis-plus 代码生成器  微服务使用
 * @Date: 2019/9/6 13:47
 */
public class ServiceGenerator {

    /**
     * RUN THIS
     */
    public static void main(String[] args) {
        // 获取参数
        GenerModel generModel = GenUtils.getGenerByProps();

        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();

        // 全局配置
        GlobalConfig gc = GenUtils.setGlobalConfig(generModel);
        mpg.setGlobalConfig(gc);

        // 数据源配置
        GenUtils.setDataSourceConfig(generModel, mpg);

        // 包配置
        setPackageConfig(mpg, generModel);

        //自定义生成文件
        setTemplateMapper(mpg, generModel);

        // 策略配置
        StrategyConfig strategyConfig = GenUtils.setStrategyConfig(generModel, mpg);
        strategyConfig.setSuperControllerClass("com.dcy.db.base.controller.BaseController")
                .setSuperEntityClass("com.dcy.db.base.model.BaseModel")
                .setSuperServiceClass("com.dcy.db.base.service.BaseService")
                .setSuperServiceImplClass("com.dcy.db.base.service.impl.BaseServiceImpl")
                .setSuperEntityColumns("createBy", "createDate", "updateBy", "updateDate", "delFlag", "remark");

        mpg.setStrategy(strategyConfig);
        // 选择 freemarker 引擎需要指定如下加，注意 pom 依赖必须有！
        mpg.setTemplateEngine(new FreemarkerTemplateEngine());
        TemplateConfig tc = GenUtils.setTemplateConfig();
        mpg.setTemplate(tc);
        mpg.execute();
    }

    /**
     * 包设置
     *
     * @param mpg
     * @param generModel
     */
    private static void setPackageConfig(AutoGenerator mpg, GenerModel generModel) {
        PackageConfig pc = new PackageConfig();
        String models = generModel.getModules();

        pc.setParent("com.dcy.modules")
                .setController("controller")
                .setEntity("model")
                .setService("service")
                .setServiceImpl("service.impl")
                .setMapper("mapper")
                .setXml("mapper")
                //父包模块名
                .setModuleName(models);
        mpg.setPackageInfo(pc);
    }

    private static void setTemplateMapper(AutoGenerator mpg, final GenerModel generModel) {
        // 注入自定义配置，可以在 VM 中使用 cfg.abc 【可无】  ${cfg.abc}
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("swagger2", true);
                map.put("entityColumnConstant", false);
                this.setMap(map);
            }
        };
        List<FileOutConfig> focList = new ArrayList<FileOutConfig>();
        focList.add(new FileOutConfig("/dto/output.java.ftl") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输入文件名称
                System.out.println(tableInfo);
                return generModel.getPack() + "//dto//output//" + tableInfo.getEntityName() + "OutputDTO.java";
            }
        });
        focList.add(new FileOutConfig("/dto/input.java.ftl") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输入文件名称
                System.out.println(tableInfo);
                return generModel.getPack() + "//dto//input//" + tableInfo.getEntityName() + "InputDTO.java";
            }
        });
        focList.add(new FileOutConfig("/vue/manage-element.vue.ftl") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输入文件名称
                return generModel.getPack() + "//vue//" + tableInfo.getEntityPath() + "-manage.vue";
            }
        });
        focList.add(new FileOutConfig("/vue/vue.js.ftl") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输入文件名称
                return generModel.getPack() + "//vue//" + tableInfo.getEntityPath() + ".js";
            }
        });
        focList.add(new FileOutConfig("/service/clientService.java.ftl") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输入文件名称
                return generModel.getPack() + "//client-service//" + tableInfo.getEntityName() + "ClientService" + StringPool.DOT_JAVA;
            }
        });
        focList.add(new FileOutConfig("/service/remoteService.java.ftl") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输入文件名称
                return generModel.getPack() + "//api-service//" + tableInfo.getEntityName() + "RemoteService" + StringPool.DOT_JAVA;
            }
        });
        cfg.setFileOutConfigList(focList);
        mpg.setCfg(cfg);
    }


}
