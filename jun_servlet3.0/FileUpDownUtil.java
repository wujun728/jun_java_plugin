package com.dcf.util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;

import com.dcf.system.DataSourceUtils;
import com.eos.foundation.database.DatabaseExt;
import com.eos.runtime.core.ApplicationContext;
import com.eos.spring.BeanFactory;

/**
 * 
 * 文件操作工具类
 * 
 */
public class FileUpDownUtil {

	private static final String FILE_TEMP_DIR = "File_Temp_Dir";

	private static final String FILE_UPLOAD_DIR = "File_Upload_Dir";

	private static List<String> _allowedFileExtensionList = new ArrayList<String>();

	/**
	 * 获取文件全路径
	 * 
	 * @param fileFullName
	 * @return
	 */
	private String getFileAbsPath() {
		String warRealPath = ApplicationContext.getInstance().getWarRealPath();
		String _tempFileUploadFolderPath = warRealPath + File.separator + PropertiesUtil.getPropertyValue(FILE_UPLOAD_DIR);
		return _tempFileUploadFolderPath;
	}
	private String getFileName() {
		String rootDir = PropertiesUtil.getPropertyValue(FILE_UPLOAD_DIR);// TODO fix bug
		//String rootDir = "/Users/wanderer/tmpFileUpload";
		int year = Calendar.getInstance().get(Calendar.YEAR);
		int month = Calendar.getInstance().get(Calendar.MONTH) + 1;// 月份是从0开始所以要加1
		int day = Calendar.getInstance().get(Calendar.DATE);
		
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String str = sdf.format(date);
		String fileAbsPath = str;
//		String fileAbsPath = rootDir + File.separator + year + File.separator + month + File.separator + day;
		return fileAbsPath;
	}

	/**
	 * 如果路径不存在，则创建文件夹
	 * 
	 * @param path
	 */
	public static void createFolderIfNotExists(String path) {
		File file = new File(path);
		if ((!file.isDirectory()) || (!file.exists())) {
			file.mkdirs();
		}
	}

	public List<Map<String, String>> upload(HttpServletRequest request) {
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		long maxFileSize = 10 * 1024 * 1024;
		byte[] byteArray = new byte[0];
		if (ServletFileUpload.isMultipartContent(request)) {
			ServletFileUpload sfu = initServletFileUpload();
			String suffix = null;
			List<?> items = null;
			try {
				items = sfu.parseRequest(request);
			} catch (FileUploadException e) {
				e.printStackTrace();
			}
			Iterator<?> it = items.iterator();
			Map<String, String> fieldMap = new HashMap<String, String>();
			String fileName = "";
			String fileFullPath ="";
			while (it.hasNext()) {
				FileItem fileItem = (FileItem) it.next();
				if (fileItem.isFormField()) {
					String fieldName = fileItem.getFieldName();
					try {
						fieldMap.put(fieldName, fileItem.getString("UTF-8"));
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
				} else {
					fileName = fileItem.getName();
					try {
						byteArray = IOUtils.toByteArray(fileItem.getInputStream());
					} catch (IOException e) {
						e.printStackTrace();
					}
					suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
				}
			}
			if (byteArray.length > maxFileSize) {
				// response.getWriter().write("<script>parent.callback('" + "上传的文件大于10M！" + "')</script>");
				// TODO:
				return null;
			}
			
			String bizObjId = fieldMap.get("bizObjId");
			String bizObjType = fieldMap.get("bizObjType");

			BeanFactory beanFactory = BeanFactory.newInstance();
//			IBidAttachmentService attachmentService = beanFactory.getBean("BidAttachmentBean");
			if (byteArray != null && byteArray.length > 0) {
//				BidAttachment attach = BidAttachment.FACTORY.create();
				/*attach.setPertainTo(bizObjId);
				attach.setPertainToType(bizObjType);
				attach.setAttachmentName(fileName);
				attach.setAttachmentExt(suffix);
				attach.setAttachmentContent(null);
				attach.setAttachmentSize(new BigDecimal(byteArray.length / 1024));
				attach.setAttachmentDesc("");
				attach.setAttachmentVer(new BigDecimal(0));
				attach.setAbsolutePath("");
				attach.setRelativePath("");
				attach.setDeletedFlag(BizObjectConsts.DELETED_FLAG_NO);
				attach.setCreatorId(UserUtil.getCurrentUserId());
				attach.setCreatedTime(new Date());
				attach.setLastModifierId(UserUtil.getCurrentUserId());
				attach.setLastModifiedTime(new Date());
				attachmentService.addBidAttachment(attach);*/

				OutputStream os = null;
				try {
					File fileFullDir = new File(this.getFileAbsPath());
					if (!fileFullDir.exists()) {
						fileFullDir.mkdirs();
					}
					String FILE_NEW_NAME =this.getFileName();
					String fileFullName = fileFullDir.getAbsoluteFile() + File.separator + FILE_NEW_NAME + "." + suffix;
//					String fileFullName = fileFullDir.getAbsoluteFile() + File.separator + attach.getId() + "." + suffix;
					fileFullPath = fileFullName;
//					attach.setAbsolutePath(fileFullPath);
//					attachmentService.updateBidAttachment(attach);
					os = new BufferedOutputStream(new FileOutputStream(new File(fileFullName)));
					Map map=new HashMap();
					map.put("FILE_ID", DcfUtil.getPrimaryKey("AT_FILEUPLOAD.FILE_ID"));
					map.put("FILE_NAME", fileName);
					map.put("FILE_SIZE", byteArray.length/1000);
					map.put("FILE_CATALOG", "");
					map.put("FILE_TIME",""+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
					map.put("FILE_NEW_NAME", FILE_NEW_NAME+"."+suffix);
					map.put("FILE_PATH", PropertiesUtil.getPropertyValue(FILE_UPLOAD_DIR));
					map.put("ABSOLUTEPATH", fileFullName);
					map.put("FILE_TYPE", suffix);
					map.put("FILE_SAVE", PropertiesUtil.getPropertyValue(FILE_UPLOAD_DIR)+"/" + FILE_NEW_NAME + "." + suffix);
					map.put("RELATION_ID", bizObjId);
					map.put("GROUP_ID", bizObjId);
					DataSourceUtils.fileUploadInsertInfo(map);
					os.write(byteArray);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}catch (Exception e) {
					e.printStackTrace();
				} finally {
					if (os != null) {
						try {
							os.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			}

			Map<String, String> map = new HashMap<String, String>();
			map.put("fileName", fileName);
			map.put("bizObjId", bizObjId);
			map.put("bizObjType", bizObjType);
			list.add(map);
		}
		return list;

	}
	
	public List<Map<String, String>> uploadBidMonthlyBankApplyAttach(HttpServletRequest request) {
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		long maxFileSize = 10 * 1024 * 1024;
		byte[] byteArray = new byte[0];
		if (ServletFileUpload.isMultipartContent(request)) {
			ServletFileUpload sfu = initServletFileUpload();
			String suffix = null;
			List<?> items = null;
			try {
				items = sfu.parseRequest(request);
			} catch (FileUploadException e) {
				e.printStackTrace();
			}
			Iterator<?> it = items.iterator();
			Map<String, String> fieldMap = new HashMap<String, String>();
			String fileName = "";
			String fileFullName ="";
			while (it.hasNext()) {
				FileItem fileItem = (FileItem) it.next();
				if (fileItem.isFormField()) {
					String fieldName = fileItem.getFieldName();
					try {
						fieldMap.put(fieldName, fileItem.getString("UTF-8"));
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
				} else {
					fileName = fileItem.getName();
					try {
						byteArray = IOUtils.toByteArray(fileItem.getInputStream());
					} catch (IOException e) {
						e.printStackTrace();
					}
					suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
				}
			}
			if (byteArray.length > maxFileSize) {
				// response.getWriter().write("<script>parent.callback('" + "上传的文件大于10M！" + "')</script>");
				// TODO:
				return null;
			}
			
			String bizObjId = fieldMap.get("bizObjId");
			String bizObjType = fieldMap.get("bizObjType");

			BeanFactory beanFactory = BeanFactory.newInstance();
			/*IBidAttachmentService attachmentService = beanFactory.getBean("BidAttachmentBean");
			if (byteArray != null && byteArray.length > 0) {
				BidAttachment attach = BidAttachment.FACTORY.create();
				attach.setPertainTo(bizObjId);
				attach.setPertainToType(bizObjType);
				attach.setAttachmentName(fileName);
				attach.setAttachmentExt(suffix);
				attach.setAttachmentContent(null);
				attach.setAttachmentSize(new BigDecimal(byteArray.length / 1024));
				attach.setAttachmentDesc("");
				attach.setAttachmentVer(new BigDecimal(0));
				attach.setAbsolutePath("");
				attach.setRelativePath("");
				attach.setDeletedFlag(BizObjectConsts.DELETED_FLAG_NO);
				attach.setCreatorId(UserUtil.getCurrentUserId());
				attach.setCreatedTime(new Date());
				attach.setLastModifierId(UserUtil.getCurrentUserId());
				attach.setLastModifiedTime(new Date());
				attachmentService.addBidAttachment(attach);

				OutputStream os = null;
				try {

					File fileFullDir = new File(this.getFileAbsPath());
					if (!fileFullDir.exists()) {
						fileFullDir.mkdirs();
					}
					fileFullName = fileFullDir.getAbsoluteFile() + File.separator + attach.getId() + "." + suffix;
					os = new BufferedOutputStream(new FileOutputStream(new File(fileFullName)));
					os.write(byteArray);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					if (os != null) {
						try {
							os.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			}*/

			Map<String, String> map = new HashMap<String, String>();
			map.put("fileName", fileName);
			map.put("bizObjId", bizObjId);
			map.put("bizObjType", bizObjType);
			map.put("fileFullName", fileFullName);
			list.add(map);
		}
		return list;

	}
	private static ServletFileUpload initServletFileUpload() {
		String tempPath = getTempFileUploadFolderPath();
		createFolderIfNotExists(tempPath);
		createFolderIfNotExists(getUploadFileUploadFolderPath());
		DiskFileItemFactory fileItemFactory = new DiskFileItemFactory();
		// 最大缓存
		fileItemFactory.setSizeThreshold(100 * 1024);
		// 设置临时文件目录
		fileItemFactory.setRepository(new File(tempPath));
		ServletFileUpload upload = new ServletFileUpload(fileItemFactory);
		upload.setHeaderEncoding("UTF-8");
		return upload;
	}

	private static String getTempFileUploadFolderPath() {
		String warRealPath = ApplicationContext.getInstance().getWarRealPath();
		String _tempFileUploadFolderPath = warRealPath + File.separator + PropertiesUtil.getPropertyValue(FILE_TEMP_DIR);
		return _tempFileUploadFolderPath;
	}
	private static String getUploadFileUploadFolderPath() {
		String warRealPath = ApplicationContext.getInstance().getWarRealPath();
		String _tempFileUploadFolderPath = warRealPath + File.separator + PropertiesUtil.getPropertyValue(FILE_UPLOAD_DIR);
		return _tempFileUploadFolderPath;
	}

}
