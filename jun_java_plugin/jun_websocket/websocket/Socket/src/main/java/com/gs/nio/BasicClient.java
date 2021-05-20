package com.gs.nio;

import com.gs.Constants;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Random;

/**
 * Created by Wang Genshen on 2017-04-18.
 */
public class BasicClient {

    public static void main(String[] args) throws IOException, InterruptedException {
        for (int i = 0; i < 2000; i++) {
            Socket socket = new Socket(Constants.IP, Constants.PORT);
            new Thread(new WriteThread(socket, i)).start();
        }
    }

    private static class WriteThread implements Runnable {
        private Socket socket;
        private int num;
        public WriteThread(Socket socket, int num) {
            this.socket = socket;
            this.num = num;
        }
        public void run() {
            OutputStream out = null;
            try {
                out = socket.getOutputStream();
                while (true) {
                    out.write(("socket" + num).getBytes());
                    out.flush();
                    InputStream in = socket.getInputStream();
                    byte[] bytes = new byte[1024];
                    int length = in.read(bytes);
                    System.out.println("socket" + num + "=====" + new String(bytes, 0, length, "utf-8"));
                    System.out.println("=========================");
                    Thread.sleep(5000);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
