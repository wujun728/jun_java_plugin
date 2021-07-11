package com.luo.action;

import java.io.File;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletContext;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import com.luo.pojo.Message;
import com.luo.service.MessageService;
import com.luo.util.PropertiesUtil;

@Scope(value="prototype")
@Namespace("/message")
@Result(name="list",type="redirectAction",location="/list.action")
public class MessageAction extends BaseAction{

	private static final long serialVersionUID = 1L;
	@Autowired
	private MessageService messageService;
	private Message message;
	private List<Message> messageList;
	private File file;
	private String fileFileName;
	
	
	public Message getMessage() {
		return message;
	}

	public void setMessage(Message message) {
		this.message = message;
	}

	public List<Message> getMessageList() {
		return messageList;
	}

	public void setMessageList(List<Message> messageList) {
		this.messageList = messageList;
	}

	@Action(value="list",results={@Result(name=SUCCESS,location="/WEB-INF/content/messageList.jsp")})
	@Override
	public String list() throws Exception {
		messageList = messageService.list();
		return SUCCESS;
	}
	
	@Action(value="addMessage",results={@Result(name=SUCCESS,location="/WEB-INF/content/addMessage.jsp")})
	public String add() throws Exception {
		return SUCCESS;
	}
	
	@Action(value="saveMessage")
	public String save() throws Exception {
		message.setAddTime(new Date());
		String path = request.getRealPath("/upload");
		String fName=new Date().getTime()+fileFileName;
		if(file!=null){
			File f=new File(path,fName);
			FileUtils.copyFile(file, f);//不是
			message.setAttachUrl(f.getPath());
		}
		messageService.save(message);
		return LIST;
	}

	public String del() throws Exception {
		messageService.delete(message.getId());
		return LIST;
	}

	public String getFileFileName() {
		return fileFileName;
	}

	public void setFileFileName(String fileFileName) {
		this.fileFileName = fileFileName;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}
}
