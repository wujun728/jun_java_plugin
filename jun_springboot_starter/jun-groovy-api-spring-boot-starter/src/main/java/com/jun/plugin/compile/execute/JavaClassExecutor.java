package com.jun.plugin.compile.execute;

import com.alibaba.fastjson2.JSON;
import com.jun.plugin.common.util.SpringContextUtil;
import com.jun.plugin.compile.compile.StringSourceCompilerExtend;
import com.jun.plugin.compile.util.ClassLoaderUtil;
import com.jun.plugin.compile.util.CompileResult;
import com.jun.plugin.common.util.HttpRequestUtil;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.tools.JavaFileObject;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * 执行外部传来的一个代表Java类的byte数组
 * 执行过程：
 * 1. 清空HackSystem中的缓存
 * 2. new ClassModifier，并传入需要被修改的字节数组
 * 3. 调用ClassModifier#modifyUTF8Constant修改：
 *      java/lang/System -> com/jvm/ch9/remoteinvoke/HackSystem
 * 4. new一个类加载器，把字节数组加载为Class对象
 * 5. 通过反射调用Class对象的main方法
 * 6. 从HackSystem中获取返回结果
 */
public class JavaClassExecutor {
	/* 程序中正在运行的客户端代码个数 */
	//    private static volatile AtomicInteger runningCount = new AtomicInteger(0);

	public static String execute(byte[] classByte, String systemIn) {
		// 2. new ClassModifier，并传入需要被修改的字节数组
		ClassModifier cm = new ClassModifier(classByte);

		// 3. 调用ClassModifier#modifyUTF8Constant修改
		byte[] modifyBytes = cm.modifyUTF8Constant("java/lang/System", "com/jun/plugin/compile/execute/HackSystem");
		modifyBytes = cm.modifyUTF8Constant("java/util/Scanner", "com/jun/plugin/compile/execute/HackScanner");

		// 设置用户传入的标准输入
		((HackInputStream) HackSystem.in).set(systemIn);

		// 4. new一个类加载器，把字节数组加载为Class对象
		HotSwapClassLoader classLoader = new HotSwapClassLoader(ClassLoaderUtil.get());
		Class clazz = classLoader.loadByte(modifyBytes);
		// 5. 通过反射调用Class对象的main方法
		try {
			Method mainMethod = clazz.getMethod("main", new Class[]{String[].class});
			mainMethod.invoke(null, new String[]{null});
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
            /*
            被调用的方法的内部抛出了异常而没有被捕获时，将由此异常接收，
            由于这部分异常是远程执行代码的异常，我们要把异常栈反馈给客户端，
            所以不能使用默认的无参 printStackTrace() 把信息 print 到 System.err 中，
            而是要把异常信息 print 到 HackSystem.err 以反馈给客户端
            */
			e.getCause().printStackTrace(HackSystem.err);
		}

		// 6. 从HackSystem中获取返回结果
		String res = HackSystem.getBufferString();
		HackSystem.closeBuffer();
		return res;
	}

	public static String execute(StringSourceCompilerExtend compilerExtend, String systemIn) {
		HttpServletRequest request = HttpRequestUtil.getRequest();
		Map params = HttpRequestUtil.getAllParameters(request);
		HotSwapClassLoader classLoader = new HotSwapClassLoader(ClassLoaderUtil.get());
		Map<String, JavaFileObject> fileObjectMap = compilerExtend.getFileObjectMap();
		for (JavaFileObject javaFileObject : fileObjectMap.values()) {
			try {
				byte[] classByte = ((StringSourceCompilerExtend.TmpJavaFileObject) javaFileObject).getCompiledBytes();
				// 2. new ClassModifier，并传入需要被修改的字节数组
				ClassModifier cm = new ClassModifier(classByte);
				// 3. 调用ClassModifier#modifyUTF8Constant修改
				byte[] modifyBytes = cm.modifyUTF8Constant("java/lang/System", "com/jun/plugin/compile/execute/HackSystem");
				modifyBytes = cm.modifyUTF8Constant("java/util/Scanner", "com/jun/plugin/compile/execute/HackScanner");
				modifyBytes = cm.modifyUTF8Constant("java/util/ArrayList", "com/jun/plugin/compile/execute/HackArrayList");
				modifyBytes = cm.modifyUTF8Constant("java/util/HashMap", "com/jun/plugin/compile/execute/HackHashMap");

				// 4. new一个类加载器，把字节数组加载为Class对象
				classLoader.loadByte(modifyBytes);
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		// 设置用户传入的标准输入
		((HackInputStream) HackSystem.in).set(systemIn);
		// 5. 通过反射调用Class对象的main方法
		try {
			Class clazz = classLoader.loadClass(compilerExtend.getMainClassName());
			if(StringUtils.isEmpty(systemIn)){
				Method mainMethod = clazz.getMethod("main", new Class[]{String[].class});
				Object o = clazz.newInstance();
				SpringContextUtil.autowireBean(o);
				mainMethod.invoke(o, new String[]{null});
			}else if("execute".equalsIgnoreCase(systemIn)){
				Method mainMethod = clazz.getMethod("execute", Map.class);
				Object o = clazz.newInstance();
				SpringContextUtil.autowireBean(o);
				//SpringContextUtil.getBean(o.getClass());
				Object reObj = mainMethod.invoke(o, params);
				System.out.println(JSON.toJSONString(reObj));
			}else{
				Method mainMethod = clazz.getMethod(systemIn);
				if(ObjectUtils.isEmpty(mainMethod)){
					System.out.println("执行源码，找不到方法"+systemIn);
				}else{
					mainMethod = clazz.getMethod(systemIn);
					Object o = clazz.newInstance();
					SpringContextUtil.autowireBean(o);
					mainMethod.invoke(o);
				}
			}
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
			e.getCause().printStackTrace(HackSystem.err);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			e.getCause().printStackTrace(HackSystem.err);
		} catch (InvocationTargetException | ClassNotFoundException e) {
            /*
            被调用的方法的内部抛出了异常而没有被捕获时，将由此异常接收，
            由于这部分异常是远程执行代码的异常，我们要把异常栈反馈给客户端，
            所以不能使用默认的无参 printStackTrace() 把信息 print 到 System.err 中，
            而是要把异常信息 print 到 HackSystem.err 以反馈给客户端
            */
			e.printStackTrace();
			e.getCause().printStackTrace(HackSystem.err);
		} catch (InstantiationException e) {
			e.printStackTrace();
			e.getCause().printStackTrace(HackSystem.err);
			//throw new RuntimeException(e);
		}

		// 6. 从HackSystem中获取返回结果
		String res = HackSystem.getBufferString();
		HackSystem.closeBuffer();
		return res;
	}
	public static CompileResult execute3(StringSourceCompilerExtend compilerExtend, String systemIn) {
		CompileResult compileResult = new CompileResult();
		HttpServletRequest request = HttpRequestUtil.getRequest();
		Map params = HttpRequestUtil.getAllParameters(request);
		HotSwapClassLoader classLoader = new HotSwapClassLoader(ClassLoaderUtil.get());
		Map<String, JavaFileObject> fileObjectMap = compilerExtend.getFileObjectMap();
		for (JavaFileObject javaFileObject : fileObjectMap.values()) {
			try {
				byte[] classByte = ((StringSourceCompilerExtend.TmpJavaFileObject) javaFileObject).getCompiledBytes();
				// 2. new ClassModifier，并传入需要被修改的字节数组
				ClassModifier cm = new ClassModifier(classByte);
				// 3. 调用ClassModifier#modifyUTF8Constant修改
				byte[] modifyBytes = cm.modifyUTF8Constant("java/lang/System", "com/jun/plugin/compile/execute/HackSystem");
				modifyBytes = cm.modifyUTF8Constant("java/util/Scanner", "com/jun/plugin/compile/execute/HackScanner");
				modifyBytes = cm.modifyUTF8Constant("java/util/ArrayList", "com/jun/plugin/compile/execute/HackArrayList");
				modifyBytes = cm.modifyUTF8Constant("java/util/HashMap", "com/jun/plugin/compile/execute/HackHashMap");

				// 4. new一个类加载器，把字节数组加载为Class对象
				classLoader.loadByte(modifyBytes);
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		compileResult.setMethod(systemIn);
		// 设置用户传入的标准输入
		((HackInputStream) HackSystem.in).set(systemIn);
		// 5. 通过反射调用Class对象的main方法
		try {
			Class clazz = classLoader.loadClass(compilerExtend.getMainClassName());
			if(StringUtils.isEmpty(systemIn)){
				Method mainMethod = clazz.getMethod("main", new Class[]{String[].class});
				Object o = clazz.newInstance();
				SpringContextUtil.autowireBean(o);
				mainMethod.invoke(o, new String[]{null});
			}else if("execute".equalsIgnoreCase(systemIn)){
				Method mainMethod = clazz.getMethod("execute", Map.class);
				Object o = clazz.newInstance();
				SpringContextUtil.autowireBean(o);
				//SpringContextUtil.getBean(o.getClass());
				Object reObj = mainMethod.invoke(o, params);
				compileResult.setExecuteResult(reObj);
				System.out.println(JSON.toJSONString(reObj));
			}else{
				Method mainMethod = clazz.getMethod(systemIn);
				if(ObjectUtils.isEmpty(mainMethod)){
					System.out.println("执行源码，找不到方法"+systemIn);
				}else{
					mainMethod = clazz.getMethod(systemIn);
					Object o = clazz.newInstance();
					SpringContextUtil.autowireBean(o);
					mainMethod.invoke(o);
				}
			}
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
			e.getCause().printStackTrace(HackSystem.err);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			e.getCause().printStackTrace(HackSystem.err);
		} catch (InvocationTargetException | ClassNotFoundException e) {
            /*
            被调用的方法的内部抛出了异常而没有被捕获时，将由此异常接收，
            由于这部分异常是远程执行代码的异常，我们要把异常栈反馈给客户端，
            所以不能使用默认的无参 printStackTrace() 把信息 print 到 System.err 中，
            而是要把异常信息 print 到 HackSystem.err 以反馈给客户端
            */
			e.printStackTrace();
			e.getCause().printStackTrace(HackSystem.err);
		} catch (InstantiationException e) {
			e.printStackTrace();
			e.getCause().printStackTrace(HackSystem.err);
			//throw new RuntimeException(e);
		}

		// 6. 从HackSystem中获取返回结果
		String res = HackSystem.getBufferString();
		HackSystem.closeBuffer();
		compileResult.setExecuteMsg(res);
		compileResult.setIsSucess(true);
		return compileResult;
	}
}
