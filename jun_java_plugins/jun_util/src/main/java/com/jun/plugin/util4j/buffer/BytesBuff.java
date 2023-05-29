package com.jun.plugin.util4j.buffer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface BytesBuff {

	/**
	 * 标记读索引
	 * @return 返回当前buff
	 */
	public abstract BytesBuff markReaderIndex();

	/**
	 * 重置读索引
	 * @return 返回当前buff
	 */
	public abstract BytesBuff resetReaderIndex();

	/**
	 * 标记写索引
	 * 
	 * @return 返回当前buff
	 */
	public abstract BytesBuff markWriterIndex();

	/**
	 * 重置写索引
	 * 
	 * @return 返回当前buff
	 */
	public abstract BytesBuff resetWriterIndex();

	/**
	 * 读索引
	 * 
	 * @return 返回当前buff
	 */
	public abstract int readerIndex();

	/**
	 * 设置读索引
	 * 
	 * @param readerIndex
	 * @return 返回当前buff
	 */
	public abstract BytesBuff readerIndex(int readerIndex);

	/**
	 * 获取写索引
	 * 
	 * @return 返回当前buff
	 */
	public abstract int writerIndex();

	/**
	 * 设置写索引
	 * 
	 * @param writerIndex
	 * @return 返回当前buff
	 */
	public abstract BytesBuff writerIndex(int writerIndex);

	/**
	 * 设置读写索引
	 * 
	 * @param readerIndex
	 * @param writerIndex
	 * @return 返回当前buff
	 */
	public abstract BytesBuff setIndex(int readerIndex, int writerIndex);

	/**
	 * 可读字节数
	 * 
	 * @return 返回当前buff
	 */
	public abstract int readableBytes();

	/**
	 * 可写字节数
	 * 
	 * @return 返回当前buff
	 */
	public int writableBytes();

	/**
	 * 当前容量
	 * 
	 * @return 返回当前buff
	 */
	public int capacity();

	/**
	 * 清理缓冲区
	 */
	public void clear();

	/**
	 * 重置缓冲区,使得可以重读
	 */
	public void reset();

	/**
	 * 有效数据长度 写入长度
	 * 
	 * @return 返回buff长度
	 */
	public int length();

	/**
	 * 复制可读内容为一个缓冲区
	 * 
	 * @return 返回当前buff
	 */
	public abstract BytesBuff copy();

	/**
	 * 复制指定区块为一个缓冲区
	 * 
	 * @param index
	 * @param length
	 * @return 返回当前buff
	 */
	public abstract BytesBuff copy(int index, int length);

	/**
	 * 获取写入的字节数组(0~writeIndex)
	 * 
	 * @return 返回写入的所有数据
	 */
	public byte[] getBytes();
	
	/**
	 * 获取可读取的字节数组(readIndex~writeIndex)
	 * @return 返回可读数据
	 */
	public byte[] getReadableBytes();

	/**
	 * 获取全部字节数组
	 * 
	 * @return 返回所有数据包含未写入的
	 */
	public byte[] getRawBytes();

	public abstract boolean getBoolean(int index);

	public abstract byte getByte(int index);

	public abstract short getUnsignedByte(int index);

	public abstract short getShort(int index);

	public abstract short getShortLE(int index);

	public abstract int getUnsignedShort(int index);

	public abstract int getUnsignedShortLE(int index);

	public abstract int getMedium(int index);

	public abstract int getMediumLE(int index);

	public abstract int getUnsignedMedium(int index);

	public abstract int getUnsignedMediumLE(int index);

	public abstract int getInt(int index);

	public abstract int getIntLE(int index);

	public abstract long getUnsignedInt(int index);

	public abstract long getUnsignedIntLE(int index);

	public abstract long getLong(int index);

	public abstract long getLongLE(int index);

	public abstract char getChar(int index);

	public abstract float getFloat(int index);

	public abstract double getDouble(int index);

	public abstract BytesBuff getBytes(int index, BytesBuff dst);

	public abstract BytesBuff getBytes(int index, BytesBuff dst, int length);

	public abstract BytesBuff getBytes(int index, BytesBuff dst, int dstIndex, int length);

	public abstract BytesBuff getBytes(int index, byte[] dst);

	public abstract BytesBuff getBytes(int index, byte[] dst, int dstIndex, int length);

	public abstract BytesBuff getBytes(int index, OutputStream out, int length) throws IOException;
	
	public abstract BytesBuff setBoolean(int index, boolean value);

	public abstract BytesBuff setByte(int index, int value);

	public abstract BytesBuff setShort(int index, int value);

	public abstract BytesBuff setShortLE(int index, int value);

	public abstract BytesBuff setMedium(int index, int value);

	public abstract BytesBuff setMediumLE(int index, int value);

	public abstract BytesBuff setInt(int index, int value);

	public abstract BytesBuff setIntLE(int index, int value);

	public abstract BytesBuff setLong(int index, long value);

	public abstract BytesBuff setLongLE(int index, long value);

	public abstract BytesBuff setChar(int index, int value);

	public abstract BytesBuff setFloat(int index, float value);

	public abstract BytesBuff setDouble(int index, double value);

	public abstract BytesBuff setBytes(int index, BytesBuff src);

	public abstract BytesBuff setBytes(int index, BytesBuff src, int length);

	public abstract BytesBuff setBytes(int index, BytesBuff src, int srcIndex, int length);

	public abstract BytesBuff setBytes(int index, byte[] src);

	public abstract BytesBuff setBytes(int index, byte[] src, int srcIndex, int length);

	public abstract int setBytes(int index, InputStream in, int length) throws IOException;
	
	public abstract BytesBuff setZero(int index, int length);

	public abstract boolean readBoolean();

	public abstract byte readByte();

	public abstract short readUnsignedByte();

	public abstract short readShort();

	public abstract short readShortLE();

	public abstract int readUnsignedShort();

	public abstract int readUnsignedShortLE();

	public abstract int readMedium();

	public abstract int readMediumLE();

	public abstract int readUnsignedMedium();

	public abstract int readUnsignedMediumLE();

	public abstract int readInt();

	public abstract int readIntLE();

	public abstract long readUnsignedInt();

	public abstract long readUnsignedIntLE();

	public abstract long readLong();

	public abstract long readLongLE();

	public abstract char readChar();

	public abstract float readFloat();

	public abstract double readDouble();

	public abstract BytesBuff readBytes(int length);

	public abstract BytesBuff readBytes(BytesBuff dst);

	public abstract BytesBuff readBytes(BytesBuff dst, int length);

	public abstract BytesBuff readBytes(BytesBuff dst, int dstIndex, int length);

	public abstract BytesBuff readBytes(byte[] dst);

	public abstract BytesBuff readBytes(byte[] dst, int dstIndex, int length);

	public abstract BytesBuff readBytes(OutputStream out, int length) throws IOException;

	public abstract BytesBuff skipBytes(int length);

	public abstract BytesBuff writeBoolean(boolean value);

	public abstract BytesBuff writeByte(int value);

	public abstract BytesBuff writeShort(int value);

	public abstract BytesBuff writeShortLE(int value);

	public abstract BytesBuff writeMedium(int value);

	public abstract BytesBuff writeMediumLE(int value);

	public abstract BytesBuff writeInt(int value);

	public abstract BytesBuff writeIntLE(int value);

	public abstract BytesBuff writeLong(long value);

	public abstract BytesBuff writeLongLE(long value);

	public abstract BytesBuff writeChar(int value);

	public abstract BytesBuff writeFloat(float value);

	public abstract BytesBuff writeDouble(double value);

	public abstract BytesBuff writeBytes(BytesBuff src);

	public abstract BytesBuff writeBytes(BytesBuff src, int length);

	public abstract BytesBuff writeBytes(BytesBuff src, int srcIndex, int length);

	public abstract BytesBuff writeBytes(byte[] src);

	public abstract BytesBuff writeBytes(byte[] src, int srcIndex, int length);
}
