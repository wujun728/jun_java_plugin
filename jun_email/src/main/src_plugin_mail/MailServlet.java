

import java.io.IOException;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


//http://localhost:18080/jun_plugin_base/mail?method=getMsg&username=abc
@WebServlet(name = "MailServlet", value = { "/mail" }, asyncSupported = true)
public class MailServlet extends HttpServlet {

	 
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doPost(request, response);
	}

	 
	public void sendMail(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			//Demo2.main(new String[]{});
			
			Context initCtx = new InitialContext();
			Context envCtx = (Context) initCtx.lookup("java:comp/env");
			Session session = (Session) envCtx.lookup("mail/Dog");

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("itcast_test@sina.com"));
			InternetAddress to[] = new InternetAddress[1];
			to[0] = new InternetAddress("itcast_test@sina.com");
			message.setRecipients(Message.RecipientType.TO, to);
			message.setSubject("ha");
			message.setText("test");
			//Transport.send(message);
			Transport transport = session.getTransport();
			transport.connect("smtp.sina.com", "itcast_test", "123456");
			transport.sendMessage(message, to);
			transport.close();
			response.getWriter().print("ok!");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace(response.getWriter());
		}
	}

}
