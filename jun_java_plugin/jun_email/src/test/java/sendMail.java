

import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;

public class sendMail {

	private MimeMessage mimeMsg; // MIME�ʼ�����

	private Session session; // �ʼ��Ự����

	private Properties props; // ϵͳ����

	private boolean needAuth = false; // smtp�Ƿ���Ҫ��֤

	private String username = ""; // smtp��֤�û���������

	private String password = "";

	private Multipart mp; // Multipart����,�ʼ�����,����,���������ݾ���ӵ����к�������MimeMessage����

	public sendMail(String smtp) {
		setSmtpHost(smtp);
		createMimeMessage();
	}

	/**
	 * @param hostName
	 *            String
	 */
	public void setSmtpHost(String hostName) {
		System.out.println("����ϵͳ���ԣ�mail.smtp.host = " + hostName);
		if (props == null)
			props = System.getProperties(); // ���ϵͳ���Զ���

		props.put("mail.smtp.host", hostName); // ����SMTP����
	}

	/**
	 * @return boolean
	 */
	public boolean createMimeMessage() {
		try {
			System.out.println("׼����ȡ�ʼ��Ự����");
			session = Session.getDefaultInstance(props, null); // ����ʼ��Ự����
		} catch (Exception e) {
			System.err.println("��ȡ�ʼ��Ự����ʱ��������" + e);
			return false;
		}

		System.out.println("׼������MIME�ʼ�����");
		try {
			mimeMsg = new MimeMessage(session); // ����MIME�ʼ�����
			mp = new MimeMultipart();

			return true;
		} catch (Exception e) {
			System.err.println("����MIME�ʼ�����ʧ�ܣ�" + e);
			return false;
		}
	}

	/**
	 * @param need
	 *            boolean
	 */
	public void setNeedAuth(String need) {
		System.out.println("����smtp�����֤��mail.smtp.auth = " + need);
		if (props == null)
			props = System.getProperties();

		if (need.equals("true")) {
			props.put("mail.smtp.auth", "true");
		} else {
			props.put("mail.smtp.auth", "false");
		}
	}

	/**
	 * @param name
	 *            String
	 * @param pass
	 *            String
	 */
	public void setNamePass(String name, String pass) {
		username = name;
		password = pass;
	}

	/**
	 * @param mailSubject
	 *            String
	 * @return boolean
	 */
	public boolean setSubject(String mailSubject) {
		System.out.println("�����ʼ����⣡");
		try {
			mimeMsg.setSubject(mailSubject);
			return true;
		} catch (Exception e) {
			System.err.println("�����ʼ����ⷢ������");
			return false;
		}
	}

	/**
	 * @param mailBody
	 *            String
	 */
	public boolean setBody(String mailBody) {
		try {
			BodyPart bp = new MimeBodyPart();
			bp.setContent("" + mailBody, "text/html;charset=GB2312");
			mp.addBodyPart(bp);

			return true;
		} catch (Exception e) {
			System.err.println("�����ʼ�����ʱ��������" + e);
			return false;
		}
	}

	/**
	 * @param name
	 *            String
	 * @param pass
	 *            String
	 */
	public boolean addFileAffix(String[] filenameArry) {

		System.out.println("�����ʼ�������" + filenameArry.toString());
		try {
			BodyPart bp = new MimeBodyPart();
			for (String filename : filenameArry) {
				FileDataSource fileds = new FileDataSource(filename);
				bp.setDataHandler(new DataHandler(fileds));
				bp.setFileName(fileds.getName());
				mp.addBodyPart(bp);
			}
			return true;
		} catch (Exception e) {
			System.err.println("�����ʼ�������" + filenameArry.toString() + "��������" + e);
			return false;
		}
	}

	/**
	 * @param name
	 *            String
	 * @param pass
	 *            String
	 */
	public boolean setFrom(String from) {
		System.out.println("���÷����ˣ�");
		try {
			mimeMsg.setFrom(new InternetAddress(from)); // ���÷�����
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * @param name
	 *            String
	 * @param pass
	 *            String
	 */
	public boolean setTo(String to) {
		if (to == null)
			return false;
		try {
			mimeMsg.setRecipients(Message.RecipientType.TO, (Address[]) InternetAddress.parse(to));
			return true;
		} catch (Exception e) {
			return false;
		}

	}

	/**
	 * @param name
	 *            String
	 * @param pass
	 *            String
	 */
	public boolean setCopyTo(String copyto) {
		if (copyto == null)
			return false;
		try {
			mimeMsg.setRecipients(Message.RecipientType.CC, (Address[]) InternetAddress.parse(copyto));
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * @param name
	 *            String
	 * @param pass
	 *            String
	 */
	public boolean setBCopyTo(String copyto) {
		if (copyto == null)
			return false;
		try {
			mimeMsg.setRecipients(Message.RecipientType.BCC, (Address[]) InternetAddress.parse(copyto));
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * @param name
	 *            String
	 * @param pass
	 *            String
	 */
	public boolean sendout() {
		try {
			mimeMsg.setContent(mp);
			mimeMsg.saveChanges();
			System.out.println("���ڷ����ʼ�....");

			Session mailSession = Session.getInstance(props, null);
			Transport transport = mailSession.getTransport("smtp");
			transport.connect((String) props.get("mail.smtp.host"), username, password);
			transport.sendMessage(mimeMsg, mimeMsg.getRecipients(Message.RecipientType.TO));
			// transport.send(mimeMsg);
			transport.sendMessage(mimeMsg, mimeMsg.getRecipients(Message.RecipientType.CC));
			transport.sendMessage(mimeMsg, mimeMsg.getRecipients(Message.RecipientType.BCC));
			System.out.println("�����ʼ��ɹ���");
			transport.close();

			return true;
		} catch (Exception e) {
			System.err.println("�ʼ�����ʧ�ܣ�" + e);
			return false;
		}
	}

	/**
	 * Just do it as this
	 */
	public static void main(String[] args) {

		ResourceBundle rb = ResourceBundle.getBundle("mail", Locale.CHINA);

		// String mailbody = "��ã�����JavaMail��";
		String mailbody = rb.getString("mailbody");
		String smtpServer = rb.getString("smtpServer");
		String smtpAuth = rb.getString("smtpAuth");
		String sendTo = rb.getString("sendTo");
		String sendCC = rb.getString("sendCC");
		String sendBCC = rb.getString("sendBCC");
		String sendSubject = rb.getString("sendSubject");
		String sendFrom = rb.getString("sendFrom");
		String sendFile = rb.getString("sendFile");
		String sendUser = rb.getString("sendUser");
		String sendPasswd = rb.getString("sendPasswd");
		// sendMail themail = new sendMail("smtp.139.com");

		sendMail themail = new sendMail(smtpServer);

		themail.setNeedAuth(smtpAuth);

		// if (themail.setSubject("����") == false)
		if (themail.setSubject(sendSubject) == false)
			return;
		if (themail.setBody(mailbody) == false)
			return;
		// if (themail.setTo("15034062439@139.com") == false)
		if (themail.setTo(sendTo) == false)
			return;
		// if (themail.setCopyTo("zhanghongqiang_321@163.com") == false)
		if (themail.setCopyTo(sendCC) == false)
			return;
		// if (themail.setBCopyTo("421044319@qq.com")== false)
		if (themail.setBCopyTo(sendBCC) == false)
			return;
		// if (themail.setFrom("15034062439@139.com") == false)
		if (themail.setFrom(sendFrom) == false)
			return;
		// if (themail.addFileAffix("d:\\chart.xls") == false)
		// return;
		String[] file_arry = sendFile.split(",");
		if (themail.addFileAffix(file_arry) == false)
			return;
		// themail.setNamePass("15034062439@139.com", "map3084344");
		themail.setNamePass(sendUser, sendPasswd);

		// �����ʼ����û��������루���Լ�������û���������,�κε����䣬��163���䣬smtp��������ַΪ��smtp.163.com,�û��������������163ע����û��������룩

		if (themail.sendout() == false)
			return;
	}
}