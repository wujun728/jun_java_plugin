package designpatterns.createPatterns.factory;

public class MailFactory implements Provider {

	@Override
	public void sendMsg() {
		Sender sender=new MailSender();
		sender.send();
	}

}
