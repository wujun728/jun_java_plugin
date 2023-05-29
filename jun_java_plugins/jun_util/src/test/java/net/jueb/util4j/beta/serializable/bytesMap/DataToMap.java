package net.jueb.util4j.beta.serializable.bytesMap;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DataToMap extends DataToMapBase{
	protected Logger log = LoggerFactory.getLogger(getClass());
	private Map<Object, Object> map=new TreeMap<Object, Object>();
	private DataTools dt=new DataTools();
	private byte[] data;
	private int p;
	
	public DataToMap()
	{
		
	}
	/**
	 * 返回解析后的数据
	 * @return
	 * @throws IOException
	 */
	public Map<Object, Object> getMap(byte[] data) throws IOException
	{
		this.data=data;
		if(data==null||data.length<=0)
		{
			log.error("data没有数据!");
			return map;
		}
		p=0;
		int type=getType();
		if(type!=_OBJ)
		{
			log.debug("第一个字节非08类型!:"+new String(data,"UTF8"));
			return map;
		}
		start(type);//从第一个位置开始读取
		return map;
	}
	/**
	 * 根据开始类执行解析
	 * @param type
	 * @throws IOException
	 */
	private void start(int type) throws IOException
	{
		//根据类型读取内容
		readObject(type,this.map);
	}
	/**
	 * 从某个map对象开始读取，P=map对象标记符位置
	 * 会读取map对象后面的map容量
	 * 根据对象类型执行响应的方法
	 * @param p Oject标记的位置
	 * @throws IOException 
	 */
	private void readObject(int objType,Map<Object, Object> map) throws IOException
	{
		switch (objType) {
		case _OBJ://凡事P=08的都会进来
				//如果p位置是此类型，则开始判断类型种类
				//读取当前(08类型)p后面的四个字节
			int num=readInt();
			String phex=Integer.toHexString(p).toUpperCase();
			log.debug("发现Map对象,容量:"+num+",当前p位置:"+p+"/"+phex);
			//08后面任何子类型都可以进来
			readMap(map,num);
			break;
		default://不是08类型的则跳出
			System.out.println("##############################");
			break;
		}
	}
	/**
	 * 从08后面的第一个键值对开始读取
	 * @param map
	 * @param num 该08类型map的容量
	 * @throws IOException
	 */
	private void readMap(Map<Object, Object> map,int num) throws IOException
	{
		for(int i=1;i<=num;i++)
		{
			byte keyType;
			//将指针移动到类型种类后下一个类型索引处，
			//比如08 04 00 00 00 06中的06处
			p++;//每次map遍历开始，先判断第一个key的类型是否可以拿到
			if(!canGetNext())
			{//判断是否可以拿keyType
				log.debug("已经到达末尾,没有下一个key类型了!");
				break;
			}
			keyType=getType();//获取第一次遇到的类型
			log.debug("第"+i+"个类型:"+keyType);
			Object key=readObjectKey();//此时p在对象后面的key类型处
			String phex=Integer.toHexString(p).toUpperCase();
			log.debug("拿到第"+i+"个key:"+key+",p位置:"+p+"/"+phex);
			
			p++;
			if(!canGetNext())
			{//判断是否可以拿valueType
				log.debug("已经到达末尾,不能拿"+key+"的valueType类型!");
				break;
			}
			byte valueType=getType();//获取key值后面的vlaue类型值
			
			//根据valuetype类型，拿value值
			if(valueType==_OBJ)
			{//如果vlaue是对象类型，则将这个key新建一个map
				phex=Integer.toHexString(p).toUpperCase();
				log.debug("找到map对象:"+key+",所在位置p:"+p+"/"+phex);
				Map<Object, Object> value=new TreeMap<Object, Object>();
				readObject(valueType,value);
				map.put(key, value);
				phex=Integer.toHexString(p).toUpperCase();
				log.debug("map对象:"+key+"添加父容器完成!");
			}
			else
			{//如果是普通的值，则添加
				Object value=readObjectValue();//根据类型值读取值内容
				map.put(key, value);
				phex=Integer.toHexString(p).toUpperCase();
				log.debug("拿到第"+i+"个value:"+value+",p位置:"+p+"/"+phex);
			}
			//map结束，就该重新调用了，此时刚好p指向了下一个类型
		}
	}
	/**
	 * 取对象名
	 * 根据当前p的类型，返回p后面的值，之后P再值结束符号处
	 * @return
	 */
	private Object readObjectKey()
	{
		int pv=data[p];
		switch (pv) {
		case _NULL:
			return null;
		case _FALSE:
			return false;
		case _TRUE:
			return true;
		case _INT:
			return readInt();
		case _UTF8:
			return readStringUTF8();
		case _UTF16LE:
			return readStringUTF16LE();
		default:
			break;
		}
		return null;
	}
	/**
	 * 取对象值
	 * p必须为类型标记位置索引
	 * @param p表示 value的类型符号索引
	 * @throws IOException 
	 */
	private Object readObjectValue() throws IOException
	{
		int pv=data[p];
		switch (pv) {
		case _NULL:
			return null;
		case _FALSE:
			return false;
		case _TRUE:
			return true;
		case _INT:
			return readInt();
		case _UTF8:
			return readStringUTF8();
		case _UTF16LE:
			return readStringUTF16LE();
		case _IMAGE:
			return readByteArray();
		default:
			break;
		}
		return null;
	}
	/**
	 * 读取字节数组类型
	 * 09后面4个字节是数组长度,大小后面是数据
	 * @return
	 */
	private ByteArray readByteArray() {
		int byteArraySize=readInt();//读取图片大小
		if(byteArraySize<=0)
		{
			return null;
		}
		byte[] byteArrayData=new byte[byteArraySize];
		for(int i=0;i<byteArraySize;i++)
		{
			p++;//移动指针到后面的字节
			byteArrayData[i]=data[p];//读取移动后的字节内容
		}
		return new ByteArray(byteArraySize, byteArrayData);
	}
	/**
	 * @param p类型的字节位置
	 * @return 返回p处的类型值
	 */
	private byte getType()
	{
		if(p>=data.length)
		{
			log.error("已到达数据末尾,返回-1");
			return -1;
		}
		return data[p];
	}
	/**
	 * 判断当前p后面是否还可以继续再取一个字节
	 * @return
	 */
	private boolean canGetNext()
	{
		if(p>=data.length)
		{
			log.error("已到达数据末尾,返回-1");
			return false;
		}
		return true;
	}

	/**
	 * 从p后面读取4个字节返回int值,P指针指向最后值的一个字节
	 * 04 00 00 00 00只允许p的位置在04处或08处
	 * @param p 4个字节的前一个字节位置
	 * @return
	 */
	private int readInt()
	{
		if(!canRead(4))
		{
			log.error("没有足够的4个字节供读取,p:"+p+",data.length:"+data.length);
			return -1;
		}
		byte[] v=new byte[4];
		for(int i=0;i<4;i++)
		{
			p++;//移动指针到后面的字节
			v[i]=data[p];//读取移动后的字节内容
		}
		return dt.byteArrayToInt(v);
	}
	/**
	 * 从p位置后，读取一个UTF8文本,p指针停留在结束符合位置
	 * @param p 类型标识位置,必须为06
	 * @return
	 */
	private String readStringUTF8()
	{
		if(data[p]!=_UTF8)
		{
			log.error("p!=06,无法从后面读取StringUTF8");
			String phex=Integer.toHexString(p).toUpperCase();
			log.debug("当前p:"+p+"/"+phex);
			return null;
		}
		List<Byte> str=new ArrayList<Byte>();
		while(true)
		{
			if(canRead(1))
			{
				p++;//移动指针到下一位
				if(data[p]==0x00)
				{//如果遇到结束符号就退出
					break;
				}
				str.add(data[p]);
			}
		}
		if(str.size()<=0)
		{
			return "";
		}
		byte[] strBytes=new byte[str.size()];
		for(int i=0;i<str.size();i++)
		{
			strBytes[i]=str.get(i);
		}
		String strutf8=null;
		try {
			strutf8=new String(strBytes,"UTF8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return strutf8;
	}
	/**
	 * 从07开始读取UTF-16LE文本
	 * 读取后p所在位置为
	 * @param p 07类型符号所在位置
	 * @return
	 */
	private String readStringUTF16LE()
	{
		if(data[p]!=_UTF16LE)
		{
			log.error("p!=07,无法从后面读取StringUTF16LE");
			return null;
		}
		List<Byte> str=new ArrayList<Byte>();
		while(true)
		{
			if(canRead(2))
			{//UTF16LE一个字符占2位
				p++;//移动指针到下一位
				byte b1=data[p];
				p++;
				byte b2=data[p];
				if(b1==0x00&&b2==0x00)
				{//如果遇到结束符号就退出
					break;
				}
				str.add(b1);
				str.add(b2);
			}
		}
		byte[] strBytes=new byte[str.size()];
		for(int i=0;i<str.size();i++)
		{
			strBytes[i]=str.get(i);
		}
		String strutf8=null;
		try {
			strutf8=new String(strBytes,"UTF-16LE");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return strutf8;
	}
	/**
	 * 判断P后面是否还有length个字节可以读取
	 * @param p
	 * @return
	 */
	private boolean canRead(int length)
	{
		//log.debug("当前p:"+p+"判断p后面字节数:"+length);
		if(p+length<=data.length-1)
		{
			return true;
		}
		else
		{
			log.error("没有足够的字节供读取,p:"+p+"待读取长度:"+length+",总长度:"+data.length);
			return false;
		}
	}
	

}
