package net.jueb.util4j.beta.serializable.nmap.type;

import java.io.ByteArrayOutputStream;
import java.util.LinkedList;

import net.jueb.util4j.beta.serializable.nmap.falg.Flag;
import net.jueb.util4j.beta.tools.convert.typebytes.TypeBytesInputStream;

public class NString extends NType<String>{

	public NString(String obj) {
		super(obj, Flag.Head.NString, Flag.End.NString);
	}

	@Override
	public byte[] getObjectBytes() {
		return obj.getBytes();
	}

	@Override
	public String getString() {
		return obj;
	}


	@Override
	protected NType<String> decoder(TypeBytesInputStream ti) throws Exception {
		if(getFlagEnd()==null || getFlagEnd().length<=0)
		{
			throw new RuntimeException("字符串类型必须有结束符合");
		}
		if(checkHead(ti))
		{
			//读取UTF8字符串，这里end长度必定大于0
			int el=getFlagEnd().length;
			//先进先出缓冲队列
			LinkedList<Byte> cache=new LinkedList<Byte>();
			ByteArrayOutputStream body=new ByteArrayOutputStream();
			while(ti.available()>0)
			{
				if(cache.size()<el)
				{//只要缓存存在位置，则读取字节到队列尾
					cache.add(ti.readByte());
				}
				if(cache.size()==el)
				{//如果缓存满了，则判断是否是尾巴标记
					boolean isEnd=false;
					for(int i=0;i<el;i++)
					{
						isEnd=cache.get(i).byteValue()==getFlagEnd()[i];
						if(!isEnd)
						{//有一个不匹配则退出遍历比较
							break;
						}
					}
					if(isEnd)
					{//如果是end,则返回字符串
						return new NString(new String(body.toByteArray()));
					}
					//如果满了，必须移出最之前的字节，以供后面的读取
					body.write(cache.pollFirst());
				}if(cache.size()<el)
				{//只要缓存存在位置，则读取字节到队列尾
					cache.add(ti.readByte());
				}
				if(cache.size()==el)
				{//如果缓存满了，则判断是否是尾巴标记
					boolean isEnd=false;
					for(int j=0;j<el;j++)
					{
						isEnd=cache.get(j).byteValue()==getFlagEnd()[j];
						if(!isEnd)
						{//有一个不匹配则退出遍历比较
							break;
						}
					}
					if(isEnd)
					{//如果是end,则返回字符串
						return new NString(new String(body.toByteArray()));
					}
					//如果满了，必须移出最之前的字节，以供后面的读取
					body.write(cache.pollFirst());
				}
			}
		}
		return null;
	}

}
