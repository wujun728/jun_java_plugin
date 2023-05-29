package net.jueb.util4j.beta.serializable.nmap.type;

import net.jueb.util4j.beta.serializable.nmap.falg.Flag;
import net.jueb.util4j.beta.tools.convert.typebytes.TypeBytesInputStream;

public final class NNull extends NType<Object>{

	public NNull() 
	{
		super(0xFF,Flag.Head.NNull,Flag.End.NNull);
	}

	@Override
	public byte[] getBytes() {
		return addByteArray(getFlagHead(),getFlagEnd());
	}
	
	@Override
	public byte[] getObjectBytes() {
		return new byte[]{getFlagHead()};
	}

	@Override
	public String getString() {
		return "NULL";
	}

	@Override
	protected NType<Object> decoder(TypeBytesInputStream ti) throws Exception {
		if(checkHead(ti))
		{//NULL的head就等于值
			return new NNull();
		}
		return null;
	}
	@Override
	public Object getConvertValue() {
		return null;
	}

}
