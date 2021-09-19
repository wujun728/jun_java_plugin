package com.jun.web.utils.mail;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

/**
 * �ʼ����ʹ�������
 * @author Wujun
 * @date 2013-12-19 14:08
 */
public class EmailHandle {

	/**�ʼ�����**/
	private MimeMessage mimeMsg; 
	/**�����ʼ���Session�Ự**/
	private Session session; 
	/**�ʼ�����ʱ��һЩ������Ϣ��һ�����Զ���**/
	private Properties props;
	/**�����˵��û���**/
	private String sendUserName;
	/**����������**/
	private String sendUserPass; 
	/**������ӵ����**/
	private Multipart mp;
	/**��Ÿ����ļ�**/
	private List<FileDataSource> files = new LinkedList<FileDataSource>();

	
	public EmailHandle(String smtp) {
		sendUserName = "";
		sendUserPass = "";
		setSmtpHost(smtp);
		createMimeMessage();
	}

	private void setSmtpHost(String hostName) {
		if (props == null){
			props = System.getProperties();
		}
		props.put("mail.smtp.host", hostName);
	}

	public boolean createMimeMessage() {
		try {
			/**��props��������������ʼ��session����**/
			session = Session.getDefaultInstance(props, null);
			/**��session��������������ʼ���ʼ�����**/
			mimeMsg = new MimeMessage(session);
			/**���ɸ��������ʵ��**/
			mp = new MimeMultipart();
			return true;
		} catch (Exception e) {
			System.err.println("��ȡ�ʼ��Ự����ʱ��������" + e);
			return false;
		}
		
	}

	/**
	 * ����SMTP�������֤
	 */
	public void setNeedAuth(boolean need) {
		if (props == null){ props = System.getProperties();}
		if (need){
			props.put("mail.smtp.auth", "true");
		}else{
			props.put("mail.smtp.auth", "false");
		}
			
	}

	/**
	 * �����û������֤ʱ�������û���������
	 */
	public void setNamePass(String name, String pass) {
		sendUserName = name;
		sendUserPass = pass;
	}

	/**
	 * �����ʼ�����
	 * 
	 * @param mailSubject
	 * @return
	 */
	public boolean setSubject(String mailSubject) {
		try {
			mimeMsg.setSubject(mailSubject);
			return true;
		} catch (Exception e) {
			return false;
		}
		
	}

	/**
	 * �����ʼ�����,��������Ϊ�ı���ʽ��HTML�ļ���ʽ�����뷽ʽΪUTF-8
	 * @param mailBody
	 * @return
	 */
	public boolean setBody(String mailBody) {
		try {
			BodyPart bp = new MimeBodyPart();
			bp.setContent("<meta http-equiv=Content-Type content=text/html; charset=UTF-8>" + mailBody, "text/html;charset=UTF-8");
			/**�����������ʼ��ı�**/
			mp.addBodyPart(bp);
		} catch (Exception e) {
			System.err.println("�����ʼ�����ʱ��������" + e);
			return false;
		}
		return true;
	}

	/**
	 * ���ӷ��͸���
	 * @param filename �ʼ������ĵ�ַ��ֻ���Ǳ�����ַ�������������ַ�������׳��쳣
	 * @return
	 */
	public boolean addFileAffix(String filename) {
		try {
			BodyPart bp = new MimeBodyPart();
			FileDataSource fileds = new FileDataSource(filename);
			bp.setDataHandler(new DataHandler(fileds));
			/**���������������**/
			bp.setFileName(MimeUtility.encodeText(fileds.getName(), "UTF-8",null)); 
			/**��Ӹ���**/
			mp.addBodyPart(bp);
			files.add(fileds);
			return true;
		} catch (Exception e) {
			System.err.println("�����ʼ�����<" + filename + ">ʱ��������" + e);
			return false;
		}
		
	}

	/**
	 * ɾ����ӵĸ���
	 * @return
	 */
	public boolean delFileAffix() {
		try {
			FileDataSource fileds = null;
			for (Iterator<FileDataSource> it = files.iterator(); it.hasNext();) {
				fileds = it.next();
				if (fileds != null && fileds.getFile() != null) {
					fileds.getFile().delete();
				}
			}
			return true;
		} catch (Exception e) {
			System.err.println("ɾ���ʼ�������������" + e);
			return false;
		}
		
	}

	/**
	 * ���÷����˵�ַ
	 * @param from   �����˵�ַ
	 * @return
	 */
	public boolean setFrom(String from) {
		try {
			mimeMsg.setFrom(new InternetAddress(from));
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * �����ռ��˵�ַ
	 * @param to�ռ��˵ĵ�ַ
	 * @return
	 */
	public boolean setTo(String to) {
		try {
			if (to == null){ return false;}
			mimeMsg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * ���ͳ���
	 * @param copyto
	 * @return
	 */
	public boolean setCopyTo(String copyto) {
		try {
			if (copyto == null){return false;}
			mimeMsg.setRecipients(javax.mail.Message.RecipientType.CC,InternetAddress.parse(copyto));
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * �����ʼ�
	 * @return
	 */
	public boolean sendEmail() throws Exception {
		System.out.println("���ڷ����ʼ�....");
		mimeMsg.setContent(mp);
		mimeMsg.saveChanges();
		Session mailSession = Session.getInstance(props, null);
		Transport transport = mailSession.getTransport("smtp");
		/** �����ʼ������������������֤ **/
		transport.connect((String) props.get("mail.smtp.host"), sendUserName, sendUserPass);
		/** �����ʼ� **/
		transport.sendMessage(mimeMsg, mimeMsg.getRecipients(Message.RecipientType.TO));
		transport.close();
		System.out.println("�����ʼ��ɹ���");
		return true;
	}
}