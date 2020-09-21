package org.coody.framework.web.adapt.dispat;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.coody.framework.web.adapt.iface.IcopParamsAdapt;


public class DispatAdapt {

	private static final Map<Class<?>, IcopParamsAdapt> ADAPT_MAP=new ConcurrentHashMap<Class<?>, IcopParamsAdapt>();
	
	
	
	public static IcopParamsAdapt getAdapt(Class<?> clazz) throws InstantiationException, IllegalAccessException{
		if(ADAPT_MAP.containsKey(clazz)){
			return ADAPT_MAP.get(clazz);
		}
		IcopParamsAdapt adapt=(IcopParamsAdapt) clazz.newInstance();
		ADAPT_MAP.put(clazz, adapt);
		return adapt;
	}
}
