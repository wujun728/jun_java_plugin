package com.yzm.scoket;

import java.io.IOException;
import java.io.Serializable;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicInteger;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.yzm.common.cache.GlobalStore;
import com.yzm.vo.CheckVo;

@ServerEndpoint("/v2/getCheckWebScoket")
@Component
public class GetCheckV2WebScoket implements Serializable {
	// 静态变量，用来记录当前在线连接数。
	private static AtomicInteger onlineCount = new AtomicInteger(0);
	
	// concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。若要实现服务端与单一客户端通信的话，可以使用Map来存放，其中Key可以为用户标识
	public static CopyOnWriteArraySet<GetCheckV2WebScoket> webSocketSet = new CopyOnWriteArraySet<GetCheckV2WebScoket>();

	// 与某个客户端的连接会话，需要通过它来给客户端发送数据
	private Session session;

	private static final Logger logger = LoggerFactory.getLogger(GetCheckV2WebScoket.class);

	public GetCheckV2WebScoket() {
		System.out.println(" GetCheckV2WebScoket init~~~");
	}

	/**
	 * 连接建立成功调用的方法
	 * 
	 * @param session
	 *            可选的参数。session为与某个客户端的连接会话，需要通过它来给客户端发送数据
	 */
	@OnOpen
	public void onOpen(Session session) {
		this.session = session;
		webSocketSet.add(this); // 加入set中
		addOnlineCount(); // 在线数加1
		System.out.println("有新连接加入！当前在线人数为" + getOnlineCount());
	}

	/**
	 * 连接关闭调用的方法
	 */
	@OnClose
	public void onClose() {
		webSocketSet.remove(this); // 从set中删除
		subOnlineCount(); // 在线数减1
		System.out.println("有一连接关闭！当前在线人数为" + getOnlineCount());
	}

	/**
	 * 收到客户端消息后调用的方法
	 * 
	 * @param message
	 *            客户端发送过来的消息
	 * @param session
	 *            可选的参数
	 */
	@OnMessage
	public void onMessage(String message, Session session) {
		logger.info("生成check成功:" + message);
		String[] resultArray = message.split("&");
		GlobalStore.responseQueue.offer(new CheckVo(resultArray[0], resultArray[1]));
	}

	/**
	 * 发生错误时调用
	 * 
	 * @param session
	 * @param error
	 */
	@OnError
	public void onError(Session session, Throwable error) {
		System.out.println("发生错误");
		error.printStackTrace();
	}

	/**
	 * 这个方法与上面几个方法不一样。没有用注解，是根据自己需要添加的方法。
	 * 
	 * @param message
	 * @throws IOException
	 */
	public void sendMessage(String message) throws IOException {
		this.session.getBasicRemote().sendText(message);
		// this.session.getAsyncRemote().sendText(message);
	}

	public static int getOnlineCount() {
		return onlineCount.get();
	}

	public static void addOnlineCount() {
		onlineCount.incrementAndGet();
	}

	public static void subOnlineCount() {
		onlineCount.decrementAndGet();
	}

}
