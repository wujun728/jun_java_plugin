package com.jun.plugin.file.fileupload;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

public final class UploadUtil {
	// ȡ���ϴ�ʹ�õ���ʱ����ʵĿ¼
	public static final String tempPath = "/WEB-INF/temp";
	public static final String uploadPath = "/WEB-INF/upload";

	// ȡ����ʵ�ļ���
	public static String getRealFileName(String realFileName) {
		int index = realFileName.lastIndexOf("\\");
		if (index >= 0) {
			// IE6�����?
			realFileName = realFileName.substring(index + 1);
		}
		return realFileName;
	}

	// ȡ��uuid�ļ���
	public static String makeUuidFilePath(String uploadPath, String uuidFileName) {
		String uuidFilePath = null;
		int code = uuidFileName.hashCode();// 8
		int dir1 = code & 0xF;// 3
		int dir2 = code >> 4 & 0xF;// A
		File file = new File(uploadPath + "/" + dir1 + "/" + dir2);
		// ����Ŀ¼δ����
		if (!file.exists()) {
			// һ���Դ���N��Ŀ¼
			file.mkdirs();
		}
		uuidFilePath = file.getPath();
		return uuidFilePath;
	}

	// ȡ��upload/Ŀ¼�µķ�ɢĿ¼
	public static String makeUuidFileName(String realFileName) {
		return UUID.randomUUID().toString() + "_" + realFileName;
	}

	// �ļ�����
	public static void doSave(InputStream is, String uuidFileName,
			String uuidFilePath) {
		OutputStream os = null;
		try {
			os = new FileOutputStream(uuidFilePath + "/" + uuidFileName);
			byte[] buf = new byte[1024];
			int len = 0;
			while ((len = is.read(buf)) > 0) {
				os.write(buf, 0, len);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (os != null) {
				try {
					os.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
 
	// public static void doSave(User user, String uploadPath,List<Up> upList)
	// throws Exception {
	// for(FileItem fileItem : fileItemList){
	// //����Up����
	// Up up = new Up();
	// up.setUsername(user.getUsername());
	// //ȡ��������
	// InputStream is = fileItem.getInputStream();
	// //ȡ����ʵ�ļ���
	// String realFileName = fileItem.getName();
	// realFileName = UploadUtil.getRealFileName(realFileName);
	// //ȡ��UUID�ļ���
	// String uuidFileName = UploadUtil.makeUuidFileName(realFileName);
	// //ȡ��UUID�ļ�·��
	// String uuidFilePath =
	// UploadUtil.makeUuidFilePath(uploadPath,uuidFileName);
	// //����
	// UploadUtil.doSave(is,uuidFileName,uuidFilePath);
	// //�ռ�Up��Ϣ
	// up.setUuidFileName(uuidFileName);
	// up.setRealFileName(realFileName);
	// upList.add(up);
	// //ɾ����ʱ�ļ�
	// fileItem.delete();
	// }
	// }
}
