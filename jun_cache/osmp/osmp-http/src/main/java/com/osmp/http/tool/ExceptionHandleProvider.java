/*   
 * Project: OSMP
 * FileName: ExceptionHandleProvider.java
 * version: V1.0
 */
package com.osmp.http.tool;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.SQLException;
import java.util.Date;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.InvalidDataAccessApiUsageException;

import com.osmp.http.define.ResponseBody;
import com.osmp.http.define.RtCodeConst;
import com.osmp.http.service.MessageManager;
import com.osmp.utils.base.GlobalUtils;

/**
 * 异常处理
 * @author heyu
 */
public class ExceptionHandleProvider {

	private Logger logger = LoggerFactory.getLogger(ExceptionHandleProvider.class);
	private String phoneDesc = "[java_web]%s,%s";
	private String mailDesc = "程序版本:java_web1.0<br>发生时间:%s<br>错误信息:%s<br>堆栈信息:%s";

	private MessageManager messageManager;

	public void setMessageManager(MessageManager messageManager) {
		this.messageManager = messageManager;
	}

	public Response toResponse(Throwable ex, String paramete) {
		String shortDesc = ex.getMessage() == null ? ex.toString() : ex.getMessage();
		String desc = paramete + "\n" + getStackTrace(ex);
		String title = "系统异常";
		String time = GlobalUtils.formartDateTime(new Date());
		if (ex instanceof SQLException) {
			title = "数据库异常";
			if (messageManager != null) {
				messageManager.warnSMS(String.format(phoneDesc, title, shortDesc.substring(0, 40)));
			}
		}
		if (messageManager != null) {
			if (!(ex instanceof InvalidDataAccessApiUsageException)) {
				messageManager.warnMail("[java_web]" + title, String.format(mailDesc, time, title, desc));
			}
		}
		logger.error(desc);
		ResponseBody resBody = new ResponseBody();
		resBody.setCode(RtCodeConst.ERR_CODE);
		resBody.setMessage(shortDesc);
		ResponseBuilder rb = Response.status(Response.Status.OK);
		rb.type("application/json;charset=UTF-8");
		rb.entity(resBody);
		return rb.build();
	}

	private String getStackTrace(Throwable t) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);

		try {
			t.printStackTrace(pw);
			return sw.toString();
		} finally {
			pw.close();
		}
	}

}
