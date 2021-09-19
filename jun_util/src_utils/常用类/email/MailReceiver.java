package book.email;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.util.Date;

import javax.mail.BodyPart;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.NoSuchProviderException;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;

/**
 * 邮件接收器，目前支持pop3协议。
 * 能够接收文本、HTML和带有附件的邮件
 */
public class MailReceiver {
	// 收邮件的参数配置
	private MailReceiverInfo receiverInfo;
	// 与邮件服务器连接后得到的邮箱
	private Store store;
	// 收件箱
	private Folder folder;
	// 收件箱中的邮件消息
	private Message[] messages;
	// 当前正在处理的邮件消息
	private Message currentMessage;

	private String currentEmailFileName;

	public MailReceiver(MailReceiverInfo receiverInfo) {
		this.receiverInfo = receiverInfo;
	}
	/**
	 * 收邮件
	 */
	public void receiveAllMail() throws Exception{
		if (this.receiverInfo == null){
			throw new Exception("必须提供接收邮件的参数！");
		}
		// 连接到服务器
		if (this.connectToServer()) {
			// 打开收件箱
			if (this.openInBoxFolder()) {
				// 获取所有邮件
				this.getAllMail();
				this.closeConnection();
			} else {
				throw new Exception("打开收件箱失败！");
			}
		} else {
			throw new Exception("连接邮件服务器失败！");
		}
	}
	
	/**
	 * 登陆邮件服务器
	 */
	private boolean connectToServer() {
		// 判断是否需要身份认证
		MyAuthenticator authenticator = null;
		if (this.receiverInfo.isValidate()) {
			// 如果需要身份认证，则创建一个密码验证器
			authenticator = new MyAuthenticator(this.receiverInfo.getUserName(),
					this.receiverInfo.getPassword());
		}
		//创建session
		Session session = Session.getInstance(this.receiverInfo
				.getProperties(), authenticator);

		//创建store,建立连接
		try {
			this.store = session.getStore(this.receiverInfo.getProtocal());
		} catch (NoSuchProviderException e) {
			System.out.println("连接服务器失败！");
			return false;
		}

		System.out.println("connecting");
		try {
			this.store.connect();
		} catch (MessagingException e) {
			System.out.println("连接服务器失败！");
			return false;
		}
		System.out.println("连接服务器成功");
		return true;
	}
	/**
	 * 打开收件箱
	 */
	private boolean openInBoxFolder() {
		try {
			this.folder = store.getFolder("INBOX");
			// 只读
			folder.open(Folder.READ_ONLY);
			return true;
		} catch (MessagingException e) {
			System.err.println("打开收件箱失败！");
		}
		return false;
	}
	/**
	 * 断开与邮件服务器的连接
	 */
	private boolean closeConnection() {
		try {
			if (this.folder.isOpen()) {
				this.folder.close(true);
			}
			this.store.close();
			System.out.println("成功关闭与邮件服务器的连接！");
			return true;
		} catch (Exception e) {
			System.out.println("关闭和邮件服务器之间连接时出错！");
		}
		return false;
	}
	
	/**
	 * 获取messages中的所有邮件
	 * @throws MessagingException 
	 */
	private void getAllMail() throws MessagingException {
		//从邮件文件夹获取邮件信息
		this.messages = this.folder.getMessages();
		System.out.println("总的邮件数目：" + messages.length);
		System.out.println("新邮件数目：" + this.getNewMessageCount());
		System.out.println("未读邮件数目：" + this.getUnreadMessageCount());
		//将要下载的邮件的数量。
		int mailArrayLength = this.getMessageCount();
		System.out.println("一共有邮件" + mailArrayLength + "封");
		int errorCounter = 0; //邮件下载出错计数器
		int successCounter = 0;
		for (int index = 0; index < mailArrayLength; index++) {
			try {
				this.currentMessage = (messages[index]); //设置当前message
				System.out.println("正在获取第" + index + "封邮件......");
				this.showMailBasicInfo();
				getMail(); //获取当前message
				System.out.println("成功获取第" + index + "封邮件");
				successCounter++;
			} catch (Throwable e) {
				errorCounter++;
				System.err.println("下载第" + index + "封邮件时出错");
			}
		}
		System.out.println("------------------");
		System.out.println("成功下载了" + successCounter + "封邮件");
		System.out.println("失败下载了" + errorCounter + "封邮件");
		System.out.println("------------------");
	}

	/**
	 * 显示邮件的基本信息
	 */
	private void showMailBasicInfo() throws Exception{
		showMailBasicInfo(this.currentMessage);
	}
	private void showMailBasicInfo(Message message) throws Exception {
		System.out.println("-------- 邮件ID：" + this.getMessageId()
				+ " ---------");
		System.out.println("From：" + this.getFrom());
		System.out.println("To：" + this.getTOAddress());
		System.out.println("CC：" + this.getCCAddress());
		System.out.println("BCC：" + this.getBCCAddress());
		System.out.println("Subject：" + this.getSubject());
		System.out.println("发送时间：：" + this.getSentDate());
		System.out.println("是新邮件？" + this.isNew());
		System.out.println("要求回执？" + this.getReplySign());
		System.out.println("包含附件？" + this.isContainAttach());
		System.out.println("------------------------------");
	}

	/**
	 * 获得邮件的收件人，抄送，和密送的地址和姓名，根据所传递的参数的不同 
	 * "to"----收件人 "cc"---抄送人地址 "bcc"---密送人地址
	 */
	private String getTOAddress() throws Exception {
		return getMailAddress("TO", this.currentMessage);
	}

	private String getCCAddress() throws Exception {
		return getMailAddress("CC", this.currentMessage);
	}

	private String getBCCAddress() throws Exception {
		return getMailAddress("BCC", this.currentMessage);
	}

	/**
	 * 获得邮件地址
	 * @param type		类型，如收件人、抄送人、密送人
	 * @param mimeMessage	邮件消息
	 * @return
	 * @throws Exception
	 */
	private String getMailAddress(String type, Message mimeMessage)
			throws Exception {
		String mailaddr = "";
		String addtype = type.toUpperCase();
		InternetAddress[] address = null;
		if (addtype.equals("TO") || addtype.equals("CC")
				|| addtype.equals("BCC")) {
			if (addtype.equals("TO")) {
				address = (InternetAddress[]) mimeMessage
						.getRecipients(Message.RecipientType.TO);
			} else if (addtype.equals("CC")) {
				address = (InternetAddress[]) mimeMessage
						.getRecipients(Message.RecipientType.CC);
			} else {
				address = (InternetAddress[]) mimeMessage
						.getRecipients(Message.RecipientType.BCC);
			}
			if (address != null) {
				for (int i = 0; i < address.length; i++) {
					// 先获取邮件地址
					String email = address[i].getAddress();
					if (email == null){
						email = "";
					}else {
						email = MimeUtility.decodeText(email);
					}
					// 再取得个人描述信息
					String personal = address[i].getPersonal();
					if (personal == null){
						personal = "";
					} else {
						personal = MimeUtility.decodeText(personal);
					}
					// 将个人描述信息与邮件地址连起来
					String compositeto = personal + "<" + email + ">";
					// 多个地址时，用逗号分开
					mailaddr += "," + compositeto;
				}
				mailaddr = mailaddr.substring(1);
			}
		} else {
			throw new Exception("错误的地址类型！!");
		}
		return mailaddr;
	}

	/**
	 * 获得发件人的地址和姓名
	 * @throws Exception
	 */
	private String getFrom() throws Exception {
		return getFrom(this.currentMessage);
	}

	private String getFrom(Message mimeMessage) throws Exception {
		InternetAddress[] address = (InternetAddress[]) mimeMessage.getFrom();
		// 获得发件人的邮箱
		String from = address[0].getAddress();
		if (from == null){
			from = "";
		}
		// 获得发件人的描述信息
		String personal = address[0].getPersonal();
		if (personal == null){
			personal = "";
		}
		// 拼成发件人完整信息
		String fromaddr = personal + "<" + from + ">";
		return fromaddr;
	}

	/**
	 * 获取messages中message的数量
	 * @return
	 */
	private int getMessageCount() {
		return this.messages.length;
	}

	/**
	 * 获得收件箱中新邮件的数量
	 * @return
	 * @throws MessagingException
	 */
	private int getNewMessageCount() throws MessagingException {
		return this.folder.getNewMessageCount();
	}

	/**
	 * 获得收件箱中未读邮件的数量
	 * @return
	 * @throws MessagingException
	 */
	private int getUnreadMessageCount() throws MessagingException {
		return this.folder.getUnreadMessageCount();
	}

	/**
	 * 获得邮件主题
	 */
	private String getSubject() throws MessagingException {
		return getSubject(this.currentMessage);
	}

	private String getSubject(Message mimeMessage) throws MessagingException {
		String subject = "";
		try {
			// 将邮件主题解码
			subject = MimeUtility.decodeText(mimeMessage.getSubject());
			if (subject == null){
				subject = "";
			}
		} catch (Exception exce) {
		}
		return subject;
	}

	/**
	 * 获得邮件发送日期
	 */
	private Date getSentDate() throws Exception {
		return getSentDate(this.currentMessage);
	}

	private Date getSentDate(Message mimeMessage) throws Exception {
		return mimeMessage.getSentDate();
	}

	/**
	 * 判断此邮件是否需要回执，如果需要回执返回"true",否则返回"false"
	 */
	private boolean getReplySign() throws MessagingException {
		return getReplySign(this.currentMessage);
	}

	private boolean getReplySign(Message mimeMessage) throws MessagingException {
		boolean replysign = false;
		String needreply[] = mimeMessage
				.getHeader("Disposition-Notification-To");
		if (needreply != null) {
			replysign = true;
		}
		return replysign;
	}

	/**
	 * 获得此邮件的Message-ID
	 */
	private String getMessageId() throws MessagingException {
		return getMessageId(this.currentMessage);
	}

	private String getMessageId(Message mimeMessage) throws MessagingException {
		return ((MimeMessage) mimeMessage).getMessageID();
	}

	/**
	 * 判断此邮件是否已读，如果未读返回返回false,反之返回true
	 */
	private boolean isNew() throws MessagingException {
		return isNew(this.currentMessage);
	}
	private boolean isNew(Message mimeMessage) throws MessagingException {
		boolean isnew = false;
		Flags flags = mimeMessage.getFlags();
		Flags.Flag[] flag = flags.getSystemFlags();
		for (int i = 0; i < flag.length; i++) {
			if (flag[i] == Flags.Flag.SEEN) {
				isnew = true;
				break;
			}
		}
		return isnew;
	}

	/**
	 * 判断此邮件是否包含附件
	 */
	private boolean isContainAttach() throws Exception {
		return isContainAttach(this.currentMessage);
	}
	private boolean isContainAttach(Part part) throws Exception {
		boolean attachflag = false;
		if (part.isMimeType("multipart/*")) {
			// 如果邮件体包含多部分
			Multipart mp = (Multipart) part.getContent();
			// 遍历每部分
			for (int i = 0; i < mp.getCount(); i++) {
				// 获得每部分的主体
				BodyPart bodyPart = mp.getBodyPart(i);
				String disposition = bodyPart.getDisposition();
				if ((disposition != null)
						&& ((disposition.equals(Part.ATTACHMENT)) || (disposition
								.equals(Part.INLINE)))){
					attachflag = true;
				} else if (bodyPart.isMimeType("multipart/*")) {
					attachflag = isContainAttach((Part) bodyPart);
				} else {
					String contype = bodyPart.getContentType();
					if (contype.toLowerCase().indexOf("application") != -1){
						attachflag = true;
					}
					if (contype.toLowerCase().indexOf("name") != -1){
						attachflag = true;
					}
				}
			}
		} else if (part.isMimeType("message/rfc822")) {
			attachflag = isContainAttach((Part) part.getContent());
		}
		return attachflag;
	}

	
	/**
	 * 获得当前邮件
	 */
	private void getMail() throws Exception {
		try {
			this.saveMessageAsFile(currentMessage);
			this.parseMessage(currentMessage);
		} catch (IOException e) {
			throw new IOException("保存邮件出错，检查保存路径");
		} catch (MessagingException e) {
			throw new MessagingException("邮件转换出错");
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("未知错误");
		}
	}
	
	/**
	 * 保存邮件源文件
	 */
	private void saveMessageAsFile(Message message) {
		try {
			// 将邮件的ID中尖括号中的部分做为邮件的文件名
			String oriFileName = getInfoBetweenBrackets(this.getMessageId(message)
					.toString());
			//设置文件后缀名。若是附件则设法取得其文件后缀名作为将要保存文件的后缀名，
			//若是正文部分则用.htm做后缀名
			String emlName = oriFileName;
			String fileNameWidthExtension = this.receiverInfo.getEmailDir()
					+ oriFileName + this.receiverInfo.getEmailFileSuffix();
			File storeFile = new File(fileNameWidthExtension);
			for (int i = 0; storeFile.exists(); i++) {
				emlName = oriFileName + i;
				fileNameWidthExtension = this.receiverInfo.getEmailDir()
						+ emlName + this.receiverInfo.getEmailFileSuffix();
				storeFile = new File(fileNameWidthExtension);
			}
			this.currentEmailFileName = emlName;
			System.out.println("邮件消息的存储路径: " + fileNameWidthExtension);
			// 将邮件消息的内容写入ByteArrayOutputStream流中
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			message.writeTo(baos);
			// 读取邮件消息流中的数据
			StringReader in = new StringReader(baos.toString());
			// 存储到文件
			saveFile(fileNameWidthExtension, in);
		} catch (MessagingException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * 解析邮件
	 */
	private void parseMessage(Message message) throws IOException,
			MessagingException {
		Object content = message.getContent();
		// 处理多部分邮件
		if (content instanceof Multipart) {
			handleMultipart((Multipart) content);
		} else {
			handlePart(message);
		}
	}

	/*
	 * 解析Multipart
	 */
	private void handleMultipart(Multipart multipart) throws MessagingException,
			IOException {
		for (int i = 0, n = multipart.getCount(); i < n; i++) {
			handlePart(multipart.getBodyPart(i));
		}
	}
	/*
	 * 解析指定part,从中提取文件
	 */
	private void handlePart(Part part) throws MessagingException, IOException {
		String disposition = part.getDisposition();
		String contentType = part.getContentType();
		String fileNameWidthExtension = "";
		// 获得邮件的内容输入流
		InputStreamReader sbis = new InputStreamReader(part.getInputStream());
		// 没有附件的情况
		if (disposition == null) {
			if ((contentType.length() >= 10)
					&& (contentType.toLowerCase().substring(0, 10)
							.equals("text/plain"))) {
				fileNameWidthExtension = this.receiverInfo.getAttachmentDir()
						+ this.currentEmailFileName + ".txt";
			} else if ((contentType.length() >= 9) // Check if html
					&& (contentType.toLowerCase().substring(0, 9)
							.equals("text/html"))) {
				fileNameWidthExtension = this.receiverInfo.getAttachmentDir()
						+ this.currentEmailFileName + ".html";
			} else if ((contentType.length() >= 9) // Check if html
					&& (contentType.toLowerCase().substring(0, 9)
							.equals("image/gif"))) {
				fileNameWidthExtension = this.receiverInfo.getAttachmentDir()
						+ this.currentEmailFileName + ".gif";
			} else if ((contentType.length() >= 11)
					&& contentType.toLowerCase().substring(0, 11).equals(
							"multipart/*")) {
//				System.out.println("multipart body: " + contentType);
				handleMultipart((Multipart) part.getContent());
			} else { // Unknown type
//				System.out.println("Other body: " + contentType);
				fileNameWidthExtension = this.receiverInfo.getAttachmentDir()
						+ this.currentEmailFileName + ".txt";
			}
			// 存储内容文件
			System.out.println("保存邮件内容到：" + fileNameWidthExtension);
			saveFile(fileNameWidthExtension, sbis);

			return;
		}

		// 各种有附件的情况
		String name = "";
		if (disposition.equalsIgnoreCase(Part.ATTACHMENT)) {
			name = getFileName(part);
//			System.out.println("Attachment: " + name + " : "
//					+ contentType);
			fileNameWidthExtension = this.receiverInfo.getAttachmentDir() + name;
		} else if (disposition.equalsIgnoreCase(Part.INLINE)) {
			name = getFileName(part);
//			System.out.println("Inline: " + name + " : "
//					+ contentType);
			fileNameWidthExtension = this.receiverInfo.getAttachmentDir() + name;
		} else {
//			System.out.println("Other: " + disposition);
		}
		// 存储各类附件
		if (!fileNameWidthExtension.equals("")) {
			System.out.println("保存邮件附件到：" + fileNameWidthExtension);
			saveFile(fileNameWidthExtension, sbis);
		}
	}
	private String getFileName(Part part) throws MessagingException,
			UnsupportedEncodingException {
		String fileName = part.getFileName();
		fileName = MimeUtility.decodeText(fileName);
		String name = fileName;
		if (fileName != null) {
			int index = fileName.lastIndexOf("/");
			if (index != -1) {
				name = fileName.substring(index + 1);
			}
		}
		return name;
	}
	/**
	 * 保存文件内容
	 * @param fileName	文件名
	 * @param input		输入流
	 * @throws IOException
	 */
	private void saveFile(String fileName, Reader input) throws IOException {

		// 为了放置文件名重名，在重名的文件名后面天上数字
		File file = new File(fileName);
		// 先取得文件名的后缀
		int lastDot = fileName.lastIndexOf(".");
		String extension = fileName.substring(lastDot);
		fileName = fileName.substring(0, lastDot);
		for (int i = 0; file.exists(); i++) {
			//　如果文件重名，则添加i
			file = new File(fileName + i + extension);
		}
		// 从输入流中读取数据，写入文件输出流
		FileWriter fos = new FileWriter(file);
		BufferedWriter bos = new BufferedWriter(fos);
		BufferedReader bis = new BufferedReader(input);
		int aByte;
		while ((aByte = bis.read()) != -1) {
			bos.write(aByte);
		}
		// 关闭流
		bos.flush();
		bos.close();
		bis.close();
	}

	/**
	 * 获得尖括号之间的字符
	 * @param str
	 * @return
	 * @throws Exception
	 */
	private String getInfoBetweenBrackets(String str) throws Exception {
		int i, j; //用于标识字符串中的"<"和">"的位置
		if (str == null) {
			str = "error";
			return str;
		}
		i = str.lastIndexOf("<");
		j = str.lastIndexOf(">");
		if (i != -1 && j != -1){
			str = str.substring(i + 1, j);
		}
		return str;
	}

	public static void main(String[] args) throws Exception {
		MailReceiverInfo receiverInfo = new MailReceiverInfo();
		receiverInfo.setMailServerHost("pop.163.com");
		receiverInfo.setMailServerPort("110");
		receiverInfo.setValidate(true);
		receiverInfo.setUserName("***");
		receiverInfo.setPassword("***");
		receiverInfo.setAttachmentDir("C:/temp/mail/");
		receiverInfo.setEmailDir("C:/temp/mail/");

		MailReceiver receiver = new MailReceiver(receiverInfo);
		receiver.receiveAllMail();
	}
}