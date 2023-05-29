package com.jun.plugin.util4j.hotSwap.annationScriptFactory;

import java.lang.reflect.Modifier;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jun.plugin.util4j.hotSwap.annationScriptFactory.annations.AnnationScript;
import com.jun.plugin.util4j.hotSwap.classProvider.IClassProvider;
import com.jun.plugin.util4j.hotSwap.classProvider.IClassProvider.State;

/**
 * 动态加载jar内的脚本,支持包含匿名内部类 T不能做为父类加载 T尽量为接口类型,
 * 因为只有接口类型的类才没有逻辑,才可以不热加载,并且子类可选择实现.
 * 此类提供的脚本最好不要长期保持引用,由其是热重载后,原来的脚本要GC必须保证引用不存在
 * 通过监听脚本源实现代码的加载
 */
public abstract class AnnationScriptClassProvider<S extends IAnnotationScript> implements IAnnationScriptFactory<S>{
	protected final Logger _log = LoggerFactory.getLogger(this.getClass());
	/**
	 * 脚本库目录
	 */
	protected final IClassProvider classProvider;

	/**
	 * 是否自动重载变更代码
	 */
	protected volatile boolean autoReload;
	
	private final Map<String, Class<? extends S>> nameMap = new ConcurrentHashMap<String, Class<? extends S>>();
	private final Map<Integer, Class<? extends S>> idMap = new ConcurrentHashMap<Integer, Class<? extends S>>();
	private final ReentrantReadWriteLock rwLock=new ReentrantReadWriteLock();

	protected AnnationScriptClassProvider(IClassProvider classProvider) {
		Objects.requireNonNull(classProvider);
		this.classProvider=classProvider;
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
		Set<Class<? extends S>> scriptClass=findScriptClass(classes);
		Set<Class<? extends S>> instanceAbleScript = findInstanceAbleScript(scriptClass);
		this.idMap.clear();
		this.nameMap.clear();
		for(Class<? extends S> clazz:instanceAbleScript)
		{
			AnnationScript[] key=clazz.getDeclaredAnnotationsByType(AnnationScript.class);
			if(key!=null && key.length>0)
			{
				AnnationScript mapper=key[0];
				int id=mapper.id();
				String name=mapper.name();
				if(id!=0)
				{
					Class<? extends S> old=idMap.getOrDefault(id,null);
					if(old!=null)
					{
						_log.error("find Repeat Id ScriptClass,id="+id+",addingScript:" + clazz + ",existScript:"+ old);
					}else
					{
						idMap.put(id, clazz);
						_log.info("regist id mapping ScriptClass:id="+id+",class=" + clazz);
					}
				}
				if(name!=null && name.trim().length()>0)
				{
					Class<? extends S> old=nameMap.getOrDefault(name,null);
					if(old!=null)
					{
						_log.error("find Repeat name ScriptClass,name="+name+",addingScript:" + clazz + ",existScript:"+ old);
					}else
					{
						nameMap.put(name, clazz);
						_log.info("regist name mapping ScriptClass:name="+name+",class=" + clazz);
					}
				}
			}else
			{
				_log.error("find no mapping ScriptClass:"+clazz);
			}
		}
		_log.info("loadScriptClass complete,id mapping size:"+idMap.size()+",name mapping size:"+nameMap.size());
	}
	
	/**
	 * 找出脚本类
	 * @param clazzs
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	@SuppressWarnings("unchecked")
	private Set<Class<? extends S>> findScriptClass(Set<Class<?>> clazzs)
			throws InstantiationException, IllegalAccessException {
		Set<Class<? extends S>> scriptClazzs = new HashSet<Class<? extends S>>();
		for (Class<?> clazz : clazzs) {
			if (IAnnotationScript.class.isAssignableFrom(clazz)) {
				Class<S> scriptClazz = (Class<S>) clazz;
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
	private Set<Class<? extends S>> findInstanceAbleScript(Set<Class<? extends S>> scriptClazzs)
			throws InstantiationException, IllegalAccessException {
		Set<Class<? extends S>> result=new HashSet<>();
		for (Class<? extends S> scriptClazz : scriptClazzs) 
		{
			S script = getInstacne(scriptClazz);
			if(script==null)
			{
				continue;
			}
			if(skipRegistScript(script))
			{
				_log.warn("skip regist script,class=" + script.getClass());
				continue;
			}
			result.add(scriptClazz);
		}
		return result;
	}

	protected final boolean isAbstractOrInterface(Class<?> clazz)
	{
		return Modifier.isAbstract(clazz.getModifiers())|| Modifier.isInterface(clazz.getModifiers());// 是否是抽象类
	}
	
	protected final <C> C getInstacne(Class<C> clazz)
	{
		C instacne=null;
		if (!isAbstractOrInterface(clazz)) {// 可实例化脚本
			try {
				instacne=clazz.newInstance();
			} catch (Exception e) {
				_log.error("can't newInstance Class:" + clazz,e);
			}
		}
		return instacne;
	}
	
	/**
	 * 是否跳过此脚本类的注册
	 * @param script
	 * @return
	 */
	protected boolean skipRegistScript(S script)
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
	
	protected final Class<? extends S> getScriptClass(int id)
	{
		rwLock.readLock().lock();
		try {
			return idMap.get(id);
		} finally {
			rwLock.readLock().unlock();
		}
	}
	
	protected final Class<? extends S> getScriptClass(String name)
	{
		rwLock.readLock().lock();
		try {
			return nameMap.get(name);
		} finally {
			rwLock.readLock().unlock();
		}
	}
	
	protected final S newInstance(Class<? extends S> c,Object... args) {
		S result = null;
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
	
	protected final S newInstance(Class<? extends S> c) {
		S result = null;
		try {
			result = c.newInstance();
		} catch (Exception e) {
			_log.error(e.getMessage(), e);
		}
		return result;
	}
	
	public S buildInstance(int id) {
		S result=null;
		Class<? extends S> c = getScriptClass(id);
		if (c == null) 
		{
			_log.error("not found script,id=" + id);
		}else
		{
			result=newInstance(c);
		}
		return result;
	}
	
	@Override
	public S buildInstance(int id, Object... args) {
		S result=null;
		Class<? extends S> c = getScriptClass(id);
		if (c == null) 
		{
			_log.error("not found script,id=" + id);
		}else
		{
			result=newInstance(c,args);
		}
		return result;
	}
	
	public S buildInstance(String name) {
		S result=null;
		Class<? extends S> c = getScriptClass(name);
		if (c == null) 
		{
			_log.error("not found script,name=" + name);
		}else
		{
			result=newInstance(c);
		}
		return result;
	}
	
	@Override
	public S buildInstance(String name, Object... args) {
		S result=null;
		Class<? extends S> c = getScriptClass(name);
		if (c == null) 
		{
			_log.error("not found script,name=" + name);
		}else
		{
			result=newInstance(c,args);
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