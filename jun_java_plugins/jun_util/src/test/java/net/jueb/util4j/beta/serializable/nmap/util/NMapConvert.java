package net.jueb.util4j.beta.serializable.nmap.util;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import net.jueb.util4j.beta.serializable.nmap.type.NMap;
import net.jueb.util4j.beta.serializable.nmap.type.NNull;
import net.jueb.util4j.beta.serializable.nmap.type.NType;

import java.util.TreeMap;

public class NMapConvert {

	private HashMap<Class<?>,Class<?>> map;
	private final NMap nmap;
	public NMapConvert(NMap nmap) {
		if(nmap==null)
		{
			throw new RuntimeException("nmap==null");
		}
		this.nmap=nmap;
		initTypes();
	}
	
	private void initTypes()
	{
		//以obj为key
		map=new HashMap<Class<?>, Class<?>>();
		for(Entry<Byte,NType<?>> kv:nmap.getRegisgHeads().entrySet())
		{	//保存NType的封装Obj类型和自身类型的map
			NType<?> ntype=kv.getValue();
			if(ntype instanceof NNull)
			{//注意,null要特别判断
				//null用的0xFF标记，不能用来判断，会影响映射关系
			}else
			{
				map.put(ntype.getObjectValue().getClass(), ntype.getClass());
			}
		}
	}
	
	/**
	 * 将nmap对象转换为object到目标map
	 * 目标map将被清空
	 * @param nmap
	 * @param map
	 */
	public final void toTreeMap(NMap nmap,TreeMap<Object,Object> map)
	{
		map.clear();
		Set<Entry<NType<?>, NType<?>>> set=nmap.entrySet();
		for(Entry<NType<?>, NType<?>> kv:set)
		{
			map.put(toTreeMapObject(kv.getKey()),toTreeMapObject(kv.getValue()));
		}
	}
	
	
	public final Object toTreeMapObject(NType<?> ntype)
	{
		if(ntype instanceof NNull)
		{
			return null;
		}
		else if(ntype instanceof NMap)
		{
			TreeMap<Object,Object> tmap=new TreeMap<Object, Object>();
			toTreeMap((NMap) ntype,tmap);
			return tmap;
		}
		return ntype.getConvertValue();
	}
	/**
	 * 根据参数map以及NMap注册的类型，转换为一个新的NMap类型
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@Deprecated
	public  final NMap toNMap(Map<Object,Object> map) throws Exception
	{
		final NMap nmap=new NMap();
		Set<Entry<Object, Object>> set=map.entrySet();
		for(Entry<Object, Object> kv:set)
		{
			nmap.put(toNtype(kv.getKey()),toNtype(kv.getValue()));
		}
		return nmap;
	}
	/**
	 * String类型全部用NUTF8代替
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@Deprecated
	@SuppressWarnings("unchecked")
	public final NType<?> toNtype(Object obj) throws Exception
	{
		if(obj==null)
		{
			return new NNull();
		}
		if(map.containsKey(obj.getClass()))
		{//如果有该类型的封装类型
			if(obj instanceof Map)
			{//如果是map类型，则
				return toNMap((Map<Object, Object>) obj);
			}
			Class<? extends NType<?>> c=(Class<? extends NType<?>>) map.get(obj.getClass());//拿到包装类对应的ntype类型
			Constructor<?>[] cons=c.getConstructors();
			for(Constructor<?> con:cons)
			{
				if(con.getParameterAnnotations().length==1)
				{//当子类存在构造参数的构造器时
					NType<?> ntype=c.getConstructor(con.getParameterTypes()[0]).newInstance(obj);
					return ntype;
				}
			}
		}
		throw new RuntimeException(obj+"["+obj.getClass().toString()+"]"+"转换为NType类型异常!");
	}
}
