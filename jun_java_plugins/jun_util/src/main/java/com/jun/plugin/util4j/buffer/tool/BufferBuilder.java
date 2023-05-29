package com.jun.plugin.util4j.buffer.tool;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.function.Predicate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BufferBuilder{

	protected Logger log=LoggerFactory.getLogger(getClass());
	
	private final String bufferClass;
	private final String writeMethodName;
	private final String readMethodName;
	
	private final List<Predicate<Field>> fieldFilter=new ArrayList<>();
	
	private final List<TypeHandler> typeHandler=new ArrayList<>();
	
	public BufferBuilder(String bufferClass, String writeMethodName, String readMethodName) {
		super();
		this.bufferClass = bufferClass;
		this.writeMethodName = writeMethodName;
		this.readMethodName = readMethodName;
	}
	
	/**
	 * 增加属性过滤器
	 * @param filter
	 */
	public void addFieldSkipFilter(Predicate<Field> filter)
	{
		fieldFilter.add(filter);
	}
	
	/**
	 * 增加类型处理
	 * @param typeHandler
	 */
	public void addTypeHandler(TypeHandler typeHandler)
	{
		this.typeHandler.add(typeHandler);
	}
	
	public void build(Class<?> clazz,StringBuilder writesb,StringBuilder readsb)throws Exception
	{
		Class<?> buffClass=Thread.currentThread().getContextClassLoader().loadClass(bufferClass);
		String buffClassSimpleName=buffClass.getSimpleName();
		writesb.append("\t").append("@Override").append("\n");
		writesb.append("\t").append("public void "+writeMethodName+"("+buffClassSimpleName+" buffer) {").append("\n");
		readsb.append("\t").append("@Override").append("\n");
		readsb.append("\t").append("public void "+readMethodName+"("+buffClassSimpleName+" buffer) {").append("\n");
		if (clazz.getSuperclass() != null) {
			try {
				clazz.getSuperclass().getMethod(writeMethodName,buffClass);
				writesb.append("\t").append("super."+writeMethodName+"(buffer);").append("\n");
			} catch (NoSuchMethodException ex) {
			}
			try {
				clazz.getSuperclass().getMethod(readMethodName,buffClass);
				readsb.append("\t").append("super."+readMethodName+"(buffer);").append("\n");
			} catch (NoSuchMethodException ex) {
			}
		}
		Field[] fields = clazz.getDeclaredFields();
		for(Field field: fields)
		{
			if(skipField(field))
			{
				log.warn(clazz.getSimpleName()+"==>skipField:"+field.getName());
				continue;
			}
			StringBuilder write = new StringBuilder();
			StringBuilder read = new StringBuilder();
			try {
				write.append("\t").append("//field->"+field.getName()).append("\n");
				read.append("\t").append("//field->"+field.getName()).append("\n");
				readWriteField(field, write, read);
				writesb.append(write.toString());
				readsb.append(read.toString());
			} catch (Exception e) {
				log.error(clazz.getSimpleName()+"==>buildFieldError:field="+field.getName()+",error="+e.getMessage());
			}
		}
		writesb.append("\t").append("}").append("\n");
		readsb.append("\t").append("}").append("\n");
	}
	
	public boolean skipField(Field field)
	{
		for(Predicate<Field> f:fieldFilter)
		{
			if(f.test(field))
			{
				return true;
			}
		}
		return false;
	}

	/**
	 * 读写属性
	 * @param field
	 * @param writesb
	 * @param readsb
	 */
	public void readWriteField(Field field,StringBuilder writesb,StringBuilder readsb)
	{
		Class<?> type=field.getType();
		String varName=field.getName();
		//泛型类型
		Type[] actTypes=new Type[]{};
		if(field.getGenericType() instanceof ParameterizedType)
		{
			actTypes=((ParameterizedType)field.getGenericType()).getActualTypeArguments();
		}
		//变量声明
		declearVar(type, varName, writesb, readsb,actTypes);
		if(type.isPrimitive() && type.equals(boolean.class))
		{
			writesb.append("\t").append(varName+"=is"+fieldUper(field.getName())+"();").append("\n");
		}else
		{
			writesb.append("\t").append(varName+"=get"+fieldUper(field.getName())+"();").append("\n");
		}
		readWriteVar(type, varName, writesb, readsb,true,actTypes);//读写属性变量
		readsb.append("\t").append("set" + fieldUper(field.getName()) + "(" + varName + ");").append("\n");
	}

	/**
	 * 获取变量类型名称
	 */
	public String getVarTypeName(Class<?> varType)
	{
		String str=varType.getSimpleName();
		if(varType.isPrimitive())
		{
			str=varType.getName();
		}
		str=str.replace('$','.');
		return str;
	}
	
	/**
	 * 获取带泛型的类型名称
	 * @param type
	 * @param actTypes
	 * @return
	 */
	public String getVarTypeName(Class<?> type,Type ...actTypes)
	{
		String varType=getVarTypeName(type);
		if(actTypes!=null)
		{
			List<String> acts=new ArrayList<>();
			for(Type act:actTypes)
			{
				acts.add(act.getTypeName());
			}
			if(!acts.isEmpty())
			{
				if(acts.size()==1)
				{
					varType+="<"+acts.remove(0)+">";
				}else
				{
					varType+="<"+String.join(",", acts)+">";
				}
			}
		}
		varType=varType.replace('$','.');
		return varType;
	}
	
	/**
	 * 变量声明
	 * @param type
	 * @param varName
	 */
	public void declearVar(Class<?> type,String varName,StringBuilder writesb,StringBuilder readsb,Type ...actTypes)
	{
		String varType=getVarTypeName(type, actTypes);
		//变量声明
		if(type.isPrimitive())
		{
			if(type.equals(boolean.class))
			{
				writesb.append("\t").append(varType+" "+varName+"=false;").append("\n");
			}else
			{
				writesb.append("\t").append(varType+" "+varName+"=0;").append("\n");
				readsb.append("\t").append(varType+" "+varName+"=0;").append("\n");
			}
		}else
		{
			writesb.append("\t").append(varType+" "+varName+"=null;").append("\n");
			readsb.append("\t").append(varType+" "+varName+"=null;").append("\n");
		}
	}
	
	/**
	 * 读写变量
	 * @param type
	 * @param varName
	 * @param writesb
	 * @param readsb
	 */
	public void readWriteVar(Class<?> type,String varName,StringBuilder writesb,StringBuilder readsb,boolean nullCheck,Type ...actualType)
	{
//		if(type.isArray())
//		{//数组不支持泛型
//			Class<?> ctype=type.getComponentType();
//			String varTypeName=getVarTypeName(type);
//			String i=varName+"_i";
//			String var_data=varName+"_d";
//			String var_dataTypeName=getVarTypeName(ctype);
//			String var_len=varName+"_l";
//			
//			if(nullCheck)
//			{
//				writesb.append("\t").append("if (" + varName + "!=null){").append("\n");
//				writesb.append("\t").append("buffer.writeBoolean(true);").append("\n");
//				
//				readsb.append("\t").append("if (buffer.readBoolean()){").append("\n");
//			}
//			writesb.append("\t").append("int "+var_len+"="+varName+".length;").append("\n");
//			writesb.append("\t").append("buffer.writeInt("+var_len+");").append("\n");
//			writesb.append("\t").append("for(int "+i +"=0;" +i+"<"+var_len+";"+i+"++){").append("\n");
//			writesb.append("\t").append(var_dataTypeName+" "+var_data+"="+varName+"["+i+"];").append("\n");
//			
//			readsb.append("\t").append("int "+var_len+"=buffer.readInt();").append("\n");
//			readsb.append("\t").append(varName+"=("+varTypeName+") java.lang.reflect.Array.newInstance("+var_dataTypeName+".class,"+var_len+");").append("\n");
//			readsb.append("\t").append("for(int "+i +"=0;" +i+"<"+var_len+";"+i+"++){").append("\n");
//			readsb.append("\t").append(var_dataTypeName+" "+var_data+";").append("\n");
//			//读写变量
//			readWriteVar_Base(ctype, var_data, writesb, readsb, false);
//			//设置值
//			readsb.append("\t").append(varName+"["+i+"]="+var_data+";").append("\n");
//			
//			writesb.append("\t").append("}").append("\n");
//			readsb.append("\t").append("}").append("\n");
//			
//			if(nullCheck)
//			{
//				writesb.append("\t").append("}else{");
//				writesb.append("\t").append("buffer.writeBoolean(false);}").append("\n");
//				
//				readsb.append("\t").append("}").append("\n");
//			}
//			return ;
//		}
		if(Map.class.isAssignableFrom(type))
		{
			Type keyType_=actualType[0];
			Type valueType_=actualType[1];
			Class<?> keyType=null;
			Class<?> valueType=null;
			Type[] keyTypeActs=new Type[]{};
			Type[] valueTypeActs=new Type[]{};
			if(keyType_ instanceof ParameterizedType) 
			{
				ParameterizedType pt=(ParameterizedType)keyType_;
				keyType=(Class<?>)pt.getRawType();
				keyTypeActs=pt.getActualTypeArguments();
			}else
			{
				keyType=(Class<?>)keyType_;
			}
			if(valueType_ instanceof ParameterizedType) 
			{
				ParameterizedType pt=(ParameterizedType)valueType_;
				valueType=(Class<?>)pt.getRawType();
				valueTypeActs=pt.getActualTypeArguments();
			}else
			{
				valueType=(Class<?>)valueType_;
			}
			String var_mapSize=varName+"_ms";
			String var_mapI=varName+"_mi";
			String var_mapKey=varName+"_mk";
			String var_mapValue=varName+"_mv";
			
			String var_mapKeyType=getVarTypeName(keyType,keyTypeActs);
			String var_mapValueType=getVarTypeName(valueType,valueTypeActs);
			String varInstanceType="java.util.HashMap";
			
			if(nullCheck)
			{
				writesb.append("\t").append("if (" + varName + "!=null){").append("\n");
				writesb.append("\t").append("buffer.writeBoolean(true);").append("\n");
				
				readsb.append("\t").append("if (buffer.readBoolean()){").append("\n");
			}
			writesb.append("\t").append("int "+var_mapSize+"="+varName+".size();").append("\n");
			writesb.append("\t").append("buffer.writeInt("+var_mapSize+");").append("\n");
			writesb.append("\t").append("for("+var_mapKeyType+" "+var_mapKey +":" +varName+".keySet()){").append("\n");
			writesb.append("\t").append(var_mapValueType+" "+var_mapValue+"="+varName+".get("+var_mapKey+");").append("\n");
			
			readsb.append("\t").append("int "+var_mapSize+"=buffer.readInt();").append("\n");
			readsb.append("\t").append(varName+"=new "+varInstanceType+"<>();").append("\n");
			readsb.append("\t").append("for(int "+var_mapI +"=0;" +var_mapI+"<"+var_mapSize+";"+var_mapI+"++){").append("\n");
				
			//声明变量
			readsb.append("\t").append(var_mapKeyType+" "+var_mapKey+";").append("\n");
			readsb.append("\t").append(var_mapValueType+" "+var_mapValue+";").append("\n");
			
			//读写变量
			if(keyTypeActs.length>0)
			{
				readWriteVar(keyType, var_mapKey, writesb, readsb, false,keyTypeActs);
			}else
			{
				readWriteVar_Base(keyType, var_mapKey, writesb, readsb, false);
			}
			if(valueTypeActs.length>0)
			{
				readWriteVar(valueType, var_mapValue, writesb, readsb, false,valueTypeActs);
			}else
			{
				readWriteVar_Base(valueType, var_mapValue, writesb, readsb, false);
			}
			
			//设置值
			readsb.append("\t").append(varName+".put("+var_mapKey+","+var_mapValue+");").append("\n");
			
			writesb.append("\t").append("}").append("\n");
			readsb.append("\t").append("}").append("\n");
			
		    if(nullCheck)
			{
				writesb.append("\t").append("}else{");
				writesb.append("\t").append("buffer.writeBoolean(false);}").append("\n");
				
				readsb.append("\t").append("}").append("\n");
			}
			return ;
		}
		if(Collection.class.isAssignableFrom(type))
		{
			Type valueType_=actualType[0];
			Class<?> valueType=null;
			Type[] valueTypeActs=new Type[]{};
			if(valueType_ instanceof ParameterizedType) 
			{
				ParameterizedType pt=(ParameterizedType)valueType_;
				valueType=(Class<?>)pt.getRawType();
				valueTypeActs=pt.getActualTypeArguments();
			}else
			{
				valueType=(Class<?>)valueType_;
			}
			
			String var_listSize=varName+"_ls";
			String var_listI=varName+"_li";
			String var_listValue=varName+"_lv";
			String var_listValueType=getVarTypeName(valueType,valueTypeActs);
			
			String varInstanceType="java.util.ArrayList";
			if(List.class.isAssignableFrom(type))
			{
				varInstanceType="java.util.ArrayList";
			}
			if(Queue.class.isAssignableFrom(type))
			{
				varInstanceType="java.util.concurrent.ConcurrentLinkedQueue";
			}
			if(Set.class.isAssignableFrom(type))
			{
				varInstanceType="java.util.HashSet";
			}
			if(nullCheck)
			{
				writesb.append("\t").append("if (" + varName + "!=null){").append("\n");
				writesb.append("\t").append("buffer.writeBoolean(true);").append("\n");
				
				readsb.append("\t").append("if (buffer.readBoolean()){").append("\n");
			}
			writesb.append("\t").append("int "+var_listSize+"="+varName+".size();").append("\n");
			writesb.append("\t").append("buffer.writeInt("+var_listSize+");").append("\n");
			writesb.append("\t").append("for("+var_listValueType +" "+var_listValue+":" +varName+"){").append("\n");
			
			readsb.append("\t").append("int "+var_listSize+"=buffer.readInt();").append("\n");
			
			readsb.append("\t").append(varName+"=new "+varInstanceType+"<>();").append("\n");
			
			readsb.append("\t").append("for(int "+var_listI +"=0;" +var_listI+"<"+var_listSize+";"+var_listI+"++){").append("\n");
				
			//声明变量
			readsb.append("\t").append(var_listValueType+" "+var_listValue+";").append("\n");
			
			//读写变量
			if(valueTypeActs.length>0)
			{
				readWriteVar(valueType, var_listValue, writesb, readsb, false,valueTypeActs);
			}else
			{
				readWriteVar_Base(valueType, var_listValue, writesb, readsb, false);
			}
			
			//设置值
			readsb.append("\t").append(varName+".add("+var_listValue+");").append("\n");
			
			writesb.append("\t").append("}").append("\n");
			readsb.append("\t").append("}").append("\n");
			
		    if(nullCheck)
			{
				writesb.append("\t").append("}else{");
				writesb.append("\t").append("buffer.writeBoolean(false);}").append("\n");
				
				readsb.append("\t").append("}").append("\n");
			}
			return ;
		}
		//简单类型
		readWriteVar_Base(type, varName, writesb, readsb, nullCheck);
	}

	
	/**
	 * 简单变量读写
	 * @param type
	 * @param varName
	 * @param writesb
	 * @param readsb
	 * @param nullCheck
	 */
	public void readWriteVar_Base(Class<?> type,String varName,StringBuilder write,StringBuilder read,boolean nullCheck)
	{
		if (type.isPrimitive()) 
		{// 标准类型
			String typeName = type.getName();
			typeName = typeName.substring(0, 1).toUpperCase() + typeName.substring(1);// 类型
			write.append("\t").append("buffer.write"+typeName+"(" + varName + ");").append("\n");
			read.append("\t").append(varName+"=buffer.read"+typeName+"();").append("\n");
			return ;
		}
		StringBuilder writesb=new StringBuilder();
		StringBuilder readsb=new StringBuilder();
		if(nullCheck)
		{
			writesb.append("\t").append("if (" + varName + "!=null){").append("\n");
			writesb.append("\t").append("buffer.writeBoolean(true);").append("\n");
			
			readsb.append("\t").append("if (buffer.readBoolean()){").append("\n");
		}
		boolean match=false;
		if(!match && type.isArray())
		{//数组不支持泛型
			Class<?> ctype=type.getComponentType();
			String varTypeName=getVarTypeName(type);
			String i=varName+"_i";
			String var_data=varName+"_d";
			String var_dataTypeName=getVarTypeName(ctype);
			String var_len=varName+"_l";
			writesb.append("\t").append("int "+var_len+"="+varName+".length;").append("\n");
			writesb.append("\t").append("buffer.writeInt("+var_len+");").append("\n");
			writesb.append("\t").append("for(int "+i +"=0;" +i+"<"+var_len+";"+i+"++){").append("\n");
			writesb.append("\t").append(var_dataTypeName+" "+var_data+"="+varName+"["+i+"];").append("\n");
			
			readsb.append("\t").append("int "+var_len+"=buffer.readInt();").append("\n");
			readsb.append("\t").append(varName+"=("+varTypeName+") java.lang.reflect.Array.newInstance("+var_dataTypeName+".class,"+var_len+");").append("\n");
			readsb.append("\t").append("for(int "+i +"=0;" +i+"<"+var_len+";"+i+"++){").append("\n");
			readsb.append("\t").append(var_dataTypeName+" "+var_data+";").append("\n");
			//读写变量
			readWriteVar_Base(ctype, var_data, writesb, readsb, false);
			//设置值
			readsb.append("\t").append(varName+"["+i+"]="+var_data+";").append("\n");
			
			writesb.append("\t").append("}").append("\n");
			readsb.append("\t").append("}").append("\n");
			match=true;
		}
		if(!match && type.isEnum())
		{//枚举类型
			String typeName = getVarTypeName(type);
			writesb.append("\t").append("buffer.writeUTF(" + varName + ".name());").append("\n");
			readsb.append("\t").append(varName+"="+typeName+".valueOf(buffer.readUTF());").append("\n");
			match=true;
		}
		if(!match && String.class.isAssignableFrom(type))
		{
			writesb.append("\t").append("buffer.writeUTF(" + varName + ");").append("\n");
			readsb.append("\t").append(varName+"=buffer.readUTF();").append("\n");
			match=true;
		}
		if(!match && Boolean.class.isAssignableFrom(type))
		{
			writesb.append("\t").append("buffer.writeBoolean(" + varName + ");").append("\n");
			readsb.append("\t").append(varName+"=buffer.readBoolean();").append("\n");
			match=true;
		}
		if(!match && Byte.class.isAssignableFrom(type))
		{
			writesb.append("\t").append("buffer.writeByte(" + varName + ");").append("\n");
			readsb.append("\t").append(varName+"=buffer.readByte();").append("\n");
			match=true;
		}
		if(!match && Short.class.isAssignableFrom(type))
		{
			writesb.append("\t").append("buffer.writeShort(" + varName + ");").append("\n");
			readsb.append("\t").append(varName+"=buffer.readShort();").append("\n");
			match=true;
		}
		if(!match && Integer.class.isAssignableFrom(type))
		{
			writesb.append("\t").append("buffer.writeInt(" + varName + ");").append("\n");
			readsb.append("\t").append(varName+"=buffer.readInt();").append("\n");
			match=true;
		}
		if(!match && Long.class.isAssignableFrom(type))
		{
			writesb.append("\t").append("buffer.writeLong(" + varName + ");").append("\n");
			readsb.append("\t").append(varName+"=buffer.readLong();").append("\n");
			match=true;
		}
		if(!match && Double.class.isAssignableFrom(type))
		{
			writesb.append("\t").append("buffer.writeDouble(" + varName + ");").append("\n");
			readsb.append("\t").append(varName+"=buffer.readDouble();").append("\n");
			match=true;
		}
		if(!match && Float.class.isAssignableFrom(type))
		{
			writesb.append("\t").append("buffer.writeFloat(" + varName + ");").append("\n");
			readsb.append("\t").append(varName+"=buffer.readFloat();").append("\n");
			match=true;
		}
//		if(ISqlTableData.class.isAssignableFrom(type))
//		{//自定义类型
//			String ClassName=type.getSimpleName();
//			writesb.append("\t").append(varName+"."+writeMethodName+"(buffer);").append("\n");
//			
//			readsb.append("\t").append(varName +"=new "+ClassName+"();").append("\n");
//			readsb.append("\t").append(varName + "."+readMethodName+"(buffer);").append("\n");
//			match=true;
//		}
		if(!match)
		{//没有匹配,可能是集合中的复合类型
			boolean varType=type.isArray();
			if(varType)
			{
				readWriteVar(type, varName, writesb, readsb, nullCheck);
			}else
			{
				StringBuilder tmpRead=new StringBuilder();
				StringBuilder tmpWrite=new StringBuilder();
				Context ctx=new Context(){
					@Override
					public Class<?> varType() {
						return type;
					}
					@Override
					public String varName() {
						return varName;
					}
					@Override
					public String varBuffer() {
						return "buffer";
					}
					@Override
					public StringBuilder read() {
						return tmpRead;
					}
					@Override
					public StringBuilder write() {
						return tmpWrite;
					}
					@Override
					public String writeMethodName() {
						return writeMethodName;
					}
					@Override
					public String readMethodName() {
						return readMethodName;
					}
				};
				for(TypeHandler t:typeHandler)
				{
					match=t.handle(ctx);
					if(match)
					{
						break;
					}
				}
				if(match)
				{
					writesb.append(tmpWrite.toString());
					readsb.append(tmpRead.toString());
				}else
				{
					throw new RuntimeException("unSupported type:"+type+",varName:"+varName);
				}
			}
		}
		if(nullCheck)
		{
			writesb.append("\t").append("}else{");
			writesb.append("\t").append("buffer.writeBoolean(false);}").append("\n");
			
			readsb.append("\t").append("}").append("\n");
		}
		write.append(writesb.toString());
		read.append(readsb.toString());
	}
	
	@FunctionalInterface
	public static interface TypeHandler{
		
		public boolean handle(Context context);
	}
	
	public static interface Context{
		Class<?> varType();
		String varName();
		String varBuffer();
		StringBuilder read();
		StringBuilder write();
		String writeMethodName();
		String readMethodName();
	}
	
	protected boolean isAbstract(Class<?> type)
	{
		return Modifier.isAbstract(type.getModifiers());
	}

	/**
	 * 属性名首字母大写
	 * @param filedName
	 * @return
	 */
	protected String fieldUper(String filedName)
	{
		return filedName.substring(0, 1).toUpperCase() + filedName.substring(1);// 类型
	}
}
