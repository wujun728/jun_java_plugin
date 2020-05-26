package designpatterns.createPatterns.factory;

public class TestFactory {

	public static void main(String[] args) {
		Provider p=new MailFactory();
		p.sendMsg();
		Provider sms=new SmsFactory();
		sms.sendMsg();
	}

}
