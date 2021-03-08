package com.zh.springbootwebsocket.config;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * websocket实现
 * @author Wujun
 * @date 2019/1/3 17:54
 */
@Slf4j
@Component
@Data
@ServerEndpoint(value = "/websocket/{userName}")
public class WebSocket {

    private String userName;

    private Session session;

    public static List<WebSocket> webSockets = new ArrayList<>(16);

    @OnOpen
    public void onOpen(@PathParam("userName") String userName, Session session) {
        this.userName = userName;
        this.session = session;
        webSockets.add(this);
        this.sendLoginMsg(userName);
        this.updateOnline(this.listOnline());
        log.info("【websocket消息】有新的连接, 总数:{}", webSockets.size());
    }


    @OnClose
    public void onClose() {
        this.sendLogoutMsg(this.getUserName());
        this.updateOnline(this.listOnline());
        webSockets.remove(this);
        log.info("【websocket消息】连接断开, 总数:{}", webSockets.size());
    }

    public List<JSONObject> listOnline(){
        List<JSONObject> list =  WebSocket.webSockets.stream().map(e -> {
            JSONObject json = new JSONObject();
            json.put("text",e.getUserName());
            json.put("value",e.getUserName());
            return json;
        }).collect(Collectors.toList());
        JSONObject json = new JSONObject();
        json.put("text","请选择");
        json.put("value","");
        list.add(0,json);
        return list;
    }

    public void updateOnline(List<JSONObject> list){
        JSONObject json = new JSONObject();
        json.put("type","2");
        json.put("msg",list);
        this.sendMsg(json.toJSONString());
    }

    public void sendLoginMsg(String userName){
        String msg = userName + "上线啦~~~\n";
        JSONObject json = new JSONObject();
        json.put("type","-1");
        json.put("msg",msg);
        this.sendMsg(json.toJSONString());
    }

    public void sendLogoutMsg(String userName){
        String msg = userName + "下线啦!!!\n";
        JSONObject json = new JSONObject();
        json.put("type","-2");
        json.put("msg",msg);
        this.sendMsg(json.toJSONString());
    }

    @OnMessage
    public void onMessage(String message) {
        log.info("【websocket消息】收到客户端发来的消息:{}", message);
    }

    /**
     * 点对点广播消息
     * @param toUserName 接收用户
     * @param fromUserName 发送用户
     * @param msg 消息
     */
    public void sendP2PMsgBy2UserName(String fromUserName,String toUserName,String msg){
        for (WebSocket webSocket: webSockets) {
            log.info("【websocket消息】点对点广播消息, userName={},message={}", toUserName,msg);
            try {
                if (webSocket.userName.equals(toUserName)) {
                    JSONObject json = new JSONObject();
                    json.put("type","1");
                    json.put("fromUserName",fromUserName);
                    json.put("msg",msg);
                    webSocket.session.getBasicRemote().sendText(json.toJSONString());
                }
            } catch (Exception e) {
                log.error("=================websocket点对点广播消息出错===================");
                log.error(e.getMessage(),e);
            }
        }
    }

    /**
     * 全体广播消息
     * @param msg 消息
     */
    public void sendMsg(String fromUserName,String msg){
        msg = fromUserName + ":\n" + msg + "\n";
        for (WebSocket webSocket: webSockets) {
            log.info("【websocket消息】全体广播消息,message={}",msg);
            try {
                JSONObject json = new JSONObject();
                json.put("type","0");
                json.put("msg",msg);
                webSocket.session.getBasicRemote().sendText(json.toJSONString());
            } catch (Exception e) {
                log.error("=================websocket全体广播消息出错===================");
                log.error(e.getMessage(),e);
            }
        }
    }

    /**
     * 全体广播消息
     * @param jsonMsg 消息
     */
    public void sendMsg(String jsonMsg){
        for (WebSocket webSocket: webSockets) {
            log.info("【websocket消息】全体广播消息,message={}",jsonMsg);
            try {
                webSocket.session.getBasicRemote().sendText(jsonMsg);
            } catch (Exception e) {
                log.error("=================websocket全体广播消息出错===================");
                log.error(e.getMessage(),e);
            }
        }
    }

}
