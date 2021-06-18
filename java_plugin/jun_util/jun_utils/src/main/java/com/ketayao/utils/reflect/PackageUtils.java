package com.ketayao.utils.reflect;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * 	
 */
public class PackageUtils {
	
	/**
	 * 得到指定包下面所有的类名
	 * @param packageName
	 * @return
	 */
	public static String[] getAllClassNames(String packageName){
		Class<?> cla = PackageUtils.class;
		String path = packageName.replace('.', '/');
		Enumeration<URL> resources = null;
		try {
			resources = cla.getClassLoader().getResources(path);
		} catch (IOException e) {
		}
		List<File> dirs = new ArrayList<File>();
		while (resources.hasMoreElements()) {
			URL resource = resources.nextElement();
			try {
				dirs.add(new File(URLDecoder.decode(resource.getFile(), "utf-8")));//处理空格等特殊字符
			} catch (UnsupportedEncodingException e) {
			}
		}
		ArrayList<String> classes = new ArrayList<String>();
		for (File directory : dirs) {
			classes.addAll(findClassNames(directory, packageName));
		}
		return classes.toArray(new String[classes.size()]);
	}
	
	/**
	 * 找到类名
	 * @param directory
	 * @param packageName
	 * @return
	 */
	private static List<String> findClassNames(File directory, String packageName) {
		List<String> classes = new ArrayList<String>();
		if (!directory.exists()) {
			return classes;
		}
		File[] files = directory.listFiles();
		for (File file : files) {
			if (file.isDirectory()) {
				classes.addAll(findClassNames(file, packageName + "." + file.getName()));
			} else if (file.getName().endsWith(".class")) {
				String cn = packageName + '.' + file.getName().substring(0, file.getName().length() - 6);
				classes.add(cn);
			}
		}
		return classes;
	}

	/**
	 * 扫描给定包及子包内的所有类
	 * 
	 * @param packageName
	 *            给定的包名
	 * @return The classes
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	public static Class<?>[] getAllClasses(String packageName)
			throws ClassNotFoundException, IOException {
		Class<?> cla = PackageUtils.class;
		String path = packageName.replace('.', '/');
		Enumeration<URL> resources = cla.getClassLoader().getResources(path);
		List<File> dirs = new ArrayList<File>();

		while (resources.hasMoreElements()) {
			URL resource = resources.nextElement();
			dirs.add(new File(resource.getFile()));
		}
		ArrayList<Class<?>> classes = new ArrayList<Class<?>>();
		for (File directory : dirs) {
			classes.addAll(findClasses(directory, packageName));
		}
		return classes.toArray(new Class[classes.size()]);
	}

	/**
	 * 找到制定包内的所有class文件
	 * 
	 * @param directory
	 *            目录名称
	 * @param packageName
	 *            包名称
	 * @return The classes
	 * @throws ClassNotFoundException
	 */
	private static List<Class<?>> findClasses(File directory, String packageName)
			throws ClassNotFoundException {
		List<Class<?>> classes = new ArrayList<Class<?>>();
		if (!directory.exists()) {
			return classes;
		}
		File[] files = directory.listFiles();
		for (File file : files) {
			if (file.isDirectory()) {
				classes.addAll(findClasses(file, packageName + "." + file.getName()));
			} else if (file.getName().endsWith(".class")) {
				classes.add(Class.forName(packageName + '.' + file.getName().substring(0, file.getName().length() - 6)));
			}
		}
		return classes;
	}

	/**
	 * @throws IOException
	 * @throws ClassNotFoundException
	 * 
	 */
	public static void main(String[] args) throws Exception {
		//Class<?>[] clas = PackageUtils.getAllClasses("com");
//		String[] clas = PackageUtils.getClassNames("com");
//		for (String cla : clas) {
//			System.out.println(cla);
//		}
		
		String path = "E:/Program%20Files/apache-tomcat-6.0.33/webapps/loveJ4Fensy/WEB-INF/classes/com/ketayao/action/";
		path = URLDecoder.decode(path, "UTF-8");
		System.out.println(path);
	}

}
