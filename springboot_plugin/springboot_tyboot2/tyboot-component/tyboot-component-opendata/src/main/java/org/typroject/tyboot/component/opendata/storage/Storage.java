package org.typroject.tyboot.component.opendata.storage;

import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.FileInfo;
import com.qiniu.storage.model.FileListing;
import com.qiniu.util.Auth;
import org.typroject.tyboot.component.opendata.storage.constant.QiNiuConstants;
import org.typroject.tyboot.component.opendata.storage.model.QiniuResourceModel;
import org.typroject.tyboot.core.foundation.utils.ValidationUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Storage {

	private String qiniuConfigAccessKey;
	
	private String qiniuConfigSecretKey;

	private Auth auth;

	private BucketManager bucketManager;

	private UploadManager uploadManager;



	public Storage(String qiniuConfigAccessKey,String qiniuConfigSecretKey)
	{
		this.qiniuConfigAccessKey = qiniuConfigAccessKey;
		this.qiniuConfigSecretKey = qiniuConfigSecretKey;
	}



	private synchronized Auth getAuth() {
		if (this.auth == null) {
			this.auth = Auth.create(qiniuConfigAccessKey, qiniuConfigSecretKey);
		}
		return this.auth;
	}

	private synchronized BucketManager getBucketManager() {
		if (this.bucketManager == null) {
			this.bucketManager = new BucketManager(this.getAuth());
		}
		return this.bucketManager;
	}



	private synchronized UploadManager getUploadManager() {
		if (this.uploadManager == null) {
			this.uploadManager = new UploadManager();
		}
		return this.uploadManager;
	}

	/**
	 * @param spaceName
	 * @return 上传凭证
	 * @throws Exception
	 */
	private String getUpToken(String spaceName) throws Exception {
		//@TODO 缓存七牛TOKEN
		String currentToken = null;
		if (ValidationUtil.isEmpty(currentToken)) {
			currentToken = this.getAuth().uploadToken(spaceName);
		}
		return currentToken;
	}

	/**
	 * 获取所有的空间名列表
	 * 
	 * @return
	 * @throws QiniuException
	 */
	public String[] getSapceNameList() throws QiniuException {
		String[] buckets = this.getBucketManager().buckets();
		return buckets;
	}

	/**
	 * 获取空间下的文件属性
	 * 
	 * @param spaceName
	 * @param fileName
	 * @return
	 * @throws
	 * @throws QiniuException
	 */
	public boolean getFileInfo(String spaceName, String fileName) throws Exception {
		if (ValidationUtil.isEmpty(spaceName)) {
			throw new Exception(QiNiuConstants.SAPCE_NAME_ERROR);
		}
		FileInfo info;
		try {
			info = this.getBucketManager().stat(spaceName, fileName);
			if (!ValidationUtil.isEmpty(info.fsize)) {
				return true;
			}
		} catch (QiniuException e) {
			return false;
		}
		return false;
	}

	/**
	 * 删除指定空间下的文件
	 * 
	 * @param spaceName
	 * @param fileName
	 * @throws QiniuException
	 */
	public void deleteFile(String spaceName, String fileName) throws QiniuException {
		this.getBucketManager().delete(spaceName, fileName);
	}

	/**
	 * @param filePath
	 * @param fileName
	 * @param spaceName
	 * @return
	 * @throws Exception
	 */
	public Response upload(String filePath, String fileName, String spaceName) throws Exception {
		Response response = null;
		try {
			response = this.getUploadManager().put(filePath, fileName, getUpToken(spaceName));
			if (response.statusCode != 200) {
				throw new Exception(response.error);
			}
			return response;
		} catch (QiniuException e) {
			response = e.response;
			throw new Exception(response.error);
		}
	}

	/**
	 * @param file
	 * @param fileName
	 * @param spaceName
	 * @return
	 * @throws Exception
	 */
	public Response upload(File file, String fileName, String spaceName) throws Exception {
		Response response = null;
		try {
			response = this.getUploadManager().put(file, fileName, getUpToken(spaceName));
			if (response.statusCode != 200) {
				throw new Exception(response.error);
			}
			return response;
		} catch (QiniuException e) {
			response = e.response;
			throw new Exception(response.error);
		}
	}

	/**
	 * @param file
	 * @param fileName
	 * @param spaceName
	 * @return
	 * @throws Exception
	 */
	public Response upload(byte[] file, String fileName, String spaceName) throws Exception {

		Response response = null;
		try {
			response = this.getUploadManager().put(file, fileName, getUpToken(spaceName));
			if (response.statusCode != 200) {
				throw new Exception(response.error);
			}
			return response;
		} catch (QiniuException e) {
			response = e.response;
			throw new Exception(response.error);
		}
	}

	public String flushQiniuToken(String spaceName) throws Exception {
		return this.getUpToken(spaceName);
	}

	public List<QiniuResourceModel> getResourceList(String spaceName, String dir) throws Exception {
		FileListing filelisting = this.getBucketManager().listFiles(spaceName, dir, null,
				QiNiuConstants.QN_GET_FILES_MAX_LIMIT, null);
		List<FileInfo> fileList = Arrays.asList(filelisting.items);
		List<QiniuResourceModel> result = new ArrayList<QiniuResourceModel>();
		QiniuResourceModel currentFile = null;
		for (FileInfo file : fileList) {
			currentFile = new QiniuResourceModel();
			currentFile.setKey(file.key.substring(dir.length() + 1));
			currentFile.setFsize(file.fsize);
			currentFile.setRealFileName(file.key);
			currentFile.setPutTime(file.putTime);
			currentFile.setFileName(file.key);
			result.add(currentFile);
		}
		return result;
	}
}
