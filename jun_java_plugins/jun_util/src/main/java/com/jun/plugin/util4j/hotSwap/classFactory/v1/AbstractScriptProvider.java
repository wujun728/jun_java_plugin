package com.jun.plugin.util4j.hotSwap.classFactory.v1;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jun.plugin.util4j.file.FileUtil;
import com.jun.plugin.util4j.hotSwap.classFactory.IScript;
import com.jun.plugin.util4j.hotSwap.classFactory.v1.ScriptSource.DirClassFile;
import com.jun.plugin.util4j.hotSwap.classFactory.v1.ScriptSource.URLClassFile;

/**
 * 动态加载jar内的脚本,支持包含匿名内部类 T不能做为父类加载 T尽量为接口类型,
 * 因为只有接口类型的类才没有逻辑,才可以不热加载,并且子类可选择实现.
 * 此类提供的脚本最好不要长期保持引用,由其是热重载后,原来的脚本要GC必须保证引用不存在
 * 通过监听脚本源实现代码的加载
 */
public abstract class AbstractScriptProvider<T extends IScript> extends AbstractStaticScriptFactory<T> {
	protected final Logger _log = LoggerFactory.getLogger(this.getClass());

	/**
	 * 脚本库目录
	 */
	protected final ScriptSource scriptSource;
	protected ScriptClassLoader classLoader;

	/**
	 * 是否自动重载变更代码
	 */
	protected volatile boolean autoReload;
	protected final Map<Integer, Class<? extends T>> codeMap = new ConcurrentHashMap<Integer, Class<? extends T>>();
	private final ReentrantReadWriteLock rwLock=new ReentrantReadWriteLock();
	
	public static enum State {
		/**
		 * 脚本未加载
		 */
		ready,
		/**
		 * 脚本加载中
		 */
		loading,
		/**
		 * 脚本加载完成
		 */
		loaded,
	}
	
	protected volatile State state = State.ready;

	protected AbstractScriptProvider(ScriptSource scriptSource) {
		this(scriptSource, true);
	}

	protected AbstractScriptProvider(ScriptSource scriptSource, boolean autoReload) {
		this.scriptSource = scriptSource;
		this.autoReload = autoReload;
		init();
	}
	
	private boolean disableReload;
	
	private void init() {
		try {
			scriptSource.addEventListener((event->{
				switch (event) {
				case Change:
					disableReload=false;
					if(autoReload)
					{
						reload();
					}
					break;
				case Delete:
					disableReload=true;
					break;
				default:
					break;
				}
			}));
			loadScirptClass();
			onLoaded();
		} catch (Exception e) {
			_log.error(e.getMessage(), e);
		}
	}

	/**
	 * 加载所有的脚本类
	 * @param jarFiles
	 * @throws IOException
	 * @throws ClassNotFoundException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 */
	protected final void loadScirptClass() throws Exception 
	{
		if (state == State.loading) 
		{
			return;
		}
		rwLock.writeLock().lock();
		try {
			state = State.loading;
			ScriptClassLoader loader=loadClassByLoader();
			Set<Class<?>> allClass = loader.findedClass;
			Set<Class<? extends T>> scriptClass=findScriptClass(allClass);
			Map<Integer, Class<? extends T>> newCodeMap = findInstanceAbleScript(scriptClass);
			this.codeMap.clear();
			this.codeMap.putAll(newCodeMap);
			this.classLoader = loader;
			this.classLoader.close();//关闭资源文件引用
		} finally {
			state = State.loaded;
			rwLock.writeLock().unlock();
		}
	}

	/**
	 * 使用loader加载所有class
	 * @return
	 * @throws Exception
	 */
	private ScriptClassLoader loadClassByLoader() throws Exception 
	{
		ScriptClassLoader newClassLoader = new ScriptClassLoader();
		Set<Class<?>> allClass = newClassLoader.findedClass;
		Set<Class<?>> fileClass=new HashSet<>();
		for(DirClassFile dcf:scriptSource.getDirClassFiles())
		{
			File file=new File(dcf.getRootDir().getFile());
			if(!file.exists())
			{
				continue;
			}
			newClassLoader.addURL(dcf.getRootDir());
			for(String className:dcf.getClassNames())
			{
				Class<?> clazz=newClassLoader.loadClass(className);
				if(clazz!=null)
				{
					fileClass.add(clazz);
				}
			}
		}
		Set<Class<?>> urlClass=new HashSet<>();
		for(URLClassFile ucf:scriptSource.getUrlClassFiles())
		{
			newClassLoader.addURL(ucf.getURL());
			Class<?> clazz=newClassLoader.loadClass(ucf.getClassName());
			if(clazz!=null)
			{
				urlClass.add(clazz);
			}
		}
		Set<Class<?>> jarClass=new HashSet<>();
		for(URL jar:scriptSource.getJars())
		{
			JarFile jarFile=null;
			try {
				File file=new File(jar.getFile());
				if(!file.exists())
				{
					continue;
				}
				jarFile=new JarFile(jar.getFile());
				Map<String, JarEntry>  map=FileUtil.findClassByJar(jarFile);
				if(!map.isEmpty())
				{
					newClassLoader.addURL(jar);
					for(String className:map.keySet())
					{
						Class<?> clazz=newClassLoader.loadClass(className);
						if(clazz!=null)
						{
							jarClass.add(clazz);
						}
					}
				}
			} finally {
				if(jarFile!=null)
				{
					jarFile.close();
				}
			}
		}
		allClass.addAll(fileClass);
		allClass.addAll(urlClass);
		allClass.addAll(jarClass);
		_log.debug("classloader init complete,allClass:"+allClass.size()+",fileClass:" + fileClass.size() + ",urlClass:" + urlClass.size() + ",jarClass:" + jarClass.size());
		return newClassLoader;
	}

	/**
	 * 找出脚本类
	 * @param clazzs
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	@SuppressWarnings("unchecked")
	private Set<Class<? extends T>> findScriptClass(Set<Class<?>> clazzs)
			throws InstantiationException, IllegalAccessException {
		Set<Class<? extends T>> scriptClazzs = new HashSet<Class<? extends T>>();
		for (Class<?> clazz : clazzs) {
			Class<T> scriptClazz = null;
			try {
				scriptClazz=(Class<T>) clazz;
			}catch (Throwable e) {
			}
			if(scriptClazz==null)
			{
				continue;
			}
			if(acceptClass(scriptClazz))
			{
				scriptClazzs.add(scriptClazz);
			}
			//如果A接口有B,C实现,B和C之间不能转换,如果T是B,则不能使用A.class.isAssignableFrom(clazz)判断
//			if (IScript.class.isAssignableFrom(clazz)) {
//				Class<T> scriptClazz = (Class<T>) clazz;
//				scriptClazzs.add(scriptClazz);
//			}
		}
		return scriptClazzs;
	}
	
	/**
	 * 是否接受此类
	 * @param clazz
	 * @return
	 */
	protected boolean acceptClass(Class<T> clazz)
	{
		return true;
	}

	/**
	 * 查找可实例化的脚本
	 * @param scriptClazzs
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	private Map<Integer, Class<? extends T>> findInstanceAbleScript(Set<Class<? extends T>> scriptClazzs)
			throws InstantiationException, IllegalAccessException {
		Map<Integer, Class<? extends T>> codeMap = new ConcurrentHashMap<Integer, Class<? extends T>>();
		for (Class<? extends T> scriptClazz : scriptClazzs) 
		{
			boolean isAbstractOrInterface = Modifier.isAbstract(scriptClazz.getModifiers())|| Modifier.isInterface(scriptClazz.getModifiers());// 是否是抽象类
			if (!isAbstractOrInterface) {// 可实例化脚本
				T script = null;
				try {
					script=scriptClazz.newInstance();
				} catch (Exception e) {
					_log.error("can't newInstance ScriptClass:" + scriptClazz);
					continue;
				}
				int code = script.getMessageCode();
				if (codeMap.containsKey(script.getMessageCode())) {// 重复脚本code定义
					_log.error("find Repeat ScriptClass,code="+code+",addingScript:" + script.getClass() + ",existScript:"
							+ codeMap.get(code));
				} else {
					codeMap.put(code, scriptClazz);
					_log.info("regist ScriptClass:code="+code+",class=" + scriptClazz);
				}
			}
		}
		return codeMap;
	}

	class ScriptClassLoader extends URLClassLoader {
		Set<Class<?>> findedClass = new HashSet<Class<?>>();
		protected Logger log = LoggerFactory.getLogger(getClass());

		public ScriptClassLoader(URL[] urls) {
			super(urls, Thread.currentThread().getContextClassLoader());
		}

		public ScriptClassLoader(URL url) {
			this(new URL[] { url });
		}

		public ScriptClassLoader() {
			this(new URL[] {});
		}

		/**
		 * 加载类,如果是系统类则交给系统加载 如果当前类已经加载则返回类 如果当前类没有加载则定义并返回
		 */
		@Override
		protected Class<?> loadClass(String className, boolean resolve) throws ClassNotFoundException {
			Class<?> clazz = null;
			// 查找当前类加载中已加载的
			if (clazz == null) {
				clazz = findLoadedClass(className);
			}
			// 当前jar中加载
			if (clazz == null) {
				// 查找当前类加载器urls或者当前类加载器所属线程类加载器
				try {
					clazz = findClass(className);
				} catch (Exception e) {
				}
			}
			if (clazz == null) {// 系统类加载
				try {
					clazz = findSystemClass(className);
				} catch (Exception e) {

				}
			}
			String ClassLoader = null;
			if (clazz != null) {// 解析类结构
				ClassLoader = "" + clazz.getClassLoader();
				if (resolve) {
					resolveClass(clazz);
				}
			}
			log.debug("loadClass "+(clazz==null)+":" + className + ",resolve=" + resolve + ",Clazz=" + clazz + ",ClassLoader="+ ClassLoader);
			return clazz;
		}

		/**
		 * 查找类,这个方法一般多用于依赖类的查找,如果之前已经加载过,则重复加载会报错,所以需要添加findLoadedClass判断
		 */
		@Override
		protected Class<?> findClass(final String name) throws ClassNotFoundException {
			log.trace("findClass:"+name);
			Class<?> clazz=findLoadedClass(name);
			if (clazz != null) 
			{
				return clazz;
			}
			return super.findClass(name);
		}

		@Override
		protected void addURL(URL url) {
			super.addURL(url);
		}
	}

	protected final Class<? extends T> getScriptClass(int code)
	{
		rwLock.readLock().lock();
		try {
			return codeMap.get(code);
		} finally {
			rwLock.readLock().unlock();
		}
	}

	public final State getState() {
		return state;
	}

	private Class<? extends T> getClass(int code)
	{
		Class<? extends T> c = getStaticScriptClass(code);
		if(c==null)
		{
			c = getScriptClass(code);
		}
		return c;
	}
	
	public final T buildInstance(int code) {
		T result=null;
		Class<? extends T> c = getClass(code);
		if (c == null) 
		{
			_log.error("not found script,code=" + code + "(0x" + Integer.toHexString(code) + ")");
		} else 
		{
			result = newInstance(c);
		}
		return result;
	}
	
	@Override
	public final T buildInstance(int code, Object... args) {
		T result=null;
		Class<? extends T> c = getClass(code);
		if (c == null) 
		{
			_log.error("not found script,code=" + code + "(0x" + Integer.toHexString(code) + ")");
		} else 
		{
			result = newInstance(c,args);
		}
		return result;
	}

	public final void reload() {
		if(disableReload)
		{//脚本源已经删除
			_log.error("disableReload="+disableReload);
			return ;
		}
		try {
			loadScirptClass();
			onLoaded();
		} catch (Throwable e) {
			_log.error(e.getMessage(), e);
		}
	}
	
	public final boolean hasCode(int code)
	{
		return getClass(code)!=null;
	}
	
	public boolean isAutoReload() {
		return autoReload;
	}

	public void setAutoReload(boolean autoReload) {
		this.autoReload = autoReload;
	}
	
	/**
	 * 加载完成
	 */
	protected void onLoaded()
	{
		
	}
}
