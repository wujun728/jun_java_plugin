package org.springrain.ueditor;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HeaderElement;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springrain.frame.controller.BaseController;
import org.springrain.frame.util.FileUtils;
import org.springrain.frame.util.GlobalStatic;
import org.springrain.frame.util.HttpClientUtils;
import org.springrain.frame.util.JsonUtils;

@Controller
@RequestMapping("/ueditor")
public class UeditorController extends BaseController {

	private static final String UEFILEPATH = "/upload";

	private static final Map<String, String> CONTENT_TYPE_MAP = new HashMap<String, String>() {
		private static final long serialVersionUID = 1L;
		{
			put("image/gif", ".gif");
			put("image/jpeg", ".jpg");
			put("image/jpg", ".jpg");
			put("image/png", ".png");
			put("image/bmp", ".bmp");
			put("image/webp", ".webp");
		}
	};

	/**
	 * 主入口,处理所有的请求
	 * 
	 * @param requestfile
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/{siteId}/init")
	@ResponseBody
	public Object config(HttpServletRequest requestfile, HttpServletRequest request, Model model,
			@PathVariable String siteId) throws Exception {

		String action = request.getParameter("action");
		if (StringUtils.isBlank(action)) {
			return getResultMap(false);
		}

		// 项目名
		String path = request.getContextPath();

		// 文件的访问路径,不带项目名称,siteId可以是个变量,也可以是固定值,根据实际情况控制
		String fileuploadpath = UEFILEPATH + "/u/" + siteId + "/ueditor/";

		// 主配置,使用类直接处理,没有使用配置config.json文件,请根据实际要求处理
		UeditorConfig config = new UeditorConfig(path + fileuploadpath);

		Object obj = null;

		if ("config".equals(action)) {
			obj = config;
		} else if (UeditorConfig.ACTION_UPLOAD_IMAGE.equals(action)) {
			fileuploadpath = fileuploadpath + "image/";
			obj = upload(requestfile, fileuploadpath, config.getImageFieldName(), config.getImageAllowFiles(),
					config.getImageMaxSize());
		} else if (UeditorConfig.ACTION_UPLOAD_FILE.equals(action)) {
			fileuploadpath = fileuploadpath + "file/";
			obj = upload(requestfile, fileuploadpath, config.getFileFieldName(), config.getFileAllowFiles(),
					config.getFileMaxSize());
		} else if (UeditorConfig.ACTION_UPLOAD_VIDEO.equals(action)) {
			fileuploadpath = fileuploadpath + "video/";
			obj = upload(requestfile, fileuploadpath, config.getVideoFieldName(), config.getVideoAllowFiles(),
					config.getVideoMaxSize());
		} else if (UeditorConfig.ACTION_UPLOAD_SCRAWL.equals(action)) {
			fileuploadpath = fileuploadpath + "image/";
			obj = uploadScrawl(requestfile, fileuploadpath, config);
		} else if (UeditorConfig.ACTION_LISTFILE.equals(action)) {
			fileuploadpath = fileuploadpath + "file/";
			obj = listFile(requestfile, fileuploadpath, config);
		} else if (UeditorConfig.ACTION_LISTIMAGE.equals(action)) {
			fileuploadpath = fileuploadpath + "image/";
			obj = listFile(requestfile, fileuploadpath, config);
		} else if (UeditorConfig.ACTION_CATCHIMAGE.equals(action)) {
			fileuploadpath = fileuploadpath + "image/";
			obj = catchImage(requestfile, fileuploadpath, config);
		} else {
			return getResultMap(false);
		}

		String callbackName = request.getParameter("callback");

		if (StringUtils.isBlank(callbackName)) {
			return obj;
		}

		callbackName = URLEncoder.encode(callbackName, "UTF-8");

		if (!validCallbackName(callbackName)) {
			return obj;
		}

		return callbackName + "(" + JsonUtils.writeValueAsString(obj) + ");";
	}

	/**
	 * 上传图片和文件
	 * 
	 * @param request
	 * @param fileuploadpath
	 * @param fieldName
	 * @param allows
	 * @param maxSize
	 * @return
	 * @throws Exception
	 */
	private Map<String, Object> upload(HttpServletRequest request, String fileuploadpath, String fieldName,
			List<String> allows, Integer maxSize) throws Exception {

		MultipartHttpServletRequest requestfile = (MultipartHttpServletRequest) request;

		MultipartFile file = requestfile.getFile(fieldName);
		String originalName = file.getOriginalFilename();
		String suffix = FileUtils.getSuffix(originalName);
		if (StringUtils.isNotBlank(suffix)) {
			suffix = suffix.toLowerCase();
		}

		if (!allows.contains(suffix)) {
			return getResultMap(false);
		}

		long size = file.getSize();
		if (maxSize - size < 0) {
			return getResultMap(false);
		}

		String fileName = FileUtils.reSetFileName(suffix);
		// 保存到文件
		upload(file, GlobalStatic.rootDir + fileuploadpath + fileName);

		Map<String, Object> map = getResultMap(true);
		map.put("size", size);
		map.put("title", originalName);
		map.put("url", fileName);
		map.put("type", suffix);
		map.put("original", originalName);
		return map;
	}

	/**
	 * 列表展示文件或图片
	 * 
	 * @param request
	 * @param fileuploadpath
	 * @param config
	 * @return
	 * @throws Exception
	 */
	private Map<String, Object> listFile(HttpServletRequest request, String fileuploadpath, UeditorConfig config)
			throws Exception {

		String start_str = request.getParameter("start");

		if (StringUtils.isBlank(start_str)) {
			return getResultMap(false);
		}

		int index = Integer.valueOf(start_str);

		File dir = new File(GlobalStatic.rootDir + fileuploadpath);

		File[] list = dir.listFiles();

		if (list == null || list.length < 1) {
			return getResultMap(false);
		}

		Map<String, Object> resultMap = getResultMap(true);

		Integer count = config.getImageManagerListSize();

		if (index < 0 || index > list.length) {
			return resultMap;
		}

		Object[] fileList = Arrays.copyOfRange(list, index, index + count);
		if (fileList == null || fileList.length < 1) {
			return resultMap;
		}

		List<Map<String, String>> listUrl = new ArrayList<>(fileList.length);

		for (Object o : fileList) {
			if (o == null) {
				break;
			}
			File f = (File) o;
			Map<String, String> map = new HashMap<String, String>();
			map.put("url", f.getName());
			listUrl.add(map);
		}

		resultMap.put("list", listUrl);
		resultMap.put("start", index);
		resultMap.put("total", list.length);
		return resultMap;
	}

	/**
	 * 上传涂鸦图片
	 * 
	 * @param request
	 * @param fileuploadpath
	 * @param config
	 * @return
	 * @throws Exception
	 */
	private Map<String, Object> uploadScrawl(HttpServletRequest request, String fileuploadpath, UeditorConfig config)
			throws Exception {

		String fileStr = request.getParameter(config.getScrawlFieldName());

		if (StringUtils.isBlank(fileStr)) {
			return getResultMap(false);
		}
		String fileName = FileUtils.reSetFileName(UeditorConfig.SCRAWL_TYPE);
		File file = new File(GlobalStatic.rootDir + fileuploadpath + fileName);
		byte[] decodeBase64 = Base64.decodeBase64(fileStr);
		int length = decodeBase64.length;

		org.apache.commons.io.FileUtils.writeByteArrayToFile(file, decodeBase64);

		Map<String, Object> map = getResultMap(true);
		map.put("size", length);
		map.put("title", file.getName());
		map.put("url", fileName);
		map.put("type", UeditorConfig.SCRAWL_TYPE);
		map.put("original", "scrawl" + UeditorConfig.SCRAWL_TYPE);
		return map;
	}

	/**
	 * 下载远程的图片
	 * 
	 * @param request
	 * @param fileuploadpath
	 * @param config
	 * @return
	 * @throws Exception
	 */
	private Map<String, Object> catchImage(HttpServletRequest request, String fileuploadpath, UeditorConfig config)
			throws Exception {
		String[] fileNames = request.getParameterValues(config.getCatcherFieldName() + "[]");
		if (fileNames == null || fileNames.length < 1) {
			return getResultMap(false);
		}

		List<Map<String, Object>> list = new ArrayList<>();

		for (String fileName : fileNames) {

			CloseableHttpClient httpClient = HttpClientUtils.getHttpClient();
			HttpGet httpGet = new HttpGet(fileName);
			CloseableHttpResponse response = httpClient.execute(httpGet);
			HttpEntity entity = response.getEntity();

			try {
				if (entity == null) {
					continue;
				}

				long contentLength = entity.getContentLength();

				if (config.getCatcherMaxSize() - contentLength < 0) {
					return getResultMap(false);
				}

				HeaderElement[] headers = entity.getContentType().getElements();
				if (headers == null || headers.length < 1) {
					continue;
				}

				String suffixKey = entity.getContentType().getElements()[0].getName();
				String suffix = null;

				if (StringUtils.isNotBlank(suffixKey)) {
					suffix = CONTENT_TYPE_MAP.get(suffixKey.toLowerCase());
				}

				if (StringUtils.isNotBlank(suffix)) {
					suffix = suffix.toLowerCase();
				} else {
					suffix = UeditorConfig.SCRAWL_TYPE;
				}

				if (!config.getCatcherAllowFiles().contains(suffix)) {
					return getResultMap(false);
				}

				String fileImage = FileUtils.reSetFileName(suffix);
				// 保存到文件
				File saveFile = new File(GlobalStatic.rootDir + fileuploadpath + fileImage);
				org.apache.commons.io.FileUtils.copyInputStreamToFile(entity.getContent(), saveFile);

				Map<String, Object> map = getResultMap(true);
				map.put("size", contentLength);
				map.put("title", fileImage);
				map.put("url", fileImage);
				// map.put("original", fileImage);
				map.put("source", fileName);
				list.add(map);

			} finally {
				if (entity != null) {
					EntityUtils.consume(entity);
				}
				if (response != null) {
					response.close();
				}

				// 不要关闭httpClient,会造成连接池关闭!!!!

				// if(httpClient!=null){
				// httpClient.close();
				// }

			}

		}
		Map<String, Object> map = getResultMap(true);
		map.put("list", list);
		return map;

	}

	private Map<String, Object> getResultMap(boolean success) {
		if (success) {
			return getResultMap("SUCCESS");
		} else {
			return getResultMap("ERROR");
		}

	}

	private Map<String, Object> getResultMap(String message) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("state", message);
		return map;
	}

	/**
	 * callback参数验证
	 * 
	 * @param name
	 * @return
	 */
	private boolean validCallbackName(String name) {
		if (name.matches("^[a-zA-Z_]+[\\w0-9_]*$")) {
			return true;
		}
		return false;
	}

	/**
	 * 上传文件
	 * 
	 * @param file
	 * @param fileName
	 * @return
	 * @throws IllegalStateException
	 * @throws IOException
	 */
	private String upload(MultipartFile file, String fileName) throws IllegalStateException, IOException {
		File dest = new File(fileName);
		dest.getParentFile().mkdirs();
		file.transferTo(dest);
		return dest.getName();
	}

}
