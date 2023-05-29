package net.jueb.util4j.beta.serializable.bytesMap;

public class DataToMapBase {
	/**
	 * 野地有些属性为空或null
	 */
	public static final int _NULL=0xFF;
	public static final int _FALSE=0x00;
	public static final int _TRUE=0x01;
	/**
	 * 此类型的值直接去类型后4个字节
	 */
	public static final int _INT=0x04;
	/**
	 * 此类型的值，以00字节表示值结束
	 */
	public static final int _UTF8=0x06;
	/**
	 * 此类型的值，以00 00字节表示值结束
	 */
	public static final int _UTF16LE=0x07;
	/**
	 * map对象类型
	 */
	public static final byte _OBJ=0x08;
	/**
	 * 图片对象
	 */
	public static final byte _IMAGE=0x09;
}
