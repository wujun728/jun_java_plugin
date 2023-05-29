package com.jun.plugin.util4j.hotSwap.classFactory.lite;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jun.plugin.util4j.file.FileUtil;
import com.jun.plugin.util4j.hotSwap.classFactory.IScript;
import com.jun.plugin.util4j.hotSwap.classFactory.v1.AbstractStaticScriptFactory;
import com.jun.plugin.util4j.thread.NamedThreadFactory;

/**
 * 动态加载jar内的脚本,支持包含匿名内部类 T不能做为父类加载 T尽量为接口类型,
 * 因为只有接口类型的类才没有逻辑,才可以不热加载,并且子类可选择实现
 * (本类实现的文件变动监听用以执行重加载)
 */
public abstract class AbstractLibScriptFactory<T extends IScript> extends AbstractStaticScriptFactory<T> {
	protected final Logger _log = LoggerFactory.getLogger(this.getClass());

	/**
	 * 脚本库目录
	 */
	protected final String scriptLibDir;
	protected ScriptClassLoader classLoader;

	/**
	 * 是否自动重载变更代码
	 */
	protected volatile boolean autoReload;

	/**
	 * 记录加载的jar文件和时间
	 */
	protected final Map<String, RecordFile> loadedRecord = new ConcurrentHashMap<String, RecordFile>();
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

	/**
	 * 文件监测间隔时间
	 */
	protected long intervalMillis = TimeUnit.SECONDS.toMillis(10);
	protected static final ScheduledExecutorService schedule = Executors.newScheduledThreadPool(1,
			new NamedThreadFactory("ScriptFactoryMonitor", true));

	protected AbstractLibScriptFactory(String scriptLibDir) {
		this(scriptLibDir, true);
	}

	protected AbstractLibScriptFactory(String scriptLibDir, boolean autoReload) {
		this.scriptLibDir = scriptLibDir;
		this.autoReload = autoReload;
		init();
	}

	/**
	 * 寻找目录下的jar文件
	 * @param scriptLibDir
	 * @return
	 */
	protected List<File> findJarFile(String scriptLibDir) {
		List<File> files = new ArrayList<File>();
		try {
			File file = new File(scriptLibDir);
			if (file.isDirectory()) {
				for (File f : file.listFiles()) {
					if (f.isFile() && f.getName().endsWith(".jar")) {
						files.add(f);
					}
				}
			}
		} catch (Exception e) {
			_log.error(e.getMessage(), e);
		}
		return files;
	}

	private void init() {
		try {
			loadAllClass();
			schedule.scheduleWithFixedDelay(new ScriptMonitorTask(), 0, intervalMillis, TimeUnit.MILLISECONDS);
		} catch (Exception e) {
			_log.error(e.getMessage(), e);
		}
	}

	/**
	 * 获取class文件根目录
	 * 
	 * @return
	 */
	protected final String getClassRootDir() {
		return scriptLibDir;
	}

	/**
	 * 遍历配置的目录的jar文件并加载class
	 * 
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public final void loadAllClass() throws Exception {
		loadAllClassByDir(scriptLibDir);
	}

	/**
	 * 遍历目录所有jar并加载class
	 * 
	 * @throws IOException
	 * @throws ClassNotFoundException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 */
	protected final void loadAllClassByDir(String scriptLibDir) throws Exception {
		List<File> jarFiles = findJarFile(scriptLibDir);
		loadAllClass(jarFiles);
	}

	private final AtomicInteger ato=new AtomicInteger();
	
	private List<MirrorFile> copyToTmpDir(List<File> files) throws IOException
	{
		List<MirrorFile> tmpFiles=new ArrayList<>();
		File tmpDir=null;
		for(File f:files)
		{
			if(f.exists() && f.isFile())
			{
				if(tmpDir==null)
				{
					tmpDir=FileUtil.createTmpDir("scriptFactoryTmp_"+ato.getAndIncrement());
					if(tmpDir.exists())
					{
						tmpDir.delete();
						tmpDir.mkdir();
					}
				}
				File newFile=new File(tmpDir,f.getName());
				_log.debug("copyFile "+f.getPath()+" to:"+newFile.getPath());
				Files.copy(f.toPath(), newFile.toPath(), StandardCopyOption.COPY_ATTRIBUTES,StandardCopyOption.REPLACE_EXISTING);
				tmpFiles.add(new MirrorFile(f, newFile));
			}
		}
		_log.debug("copyFiles to tmpDir:"+tmpDir);
		return tmpFiles;
	}
	
	private class MirrorFile{
		private final File file;
		private final File mirrorFile;
		public MirrorFile(File file, File mirrorFile) {
			super();
			this.file = file;
			this.mirrorFile = mirrorFile;
		}
		public File getFile() {
			return file;
		}
		public File getMirrorFile() {
			return mirrorFile;
		}
		@Override
		public String toString() {
			return "MirrorFile [file=" + file + ", mirrorFile=" + mirrorFile + "]";
		}
	}
	
	/**
	 * 加载所有的类
	 * @param jarFiles
	 * @throws IOException
	 * @throws ClassNotFoundException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 */
	protected final void loadAllClass(List<File> jarFiles) throws Exception 
	{
		if (state == State.loading) 
		{
			return;
		}
		rwLock.writeLock().lock();
		try {
			state = State.loading;
			//复制文件到临时目录,因为urlClassLoader加载后会占用文件,导致后续无法删除scriptLibDir下的文件
			List<MirrorFile> waiteLoad=copyToTmpDir(jarFiles);
			ScriptClassLoader newClassLoader = new ScriptClassLoader();
			Map<String, RecordFile> newLoadedRecord = new HashMap<String, RecordFile>();
			Set<Class<?>> allClass = new HashSet<Class<?>>();
			for (MirrorFile mf : waiteLoad) 
			{
				File file=mf.getFile();
				File mirrorFile=mf.getMirrorFile();
				//使用镜像文件加载进classLoader
				Set<Class<?>> jarClass = loadAllClass(mirrorFile, newClassLoader);
				allClass.addAll(jarClass);
				//记录文件变化,使用源文件的修改时间
				RecordFile rf = new RecordFile(file.getPath());
				rf.setLastModifyTime(file.lastModified());
				newLoadedRecord.put(rf.getFilePath(), rf);
				_log.debug("加载jar文件" + rf.getFilePath() + ",class数量:" + jarClass.size() + ",class:" + jarClass.toString());
			}
			Map<Integer, Class<? extends T>> newCodeMap = findScriptCodeMap(findScriptClass(allClass));
			this.codeMap.clear();
			this.loadedRecord.clear();
			this.classLoader.close();
			this.codeMap.putAll(newCodeMap);
			this.loadedRecord.putAll(newLoadedRecord);
			this.classLoader = newClassLoader;
			this.classLoader.close();
		} finally {
			state = State.loaded;
			rwLock.writeLock().unlock();
		}
	}

	/**
	 * 加载jar的所有类
	 * 
	 * @param jarFiles
	 * @param loader
	 * @return
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	protected Set<Class<?>> loadAllClass(File jarFile, ScriptClassLoader loader) throws Exception {
		Set<Class<?>> clzzs = new HashSet<Class<?>>();
		JarFile jf = new JarFile(jarFile);
		try {
			loader.addURL(jarFile.toURI().toURL());// 添加文件到加载器
			Enumeration<JarEntry> it = jf.entries();
			while (it.hasMoreElements()) {
				JarEntry jarEntry = it.nextElement();
				if (jarEntry.getName().endsWith(".class")) {
					String className = jarEntry.getName().replace("/", ".").replaceAll(".class", "");
					clzzs.add(loader.findClass(className));
				}
			}
		} finally {
			jf.close();
		}
		return clzzs;
	}

	/**
	 * 找出脚本类
	 * 
	 * @param clazzs
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	@SuppressWarnings("unchecked")
	protected Set<Class<? extends T>> findScriptClass(Set<Class<?>> clazzs)
			throws InstantiationException, IllegalAccessException {
		Set<Class<? extends T>> scriptClazzs = new HashSet<Class<? extends T>>();
		for (Class<?> clazz : clazzs) {
			if (IScript.class.isAssignableFrom(clazz)) {
				Class<T> scriptClazz = (Class<T>) clazz;
				scriptClazzs.add(scriptClazz);
			}
		}
		return scriptClazzs;
	}

	/**
	 * 查找可实例化的脚本
	 * 
	 * @param scriptClazzs
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	protected Map<Integer, Class<? extends T>> findScriptCodeMap(Set<Class<? extends T>> scriptClazzs)
			throws InstantiationException, IllegalAccessException {
		Map<Integer, Class<? extends T>> codeMap = new ConcurrentHashMap<Integer, Class<? extends T>>();
		for (Class<? extends T> scriptClazz : scriptClazzs) {
			boolean isAbstractOrInterface = Modifier.isAbstract(scriptClazz.getModifiers())
					|| Modifier.isInterface(scriptClazz.getModifiers());// 是否是抽象类
			if (!isAbstractOrInterface) {// 可实例化脚本
				IScript script = scriptClazz.newInstance();
				int code = script.getMessageCode();
				if (codeMap.containsKey(script.getMessageCode())) {// 重复脚本code定义
					_log.error("find Repeat CodeScriptClass,code="+code+",addingScript:" + script.getClass() + ",existScript:"
							+ codeMap.get(code));
				} else {
					codeMap.put(code, scriptClazz);
					_log.info("loaded CodeScriptClass:code="+code+",class=" + scriptClazz);
				}
			}
		}
		return codeMap;
	}

	class ScriptClassLoader extends URLClassLoader {
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
			log.debug("loadClass:" + className + ",resolve=" + resolve + ",Clazz=" + clazz + ",ClassLoader="+ ClassLoader);
			return clazz;
		}

		/**
		 * 查找类,这个方法一般多用于依赖类的查找,如果之前已经加载过,则重复加载会报错,所以需要添加findLoadedClass判断
		 */
		@Override
		protected Class<?> findClass(final String name) throws ClassNotFoundException {
			log.debug("findClass:"+name);
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

	/**
	 * jar记录
	 * 
	 * @author juebanlin
	 */
	class RecordFile {

		private final String filePath;
		private long lastModifyTime;

		public RecordFile(String path) {
			this.filePath = path;
		}

		public String getFilePath() {
			return filePath;
		}

		public long getLastModifyTime() {
			return lastModifyTime;
		}

		public void setLastModifyTime(long lastModifyTime) {
			this.lastModifyTime = lastModifyTime;
		}

		/**
		 * 是否存在
		 * 
		 * @return
		 */
		public boolean isExist() {
			File f = new File(filePath);
			return f.exists();
		}

		@Override
		public String toString() {
			return "JarFile [filePath=" + filePath + ", lastModifyTime=" + lastModifyTime + "]";
		}
	}

	private boolean hashChange()
	{
		rwLock.readLock().lock();
		boolean trigLoad = false;
		try {
			Map<String, File> files = new HashMap<String, File>();
			for (File file : findJarFile(scriptLibDir)) 
			{
				files.put(file.getPath(), file);
			}
			for (File file : files.values()) 
			{
				RecordFile rf = loadedRecord.get(file.getPath());
				if (rf == null) 
				{// 新增jar
					trigLoad = true;
					break;
				} else 
				{// 文件变动
					if (file.lastModified() > rf.getLastModifyTime()) 
					{
						trigLoad = true;
						break;
					}
				}
			}
			if (!trigLoad) 
			{//判断是否减少了jar
				for (String key : loadedRecord.keySet()) 
				{
					if (!files.containsKey(key)) 
					{//减少jar
						trigLoad = true;
						break;
					}
				}
			}
		} catch(Exception e){
			_log.error(e.getMessage(),e);
		}
		finally {
			rwLock.readLock().unlock();
		}
		return trigLoad;
	}
	
	
	/**
	 * jar目录监控任务,发生jar新增或者改变,则重置jarFiles并执行加载class
	 * 
	 * @author Administrator
	 */
	class ScriptMonitorTask implements Runnable {

		public void run() {
			if (!autoReload) {
				return;
			}
			if (state == State.loading) 
			{
				return;
			}
			if(hashChange())
			{
				_log.debug("trigger reload scriptLibs……");
				reload();
			}
		}
	}

	public boolean isAutoReload() {
		return autoReload;
	}

	public void setAutoReload(boolean autoReload) {
		this.autoReload = autoReload;
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

	public final void reload() {
		try {
			loadAllClass();
		} catch (Throwable e) {
			_log.error(e.getMessage(), e);
		}
	}
}
