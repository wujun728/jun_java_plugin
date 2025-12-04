package com.jun.plugin.codegenerator;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import io.github.wujun728.common.generator.GeneratorUtil;
//import com.jun.plugin.common.generator.GeneratorUtil;

import java.util.List;
import java.util.Map;

/**
 * 代码生成器，根据DatabaseMetaData及数据表名称生成对应的Model、Mapper、Service、Controller简化基础代码逻辑开发。
 * @author Wujun
 */
public class CodeGeneratorMain {

    public static void main(String[] args) throws Exception {
//		String tables = "res_basc,res_basc_arg,api_config";
//		String tables = "git_user";
        Map config = Maps.newHashMap();
        config.put("authorName","Wujun");
        config.put("packageName","com.jun.plugin.app1");
        config.put("template_path","D:\\workspace\\github\\jun_code_generator\\jun_code_freemarker\\src\\main\\resources\\mybatis-plus-single-v3");
        config.put("output_path","D:\\workspace\\github\\jun_code_generator\\jun_code_freemarker");
        config.put("userDefaultTemplate","false");
        config.put("jdbc.url","jdbc:mysql://localhost:3306/db_qixing_bk?useUnicode=true&characterEncoding=UTF-8&useSSL=true&serverTimezone=UTC&useInformationSchema=true");
        config.put("jdbc.username","root");
        config.put("jdbc.password","");
        config.put("jdbc.driver","com.mysql.cj.jdbc.Driver");
        config.put("userDefaultTemplate","false");
        config.put("isLombok","true");
        config.put("isSwagger","true");
        config.put("moduleName","testModule");
        GeneratorUtil.setTemplates(getTemplates());
        GeneratorUtil.initConfig(config);
        GeneratorUtil.genCode("biz_test");
    }

    public static List<String> getTemplates() {
        List<String> templates = Lists.newArrayList();
        templates.add("controller.java.ftl");
        templates.add("entity.java.ftl");
        templates.add("mapper.java.ftl");
        templates.add("service.java.ftl");
        templates.add("dto.java.ftl");
        templates.add("vo.java.ftl");
        templates.add("service.impl.java.ftl");
        templates.add("list.html.ftl");
        return templates;
    }

}
