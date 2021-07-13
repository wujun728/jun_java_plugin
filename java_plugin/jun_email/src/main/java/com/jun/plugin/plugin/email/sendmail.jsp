＜%@ page
　　import=" javax.mail.*, javax.mail.internet.*, javax.activation.*,java.util.*"
　　%＞
　　＜html＞
　　＜head＞
　　＜TITLE＞JSP meets JavaMail, what a sweet combo.＜/TITLE＞
　　＜/HEAD＞
　　＜BODY＞
　　＜%
　　try{
　　Properties props = new Properties();
　　Session sendMailSession;
　　Store store;
　　Transport transport;
　　sendMailSession = Session.getInstance(props, null);
　　props.put("mail.smtp.host", "smtp.jspinsider.com");
　　Message newMessage = new MimeMessage(sendMailSession);
　　newMessage.setFrom(new InternetAddress(request.getParameter("from")));
　　newMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(request.getParameter("to")));
　　newMessage.setSubject(request.getParameter("subject"));
　　newMessage.setSentDate(new Date());
　　newMessage.setText(request.getParameter("text"));
　　transport = sendMailSession.getTransport("smtp");
　　transport.send(newMessage);
　　%＞
　　＜P＞Your mail has been sent.＜/P＞
　　＜%
　　}
　　catch(MessagingException m)
　　{
　　out.println(m.toString());
　　}
　　%＞
　　＜/BODY＞
　　＜/HTML＞