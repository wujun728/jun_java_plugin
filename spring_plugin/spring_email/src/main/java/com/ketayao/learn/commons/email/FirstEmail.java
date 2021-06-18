/**
 * <pre>
 * Date:			2013年9月6日
 * Author:			<a href="mailto:ketayao@gmail.com">ketayao</a>
 * Description:
 * </pre>
 **/

package com.ketayao.learn.commons.email;

import java.net.URL;

import org.apache.commons.mail.HtmlEmail;

/**
 * 
 * @author <a href="mailto:ketayao@gmail.com">ketayao</a>
 * @since 2013年9月6日 下午5:23:02
 */

public class FirstEmail {
	public static void main(String[] args) throws Exception {
		// Create the email message
		HtmlEmail email = new HtmlEmail();
		//email.setSmtpPort(587);
		email.setHostName("smtp.live.com");
		email.setStartTLSEnabled(true);
		//email.setSSLOnConnect(true);
		email.setFrom("ketayao@hotmail.com", "Me");
		email.setAuthentication("ketayao@hotmail.com", "84893627");
		
		email.addTo("279763248@qq.com", "John Doe");
		
		email.setSubject("Test email with inline image");

		// embed the image and get the content id
		URL url = new URL("http://www.apache.org/images/asf_logo_wide.gif");
		String cid = email.embed(url, "Apache logo");

		// set the html message
		email.setHtmlMsg("<html>The apache logo - <img src=\"cid:" + cid
				+ "\"></html>");

		// set the alternative message
		email.setTextMsg("Your email client does not support HTML messages");

		// send the email
		email.send();
	}
}
