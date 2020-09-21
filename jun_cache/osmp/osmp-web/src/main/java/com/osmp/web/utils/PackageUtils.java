package com.osmp.web.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.util.ClassUtils;
import org.springframework.util.SystemPropertyUtils;


/**
 * 针对包，类，路径的一些操作方法
 * 
 * @author sunjian  
 * @version 1.0 2013-2-26
 */
public class PackageUtils {
	/**
	 * 获取某一个包下的所有包
	 * 如：com.zznode.*,将返回所有com.zznode下的包名
	 * @param packagePattern 通配符
	 * @return
	 */
	public static List<String> getPackages(String packagePattern){
		if(Assert.isEmpty(packagePattern)){
			return null;
		}
		
		//找到第一个通配符*
		int pt = packagePattern.indexOf("*");
		String basePackage = packagePattern.trim();
		if(pt!=-1){
			basePackage = packagePattern.substring(0,pt);
		}
		
		//找到最后一个 . 标示为包结束，获得基础包名
		int pt2 = basePackage.lastIndexOf(".");
		if(pt2!=-1&&pt!=-1){
			basePackage = basePackage.substring(0,pt2);
		}
		
		
		//如果基础包为空则表示不是标准格式的package通配符
		if(Assert.isEmpty(basePackage)){
			return null;
		}
		
		PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
		
		basePackage = ClassUtils.convertClassNameToResourcePath(SystemPropertyUtils.resolvePlaceholders(basePackage));
		
		//基准包可能被分布到多个物理路径中,如org.springframework这样的包在多个jar中存在
		List<String> basePackages = new ArrayList<String>();
		
		try {
			for(Resource rs : resolver.getResources(ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX+basePackage)){
				//获得基准路径
				String str = rs.getURI().getPath();
				basePackages.add(str.substring(0,str.lastIndexOf(basePackage)));
			}
		} catch (IOException e) {
			return null;
		}
		
		//如果没有实际存在的基准包返回null
		if(basePackages.isEmpty()){
			return null;
		}
		
		String packageSearchPath = ClassUtils.convertClassNameToResourcePath(SystemPropertyUtils.resolvePlaceholders(packagePattern));
		packageSearchPath = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX +packageSearchPath + "/**/";
		
		List<String> pkgs = new ArrayList<String>();
		try {
			for(Resource r:resolver.getResources(packageSearchPath)){
				String path = r.getURI().getPath();
				//只取得包路径
				if(path.endsWith("/")){
					
					for(String base :basePackages){
						if(path.contains(base)){
							path = path.replace(base, "").replace("/", ".");
							pkgs.add(path.substring(0,path.length()-1));
							break;
						}
					}
					
				}
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return pkgs;
	}
	
	/**
	 * 获取classpath下文件的绝对路径
	 * @param path
	 * @return
	 */
	public static String getClassPathFile(String path){
		ClassPathResource cpr = new ClassPathResource(path);
		try {
			return cpr.getFile().getAbsolutePath();
		} catch (IOException e) {
			return null;
		}
	}
	
}
