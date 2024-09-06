//package io.github.wujun728.common.generator;
//
//import com.google.common.collect.Lists;
//import freemarker.template.TemplateException;
//import org.apache.commons.lang3.StringUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.util.CollectionUtils;
//
//import java.io.IOException;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
///**
// * 代码生成器，根据DatabaseMetaData及数据表名称生成对应的Model、Mapper、Service、Controller简化基础代码逻辑开发。
// *
// * @author Wujun
// */
//public class CodeGeneratorBak {
//	private static final Logger logger = LoggerFactory.getLogger(CodeGeneratorBak.class);
//
//	public static void main(String[] args) throws Exception {
//		String tables = "t_admin";
////		String tables = "git_user";
////		String tables = "app_infoenvt,app_member,app_datasource,app_git_config,git_user,app_deploy_config";
//		String  jarLocal = new String(CodeGeneratorBak.class.getProtectionDomain().getCodeSource().getLocation().getPath().getBytes(), "UTF-8");
//		String url8 = CodeGeneratorBak.class.getResource("/").getPath();
//		System.out.println( url8);
//		System.out.println( jarLocal);
//		System.out.println( System.getProperty("user.dir"));
//
//		String s = CodeGeneratorBak.class.getProtectionDomain().getCodeSource().getLocation().getPath() + "templates";
//		System.out.println("---- "+ s);
//
//		genTables(tables.split(","));
//	}
//
//
//	@Deprecated
//	public static void genTables(String [] tables) throws Exception {
//		genTables(tables,"");
//	}
//	@Deprecated
//	public static void genTables(String [] tables,String genType) throws Exception {
//		if(StringUtils.isEmpty(genType)){
//			genType = "single";
//		}
//		List<String> templates = getTemplates();
////		switch (genType){
////			case "single" : templates = getTemplates();break;
//////			case "parent" : templates = getTemplates();break;
////		}
//		List<ClassInfo> classInfos = GenUtilsBak.getClassInfo222(tables);
//		classInfos.forEach(classInfo -> {
//			Map<String, Object> datas = new HashMap<String, Object>();
//			datas.put("classInfo", classInfo);
//			datas.putAll(getPackages());
//			datas.put("authorName","wujun");
//			datas.put("isLombok",true);
//			datas.put("isAutoImport",true);
//			datas.put("isWithPackage",true);
//			datas.put("isSwagger",true);
//			datas.put("isComment",true);
//			datas.put("packageName",GenUtilsBak.getProp("packageName"));
//			Map<String, String> result = new HashMap<String, String>();
//			try {
//				//GenUtilsBak.processTemplatesStringWriter(datas, result);
//				GenUtilsBak.processTemplatesFileWriter(classInfo, datas,templates);
//			} catch (IOException e) {
//				e.printStackTrace();
//			} catch (TemplateException e) {
//				e.printStackTrace();
//			}
//			// 计算,生成代码行数
//			int lineNum = 0;
//			for (Map.Entry<String, String> item : result.entrySet()) {
//				if (item.getValue() != null) {
//					lineNum += StringUtils.countMatches(item.getValue(), "\n");
//				}
//			}
//			logger.info("生成代码行数：{}", lineNum);
//		});
//		if(CollectionUtils.isEmpty(classInfos)){
//			logger.error("找不到当前表的元数据classInfos.size()：{}", classInfos.size());
//		}
//	}
//
//
//	public static List<String> getTemplates() {
//        List<String> templates = Lists.newArrayList();
////        templates.add("code-generator/controller.ftl");
////        templates.add("code-generator/service.ftl");
////        templates.add("code-generator/service_impl.ftl");
////        templates.add("code-generator/dao.ftl");
////        templates.add("code-generator/mybatis.ftl");
////        templates.add("code-generator/model.ftl");
//        // ************************************************************************************
//
////        templates.add("code-generator/mybatis-plus-single/controller.ftl");
////        templates.add("code-generator/mybatis-plus-single/entity.ftl");
////        templates.add("code-generator/mybatis-plus-single/mapper.ftl");
////        templates.add("code-generator/mybatis-plus-single/service.ftl");
////        templates.add("code-generator/mybatis-plus-single/dto.ftl");
////        templates.add("code-generator/mybatis-plus-single/vo.ftl");
////        templates.add("code-generator/mybatis-plus-single/service.impl.ftl");
//        templates.add("code-generator/mybatis-plus-v2/plus-controller.ftl");
//        templates.add("code-generator/mybatis-plus-v2/plus-entity.ftl");
//        templates.add("code-generator/mybatis-plus-v2/plus-mapper.ftl");
//        templates.add("code-generator/mybatis-plus-v2/plus-service.ftl");
//        templates.add("code-generator/mybatis-plus-v2/plus-dto.ftl");
//        templates.add("code-generator/mybatis-plus-v2/plus-vo.ftl");
//        templates.add("code-generator/mybatis-plus-v2/plus-serviceimpl.ftl");
//        return templates;
//    }
//    public static Map<String, Object> getPackages() {
//    	Map<String, Object> datas = new HashMap<String, Object>();
////    	datas.put("packageController", "io.github.wujun728.biz.controller");
////		datas.put("packageService", "io.github.wujun728.biz.service");
////		datas.put("packageServiceImpl", "io.github.wujun728.biz.service.impl");
////		datas.put("packageDao", "io.github.wujun728.biz.dao");
////		datas.put("packageMybatisXML", "io.github.wujun728.biz.model");
////		datas.put("packageModel", "io.github.wujun728.biz.model");
//    	// ************************************************************************************
//    	datas.put("packageController", "io.github.wujun728.biz.controller");
//    	datas.put("packageModel", "io.github.wujun728.biz.entity");
//    	datas.put("packageMapper", "io.github.wujun728.biz.mapper");
//		datas.put("packageService", "io.github.wujun728.biz.service");
//		datas.put("packageDTO", "io.github.wujun728.biz.dto");
//		datas.put("packageVO", "io.github.wujun728.biz.vo");
//		datas.put("packageService", "io.github.wujun728.biz.service.impl");
//    	return datas;
//    }
//
//}
