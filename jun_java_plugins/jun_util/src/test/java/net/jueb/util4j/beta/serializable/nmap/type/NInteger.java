package net.jueb.util4j.beta.serializable.nmap.type;

import java.util.Arrays;
import net.jueb.util4j.beta.serializable.nmap.falg.Flag;
import net.jueb.util4j.beta.tools.convert.typebytes.TypeBytesInputStream;

public class NInteger extends NType<Integer>{
	public NInteger(Integer i) {
		super(i, Flag.Head.NInteger, Flag.End.NInteger);
	}

	@Override
	public byte[] getObjectBytes() {
		return tb.getBytes(obj);
	}

	@Override
	public String getString() {
		return Integer.toString(obj);
	}

	@Override
	protected NType<Integer> decoder(TypeBytesInputStream ti) throws Exception {
		if(ti.available()>= 1+4+getFlagEnd().length)
		{
			//读取头
			byte head=ti.readByte();
			if(head!=getFlagHead())
			{//如果头部不正确
				return null;
			}
			//读取值
			int obj=ti.readInt();
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
			return new NInteger(obj);
		}
		return null;
	}
	
	@Override
	public Object getConvertValue() {
		return obj.intValue();
	}

}
