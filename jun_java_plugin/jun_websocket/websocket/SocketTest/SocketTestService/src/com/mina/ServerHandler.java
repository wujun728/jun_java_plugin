package com.mina;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

public class ServerHandler extends IoHandlerAdapter {

	@Override
	public void messageReceived(IoSession session, Object message)
			throws Exception {
		super.messageReceived(session, message);
		System.out.println("Received:"+message);
		if(message.toString().length()>0){
			session.write(message.toString().trim()+" reveived at: "+System.currentTimeMillis());
		}else{
			session.write("ok");
		}
	}

	@Override
	public void messageSent(IoSession session, Object message) throws Exception {
		super.messageSent(session, message);
		System.out.println("messageSent:"+message);
	}

	@Override
	public void sessionClosed(IoSession session) throws Exception {
		super.sessionClosed(session);
		System.out.println("sessionClosed:"+session);
	}

	@Override
	public void sessionCreated(IoSession session) throws Exception {
		super.sessionCreated(session);
		System.out.println("sessionCreated:"+session);
		session.getConfig().setIdleTime(IdleStatus.BOTH_IDLE, 3*1000);
	}

	@Override
	public void sessionIdle(IoSession session, IdleStatus status)
			throws Exception {
		super.sessionIdle(session, status);
		System.out.println("sessionIdle:"+session);
		if(status==IdleStatus.BOTH_IDLE){
			session.write("heartBeat");
		}
	}

	@Override
	public void sessionOpened(IoSession session) throws Exception {
		super.sessionOpened(session);
		System.out.println("sessionOpened:"+session);
		session.write("Hello");
	}

	

}
