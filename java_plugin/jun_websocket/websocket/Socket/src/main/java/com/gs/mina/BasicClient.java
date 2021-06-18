package com.gs.mina;

import com.gs.Constants;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by Wang Genshen on 2017-04-18.
 */
public class BasicClient {

    public static void main(String[] args) throws IOException, InterruptedException {
        Socket socket = new Socket(Constants.IP, Constants.PORT);
        PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
        printWriter.write("hello");
        printWriter.flush();
        InputStream in = socket.getInputStream();
        byte[] bytes = new byte[1024];
        int length = in.read(bytes);
        System.out.println(new String(bytes, 0, length, "utf-8"));
        Thread.sleep(100000);
    }
}
