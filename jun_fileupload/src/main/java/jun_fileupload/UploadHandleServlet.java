package jun_fileupload;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadBase;
import org.apache.commons.fileupload.ProgressListener;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
//import com.jun.plugin.utils.StringHelper;
import com.jun.plugin.utils.StringUtil;
import com.jun.plugin.utils.time.DateUtil;
//import com.thinkive.base.config.Configuration;
//import com.thinkive.base.jdbc.DataRow;
//import com.thinkive.base.util.DateHelper;
//import com.thinkive.base.util.StringHelper;
//import com.thinkive.gateway.v2.result.Result;

/**
 * @描述: 文件上传servlet
 */
@SuppressWarnings("serial")
public class UploadHandleServlet extends HttpServlet {

	private static Logger logger = Logger.getLogger(UploadHandleServlet.class);

	private static long fileSizeMax = 0;//Long.parseLong(Configuration.getString("h5_upload.fileSizeMax", "2097152")); // 单个文件的最大值，默认5M

	private static long sizeMax = 0;//Long.parseLong(Configuration.getString("h5_upload.sizeMax", "10485760")); // 单次上传的多个文件的最大值,默认10M

	private static String savePath = "";//Configuration.getString("h5_upload.savePath"); // 文件存储路径

	private static String tempPath = "";//Configuration.getString("h5_upload.tempPath"); // 上传临时文件存放路径

	private static String dbSavePathStartFolder = "";//Configuration.getString("h5_upload.dbSavePathStartFolder"); // 数据库存储路径起始文件夹

	private static Set<String> fileTypeSet = new HashSet<String>(); // 允许上传的文件类型，用“|”分隔，全小写

	private static boolean isDeleteTempFile = true; // 是否删除临时文件

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 设置文件保存和临时文件的默认路径：WebRoot下的upload文件夹 和 temp文件夹
		if (StringUtil.isEmpty(savePath)) {
			savePath = this.getServletContext().getRealPath("/upload");
		}
		if (StringUtil.isEmpty(tempPath)) {
			tempPath = this.getServletContext().getRealPath("/temp");
		}
		// 创建临时目录
		File tmpFile = new File(tempPath);
		if (!tmpFile.exists()) {
			tmpFile.mkdir();
		}
		if (StringUtil.isEmpty(dbSavePathStartFolder)) {
			dbSavePathStartFolder = "upload";
			savePath = this.getServletContext().getRealPath("/upload");
		}
		if (StringUtil.isEmpty(""//Configuration.getString("h5_upload.isDeleteTempFile")
				)) {
			isDeleteTempFile = true;//Configuration.getBoolean("h5_upload.isDeleteTempFile");
		}
		// 允许上传文件类型
		String fileType = "";//Configuration.getString("h5_upload.fileType");
		fileTypeSet.addAll(Arrays.asList(fileType.split("\\|")));

		Map result = new HashMap<>();
		Map resultData = new HashMap<>();
		// 返回
//		DataRow resultData = new DataRow();

		try {
			/**
			 * 使用Apache的commons-fileupload文件上传组件处理文件上传步骤：
			 */
			// 1、创建一个磁盘文件创建工厂，用户对每个上传的文件创建一个FileItem对象
			DiskFileItemFactory factory = new DiskFileItemFactory();

			factory.setRepository(tmpFile);// 设置上传时生成的临时文件的保存目录
			factory.setSizeThreshold(1024 * 100);// 设置缓冲区的大小为100KB，当上传的文件大小超过缓冲区的大小时，就会生成一个临时文件存放到指定的临时目录当中。如果不指定，那么缓冲区的大小默认是10KB

			// 2、用指定的工厂创建一个文件上传解析器
			ServletFileUpload upload = new ServletFileUpload(factory);

			// 监听文件上传进度，这个是在FileUploadBase抽象类中定义的方法
			upload.setProgressListener(new ProgressListener() {
				/**
				 * 
				 * @描述：更新侦听器状态信息。 @作者：严磊
				 * @时间：2019年11月13日 上午10:12:59
				 * @param pBytesRead     到目前为止已读取的总字节数
				 * @param pContentLength 正在读取的字节总数。如果该数字未知，则可能为-1
				 * @param pItems         当前正在读取的字段编号。（0 =到目前为止没有任何项目，1 =正在读取第一个项目，...）
				 */
				public void update(long pBytesRead, long pContentLength, int pItems) {
					// System.out.println("文件大小为：" + pContentLength + ",当前已处理：" + pBytesRead);
				}
			});

			upload.setFileSizeMax(fileSizeMax);// 设置上传单个文件的大小的最大字节数

			upload.setSizeMax(sizeMax);// 设置上传文件总量的最大字节数

			upload.setHeaderEncoding("UTF-8");// 解决上传文件名的中文乱码

			// 3、判断提交上来的数据是否是上传表单的数据
			if (!ServletFileUpload.isMultipartContent(request)) {
				// 请求的数据不是一个表单数据，在前端需要使用FormData对象模拟表单提交
				result.put("err_no", -1);
				result.put("err_info","请求的数据不是一个表单数据");
//				result.setErr_info();
				responseJSONData(response, result);
				return;
			}

			// 4、使用ServletFileUpload解析器解析上传数据，解析结果返回的是一个List<FileItem>集合，每一个FileItem对应一个Form表单的输入项
			List<FileItem> list = upload.parseRequest(request);
			for (FileItem item : list) {
				// 如果fileitem中封装的是普通输入项的数据
				if (item.isFormField()) {
					String name = item.getFieldName();
					String value = item.getString("UTF-8");
					// value = new String(value.getBytes("iso8859-1"),"UTF-8");
					logger.info(name + "=" + value);
				} else
				// 如果fileitem中封装的是上传文件
				{

					String filename = item.getName();
					logger.error("文件名：" + filename);
					if (filename == null || filename.trim().equals("")) {
						continue;
					}
					// 注意：不同的浏览器提交的文件名是不一样的，有些浏览器提交上来的文件名是带有路径的，如： c:\a\b\1.txt，而有些只是单纯的文件名，如：1.txt
					// 处理获取到的上传文件的文件名的路径部分，只保留文件名部分
					filename = filename.substring(filename.lastIndexOf("\\") + 1);

					// 得到上传文件的扩展名，限制上传的文件类型
					String fileExtName = "";
					if (filename.lastIndexOf(".") > -1) {
						fileExtName = filename.substring(filename.lastIndexOf(".") + 1);
					}

					logger.info("上传的文件的扩展名是：" + fileExtName);
					// 扩展名为空，或者扩展名不在配置文件里，则禁止上传
					if (StringUtil.isEmpty(fileExtName) || !fileTypeSet.contains(fileExtName.toLowerCase())) {
						logger.error("禁止上传fileExtName" + fileExtName + "类型的文件");
						// resultData.set(filename, "禁止上传fileExtName"+fileExtName+"类型的文件");
						continue;
					}

					// 获取item中的上传文件的输入流
					InputStream in = item.getInputStream();

					String realSavePath = makeFileSavePath(fileExtName, savePath);// 得到文件的存储目录

					FileOutputStream out = new FileOutputStream(realSavePath);

					byte buffer[] = new byte[1024];
					int len = 0;
					// 循环将输入流读入到缓冲字节数组
					while ((len = in.read(buffer)) > 0) {
						out.write(buffer, 0, len);
					}
					// 关闭输入流
					in.close();
					// 关闭输出流
					out.close();
					// 删除临时文件
					if (isDeleteTempFile) {
						item.delete();
					}
					resultData.put(filename,
							realSavePath.substring(realSavePath.indexOf(File.separator + dbSavePathStartFolder)));
				}
			}
		} catch (FileUploadBase.FileSizeLimitExceededException e) {
			result.put("err_no", -2);
			result.put("err_info","单个文件超出最大值！！！");
			responseJSONData(response, result);
			return;
		} catch (FileUploadBase.SizeLimitExceededException e) {
			result.put("err_no", -3);
			result.put("err_info","上传文件的总的大小超出限制的最大值！！！");
			responseJSONData(response, result);
			return;
		} catch (Exception e) {
			logger.error("【upload】文件上传失败", e);
			result.put("err_no", -43);
			result.put("err_info","文件上传失败");
			responseJSONData(response, result);
			return;
		}
		result.put("err_no", 0);
		result.put("err_info","success");
		result.put("result",resultData);
		responseJSONData(response, result);
	}

	public void responseJSONData(HttpServletResponse response, Map result) throws IOException {
		JSONObject json = new JSONObject();
		json.put("err_info", result.get("err_info"));
		json.put("err_no", result.get("err_no"));
		json.put("data", JSON.toJSON(result.get("data")));
		response.getWriter().print(json);
	}

	/**
	 * 
	 * @描述：创建文件保存路径
	 * @作者：严磊
	 * @时间：2019年11月13日 上午10:51:18
	 * @param fileExtName 文件扩展名
	 * @param savePath    文件存储路径
	 * @return 新的存储目录
	 */
	private String makeFileSavePath(String fileExtName, String savePath) {
		String fileName = UUID.randomUUID().toString().replace("-", "") + "." + fileExtName;
		String date = DateUtil.date2Str(new Date());
		// 构造新的保存目录
		String dir = savePath + File.separator + date;

		// File既可以代表文件也可以代表目录
		File file = new File(dir);
		// 如果目录不存在
		if (!file.exists()) {
			// 创建目录
			file.mkdirs();
		}
		return dir + File.separator + fileName;
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}