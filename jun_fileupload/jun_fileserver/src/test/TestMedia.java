package test;

import java.io.File;
import java.io.FileInputStream;

import swift.net.lfs.LFS_Stream;
import swift.net.lfs.LFS_Stream.IReadStream;
import swift.net.lfs.LFS_Stream.ReadStreamEnum;

/**
 * 音乐？视频？图片？或是一个字符串
 * 这不仅是极致简单的，而且是最快的
 * 断点下载，断点上传（依然只有一行）
 * 对于视频，切片竟如此简单
 */
public class TestMedia
{
	static private String FILE_NAME = "Album/Video";

	public TestMedia()
	{
		long fileId = writeMedia("video.mp4", -1);
		readMedia(fileId);
	}

	/**
	 * 上传文件
	 * @param path
	 * @param fileId
	 * @return 上传成功的文件 ID
	 */
	private long writeMedia(String path, long fileId)
	{
		if (null == path) return -1;
		File file = new File(path);
		if (file.exists()) {
			try {
				FileInputStream inputStream = new FileInputStream(file);
				fileId = LFS_Stream.writeStream(FILE_NAME, fileId, inputStream, file.length());
				inputStream.close();
			} catch (Exception e) {
				fileId = -1;
			}
			System.out.println("FILE ID: " + fileId);
		}
		else {
			fileId = -1;
			System.out.println(path + " not exists");
		}
		return fileId;
	}

	/**
	 * 读取文件
	 * @param fileId
	 */
	private void readMedia(long fileId)
	{
		IReadStream readStream = new IReadStream()
		{
			@Override
			public boolean init(long fileId, int size, long sizeTotal, long sizeTotalRead, long offset)
			{
				//response.setHeader("Cache-Control", "max-age=604800");
				//response.setIntHeader("Etag", 0);
				return true;
			}

			@Override
			public boolean parseData(byte[] b, int bytesAvalibale, int size, long sizeTotal, long sizeTotalRead, long sizeTotalReaded, long offset)
			{
				try {
					//这里应实现自己的数据输出
					//可以输出到文件，也可以输出到 http
					//response.getOutputStream().write(b, 0, bytesAvalibale);
					return true;
				} catch (Exception e) {}
				return false;
			}
		};

		switch ((int)LFS_Stream.readStream(FILE_NAME, fileId, readStream)) {
			case ReadStreamEnum.ERROR_SOCKET_BACK_VALUE:
				//response.setStatus(HttpServletResponse.SC_NOT_FOUND);
				break;
			case ReadStreamEnum.ERROR_SOCKET_CLOSED:
				//response.setStatus(HttpServletResponse.SC_GATEWAY_TIMEOUT);
				break;
			case ReadStreamEnum.ERROR_IREAD_STREAM_PARSE_DATA:
			case ReadStreamEnum.ERROR_IREAD_STREAM_INIT:
			case ReadStreamEnum.ERROR_FILE_ID:
			case ReadStreamEnum.ERROR_OUT_OF_BOUNDS:
				//response.setStatus(HttpServletResponse.SC_FORBIDDEN);
				break;
		}
	}

}