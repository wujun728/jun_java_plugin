package com.jun.plugin.util4j.common.game;

import java.lang.reflect.Field;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 配置属性映射
 * @author juebanlin
 */
public abstract class PropertiesMapping {
	
	protected  Logger log=LoggerFactory.getLogger(getClass());		
	
	private final void setFiled(Field f,String value) throws Exception
	{
		Class<?> type=f.getType();
		if(type==String.class)
		{
			f.set(this, value);
			return ;
		}
		if(value==null || value.trim().isEmpty())
		{//字符串不转换为非字符串的属性
			log.warn("filedValue is empty,name="+f.getName()+",type="+type+",value="+value);
			return ;
		}
		if(type==Integer.class || type==int.class)
		{
			f.set(this, Integer.parseInt(value));
			return ;
		}
		if(type==Long.class || type==long.class)
		{
			f.set(this,Long.parseLong(value));
			return ;
		}
		if(type==Double.class || type==double.class)
		{
			f.set(this,Double.parseDouble(value));
			return ;
		}
		if(type==Float.class || type==float.class)
		{
			f.set(this,Float.parseFloat(value));
			return ;
		}
		if(type==Boolean.class || type==boolean.class)
		{
			f.set(this,Boolean.parseBoolean(value));
			return ;
		}
		if(type==Byte.class || type==byte.class)
		{
			f.set(this,Byte.parseByte(value));
			return ;
		}
		if(type==Short.class || type==short.class)
		{
			f.set(this,Short.parseShort(value));
			return ;
		}
		log.warn("no type mapping filed,name="+f.getName()+",type="+type+",value="+value);
	}
	
	/**
	 * getFields()只能访问类中声明为公有的字段,私有的字段它无法访问，能访问从其它类继承来的公有方法.
	 * @param ps
	 */
	public final void loadPublic(Properties ps)
	{
		//表示如果Field是static的，则obj即便给它传值，JVM也会忽略的。还说明了，此入参在这种情况下可以为null
		for(Field f:getClass().getFields())
		{
			try {
				setFiled(f,ps.getProperty(f.getName()));
			} catch (Exception e) {
				log.error(e.getMessage(),e);
			}
		}
	}

	/**
	 * getDeclaredFields()能访问类中所有的字段,与public,private,protect无关，不能访问从其它类继承来的方法  
	 * @param ps
	 */
	public final void loadAll(Properties ps)
	{
		//表示如果Field是static的，则obj即便给它传值，JVM也会忽略的。还说明了，此入参在这种情况下可以为null
		for(Field f:getClass().getDeclaredFields())
		{
			try {
				setFiled(f,ps.getProperty(f.getName()));
			} catch (Exception e) {
				log.error(e.getMessage(),e);
			}
		}
	}
}
