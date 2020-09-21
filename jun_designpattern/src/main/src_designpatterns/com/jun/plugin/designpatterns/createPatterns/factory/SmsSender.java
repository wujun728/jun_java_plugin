package com.jun.plugin.designpatterns.createPatterns.factory;

public class SmsSender implements Sender {

	@Override
	public void send() {
		System.err.println(" sms send .... ");
	}

}
