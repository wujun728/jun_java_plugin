package test;

import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.util.Enumeration;

import org.junit.Test;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springrain.frame.entity.BaseEntity;

public class TestClassUtils {

	//@Test
	public void test1() throws IOException{
		String path = "org/springrain";
		if (path.startsWith("/")) {
			path = path.substring(1);
		}
		ClassLoader cl = Thread.currentThread().getContextClassLoader();
		Enumeration<URL> resourceUrls = (cl != null ? cl.getResources(path) : ClassLoader.getSystemResources(path));
		while (resourceUrls.hasMoreElements()) {
			URL url = resourceUrls.nextElement();
			System.out.println(url);
		}
		
	}
	
	@Test
	public void test2() throws IOException, ClassNotFoundException{

		String basePathName= BaseEntity.class.getName();
		String[] basePaths=basePathName.split("\\.");
		
		String classPath="**/*.class";
		
		if(basePaths==null||basePaths.length<2){
			return;
		}
		
	    String packagePath=basePaths[0]+"/"+basePaths[1]+"/";
	    classPath=packagePath+classPath;
		
		PathMatchingResourcePatternResolver pmrpr=new PathMatchingResourcePatternResolver();
		Resource[] resources = pmrpr.getResources(PathMatchingResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX+classPath);
		
		
		
		for (Resource resource:resources) {
			URI uri = resource.getURI();
			String entityClassName=uri.toString();
			
			entityClassName=entityClassName.substring(entityClassName.lastIndexOf(packagePath), entityClassName.lastIndexOf(".class"));
			entityClassName=entityClassName.replaceAll("/", ".");
			
			Class<?> clazz=Class.forName(entityClassName);
			//ClassUtils.getEntityInfoByClass(clazz);
			System.out.println(clazz.getName());
			
		
		}
		
		
		
	}
	
	
}
