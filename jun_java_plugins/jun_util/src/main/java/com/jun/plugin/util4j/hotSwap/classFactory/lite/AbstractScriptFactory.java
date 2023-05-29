package com.jun.plugin.util4j.hotSwap.classFactory.lite;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Modifier;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jun.plugin.util4j.bytesStream.InputStreamUtils;
import com.jun.plugin.util4j.hotSwap.classFactory.IScript;
import com.jun.plugin.util4j.hotSwap.classFactory.v1.AbstractStaticScriptFactory;
import com.jun.plugin.util4j.thread.NamedThreadFactory;

/**
 * 动态加载类 
 * 注意:脚本类不能包含匿名类
 * T不能做为父类加载
 * T尽量为接口类型,因为只有接口类型的类才没有逻辑,才可以不热加载,并且子类可选择实现
 */
public abstract class AbstractScriptFactory<T extends IScript> extends AbstractStaticScriptFactory<T>{
	protected final Logger _log = LoggerFactory.getLogger(this.getClass());
	
	/**
	 * 是否自动重载变更代码
	 */
	protected boolean autoReload;
	protected final Map<String,ClassFile> scriptFilePaths=new ConcurrentHashMap<String,ClassFile>();
	protected final Map<Integer, Class<? extends T>> codeMap=new ConcurrentHashMap<Integer, Class<? extends T>>();
	protected boolean isLoading;
	
	/**
	 * 文件监测间隔时间
	 */
	protected long intervalMillis=TimeUnit.SECONDS.toMillis(10);
	
	protected static final ScheduledExecutorService schedule=Executors.newScheduledThreadPool(1, new NamedThreadFactory("ScriptFactoryMonitor",true));

	protected ScriptClassLoader classLoader;
	protected final String classRootDir;
	
	protected AbstractScriptFactory(String classRootDir) {
		this.classRootDir=classRootDir;
		init();
	}
	
	protected AbstractScriptFactory(String classRootDir,boolean autoReload) {
		this.classRootDir=classRootDir;
		this.autoReload=autoReload;
		init();
	}
	
	private void init()
	{
		try {
			initScriptRegist(new ScriptRegister(this));
			schedule.scheduleWithFixedDelay(new ScriptMonitorTask(),0, intervalMillis, TimeUnit.MILLISECONDS);
		} catch (Exception e) {
			_log.error(e.getMessage(),e);
		}
	}
	
	/**
	 * 验证路径是否正确
	 * @param path
	 * @return
	 */
	protected boolean validClassFilePath(String path)
	{
		if(path==null)
		{
			return false;
		}
		File file=new File(path);
		return file.exists()&&file.isFile();
	}
	
	/**
	 * 验证注册类是否符合要求
	 * @param className
	 * @param depends
	 */
	private boolean validate(String className,String ...depends)
	{
		ClassFile cf = getClassFile(className);
		if(cf==null)
		{
			_log.error("can't find classFile:"+className);
			return false;
		}
		boolean result=true;
		for(int i=0;i<depends.length;i++)
		{
			String parentClassName=depends[i];
			ClassFile parentCf=getClassFile(parentClassName);
			if(parentCf==null)
			{//如果依赖的类不能能拿到
				_log.error("can't find dependClassFile:"+parentClassName);
				result= false;
				break;
			}
		}
		return result;
	}
	
	/**
	 * 根据类名注册类
	 * @param script 脚本类
	 * @param depends 依赖类
	 */
	protected final void registClass(String className,String ...depends) {
		if(!validate(className, depends))
		{
			return ;
		}
		ClassFile cf = getClassFile(className);
		scriptFilePaths.put(cf.getFilePath(), cf);
		_log.info("registClass:"+cf.getFilePath());
		ClassFile subClass=cf;//子类
		for(int i=0;i<depends.length;i++)
		{
			String parentClassName=depends[i];
			ClassFile parentCf=getClassFile(parentClassName);
			subClass.setParent(parentCf);//设置子类
			if(!scriptFilePaths.containsKey(parentCf.getFilePath()))
			{
				scriptFilePaths.put(parentCf.getFilePath(),parentCf);
				_log.info("registDependClass:"+parentCf.getClassName());
			}
			subClass=parentCf;//更新子类为当前父类
		}
	}
	/**
	 * 注册类
	 * @param script 脚本类
	 * @param depends 依赖类
	 */
	protected final void registClass(Class<? extends T> scriptClass,Class<?> ...depends) {
		if(scriptClass!=null)
		{
			boolean isAbstract=Modifier.isAbstract(scriptClass.getModifiers());//是否是抽象类
			if(isAbstract)
			{//抽象类
				_log.error("can't regist isAbstract class:"+scriptClass.getClass());
				return ;
			}else
			{
				try {
					T script= scriptClass.newInstance();
					int code=script.getMessageCode();
					if(codeMap.containsKey(code))
					{//如果已经存在相同code的脚步则不加载
						_log.error("find Repeat code script,addingScript:"+script.getClass()+",existScript:"+codeMap.get(code));
					}else
					{
						codeMap.put(code,scriptClass);
						_log.info("loaded codeScript,code="+code+"(0x"+Integer.toHexString(code)+"),class="+scriptClass);
					}
				} catch (Exception e) {
					_log.error("scriptClass error:"+scriptClass,e);
				}
			}
			//注册class文件方便热更新
			String className=scriptClass.getName();
			String[] parents=new String[depends.length];
			for(int i=0;i<depends.length;i++)
			{
				Class<?> pt=depends[i];
				parents[i]=pt.getName();
			}
			registClass(className,parents);
		}
	}
	
	/**
	 * 获取class文件根目录
	 * @return
	 */
	protected  final String getClassRootDir(){
		return classRootDir; 
	}

	/**
	 * 路径解析
	 * @param className
	 * @return
	 */
	protected final ClassFile getClassFile(String className)
	{
		ClassFile cf=null;
		String filePath=null;
		if(className!=null && !className.isEmpty())
		{
			String rootPath=getClassRootDir();
			if(rootPath.endsWith("/")||rootPath.endsWith("\\"))
			{
			}else
			{
				rootPath=rootPath+File.separator;
			}
			filePath=rootPath+className.replace('.', File.separatorChar)+".class";
		}
		File file=new File(filePath);
		if(file.exists() && file.isFile())
		{
			cf=new ClassFile(className, file.getPath());
			cf.setLastModifyTime(file.lastModified());
		}
		return cf;
	}
	
	protected final Class<? extends T> getScriptClass(int code)
	{
		return codeMap.get(code);
	}
	
	// 加载所有的类
	@SuppressWarnings("unchecked")
	public final void loadAllClass(){
		if(isLoading)
		{
			return;
		}
		isLoading=true;
		classLoader = new ScriptClassLoader();
		final ConcurrentHashMap<Integer, Class<? extends T>> codeMap=new ConcurrentHashMap<Integer, Class<? extends T>>();
		for (Entry<String,ClassFile> entry : scriptFilePaths.entrySet()) 
		{
			ClassFile classFile = entry.getValue();
			try {
				Class<T> scriptClass=null;
				Class<?> clazz=loadClass(classLoader, classFile);
				if(clazz!=null)
				{//类型转换
					scriptClass=(Class<T>)clazz;
				}
				if(scriptClass!=null)
				{
					boolean isAbstract=Modifier.isAbstract(scriptClass.getModifiers());//是否是抽象类
					if(isAbstract)
					{//抽象类
//						_log.info("loaded abstractScript:" + classFile.getFilePath());
					}else
					{
						T script=scriptClass.newInstance();
						int code=script.getMessageCode();
						if(codeMap.containsKey(code))
						{//如果已经存在相同code的脚步则不加载
							_log.error("find Repeat code script,addingScript:"+script.getClass()+",existScript:"+codeMap.get(code));
						}else
						{
							_log.info("**********LoadRegistClass<" + classFile.getClassName()+">**********");
							codeMap.put(code,scriptClass);
							_log.info("loaded codeScript,code="+code+"(0x"+Integer.toHexString(code)+"),path=" + classFile.getFilePath());
						}
					}
					//如果相同脚步的类文件发生改变后可能code会不一致,所以即使有相同code的脚步依然要记录时间
					classFile.setLastModifyTime(new File(classFile.getFilePath()).lastModified());//更新时间
				}
			} catch (Exception e) {
				_log.error(e.getMessage(),e);
			}
		}
		this.codeMap.putAll(codeMap);
		isLoading=false;
	}
	
	/**
	 * @param dc
	 * @param classFile
	 * @return
	 * @throws IOException
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	protected final Class<?> loadClass(ScriptClassLoader dc,ClassFile classFile) throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException
	{
		Class<?> clazz=null;
		if(classFile!=null && classFile.isExist())
		{
			ClassFile parent=classFile.getParent();
			if(parent!=null)
			{//返回加载父类
				Class<?> depend=loadClass(dc, parent);
				_log.info("loadDependClass:"+depend.getName());
			}
			boolean isLoaded=dc.hasLoaded(classFile.getClassName());
			if(!isLoaded)
			{
				clazz=dc.findClass(classFile.getClassName(),getFileBytes(classFile));
			}else
			{
				clazz=dc.loadClass(classFile.getClassName());
			}
		}
		return clazz;
	}
	
	/**
	 * 初始化类注册
	 */
	protected abstract void initScriptRegist(ScriptRegister reger);
	
	// 从本地读取文件
	protected byte[] getFileBytes(ClassFile classFile) throws IOException {
		File file = new File(classFile.getFilePath());
		FileInputStream fin = new FileInputStream(file);
		byte[] raw=InputStreamUtils.getBytes(fin);
		fin.close();
		return raw;
	}

	class ScriptClassLoader extends ClassLoader {
		public ScriptClassLoader() {
			//解决在webapp里面支持热更新脚本
			super(Thread.currentThread().getContextClassLoader());
		}
		private Set<String> loadedClass=new HashSet<String>();
		
		public Class<?> findClass(String className,byte[] b) throws ClassNotFoundException {
			if(hasLoaded(className))
			{
				return findLoadedClass(className);
			}
			Class<?> clazz=defineClass(className, b, 0, b.length);
			if(clazz!=null)
			{
				loadedClass.add(clazz.getName());
			}
			return clazz;
		}
		public boolean hasLoaded(String className)
		{
			return loadedClass.contains(className);
		}
	}
	class ClassFile {

		private final String className;
		private ClassFile parent;//上级父类
		private final String filePath;
		private long lastModifyTime;
		public ClassFile(String className,String path) {
			this.className=className;
			this.filePath = path;
		}

		public String getClassName() {
			return className;
		}

		public String getFilePath() {
			return filePath;
		}

		public ClassFile getParent() {
			return parent;
		}

		public void setParent(ClassFile parent) {
			this.parent = parent;
		}
		
		public long getLastModifyTime() {
			return lastModifyTime;
		}

		public void setLastModifyTime(long lastModifyTime) {
			this.lastModifyTime = lastModifyTime;
		}

		/**
		 * 是否存在
		 * @return
		 */
		public boolean isExist()
		{
			File f=new File(filePath);
			return f.exists();
		}
		
		@Override
		public String toString() {
			return "ClassFile [className=" + className + ", parent=" + parent
					+ ", filePath=" + filePath + "]";
		}
	}
	
	/**
	 * 脚本监视任务
	 * @author Administrator
	 */
	class ScriptMonitorTask implements Runnable{

		@Override
		public void run() {
			try {
				if(!autoReload)
				{
					return ;
				}
				if(isLoading)
				{
					return;
				}
				boolean reload=false;
				for (Entry<String,ClassFile> entry : scriptFilePaths.entrySet()) {
					ClassFile classFile = entry.getValue();
					File file = new File(classFile.getFilePath());
					if (file.exists() && file.lastModified() > classFile.getLastModifyTime()) {
						reload = true;
						break;
					}
				}
				if(reload && !isLoading)
				{
					_log.debug("reload allClass……");
					loadAllClass();
				}
			} catch (Throwable e) {
				_log.error(e.getMessage(),e);
			}
		}
	}

	protected class ScriptRegister{
		private final AbstractScriptFactory<T> factory;
		
		public ScriptRegister(AbstractScriptFactory<T> factory) {
			super();
			this.factory = factory;
		}
		/**
		 * 注册需要热更新的class
		 * 注意:脚本类不能包含匿名类
		 * @param scriptClass
		 * @param depends
		 */
		public final void registClass(Class<? extends T> scriptClass,Class<?> ...depends) {
			factory.registClass(scriptClass, depends);
		}
		/**
		 * 根据类名注册类
		 * 注意:脚本类不能包含匿名类
		 * @param script 脚本类
		 * @param depends 依赖类
		 */
		public final void registClass(String className,String ...depends) {
			factory.registClass(className, depends);
		}
	}
	
	public boolean isAutoReload() {
		return autoReload;
	}
	public void setAutoReload(boolean autoReload) {
		this.autoReload = autoReload;
	}
	
	public final T buildInstance(int code) {
		T result=null;
		Class<? extends T> c = getStaticScriptClass(code);
		if(c==null)
		{
			c = getScriptClass(code);
		}
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
	public T buildInstance(int code, Object... args) {
		T result=null;
		Class<? extends T> c = getStaticScriptClass(code);
		if(c==null)
		{
			c = getScriptClass(code);
		}
		if (c == null) 
		{
			_log.error("not found script,code=" + code + "(0x" + Integer.toHexString(code) + ")");
		} else 
		{
			result = newInstance(c,args);
		}
		return result;
	}
	
	@Override
	public final void reload() {
		loadAllClass();
	}
}
