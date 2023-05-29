package com.jun.plugin.util4j.hotSwap.classFactory;

import java.lang.reflect.Modifier;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 静态脚本工厂
 * 当不需要使用到热重载脚本需求时,可直接使用静态脚本注册
 * @author juebanlin
 */
public abstract class StaticScriptClassFactory<T extends IScript> implements IScriptFactory<T>{
	
	protected final Logger _log = LoggerFactory.getLogger(this.getClass());
	protected final Map<Integer, Class<? extends T>> staticCodeMap = new ConcurrentHashMap<Integer, Class<? extends T>>(); 
	protected class StaticScriptRegister{
		
		protected final StaticScriptClassFactory<T> factory;
		
		public StaticScriptRegister(StaticScriptClassFactory<T> factory) {
			super();
			this.factory = factory;
		}
		
		/**
		 * 注册静态脚本,此脚本不会被动态加载类覆盖
		 * @param scriptClass
		 */
		public final void registStaticScript(Class<? extends T> scriptClass) {
			factory.registStaticScript(scriptClass);
		}
	}
	
	public StaticScriptClassFactory() {
		super();
		init();
	}
	
	private void init()
	{
		initStaticScriptRegist(new StaticScriptRegister(this));
	}
	
	/**
	 * 注册静态脚本,此脚本不可被动态脚本覆盖
	 * @param reg
	 */
	protected abstract void initStaticScriptRegist(StaticScriptRegister reg);
	
	/**
	 * 注册静态脚本
	 * @param scriptClass
	 */
	protected final void registStaticScript(Class<? extends T> scriptClass)
	{
		try {
			boolean isAbstractOrInterface = Modifier.isAbstract(scriptClass.getModifiers())
					|| Modifier.isInterface(scriptClass.getModifiers());// 是否是抽象类
			if(isAbstractOrInterface)
			{
				throw new UnsupportedOperationException(scriptClass +"can not newInstance");
			}
			T script = scriptClass.newInstance();
			int code=script.getMessageCode();
			Class<? extends T> old=staticCodeMap.get(code);
			staticCodeMap.put(code,scriptClass);
			if(old==null)
			{
				_log.info("regist Static codeScript,code="+code+"(0x"+Integer.toHexString(code)+"),class="+scriptClass);
			}else
			{
				_log.info("regist Static codeScript,code="+code+"(0x"+Integer.toHexString(code)+"),class="+scriptClass+",Override script="+old);
			}
		} catch (Exception e) {
			_log.error(e.getMessage(),e);
		}
	}
	
	protected final Class<? extends T> getStaticScriptClass(int code)
	{
		return staticCodeMap.get(code);
	}
	
	protected final T newInstance(Class<? extends T> c,Object... args) {
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
	
	protected final T newInstance(Class<? extends T> c) {
		T result = null;
		try {
			result = c.newInstance();
		} catch (Exception e) {
			_log.error(e.getMessage(), e);
		}
		return result;
	}
	
	@Override
	public T buildInstance(int code) {
		T result=null;
		Class<? extends T> c = getStaticScriptClass(code);
		if(c==null)
		{
			_log.error("not found script,code=" + code+"(0x"+Integer.toHexString(code)+")");
		}else
		{
			result=newInstance(c);
		}
		return result;
	}

	@Override
	public T buildInstance(int code,Object ...args) {
		T result = null;
		Class<? extends T> c = getStaticScriptClass(code);
		if (c == null) {
			_log.error("not found script,code=" + code + "(0x" + Integer.toHexString(code) + ")");
		} else 
		{
			result=newInstance(c,args);
		}
		return result;
	}

	@Override
	public void reload() {
		
	}
}
