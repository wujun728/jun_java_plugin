/**   
  * @Title: ClassUtils.java 
  * @package com.sysmanage.common.tools.util 
  * @Description: 
  * @author zhangfeng 13940488705@163.com 
  * @date 2011-8-9 下午04:23:05 
  * @version V1.0   
  */

package com.jun.plugin.utils.reflect;



import java.beans.PropertyDescriptor;
import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
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
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConversionException;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.Converter;
import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.DynaProperty;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.beanutils.PropertyUtilsBean;
import org.apache.commons.beanutils.locale.converters.DateLocaleConverter;

import com.jun.plugin.utils.StringUtil;

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
	public static Set<Class<?>> getClasses2(String pack , boolean recursive) {

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
	


	/**
	 * 将源对象中的值覆盖到目标对象中，仅覆盖源对象中不为NULL值的属性
	 * 
	 * @param dest
	 *            目标对象，标准的JavaBean
	 * @param orig
	 *            源对象，可为Map、标准的JavaBean
	 * @throws BusinessException
	 */
	@SuppressWarnings("rawtypes")
	public static void applyIf(Object dest, Object orig) throws Exception {
		try {
			if (orig instanceof Map) {
				Iterator names = ((Map) orig).keySet().iterator();
				while (names.hasNext()) {
					String name = (String) names.next();
					if (PropertyUtils.isWriteable(dest, name)) {
						Object value = ((Map) orig).get(name);
						if (value != null) {
							PropertyUtils.setSimpleProperty(dest, name, value);
						}
					}
				}
			} else {
				java.lang.reflect.Field[] fields = orig.getClass().getDeclaredFields();
				for (int i = 0; i < fields.length; i++) {
					String name = fields[i].getName();
					if (PropertyUtils.isReadable(orig, name) && PropertyUtils.isWriteable(dest, name)) {
						Object value = PropertyUtils.getSimpleProperty(orig, name);
						if (value != null) {
							PropertyUtils.setSimpleProperty(dest, name, value);
						}
					}
				}
			}
		} catch (Exception e) {
			throw new Exception("将源对象中的值覆盖到目标对象中，仅覆盖源对象中不为NULL值的属性", e);
		}
	}

	/**
	 * 获取当前类声明的private/protected变量
	 */
	public static Object getPrivateProperty(Object object, String propertyName)
			throws IllegalAccessException, NoSuchFieldException {
		Field field = object.getClass().getDeclaredField(propertyName);
		field.setAccessible(true);
		return field.get(object);
	}

	/**
	 * 设置当前类声明的private/protected变量
	 */
	public static void setPrivateProperty(Object object, String propertyName, Object newValue)
			throws IllegalAccessException, NoSuchFieldException {
		Field field = object.getClass().getDeclaredField(propertyName);
		field.setAccessible(true);
		field.set(object, newValue);
	}

	/**
	 * 调用当前类声明的private/protected函数
	 */
	public static Object invokePrivateMethod(Object object, String methodName, Object[] params)
			throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
		Class[] types = new Class[params.length];
		for (int i = 0; i < params.length; i++) {
			types[i] = params[i].getClass();
		}
		Method method = object.getClass().getDeclaredMethod(methodName, types);
		method.setAccessible(true);
		return method.invoke(object, params);
	}

	/**
	 * 调用当前类声明的private/protected函数
	 */
	public static Object invokePrivateMethod(Object object, String methodName, Object param)
			throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
		return invokePrivateMethod(object, methodName, new Object[] { param });
	}

	/**
	 * @param T
	 *            t
	 * @param param
	 * @return
	 */
	public static <T> T populate(T t, Map<String, ? extends Object> param) {
		try {
			org.apache.commons.beanutils.BeanUtils.populate(t, param);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return t;
	}

	/**
	 * @param cls
	 * @param param
	 * @return
	 */
	public static <T> T populate(Class<T> cls, Map<String, ? extends Object> param) {
		T t = null;
		try {
			t = populate(cls.newInstance(), param);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return t;
	}

	/**
	 * @param map
	 * @param clazz
	 * @return
	 */
	public static <T> T toBean(Map map, Class<T> clazz) {
		try {
			T bean = clazz.newInstance();
			BeanUtils.populate(bean, map);
			return bean;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * @Title: describe @Description: 获得同时有get和set的field和value。 @param bean
	 *         获得bean对象 @return Map 返回属性列表 @throws
	 */
	@SuppressWarnings("unchecked")
	public static Map describe(Object bean) {
		Map des = new HashMap();
		PropertyDescriptor desor[] = PropertyUtils.getPropertyDescriptors(bean);
		String name = null;
		for (int i = 0; i < desor.length; i++) {
			if (desor[i].getReadMethod() != null && desor[i].getWriteMethod() != null) {
				name = desor[i].getName();
				try {
					des.put(name, PropertyUtils.getProperty(bean, name));
				} catch (Exception e) {
					throw new RuntimeException("属性不存在：" + name);
				}
			}
		}
		return des;
	}

	public static void setSimpleProperty(Object bean, String name, Object value) {
		try {
			PropertyUtils.setSimpleProperty(bean, name, value);
		} catch (Exception e) {
			throw new RuntimeException("属性不存在：" + name);
		}
	}

	public static Object setSimpleProperty(Object bean, String name) {
		try {
			return PropertyUtils.getSimpleProperty(bean, name);
		} catch (Exception e) {
			throw new RuntimeException("属性不存在：" + name);
		}
	}


 

	/**
	 * @Title: getDeclaredField @Description: 循环向上转型,获取类的DeclaredField. @param clazz
	 *         类名称 @param fieldName 字段名称 @param @throws NoSuchFieldException @return
	 *         Field @throws
	 */
	@SuppressWarnings("unchecked")
	public static Field getDeclaredField(Class clazz, String fieldName) throws NoSuchFieldException {
		for (Class superClass = clazz; superClass != Object.class; superClass = superClass.getSuperclass()) {
			try {
				return superClass.getDeclaredField(fieldName);
			} catch (NoSuchFieldException e) {
				// Field不在当前类定义,继续向上转型
			}
		}
		throw new NoSuchFieldException("No such field: " + clazz.getName() + '.' + fieldName);
	}

	/**
	 * 将源对象中的值覆盖到目标对象中，仅覆盖源对象中不为NULL值的属性
	 * 
	 * @param orig
	 *            源对象，标准的JavaBean
	 * @param dest
	 *            排除检查的属性，Map
	 * 
	 * @throws BusinessException
	 */
	@SuppressWarnings("rawtypes")
	public static boolean checkObjProperty(Object orig, Map dest) throws Exception {
		try {
			java.lang.reflect.Field[] fields = orig.getClass().getDeclaredFields();
			for (int i = 0; i < fields.length; i++) {
				String name = fields[i].getName();
				if (!dest.containsKey(name)) {
					if (PropertyUtils.isReadable(orig, name)) {
						Object value = PropertyUtils.getSimpleProperty(orig, name);
						if (value == null) {
							return true;
						}
					}
				}
			}
			return false;
		} catch (Exception e) {
			throw new Exception("将源对象中的值覆盖到目标对象中，仅覆盖源对象中不为NULL值的属性", e);
		}
	}

	/**
	 * 将属性样式字符串转成驼峰样式字符串 (例:branchNo -> branch_no)
	 * 
	 * @param inputString
	 * @return
	 */
	private static final char SEPARATOR = '_';
	public static String toUnderlineString(String inputString) {
		if (inputString == null)
			return null;
		StringBuilder sb = new StringBuilder();
		boolean upperCase = false;
		for (int i = 0; i < inputString.length() - 1; i++) {
			char c = inputString.charAt(i);
			boolean nextUpperCase = true;
			if (i < inputString.length()) {
				nextUpperCase = Character.isUpperCase(inputString.charAt(i + 1));
			}
			if ((i >= 0) && Character.isUpperCase(c)) {
				if (!upperCase || !nextUpperCase) {
					if (i > 0)
						sb.append(SEPARATOR);
				}
				upperCase = true;
			} else {
				upperCase = false;
			}
			sb.append(Character.toLowerCase(c));
		}
		return sb.toString();
	}

	/**
	 * 将驼峰字段转成属性字符串 (例:branch_no -> branchNo )
	 * 
	 * @param inputString
	 *            输入字符串
	 * @return
	 */
	public static String toCamelCaseString(String inputString) {
		return toCamelCaseString(inputString, false);
	}

	/**
	 * 将驼峰字段转成属性字符串 (例:branch_no -> branchNo )
	 * 
	 * @param inputString
	 *            输入字符串
	 * @param firstCharacterUppercase
	 *            是否首字母大写
	 * @return
	 */
	public static String toCamelCaseString(String inputString, boolean firstCharacterUppercase) {
		if (inputString == null)
			return null;
		StringBuilder sb = new StringBuilder();
		boolean nextUpperCase = false;
		for (int i = 0; i < inputString.length(); i++) {
			char c = inputString.charAt(i);
			switch (c) {
			case '_':
			case '-':
			case '@':
			case '$':
			case '#':
			case ' ':
			case '/':
			case '&':
				if (sb.length() > 0) {
					nextUpperCase = true;
				}
				break;
			default:
				if (nextUpperCase) {
					sb.append(Character.toUpperCase(c));
					nextUpperCase = false;
				} else {
					sb.append(Character.toLowerCase(c));
				}
				break;
			}
		}
		if (firstCharacterUppercase) {
			sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));
		}
		return sb.toString();
	}

	/**
	 * 得到标准字段名称
	 * 
	 * @param inputString
	 *            输入字符串
	 * @return
	 */
	public static String getValidPropertyName(String inputString) {
		String answer;
		if (inputString == null) {
			answer = null;
		} else if (inputString.length() < 2) {
			answer = inputString.toLowerCase(Locale.US);
		} else {
			if (Character.isUpperCase(inputString.charAt(0)) && !Character.isUpperCase(inputString.charAt(1))) {
				answer = inputString.substring(0, 1).toLowerCase(Locale.US) + inputString.substring(1);
			} else {
				answer = inputString;
			}
		}
		return answer;
	}

	/**
	 * 将属性转换成标准set方法名字符串
	 * 
	 * @param property
	 * @return
	 */
	public static String getSetterMethodName(String property) {
		StringBuilder sb = new StringBuilder();
		sb.append(property);
		if (Character.isLowerCase(sb.charAt(0))) {
			if (sb.length() == 1 || !Character.isUpperCase(sb.charAt(1))) {
				sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));
			}
		}
		sb.insert(0, "set");
		return sb.toString();
	}

	/**
	 * 将属性转换成标准get方法名字符串
	 * 
	 * @param property
	 * @return
	 */
	public static String getGetterMethodName(String property) {
		StringBuilder sb = new StringBuilder();
		sb.append(property);
		if (Character.isLowerCase(sb.charAt(0))) {
			if (sb.length() == 1 || !Character.isUpperCase(sb.charAt(1))) {
				sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));
			}
		}
		sb.insert(0, "get");
		return sb.toString();
	}

	/**
	 * 对象转Map
	 * 
	 * @param object
	 *            目标对象
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 */
	@SuppressWarnings("unchecked")
	public static Map toMap(Object object)
			throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		return ClassUtil.describe(object);
	}

	/**
	 * 转换为Collection>
	 * 
	 * @param collection
	 *            待转换对象集合
	 * @return 转换后的Collection>
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 */
	public static Collection toMapList(Collection collection)
			throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		List retList = new ArrayList();
		if (collection != null && !collection.isEmpty()) {
			for (Object object : collection) {
				Map map = toMap(object);
				retList.add(map);
			}
		}
		return retList;
	}

	private static void convert(Object dest, Object orig) throws IllegalAccessException, InvocationTargetException {

		// Validate existence of the specified beans
		if (dest == null) {
			throw new IllegalArgumentException("No destination bean specified");
		}
		if (orig == null) {
			throw new IllegalArgumentException("No origin bean specified");
		}

		// Copy the properties, converting as necessary
		if (orig instanceof DynaBean) {
			DynaProperty origDescriptors[] = ((DynaBean) orig).getDynaClass().getDynaProperties();
			for (int i = 0; i < origDescriptors.length; i++) {
				String name = origDescriptors[i].getName();
				if (PropertyUtils.isWriteable(dest, name)) {
					Object value = ((DynaBean) orig).get(name);
					try {
						BeanUtils.copyProperty(dest, name, value);
					} catch (Exception e) {
						; // Should not happen
					}

				}
			}
		} else if (orig instanceof Map) {
			Iterator names = ((Map) orig).keySet().iterator();
			while (names.hasNext()) {
				String name = (String) names.next();
				if (PropertyUtils.isWriteable(dest, name)) {
					Object value = ((Map) orig).get(name);
					try {
						BeanUtils.copyProperty(dest, name, value);
					} catch (Exception e) {
						; // Should not happen
					}

				}
			}
		} else
		/* if (orig is a standard JavaBean) */
		{
			PropertyDescriptor origDescriptors[] = PropertyUtils.getPropertyDescriptors(orig);
			for (int i = 0; i < origDescriptors.length; i++) {
				String name = origDescriptors[i].getName();
				// String type =
				// origDescriptors[i].getPropertyType().toString();
				if ("class".equals(name)) {
					continue; // No point in trying to set an object's class
				}
				if (PropertyUtils.isReadable(orig, name) && PropertyUtils.isWriteable(dest, name)) {
					try {
						Object value = PropertyUtils.getSimpleProperty(orig, name);
						BeanUtils.copyProperty(dest, name, value);
					} catch (java.lang.IllegalArgumentException ie) {
						; // Should not happen
					} catch (Exception e) {
						; // Should not happen
					}

				}
			}
		}

	}

	/**
	 * 对象拷贝 数据对象空值不拷贝到目标对象
	 * 
	 * @param dataObject
	 * @param toObject
	 * @throws NoSuchMethodException
	 *             copy
	 */
	public static void copyBeanNotNull2Bean(Object databean, Object tobean) throws Exception {
		PropertyDescriptor origDescriptors[] = PropertyUtils.getPropertyDescriptors(databean);
		for (int i = 0; i < origDescriptors.length; i++) {
			String name = origDescriptors[i].getName();
			// String type = origDescriptors[i].getPropertyType().toString();
			if ("class".equals(name)) {
				continue; // No point in trying to set an object's class
			}
			if (PropertyUtils.isReadable(databean, name) && PropertyUtils.isWriteable(tobean, name)) {
				try {
					Object value = PropertyUtils.getSimpleProperty(databean, name);
					if (value != null) {
						BeanUtils.copyProperty(tobean, name, value);
					}
				} catch (java.lang.IllegalArgumentException ie) {
					; // Should not happen
				} catch (Exception e) {
					; // Should not happen
				}

			}
		}
	}

	/**
	 * 把orig和dest相同属性的value复制到dest中
	 * 
	 * @param dest
	 * @param orig
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public static void copyBean2Bean(Object dest, Object orig) throws Exception {
		convert(dest, orig);
	}

	public static void copyBean2Map(Map map, Object bean) {
		PropertyDescriptor[] pds = PropertyUtils.getPropertyDescriptors(bean);
		for (int i = 0; i < pds.length; i++) {
			PropertyDescriptor pd = pds[i];
			String propname = pd.getName();
			try {
				Object propvalue = PropertyUtils.getSimpleProperty(bean, propname);
				map.put(propname, propvalue);
			} catch (IllegalAccessException e) {
				// e.printStackTrace();
			} catch (InvocationTargetException e) {
				// e.printStackTrace();
			} catch (NoSuchMethodException e) {
				// e.printStackTrace();
			}
		}
	}

	/**
	 * 将Map内的key与Bean中属性相同的内容复制到BEAN中
	 * 
	 * @param bean
	 *            Object
	 * @param properties
	 *            Map
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public static void copyMap2Bean(Object bean, Map properties)
			throws IllegalAccessException, InvocationTargetException {
		// Do nothing unless both arguments have been specified
		if ((bean == null) || (properties == null)) {
			return;
		}
		// Loop through the property name/value pairs to be set
		Iterator names = properties.keySet().iterator();
		while (names.hasNext()) {
			String name = (String) names.next();
			// Identify the property name and value(s) to be assigned
			if (name == null) {
				continue;
			}
			Object value = properties.get(name);
			try {
				Class clazz = PropertyUtils.getPropertyType(bean, name);
				if (null == clazz) {
					continue;
				}
				String className = clazz.getName();
				if (className.equalsIgnoreCase("java.sql.Timestamp")) {
					if (value == null || value.equals("")) {
						continue;
					}
				}
				BeanUtils.setProperty(bean, name, value);
			} catch (NoSuchMethodException e) {
				continue;
			}
		}
	}

	/**
	 * 自动转Map key值大写 将Map内的key与Bean中属性相同的内容复制到BEAN中
	 * 
	 * @param bean
	 *            Object
	 * @param properties
	 *            Map
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public static void copyMap2Bean_Nobig(Object bean, Map properties)
			throws IllegalAccessException, InvocationTargetException {
		// Do nothing unless both arguments have been specified
		if ((bean == null) || (properties == null)) {
			return;
		}
		// Loop through the property name/value pairs to be set
		Iterator names = properties.keySet().iterator();
		while (names.hasNext()) {
			String name = (String) names.next();
			// Identify the property name and value(s) to be assigned
			if (name == null) {
				continue;
			}
			Object value = properties.get(name);
			name = name.toLowerCase();
			try {
				Class clazz = PropertyUtils.getPropertyType(bean, name);
				if (null == clazz) {
					continue;
				}
				String className = clazz.getName();
				if (className.equalsIgnoreCase("java.sql.Timestamp")) {
					if (value == null || value.equals("")) {
						continue;
					}
				}
				BeanUtils.setProperty(bean, name, value);
			} catch (NoSuchMethodException e) {
				continue;
			}
		}
	}

	/**
	 * Map内的key与Bean中属性相同的内容复制到BEAN中 对于存在空值的取默认值
	 * 
	 * @param bean
	 *            Object
	 * @param properties
	 *            Map
	 * @param defaultValue
	 *            String
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public static void copyMap2Bean(Object bean, Map properties, String defaultValue)
			throws IllegalAccessException, InvocationTargetException {
		// Do nothing unless both arguments have been specified
		if ((bean == null) || (properties == null)) {
			return;
		}
		// Loop through the property name/value pairs to be set
		Iterator names = properties.keySet().iterator();
		while (names.hasNext()) {
			String name = (String) names.next();
			// Identify the property name and value(s) to be assigned
			if (name == null) {
				continue;
			}
			Object value = properties.get(name);
			try {
				Class clazz = PropertyUtils.getPropertyType(bean, name);
				if (null == clazz) {
					continue;
				}
				String className = clazz.getName();
				if (className.equalsIgnoreCase("java.sql.Timestamp")) {
					if (value == null || value.equals("")) {
						continue;
					}
				}
				if (className.equalsIgnoreCase("java.lang.String")) {
					if (value == null) {
						value = defaultValue;
					}
				}
				BeanUtils.setProperty(bean, name, value);
			} catch (NoSuchMethodException e) {
				continue;
			}
		}
	}

	/**
	 * 处理object以下属性中,存在null值
	 * 
	 * @param obj
	 * @return
	 */
	public static Object parseNull(Object obj) {
		try {
			PropertyUtilsBean b = new PropertyUtilsBean();
			Map map = b.describe(obj);
			Iterator it = map.keySet().iterator();
			while (it.hasNext()) {
				String key = it.next().toString();
				if (map.get(key) == null) {
					if (b.getPropertyType(obj, key) == String.class)
						b.setProperty(obj, key.toString(), "");
					else if (b.getPropertyType(obj, key) == Integer.class || b.getPropertyType(obj, key) == int.class)
						b.setProperty(obj, key.toString(), 0);
					else if (b.getPropertyType(obj, key) == Long.class || b.getPropertyType(obj, key) == long.class)
						b.setProperty(obj, key.toString(), 0l);
					else if (b.getPropertyType(obj, key) == Double.class || b.getPropertyType(obj, key) == double.class)
						b.setProperty(obj, key.toString(), 0.00);
					else
						b.setProperty(obj, key.toString(), "");
				}
			}
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return obj;
	}

	public static void main(String[] args) throws IllegalAccessException {
		System.out.println(ClassUtil.toUnderlineString("ISOCertifiedStaff"));
		System.out.println(ClassUtil.getValidPropertyName("CertifiedStaff"));
		System.out.println(ClassUtil.getSetterMethodName("userID"));
		System.out.println(ClassUtil.getGetterMethodName("userID"));
		System.out.println(ClassUtil.toCamelCaseString("iso_certified_staff", true));
		System.out.println(ClassUtil.getValidPropertyName("certified_staff"));
		System.out.println(ClassUtil.toCamelCaseString("site_Id"));
		ClassUtil p = new ClassUtil();
		// System.out.println("Web Class Path = " + p.getWebClassesPath());
		// System.out.println("WEB-INF Path = " + p.getWebInfPath());
		// System.out.println("WebRoot Path = " + p.getWebRoot());

		// SampleBean bean1 = new SampleBean();
		// bean1.setName("rensanning");
		// bean1.setAge(31);
		//
		// String name = BeanUtils.getProperty(bean1, "name");
		// String age = BeanUtils.getProperty(bean1, "age");
		//
		// System.out.println(name);
		// System.out.println(age);
		//
		// // =======setProperty
		// SampleBean bean2 = new SampleBean();
		// BeanUtils.setProperty(bean2, "name", "rensanning");
		// BeanUtils.setProperty(bean2, "age", 31);
		//
		// System.out.println(bean2.getName());
		// System.out.println(bean2.getAge());
		//
		// // =======cloneBean
		// SampleBean bean3 = new SampleBean();
		// bean3.setName("rensanning");
		// bean3.setAge(31);
		//
		// SampleBean clone = (SampleBean) BeanUtils.cloneBean(bean3);
		//
		// System.out.println(clone.getName());
		// System.out.println(clone.getAge());
		//
		// // =======describe
		// SampleBean bean4 = new SampleBean();
		// bean4.setName("rensanning");
		// bean4.setAge(31);
		//
		// @SuppressWarnings("unchecked")
		// Map<String, String> map4 = BeanUtils.describe(bean4);
		//
		// System.out.println(map4.get("name"));
		// System.out.println(map4.get("age"));
		//
		// // =======populate
		// SampleBean bean5 = new SampleBean();
		//
		// Map<String, String> map5 = new HashMap<String, String>();
		// map5.put("name", "rensanning");
		// map5.put("age", "31");
		//
		// BeanUtils.populate(bean5, map5);
		//
		// System.out.println(bean5.getName());
		// System.out.println(bean5.getAge());
		//
		// // =======getArrayProperty
		// SampleBean bean6 = new SampleBean();
		// bean6.setArray(new String[] { "a", "b", "c" });
		// List<String> list0 = new ArrayList<String>();
		// list0.add("d");
		// list0.add("e");
		// list0.add("f");
		// bean6.setList(list0);
		//
		// String[] array = BeanUtils.getArrayProperty(bean6, "array");
		//
		// System.out.println(array.length);// 3
		// System.out.println(array[0]);// "a"
		// System.out.println(array[1]);// "b"
		// System.out.println(array[2]);// "c"
		//
		// String[] list = BeanUtils.getArrayProperty(bean6, "list");
		// System.out.println(list.length);// 3
		// System.out.println(list[0]);// "d"
		// System.out.println(list[1]);// "e"
		// System.out.println(list[2]);// "f"
		//
		// System.out.println(BeanUtils.getProperty(bean6, "array[1]"));// "b"
		// System.out.println(BeanUtils.getIndexedProperty(bean6, "array",
		// 2));// "c"
		//
		// // =======getMappedProperty
		// SampleBean bean7 = new SampleBean();
		// Map<String, String> map = new HashMap<String, String>();
		// map.put("key1", "value1");
		// map.put("key2", "value2");
		// bean7.setMap(map);
		//
		// String value1 = BeanUtils.getMappedProperty(bean7, "map", "key1");
		// System.out.println(value1);// "value1"
		//
		// String value2 = BeanUtils.getMappedProperty(bean7, "map", "key2");
		// System.out.println(value2);// "value2"
		//
		// System.out.println(BeanUtils.getProperty(bean7, "map.key1"));//
		// "value1"
		// System.out.println(BeanUtils.getProperty(bean7, "map.key2"));//
		// "value2"
		//
		// // =======getNestedProperty
		// SampleBean bean = new SampleBean();
		// NestedBean nestedBean = new NestedBean();
		// nestedBean.setNestedProperty("xxx");
		// bean.setNestedBean(nestedBean);
		//
		// String value = BeanUtils.getNestedProperty(bean,
		// "nestedBean.nestedProperty");
		// System.out.println(value);// "xxx"
		//
		// System.out.println(BeanUtils.getProperty(bean,
		// "nestedBean.nestedProperty"));// "xxx"
		//
		// // =======testURLConversion
		// SampleBean bean8 = new SampleBean();
		//
		// BeanUtils.setProperty(bean8, "url", "http://www.google.com/");
		//
		// URL url = bean8.getUrl();
		// System.out.println(url.getProtocol());// "http"
		// System.out.println(url.getHost());// "www.google.com"
		// System.out.println(url.getPath());// "/"
		//
		// // =======testDateConversion
		// SampleBean bean9 = new SampleBean();
		//
		// DateConverter converter = new DateConverter();
		// converter.setPattern("yyyy/MM/dd HH:mm:ss");
		//
		// ConvertUtils.register(converter, Date.class);
		// ConvertUtils.register(converter, String.class);
		//
		// BeanUtils.setProperty(bean9, "date", "2010/12/19 23:40:00");
		//
		// String value9 = BeanUtils.getProperty(bean9, "date");
		// System.out.println(value9);// "2010/12/19 23:40:00"
	}

	// @Test
	public void Test_test13() throws Exception {
		String birthday = "1983-12-1";
		// 为了让日期赋值到bean的birthday属性上，需要在 beanUtils中 注册一个日期转换器，以便在需要转换时自动调用。可查
		// BeanUtil 的文档。
		// ConvertUtils.register(converter, clazz);
		ConvertUtils.register(new Converter() { // 下面是自定义的类型转换器。
			public Object convert(Class type, Object value) {
				if (value == null)
					return null;
				if (!(value instanceof String)) {
					throw new ConversionException("只支持String类型的转换");// 可查文档，此异常父类是RunnableTimeException
				}
				String str = (String) value;
				if (str.trim().equals(""))
					return null;
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
				try {
					return df.parse(str); // 此语句将会抛出异常，但是由于
					// 子类不能抛出比父类更多的异常，所以只能catch
				} catch (ParseException e) {
					throw new RuntimeException(e); // 抛出运行时异常并停止程序的执行，以便通知上层异常信息，
					// 并建议其修改代码，以期提高代码的严谨性。
				}
			}
		}, Date.class);
		// Person p = new Person();
		// BeanUtils.setProperty(p, "birthday", birthday);// 因上面注册了“字符串--日起类型”
		// 的类型转换器，故此就不会报错异常了。
		// System.out.println(p.getBirthday());//pw
		// System.out.println("___" + BeanUtils.getProperty(p, "birthday"));
		/*
		 * 关于转换器：BeanUtils已经为 Converter接口 编写很多的转换器， 可查阅文档中All Known Implementing
		 * Classes:中所提示的各种框架自带转换器。 注：其中DateLocalConverter转换器，是针对中文样式的，但是里面有Bug：当输入的字符串类型
		 * 的日期参数值为" "时，就会抛出异常， 可考虑在编码时避免传入" " 值，就可以直接使用该转换器 Demo：
		 * ConvertUtils.register(new DateLocalConverter(), Date.class); 仅需要 这一句代码
		 * 就可以实现中文样式的 “字符串--日期” 的类型转换器：
		 */
	}

	// @Test
	public void Test_test15() throws Exception {
		Map map = new HashMap();
		map.put("name", "aaa");
		map.put("password", "123");
		map.put("brithday", "1980-09-09");
		ConvertUtils.register(new DateLocaleConverter(), Date.class);
		// Person p = new Person();
		// 用map集合填充bean属性,map关键字和bean属性要一致
		// BeanUtil.populate(p, map);
	}

	protected static Object convertToDate(Class type, Object value, String pattern) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		if (value instanceof String) {
			try {
				if (StringUtil.isEmpty(value.toString())) {
					return null;
				}
				java.util.Date date = sdf.parse(String.valueOf(value));
				if (type.equals(Timestamp.class)) {
					return new Timestamp(date.getTime());
				}
				return date;
			} catch (Exception pe) {
				return null;
			}
		} else if (value instanceof Date) {
			return value;
		}
		throw new ConversionException("不能转换 " + value.getClass().getName() + " 为 " + type.getName());
	}

	protected static Object convertToString(Class type, Object value) {
		if (value instanceof Date) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			if (value instanceof Timestamp) {
				sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			}
			try {
				return sdf.format(value);
			} catch (Exception e) {
				throw new ConversionException("日期转换为字符串时出错！");
			}
		} else {
			return value.toString();
		}
	}

	// 复制所有属性值从originBean到destinationBean中；
	public static void copyBean(Object src, Object dest) {
		ConvertUtils.register(new Converter() {// 注册自定义转换器；
			@SuppressWarnings("rawtypes")
			public Object convert(Class type, Object value) {
				if (value == null) {
					return null;
				} else if (type == Timestamp.class) {
					return convertToDate(type, value, "yyyy-MM-dd HH:mm:ss");
				} else if (type == Date.class) {
					return convertToDate(type, value, "yyyy-MM-dd");
				} else if (type == String.class) {
					return convertToString(type, value);
				} else {
					throw new ConversionException("不能转换 " + value.getClass().getName() + " 为 " + type.getName());
				}
			}
		}, java.util.Date.class);
		// ConvertUtils.register(new DateLocaleConverter(),
		// java.util.Date.class);
		// BeanUtils.populate(b, map);

		try {
			BeanUtils.copyProperties(dest, src);// 复制所有两个bean都有的属性；
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		} catch (InvocationTargetException e) {
			throw new RuntimeException(e);
		}
	}

	// 复制所有属性值从originBean到destinationBean中；
	@SuppressWarnings("rawtypes")
	public static void MapToBean(Map map, Object dest) {
		ConvertUtils.register(new Converter() {// 注册自定义转换器；
			public Object convert(Class type, Object value) {
				if (value == null) {
					return null;
				} else if (type == Timestamp.class) {
					return convertToDate(type, value, "yyyy-MM-dd HH:mm:ss");
				} else if (type == Date.class) {
					return convertToDate(type, value, "yyyy-MM-dd");
				} else if (type == String.class) {
					return convertToString(type, value);
				} else {
					throw new ConversionException("不能转换 " + value.getClass().getName() + " 为 " + type.getName());
				}
			}
		}, java.util.Date.class);
		// ConvertUtils.register(new DateLocaleConverter(),
		// java.util.Date.class);

		try {
			BeanUtils.populate(dest, map);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		} catch (InvocationTargetException e) {
			throw new RuntimeException(e);
		}
	}

	
	

 

	/**
	 * 转换为Collection,同时为字段做驼峰转换<Map<K, V>>
	 * 
	 * @param collection
	 *            待转换对象集合
	 * @return 转换后的Collection<Map<K, V>>
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 */
	public static <T> Collection<Map<String, String>> toMapListForFlat(Collection<T> collection) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		List<Map<String, String>> retList = new ArrayList<Map<String, String>>();
		if (collection != null && !collection.isEmpty()) {
			for (Object object : collection) {
				Map<String, String> map = toMapForFlat(object);
				retList.add(map);
			}
		}
		return retList;
	}

	/**
	 * 转换成Map并提供字段命名驼峰转平行
	 * 
	 * @param clazz
	 *            目标对象所在类
	 * @param object
	 *            目标对象
	 * @param map
	 *            待转换Map
	 * @throws NoSuchMethodException
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 */
	public static Map<String, String> toMapForFlat(Object object) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		Map<String, String> map = toMap(object);
		return toUnderlineStringMap(map);
	}

	 

	/**
	 * 将Map的Keys转译成下划线格式的<br>
	 * (例:branchNo -> branch_no)<br>
	 * 
	 * @param map
	 *            待转换Map
	 * @return
	 */
	public static <V> Map<String, V> toUnderlineStringMap(Map<String, V> map) {
		Map<String, V> newMap = new HashMap<String, V>();
		for (String key : map.keySet()) {
			newMap.put(toUnderlineString(key), map.get(key));
		}
		return newMap;
	}

	public static boolean isNotNull(String param)
	{
		boolean ret = true;
		if(null==param)
			ret = false;
		else if(param.trim().length()==0)
		{
			ret = false;
		}
		return ret;
	}
	public static boolean isNotNull(Integer param)
	{
		boolean ret = true;
		if(null==param)
			ret = false;
		else if(param==0)
		{
			ret = false;
		}
		return ret;
	}
	public static boolean isNotNull(Long param)
	{
		boolean ret = true;
		if(null==param)
			ret = false;
		else if(param==0)
		{
			ret = false;
		}
		return ret;
	}
	/**
	 * @description 去掉末尾的0
	 * @param strDate
	 * @return
	 */
	public static String formatDateStr(String strDate)
	{
		String ret = strDate;
		if(strDate!=null)
		{
			ret = strDate.replaceAll(" 00:00:00.0", "");
		}
		return ret;
	}
	

	/**
	 * ��java�Ŀ����л��Ķ���(ʵ��Serializable�ӿ�)���л����浽XML�ļ�����,�����һ�α����������л��������ü��Ͻ��з�װ ����ʱ���������ڵĶ���ԭ����XML�ļ�����
	 * 
	 * @param obj
	 *            Ҫ���л��Ŀ����л��Ķ���
	 * @param fileName
	 *            ����ȫ�ı���·�����ļ���
	 * @throws FileNotFoundException
	 *             ָ��λ�õ��ļ�������
	 * @throws IOException
	 *             ���ʱ�����쳣
	 * @throws Exception
	 *             ��������ʱ�쳣
	 */
	public static void objectXmlEncoder(Object obj, String fileName) throws FileNotFoundException, IOException, Exception {
		// ��������ļ�
		File fo = new File(fileName);
		// �ļ�������,�ʹ������ļ�
		if (!fo.exists()) {
			// �ȴ����ļ���Ŀ¼
			String path = fileName.substring(0, fileName.lastIndexOf('.'));
			File pFile = new File(path);
			pFile.mkdirs();
		}
		// �����ļ������
		FileOutputStream fos = new FileOutputStream(fo);
		// ����XML�ļ����������ʵ��
		XMLEncoder encoder = new XMLEncoder(fos);
		// �������л������XML�ļ�
		encoder.writeObject(obj);
		encoder.flush();
		// �ر����л�����
		encoder.close();
		// �ر������
		fos.close();
	}

	/**
	 * ��ȡ��objSourceָ����XML�ļ��е����л�����Ķ���,���صĽ�����List��װ
	 * 
	 * @param objSource
	 *            ��ȫ���ļ�·�����ļ�ȫ��
	 * @return ��XML�ļ����汣��Ķ��󹹳ɵ�List�б�(������һ�����߶�������л�����Ķ���)
	 * @throws FileNotFoundException
	 *             ָ���Ķ����ȡ��Դ������
	 * @throws IOException
	 *             ��ȡ�������
	 * @throws Exception
	 *             ��������ʱ�쳣����
	 */
	public static List objectXmlDecoder(String objSource) throws FileNotFoundException, IOException, Exception {
		List objList = new ArrayList();
		File fin = new File(objSource);
		FileInputStream fis = new FileInputStream(fin);
		XMLDecoder decoder = new XMLDecoder(fis);
		Object obj = null;
		try {
			while ((obj = decoder.readObject()) != null) {
				objList.add(obj);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
		}
		fis.close();
		decoder.close();
		return objList;
	}
	


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
 
 
}
