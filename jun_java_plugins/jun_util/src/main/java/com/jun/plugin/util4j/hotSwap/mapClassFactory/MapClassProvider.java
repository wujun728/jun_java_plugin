package com.jun.plugin.util4j.hotSwap.mapClassFactory;

import java.lang.reflect.Modifier;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jun.plugin.util4j.hotSwap.classProvider.IClassProvider;
import com.jun.plugin.util4j.hotSwap.classProvider.IClassProvider.State;

/**
 * 动态加载jar内的脚本,支持包含匿名内部类 T不能做为父类加载 T尽量为接口类型,
 * 因为只有接口类型的类才没有逻辑,才可以不热加载,并且子类可选择实现.
 * 此类提供的脚本最好不要长期保持引用,由其是热重载后,原来的脚本要GC必须保证引用不存在
 * 通过监听脚本源实现代码的加载
 */
public abstract class MapClassProvider<C,K> implements IMapClassFactory<C, K>{
	
	protected final Logger _log = LoggerFactory.getLogger(this.getClass());
	/**
	 * 脚本库目录
	 */
	protected final IClassProvider classProvider;

	/**
	 * 是否自动重载变更代码
	 */
	protected volatile boolean autoReload;
	
	private final Class<C> parentClass;
	private final Map<K, Class<? extends C>> codeMap = new ConcurrentHashMap<K, Class<? extends C>>();
	private final ReentrantReadWriteLock rwLock=new ReentrantReadWriteLock();


	protected MapClassProvider(Class<C> parentClass,IClassProvider classProvider) {
		Objects.requireNonNull(parentClass);
		Objects.requireNonNull(classProvider);
		this.classProvider=classProvider;
		this.parentClass=parentClass;
		init();
	}
	
	private void init() {
		reload();//主动加载
		classProvider.addListener(this::classProviderListener);//被动加载监听器
	}

	/**
	 * 加载完成
	 */
	private void classProviderListener()
	{
		try {
			load();
		} catch (Exception e) {
			_log.error(e.getMessage(),e);
		}
	}
	
	/**
	 * 加载
	 * @throws Exception
	 */
	protected final void load()throws Exception
	{
		rwLock.writeLock().lock();
		try {
			Set<Class<?>> classes=classProvider.getLoadedClasses();
			initScriptClasses(classes);
			onScriptLoaded(classes);
		} finally {
			rwLock.writeLock().unlock();
		}
	}
	
	/**
	 * 脚本类的加载不交给父类控制
	 * @param classes
	 * @throws Exception
	 */
	private void initScriptClasses(Set<Class<?>> classes)throws Exception
	{
		Set<Class<? extends C>> scriptClass=findScriptClass(classes);
		Map<K, Class<? extends C>> newCodeMap = findInstanceAbleScript(scriptClass);
		this.codeMap.clear();
		this.codeMap.putAll(newCodeMap);
		_log.info("loadScriptClass complete,find Class:"+newCodeMap.size());
	}
	
	/**
	 * 找出脚本类
	 * @param clazzs
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	@SuppressWarnings("unchecked")
	private Set<Class<? extends C>> findScriptClass(Set<Class<?>> clazzs)
			throws InstantiationException, IllegalAccessException {
		Set<Class<? extends C>> scriptClazzs = new HashSet<Class<? extends C>>();
		for (Class<?> clazz : clazzs) {
			if (parentClass.isAssignableFrom(clazz)) {
				Class<C> scriptClazz = (Class<C>) clazz;
				scriptClazzs.add(scriptClazz);
			}
		}
		return scriptClazzs;
	}
	
	/**
	 * 查找可实例化的脚本
	 * @param scriptClazzs
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	private Map<K, Class<? extends C>> findInstanceAbleScript(Set<Class<? extends C>> scriptClazzs)
			throws InstantiationException, IllegalAccessException {
		Map<K, Class<? extends C>> codeMap = new ConcurrentHashMap<K, Class<? extends C>>();
		for (Class<? extends C> scriptClazz : scriptClazzs) 
		{
			C script = getInstacne(scriptClazz);
			if(script==null)
			{
				continue;
			}
			K key=null;
			try {
				key = findKey(script);
			} catch (Exception e) {
			}
			if(key==null)
			{
				continue;
			}
			if(skipRegistScript(script))
			{
				_log.warn("skip regist script,key="+key+",class=" + script.getClass());
				continue;
			}
			if (codeMap.containsKey(key)) {// 重复脚本code定义
				_log.error("find Repeat ScriptClass,key="+key+",addingScript:" + script.getClass() + ",existScript:"
						+ codeMap.get(key));
			} else {
				codeMap.put(key, scriptClazz);
				_log.info("regist ScriptClass:key="+key+",class=" + scriptClazz);
			}
		}
		return codeMap;
	}

	/**
	 * 获取这个实例的key
	 * @param instance
	 * @return
	 */
	protected abstract K findKey(C instance);
	
	protected final boolean isAbstractOrInterface(Class<?> clazz)
	{
		return Modifier.isAbstract(clazz.getModifiers())|| Modifier.isInterface(clazz.getModifiers());// 是否是抽象类
	}
	
	protected final <X> X getInstacne(Class<X> clazz)
	{
		X instacne=null;
		if (!isAbstractOrInterface(clazz)) {// 可实例化脚本
			try {
				instacne=clazz.newInstance();
			} catch (Exception e) {
				_log.error("can't newInstance Class:" + clazz);
			}
		}
		return instacne;
	}
	
	/**
	 * 是否跳过此脚本类的注册
	 * @param script
	 * @return
	 */
	protected boolean skipRegistScript(C script)
	{
		return false;
	}

	/**
	 * 当脚本加载完成后调用此方法,子类可继续过滤查找其它类
	 * @param classes 
	 */
	protected void onScriptLoaded(Set<Class<?>> loadedClasses)throws Exception
	{
		
	}
	public final State getState() {
		return classProvider.getState();
	}
	
	public final Set<K> getRegistKeys()
	{
		rwLock.readLock().lock();
		try {
			return new HashSet<>(codeMap.keySet());
		} finally {
			rwLock.readLock().unlock();
		}
	}
	
	protected  final <T> T newInstance(Class<? extends T> c,Object... args) {
		T result = null;
		try {
			Class<?>[] cs=new Class<?>[args.length];
			for(int i=0;i<args.length;i++)
			{
				cs[i]=args[i].getClass();
			}
			result = c.getConstructor(cs).newInstance(args);
		} catch (Exception e) {
			_log.error(e.getMessage(), e);
		}
		return result;
	}
	
	protected final <T> T newInstance(Class<? extends T> c) {
		T result = null;
		try {
			result = c.newInstance();
		} catch (Exception e) {
			_log.error(e.getMessage(), e);
		}
		return result;
	}
	
	protected final Class<? extends C> getScriptClass(K key)
	{
		rwLock.readLock().lock();
		try {
			return codeMap.get(key);
		} finally {
			rwLock.readLock().unlock();
		}
	}


	private Class<? extends C> getClass(K key)
	{
		return getScriptClass(key);
	}
	
	public final C buildInstance(K key) {
		C result=null;
		Class<? extends C> c = getClass(key);
		if (c == null) 
		{
			_log.error("not found script,key=" + key);
		} else 
		{
			result = newInstance(c);
		}
		return result;
	}
	
	@Override
	public final C buildInstance(K key, Object... args) {
		C result=null;
		Class<? extends C> c = getClass(key);
		if (c == null) 
		{
			_log.error("not found script,key=" + key );
		} else 
		{
			result = newInstance(c,args);
		}
		return result;
	}

	public final void reload() {
		try {
			load();
		} catch (Throwable e) {
			_log.error(e.getMessage(), e);
		}
	}
	
	public boolean isAutoReload() {
		return autoReload;
	}

	public void setAutoReload(boolean autoReload) {
		this.autoReload = autoReload;
	}
}