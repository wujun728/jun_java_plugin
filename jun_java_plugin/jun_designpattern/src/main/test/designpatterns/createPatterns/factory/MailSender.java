package designpatterns.createPatterns.factory;

public class MailSender implements Sender {

	@Override
	public void send() {
		System.err.println("send mail ...  ");
	}

}
