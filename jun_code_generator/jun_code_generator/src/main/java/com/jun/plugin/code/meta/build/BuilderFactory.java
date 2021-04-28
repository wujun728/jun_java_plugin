package com.jun.plugin.code.meta.build;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

import com.jun.plugin.code.meta.util.ConfigUtils;

import freemarker.template.Template;

/****
 * @author Wujun
 * @Description:构建对象的工厂
 *****/
public class BuilderFactory {
	
	public static Logger log = Logger.getLogger(BuilderFactory.class.toString());
	/***
	 * 构建Controller
	 * @param modelMap
	 */
	public static void builder(Map<String, Object> modelMap, // 数据模型
			String templatePath, // 模板路径
			String templateFile, // 模板文件
			String storePath, // 存储路径
			String suffix) { // 生成文件后缀名字
		try {
			// 获取模板对象
			Template template = TemplateUtil.loadTemplate(BuilderFactory.class.getResource(templatePath).getPath(),
					templateFile);

			// 创建文件夹
			String path = ConfigUtils.PROJECT_PATH + storePath.replace(".", "/");
			File file = new File(path);
			if (!file.exists()) {
				file.mkdirs();
			}

			// 创建文件
			TemplateUtil.writer(template, modelMap, path + "/" + modelMap.get("Table") + suffix);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void builder(Map<String, Object> modelMap, String templatePath, String templateFile) {
		String templateName = templateFile.substring(0, templateFile.lastIndexOf("."));
		String storePath = ConfigUtils.PACKAGE_BASE + "." + templateName; // 存储路径
		String suffix = templateName + ".java"; // 生成文件后缀名字
		BuilderFactory.builder(modelMap, templatePath, templateFile, storePath, suffix);
	}

	/***
	 * 构建 
	 * @param modelMap
	 */
	public static void batchBuilder(Map<String, Object> modelMap) {
		// 生成Controller层文件
		BuilderFactory.builder(modelMap, "/template_v1/controller", "Controller.java");
		// 生成Dao层文件
		BuilderFactory.builder(modelMap, "/template_v1/dao", "Mapper.java");
		// 生成Feign层文件
		BuilderFactory.builder(modelMap, "/template_v1/feign", "Feign.java");
		// 生成Pojo层文件
		BuilderFactory.builder(modelMap, "/template_v1/pojo", "Pojo.java");
		// 生成Service层文件
		BuilderFactory.builder(modelMap, "/template_v1/service", "Service.java");
		// 生成ServiceImpl层文件
		BuilderFactory.builder(modelMap, "/template_v1/service/impl", "ServiceImpl.java");

	}
	/***
	 * 构建 Java文件
	 * @param modelMap
	 */
	public static void batchBuilderV2(Map<String, Object> modelMap) {
		List<Map<String,Object>> srcFiles = new ArrayList<Map<String,Object>>();
		getFile(ConfigUtils.TEMPLATE_PATH,srcFiles);
		
		for(int i = 0; i < srcFiles.size(); i++) {
            HashMap<String, Object> m = (HashMap<String, Object>) srcFiles.get(i);
            Set<String> set = m.keySet();
            Iterator<String> it = set.iterator();
            while (it.hasNext()) {
                String key = (String) it.next();
                if((boolean) modelMap.get("Swagger")==true) {
                	if(!key.contains(".json")) {
                		continue;
                	}
                }
                String templateFileName = key;
                String templateFileNameSuffix = key.substring(key.lastIndexOf("."), key.length());
                String templateFileNamePrefix = key.substring(0,key.lastIndexOf("."));
                String templateFilePathAndName = String.valueOf(m.get(key));
                String templateFilePath = templateFilePathAndName.replace("\\"+templateFileName, "");
                String templateFilePathMiddle="";
                if(!templateFilePath.endsWith(ConfigUtils.TEMPLATE_NAME.replace("/", "\\"))) {
                	templateFilePathMiddle=templateFilePath.substring(templateFilePath.lastIndexOf("\\"), templateFilePath.length()).replace("\\", "");
                }
                if(key.contains(".json")) {
                	log.info("templateFilePath="+templateFilePath);;
                	continue;
                }
                try {
					// 获取模板对象
					Template template = TemplateUtil.loadTemplate(templateFilePath, templateFileName);
					String path = null;
					if(templateFileNameSuffix.equalsIgnoreCase(".java")) {
						// 创建文件夹
						path = ConfigUtils.PROJECT_PATH+"/" + ConfigUtils.PACKAGE_BASE.replace(".", "/")+"/"+templateFileNamePrefix.toLowerCase();
					}
					if(templateFileNameSuffix.equalsIgnoreCase(".ftl") ) {
						path = ConfigUtils.PROJECT_PATH+"/" + ConfigUtils.PACKAGE_BASE.replace(".", "/")+"/"+templateFilePathMiddle+"/";
					}
					File file = new File(path);
					if (!file.exists()) {
						file.mkdirs();
					}
					String fileNameNew = templateFileNamePrefix.replace("${className}", String.valueOf(modelMap.get("Table"))).replace("${classNameLower}", String.valueOf(modelMap.get("Table")).toLowerCase()) ;
					// 创建文件
					TemplateUtil.writer(template, modelMap, path + "/" + fileNameNew);
				} catch (Exception e) {
					e.printStackTrace();
				}
            }
            
		}
	}
	
	public static String upperCaseFirstWord(String str) {  
		return str.substring(0, 1).toUpperCase() + str.substring(1);  
	}  
	
  
//	public static void main(String[] args) {
//        // This is the path where the file's name you want to take.
//        String path = "D:\\workspace\\github\\jun_code_generator\\jun_code_helper\\code_generator\\src\\main\\resources\\template_ds";
//        List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
//        getFile(path,list);
//    }
	
	public static void getFile(String path,List<Map<String,Object>> list) {
	        File file = new File(path);
	        File[] array = file.listFiles();
	        for (int i = 0; i < array.length; i++) {
	            if (array[i].isFile()) {
	            	Map<String,Object> map = new HashMap<String,Object>();
	                // only take file name
	                //System.out.println("^^^^^" + array[i].getName());
	                // take file path and name
	                //System.out.println("*****" + array[i].getPath());
	                map.put(array[i].getName(), array[i].getPath());
	                list.add(map);
	            } else if (array[i].isDirectory()) {
	                getFile(array[i].getPath(),list);
	            }
	        }
	    }
}
