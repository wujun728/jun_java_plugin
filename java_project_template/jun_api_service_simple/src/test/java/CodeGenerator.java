import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;

/**
 * 代码生成器，根据DatabaseMetaData及数据表名称生成对应的Model、Mapper、Service、Controller简化基础代码逻辑开发。
 * @author Wujun
 */
public class CodeGenerator {
	private static final Logger logger = LoggerFactory.getLogger(CodeGenerator.class);
	
	public static void main(String[] args) throws Exception {
		String tables = "t_admin";
//		String tables = "res_basc,res_basc_arg,api_config";
//		String tables = "git_user";
//		String tables = "app_infoenvt,app_member,app_datasource,app_git_config,git_user,app_deploy_config";
		//GenUtils.isDefaultTemplate = true;
		//GenUtils.genTables(GenUtils.getClassInfo(tables.split(",")),getTemplates());;
//		GenUtils.genCode(Arrays.asList(tables.split(",")),getTemplates());;
	}

	public static List<String> getTemplates() {
		List<String> templates = Lists.newArrayList();
		// ************************************************************************************
		templates.add("code-generator/mybatis-plus-single/controller.java.ftl");
		templates.add("code-generator/mybatis-plus-single/entity.java.ftl");
		templates.add("code-generator/mybatis-plus-single/mapper.java.ftl");
		templates.add("code-generator/mybatis-plus-single/service.java.ftl");
		templates.add("code-generator/mybatis-plus-single/dto.java.ftl");
		templates.add("code-generator/mybatis-plus-single/vo.java.ftl");
		templates.add("code-generator/mybatis-plus-single/service.impl.java.ftl");
//        templates.add("code-generator/mybatis-plus-v2/plus-controller.ftl");
//        templates.add("code-generator/mybatis-plus-v2/plus-entity.ftl");
//        templates.add("code-generator/mybatis-plus-v2/plus-mapper.ftl");
//        templates.add("code-generator/mybatis-plus-v2/plus-service.ftl");
//        templates.add("code-generator/mybatis-plus-v2/plus-dto.ftl");
//        templates.add("code-generator/mybatis-plus-v2/plus-vo.ftl");
//        templates.add("code-generator/mybatis-plus-v2/plus-serviceimpl.ftl");
		return templates;
	}

}
