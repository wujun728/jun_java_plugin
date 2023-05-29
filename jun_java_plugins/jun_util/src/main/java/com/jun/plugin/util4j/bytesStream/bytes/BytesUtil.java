package com.jun.plugin.util4j.bytesStream.bytes;

public final class BytesUtil {

    public static byte readByte(byte[] bytes, int index) {
        return bytes[index];
    }

    public static short readShort(byte[] bytes, int index) {
        return (short) (bytes[index] << 8 | bytes[index + 1] & 0xFF);
    }

    public static short readShortLE(byte[] bytes, int index) {
        return (short) (bytes[index] & 0xff | bytes[index + 1] << 8);
    }

    public static int readUnsignedMedium(byte[] bytes, int index) {
        return  (bytes[index]     & 0xff) << 16 |
                (bytes[index + 1] & 0xff) <<  8 |
                bytes[index + 2] & 0xff;
    }

    public static int readUnsignedMediumLE(byte[] bytes, int index) {
        return  bytes[index]     & 0xff         |
                (bytes[index + 1] & 0xff) <<  8 |
                (bytes[index + 2] & 0xff) << 16;
    }

    public static int readInt(byte[] bytes, int index) {
        return  (bytes[index]     & 0xff) << 24 |
                (bytes[index + 1] & 0xff) << 16 |
                (bytes[index + 2] & 0xff) <<  8 |
                bytes[index + 3] & 0xff;
    }

    public static int readIntLE(byte[] bytes, int index) {
        return  bytes[index]      & 0xff        |
                (bytes[index + 1] & 0xff) << 8  |
                (bytes[index + 2] & 0xff) << 16 |
                (bytes[index + 3] & 0xff) << 24;
    }

    public static long readLong(byte[] bytes, int index) {
        return  ((long) bytes[index]     & 0xff) << 56 |
                ((long) bytes[index + 1] & 0xff) << 48 |
                ((long) bytes[index + 2] & 0xff) << 40 |
                ((long) bytes[index + 3] & 0xff) << 32 |
                ((long) bytes[index + 4] & 0xff) << 24 |
                ((long) bytes[index + 5] & 0xff) << 16 |
                ((long) bytes[index + 6] & 0xff) <<  8 |
                (long) bytes[index + 7] & 0xff;
    }

    public static long readLongLE(byte[] bytes, int index) {
        return  (long) bytes[index]      & 0xff        |
                ((long) bytes[index + 1] & 0xff) <<  8 |
                ((long) bytes[index + 2] & 0xff) << 16 |
                ((long) bytes[index + 3] & 0xff) << 24 |
                ((long) bytes[index + 4] & 0xff) << 32 |
                ((long) bytes[index + 5] & 0xff) << 40 |
                ((long) bytes[index + 6] & 0xff) << 48 |
                ((long) bytes[index + 7] & 0xff) << 56;
    }
    
    public static float readFloat(byte[] bytes,int index)
	{
		return Float.intBitsToFloat(readInt(bytes,index));
	}
	
	public static double readDouble(byte[] bytes,int index)
	{
		return Double.longBitsToDouble(readLong(bytes,index));
	}

    public static void writeByte(byte[] bytes, int index, int value) {
        bytes[index] = (byte) value;
    }

    public static void writeShort(byte[] bytes, int index, int value) {
        bytes[index]     = (byte) (value >>> 8);
        bytes[index + 1] = (byte) value;
    }

    public static void writeShortLE(byte[] bytes, int index, int value) {
        bytes[index]     = (byte) value;
        bytes[index + 1] = (byte) (value >>> 8);
    }

    public static void writeMedium(byte[] bytes, int index, int value) {
        bytes[index]     = (byte) (value >>> 16);
        bytes[index + 1] = (byte) (value >>> 8);
        bytes[index + 2] = (byte) value;
    }

    public static void writeMediumLE(byte[] bytes, int index, int value) {
        bytes[index]     = (byte) value;
        bytes[index + 1] = (byte) (value >>> 8);
        bytes[index + 2] = (byte) (value >>> 16);
    }

    public static void writeInt(byte[] bytes, int index, int value) {
        bytes[index]     = (byte) (value >>> 24);
        bytes[index + 1] = (byte) (value >>> 16);
        bytes[index + 2] = (byte) (value >>> 8);
        bytes[index + 3] = (byte) value;
    }

    public static void writeIntLE(byte[] bytes, int index, int value) {
        bytes[index]     = (byte) value;
        bytes[index + 1] = (byte) (value >>> 8);
        bytes[index + 2] = (byte) (value >>> 16);
        bytes[index + 3] = (byte) (value >>> 24);
    }

    public static void writeLong(byte[] bytes, int index, long value) {
        bytes[index]     = (byte) (value >>> 56);
        bytes[index + 1] = (byte) (value >>> 48);
        bytes[index + 2] = (byte) (value >>> 40);
        bytes[index + 3] = (byte) (value >>> 32);
        bytes[index + 4] = (byte) (value >>> 24);
        bytes[index + 5] = (byte) (value >>> 16);
        bytes[index + 6] = (byte) (value >>> 8);
        bytes[index + 7] = (byte) value;
    }

    public static void writeLongLE(byte[] bytes, int index, long value) {
        bytes[index]     = (byte) value;
        bytes[index + 1] = (byte) (value >>> 8);
        bytes[index + 2] = (byte) (value >>> 16);
        bytes[index + 3] = (byte) (value >>> 24);
        bytes[index + 4] = (byte) (value >>> 32);
        bytes[index + 5] = (byte) (value >>> 40);
        bytes[index + 6] = (byte) (value >>> 48);
        bytes[index + 7] = (byte) (value >>> 56);
    }
    
	public static void writeFloat(byte[] bytes, int index, float v) {
        writeInt(bytes,index,Float.floatToIntBits(v));
    }

	public static void writeDouble(byte[] bytes, int index, double v) {
        writeLong(bytes,index,Double.doubleToLongBits(v));
    }
}
