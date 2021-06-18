package swift.net.lfs;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class LFSByteArray extends ByteArray
{
	static public final long UINT_MAX = ((long)1 << 32) - 1;

	static public final int EXTERN_TYPE_BYTE = 1;
	static public final int EXTERN_TYPE_BOOLEAN = -2;
	static public final int EXTERN_TYPE_SHORT = 2;
	static public final int EXTERN_TYPE_INT = 4;
	static public final int EXTERN_TYPE_LONG = 8;
	static public final int EXTERN_TYPE_FLOAT = 3;
	static public final int EXTERN_TYPE_DOUBLE = 7;
	static public final int EXTERN_TYPE_STRING = -4;
	static public final int EXTERN_TYPE_STRING_BYTES = -5;
	static public final int EXTERN_TYPE_BYTES = -3;
	static public final int EXTERN_TYPE_NULL = -1;

	static public final int ACTION_TYPE_STATEMENT = 102;
	
	static public final int WRITE_TYPE_NORMAL = 0;
	static public final int WRITE_TYPE_STREAM_TRY_READ = 1;
	static public final int WRITE_TYPE_STREAM_TRY_SKIP = 2;

	public int STRING_TYPE = EXTERN_TYPE_STRING_BYTES;

	protected int[] varsMeta;
	protected int varsLength;
	protected int messageLength;
	/**
	 * 系统状态
	 */
	protected int status;
	/**
	 * 用户状态
	 */
	protected int state;
	/**
	 * 类型
	 */
	protected int actionType;
	/**
	 * Server 用时
	 */
	protected long time;
	/**
	 * 全部用时（含通信用时）
	 */
	public long totalTime;

	public LFSByteArray()
	{
		this(32);
	}

	/**
	 * @param size
	 */
	public LFSByteArray(int size)
	{
		super(size);
		reset();
	}

	public void putByte(int v)
	{
		writeByte(EXTERN_TYPE_BYTE);
		writeByte(v);
		varsLength++;
	}

	public void putBytes(byte[] v)
	{
		writeByte(EXTERN_TYPE_BYTES);
		writeInt(v.length);
		write(v);
		writeByte(0);
		varsLength++;
	}

	public void putBytes(byte[] v, int off, int len)
	{
		writeByte(EXTERN_TYPE_BYTES);
		writeInt(len);
		write(v, off, len);
		writeByte(0);
		varsLength++;
	}

	public void putBoolean(boolean v)
	{
		writeByte(EXTERN_TYPE_BOOLEAN);
		writeBoolean(v);
		varsLength++;
	}

	public void putNull()
	{
		writeByte(EXTERN_TYPE_NULL);
		varsLength++;
	}

	public void putShort(int v)
	{
		writeByte(EXTERN_TYPE_SHORT);
		writeShort(v);
		varsLength++;
	}

	public void putChar(int v)
	{
		writeByte(EXTERN_TYPE_SHORT);
		writeChar(v);
		varsLength++;
	}

	public void putInt(int v)
	{
		writeByte(EXTERN_TYPE_INT);
		writeInt(v);
		varsLength++;
	}

	public void putLong(long v)
	{
		writeByte(EXTERN_TYPE_LONG);
		writeLong(v);
		varsLength++;
	}

	public void putFloat(float v)
	{
		writeByte(EXTERN_TYPE_FLOAT);
		writeFloat(v);
		varsLength++;
	}

	public void putDouble(double v)
	{
		writeByte(EXTERN_TYPE_DOUBLE);
		writeDouble(v);
		varsLength++;
	}

	public void putString(String v)
	{
		try {
			byte[] b = v.getBytes("UTF-8");
			writeByte(STRING_TYPE);
			writeInt(b.length);
			write(b);
			writeByte(0);
			varsLength++;
		} catch (Exception e) {}
	}

	public void putStringBytes(String v)
	{
		try {
			byte[] b = v.getBytes("UTF-8");
			writeByte(EXTERN_TYPE_STRING_BYTES);
			writeInt(b.length);
			write(b);
			writeByte(0);
			varsLength++;
		} catch (Exception e) {}
	}

	public boolean setStatement(String v) throws Exception
	{
		if (position == 12) {
			try {
				byte[] b = v.getBytes("UTF-8");
				writeInt(b.length);
				write(b);
				writeByte(0);
				return true;
			} catch (Exception e) {}
		}
		throw new Exception("Must be the first");
	}

	public void setActionType(int v)
	{
		actionType = v;
	}

	public void writeTo(OutputStream out) throws IOException
	{
		int len = position;
		switch (actionType) {
			case ACTION_TYPE_STATEMENT:
				position = 8;
				writeInt(varsLength);
				break;
			default:
				len = 8;
				break;
		}
		position = 0;
		writeInt(len - 4);
		writeInt(actionType);
		position = len;
		super.writeTo(out);
	}

	private void __writeTo(Socket socket) throws IOException
	{
		totalTime = System.currentTimeMillis();
		writeTo(socket.getOutputStream());
		socket.getOutputStream().flush();
		InputStream in = socket.getInputStream();
		reset();
		position = 0;
		messageLength = 28;
		ensureCapacity(messageLength, false);
		int bytesAvalibale = 0;
		while (position < messageLength) {
			bytesAvalibale = in.read(buf, position, messageLength - position);
			if (bytesAvalibale > 0) {
				position += bytesAvalibale;
			}
			else if (bytesAvalibale < 0) {
				throw new IOException("Broken Pipe");
			}
		}
	}

	/**
	 * 写入数据并返回读取的数据
	 * @param socket
	 * @return status == 0 && state >= 0（方便判断返回结果，通常 state < 0 表示错误，但具体含义由用户自己决定）
	 * @throws IOException
	 */
	public boolean writeTo(Socket socket) throws IOException
	{
		return writeTo(socket, WRITE_TYPE_NORMAL);
	}
	
	/**
	 * 写入数据并返回读取的数据
	 * @param socket
	 * @param type <ul><ul>
	 * <li>WRITE_TYPE_NORMAL: 读取全部数据并解析</li>
	 * <li>WRITE_TYPE_STREAM_TRY_READ: 当失败时读取全部数据并解析</li>
	 * <li>WRITE_TYPE_STREAM_TRY_SKIP: 当失败时忽略后续数据</li></ul></ul>
	 * @return status == 0 && state >= 0（方便判断返回结果，通常 state < 0 表示错误，但具体含义由用户自己决定）
	 * @throws IOException
	 */
	public boolean writeTo(Socket socket, int type) throws IOException
	{
		return writeTo(socket, type, null);
	}

	/**
	 * 写入数据并返回读取的数据
	 * @param socket
	 * @param type <ul><ul>
	 * <li>WRITE_TYPE_NORMAL: 读取全部数据并解析</li>
	 * <li>WRITE_TYPE_STREAM_TRY_READ: 当失败时读取全部数据并解析</li>
	 * <li>WRITE_TYPE_STREAM_TRY_SKIP: 当失败时忽略后续数据</li></ul></ul>
	 * @param streamParse
	 * @return status == 0 && state >= 0（方便判断返回结果，通常 state < 0 表示错误，但具体含义由用户自己决定）
	 * @throws IOException
	 */
	public boolean writeTo(Socket socket, int type, IStreamParse streamParse) throws IOException
	{
		__writeTo(socket);
		position = 0;
		
		messageLength = readInt() + 4;
		status = readInt();
		actionType = readInt();
		state = readInt();
		varsLength = readInt();
		time = readLong();
		
		if (type == WRITE_TYPE_NORMAL) {
			readStream(socket, streamParse);
		}
		else if (status != 0 || state < 0) {
			if (type == WRITE_TYPE_STREAM_TRY_READ) {
				readStream(socket, streamParse);
			}
			else if (type == WRITE_TYPE_STREAM_TRY_SKIP) {
				socket.getInputStream().skip(messageLength - position);
			}
		}
		else if (null != streamParse) {
			readStream(socket, streamParse);
		}
		return status == 0 && state >= 0;
	}

	public void readStream(Socket socket, IStreamParse streamParse) throws IOException
	{
		ensureCapacity(messageLength, false);
		InputStream in = socket.getInputStream();
		int bytesAvalibale = 0;
		while (position < messageLength) {
			bytesAvalibale = in.read(buf, position, messageLength - position);
			if (bytesAvalibale > 0) {
				if (null != streamParse) {
					streamParse.parseData(buf, bytesAvalibale, position, messageLength);
				}
				position += bytesAvalibale;
			}
			else if (bytesAvalibale < 0) {
				throw new IOException("Broken Pipe");
			}
		}
		initVars(false);
		totalTime = System.currentTimeMillis() - totalTime;
	}

	public int readNext(Socket socket) throws IOException
	{
		return readNext(socket.getInputStream(), false);
	}

	public int readNext(Socket socket, boolean initVars) throws IOException
	{
		return readNext(socket.getInputStream(), initVars);
	}

	public int readNext(InputStream in) throws IOException
	{
		return readNext(in, false);
	}

	public int readNext(InputStream in, boolean initVars) throws IOException
	{
		int bytesAvalibale = -1;
		if (position < messageLength) {
			bytesAvalibale = in.read(buf, position, messageLength - position);
			if (bytesAvalibale > 0) {
				position += bytesAvalibale;
				if (position == messageLength) {
					if (initVars == true) {
						initVars(true);
					}
					totalTime = System.currentTimeMillis() - totalTime;
				}
			}
			else if (bytesAvalibale < 0) {
				throw new IOException("Broken Pipe");
			}
		}
		return bytesAvalibale;
	}

	public int initVars(boolean readHead)
	{
		return initVars(readHead, 0, -1);
	}

	public int initVars(boolean readHead, int start, int end)
	{
		if (readHead == true) {
			position = 0;
			messageLength = readInt() + 4;
			status = readInt();
			actionType = readInt();
			state = readInt();
			varsLength = readInt();
			time = readLong();
		}
		if (null == varsMeta) {
			varsMeta = new int[varsLength * 3];
		}
		if (end == -1 || end >= varsLength) {
			end = varsLength - 1;
		}
		if (start == 0) {
			position = 28;
		}
		else {
			position = varsMeta[(start - 1) * 3 + 1] + varsMeta[(start - 1) * 3 + 2];
		}
		int len = buf.length;
		int t = 0;
		for (int j = start * 3; start <= end && position < len; start++) {
			t = read();
			varsMeta[j++] = t;
			varsMeta[j] = position;
			switch (t) {
				case EXTERN_TYPE_INT:
				case EXTERN_TYPE_LONG:
				case EXTERN_TYPE_SHORT:
					break;
				case EXTERN_TYPE_DOUBLE:
				case EXTERN_TYPE_FLOAT:
					t++;
					break;
				case EXTERN_TYPE_BYTE:
				case EXTERN_TYPE_BOOLEAN:
					t = 1;
					break;
				case EXTERN_TYPE_NULL:
					t = 0;
					break;
				default:
					if (position + 5 <= len) {
						t = readInt();
						position++;
						varsMeta[j] += 4;
						if (position + t > len) {
							break;
						}
					}
					else {
						break;
					}
					break;
			}
			j++;
			varsMeta[j++] = t;
			position += t;
		}
		return start;
	}

	public int getLength(int index)
	{
		int length = 0;
		if (index < varsLength && null != varsMeta) {
			index = index * 3;
			length = varsMeta[index + 2];
		}
		return length;
	}

	public Object get(int index)
	{
		if (index < varsLength && null != varsMeta) {
			switch (varsMeta[index * 3]) {
				case EXTERN_TYPE_INT:
					return getInt(index);
				case EXTERN_TYPE_LONG:
					return getLong(index);
				case EXTERN_TYPE_DOUBLE:
					return getDouble(index);
				case EXTERN_TYPE_FLOAT:
					return getFloat(index);
				case EXTERN_TYPE_SHORT:
					return getShort(index);
				case EXTERN_TYPE_BYTE:
					return getByte(index);
				case EXTERN_TYPE_BOOLEAN:
					return getBoolean(index);
				case EXTERN_TYPE_NULL:
					return null;
				case EXTERN_TYPE_STRING:
				case EXTERN_TYPE_STRING_BYTES:
					return getString(index);
				default:
					return getBytes(index);
			}
		}
		return null;
	}

	public byte getByte(int index)
	{
		if (index < varsLength && null != varsMeta) {
			index *= 3;
			if (varsMeta[index] == EXTERN_TYPE_BYTE) {
				position = varsMeta[++index];
				return readByte();
			}
		}
		return 0;
	}

	public int getUnsignedByte(int index)
	{
		if (index < varsLength && null != varsMeta) {
			index *= 3;
			if (varsMeta[index] == EXTERN_TYPE_BYTE) {
				position = varsMeta[++index];
				return readUnsignedByte();
			}
		}
		return 0;
	}

	public byte[] getBytes(int index)
	{
		if (index < varsLength && null != varsMeta) {
			index *= 3;
			switch (varsMeta[index]) {
				case EXTERN_TYPE_BYTES:
				case EXTERN_TYPE_STRING:
				case EXTERN_TYPE_STRING_BYTES:
					position = varsMeta[++index];
					index = varsMeta[++index];
					if (index > 0) {
						byte[] b = new byte[index];
						read(b);
						return b;
					}
					break;
			}
		}
		return null;
	}

	public byte[] getBytes(int index, int len)
	{
		if (index < varsLength && null != varsMeta) {
			index *= 3;
			switch (varsMeta[index]) {
				case EXTERN_TYPE_BYTES:
				case EXTERN_TYPE_STRING:
				case EXTERN_TYPE_STRING_BYTES:
					position = varsMeta[++index];
					index = varsMeta[++index];
					if (index > 0 && len > 0) {
						len = len > index ? index : len;
						byte[] b = new byte[len];
						read(b);
						return b;
					}
					break;
			}
		}
		return null;
	}

	public byte[] getBytes(int index, byte[] b)
	{
		if (index < varsLength && null != varsMeta) {
			index *= 3;
			switch (varsMeta[index]) {
				case EXTERN_TYPE_BYTES:
				case EXTERN_TYPE_STRING:
				case EXTERN_TYPE_STRING_BYTES:
					position = varsMeta[++index];
					index = varsMeta[++index];
					if (index > 0) {
						read(b);
						return b;
					}
					break;
			}
		}
		return null;
	}

	public byte[] getBytes(int index, byte[] b, int off, int len)
	{
		if (index < varsLength && null != varsMeta) {
			index *= 3;
			switch (varsMeta[index]) {
				case EXTERN_TYPE_BYTES:
				case EXTERN_TYPE_STRING:
				case EXTERN_TYPE_STRING_BYTES:
					position = varsMeta[++index];
					index = varsMeta[++index];
					if (index > 0 && len > 0) {
						read(b, off, len);
						return b;
					}
					break;
			}
		}
		return null;
	}

	public String getString(int index)
	{
		if (index < varsLength && null != varsMeta) {
			index *= 3;
			switch (varsMeta[index]) {
			case EXTERN_TYPE_STRING:
			case EXTERN_TYPE_STRING_BYTES:
				position = varsMeta[++index];
				index = varsMeta[++index];
				if (index > 0) {
					byte[] b = new byte[index];
					read(b);
					try {
						return new String(b, "UTF-8");
					} catch (Exception e) {}
				}
				break;
			}
		}
		return null;
	}

	public boolean getBoolean(int index)
	{
		if (index < varsLength && null != varsMeta) {
			index *= 3;
			if (varsMeta[index] == EXTERN_TYPE_BOOLEAN) {
				position = varsMeta[++index];
				return readBoolean();
			}
		}
		return false;
	}

	public short getShort(int index)
	{
		if (index < varsLength && null != varsMeta) {
			index *= 3;
			if (varsMeta[index] == EXTERN_TYPE_SHORT) {
				position = varsMeta[++index];
				return readShort();
			}
		}
		return 0;
	}

	public int getUnsignedShort(int index)
	{
		if (index < varsLength && null != varsMeta) {
			index *= 3;
			if (varsMeta[index] == EXTERN_TYPE_SHORT) {
				position = varsMeta[++index];
				return readUnsignedShort();
			}
		}
		return 0;
	}

	public char getChar(int index)
	{
		if (index < varsLength && null != varsMeta) {
			index *= 3;
			if (varsMeta[index] == EXTERN_TYPE_SHORT) {
				position = varsMeta[++index];
				return readChar();
			}
		}
		return 0;
	}

	public int getInt(int index)
	{
		if (index < varsLength && null != varsMeta) {
			index *= 3;
			if (varsMeta[index] == EXTERN_TYPE_INT) {
				position = varsMeta[++index];
				return readInt();
			}
		}
		return 0;
	}

	public long getLong(int index)
	{
		if (index < varsLength && null != varsMeta) {
			index *= 3;
			if (varsMeta[index] == EXTERN_TYPE_LONG) {
				position = varsMeta[++index];
				return readLong();
			}
		}
		return 0;
	}

	public float getFloat(int index)
	{
		if (index < varsLength && null != varsMeta) {
			index *= 3;
			if (varsMeta[index] == EXTERN_TYPE_FLOAT) {
				position = varsMeta[++index];
				return readFloat();
			}
		}
		return 0;
	}

	public double getDouble(int index)
	{
		if (index < varsLength && null != varsMeta) {
			index *= 3;
			if (varsMeta[index] == EXTERN_TYPE_DOUBLE) {
				position = varsMeta[++index];
				return readDouble();
			}
		}
		return 0;
	}

	public int getMessageLength()
	{
		return messageLength > 0 ? messageLength : position;
	}

	public int getStatus()
	{
		return status;
	}

	public int getState()
	{
		return state;
	}

	public int getActionType()
	{
		return actionType;
	}

	public int getVarsLength()
	{
		return varsLength;
	}

	public long getTime()
	{
		return time;
	}

	public double getTimeSecond()
	{
		return (double)time / 1000000000;
	}

	public double getTimeMillis()
	{
		return (double)time / 1000000;
	}

	public String getTimeString()
	{
		return String.format("%1$.9f", (double)time / 1000000000);
	}

	public String getTimeStringMillis()
	{
		return String.format("%1$.6f", (double)time / 1000000);
	}

	public long getTotalTimeMillis()
	{
		return totalTime;
	}

	public void reset()
	{
		position = 12;
		messageLength = 0;
		varsLength = 0;
		actionType = ACTION_TYPE_STATEMENT;
		varsMeta = null;
	}

	public void clear()
	{
		super.clear();
		varsMeta = null;
	}

	public interface IStreamParse
	{
		void parseData(byte[] b, int bytesAvalibale, int position, int messageLength);
	}

}