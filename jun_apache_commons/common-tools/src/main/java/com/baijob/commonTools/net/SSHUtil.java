package com.baijob.commonTools.net;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.baijob.commonTools.LangUtil;
import com.baijob.commonTools.Exceptions.ConnException;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

/**
 * SSH安全连接相关工具类
 * 此工具类用于维护一个到跳板机的通道，并将跳板机可访问的服务器端口映射到本地使用
 * @author luxiaolei@baijob.com
 */
public class SSHUtil {
	private static Logger logger = LoggerFactory.getLogger(SSHUtil.class);
	
	/*--------------------------常量 start-------------------------------*/
	/** 不使用SSH的值 */
	public final static String SSH_NONE = "none";
	/*--------------------------常量 start-------------------------------*/
	
	/*--------------------------私有属性 start-------------------------------*/
	/** SSH会话池，key：host，value：Session对象 */
	private static Map<String, Session> sessionPool = new HashMap<String, Session>();
	/** 备选的本地端口 */
	private volatile static int alternativePort = 10000;
	/*--------------------------私有属性 start-------------------------------*/
	
	/**
	 * 生成一个本地端口，用于远程端口映射
	 * @return 未被使用的本地端口
	 */
	synchronized public static int generateLocalPort(){
		//获取可用端口
		while(!SocketUtil.isUsableLocalPort(alternativePort)){
			alternativePort ++;
		}
		return alternativePort;
	}
	
	/**
	 * 获得一个SSH跳板机会话，重用已经使用的会话
	 * @param sshHost 跳板机主机
	 * @param sshPort 跳板机端口
	 * @param sshUser 跳板机用户名
	 * @param sshPass 跳板机密码
	 * @return SSH会话
	 */
	synchronized public static Session getSession(String sshHost, int sshPort, String sshUser, String sshPass){
		Session session = sessionPool.get(sshHost);
		if(session != null && session.isConnected()){
			return session;
		}
		
		Session newSession = openSession(sshHost, sshPort, sshUser, sshPass);
		sessionPool.put(sshHost, newSession);
		return session;
	}
	
	/**
	 * 打开一个新的SSH跳板机会话
	 * @param sshHost 跳板机主机
	 * @param sshPort 跳板机端口
	 * @param sshUser 跳板机用户名
	 * @param sshPass 跳板机密码
	 * @return SSH会话
	 */
	synchronized public static Session openSession(String sshHost, int sshPort, String sshUser, String sshPass){
		if(LangUtil.isEmpty(sshHost) || sshPort < 0 || LangUtil.isEmpty(sshUser) || LangUtil.isEmpty(sshPass)) return null;
		
		try {
			Session session = new JSch().getSession(sshUser, sshHost, sshPort);
			session.setPassword(sshPass);
			session.setConfig("StrictHostKeyChecking", "no");
			session.connect();
			
			return session;
		} catch (JSchException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 绑定端口到本地。
	 * 一个会话可绑定多个端口
	 * @param session 需要绑定端口的SSH会话
	 * @param remoteHost 远程主机
	 * @param remotePort 远程端口
	 * @param localPort 本地端口
	 * @return 成功与否
	 * @throws JSchException 端口绑定失败异常
	 */
	synchronized public static boolean bindPort(Session session, String remoteHost, int remotePort, int localPort) throws JSchException{
		if(session != null && session.isConnected()){
			session.setPortForwardingL(localPort, remoteHost, remotePort);
			return true;
		}
		return false;
	}
	
	/**
	 * 解除端口映射
	 * @param session 需要解除端口映射的SSH会话
	 * @param localPort 需要解除的本地端口
	 * @return 解除成功与否
	 */
	synchronized public static boolean unBindPort(Session session, int localPort){
		try {
			session.delPortForwardingL(localPort);
			return true;
		} catch (JSchException e) {
			logger.error("端口映射解除失败，原因是：" + e.getMessage());
		}
		return false;
	}
	
	/**
	 * 打开SSH会话，并绑定远程端口到本地的一个随机端口
	 * @param sshConn SSH连接信息对象
	 * @param remoteHost 远程主机
	 * @param remotePort 远程端口
	 * @return 映射后的本地端口
	 * @throws ConnException
	 */
	synchronized public static int openAndBindPortToLocal(Connector sshConn, String remoteHost, int remotePort) throws ConnException{
		Session session = openSession(sshConn.getHost(), sshConn.getPort(), sshConn.getUser(), sshConn.getPassword());
		if (session == null) {
			throw new ConnException("SSH会话建立失败，请检查SSH相关配置！");
		}
		int localPort = generateLocalPort();
		try {
			SSHUtil.bindPort(session, remoteHost, remotePort, localPort);
		} catch (JSchException e) {
			throw new ConnException("From [" + remoteHost + "] Mapping to [" + localPort + "] error！", e);
		}
		return localPort;
	}
	
	/**
	 * 关闭SSH连接会话
	 * @param session SSH会话
	 */
	synchronized public static void close(Session session){
		if(session != null && session.isConnected()){
			session.disconnect();
		}
	}
	
	/**
	 * 关闭SSH连接会话
	 * @param host 主机
	 */
	synchronized public static void close(String host){
		Session session = sessionPool.get(host);
		if(session != null && session.isConnected()){
			session.disconnect();
		}
		sessionPool.remove(host);
	}
	
	/**
	 * 关闭所有SSH连接会话
	 */
	synchronized public static void closeAll(){
		Collection<Session> sessions = sessionPool.values();
		for (Session session : sessions) {
			if(session.isConnected()) session.disconnect();
		}
		sessionPool.clear();
	}
	
}
