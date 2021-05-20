package com.techsoft.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.techsoft.DataConverter;
import com.techsoft.ServletModule;
import com.techsoft.Session;
import com.techsoft.StringConsts;
import com.techsoft.container.DataServer;
import com.techsoft.dataconverter.DataConverterFactory;

public class DataService extends HttpServlet {
	private static final Logger Log = LoggerFactory
			.getLogger(DataService.class);
	private static final long serialVersionUID = 6929000280719060477L;

	private void readParamsFromParameter(HttpServletRequest request,
			Map<String, Object> map) {
		// url后的参数
		String paramName = null;
		String[] paramValues = null;
		Object paramValue = null;
		JSONObject obj = null;
		Iterator<String> iter = null;
		String key = null;
		Enumeration<String> paramNames = request.getParameterNames();
		while (paramNames.hasMoreElements()) {
			paramName = paramNames.nextElement();
			paramValues = request.getParameterValues(paramName);
			if (paramValues.length >= 1) {
				paramValue = paramValues[0];
			}

			if (paramName.equalsIgnoreCase(StringConsts.allparams)) {
				obj = JSONObject.parseObject((String) paramValue);
				iter = obj.keySet().iterator();
				while (iter.hasNext()) {
					key = iter.next();
					map.put(key.toLowerCase(), obj.get(key));
				}
			} else {
				map.put(paramName.toLowerCase(), paramValue);
			}
		}
	}

	private void readParamsFromAttribute(HttpServletRequest request,
			Map<String, Object> map) {
		// Sesionn上下文参数
		Enumeration<String> names = request.getAttributeNames();
		String name = null;
		while (names.hasMoreElements()) {
			name = names.nextElement();
			if (name.equals("g_s")) {
				// 表示数据格式的全局参数
				map.put("s", request.getAttribute(name));
			} else if (name.startsWith("g_")) {
				map.put(name, request.getAttribute(name));
				request.removeAttribute(name);
			}

		}
	}

	@SuppressWarnings("unchecked")
	private List<FileItem> readParamsFromInputStream(
			HttpServletRequest request, Map<String, Object> map)
			throws IOException {
		List<FileItem> result = null;
		JSONObject obj = null;
		String key = null;
		Iterator<String> iter = null;
		if (map.get("m") == null) {
			DiskFileItemFactory factory = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(factory);
			// 使用开源库读取POST分段参数
			if (ServletFileUpload.isMultipartContent(request)) {
				upload.setHeaderEncoding(System
						.getProperty(StringConsts.jvmcharset));
				try {
					List<FileItem> list = upload.parseRequest(request);
					for (FileItem item : list) {

						String fieldName = item.getFieldName();
						if (StringConsts.allparams.equalsIgnoreCase(fieldName)) {
							obj = JSONObject.parseObject(item.getString());
							iter = obj.keySet().iterator();
							while (iter.hasNext()) {
								key = iter.next();
								map.put(key, obj.get(key));
							}
						}
					}
					result = list;
				} catch (FileUploadException e) {
					throw new IOException(e.getMessage());
				}
			}

		}

		if (map.get("m") == null) {
			InputStream input = request.getInputStream();
			try {
				BufferedReader tBufferedReader = new BufferedReader(
						new InputStreamReader(input));
				StringBuffer tStringBuffer = new StringBuffer();
				String sTempOneLine = "";
				try {
					while ((sTempOneLine = tBufferedReader.readLine()) != null) {
						tStringBuffer.append(sTempOneLine);
					}

					String s = URLDecoder.decode(tStringBuffer.toString(),
							System.getProperty(StringConsts.jvmcharset));
					if (s.length() > 10) {
						s = s.substring(10);
						obj = JSONObject.parseObject(s);
						iter = obj.keySet().iterator();
						while (iter.hasNext()) {
							key = iter.next();
							map.put(key, obj.get(key));
						}
					}
				} finally {
					tBufferedReader.close();
				}
			} finally {
				input.close();
			}
		}

		return result;
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> results = new HashMap<String, Object>();
		DataConverter dataConverter = null;
		OutputStream out = response.getOutputStream();
		InputStream input = request.getInputStream();
		try {
			try {
				readParamsFromAttribute(request, map);
				readParamsFromParameter(request, map);
				List<FileItem> list = readParamsFromInputStream(request, map);

				String m = String.valueOf(map.get(StringConsts.MethodName));
				String s = String.valueOf(map.get(StringConsts.Serization));
				if ((m == null) || (m.equalsIgnoreCase(""))) {
					throw new Exception("请求的方法名称为空，请客户端检查协议!");
				}
				if ((s == null) || (s.equalsIgnoreCase(""))) {
					throw new Exception("请求的数据格式为空， 请客户端指定数据格式!");
				} else {
					if (!DataConverterFactory.containDataFormat(s)) {
						throw new Exception("客户端传入的数据格式 [" + s + "]，服务器还不支持！");
					}
				}

				// 得到输出格式对象
				if (map.get(ServletModule.JSONP) != null) {
					dataConverter = DataConverterFactory
							.getSerialization(StringConsts.jsonp);
					dataConverter.setPrefix(String.valueOf(map
							.get(ServletModule.JSONP)));
				} else {
					dataConverter = DataConverterFactory
							.getSerialization((String) map
									.get(StringConsts.Serization));
				}
				Log.info("Servlet IP: " + map.get("ip"));
				map.put(Session.sessionid, request.getSession().getId());

				m = String.valueOf(map.get(StringConsts.MethodName))
						.toLowerCase();

				Object obj = DataServer.getInstance().getCoreHandlers().get(m);
				if (obj != null) {
					ServletModule handler = (ServletModule) obj;
					if (list != null) {
						handler.process(map, true, null, out, list, results,
								dataConverter);
					} else {
						handler.process(map, false, input, out, null, results,
								dataConverter);
					}
				} else {
					obj = DataServer.getInstance().getPluginModuleByMethod(m);
					if (obj != null) {
						ServletModule handler = (ServletModule) obj;
						if (list != null) {
							handler.process(map, true, null, out, list,
									results, dataConverter);
						} else {
							handler.process(map, false, input, out, null,
									results, dataConverter);
						}
					} else {
						throw new Exception("服务器还不支持此方法，请联系开发人员或等待插件完全安装!");
					}
				}
				results.put(StringConsts.ResultType, StringConsts.Success);
				results.put(StringConsts.ResultDesc, "");
			} catch (Exception e) {
				results.put(StringConsts.ResultType, StringConsts.Error);
				results.put(StringConsts.ResultDesc, e.getClass().getName()
						+ " \n" + e.getMessage());
			}

			// 输出结果
			if (dataConverter != null) {
				if (results.get("result") == null) {
					// 如果不为空， 表示内部已处理了输出流
					Object resultobj = dataConverter.serializeObject(results);
					dataConverter.writeToOutputStream(resultobj, out);
				}
			}
		} finally {
			out.close();
			input.close();
		}
	}
}
