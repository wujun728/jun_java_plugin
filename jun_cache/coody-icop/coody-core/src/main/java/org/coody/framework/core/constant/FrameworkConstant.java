package org.coody.framework.core.constant;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.coody.framework.core.entity.AspectEntity;

public class FrameworkConstant {
	
	/**
	 * 切面存储。key为切面ID，Value为切面实例
	 */
	public static final Map<String, List<AspectEntity>> ASPECT_MAP = new ConcurrentHashMap<String, List<AspectEntity>>();
	
	
	public static void writeToAspectMap(String methodKey,AspectEntity aspectEntity){
		if(FrameworkConstant.ASPECT_MAP.containsKey(methodKey)){
			FrameworkConstant.ASPECT_MAP.get(methodKey).add(aspectEntity);
			return;
		}
		List<AspectEntity> aspectEntitys=new ArrayList<AspectEntity>();
		aspectEntitys.add(aspectEntity);
		FrameworkConstant.ASPECT_MAP.put(methodKey, aspectEntitys);
	}
}
