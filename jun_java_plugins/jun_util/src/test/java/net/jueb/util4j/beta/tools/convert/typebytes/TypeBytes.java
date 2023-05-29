package net.jueb.util4j.beta.tools.convert.typebytes;

import java.io.EOFException;
import java.util.Arrays;

import net.jueb.util4j.beta.tools.convert.HexStrBytes;


/**
 * 基础数据类型与字节数组互转
 * @author juebanlin
 * 到字节的转换，全部用无符号右移
 * 转换字节到类型时，默认字节值会变成int类型，要使用& 0xFF取字节值
 */
public final class TypeBytes {

	
	private boolean isLittleEndian=false;
	
	/**
	 * 默认大端模式
	 */
	public TypeBytes() {
		
	}
	public TypeBytes(boolean isLittleEndian) {
		this.isLittleEndian=isLittleEndian;
	}
	
	/**
	 * OK
	 * 将字节数组转换为16进制字符串文本
	 * byte[] a={1,22,31,14};==>"[01, 16, 1F, 0E]"
	 * @param data
	 * @return
	 */
	public String[] byteArrayToHexArray(byte[] data)
	{
		if(data==null||data.length<=0)
		{
			System.out.println("参数错误!");
			return null;
		}
		String[] hex=new String[data.length];
		int value=0;
		for(int i=0;i<data.length;i++)
		{
			value=data[i]&0xff;//防止出现负数，前面补0;
			if(value<=0xf)
			{//如果是F以内的数，则前面补零
				hex[i]="0"+Integer.toHexString(value).toUpperCase();
			}
			else
			{
				hex[i]=Integer.toHexString(value).toUpperCase();
			}
		}
 		return hex;
	}
	
	public byte[] getBytes(byte i)
	{
		return new byte[]{i};
	}
	
	public byte[] getBytes(boolean i)
	{
		return i?new byte[]{1}:new byte[]{0};
	}
	
	public byte[] getBytes(short i)
	{
		byte[] data=new byte[2];
		if(isLittleEndian)
		{//无符号左移
			data[0]=(byte)((i >>> 0) & 0xFF);
			data[1]=(byte)((i >>> 8) & 0xFF);
			return data;
		}else
		{
			data[0]=(byte)((i >>> 8) & 0xFF);
			data[1]=(byte)((i >>> 0) & 0xFF);//右移24位，高8位
			return data;
		}
	}
	public byte[] getBytes(char i)
	{
		byte[] data=new byte[2];
		if(isLittleEndian)
		{//无符号左移
			data[0]=(byte)((i >>> 0) & 0xFF);
			data[1]=(byte)((i >>> 8) & 0xFF);
			return data;
		}else
		{
			data[0]=(byte)((i >>> 8) & 0xFF);
			data[1]=(byte)((i >>> 0) & 0xFF);//右移24位，高8位
			return data;
		}
	}
	
	/**
	 * 大端模式，先存高位
	 * @param i
	 * @return
	 */
	public byte[] getBytes(int i)
	{
		byte[] data=new byte[4];
		if(isLittleEndian)
		{
			data[0]=(byte)((i >>> 0) & 0xFF);
			data[1]=(byte)((i >>> 8) & 0xFF);
			data[2]=(byte)((i >>> 16) & 0xFF);
			data[3]=(byte)((i >>> 24) & 0xFF);//右移24位，高8位
			return data;
		}else
		{
			data[0]=(byte)((i >>> 24) & 0xFF);
			data[1]=(byte)((i >>> 16) & 0xFF);
			data[2]=(byte)((i >>> 8) & 0xFF);
			data[3]=(byte)((i >>> 0) & 0xFF);//右移24位，高8位
			return data;
		}
	}

	public byte[] getBytes(long i)
	{
		byte writeBuffer[] = new byte[8];
		if(isLittleEndian)
        {//按低位保存
    		writeBuffer[0] = (byte)(i >>> 0);
            writeBuffer[1] = (byte)(i >>> 8);
            writeBuffer[2] = (byte)(i >>> 16);
            writeBuffer[3] = (byte)(i >>> 24);
            writeBuffer[4] = (byte)(i >>> 32);
            writeBuffer[5] = (byte)(i >>> 40);
            writeBuffer[6] = (byte)(i >>> 48);
            writeBuffer[7] = (byte)(i >>> 56);
        }else
        {//按高位保存
        	writeBuffer[0] = (byte)(i >>> 56);
            writeBuffer[1] = (byte)(i >>> 48);
            writeBuffer[2] = (byte)(i >>> 40);
            writeBuffer[3] = (byte)(i >>> 32);
            writeBuffer[4] = (byte)(i >>> 24);
            writeBuffer[5] = (byte)(i >>> 16);
            writeBuffer[6] = (byte)(i >>>  8);
            writeBuffer[7] = (byte)(i >>>  0);
        }
		return writeBuffer;
	}
	
	public byte[] getBytes(float i)
	{
		return getBytes(Float.floatToIntBits(i));
	}
	
	public byte[] getBytes(double i)
	{
		return getBytes(Double.doubleToLongBits(i));
	}
	
	public boolean readBoolean(byte i)
	{
		return !(i == 0);
	}
	
	public short readShort(byte[] i) throws EOFException
	{
		if(i==null ||i.length!=2)
		{
			 throw new EOFException();
		}
		int ch1 = i[0];
	    int ch2 = i[1];
	    if(isLittleEndian)
	       {//小端模式，先取到低位
	       		return (short)(((ch1 & 0xFF) << 0)+ ((ch2 & 0xFF) << 8));
	       }else
	       {
	       		return (short)(((ch1 & 0xFF) << 8) + ((ch2 & 0xFF) << 0));
	       }
	}
	
	public char readChar(byte[] i) throws EOFException
	{
		if(i==null ||i.length!=2)
		{
			 throw new EOFException();
		}
		 int ch1 = i[0];
	     int ch2 = i[1];
	     if(isLittleEndian)
	       {//小端模式，先取到低位
	       		return (char)(((ch1 & 0xFF) << 0)+ ((ch2 & 0xFF) << 8));
	       }else
	       {
	       		return (char)(((ch1 & 0xFF) << 8) + ((ch2 & 0xFF) << 0));
	       }
	}
	
	public int readInt(byte[] i) throws EOFException
	{
		if(i==null ||i.length!=4)
		{
			 throw new EOFException();
		}
		 int ch1 = i[0];
	     int ch2 = i[1];
	     int ch3 = i[2];
	     int ch4 = i[3];
	     if(isLittleEndian)
	     {//小端模式，先取到低位
	    	 return (((ch1 & 0xFF) << 0) + ((ch2 & 0xFF) << 8) + ((ch3 & 0xFF) << 16) + ((ch4 & 0xFF) << 24));
	     }else
	     {
	      	return (((ch1 & 0xFF) << 24) + ((ch2 & 0xFF) << 16) + ((ch3 & 0xFF) << 8) + ((ch4 & 0xFF) << 0));
	     }
	}
	
	public long readLong(byte[] i) throws EOFException
	{
		if(i==null || i.length!=8)
		{
			 throw new EOFException();
		}
		if(isLittleEndian)
        {//小端模式，先取到低位
        	return (((long)(i[7] & 0xFF) << 56) +
                    ((long)(i[6] & 0xFF) << 48) +
                    ((long)(i[5] & 0xFF) << 40) +
                    ((long)(i[4] & 0xFF) << 32) +
                    ((long)(i[3] & 0xFF) << 24) +
                    ((i[2] & 0xFF) << 16) +
                    ((i[1] & 0xFF) <<  8) +
                    ((i[0] & 0xFF) <<  0));
        }else
        {
        	return (((long)(i[0] & 0xFF) << 56) +
                    ((long)(i[1] & 0xFF) << 48) +
                    ((long)(i[2] & 0xFF) << 40) +
                    ((long)(i[3] & 0xFF) << 32) +
                    ((long)(i[4] & 0xFF) << 24) +
                    ((i[5] & 0xFF) << 16) +
                    ((i[6] & 0xFF) <<  8) +
                    ((i[7] & 0xFF) <<  0));
        }
	}
	
	public float readFloat(byte[] i) throws EOFException
	{
		return Float.intBitsToFloat(readInt(i));
	}
	
	public double readDouble(byte[] i) throws EOFException
	{
		return Double.longBitsToDouble(readLong(i));
	}
	
	
	public static void main(String[] args) throws EOFException {
		
		HexStrBytes hb=new HexStrBytes();
		TypeBytes tb=new TypeBytes(true);
		System.out.println("***************char****************");
		char c=222;
		System.out.println("测试值:"+(int)c);
		System.out.println("值到数据:"+Arrays.toString(tb.getBytes(c)));
		System.out.println("数据还原为值:"+(int)tb.readChar(tb.getBytes(c)));
		
		System.out.println("***************short****************");
		short s=5837;
		System.out.println("测试值:"+s);
		System.out.println("值到数据:"+Arrays.toString(tb.getBytes(s)));
		System.out.println("数据还原为值:"+tb.readShort(tb.getBytes(s)));
		
		System.out.println("***************int****************");
		int i=30821;
		System.out.println("测试值:"+i);
		System.out.println("值到数据:"+Arrays.toString(tb.getBytes(i)));
		System.out.println("hex："+Arrays.toString(hb.toHexArray(tb.getBytes(i))));
		System.out.println("数据还原为值:"+tb.readInt(tb.getBytes(i)));
		
		System.out.println("***************long****************");
		long l=Long.MAX_VALUE/2;
		System.out.println("测试值:"+l);
		System.out.println("值到数据:"+Arrays.toString(tb.getBytes(l)));
		System.out.println("数据还原为值:"+tb.readLong(tb.getBytes(l)));
		
		System.out.println("***************float****************");
		float f=Float.MAX_VALUE;
		System.out.println("测试值:"+f);
		System.out.println("值到数据:"+Arrays.toString(tb.getBytes(f)));
		System.out.println("数据还原为值:"+tb.readFloat(tb.getBytes(f)));
		
		System.out.println("***************double****************");
		double d=Double.MAX_VALUE;
		System.out.println("测试值:"+d);
		System.out.println("值到数据:"+Arrays.toString(tb.getBytes(d)));
		System.out.println("数据还原为值:"+tb.readDouble(tb.getBytes(d)));
	}
}
