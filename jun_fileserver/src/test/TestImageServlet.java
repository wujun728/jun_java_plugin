package test;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import swift.net.lfs.LFS_Stream;
import swift.net.lfs.LFS_Stream.IReadStream;
import swift.net.lfs.LFS_Stream.ReadStreamEnum;

/**
 * 这是一个完整的图片服务 http://localhost/testimage/123.jpg (事实上，后缀是忽略的，只是为了更像普通文件而已)
 */
@WebServlet("/testimage/*")
public class TestImageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	static private String FILE_NAME = "TestImage/Image";

	public TestImageServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, final HttpServletResponse response)
			throws ServletException, IOException {
		// 因为图片是不会变的，所以应该永久缓存才对（这里实现的是永久缓存）
		// 当然，也可以根据时间检查缓存
		if (null == request.getHeader("If-None-Match")) {
			String path = request.getPathInfo();
			if (null != path) {
				// 解析文件 ID，忽略后缀，只是为了更像普通文件而已
				path = path.substring(path.lastIndexOf("/") + 1).replaceAll("[^\\d]+.*", "");
				if (path.length() != 0) {
					try {
						long fileId = Long.parseLong(path);

						IReadStream readStream = new IReadStream() {
							@Override
							public boolean init(long fileId, int size, long sizeTotal, long sizeTotalRead,
									long offset) {
								// 设置客户端缓存
								response.setHeader("Cache-Control", "max-age=604800");
								response.setIntHeader("Etag", 0);
								return true;
							}

							@Override
							public boolean parseData(byte[] b, int bytesAvalibale, int size, long sizeTotal,
									long sizeTotalRead, long sizeTotalReaded, long offset) {
								try {
									response.getOutputStream().write(b, 0, bytesAvalibale);
									return true;
								} catch (Exception e) {
								}
								return false;
							}
						};

						switch ((int) LFS_Stream.readStream(FILE_NAME, fileId, readStream)) {
						case ReadStreamEnum.ERROR_SOCKET_BACK_VALUE:
							response.setStatus(HttpServletResponse.SC_NOT_FOUND);
							break;
						case ReadStreamEnum.ERROR_SOCKET_CLOSED:
							response.setStatus(HttpServletResponse.SC_GATEWAY_TIMEOUT);
							break;
						case ReadStreamEnum.ERROR_IREAD_STREAM_PARSE_DATA:
						case ReadStreamEnum.ERROR_IREAD_STREAM_INIT:
						case ReadStreamEnum.ERROR_FILE_ID:
						case ReadStreamEnum.ERROR_OUT_OF_BOUNDS:
							response.setStatus(HttpServletResponse.SC_FORBIDDEN);
							break;
						}
					} catch (NumberFormatException e) {
						response.setStatus(HttpServletResponse.SC_FORBIDDEN);
					}
				} else {
					response.setStatus(HttpServletResponse.SC_FORBIDDEN);
				}
			} else {
				response.setStatus(HttpServletResponse.SC_FORBIDDEN);
			}
		} else {
			response.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}