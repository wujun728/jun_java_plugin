package com.jun.plugin.designpatterns.createPatterns.factory;

public class SmsFactory implements Provider {

	@Override
	public void sendMsg() {
		Sender sender=new SmsSender();
		sender.send();
	}

}
