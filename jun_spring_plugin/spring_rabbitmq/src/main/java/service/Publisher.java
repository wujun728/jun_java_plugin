package service;

import po.Mail;

public interface Publisher {
	public void publishMail(Mail mail);//使用fanout交换机发布消息给所有队列
	public void senddirectMail(Mail mail,String routingkey);//使用direct交换机发送消息
	public void sendtopicMail(Mail mail,String routingkey);//使用topic交换机发送消息
}
