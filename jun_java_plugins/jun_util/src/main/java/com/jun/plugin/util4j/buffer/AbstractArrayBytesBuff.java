package com.jun.plugin.util4j.buffer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.jun.plugin.util4j.bytesStream.bytes.BytesUtil;
import com.jun.plugin.util4j.bytesStream.bytes.HexUtil;

public abstract class AbstractArrayBytesBuff implements BytesBuff {

	public final static int LEN_1 = 1;
	public final static int LEN_2 = 2;
	public final static int LEN_3 = 3;
	public final static int LEN_4 = 4;
	public final static int LEN_8 = 8;
	public final static int DEFAULT_CAPACITY = 8;
	private byte[] array;

	public AbstractArrayBytesBuff() {
		this(DEFAULT_CAPACITY);
	}

	public AbstractArrayBytesBuff(int capacity) {
		if (capacity < 0) {
			capacity = 0;
		}
		this.array = new byte[capacity];
	}

	public AbstractArrayBytesBuff(byte[] initialArray) {
		if (initialArray == null) {
			throw new NullPointerException("initialArray");
		}
		this.array = initialArray;
	}

	/**
	 * 检查当前数组某区块是否超出范围
	 * 
	 * @param index
	 * @param fieldLength
	 */
	void checkIndex(int index, int fieldLength) {
		if (isOutOfBounds(index, fieldLength, capacity())) {
			throw new IndexOutOfBoundsException(
					String.format("index: %d, length: %d (expected: range(0, %d))", index, fieldLength, capacity()));
		}
	}

	/**
	 * 检查索引和长度是否超出范围
	 * 
	 * @param index
	 * @param length
	 * @param capacity
	 * @return
	 */
	boolean isOutOfBounds(int index, int length, int capacity) {
		return (index | length | (index + length) | (capacity - (index + length))) < 0;
	}

	/**
	 * 检查目标索引是否超出长度
	 * 
	 * @param index
	 * @param length
	 * @param dstIndex
	 * @param dstCapacity
	 */
	void checkDstIndex(int index, int length, int dstIndex, int dstCapacity) {
		checkIndex(index, length);
		if (isOutOfBounds(dstIndex, length, dstCapacity)) {
			throw new IndexOutOfBoundsException(
					String.format("dstIndex: %d, length: %d (expected: range(0, %d))", dstIndex, length, dstCapacity));
		}
	}

	void checkSrcIndex(int index, int length, int srcIndex, int srcCapacity) {
		checkIndex(index, length);
		if (isOutOfBounds(srcIndex, length, srcCapacity)) {
			throw new IndexOutOfBoundsException(
					String.format("srcIndex: %d, length: %d (expected: range(0, %d))", srcIndex, length, srcCapacity));
		}
	}

	/**
	 * 保证缓冲区大小
	 * @param addBytes
	 */
	protected void ensureCapacityUnsafe(int addBytes) {
		int newCapacity = writerIndex() + addBytes;
		if (newCapacity > capacity()) {
			byte[] tmp = new byte[newCapacity * 3 / 2];
			System.arraycopy(array, 0, tmp, 0, writerIndex());
			array = tmp;
		}
		// int size = writableBytes();
		// if (minWritableBytes <= size) {
		// return;
		// }
		// int addSize = minWritableBytes - size;
		// array = Arrays.copyOf(array, array.length + addSize);
	}

	/**
	 * 保证缓冲区大小
	 * @param addBytes 增加的字节数组长度
	 *            
	 * @return 返回当前buff
	 */
	protected BytesBuff ensureCapacity(int addBytes) {
		if (addBytes < 0) {
			throw new IllegalArgumentException(String.format("addBytes: %d (expected: >= 0)", addBytes));
		}
		ensureCapacityUnsafe(addBytes);
		return this;
	}

	@Override
	public int capacity() {
		return array.length;
	}

	@Override
	public byte[] getBytes() {
		byte[] buf = new byte[length()];
		System.arraycopy(array, 0, buf, 0, buf.length);
		return buf;
	}
	
	@Override
	public byte[] getReadableBytes() {
		byte[] buf = new byte[readableBytes()];
		System.arraycopy(array,readerIndex(), buf, 0, buf.length);
		return buf;
	}
	
	@Override
	public byte[] getRawBytes() {
		byte[] bytes = new byte[capacity()];
		System.arraycopy(array, 0, bytes, 0, bytes.length);
		return bytes;
	}

	protected byte[] getArray() {
		return array;
	}

	@Override
	public int length() {
		return writerIndex();
	}

	@Override
	public boolean getBoolean(int index) {
		return getByte(index) != 0;
	}

	@Override
	public byte getByte(int index) {
		checkIndex(index, LEN_1);
		return BytesUtil.readByte(array, index);
	}

	@Override
	public short getUnsignedByte(int index) {
		return (short) (getByte(index) & 0xFF);
	}

	@Override
	public short getShort(int index) {
		checkIndex(index, LEN_2);
		return BytesUtil.readShort(array, index);
	}

	@Override
	public short getShortLE(int index) {
		checkIndex(index, LEN_2);
		return BytesUtil.readShortLE(array, index);
	}

	@Override
	public int getUnsignedShort(int index) {
		return getShort(index) & 0xFFFF;
	}

	@Override
	public int getUnsignedShortLE(int index) {
		return getShortLE(index) & 0xFFFF;
	}

	@Override
	public int getMedium(int index) {
		int value = getUnsignedMedium(index);
		if ((value & 0x800000) != 0) {
			value |= 0xff000000;
		}
		return value;
	}

	@Override
	public int getMediumLE(int index) {
		int value = getUnsignedMediumLE(index);
		if ((value & 0x800000) != 0) {
			value |= 0xff000000;
		}
		return value;
	}

	@Override
	public int getUnsignedMedium(int index) {
		checkIndex(index, LEN_3);
		return BytesUtil.readUnsignedMedium(array, index);
	}

	@Override
	public int getUnsignedMediumLE(int index) {
		checkIndex(index, LEN_3);
		return BytesUtil.readUnsignedMediumLE(array, index);
	}

	@Override
	public int getInt(int index) {
		checkIndex(index, LEN_4);
		return BytesUtil.readInt(array, index);
	}

	@Override
	public int getIntLE(int index) {
		checkIndex(index, LEN_4);
		return BytesUtil.readIntLE(array, index);
	}

	@Override
	public long getUnsignedInt(int index) {
		return getInt(index) & 0xFFFFFFFFL;
	}

	@Override
	public long getUnsignedIntLE(int index) {
		return getIntLE(index) & 0xFFFFFFFFL;
	}

	@Override
	public long getLong(int index) {
		checkIndex(index, LEN_8);
		return BytesUtil.readLong(array, index);
	}

	@Override
	public long getLongLE(int index) {
		checkIndex(index, LEN_8);
		return BytesUtil.readLongLE(array, index);
	}

	@Override
	public char getChar(int index) {
		return (char) getShort(index);
	}

	@Override
	public float getFloat(int index) {
		return Float.intBitsToFloat(getInt(index));
	}

	@Override
	public double getDouble(int index) {
		return Double.longBitsToDouble(getLong(index));
	}

	@Override
	public BytesBuff getBytes(int index, BytesBuff dst) {
		getBytes(index, dst, dst.writableBytes());
		return this;
	}

	@Override
	public BytesBuff getBytes(int index, BytesBuff dst, int length) {
		getBytes(index, dst, dst.writerIndex(), length);
		dst.writerIndex(dst.writerIndex() + length);
		return this;
	}

	@Override
	public BytesBuff getBytes(int index, BytesBuff dst, int dstIndex, int length) {
		checkDstIndex(index, length, dstIndex, dst.capacity());
		dst.setBytes(dstIndex, array, index, length);
		return this;
	}

	@Override
	public BytesBuff getBytes(int index, byte[] dst) {
		getBytes(index, dst, 0, dst.length);
		return this;
	}

	@Override
	public BytesBuff getBytes(int index, byte[] dst, int dstIndex, int length) {
		checkDstIndex(index, length, dstIndex, dst.length);
		System.arraycopy(array, index, dst, dstIndex, length);
		return this;
	}

	@Override
	public BytesBuff getBytes(int index, OutputStream out, int length) throws IOException {
		out.write(array, index, length);
		return this;
	}

	@Override
	public BytesBuff setBoolean(int index, boolean value) {
		setByte(index, value ? 1 : 0);
		return this;
	}

	@Override
	public BytesBuff setByte(int index, int value) {
		checkIndex(index, LEN_1);
		BytesUtil.writeByte(array, index, value);
		return this;
	}

	@Override
	public BytesBuff setShort(int index, int value) {
		checkIndex(index, LEN_2);
		BytesUtil.writeShort(array, index, value);
		return this;
	}

	@Override
	public BytesBuff setShortLE(int index, int value) {
		checkIndex(index, LEN_2);
		BytesUtil.writeShortLE(array, index, value);
		return this;
	}

	@Override
	public BytesBuff setMedium(int index, int value) {
		checkIndex(index, LEN_3);
		BytesUtil.writeMedium(array, index, value);
		return this;
	}

	@Override
	public BytesBuff setMediumLE(int index, int value) {
		checkIndex(index, LEN_3);
		BytesUtil.writeMediumLE(array, index, value);
		return this;
	}

	@Override
	public BytesBuff setInt(int index, int value) {
		checkIndex(index, LEN_4);
		BytesUtil.writeInt(array, index, value);
		return this;
	}

	@Override
	public BytesBuff setIntLE(int index, int value) {
		checkIndex(index, LEN_4);
		BytesUtil.writeIntLE(array, index, value);
		return this;
	}

	@Override
	public BytesBuff setLong(int index, long value) {
		checkIndex(index, LEN_8);
		BytesUtil.writeLong(array, index, value);
		return this;
	}

	@Override
	public BytesBuff setLongLE(int index, long value) {
		checkIndex(index, LEN_8);
		BytesUtil.writeLongLE(array, index, value);
		return this;
	}

	@Override
	public BytesBuff setChar(int index, int value) {
		setShort(index, value);
		return this;
	}

	@Override
	public BytesBuff setFloat(int index, float value) {
		setInt(index, Float.floatToRawIntBits(value));
		return this;
	}

	@Override
	public BytesBuff setDouble(int index, double value) {
		setLong(index, Double.doubleToRawLongBits(value));
		return this;
	}

	@Override
	public BytesBuff setBytes(int index, BytesBuff src) {
		setBytes(index, src, src.readableBytes());
		return this;
	}

	@Override
	public BytesBuff setBytes(int index, BytesBuff src, int length) {
		checkIndex(index, length);
		if (src == null) {
			throw new NullPointerException("src");
		}
		if (length > src.readableBytes()) {
			throw new IndexOutOfBoundsException(String.format(
					"length(%d) exceeds src.readableBytes(%d) where src is: %s", length, src.readableBytes(), src));
		}
		setBytes(index, src, src.readerIndex(), length);
		src.readerIndex(src.readerIndex() + length);
		return this;
	}

	@Override
	public BytesBuff setBytes(int index, BytesBuff src, int srcIndex, int length) {
		checkSrcIndex(index, length, srcIndex, src.capacity());
		src.getBytes(srcIndex, array, index, length);
		return this;
	}

	@Override
	public BytesBuff setBytes(int index, byte[] src) {
		setBytes(index, src, 0, src.length);
		return this;
	}

	@Override
	public BytesBuff setBytes(int index, byte[] src, int srcIndex, int length) {
		checkSrcIndex(index, length, srcIndex, src.length);
		System.arraycopy(src, srcIndex, array, index, length);
		return this;
	}

	@Override
	public int setBytes(int index, InputStream in, int length) throws IOException {
		return in.read(array, index, length);
	}

	@Override
	public BytesBuff setZero(int index, int length) {
		if (length == 0) {
			return this;
		}
		checkIndex(index, length);
		int nLong = length >>> 3;
		int nBytes = length & 7;
		for (int i = nLong; i > 0; i--) {
			setLong(index, 0);
			index += 8;
		}
		if (nBytes == 4) {
			setInt(index, 0);
			// Not need to update the index as we not will use it after this.
		} else if (nBytes < 4) {
			for (int i = nBytes; i > 0; i--) {
				setByte(index, (byte) 0);
				index++;
			}
		} else {
			setInt(index, 0);
			index += 4;
			for (int i = nBytes - 4; i > 0; i--) {
				setByte(index, (byte) 0);
				index++;
			}
		}
		return this;
	}
	
	@Override
	public String toString() {
		return HexUtil.prettyHexDump(array);
	}
}