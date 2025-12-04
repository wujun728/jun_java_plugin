package com.jun.plugin.codegenerator;

import com.alibaba.druid.pool.DruidDataSource;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.jun.plugin.common.generator.GeneratorUtil;

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
//		String tables = "app_infoenvt,app_member,app_datasource,app_git_config,git_user,app_deploy_config";
		Map config = Maps.newHashMap();
		config.put("packageName","com.bjc.lcp.user.extsalgrade");
//		config.put("project_path","D:/workspace/github/jun_code_generator/jun_code_freemarker/");
		config.put("output_path","D:\\workspace\\github\\jun_code_generator\\jun_code_freemarker");
		config.put("template_path","D:\\workspace\\github\\jun_code_generator\\jun_code_freemarker\\src\\main\\resources\\templates");
		config.put("userDefaultTemplate","false");
		DruidDataSource ds = new DruidDataSource();
		ds.setUrl("jdbc:mysql://localhost:3306/db_qixing_bk?useUnicode=true&characterEncoding=UTF-8&useSSL=true&serverTimezone=UTC&useInformationSchema=true");
		ds.setDriverClassName("com.mysql.cj.jdbc.Driver");
		ds.setUsername("root");
		ds.setPassword("");
		GeneratorUtil.setDataSource(ds);
		GeneratorUtil.setTemplates(getTemplates());
		GeneratorUtil.initConfig(config);
		GeneratorUtil.genCode("ext_salgrade");
//		GeneratorUtil.genCode(Arrays.asList(tables.split(",")),getTemplates());;
	}

	public static List<String> getTemplates() {
		List<String> templates = Lists.newArrayList();
		// ************************************************************************************
		templates.add("code-generator/mybatis-plus-single-v2/controller.java.ftl");
		templates.add("code-generator/mybatis-plus-single-v2/entity.java.ftl");
		templates.add("code-generator/mybatis-plus-single-v2/mapper.java.ftl");
		templates.add("code-generator/mybatis-plus-single-v2/service.java.ftl");
		templates.add("code-generator/mybatis-plus-single-v2/dto.java.ftl");
		templates.add("code-generator/mybatis-plus-single-v2/vo.java.ftl");
		templates.add("code-generator/mybatis-plus-single-v2/service.impl.java.ftl");
		return templates;
	}

}
