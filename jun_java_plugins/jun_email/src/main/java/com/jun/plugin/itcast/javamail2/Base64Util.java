package com.jun.plugin.itcast.javamail2;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.Message.RecipientType;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

public class Base64Util {

	/**
	 * @param args add by zxx ,Dec 30, 2008
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		System.out.println("please input user name:");
		String username = new BufferedReader(
				new InputStreamReader(System.in))
				.readLine();
		System.out.println(Base64.getEncoder().encodeToString(username.getBytes()));
		System.out.println("please input password:");
		String password = new BufferedReader(
				new InputStreamReader(System.in))
				.readLine();
		System.out.println(Base64.getEncoder().encodeToString(password.getBytes()));
	}
	

}
