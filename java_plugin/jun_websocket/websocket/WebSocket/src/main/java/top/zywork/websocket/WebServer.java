package top.zywork.websocket;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

/**
 * Created by Wang Genshen on 2017-09-20.
 */
@ServerEndpoint("/websocket/test")
public class WebServer {

    private boolean isRunning = false;

    @OnOpen
    public void open(final Session session) {
        isRunning = true;
        System.out.println("open: " + session.getId());
        final RemoteEndpoint.Basic basic = session.getBasicRemote();
        new Thread(new Runnable() {
            public void run() {
                while (true) {
                    if (isRunning) {
                        try {
                            Thread.sleep(5000);
                            if (session.isOpen()) {
                                basic.sendText("hi");
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    } else {
                        break;
                    }
                }
            }
        }).start();

    }

    @OnMessage
    public void onMessage(Session session, String message) throws IOException, InterruptedException {
        System.out.println("message: " + session.getId());
        System.out.println("Received: " + message);
        session.getBasicRemote().sendText("Hello, Client!");
        int sentMessages = 0;
        while (sentMessages < 3) {
            Thread.sleep(5000);
            session.getBasicRemote().sendText("This is an intermediate server message. Count: " + sentMessages);
            sentMessages++;
        }
        session.getBasicRemote().sendText("This is the last server message");
    }

    @OnClose
    public void close(Session session) {
        isRunning = false;
        System.out.println("close: " + session.getId());
    }

    @OnError
    public void error(Session session, Throwable throwable) {
        System.out.println("error: " + session.getId());
        System.out.println(throwable.getMessage());
    }
}
