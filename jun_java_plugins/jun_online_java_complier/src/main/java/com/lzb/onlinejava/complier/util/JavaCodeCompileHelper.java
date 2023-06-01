package com.lzb.onlinejava.complier.util;

import com.lzb.onlinejava.complier.config.Constans;

import javax.tools.*;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URI;
import java.util.Arrays;

/**
 * @author: bin
 * @email: 958615915@qq.com
 * @create: 2019-08-03
 */
public class JavaCodeCompileHelper {
    // path 是你想把.class 放在哪里的路径，
    //    private String path = "C:\\Users\\bin\\Desktop\\java\\";
//    private String path = "C:\\Users\\ucmed\\Desktop\\java\\";
    private String path = Constans.classPath;

    /**
     * 将java代码在内存中编译，并获得这个class
     * @param javaCode
     * @param className
     * @return
     * @throws Exception
     */
    public Class compileAndGetClass(String javaCode, String className) throws Exception {
        JavaCompiler javaCompiler = ToolProvider.getSystemJavaCompiler();

        // StandardJavaFileManager 对象主要负责
        // 编译文件对象的创建，编译的参数等等，我们只对它做些基本设置比如编译 CLASSPATH 等。
        StandardJavaFileManager standardJavaFileManager = javaCompiler.getStandardFileManager(null, null, null);

        // JavaFileObject 为类文件上进行操作的工具的文件抽象
        JavaFileObject javaFileObject = new myJavaFileObject(className, javaCode);

        // Array Map Set 等都属于Itreable类型
        Iterable options = Arrays.asList("-d", path);

        Iterable<? extends JavaFileObject> files = Arrays.asList(javaFileObject);

        // 通过一些选项，javafileObject， classPath 来获取JvaComiler.ComilationTask
        JavaCompiler.CompilationTask task = javaCompiler.getTask(null,
                standardJavaFileManager, null, options, null, files);

        // 将Class 在内存中编译
        Boolean result = task.call();

        // 通过类名 加载class
        ClassLoader classLoader = new ClassClassLoader(getClass().getClassLoader());
        return classLoader.loadClass(className);
    }


    /**
     *  传入类， 方法名， 参数， 获得返回值
     * @param cls
     * @param methodName
     * @param args
     * @return
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    public Object executeMethod(Class cls, String methodName, Object... args) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, InstantiationException {
        Class[] clsArray;

        // 无参方法
        if (args == null) {
            Method method = cls.getMethod(methodName, null);
            method.setAccessible(true);
            Object obj = method.invoke(cls.newInstance(), null);

            return obj;
        }


        // 一个String[] 传参
        if (args instanceof String[]) {

            Method method = cls.getMethod(methodName, String[].class);
            method.setAccessible(true);
            // java 的 bug， 为String[] 时要 用 Object 强转
            Object obj = method.invoke(cls.newInstance(), (Object) args);

            return obj;


        // 多个传参
        } else if (args instanceof Object[]) {

            clsArray = new Class[args.length];

            for (int i = 0; i < args.length; i++) {
                clsArray[i] = args[i].getClass();
            }
            Method method = cls.getMethod(methodName, clsArray);
            method.setAccessible(true);
            Object obj = method.invoke(cls.newInstance(),  args);

            return obj;


        } else {

        }

        return null;

    }

    public static void main(String[] args) {
    }


    private class myJavaFileObject extends SimpleJavaFileObject {
        private String contents = null;

        public myJavaFileObject(String clasName, String contents) throws Exception {
            super(URI.create("String:///" + clasName + Kind.SOURCE.extension), Kind.SOURCE);
            this.contents = contents;
        }

        @Override
        public CharSequence getCharContent(boolean ignoreEncodingErrors) throws IOException {
            return contents;
        }
    }


}
