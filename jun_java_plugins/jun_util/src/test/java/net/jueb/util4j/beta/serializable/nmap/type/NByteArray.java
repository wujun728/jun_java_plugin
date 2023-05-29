package net.jueb.util4j.beta.serializable.nmap.type;

import java.util.Arrays;
import net.jueb.util4j.beta.serializable.nmap.falg.Flag;
import net.jueb.util4j.beta.tools.convert.typebytes.TypeBytesInputStream;

/**
 * 字节数组以头+长度+数据+尾巴
 * @author Administrator
 *
 */
public class NByteArray extends NType<byte[]>{

	public NByteArray(byte[] obj) {
		super(obj, Flag.Head.NByteArray, Flag.End.NByteArray);
	}
	@Override
	public byte[] getBytes() {
		return addByteArray(getFlagHead(),tb.getBytes(obj.length),getFlagEnd());
	}
	@Override
	public byte[] getObjectBytes() {
		return obj;
	}
	@Override
	public String getString() {
		return "ByteArray["+Arrays.toString(obj)+"]";
	}
	@Override
	protected NType<byte[]> decoder(TypeBytesInputStream ti) throws Exception {
		if(ti.available()>= 1+4+getFlagEnd().length)
		{
			//读取头
			byte head=ti.readByte();
			if(head!=getFlagHead())
			{//如果头部不正确
				return null;
			}
			//读取长度值
			int l=ti.readInt();
			if(l==0)
			{//长度为0的情况
				//对比尾巴是否正确
				if(getFlagEnd().length>0)
				{
					byte[] tmp=new byte[getFlagEnd().length];
					ti.read(tmp);
					if(!Arrays.equals(tmp, getFlagEnd()))
					{
						return null;
					}
				}
				return new NByteArray(new byte[] {});
			}else
			{//长度不为0的情况
				if(ti.available()>=(l+getFlagEnd().length))
				{//如果剩余字节数够
					byte[] data=new byte[l];
					ti.read(data);
					//对比尾巴是否正确
					if(getFlagEnd().length>0)
					{
						byte[] tmp=new byte[getFlagEnd().length];
						ti.read(tmp);
						if(!Arrays.equals(tmp, getFlagEnd()))
						{
							return null;
						}
					}
					return new NByteArray(data);
				}
			}
		}
		return null;
	}
}
