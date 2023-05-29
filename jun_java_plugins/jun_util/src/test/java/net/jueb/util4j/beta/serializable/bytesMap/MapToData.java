package net.jueb.util4j.beta.serializable.bytesMap;


import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TreeMap;

public class MapToData {
	private byte[] head=new byte[]{0x08,0x00,0x00,0x00,0x00};//默认为空map
	private DataTools dt=new DataTools();

	
	@SuppressWarnings("unchecked")
	private byte[] getMapData(TreeMap<Object,Object> params)
	{
		byte[] data=new byte[0];
		try {
			List<Byte> bytes=new ArrayList<Byte>();
			//数据头,表示map类型+map子内容数量
			bytes.add((byte) 0x08);//加入map标记
			byte[] size=dt.intToByteArray(params.size());//获取map大小
			for(int i=0;i<size.length;i++)//加入map大小数据
			{//加入map大小信息
				bytes.add(size[i]);
			}
			//如果有属性列表，则添加
			for(Object obj:params.keySet())
			{
				//加入key属性类型+属性值+属性结尾标记
				byte[] key=getKeyBytes(obj);
				for(int i=0;i<key.length;i++)
				{
					bytes.add(key[i]);
				}
				
				//加入value属性类型+属性值+属性结尾标记
				Object val=params.get(obj);
				if(val instanceof TreeMap)
				{
					byte[] tmp=getMapData((TreeMap<Object,Object>)val);
					for(int i=0;i<tmp.length;i++)
					{
						bytes.add(tmp[i]);
					}
				}else
				{
					byte[] value=getValueBytes(val);
					for(int i=0;i<value.length;i++)
					{
						bytes.add(value[i]);
					}
				}
			}
			data=new byte[bytes.size()];
			for(int i=0;i<bytes.size();i++)
			{
				data[i]=bytes.get(i);
			}
		} catch (Exception e) {
			System.out.println("构造数据异常!");
		}
		return data;
	}
	/**
	 * 将map对象转换为字节数据
	 * date日期类型将抓换为07字符串类型
	 * @param params
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public byte[] getData(TreeMap<Object,Object> params)
	{
		if(params.size()<=0)
		{//如果没有参数，则返回空头
			return head;
		}
		byte[] data=new byte[0];
		
		try {
			List<Byte> bytes=new ArrayList<Byte>();
			//数据头,表示map类型+map子内容数量
			bytes.add((byte) 0x08);//加入map标记
			byte[] size=dt.intToByteArray(params.size());//获取map大小
			for(int i=0;i<size.length;i++)//加入map大小数据
			{//加入map大小信息
				bytes.add(size[i]);
			}
			//如果有属性列表，则添加
			for(Object obj:params.keySet())
			{
				//加入key属性类型+属性值+属性结尾标记
				byte[] key=getKeyBytes(obj);
				for(int i=0;i<key.length;i++)
				{
					bytes.add(key[i]);
				}
				
				//加入value属性类型+属性值+属性结尾标记
				Object val=params.get(obj);
				if(val instanceof TreeMap)
				{
					byte[] tmp=getMapData((TreeMap<Object,Object>)val);
					for(int i=0;i<tmp.length;i++)
					{
						bytes.add(tmp[i]);
					}
				}else
				{
					byte[] value=getValueBytes(params.get(obj));
					for(int i=0;i<value.length;i++)
					{
						bytes.add(value[i]);
					}
				}
			}
			data=new byte[bytes.size()];
			for(int i=0;i<bytes.size();i++)
			{
				data[i]=bytes.get(i);
			}
		} catch (Exception e) {
			System.out.println("构造数据异常!");
		}
		return data;
	}
	/**
	 * 是否包含汉字
	 * @param str
	 * @return
	 */
	private boolean isContainsGBK(String str)
	{
		if (null == str || "".equals(str.trim())) return false;
	
		  for (int i = 0; i < str.length(); i++) {
	
		    if (isChinese(str.charAt(i))) return true;
		    
		  }
		  return false;
	}
	/**
	 * 输入的字符是否是汉字
	 * @param a char
	 * @return boolean
	 */
	private  boolean isChinese(char a) { 
	     int v = a; 
	     return (v >=19968 && v <= 171941); 
	}
	private byte[] getKeyBytes(Object key) throws UnsupportedEncodingException
	{
		List<Byte> bytes=new ArrayList<Byte>();
		if(key instanceof String)
		{//如果是字符串类型
			String str=(String)key;
			//06类型
			bytes.add((byte) 0x06);
			byte[] tmp=str.getBytes("UTF8");
			for(int i=0;i<tmp.length;i++)
			{
				bytes.add(tmp[i]);
			}
			bytes.add((byte) 0x00);			
		}else if(key instanceof Integer)
		{//如果是整数
			bytes.add((byte)0x04);//类型标记
			byte[] tmp=dt.intToByteArray((Integer) key);
			for(int i=0;i<tmp.length;i++)
			{
				bytes.add(tmp[i]);
			}
		}
		else if(key instanceof Boolean)
		{//如果是boolean类型
			if((Boolean)key)
			{
				bytes.add((byte)0x01);
			}else
			{
				bytes.add((byte)0x00);
			}
		}else if(key==null)
		{//如果是null。则返回
			bytes.add((byte)0xFF);
		}
		else
		{
			
		}
		//生成返回数据
		byte[] data=new byte[bytes.size()];
		for(int i=0;i<bytes.size();i++)
		{
			data[i]=bytes.get(i);
		}
		return data;
	}
	/**
	 * 根据类型，获取对应的value数据
	 * 返回类型标记+值+结束标记数组
	 * @param obj
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	private byte[] getValueBytes(Object value) throws UnsupportedEncodingException
	{
		List<Byte> bytes=new ArrayList<Byte>();
		if(value instanceof Date)
		{//dateTime=2012-09-07 01:14:48, 
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String str=sdf.format(value);
			bytes.add((byte) 0x07);
			byte[] tmp=str.getBytes("UTF-16LE");
			for(int i=0;i<tmp.length;i++)
			{
				bytes.add(tmp[i]);
			}
			bytes.add((byte) 0x00);
			bytes.add((byte) 0x00);
		}else if(value instanceof UTF16LEString)
		{//07类型
			UTF16LEString utf=(UTF16LEString)value;
			String str=utf.getString();
			
			bytes.add((byte) 0x07);
			byte[] tmp=str.getBytes("UTF-16LE");
			for(int i=0;i<tmp.length;i++)
			{
				bytes.add(tmp[i]);
			}
			bytes.add((byte) 0x00);
			bytes.add((byte) 0x00);
		}
		else if(value instanceof String)
		{//如果是普通字符串类型
			String str=(String)value;
		  if(isContainsGBK(str))
			{//07类型
				bytes.add((byte) 0x07);
				byte[] tmp=str.getBytes("UTF-16LE");
				for(int i=0;i<tmp.length;i++)
				{
					bytes.add(tmp[i]);
				}
				bytes.add((byte) 0x00);
				bytes.add((byte) 0x00);
			}
			else
			{//06类型
				bytes.add((byte) 0x06);
				byte[] tmp=str.getBytes("UTF8");
				for(int i=0;i<tmp.length;i++)
				{
					bytes.add(tmp[i]);
				}
				bytes.add((byte) 0x00);	
			}		
		}else if(value instanceof Integer)
		{//如果是整数
			bytes.add((byte)0x04);//类型标记
			byte[] tmp=dt.intToByteArray((Integer) value);
			for(int i=0;i<tmp.length;i++)
			{
				bytes.add(tmp[i]);
			}
		}
		else if(value instanceof Boolean)
		{//如果是boolean类型
			if((Boolean)value)
			{
				bytes.add((byte)0x01);
			}else
			{
				bytes.add((byte)0x00);
			}
		}else if(value==null)
		{//如果是null。则返回
			bytes.add((byte)0xFF);
		}
		else
		{
			
		}
		//生成返回数据
		byte[] data=new byte[bytes.size()];
		for(int i=0;i<bytes.size();i++)
		{
			data[i]=bytes.get(i);
		}
		return data;
	}
}
