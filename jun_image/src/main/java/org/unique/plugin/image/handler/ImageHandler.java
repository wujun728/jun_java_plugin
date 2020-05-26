package org.unique.plugin.image.handler;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.unique.plugin.image.Const;
import org.unique.plugin.image.exec.ThumbExec;
import org.unique.plugin.image.util.BigDecimalUtil;
import org.unique.plugin.image.util.FileUtil;
import org.unique.plugin.image.util.ImageUtil;
import org.unique.plugin.image.util.JSONUtil;
import org.unique.plugin.image.util.QueryUtil;

/**
 * 图片处理器
 * 
 * @author rex
 */
public class ImageHandler {

	private Logger loggr = Logger.getLogger(ImageHandler.class);

	private Map<String, String> params = null;
	private String filePath;
	private HttpServletRequest request;
	private HttpServletResponse response;
	
	public void handler(final String target, final HttpServletRequest request, final HttpServletResponse response) {
		this.request = request;
		this.response = response;
		
		// 请求文件物理路径
		this.filePath = request.getServletContext().getRealPath(target);
		// 请求参数
		String[] querys = request.getQueryString().split("/");

		params = QueryUtil.query2Map(querys);

		if (params.isEmpty() || !FileUtil.exists(filePath)) {
			return;
		}
		// 既有按大小又有按比例 直接返回 不处理
		if (null != params.get("s") && null != params.get("p")) {
			return;
		}
		//执行图片处理
		run();
	}

	/**
	 * 执行器
	 * 
	 * @author Rex
	 * @param <T>
	 */
	abstract class Executor<T> {

		/*
		 * 回调
		 */
		abstract T execute();

		/*
		 * 执行结果
		 */
		public T getResult() {
			return execute();
		}
	}
	
	/**
	 * 执行
	 */
	public void run(){
		try {
			loggr.info("filePath:" + filePath);
			String field = params.get(Const.MAP_FIELD);
			// 缩略图路径
			String thumbPath = getThumbPath();
			// 缩略图显示
			if (field.equalsIgnoreCase(Const.VIEW_FIELD)) {
				viewImage(thumbPath);
			}
			// 缩略图下载
			if (field.equalsIgnoreCase(Const.DOWN_FIELD)) {
				downImage(thumbPath);
			}
			// 获取图片信息
			if (field.equalsIgnoreCase(Const.INFO_FIELD)) {
				imageInfo(thumbPath);
			}
		} catch (NumberFormatException e) {
			loggr.warn(e.getMessage());
		}
	}
	
	public String getThumbPath() {
		return new Executor<String>() {
			@Override
			String execute() {
				int width = 0, height = 0;

				double quality = 0.0D, scale = 0.0D, rotate = 0.0D;

				String thumbPath = filePath;

				// 图片质量
				if (null != params.get("q")) {
					quality = Double.valueOf(params.get("q"));
				}
				// 旋转角度
				if (null != params.get("r")) {
					rotate = Double.valueOf(params.get("r"));
				}
				// 是否保持图片比例
				boolean isforce = true;
				// 按大小
				if (null != params.get("s")) {
					String[] size = null;
					// 按照width缩放
					if (params.get("s").startsWith("w")) {
						size = new String[] { params.get("s").substring(1), "0" };
					}
					// 按照height缩放
					if (params.get("s").startsWith("h")) {
						size = new String[] { "0", params.get("s").substring(1) };
					}
					// 按照widthxheight缩放
					if (params.get("s").indexOf("x") != -1) {
						// 是否保持图片比例
						if (params.get("s").endsWith("!")) {
							size = params.get("s")
									.substring(0, params.get("s").length() - 2)
									.split("x");
							isforce = false;
						} else {
							size = params.get("s").split("x");
						}
					}
					width = Integer.valueOf(size[0]);
					height = Integer.valueOf(size[1]);
					thumbPath = FileUtil.getImgPath(filePath, width, height,
							isforce, quality, scale, rotate);
					thumbPath = ThumbExec.thumb(filePath, thumbPath, width,
							height, isforce, scale, quality, rotate);
				}

				// 按比例
				if (null != params.get("p")) {
					if (params.get("p").endsWith("!")) {
						isforce = false;
					}
					scale = BigDecimalUtil.divide(new BigDecimal(params.get("p")), new BigDecimal("100"), 10).doubleValue();
					thumbPath = FileUtil.getImgPath(filePath, width, height, isforce, quality, scale, rotate);
					thumbPath = ThumbExec.thumb(filePath, thumbPath, width, height, isforce, scale, quality, rotate);
				}
				return thumbPath;
			}
		}.getResult();
	}

	/**
	 * 显示图片 response
	 * 
	 * @param path
	 * @param request
	 * @param response
	 * @return
	 */
	private void viewImage(String path) {
		try {
			File file = new File(path);
			String fileName = file.getName();
			// 得到图片的文件流
			InputStream fis = new BufferedInputStream(new FileInputStream(file));

			byte[] buffer = new byte[fis.available()];
			fis.read(buffer);
			fis.close();
			response.reset();
			// 设置response的Header
			response.addHeader("Content-Length", file.length()+"");
			OutputStream out = new BufferedOutputStream(response.getOutputStream());
			response.setContentType("image/" + FileUtil.getSuffix(fileName) + "; charset=utf-8");

			out.write(buffer);
			out.flush();
			out.close();
		} catch (IOException ex) {
		}
	}

	/**
	 * 下载图片response
	 * 
	 * @param path
	 * @param request
	 * @param response
	 * @return
	 */
	private void downImage(String path) {
		try {
			File file = new File(path);
			String fileName = file.getName();
			// 得到图片的文件流
			InputStream fis = new BufferedInputStream(new FileInputStream(file));

			byte[] buffer = new byte[fis.available()];
			fis.read(buffer);
			fis.close();

			response.reset();
			// 设置response的Header
			response.addHeader("Content-Length", "" + file.length());
			response.addHeader("Content-Disposition", "attachment;filename="
					+ new String(fileName.getBytes("utf-8"), "iso-8859-1"));
			OutputStream out = new BufferedOutputStream(
					response.getOutputStream());
			response.setContentType("application/octet-stream");

			out.write(buffer);
			out.flush();
			out.close();
		} catch (IOException ex) {
		}
	}

	/**
	 * 获取图片信息response
	 * 
	 * @param path
	 * @param request
	 * @param response
	 * @return
	 */
	private HttpServletResponse imageInfo(final String path) {
		try {
			File file = new File(path);
			Map<String, String> result = new HashMap<String, String>();
			if (!file.exists()) {
				result.put(Const.ERROR_CODE, Const.ERROR_001);
			} else {
				result.put("key", file.getName());
				result.put("size", file.length() + "");
				Map<String, Integer> wh = ImageUtil.getImageWH(file);
				result.put("width", wh.get("width") + "");
				result.put("height", wh.get("height") + "");
			}
			response.reset();
			OutputStream out = new BufferedOutputStream(
					response.getOutputStream());
			String userAgent = request.getHeader("User-Agent");
			// 处理IE返回json跳出下载框
			if (userAgent.contains("MSIE")) {
				response.setContentType("text/html;charset=utf-8");
			} else {
				response.setContentType("application/json;charset=utf-8");
			}

			out.write(JSONUtil.map2Json(result).getBytes());
			out.flush();
			out.close();
		} catch (IOException ex) {
		}
		return response;
	}

	public static ImageHandler single() {
		return ImageHandlerHolder.single;
	}

	private ImageHandler() {
	}

	private static class ImageHandlerHolder {
		private static ImageHandler single = new ImageHandler();
	}
}
