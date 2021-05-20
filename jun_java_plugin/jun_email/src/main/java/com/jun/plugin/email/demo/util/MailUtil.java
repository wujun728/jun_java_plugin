package com.jun.plugin.email.demo.util;

import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import com.jun.plugin.email.demo.dto.MimeMessageDTO;

/**
 * 类名称:  mailUtil
 * 功能描述: TODO 邮件发送例子
 * 创建人:  Gavin-Nie 
 * 创建时间: 2014-12-4 上午9:20:16 
 * @version  V1.0  
 */
public class MailUtil {
	
	/**   
	 * 变量名 userName: TODO 邮箱用户名
	 */   
	private String userName;
	
	/**   
	 * 变量名 password: TODO 邮箱地址
	 */   
	private String password;
	
	/**   
	 * 变量名 smtpHost: TODO 邮箱smtp地址，发送地址
	 */   
	private String smtpHost;
	
	/**   
	 * 变量名 targetAddress: TODO 目标邮箱地址
	 */   
	private String targetAddress;
	
	/**
	 *  发送单邮件
	 * @param userName
	 * @param password
	 * @param targetAddress
	 * @param mimeDTO
	 * @return
	 */
	public  static boolean sendEmail(String userName,String password,String targetAddress,
			MimeMessageDTO mimeDTO){
		return publicsendEmail(userName,password,targetAddress,mimeDTO,false,null);
	}
	/**
	 * 发送单邮件(附件)
	 * @param userName
	 * @param password
	 * @param targetAddress
	 * @param mimeDTO
	 * @param filepath        文件本地绝对路径
	 * @return
	 */
	public  static boolean sendEmailByFile(String userName,String password,String targetAddress,
			MimeMessageDTO mimeDTO,List<String> filepath){
		return publicsendEmail(userName,password,targetAddress,mimeDTO,true,filepath);
	}
	/**
	 * 群发邮件 
	 * @param userName
	 * @param password
	 * @param targetAddress   多个邮件发送地址，以,分隔
	 * @param mimeDTO
	 * @return
	 */
	public  static boolean sendGroupEmail(String userName,String password,String targetAddress,
			MimeMessageDTO mimeDTO){
		return publicsendEmail(userName,password,targetAddress,mimeDTO,true,null);
	}
	/**
	 * 群发邮件 (附件)
	 * @param userName
	 * @param password
	 * @param targetAddress 多个邮件发送地址，以,分隔
	 * @param mimeDTO
	 * @param filepath      文件本地绝对路径
	 * @return
	 */
	public  static boolean sendGroupEmailByFile(String userName,String password,String targetAddress,
			MimeMessageDTO mimeDTO,List<String> filepath){
		return publicsendEmail(userName,password,targetAddress,mimeDTO,true,filepath);
	}
	
	
	
	/**
	 * 邮件发送基础方法
	 * @param userName
	 * @param password
	 * @param targetAddress
	 * @param mimeDTO
	 * @param isGroup
	 * @param filepath
	 * @return
	 */
	private static boolean publicsendEmail(String userName,String password,String targetAddress,
			MimeMessageDTO mimeDTO,boolean isGroup,List<String> filepath){
		Properties props = makeMailProperties(userName);
		String hostname=SMTPUtil.SimpleMailSender(userName);
		Session session = Session.getInstance(props, new PopupAuthenticator(userName, password));
		session.setDebug(true);
		try {
			Transport ts = session.getTransport();
			ts.connect(hostname,userName,password);
			Message message =!isGroup?createEmail(session,userName,targetAddress,mimeDTO)
					:createEmailByGroupAndFile(session,userName,
							targetAddress,mimeDTO,filepath==null?null:filepath);
			ts.sendMessage(message,message.getAllRecipients());
			ts.close();
		} catch (Exception mex) {
			mex.printStackTrace();
			return false;
		}
		return true;
	}
	
	
	
	
	
	/**
	 * 创建邮件信息
	 * @param userName
	 * @return
	 */
	private static Properties makeMailProperties(String userName){
		Properties props = new Properties();
		String hostname=SMTPUtil.SimpleMailSender(userName);
		props.put("mail.smtp.host", hostname);
		props.setProperty("mail.smtp.auth", "true");
		props.setProperty("mail.transport.protocol", "smtp");
		if(hostname.indexOf(".qq.com")!=-1){
			props.setProperty("mail.smtp.socketFactory.port", "465");
			props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		}
		return props;
	}
	
	
	
	/**
	 * 创建邮件
	 * @author Wujun
	 * Create_time:2015年10月17日 下午7:45:57
	 * description:
	 */
	private static Message createEmail(Session session,String userName,String regMail,MimeMessageDTO mimeDTO){
		MimeMessage message = new MimeMessage(session);
		try {
			message.setFrom(new InternetAddress(userName));
			message.setRecipient(Message.RecipientType.TO, new InternetAddress(regMail));
			message.setSubject(mimeDTO.getSubject());
			message.setContent(mimeDTO.getText(),"text/html;charset=UTF-8");
			message.saveChanges();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		return message;
	}
	/**
	 * 创建群发带附件
	 * @return
	 */
	private static Message createEmailByGroupAndFile(Session session,String userName,
								String regMail,MimeMessageDTO mimeDTO,List<String> filepath){
		MimeMessage message = new MimeMessage(session);
		try {
			message.setFrom(new InternetAddress(userName));
			  // 创建收件人列表  
	        if (regMail != null && regMail.trim().length() > 0) {  
	            String[] arr = regMail.split(",");  
	            int receiverCount = arr.length; 
	            if (receiverCount > 0) {
	            	InternetAddress[] address = new InternetAddress[receiverCount];  
	                for (int i = 0; i < receiverCount; i++) {  
	                    address[i] = new InternetAddress(arr[i]);  
	                }  
	                message.setRecipients(Message.RecipientType.TO, address);
	            }
	        }
	     // 后面的BodyPart将加入到此处创建的Multipart中  
            Multipart mp = new MimeMultipart();  
	     // 附件操作  
            if (filepath != null && filepath.size() > 0) {  
                for (String filename : filepath) {  
                    MimeBodyPart mbp = new MimeBodyPart();  
                    // 得到数据源  
                    FileDataSource fds = new FileDataSource(filename);  
                    // 得到附件本身并至入BodyPart  
                    mbp.setDataHandler(new DataHandler(fds));  
                    // 得到文件名同样至入BodyPart  
                    mbp.setFileName(fds.getName());  
                    mp.addBodyPart(mbp);  
                }  
                MimeBodyPart mbp = new MimeBodyPart();  
                mbp.setText(mimeDTO.getText());  
                mp.addBodyPart(mbp);  
                // 移走集合中的所有元素  
                filepath.clear();  
                // Multipart加入到信件  
                message.setContent(mp);  
            } else {  
                // 设置邮件正文  
//            	message.setText(mimeDTO.getText());  
            	message.setContent(mimeDTO.getText(),"text/html;charset=UTF-8");
            } 
			message.setSubject(mimeDTO.getSubject());
			message.saveChanges();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		return message;
	}
	
	
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getSmtpHost() {
		return smtpHost;
	}
	public void setSmtpHost(String smtpHost) {
		this.smtpHost = smtpHost;
	}
	public String getTargetAddress() {
		return targetAddress;
	}
	public void setTargetAddress(String targetAddress) {
		this.targetAddress = targetAddress;
	}
}
