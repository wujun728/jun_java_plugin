package com.leo.spring.service;

import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * WebSocketService
 *
 * @author Wujun
 * @date 2017-1-11
 */
@ServerEndpoint("/ws")
@Component
public class WebSocketService {
    private static int onlineCount = 0;
    private static CopyOnWriteArraySet<WebSocketService> webSocketSet = new CopyOnWriteArraySet<>();

    private Session session;

    @OnOpen
    public void onOpen (Session session){
        this.session = session;
        webSocketSet.add(this);
        addOnlineCount();
        System.out.println("有新链接加入!当前在线人数为" + getOnlineCount());
    }

    @OnClose
    public void onClose (){
        webSocketSet.remove(this);
        subOnlineCount();
        System.out.println("有一链接关闭!当前在线人数为" + getOnlineCount());
    }

    @OnMessage
    public void onMessage (String message, Session session) throws IOException {
        System.out.println("来自客户端的消息:" + message);
        // 群发消息
        for ( WebSocketService item : webSocketSet ){
            item.sendMessage(message);
        }
    }

    public void sendMessage (String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }

    public static synchronized  int getOnlineCount (){
        return WebSocketService.onlineCount;
    }

    public static synchronized void addOnlineCount (){
        WebSocketService.onlineCount++;
    }

    public static synchronized void subOnlineCount (){
        WebSocketService.onlineCount--;
    }
}
