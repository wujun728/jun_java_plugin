package net.jueb.util4j.beta.serializable.nmap.falg;
/**
 * 标记头注册
 * 一个字节
 * @author juebanlin
 *
 */
public interface Flag
{
	public static interface Head
	{
		public static byte NFalse=0x00;
		public static byte NTrue=0x01;
		public static byte NByte=0x09;
		public static byte NChar=0x02;
		public static byte NShort=0x03;
		public static byte NInteger=0x04;
		public static byte NLong=0x05;
		public static byte NFloat=(byte) 0x0FE;
		public static byte NDouble=(byte) 0xFD;
		public static byte NString=0x0A;
		public static byte NUTF8String=0x06;
		public static byte NUTF16LEString=0x07;
		public static byte NMap=(byte) 0x08;
		public static byte NByteArray=0x09;
		public static byte NNull=(byte) 0xFF;
	}
	public static interface End
	{
		public static byte[] NFalse={};
		public static byte[] NTrue={};
		public static byte[] NByte={};
		public static byte[] NChar={};
		public static byte[] NShort={};
		public static byte[] NInteger={};
		public static byte[] NLong={};
		public static byte[] NFloat={};
		public static byte[] NDouble={};
		public static byte[] NString={0x00};
		public static byte[] NUTF8String={0x00};
		public static byte[] NUTF16LEString={0x00,0x00};
		public static byte[] NMap={};
		public static byte[] NByteArray={};
		public static byte[] NNull={};
	}
}