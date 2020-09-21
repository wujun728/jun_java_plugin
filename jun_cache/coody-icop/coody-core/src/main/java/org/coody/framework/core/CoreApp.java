package org.coody.framework.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.coody.framework.core.annotation.Order;
import org.coody.framework.core.exception.base.IcopException;
import org.coody.framework.core.loader.AspectLoader;
import org.coody.framework.core.loader.BeanLoader;
import org.coody.framework.core.loader.FieldLoader;
import org.coody.framework.core.loader.InitRunLoader;
import org.coody.framework.core.loader.iface.IcopLoader;
import org.coody.framework.core.logger.BaseLogger;
import org.coody.framework.core.util.ClassUtil;
import org.coody.framework.core.util.StringUtil;

public class CoreApp {
	
	static BaseLogger logger=BaseLogger.getLogger(CoreApp.class);
	
	@SuppressWarnings("serial")
	static Map<Integer,List<Class<?>>> loadersMap=new TreeMap<Integer, List<Class<?>>>(){{
		put(1, Arrays.asList(new Class<?>[]{AspectLoader.class}));
		put(2, Arrays.asList(new Class<?>[]{BeanLoader.class}));
		put(3, Arrays.asList(new Class<?>[]{FieldLoader.class}));
		put(Integer.MAX_VALUE, Arrays.asList(new Class<?>[]{InitRunLoader.class}));
	}};
	
	public static void pushLoader(Class<?> loader){
		if(!IcopLoader.class.isAssignableFrom(loader)){
			throw new IcopException(loader.getName()+"不是加载器");
		}
		Integer seq=Integer.MAX_VALUE-1;
		Order order=loader.getClass().getAnnotation(Order.class);
		if(order!=null){
			seq=order.value();
		}
		if(loadersMap.containsKey(seq)){
			loadersMap.get(seq).add(loader);
			return;
		}
		List<Class<?>> loaderList=new ArrayList<Class<?>>();
		loaderList.add(loader);
		loadersMap.put(seq, loaderList);
	}

	public static void init(String... packets) throws Exception {
		
		List<String> packetArgs=new ArrayList<String>(Arrays.asList(packets));
		packetArgs.add("org.coody.framework");
		Set<Class<?>> clazzs = new HashSet<Class<?>>();
		for (String packet : packetArgs) {
			Set<Class<?>> clazzsTemp = ClassUtil.getClasses(packet);
			clazzs.addAll(clazzsTemp);
		}
		if (StringUtil.isNullOrEmpty(clazzs)) {
			return;
		}
		List<Class<?>> currentLoaders=new ArrayList<Class<?>>();
		for(Integer key:loadersMap.keySet()){
			for(Class<?> clazz:loadersMap.get(key)){
				if(currentLoaders.contains(clazz)){
					continue;
				}
				currentLoaders.add(clazz);
			}
		}
		long tInit=System.currentTimeMillis();
		for(Class<?> loader:currentLoaders){
			logger.info(loader.getName()+" >>开始加载");
			long t0=System.currentTimeMillis();
			IcopLoader icopLoader=(IcopLoader) loader.newInstance();
			icopLoader.doLoader(clazzs);
			long t1=System.currentTimeMillis();
			logger.info(loader.getName()+" >>加载耗时:"+(t1-t0)+"ms");
		}
		long tEnd=System.currentTimeMillis();
		logger.info("Coody Framework >>加载耗时:"+(tEnd-tInit)+"ms");
	}

}
