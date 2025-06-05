package io.github.wujun728.groovy;

import groovy.lang.GroovyObject;

import org.apache.ibatis.reflection.DefaultReflectorFactory;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.factory.DefaultObjectFactory;
import org.apache.ibatis.reflection.wrapper.DefaultObjectWrapperFactory;
import org.codehaus.groovy.control.CompilerConfiguration;

import java.util.List;
 
/**
 * @Title: GroovyClassLoaderApp
 * @Description: 演示 GroovyClassLoader 方式
 */
public class GroovyClassLoaderApp2 {
 
//    private static GroovyClassLoader groovyClassLoader = null;
//    private static GroovyClassLoader groovyClassLoader11 = null;
 
    public static void initGroovyClassLoader() {
        CompilerConfiguration config = new CompilerConfiguration();
        config.setSourceEncoding("UTF-8");
        // 设置该GroovyClassLoader的父ClassLoader为当前线程的加载器(默认)
//        groovyClassLoader = new GroovyClassLoader(Thread.currentThread().getContextClassLoader(), config);
//        groovyClassLoader11 = new GroovyClassLoader(Thread.currentThread().getContextClassLoader(), config);
        
    }
 
 
    public static void main(String[] args) {
//        loadClass();
        System.out.println("======================");
        loadFile();
    }
 
    /**
     * 通过文件路径加载groovy
     * @return
     */
    @SuppressWarnings("unchecked")
	private static boolean loadFile(){
//        initGroovyClassLoader();
    	GroovyAutoBuildClassUtils groovyClassLoader = new GroovyAutoBuildClassUtils();
        try {
            List<String> result;
            // 获得TestGroovy加载后的class
            Class<?> groovyClass11 = groovyClassLoader.buildClass("test2"," package io.github.wujun728.groovy;\r\n"
            		+ "\r\n"
            		+ "import java.io.Serializable;\r\n"
            		+ "\r\n"
            		+ "public class Param implements Serializable{\r\n"
            		+ "	\r\n"
            		+ "	String msg;\r\n"
            		+ "\r\n"
            		+ "	public String getMsg() {\r\n"
            		+ "		return msg;\r\n"
            		+ "	}\r\n"
            		+ "\r\n"
            		+ "	public void setMsg(String msg) {\r\n"
            		+ "		this.msg = msg;\r\n"
            		+ "	}\r\n"
            		+ "}\r\n"
            		+ " ");
            Class<?> groovyClass = groovyClassLoader.buildClass("test2"," package io.github.wujun728.groovy;\r\n"
            		+ "\r\n"
            		+ "import com.alibaba.fastjson.JSON\r\n"
            		+ "import com.alibaba.fastjson.TypeReference\r\n"
            		+ "import io.github.wujun728.groovy.Param;\r\n"
            		+ " \r\n"
            		+ "/**\r\n"
            		+ " * groove class\r\n"
            		+ " */\r\n"
            		+ "class TestGroovy {\r\n"
            		+ " \r\n"
            		+ "    void print() {\r\n"
            		+ "        System.out.println(\"hello word!!!!\");\r\n"
            		+ "    }\r\n"
            		+ " \r\n"
            		+ "    List<String> printArgs(String str1, String str2, String str3) {\r\n"
            		+ "        String jsonString = \"[\\\"\"+str1+\"\\\",\\\"\"+str2+\"\\\",\\\"\"+str3+\"\\\"]\";\r\n"
            		+ "        return JSON.parseObject(jsonString, new TypeReference<List<String>>() {});\r\n"
            		+ "    }\r\n"
            		+ "    void sayHello(Param p) {\r\n"
            		+ "		System.out.println(\"年龄是:\" + p.getMsg());\r\n"
            		+ "	}\r\n"
            		+ " \r\n"
            		+ "} ");
            // 获得TestGroovy的实例
            GroovyObject groovyObject = (GroovyObject) groovyClass.newInstance();
            GroovyObject param = (GroovyObject) groovyClass11.newInstance();
            MetaObject obj = MetaObject.forObject(param,new DefaultObjectFactory(),new DefaultObjectWrapperFactory(), new DefaultReflectorFactory());
            obj.setValue("msg", "66666666666");
            // 反射调用printArgs方法得到返回值
            //Object methodResult = groovyObject.invokeMethod("printArgs", new Object[] {"chy", "zjj", "xxx"});
            Object methodResult = groovyObject.invokeMethod("sayHello", new Object[] {param});
            if (methodResult != null) {
                result =(List<String>) methodResult;
                result.stream().forEach(System.out::println);
            }
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }
    
    
    
    /**
     * 通过类加载groovy
     */
    private static void loadClass(){
        GroovyObject groovyObject = null;
        try {
            groovyObject = (GroovyObject) GroovyClassLoaderApp2.class.getClassLoader().loadClass("io.github.wujun728.groovy.TestGroovy").newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
 
        // 执行无参函数
        groovyObject.invokeMethod("print",null);
 
        System.out.println("============================");
 
        // 执行有参函数
        Object[] objects = new Object[]{"abc", "def", "ghi"};
        List<String> ls=(List<String>) groovyObject.invokeMethod("printArgs", objects);
        ls.stream().forEach(System.out::println);
    }
}