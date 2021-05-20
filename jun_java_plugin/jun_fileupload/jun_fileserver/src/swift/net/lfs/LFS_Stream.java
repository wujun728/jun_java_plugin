package swift.net.lfs;

import java.io.DataInputStream;
import java.io.InputStream;
import java.net.Socket;

public class LFS_Stream
{
	static public int SIZE_MAX_READ = 1024 * 1024;
	static public int SIZE_MAX_WRITE = 1024 * 1024;
	/**
	 * 一个小于等于 SIZE_MAX_READ 的值
	 */
	static public int SIZE_BUFFER_READ = 1024 * 2;
	/**
	 * 每次需写一个完整的数据，所以等于 SIZE_MAX_WRITE
	 */
	static public int SIZE_BUFFER_WRITE = SIZE_MAX_WRITE;

	/**
	 * 流式读取字节，用来即时输出数据（当读取的数据不需要处理，即只是过路数据的情况下，能在数据没有完全读取的情况下，流式返回给客户端）<br><br>
	 * <u>可以让更少的数据在内存驻留，快速返回给客户端</u>
	 * @param fileName
	 * @param fileId
	 * @param readStream
	 * @return 返回读取的实际大小，如果小于 0，则返回错误号
	 */
	static public long readStream(String fileName, long fileId, IReadStream readStream)
	{
		return readStream(fileName, fileId, readStream, 0, 0, 0, 0, null);
	}

	static public long readStream(String fileName, long fileId, IReadStream readStream, long offset, long sizeTotalRead)
	{
		return readStream(fileName, fileId, readStream, offset, sizeTotalRead, 0, 0, null);
	}

	/**
	 * 流式读取字节，用来即时输出数据（当读取的数据不需要处理，即只是过路数据的情况下，能在数据没有完全读取的情况下，流式返回给客户端）<br><br>
	 * <u>可以让更少的数据在内存驻留，快速返回给客户端</u>
	 * @param fileName
	 * @param fileId
	 * @param readStream
	 * @param offset 文件的偏移（默认：0）
	 * @param sizeTotalRead 从 offset 开始需要读取的总大小（默认：0，读取全部文件）
	 * @param sizeMaxRead 每次读取的最大字节，当文件比较大时进行切片读取（默认：0，SIZE_MAX_READ）
	 * @param sizeBuffer 缓冲的大小，每次从 Socket 读取的最大字节（默认：0，SIZE_BUFFER_READ）
	 * @param buffer 缓冲，不为空时 sizeBuffer 自动为 buffer 的大小
	 * @return 返回读取的实际大小，如果小于 0，则返回错误号
	 */
	static public long readStream(String fileName, long fileId, IReadStream readStream, long offset, long sizeTotalRead, int sizeMaxRead, int sizeBuffer, byte[] buffer)
	{
		if (fileId < 0 || fileId > LFSByteArray.UINT_MAX) return ReadStreamEnum.ERROR_FILE_ID;

		sizeMaxRead = sizeMaxRead > 0 ? sizeMaxRead : SIZE_MAX_READ;
		if (null == buffer) {
			sizeBuffer = sizeBuffer > 0 ? sizeBuffer : SIZE_BUFFER_READ;
			if (sizeBuffer > sizeMaxRead) {
				sizeBuffer = sizeMaxRead;
			}
			if (sizeTotalRead > 0 && sizeBuffer > sizeTotalRead) {
				sizeBuffer = (int)sizeTotalRead;
			}
		}
		else {
			sizeBuffer = buffer.length;
		}
		long sizeTotal = 0;
		long sizeTotalReaded = 0;
		int size;
		int bytesAvalibale;

		Socket con = LFSConnection.getConnection();
		try {
			LFSByteArray lb = new LFSByteArray();
			DataInputStream in = new DataInputStream(con.getInputStream());

			boolean b = true;
			while (b == true) {
				if (sizeTotalRead <= 0 || (size = (int)(sizeTotalRead - sizeTotalReaded)) > sizeMaxRead) {
					size = sizeMaxRead;
				}
				lb.reset();
//				lb.setStatement(""
//						//默认标记失败
//						+ "setState(-1);"
//						+ "local r = data_open(vars[0]);"
//						+ "if (r == 1) then;"
//						+ "	local id = vars[1];"
//						+ "	local offset = vars[2];"
//						+ "	local size = vars[3];"
//						+ "	r = data_read(id, offset, size);"
//						+ "	if (r >= 0) then;"
//						+ "		local dataData = vars.dataData;"
//						//标记成功（0: 已经读完，1: 还没有读完）
//						+ "		setState(dataData.sizeTotal > offset + dataData.size and 1 or 0);"
//						//是否是第一次读取，如果是，则写入文件总大小
//						+ "		if (vars[4]) then "
//						+ "			putLong(dataData.sizeTotal);"
//						+ "		end;"
//						//仅返回一个 ByteArray 变量，用来方便【流式读取字节】
//						//成功或失败的状态，通过 setState 返回（从而避免引入多个变量）
//						+ "		putBytes(dataData);"
//						+ "	end;"
//						+ "end;"
//						+ "");
				//这里和上面的效果一样，这里进行压缩来节省带宽 363 213
				lb.setStatement("setState(-1);if(data_open(vars[0])==1)then;if(data_read(vars[1],vars[2],vars[3])>=0)then;d=vars.dataData;setState(d.sizeTotal>vars[2]+d.size and 1 or 0);if(vars[4])then;putLong(d.sizeTotal);end;putBytes(d);end;end");

				lb.putString(fileName);
				//why?数据不会丢失吗？
				//在 LFS 中不会
				lb.putInt((int)fileId);
				lb.putLong(offset);
				lb.putInt(size);
				//是否是第一次读取
				if (sizeTotalReaded == 0) {
					lb.putBoolean(true);
				}

				if (lb.writeTo(con, LFSByteArray.WRITE_TYPE_STREAM_TRY_SKIP) == true) {
					//变量类型
					if (in.readByte() != LFSByteArray.EXTERN_TYPE_NULL) {
						b = lb.getState() == 1 ? true : false;
						if (sizeTotal == 0) {
							sizeTotal = in.readLong();
							if (in.readByte() != LFSByteArray.EXTERN_TYPE_NULL) {
								//变量长度（因为此处是 putBytes<or putString>，所以还需要读取一个 int 为实际内容的大小，其他类型的变量仅通过类型即可判断大小）
								size = in.readInt();
								sizeTotalRead = sizeTotalRead > 0 ? sizeTotalRead : sizeTotal;
								if (sizeTotalRead + offset > sizeTotal) {
									sizeTotalRead = sizeTotal - offset;
								}
								if (null != readStream && readStream.init(fileId, size, sizeTotal, sizeTotalRead, offset) == false) {
									b = false;
									sizeTotalReaded = ReadStreamEnum.ERROR_IREAD_STREAM_INIT;
								}
							}
							else {
								if (null != readStream) {
									readStream.init(fileId, 0, sizeTotal, sizeTotalRead, offset);
								}
								b = false;
								sizeTotalReaded = ReadStreamEnum.ERROR_OUT_OF_BOUNDS;
							}
						}
						else {
							size = in.readInt();
						}
						if (sizeTotalReaded >= 0) {
							offset += size;
							sizeTotalReaded += size;
							if (sizeTotalRead > 0 && sizeTotalRead == sizeTotalReaded) {
								b = false;
							}
							if (null == buffer) {
								buffer = new byte[sizeBuffer > size ? size : sizeBuffer];
							}
							while (size > 0) {
								bytesAvalibale = in.read(buffer, 0, sizeBuffer > size ? size : sizeBuffer);
								if (bytesAvalibale > 0) {
									size -= bytesAvalibale;
									if (null != readStream && readStream.parseData(buffer, bytesAvalibale, size, sizeTotal, sizeTotalRead, sizeTotalReaded, offset) == false) {
										b = false;
										sizeTotalReaded = ReadStreamEnum.ERROR_IREAD_STREAM_PARSE_DATA;
										break;
									}
								}
								else if (bytesAvalibale < 0) {
									b = false;
									sizeTotalReaded = ReadStreamEnum.ERROR_SOCKET_CLOSED;
									break;
								}
							}
							//跳过 putBytes<or putString> 添加的 1 字节的尾（此字节恒为 0，在某些语言中用来标记字符串结束）
							//Socket InputStream 中的内容一定要完全读取完毕，然后此 Socket 才能被其他地方重复使用
							in.read();
						}
					}
					else {
						b = false;
						sizeTotalReaded = ReadStreamEnum.ERROR_OUT_OF_BOUNDS;
					}
				}
				else {
					b = false;
					sizeTotalReaded = ReadStreamEnum.ERROR_SOCKET_BACK_VALUE;
				}
			}

			lb.clear();
		} catch (Exception e) {
			sizeTotalReaded = ReadStreamEnum.ERROR_SOCKET_CLOSED;
		}
		if (sizeTotalReaded >= 0) {
			LFSConnection.put(con);
		}
		else {
			LFSConnection.close(con);
		}
		return sizeTotalReaded;
	}

	/**
	 * 流式写入字节，用来上传文件<br><br>
	 * <u>可以让更少的数据在内存驻留，切片上传文件</u>
	 * @param fileName
	 * @param fileId 文件 ID，当为 -1 时，写入新的文件
	 * @param inputStream
	 * @param sizeTotalWrite 需要写入的总大小
	 * @return 返回文件 ID，如果小于 0，则返回错误号
	 */
	static public long writeStream(String fileName, long fileId, InputStream inputStream, long sizeTotalWrite)
	{
		return writeStream(fileName, fileId, inputStream, sizeTotalWrite, 0, 0, 0, 0, null);
	}

	static public long writeStream(String fileName, long fileId, InputStream inputStream, long sizeTotalWrite, long offset, long sizeTotal)
	{
		return writeStream(fileName, fileId, new WriteStream(inputStream), sizeTotalWrite, offset, sizeTotal, 0, 0, null);
	}

	/**
	 * 流式写入字节，用来上传文件<br><br>
	 * <u>可以让更少的数据在内存驻留，切片上传文件</u>
	 * @param fileName
	 * @param fileId 文件 ID，当为 -1 时，写入新的文件
	 * @param inputStream
	 * @param sizeTotalWrite 需要写入的总大小
	 * @param offset 文件的偏移（默认：0）
	 * @param sizeTotal 文件的总大小（默认：0，等于 sizeTotalWrite）
	 * @param sizeMaxWrite 每次上传的最大字节，当文件比较大时进行切片上传（默认：0，SIZE_MAX_WRITE）
	 * @param sizeBuffer 缓冲的大小，每次上传的最大字节（默认：0，SIZE_BUFFER_WRITE）
	 * @param buffer 缓冲，不为空时 sizeBuffer 自动为 buffer 的大小
	 * @return 返回文件 ID，如果小于 0，则返回错误号
	 */
	static public long writeStream(String fileName, long fileId, InputStream inputStream, long sizeTotalWrite, long offset, long sizeTotal, int sizeMaxWrite, int sizeBuffer, byte[] buffer)
	{
		return writeStream(fileName, fileId, new WriteStream(inputStream), sizeTotalWrite, offset, sizeTotal, sizeMaxWrite, sizeBuffer, buffer);
	}

	/**
	 * 流式写入字节，用来上传文件<br><br>
	 * <u>可以让更少的数据在内存驻留，切片上传文件</u>
	 * @param fileName
	 * @param fileId 文件 ID，当为 -1 时，写入新的文件
	 * @param writeStream
	 * @param sizeTotalWrite 需要写入的总大小
	 * @param offset 文件的偏移（默认：0）
	 * @param sizeTotal 文件的总大小（默认：0，等于 sizeTotalWrite）
	 * @param sizeMaxWrite 每次上传的最大字节，当文件比较大时进行切片上传（默认：0，SIZE_MAX_WRITE）
	 * @param sizeBuffer 缓冲的大小，每次上传的最大字节（默认：0，SIZE_BUFFER_WRITE）
	 * @param buffer 缓冲，不为空时 sizeBuffer 自动为 buffer 的大小
	 * @return 返回文件 ID，如果小于 0，则返回错误号
	 */
	static public long writeStream(String fileName, long fileId, IWriteStream writeStream, long sizeTotalWrite, long offset, long sizeTotal, int sizeMaxWrite, int sizeBuffer, byte[] buffer)
	{
		if (fileId > LFSByteArray.UINT_MAX) return WriteStreamEnum.ERROR_FILE_ID;
		sizeMaxWrite = sizeMaxWrite > 0 ? sizeMaxWrite : SIZE_MAX_READ;
		if (null == buffer) {
			sizeBuffer = sizeBuffer > 0 ? sizeBuffer : SIZE_BUFFER_READ;
			if (sizeBuffer > sizeMaxWrite) {
				sizeBuffer = sizeMaxWrite;
			}
			if (sizeTotalWrite > 0 && sizeBuffer > sizeTotalWrite) {
				sizeBuffer = (int)sizeTotalWrite;
			}
			buffer = new byte[sizeBuffer];
		}
		else {
			sizeBuffer = buffer.length;
		}
		if (offset + sizeTotalWrite > sizeTotal) {
			sizeTotal = offset + sizeTotalWrite;
		}
		long sizeTotalWrited = 0;
		int size;
		Socket con = LFSConnection.getConnection();
		try {
			LFSByteArray lb = new LFSByteArray();

			boolean b = true;
			while (b == true) {
				if ((size = (int)(sizeTotalWrite - sizeTotalWrited)) > sizeMaxWrite) {
					size = sizeMaxWrite;
				}
				if (size > sizeBuffer) {
					size = sizeBuffer;
				}

				size = writeStream.parseData(buffer, size, sizeTotal, sizeTotalWrite, sizeTotalWrited, offset);
				if (size > 0) {
					lb.reset();
//					lb.setStatement(""
//							//默认标记失败
//							+ "setState(-1);"
//							+ "local r = data_open(vars[0]);"
//							+ "if (r == 1) then;"
//							+ "	local id = vars[1];"
//							+ "	local offset = vars[2];"
//							+ "	local size = vars[3];"
//							+ "	if (vars[4] > 0) then;"
//							+ "		id = data_setSize(id, vars[4], 0, 2);"
//							+ "	end;"
//							+ "	if (id >= 0) then;"
//							+ "		id = data_write(id, vars[5], size, offset, 0, 2);"
//							+ "		if (id >= 0) then;"
//							+ "			setState(0);"
//							+ "			putInt(id);"
//							+ "		end;"
//							+ "	end;"
//							+ "	if (id < 0) then;"
//							+ "		setState(id);"
//							+ "	end;"
//							+ "end;"
//							+ "");
					//这里和上面的效果一样，这里进行压缩来节省带宽 364 234
					lb.setStatement("setState(-1);if(data_open(vars[0])==1)then;i=vars[1];t=vars[4];if(t>0)then;i=data_setSize(i,t,0,2);end;if(i>=0)then;i=data_write(i,vars[5],vars[3],vars[2],0,2);if(i>=0)then;setState(0);putInt(i);end;end;if(i<0)then;setState(i);end;end");

					lb.putString(fileName);
					if (fileId < 0) {
						lb.putLong(-1);
					}
					else {
						lb.putInt((int)fileId);
					}
					if (offset > LFSByteArray.UINT_MAX) {
						lb.putLong(offset);
					}
					else {
						lb.putInt((int)offset);
					}
					lb.putInt(size);
					//是否是第一次写入
					if (sizeTotalWrited == 0) {
						if (sizeTotal > LFSByteArray.UINT_MAX) {
							lb.putLong(sizeTotal);
						}
						else {
							lb.putInt((int)sizeTotal);
						}
					}
					else {
						lb.putByte(-1);
					}
					lb.putBytes(buffer, 0, size);

					if (lb.writeTo(con) == true) {
						fileId = lb.getInt(0);
						offset += size;
						sizeTotalWrited += size;
						writeStream.progress(buffer, size, sizeTotal, sizeTotalWrite, sizeTotalWrited, offset);
						if (sizeTotalWrite == sizeTotalWrited) {
							b = false;
						}
					}
					else {
						b = false;
						fileId = WriteStreamEnum.ERROR_SOCKET_BACK_VALUE;
					}
				}
				else {
					b = false;
					fileId = WriteStreamEnum.ERROR_IWRITE_STREAM_PARSE_DATA;
				}
			}

			lb.clear();
		} catch (Exception e) {
			fileId = WriteStreamEnum.ERROR_SOCKET_CLOSED;
		}
		if (fileId >= 0) {
			LFSConnection.put(con);
		}
		else {
			LFSConnection.close(con);
		}
		return fileId;
	}

	public class ReadStreamEnum
	{
		/**
		 * 错误的文件 ID，必须不小于 0
		 */
		static public final int ERROR_FILE_ID = -1;
		/**
		 * 连接已断开
		 */
		static public final int ERROR_SOCKET_CLOSED = -2;
		/**
		 * 文件 ID 对应的文件不存在，即没有该文件
		 */
		static public final int ERROR_SOCKET_BACK_VALUE = -3;
		/**
		 * 用户自己的解析出错
		 */
		static public final int ERROR_IREAD_STREAM_PARSE_DATA = -4;
		/**
		 * 用户自己的初始化出错
		 */
		static public final int ERROR_IREAD_STREAM_INIT = -5;
		/**
		 * 指定的 offset 或 size 不合法，即文件偏移越界
		 */
		static public final int ERROR_OUT_OF_BOUNDS = -6;
	}

	public class WriteStreamEnum
	{
		/**
		 * 错误的文件 ID
		 */
		static public final int ERROR_FILE_ID = -1;
		/**
		 * 连接已断开
		 */
		static public final int ERROR_SOCKET_CLOSED = -2;
		/**
		 * 文件写入失败
		 */
		static public final int ERROR_SOCKET_BACK_VALUE = -3;
		/**
		 * 用户自己的数据出错，一般是没有向缓冲中写入有效的数据
		 */
		static public final int ERROR_IWRITE_STREAM_PARSE_DATA = -4;
	}

	public interface IReadStream
	{
		/**
		 * 第一次返回数据时应该进行的初始化（比如：设置 http 的 header 等）
		 * @param fileId
		 * @param size 此次读取的实际大小
		 * @param sizeTotal 文件的真实总大小
		 * @param sizeTotalRead 从 offset 开始需要读取的总大小（默认：0，读取全部文件）
		 * @param offset 文件的偏移（默认：0）
		 * @return 应该返回成功，如果失败，则结束 IReadStream（错误号：ERROR_IREAD_STREAM_INIT）
		 */
		boolean init(long fileId, int size, long sizeTotal, long sizeTotalRead, long offset);

		/**
		 * 每次从 Socket 读取数据后的回调，通知进行相应的处理
		 * @param b 数据缓冲
		 * @param bytesAvalibale 缓冲中可用的大小
		 * @param size 还需要读取到缓冲的大小
		 * @param sizeTotal 文件的真实总大小
		 * @param sizeTotalRead 需要读取的总大小
		 * @param sizeTotalReaded 已经读取的大小
		 * @param offset 当前的文件的偏移
		 * @return 应该返回成功，如果失败，则结束 IReadStream（错误号：ERROR_IREAD_STREAM_PARSE_DATA）
		 */
		boolean parseData(byte[] b, int bytesAvalibale, int size, long sizeTotal, long sizeTotalRead, long sizeTotalReaded, long offset);
	}

	public interface IWriteStream
	{
		/**
		 * 没有实际用途，可以是一个空函数<br>
		 * 一种比较好的使用是在此返回：<br>
		 * return LFS_Stream.writeStream(fileName, fileId, inputStream, sizeTotalWrite);<br>
		 * 从而可以方便使用：<br>
		 * fileId = writeStream.init();
		 * @return
		 */
		long init();

		/**
		 * 每次从一个 InputStream 或其他途径写入字节到数据缓冲中，并返回有效字节数<br>
		 * @param b 数据缓冲
		 * @param size 可以写入的最大大小
		 * @param sizeTotal 文件总大小
		 * @param sizeTotalWrite 需要写入的文件总大小（通常等于 sizeTotal）
		 * @param sizeTotalWrited 已经写入的大小
		 * @param offset 文件的偏移
		 * @return 数据缓冲中的有效字节数
		 */
		int parseData(byte[] b, int size, long sizeTotal, long sizeTotalWrite, long sizeTotalWrited, long offset);

		/**
		 * 上传进度：sizeTotaleWrited / sizeTotalWrite
		 * @param b 数据缓冲
		 * @param size 此次写入的真实大小
		 * @param sizeTotal 文件总大小
		 * @param sizeTotalWrite 需要写入的文件总大小（通常等于 sizeTotal）
		 * @param sizeTotalWrited 已经写入的大小
		 * @param offset 文件的偏移
		 */
		void progress(byte[] b, int size, long sizeTotal, long sizeTotalWrite, long sizeTotalWrited, long offset);
	}

	static public class WriteStream implements IWriteStream
	{
		public InputStream inputStream = null;

		public WriteStream(InputStream inputStream)
		{
			this.inputStream = inputStream;
		}

		@Override
		public long init() {
			return -1;
		}

		@Override
		public int parseData(byte[] b, int size, long sizeTotal, long sizeTotalWrite, long sizeTotalWrited, long offset)
		{
			if (null == inputStream) return 0;
			int bytesAvailable;
			int bytesOffset = 0;
			try {
				if (sizeTotalWrited == 0) {
					inputStream.skip(offset);
				}
				while (size > 0) {
					bytesAvailable = inputStream.read(b, bytesOffset, size);
					if (bytesAvailable > 0) {
						size -= bytesAvailable;
						bytesOffset += bytesAvailable;
					}
					else if (bytesAvailable < 0) {
						return 0;
					}
				}
			} catch (Exception e) {
				return 0;
			}
			return bytesOffset;
		}
		
		@Override
		public void progress(byte[] b, int size, long sizeTotal, long sizeTotalWrite, long sizeTotalWrited, long offset)
		{
			
		}

	}

}