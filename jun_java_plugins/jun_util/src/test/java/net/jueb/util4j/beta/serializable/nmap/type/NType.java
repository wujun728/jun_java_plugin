package net.jueb.util4j.beta.serializable.nmap.type;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import net.jueb.util4j.beta.tools.convert.typebytes.TypeBytes;
import net.jueb.util4j.beta.tools.convert.typebytes.TypeBytesInputStream;
import net.jueb.util4j.beta.tools.convert.typebytes.TypeBytesOutputStream;

/**
 * 一个任意类型
 * @author Administrator
 */
public abstract class NType<T> implements ValueConvert{
	protected Logger log = LoggerFactory.getLogger(getClass());
	public static boolean showLog=false;
	
	public void log(String log)
	{
		if(showLog)
		{
			if(this.log!=null)
			{
				this.log.debug(log);
			}else
			{
				System.out.println(log);
			}
		}
	}
	
	/**
	 * 小端模式转换器
	 */
	public final TypeBytes tb=new TypeBytes(true);
	
	
	/**
	 * 内存标记头
	 * 一个字节
	 */
	public final byte flagHead;
	/**
	 * 标记尾
	 * null表示无结尾或者直到遇到下一个head标记
	 */
	public final byte[] flagEnd;
	
	/**
	 * 序列号对象
	 */
	public final T obj;
	
	public NType(T obj,byte flagHead, byte[] flagEnd) {
		super();
		if(obj==null)
		{
			throw new RuntimeException("描述对象不能为空");
		}
		this.obj=obj;
		this.flagHead = flagHead;
		this.flagEnd = flagEnd;
	}


	/**
	 * 数组按顺序拼接
	 * 返回拼接后的数组
	 * @param bytes
	 * @return
	 */
	public final byte[] addByteArray(byte head,byte[] ... bytes)
	{	byte[] data=new byte[]{};
		try {
			ByteArrayOutputStream bos=new ByteArrayOutputStream();
			bos.write(head);
			for(int i=0;i<bytes.length;i++)
			{
				bos.write(bytes[i]);
			}
			bos.flush();
			data=bos.toByteArray();
			bos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return data;
	}
	/**
	 * 数组按顺序拼接
	 * 返回拼接后的数组
	 * @param bytes
	 * @return
	 */
	public final byte[] addByteArray(byte[] ... bytes)
	{	byte[] data=new byte[]{};
		try {
			ByteArrayOutputStream bos=new ByteArrayOutputStream();
			for(int i=0;i<bytes.length;i++)
			{
				bos.write(bytes[i]);
			}
			bos.flush();
			data=bos.toByteArray();
			bos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return data;
	}
	/**
	 * 获取对象加标记后的字节数组
	 * 默认:addByteArray(getFlagHead(),getObjectBytes(),getFlagEnd());
	 * @return
	 */
	public byte[] getBytes() 
	{
		return addByteArray(getFlagHead(),getObjectBytes(),getFlagEnd());
	}
	/**
	 * 获取标记头数组
	 * @return
	 */
	public final byte getFlagHead()
	{
		return flagHead;
	}
	
	/**
	 * 获取结束字节数组
	 * @return
	 */
	public final byte[] getFlagEnd()
	{
		return flagEnd;
	}

	public final T getObjectValue()
	{
		return obj;
	}
	
	/**
	 * 获取Ntype的转换类型-即存储在Map<Object,Object>中的类型
	 * 默认是getObjectValue()即obj本身
	 * @return
	 */
	@Override
	public Object getConvertValue()
	{
		return getObjectValue();
	}
	
	@Override
	public final String toString() {
		return getString();
	}
	
	/**
	 * 从输入流中解码一个该对象,解码失败则返回null并重置in的readIndex
	 * 成功解码后，in保留剩余字节数组
	 * @param 
	 * @return 返回null表示解码失败
	 */
	protected abstract NType<T> decoder(TypeBytesInputStream ti)throws Exception;
	
	/**
	 * 如果子类解码出现错误，发生异常或者返回null则重置读取位置
	 * 注意,该方法不影响当前map内数据
	 * @param data 
	 * @return null表示无法解码
	 * @throws Exception 
	 */
	public final NType<T> decoder(final byte[] data) throws Exception
	{
		final TypeBytesInputStream reader=getReader(data);
		return decoderByStream(reader);
	}
	/**
	 * 如果子类解码出现错误，发生异常则重置读取位置
	 * 要么返回异常，要么返回非null对象
	 * @param data 
	 * @return 
	 */
	public final NType<T> decoderByStream(final TypeBytesInputStream reader)throws Exception
	{
		int mark=reader.markReadIndex();
		NType<T>  type=null;
		try {
			type=decoder(reader);
		} catch (Exception e) {
			e.printStackTrace();
			reader.resetTo(mark);
			throw e;
		}
		return type;
	}
	
	protected final void abort()throws Exception
	{
		throw new RuntimeException("中断异常");
	}
	protected final void abort(String info)throws Exception
	{
		throw new RuntimeException(info);
	}
	
	/**
	 * 读取一个字节并和head匹配，返回true则表示成功读过head标记
	 * 注意：改方法不会Reset,返回失败或者异常记得要Reset
	 * @param in
	 * @return
	 */
	public final boolean checkHead(TypeBytesInputStream in)
	{
		return in.available()>=1?(byte)(in.read() & 0xFF)==getFlagHead():false;
	}
	
	/**
	 * 检查尾部标记,如果返回true,表示通过尾部标记检测
	 * 返回失败或者异常记得要Reset
	 * @param in
	 * @return 
	 * @throws IOException
	 */
	public final boolean checkEnd(TypeBytesInputStream in) throws IOException
	{
		if(getFlagEnd()!=null && getFlagEnd().length==0)
		{//如果没有结束符合，则直接通过
			return true;
		}
		if(in.available()>=getFlagEnd().length)
		{
			byte[] tmp=new byte[getFlagEnd().length];
			in.read(tmp);
			return Arrays.equals(getFlagEnd(),tmp);
		}
		return false;
	}
	
	
	/**
	 * 获取对象的字节数组表示形式
	 * @return
	 */
	public abstract byte[] getObjectBytes();
	
	/**
	 * 获取字符串表示形式
	 * @return
	 */
	public abstract String getString();
	
	public TypeBytesInputStream getReader(byte[] buf)
	{
		return new TypeBytesInputStream(buf,true);
	}
	
	public TypeBytesOutputStream getWriter()
	{
		return new TypeBytesOutputStream(true);
	}
	
	@Override
	public final int hashCode() {
		return obj.hashCode();
	}
	
	@Override
	public final boolean equals(Object obj) {
		if(this==obj)
		{
			return true;
		}
		if(obj instanceof NType<?>)
		{
			NType<?> n=(NType<?>)obj;
			return n.getObjectValue().equals(this.obj);
		}
		return super.equals(obj);
	}
	
}
