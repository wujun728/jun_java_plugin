package com.baomidou.springwind.controller;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Enumeration;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.framework.common.util.DateUtil;
import com.baomidou.framework.upload.UploadFile;
import com.baomidou.framework.upload.UploadMsg;
import com.baomidou.framework.upload.UploadMultipartRequest;
import com.baomidou.kisso.annotation.Action;
import com.baomidou.kisso.annotation.Permission;

/**
 * <p>
 * 上传服务
 * </p>
 * <p>
 * 头像上传控件：<br>
 * http://www.fullavatareditor.com/index.html
 * </p>
 * 
 * @author hubin
 * @Date 2016-04-13
 */
@Controller
@RequestMapping("/sys/upload")
public class UploadController extends BaseController {

	/* 限制最大上传 3M */
	private final static int MAX_POST_SIZE = 3 * 1024 * 1024;


	/**
	 * <p>
	 * 上传文件<br>  该演示 demo 上传后的文件保存在当前项目的根目录下
	 * </p>
	 */
	@ResponseBody
	@Permission(action = Action.Skip)
	@RequestMapping(value = "/file", method = RequestMethod.POST)
	public String file() {
		UploadMsg msg = new UploadMsg();
		try {
			UploadMultipartRequest umr = new UploadMultipartRequest(request, getSaveDir(), MAX_POST_SIZE);
			umr.setFileHeaderExts("ffd8ff.jpg");
			umr.upload();
			Enumeration<?> files = umr.getFileNames();
			while ( files.hasMoreElements() ) {
				String name = (String) files.nextElement();
				UploadFile cf = umr.getUploadFile(name);
				if ( cf != null ) {
					/**
					 * 上传成功
					 */
					if ( cf.isSuccess() ) {
						msg.setSuccess(true);
						msg.setUrl(cf.getFileUrl());
						msg.setSize(cf.getSize());
						System.err.println("上传文件地址：" + msg.getUrl());
					}
					msg.setMsg(cf.getUploadCode().desc());
				}
			}
		} catch ( IOException e ) {
			logger.error("uploadFile error. ", e);
		}
		return toJson(msg);
	}

	/**
	 * <p>
	 * 上传文件存放目录
	 * </p>
	 */
	private static String getSaveDir() {
		StringBuffer filePath = new StringBuffer(System.getProperty("user.dir"));
		filePath.append(File.separator);
		filePath.append(DateUtil.format(new Date(), "yyyy"));
		filePath.append(File.separator);
		File file = new File(filePath.toString());
		if ( !file.exists() ) {
			file.mkdirs();
		}
		return file.getPath();
	}

}
