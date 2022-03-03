/**   
  * @Title: ClassUtils.java 
  * @package com.sysmanage.common.tools.util 
  * @Description: 
  * @author zhangfeng 13940488705@163.com 
  * @date 2011-8-9 下午04:23:05 
  * @version V1.0   
  */

package com.jun.plugin.poi;



import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

//import org.apache.log4j.Logger;



/**
 * 代码来自velocity
 * Simple utility functions for manipulating classes and resources
 * from the classloader.
 *
 *  @author <a href="mailto:wglass@apache.org">Will Glass-Husain</a>
 *  @version $Id: ClassUtils.java 463298 2006-10-12 16:10:32Z henning $
 */
public class ClassUtil {

    /**
     * Utility class; cannot be instantiated.
     */
    private ClassUtil()
    {
    }

    /**
     * Return the specified class.  Checks the ThreadContext classloader first,
     * then uses the System classloader.  Should replace all calls to
     * <code>Class.forName( claz )</code> (which only calls the System class
     * loader) when the class might be in a different classloader (e.g. in a
     * webapp).
     *
     * @param clazz the name of the class to instantiate
     * @return the requested Class object
     * @throws ClassNotFoundException
     */
    public static Class getClass(String clazz) throws ClassNotFoundException
    {
        /**
         * Use the Thread context classloader if possible
         */
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        if (loader != null)
        {
            try
            {
                return Class.forName(clazz, true, loader);
            }
            catch (ClassNotFoundException E)
            {
                /**
                 * If not found with ThreadContext loader, fall thru to
                 * try System classloader below (works around bug in ant).
                 */
            }
        }
        /**
         * Thread context classloader isn't working out, so use system loader.
         */
        return Class.forName(clazz);
    }

    /**
     * Return a new instance of the given class.  Checks the ThreadContext
     * classloader first, then uses the System classloader.  Should replace all
     * calls to <code>Class.forName( claz ).newInstance()</code> (which only
     * calls the System class loader) when the class might be in a different
     * classloader (e.g. in a webapp).
     *
     * @param clazz the name of the class to instantiate
     * @return an instance of the specified class
     * @throws ClassNotFoundException
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    public static Object getNewInstance(String clazz)
        throws ClassNotFoundException,IllegalAccessException,InstantiationException
    {
        return getClass(clazz).newInstance();
    }

    /**
     * Finds a resource with the given name.  Checks the Thread Context
     * classloader, then uses the System classloader.  Should replace all
     * calls to <code>Class.getResourceAsString</code> when the resource
     * might come from a different classloader.  (e.g. a webapp).
     * @param claz Class to use when getting the System classloader (used if no Thread
     * Context classloader available or fails to get resource).
     * @param name name of the resource
     * @return InputStream for the resource.
     */
    public static InputStream getResourceAsStream(Class claz, String name)
    {
        InputStream result = null;

        /**
         * remove leading slash so path will work with classes in a JAR file
         */
        while (name.startsWith("/"))
        {
            name = name.substring(1);
        }

        ClassLoader classLoader = Thread.currentThread()
                                    .getContextClassLoader();

        if (classLoader == null)
        {
            classLoader = claz.getClassLoader();
            result = classLoader.getResourceAsStream( name );
        }
        else
        {
            result= classLoader.getResourceAsStream( name );

            /**
            * for compatibility with texen / ant tasks, fall back to
            * old method when resource is not found.
            */

            if (result == null)
            {
                classLoader = claz.getClassLoader();
                if (classLoader != null)
                    result = classLoader.getResourceAsStream( name );
            }
        }

        return result;

    }

    /** 
      * @Title: getAllFiledsName 
      * @Description:获得指定Class的声明的所有属性名称集合 
      * @param @param claz
      * @param @return
      * @return List<String>
      * @throws 
      */
    public static List<String> getAllFiledNames(Class claz){
    	List<String> names = new ArrayList<String>();
    	Field[] fields = claz.getDeclaredFields();
    	for(Field field:fields){
    		names.add(field.getName());
    	}
    	return names;
    }
    /*****************************************************************************************/

	/**  
     * ͨ����,���ָ����ĸ���ķ��Ͳ����ʵ������. ��BuyerServiceBean extends DaoSupport<Buyer>  
     *  
     * @param clazz clazz ��Ҫ�������,�������̳з��͸���
     * @param index ���Ͳ�����������,��0��ʼ.  
     * @return ���Ͳ����ʵ������, ���û��ʵ��ParameterizedType�ӿڣ�����֧�ַ��ͣ�����ֱ�ӷ���<code>Object.class</code>
     */  
    @SuppressWarnings("unchecked")
	public static Class getSuperClassGenricType(Class clazz, int index) {    
        Type genType = clazz.getGenericSuperclass();//�õ����͸���  
        //���û��ʵ��ParameterizedType�ӿڣ�����֧�ַ��ͣ�ֱ�ӷ���Object.class   
        if (!(genType instanceof ParameterizedType)) {
            return Object.class;   
        }  
        //���ر�ʾ������ʵ�����Ͳ����Type���������,������ŵĶ��Ƕ�Ӧ���͵�Class, ��BuyerServiceBean extends DaoSupport<Buyer,Contact>�ͷ���Buyer��Contact����   
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();                   
        if (index >= params.length || index < 0) { 
        	 throw new RuntimeException("�����������"+ (index<0 ? "����С��0" : "�����˲��������"));
        }      
        if (!(params[index] instanceof Class)) {
            return Object.class;   
        }   
        return (Class) params[index];
    }
	/**  
     * ͨ����,���ָ����ĸ���ĵ�һ�����Ͳ����ʵ������. ��BuyerServiceBean extends DaoSupport<Buyer>  
     *  
     * @param clazz clazz ��Ҫ�������,�������̳з��͸���
     * @return ���Ͳ����ʵ������, ���û��ʵ��ParameterizedType�ӿڣ�����֧�ַ��ͣ�����ֱ�ӷ���<code>Object.class</code>
     */  
    @SuppressWarnings("unchecked")
	public static Class getSuperClassGenricType(Class clazz) {
    	return getSuperClassGenricType(clazz,0);
    }
	/**  
     * ͨ����,��÷�������ֵ���Ͳ����ʵ������. ��: public Map<String, Buyer> getNames(){}
     *  
     * @param Method method ����
     * @param int index ���Ͳ�����������,��0��ʼ.
     * @return ���Ͳ����ʵ������, ���û��ʵ��ParameterizedType�ӿڣ�����֧�ַ��ͣ�����ֱ�ӷ���<code>Object.class</code>
     */ 
    @SuppressWarnings("unchecked")
	public static Class getMethodGenericReturnType(Method method, int index) {
    	Type returnType = method.getGenericReturnType();
    	if(returnType instanceof ParameterizedType){
    	    ParameterizedType type = (ParameterizedType) returnType;
    	    Type[] typeArguments = type.getActualTypeArguments();
            if (index >= typeArguments.length || index < 0) { 
            	 throw new RuntimeException("�����������"+ (index<0 ? "����С��0" : "�����˲��������"));
            } 
    	    return (Class)typeArguments[index];
    	}
    	return Object.class;
    }
	/**  
     * ͨ����,��÷�������ֵ��һ�����Ͳ����ʵ������. ��: public Map<String, Buyer> getNames(){}
     *  
     * @param Method method ����
     * @return ���Ͳ����ʵ������, ���û��ʵ��ParameterizedType�ӿڣ�����֧�ַ��ͣ�����ֱ�ӷ���<code>Object.class</code>
     */ 
    @SuppressWarnings("unchecked")
	public static Class getMethodGenericReturnType(Method method) {
    	return getMethodGenericReturnType(method, 0);
    }
    
	/**  
     * ͨ����,��÷�����������index�������������з��Ͳ����ʵ������. ��: public void add(Map<String, Buyer> maps, List<String> names){}
     *  
     * @param Method method ����
     * @param int index �ڼ����������
     * @return �������ķ��Ͳ����ʵ�����ͼ���, ���û��ʵ��ParameterizedType�ӿڣ�����֧�ַ��ͣ�����ֱ�ӷ��ؿռ���
     */ 
    @SuppressWarnings("unchecked")
	public static List<Class> getMethodGenericParameterTypes(Method method, int index) {
    	List<Class> results = new ArrayList<Class>();
    	Type[] genericParameterTypes = method.getGenericParameterTypes();
    	if (index >= genericParameterTypes.length ||index < 0) {
             throw new RuntimeException("�����������"+ (index<0 ? "����С��0" : "�����˲��������"));
        } 
    	Type genericParameterType = genericParameterTypes[index];
    	if(genericParameterType instanceof ParameterizedType){
    	     ParameterizedType aType = (ParameterizedType) genericParameterType;
    	     Type[] parameterArgTypes = aType.getActualTypeArguments();
    	     for(Type parameterArgType : parameterArgTypes){
    	         Class parameterArgClass = (Class) parameterArgType;
    	         results.add(parameterArgClass);
    	     }
    	     return results;
    	}
    	return results;
    }
	/**  
     * ͨ����,��÷�����������һ�������������з��Ͳ����ʵ������. ��: public void add(Map<String, Buyer> maps, List<String> names){}
     *  
     * @param Method method ����
     * @return �������ķ��Ͳ����ʵ�����ͼ���, ���û��ʵ��ParameterizedType�ӿڣ�����֧�ַ��ͣ�����ֱ�ӷ��ؿռ���
     */ 
    @SuppressWarnings("unchecked")
	public static List<Class> getMethodGenericParameterTypes(Method method) {
    	return getMethodGenericParameterTypes(method, 0);
    }
	/**  
     * ͨ����,���Field���Ͳ����ʵ������. ��: public Map<String, Buyer> names;
     *  
     * @param Field field �ֶ�
     * @param int index ���Ͳ�����������,��0��ʼ.
     * @return ���Ͳ����ʵ������, ���û��ʵ��ParameterizedType�ӿڣ�����֧�ַ��ͣ�����ֱ�ӷ���<code>Object.class</code>
     */ 
    @SuppressWarnings("unchecked")
	public static Class getFieldGenericType(Field field, int index) {
    	Type genericFieldType = field.getGenericType();
    	
    	if(genericFieldType instanceof ParameterizedType){
    	    ParameterizedType aType = (ParameterizedType) genericFieldType;
    	    Type[] fieldArgTypes = aType.getActualTypeArguments();
    	    if (index >= fieldArgTypes.length || index < 0) { 
    	    	throw new RuntimeException("�����������"+ (index<0 ? "����С��0" : "�����˲��������"));
            } 
    	    return (Class)fieldArgTypes[index];
    	}
    	return Object.class;
    }
	/**  
     * ͨ����,���Field���Ͳ����ʵ������. ��: public Map<String, Buyer> names;
     *  
     * @param Field field �ֶ�
     * @param int index ���Ͳ�����������,��0��ʼ.
     * @return ���Ͳ����ʵ������, ���û��ʵ��ParameterizedType�ӿڣ�����֧�ַ��ͣ�����ֱ�ӷ���<code>Object.class</code>
     */ 
    @SuppressWarnings("unchecked")
	public static Class getFieldGenericType(Field field) {
    	return getFieldGenericType(field, 0);
    }
    /*****************************************************************************************************/
    /*****************************************************************************************************/

    /**
     * return object's setter.
     *
     * @param object
     * @return
     */
    public static Map<String, Method> getSetMethodMap(Object object) {
            return getSetMethodMap(object.getClass());
    }
    
    /**
     * return object's getter.
     *
     * @param object
     * @return
     */
    public static Map<String, Method> getGetMethodMap(Object object) {
            return getGetMethodMap(object.getClass());
    }

    /**
     * return class's setter.
     *
     * @param clazz
     * @return
     */
    public static Map<String, Method> getSetMethodMap(Class<?> clazz) {
            return getMethodMap(clazz).get(setPrefix);
    }

    /**
     * return class's getter.
     *
     * @param clazz
     * @return
     */
    public static Map<String, Method> getGetMethodMap(Class<?> clazz) {
            return getMethodMap(clazz).get(getPrefix);
    }
    
    /**
     * clear static map.
     */
    public static void clearClassMethodMap() {
            classMethodMap.clear();
    }

    /**
     * return class's setter & getter from static map.
     *
     * @param clazz
     * @return
     */
    private static Map<String, Map<String, Method>> getMethodMap(Class<?> clazz) {
            cleanClassMethodMap();
            String classname = clazz.getName();
            if (!classMethodMap.containsKey(classname)) {
                    classMethodMap.put(classname, getMethodMapByClass(clazz));
            }
            return classMethodMap.get(classname);
    }

    /* return class's setter & getter */
    private static Map<String, Map<String, Method>> getMethodMapByClass(Class<?> clazz) {
            Method[] methods = clazz.getMethods();
            int initialCapacity = methods.length / 2;
            Map<String, Method> setMethodMap = new HashMap<String, Method>(initialCapacity);
            Map<String, Method> getMethodMap = new HashMap<String, Method>(initialCapacity + 1);
            for (Method method : methods) {
                    String name = method.getName();
                    if (name.startsWith(setPrefix) && !setPrefix.equals(name)) {
                            if (isStandardSetMethod(method)) {
                                    setMethodMap.put(toStandardFiledName(name), method);
                            }
                    } else if (name.startsWith(getPrefix) && !getPrefix.equals(name)) {
                            if (isStandardGetMethod(method)) {
                                    getMethodMap.put(toStandardFiledName(name), method);
                            }
                    }
            }
            Map<String, Map<String, Method>> methodmap = new HashMap<String, Map<String, Method>>(2);
            methodmap.put(setPrefix, setMethodMap);
            methodmap.put(getPrefix, getMethodMap);
            return methodmap;
    }

    /* return true when method is standard setter */
    private static boolean isStandardSetMethod(Method method) {
            return method.getGenericParameterTypes().length == 1;
    }

    /* return true when method is standard setter */
    private static boolean isStandardGetMethod(Method method) {
            return method.getGenericParameterTypes().length == 0;
    }

    /* return standard setter/getter fieldName */
    private static String toStandardFiledName(String name) {
            String fieldname = name.substring(3);
            return fieldname.substring(0, 1).toLowerCase() + fieldname.substring(1);
    }

    /* 清理bean的methodMap策略 */
    private static void cleanClassMethodMap() {
            ;
    }

    /* setter prefix */
    private final static String setPrefix = "set";

    /* getter prefix */
    private final static String getPrefix = "get";

    /* className getter/setter/prefix fieldName Method */
    private final static Map<String, Map<String, Map<String, Method>>> classMethodMap = new HashMap<String, Map<String, Map<String, Method>>>();
    /*****************************************************************************************************/
    /*****************************************************************************************************/
    /*****************************************************************************************************/
    
    


	/**
	 * 从包package中获取所有的Class
	 * 
	 * @param pack 包名
	 * @param recursive 是否获取子包中的类
	 * @return 获取到类的集合
	 */
	public static Set<Class<?>> getClasses(String pack , boolean recursive) {

		// 第一个class类的集合
		Set<Class<?>> classes = new LinkedHashSet<Class<?>>();
		// 是否循环迭代
		// 获取包的名字 并进行替换
		String packageName = pack;
		String packageDirName = packageName.replace('.', '/');
		// 定义一个枚举的集合 并进行循环来处理这个目录下的things
		Enumeration<URL> dirs;
		try {
			dirs = Thread.currentThread().getContextClassLoader()
					.getResources(packageDirName);
			// 循环迭代下去
			while (dirs.hasMoreElements()) {
				// 获取下一个元素
				URL url = dirs.nextElement();
				// 得到协议的名称
				String protocol = url.getProtocol();
				// 如果是以文件的形式保存在服务器上
				if ("file".equals(protocol)) {
//					System.err.println("file类型的扫描");
					// 获取包的物理路径
					String filePath = URLDecoder.decode(url.getFile(), "UTF-8");
					// 以文件的方式扫描整个包下的文件 并添加到集合中
					findAndAddClassesInPackageByFile(packageName, filePath,
							recursive, classes);
				} else if ("jar".equals(protocol)) {
					// 如果是jar包文件
					// 定义一个JarFile
//					System.err.println("jar类型的扫描");
					JarFile jar;
					try {
						// 获取jar
						jar = ((JarURLConnection) url.openConnection())
								.getJarFile();
						// 从此jar包 得到一个枚举类
						Enumeration<JarEntry> entries = jar.entries();
						// 同样的进行循环迭代
						while (entries.hasMoreElements()) {
							// 获取jar里的一个实体 可以是目录 和一些jar包里的其他文件 如META-INF等文件
							JarEntry entry = entries.nextElement();
							String name = entry.getName();
							// 如果是以/开头的
							if (name.charAt(0) == '/') {
								// 获取后面的字符串
								name = name.substring(1);
							}
							// 如果前半部分和定义的包名相同
							if (name.startsWith(packageDirName)) {
								int idx = name.lastIndexOf('/');
								// 如果以"/"结尾 是一个包
								if (idx != -1) {
									// 获取包名 把"/"替换成"."
									packageName = name.substring(0, idx)
											.replace('/', '.');
								}
								// 如果可以迭代下去 并且是一个包
								if ((idx != -1) || recursive) {
									// 如果是一个.class文件 而且不是目录
									if (name.endsWith(".class")
											&& !entry.isDirectory()) {
										// 去掉后面的".class" 获取真正的类名
										String className = name.substring(
												packageName.length() + 1,
												name.length() - 6);
										try {
											// 添加到classes
											classes.add(Class
													.forName(packageName + '.'
															+ className));
										} catch (ClassNotFoundException e) {
											// log
											// .error("添加用户自定义视图类错误 找不到此类的.class文件");
											e.printStackTrace();
										}
									}
								}
							}
						}
					} catch (IOException e) {
						// log.error("在扫描用户定义视图时从jar包获取文件出错");
						e.printStackTrace();
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return classes;
	}

	/**
	 * 以文件的形式来获取包下的所有Class
	 * 
	 * @param packageName
	 * @param packagePath
	 * @param recursive
	 * @param classes
	 */
	public static void findAndAddClassesInPackageByFile(String packageName,
			String packagePath, final boolean recursive, Set<Class<?>> classes) {
		// 获取此包的目录 建立一个File
		File dir = new File(packagePath);
		// 如果不存在或者 也不是目录就直接返回
		if (!dir.exists() || !dir.isDirectory()) {
			// log.warn("用户定义包名 " + packageName + " 下没有任何文件");
			return;
		}
		// 如果存在 就获取包下的所有文件 包括目录
		File[] dirfiles = dir.listFiles(new FileFilter() {
			// 自定义过滤规则 如果可以循环(包含子目录) 或则是以.class结尾的文件(编译好的java类文件)
			public boolean accept(File file) {
				return (recursive && file.isDirectory())
						|| (file.getName().endsWith(".class"));
			}
		});
		// 循环所有文件
		for (File file : dirfiles) {
			// 如果是目录 则继续扫描
			if (file.isDirectory()) {
				findAndAddClassesInPackageByFile(
						packageName + "." + file.getName(),
						file.getAbsolutePath(), recursive, classes);
			} else {
				// 如果是java类文件 去掉后面的.class 只留下类名
				String className = file.getName().substring(0,
						file.getName().length() - 6);
				try {
					// 添加到集合中去
					// classes.add(Class.forName(packageName + '.' +
					// className));
					// 经过回复同学的提醒，这里用forName有一些不好，会触发static方法，没有使用classLoader的load干净
					classes.add(Thread.currentThread().getContextClassLoader()
							.loadClass(packageName + '.' + className));
				} catch (ClassNotFoundException e) {
					// log.error("添加用户自定义视图类错误 找不到此类的.class文件");
					e.printStackTrace();
				}
			}
		}
	}
	
	
	
    /*****************************************************************************************************/
    /*****************************************************************************************************/
    /*****************************************************************************************************/

//	private static Logger logger = Logger.getLogger(ClassUtil.class);

	public static Object getFieldValue(Object object, String fieldName) {
		Field field = getDeclaredField(object, fieldName);

		if (field == null) {
			throw new IllegalArgumentException("Could not find field [" + fieldName + "] on target [" + object + "]");
		}
		makeAccessible(field);

		Object result = null;
		try {
			result = field.get(object);
		} catch (IllegalAccessException e) {
//			logger.error(e.getMessage());
//			logger.error("不可能抛出的异常{}", e.getMessage());
		}
		return result;
	}

	public static void setFieldValue(Object object, String fieldName, Object value) {
		Field field = getDeclaredField(object, fieldName);

		if (field == null) {
			throw new IllegalArgumentException("Could not find field [" + fieldName + "] on target [" + object + "]");
		}
		makeAccessible(field);
		try {
			field.set(object, value);
		} catch (IllegalAccessException e) {
//			logger.error( e.getMessage());
		}
	}

	public static Object invokeMethod(Object object, String methodName, Class<?>[] parameterTypes, Object[] parameters) throws InvocationTargetException {
		Method method = getDeclaredMethod(object, methodName, parameterTypes);
		if (method == null) {
			throw new IllegalArgumentException("Could not find method [" + methodName + "] on target [" + object + "]");
		}
		method.setAccessible(true);
		try {
			return method.invoke(object, parameters);
		} catch (IllegalAccessException e) {
//			logger.error(e.getMessage());
		}

		return null;
	}

	protected static Field getDeclaredField(Object object, String fieldName) {
		for (Class superClass = object.getClass(); superClass != Object.class; superClass = superClass.getSuperclass())
			try {
				return superClass.getDeclaredField(fieldName);
			} catch (NoSuchFieldException localNoSuchFieldException) {
			}
		return null;
	}

	protected static void makeAccessible(Field field) {
		if ((!(Modifier.isPublic(field.getModifiers()))) || (!(Modifier.isPublic(field.getDeclaringClass().getModifiers()))))
			field.setAccessible(true);
	}

	protected static Method getDeclaredMethod(Object object, String methodName, Class<?>[] parameterTypes) {

		for (Class superClass = object.getClass(); superClass != Object.class; superClass = superClass.getSuperclass())
			try {
				return superClass.getDeclaredMethod(methodName, parameterTypes);
			} catch (NoSuchMethodException localNoSuchMethodException) {
			}
		return null;
	}

	public static <T> Class<T> getSuperClassGenricType1(Class clazz) {
		return getSuperClassGenricType(clazz, 0);
	}

	public static Class getSuperClassGenricType1(Class clazz, int index) {
		Type genType = clazz.getGenericSuperclass();

		if (!(genType instanceof ParameterizedType)) {
//			logger.warn(clazz.getSimpleName() + "'s superclass not ParameterizedType");
			return Object.class;
		}

		Type[] params = ((ParameterizedType) genType).getActualTypeArguments();

		if ((index >= params.length) || (index < 0)) {
//			logger.warn("Index: " + index + ", Size of " + clazz.getSimpleName() + "'s Parameterized Type: " + params.length);
			return Object.class;
		}
		if (!(params[index] instanceof Class)) {
//			logger.warn(clazz.getSimpleName() + " not set the actual class on superclass generic parameter");
			return Object.class;
		}
		return ((Class) params[index]);
	}



	public static void convertToUncheckedException(Exception e) throws IllegalArgumentException {
		if ((e instanceof IllegalAccessException) || (e instanceof IllegalArgumentException) || (e instanceof NoSuchMethodException)) {
			throw new IllegalArgumentException("Refelction Exception.", e);
		}
		throw new IllegalArgumentException(e);
	}
	/*****************************************************************************************************/
	/*****************************************************************************************************/
	/*****************************************************************************************************/
	/*****************************************************************************************************/
	/*****************************************************************************************************/
	/*****************************************************************************************************/
	/*****************************************************************************************************/
	/*****************************************************************************************************/
 
}
