package com.jun.plugin.util4j.buffer;

import java.io.IOException;
import java.io.OutputStream;

public class ArrayBytesBuff extends AbstractArrayBytesBuff {

	private int readerIndex;
	private int writerIndex;
	private int markedReaderIndex;
	private int markedWriterIndex;

	public ArrayBytesBuff() {
		this(0, 0, DEFAULT_CAPACITY);
	}

	public ArrayBytesBuff(int capacity) {
		super(capacity);
		setIndex(readerIndex, writerIndex);
	}
	
	public ArrayBytesBuff(byte[] data) {
		super(data);
		setIndex(readerIndex, data.length);
	}
	
	private ArrayBytesBuff(int readerIndex, int writerIndex, int capacity) {
		super(capacity);
		setIndex(readerIndex, writerIndex);
	}

	private ArrayBytesBuff(int readerIndex, int writerIndex, byte[] initialArray) {
		super(initialArray);
		setIndex(readerIndex, writerIndex);
	}

	private void setIndexUnsafe(int readerIndex, int writerIndex) {
		this.readerIndex = readerIndex;
		this.writerIndex = writerIndex;
	}

	/**
	 * 检查是否可读字节数
	 * @param minimumReadableBytes
	 */
	private void checkReadableBytesUnsafe(int minimumReadableBytes) {
		if (readerIndex > writerIndex - minimumReadableBytes) {
			throw new IndexOutOfBoundsException(
					String.format("readerIndex(%d) + length(%d) exceeds writerIndex(%d): %s", readerIndex,
							minimumReadableBytes, writerIndex, this));
		}
	}

	private void checkReadableBytes(int minimumReadableBytes) {
		if (minimumReadableBytes < 0) {
			throw new IllegalArgumentException("minimumReadableBytes: " + minimumReadableBytes + " (expected: >= 0)");
		}
		checkReadableBytesUnsafe(minimumReadableBytes);
	}

	@Override
	public BytesBuff markReaderIndex() {
		markedWriterIndex = readerIndex;
		return this;
	}

	@Override
	public BytesBuff resetReaderIndex() {
		readerIndex(markedReaderIndex);
		return this;
	}

	@Override
	public BytesBuff markWriterIndex() {
		markedWriterIndex = writerIndex;
		return this;
	}

	@Override
	public BytesBuff resetWriterIndex() {
		writerIndex = markedWriterIndex;
		return this;
	}

	@Override
	public int readerIndex() {
		return readerIndex;
	}

	@Override
	public BytesBuff readerIndex(int readerIndex) {
		if (readerIndex < 0 || readerIndex > writerIndex) {
			throw new IndexOutOfBoundsException(String.format(
					"readerIndex: %d (expected: 0 <= readerIndex <= writerIndex(%d))", readerIndex, writerIndex));
		}
		this.readerIndex = readerIndex;
		return this;
	}

	@Override
	public int writerIndex() {
		return writerIndex;
	}

	@Override
	public BytesBuff writerIndex(int writerIndex) {
		if (writerIndex < readerIndex || writerIndex > capacity()) {
			throw new IndexOutOfBoundsException(
					String.format("writerIndex: %d (expected: readerIndex(%d) <= writerIndex <= capacity(%d))",
							writerIndex, readerIndex, capacity()));
		}
		this.writerIndex = writerIndex;
		return this;
	}

	@Override
	public BytesBuff setIndex(int readerIndex, int writerIndex) {
		if (readerIndex < 0 || readerIndex > writerIndex || writerIndex > capacity()) {
			throw new IndexOutOfBoundsException(String.format(
					"readerIndex: %d, writerIndex: %d (expected: 0 <= readerIndex <= writerIndex <= capacity(%d))",
					readerIndex, writerIndex, capacity()));
		}
		setIndexUnsafe(readerIndex, writerIndex);
		return this;
	}

	@Override
	public int readableBytes() {
		return writerIndex - readerIndex;
	}

	@Override
	public int writableBytes() {
		return capacity() - writerIndex;
	}

	@Override
	public BytesBuff copy() {
		return copy(readerIndex, readableBytes());
	}

	@Override
	public BytesBuff copy(int index, int length) {
		checkIndex(index, length);
        byte[] copiedArray = new byte[length];
        System.arraycopy(getArray(), index, copiedArray, 0, length);
        return new ArrayBytesBuff(0,0,copiedArray);
	}

	@Override
	public void clear() {
		readerIndex=writerIndex=0;
	}

	@Override
	public void reset() {
		readerIndex=0;
	}

	@Override
	public boolean readBoolean() {
		return readByte() != 0;
	}

	@Override
	public byte readByte() {
		checkReadableBytesUnsafe(LEN_1);
		return getByte(readerIndex++);
	}

	@Override
	public short readUnsignedByte() {
		return (short) (readByte() & 0xFF);
	}

	@Override
	public short readShort() {
		checkReadableBytesUnsafe(LEN_2);
		short b = getShort(readerIndex);
		readerIndex += LEN_2;
		return b;
	}

	@Override
	public short readShortLE() {
		checkReadableBytesUnsafe(LEN_2);
		short b = getShortLE(readerIndex);
		readerIndex += LEN_2;
		return b;
	}

	@Override
	public int readUnsignedShort() {
		return readShort() & 0xFFFF;
	}

	@Override
	public int readUnsignedShortLE() {
		return readShortLE() & 0xFFFF;
	}

	@Override
	public int readMedium() {
		int value = readUnsignedMedium();
		if ((value & 0x800000) != 0) {
			value |= 0xff000000;
		}
		return value;
	}

	@Override
	public int readMediumLE() {
		int value = readUnsignedMediumLE();
		if ((value & 0x800000) != 0) {
			value |= 0xff000000;
		}
		return value;
	}

	@Override
	public int readUnsignedMedium() {
		checkReadableBytesUnsafe(LEN_3);
		int b = getUnsignedMedium(readerIndex);
		readerIndex += LEN_3;
		return b;
	}

	@Override
	public int readUnsignedMediumLE() {
		checkReadableBytesUnsafe(LEN_3);
		int b = getUnsignedMediumLE(readerIndex);
		readerIndex += LEN_3;
		return b;
	}

	@Override
	public int readInt() {
		checkReadableBytesUnsafe(LEN_4);
		int b = getInt(readerIndex);
		readerIndex += LEN_4;
		return b;
	}

	@Override
	public int readIntLE() {
		checkReadableBytesUnsafe(LEN_4);
		int b = getIntLE(readerIndex);
		readerIndex += LEN_4;
		return b;
	}

	@Override
	public long readUnsignedInt() {
		return readInt() & 0xFFFFFFFFL;
	}

	@Override
	public long readUnsignedIntLE() {
		return readIntLE() & 0xFFFFFFFFL;
	}

	@Override
	public long readLong() {
		checkReadableBytesUnsafe(LEN_8);
		long b = getLong(readerIndex);
		readerIndex += LEN_8;
		return b;
	}

	@Override
	public long readLongLE() {
		checkReadableBytesUnsafe(LEN_8);
		long b = getLongLE(readerIndex);
		readerIndex += LEN_8;
		return b;
	}

	@Override
	public char readChar() {
		return (char) readShort();
	}

	@Override
	public float readFloat() {
		return Float.intBitsToFloat(readInt());
	}

	@Override
	public double readDouble() {
		return Double.longBitsToDouble(readLong());
	}

	@Override
	public BytesBuff readBytes(int length) {
		checkReadableBytes(length);
		if (length == 0) {
			return new ArrayBytesBuff();
		}
		BytesBuff buf = new ArrayBytesBuff();
		buf.writeBytes(this, readerIndex, length);
		readerIndex += length;
		return buf;
	}

	@Override
	public BytesBuff readBytes(BytesBuff dst) {
		readBytes(dst, dst.writableBytes());
		return this;
	}

	@Override
	public BytesBuff readBytes(BytesBuff dst, int length) {
		if (length > dst.writableBytes()) {
			throw new IndexOutOfBoundsException(String.format(
					"length(%d) exceeds dst.writableBytes(%d) where dst is: %s", length, dst.writableBytes(), dst));
		}
		readBytes(dst, dst.writerIndex(), length);
		dst.writerIndex(dst.writerIndex() + length);
		return this;
	}

	@Override
	public BytesBuff readBytes(BytesBuff dst, int dstIndex, int length) {
		checkReadableBytes(length);
		getBytes(readerIndex, dst, dstIndex, length);
		readerIndex += length;
		return this;
	}

	@Override
	public BytesBuff readBytes(byte[] dst) {
		readBytes(dst, 0, dst.length);
		return this;
	}

	@Override
	public BytesBuff readBytes(byte[] dst, int dstIndex, int length) {
		checkReadableBytes(length);
        getBytes(readerIndex, dst, dstIndex, length);
        readerIndex += length;
		return this;
	}

	@Override
	public BytesBuff readBytes(OutputStream out, int length) throws IOException {
		checkReadableBytes(length);
		getBytes(readerIndex, out, length);
		readerIndex += length;
		return this;
	}

	@Override
	public BytesBuff skipBytes(int length) {
		checkReadableBytes(length);
		readerIndex += length;
		return this;
	}

	@Override
	public BytesBuff writeBoolean(boolean value) {
		writeByte(value ? 1 : 0);
		return this;
	}

	@Override
	public BytesBuff writeByte(int value) {
		ensureCapacityUnsafe(LEN_1);
		setByte(writerIndex, value);
		writerIndex += LEN_1;
		return this;
	}

	@Override
	public BytesBuff writeShort(int value) {
		ensureCapacityUnsafe(LEN_2);
		setShort(writerIndex, value);
		writerIndex += LEN_2;
		return this;
	}

	@Override
	public BytesBuff writeShortLE(int value) {
		ensureCapacityUnsafe(LEN_2);
		setShortLE(writerIndex, value);
		writerIndex += LEN_2;
		return this;
	}

	@Override
	public BytesBuff writeMedium(int value) {
		ensureCapacityUnsafe(LEN_3);
		setMedium(writerIndex, value);
		writerIndex += LEN_3;
		return this;
	}

	@Override
	public BytesBuff writeMediumLE(int value) {
		ensureCapacityUnsafe(LEN_3);
		setMediumLE(writerIndex, value);
		writerIndex += LEN_3;
		return this;
	}

	@Override
	public BytesBuff writeInt(int value) {
		ensureCapacityUnsafe(LEN_4);
		setInt(writerIndex, value);
		writerIndex += LEN_4;
		return this;
	}

	@Override
	public BytesBuff writeIntLE(int value) {
		ensureCapacityUnsafe(LEN_4);
		setIntLE(writerIndex, value);
		writerIndex += LEN_4;
		return this;
	}

	@Override
	public BytesBuff writeLong(long value) {
		ensureCapacityUnsafe(LEN_8);
		setLong(writerIndex, value);
		writerIndex += LEN_8;
		return this;
	}

	@Override
	public BytesBuff writeLongLE(long value) {
		ensureCapacityUnsafe(LEN_8);
		setLongLE(writerIndex, value);
		writerIndex += LEN_8;
		return this;
	}

	@Override
	public BytesBuff writeChar(int value) {
		writeShort(value);
		return this;
	}

	@Override
	public BytesBuff writeFloat(float value) {
		writeInt(Float.floatToRawIntBits(value));
		return this;
	}

	@Override
	public BytesBuff writeDouble(double value) {
		writeLong(Double.doubleToRawLongBits(value));
		return this;
	}

	@Override
	public BytesBuff writeBytes(BytesBuff src) {
		 writeBytes(src, src.readableBytes());
	     return this;
	}

	@Override
	public BytesBuff writeBytes(BytesBuff src, int length) {
		if (length > src.readableBytes()) {
            throw new IndexOutOfBoundsException(String.format(
                    "length(%d) exceeds src.readableBytes(%d) where src is: %s", length, src.readableBytes(), src));
        }
        writeBytes(src, src.readerIndex(), length);
        src.readerIndex(src.readerIndex() + length);
        return this;
	}

	@Override
	public BytesBuff writeBytes(BytesBuff src, int srcIndex, int length) {
		 ensureCapacity(length);
	     setBytes(writerIndex, src, srcIndex, length);
	     writerIndex += length;
	     return this;
	}

	@Override
	public BytesBuff writeBytes(byte[] src) {
		writeBytes(src, 0, src.length);
        return this;
	}

	@Override
	public BytesBuff writeBytes(byte[] src, int srcIndex, int length) {
		ensureCapacity(length);
        setBytes(writerIndex, src, srcIndex, length);
        writerIndex += length;
        return this;
	}
	
	@Override
	protected Object clone() throws CloneNotSupportedException {
		return new ArrayBytesBuff(readerIndex,writerIndex,getArray().clone());
	}
}
