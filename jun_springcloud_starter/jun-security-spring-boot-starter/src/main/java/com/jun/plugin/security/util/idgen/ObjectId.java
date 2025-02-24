package com.jun.plugin.security.util.idgen;


import java.io.Serializable;
import java.nio.ByteBuffer;
import java.security.SecureRandom;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * MongoDb ObjectId java版实现
 *
 * @version 2022-05-21 9:53
 **/
public final class ObjectId implements Comparable<ObjectId>, Serializable {
    private static final long serialVersionUID = 3670079982654483072L;

    private static final int RANDOM_VALUE1;
    private static final short RANDOM_VALUE2;
    private static final AtomicInteger NEXT_COUNTER = new AtomicInteger((new SecureRandom()).nextInt());
    private static final char[] HEX_CHARS = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
    private final int timestamp;
    private final int counter;
    private final int randomValue1;
    private final short randomValue2;

    public static boolean isValid(String hexString) {
        if (hexString == null) {
            throw new IllegalArgumentException();
        } else {
            int len = hexString.length();
            if (len != 24) {
                return false;
            } else {
                for (int i = 0; i < len; ++i) {
                    char c = hexString.charAt(i);
                    if ((c < '0' || c > '9') && (c < 'a' || c > 'f') && (c < 'A' || c > 'F')) {
                        return false;
                    }
                }

                return true;
            }
        }
    }

    public ObjectId() {
        this(new Date());
    }

    public ObjectId(Date date) {
        this(dateToTimestampSeconds(date), NEXT_COUNTER.getAndIncrement() & 16777215, false);
    }

    public ObjectId(Date date, int counter) {
        this(dateToTimestampSeconds(date), counter, true);
    }

    public ObjectId(int timestamp, int counter) {
        this(timestamp, counter, true);
    }

    private ObjectId(int timestamp, int counter, boolean checkCounter) {
        this(timestamp, RANDOM_VALUE1, RANDOM_VALUE2, counter, checkCounter);
    }

    private ObjectId(int timestamp, int randomValue1, short randomValue2, int counter, boolean checkCounter) {
        if ((randomValue1 & -16777216) != 0) {
            throw new IllegalArgumentException("The machine identifier must be between 0 and 16777215 (it must fit in three bytes).");
        } else if (checkCounter && (counter & -16777216) != 0) {
            throw new IllegalArgumentException("The counter must be between 0 and 16777215 (it must fit in three bytes).");
        } else {
            this.timestamp = timestamp;
            this.counter = counter & 16777215;
            this.randomValue1 = randomValue1;
            this.randomValue2 = randomValue2;
        }
    }

    public ObjectId(String hexString) {
        this(parseHexString(hexString));
    }

    public ObjectId(byte[] bytes) {
        this(ByteBuffer.wrap((byte[]) isTrueArgument("bytes has length of 12", bytes, ((byte[]) notNull("bytes", bytes)).length == 12)));
    }

    ObjectId(int timestamp, int machineAndProcessIdentifier, int counter) {
        this(legacyToBytes(timestamp, machineAndProcessIdentifier, counter));
    }

    public ObjectId(ByteBuffer buffer) {
        notNull("buffer", buffer);
        isTrueArgument("buffer.remaining() >=12", buffer.remaining() >= 12);
        this.timestamp = makeInt(buffer.get(), buffer.get(), buffer.get(), buffer.get());
        this.randomValue1 = makeInt((byte) 0, buffer.get(), buffer.get(), buffer.get());
        this.randomValue2 = makeShort(buffer.get(), buffer.get());
        this.counter = makeInt((byte) 0, buffer.get(), buffer.get(), buffer.get());
    }

    private static byte[] legacyToBytes(int timestamp, int machineAndProcessIdentifier, int counter) {
        byte[] bytes = new byte[]{int3(timestamp), int2(timestamp), int1(timestamp), int0(timestamp), int3(machineAndProcessIdentifier), int2(machineAndProcessIdentifier), int1(machineAndProcessIdentifier), int0(machineAndProcessIdentifier), int3(counter), int2(counter), int1(counter), int0(counter)};
        return bytes;
    }

    public byte[] toByteArray() {
        ByteBuffer buffer = ByteBuffer.allocate(12);
        this.putToByteBuffer(buffer);
        return buffer.array();
    }

    public void putToByteBuffer(ByteBuffer buffer) {
        notNull("buffer", buffer);
        isTrueArgument("buffer.remaining() >=12", buffer.remaining() >= 12);
        buffer.put(int3(this.timestamp));
        buffer.put(int2(this.timestamp));
        buffer.put(int1(this.timestamp));
        buffer.put(int0(this.timestamp));
        buffer.put(int2(this.randomValue1));
        buffer.put(int1(this.randomValue1));
        buffer.put(int0(this.randomValue1));
        buffer.put(short1(this.randomValue2));
        buffer.put(short0(this.randomValue2));
        buffer.put(int2(this.counter));
        buffer.put(int1(this.counter));
        buffer.put(int0(this.counter));
    }

    public int getTimestamp() {
        return this.timestamp;
    }

    public Date getDate() {
        return new Date(((long) this.timestamp & 4294967295L) * 1000L);
    }

    public String toHexString() {
        char[] chars = new char[24];
        int i = 0;
        byte[] var3 = this.toByteArray();
        int var4 = var3.length;

        for (int var5 = 0; var5 < var4; ++var5) {
            byte b = var3[var5];
            chars[i++] = HEX_CHARS[b >> 4 & 15];
            chars[i++] = HEX_CHARS[b & 15];
        }
        return new String(chars);
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o != null && this.getClass() == o.getClass()) {
            ObjectId objectId = (ObjectId) o;
            if (this.counter != objectId.counter) {
                return false;
            } else if (this.timestamp != objectId.timestamp) {
                return false;
            } else if (this.randomValue1 != objectId.randomValue1) {
                return false;
            } else {
                return this.randomValue2 == objectId.randomValue2;
            }
        } else {
            return false;
        }
    }

    public int hashCode() {
        int result = this.timestamp;
        result = 31 * result + this.counter;
        result = 31 * result + this.randomValue1;
        result = 31 * result + this.randomValue2;
        return result;
    }

    public int compareTo(ObjectId other) {
        if (other == null) {
            throw new NullPointerException();
        } else {
            byte[] byteArray = this.toByteArray();
            byte[] otherByteArray = other.toByteArray();

            for (int i = 0; i < 12; ++i) {
                if (byteArray[i] != otherByteArray[i]) {
                    return (byteArray[i] & 255) < (otherByteArray[i] & 255) ? -1 : 1;
                }
            }

            return 0;
        }
    }

    public String toString() {
        return this.toHexString();
    }

    private static byte[] parseHexString(String s) {
        if (!isValid(s)) {
            throw new IllegalArgumentException("invalid hexadecimal representation of an ObjectId: [" + s + "]");
        } else {
            byte[] b = new byte[12];

            for (int i = 0; i < b.length; ++i) {
                b[i] = (byte) Integer.parseInt(s.substring(i * 2, i * 2 + 2), 16);
            }

            return b;
        }
    }

    private static int dateToTimestampSeconds(Date time) {
        return (int) (time.getTime() / 1000L);
    }

    private static int makeInt(byte b3, byte b2, byte b1, byte b0) {
        return b3 << 24 | (b2 & 255) << 16 | (b1 & 255) << 8 | b0 & 255;
    }

    private static short makeShort(byte b1, byte b0) {
        return (short) ((b1 & 255) << 8 | b0 & 255);
    }

    private static byte int3(int x) {
        return (byte) (x >> 24);
    }

    private static byte int2(int x) {
        return (byte) (x >> 16);
    }

    private static byte int1(int x) {
        return (byte) (x >> 8);
    }

    private static byte int0(int x) {
        return (byte) x;
    }

    private static byte short1(short x) {
        return (byte) (x >> 8);
    }

    private static byte short0(short x) {
        return (byte) x;
    }

    static {
        try {
            SecureRandom secureRandom = new SecureRandom();
            RANDOM_VALUE1 = secureRandom.nextInt(16777216);
            RANDOM_VALUE2 = (short) secureRandom.nextInt(32768);
        } catch (Exception var1) {
            throw new RuntimeException(var1);
        }
    }

    public static <T> T notNull(String name, T value) {
        if (value == null) {
            throw new IllegalArgumentException(name + " can not be null");
        } else {
            return value;
        }
    }

    public static void isTrueArgument(String name, boolean condition) {
        if (!condition) {
            throw new IllegalArgumentException("state should be: " + name);
        }
    }

    public static <T> T isTrueArgument(String name, T value, boolean condition) {
        if (!condition) {
            throw new IllegalArgumentException("state should be: " + name);
        } else {
            return value;
        }
    }

    public static ObjectId get() {
        return new ObjectId();
    }

    /**
     * 生成 mongodb ObjectId
     * @return 24长度的十六进制字符的字符串
     */
    public static String nextId() {
        return ObjectId.get().toString();
    }

}
