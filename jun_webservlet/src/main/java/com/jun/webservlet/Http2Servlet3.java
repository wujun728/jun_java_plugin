package com.jun.webservlet;

import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Queue;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import javax.servlet.AsyncContext;
import javax.servlet.AsyncEvent;
import javax.servlet.AsyncListener;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(value = {"/http23"}, asyncSupported = true)
public class Http2Servlet3 extends HttpServlet {

    private Queue<String> messages = new ConcurrentLinkedQueue<String>();
    private final Executor executor = Executors.newFixedThreadPool( 10 );
    private List<AsyncContext> ctxs = new ArrayList<AsyncContext>();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        res.setContentType( "text/plain" );
        res.setCharacterEncoding( "utf-8" );
        res.setHeader( "Access-Control-Allow-Origin", "*" );
        PrintWriter writer = res.getWriter();
        writer.print( "2;Hi;" );
        writer.flush();

        final AsyncContext ctx = req.startAsync();
        ctx.addListener( new AsyncListener() {
            @Override
            public void onStartAsync(AsyncEvent event) throws IOException {
            }
            @Override
            public void onTimeout(AsyncEvent event) throws IOException {
                ctxs.remove( ctx );
            }
            @Override
            public void onError(AsyncEvent event) throws IOException {
                ctxs.remove( ctx );
            }
            @Override
            public void onComplete(AsyncEvent event) throws IOException {
                ctxs.remove( ctx );
            }
        } );
        ctxs.add( ctx );
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        res.setContentType( "text/plain" );
        res.setCharacterEncoding( "utf-8" );
        messages.add( createRandomMessage() );
    }

    @Override
    public void init() throws ServletException {
        super.init();
        // produce random messages
        //生成随机消息
        new Thread( new Runnable() {
            @Override
            public void run() {
                while (true) {
                    messages.add( createRandomMessage() );
                    try {
                        Thread.sleep( new Random().nextInt( 5 ) * 1000 );
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        } ).start();

        // print messages to all users
        //向所有用户打印message

        new Thread( new Runnable() {
            @Override
            public void run() {
                while (true) {
                    if (!messages.isEmpty()) {
                        final String message = messages.poll();
                        executor.execute( new Runnable() {
                            @Override
                            public void run() {
                                for (AsyncContext ctx : ctxs) {
                                    try {
                                        PrintWriter writer = ctx.getResponse().getWriter();
                                        writer.print( message.length() );
                                        writer.print( ';' );
                                        writer.print( message );
                                        writer.print( ';' );
                                        writer.flush();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            };
                        } );
                    }
                }
            }
        } ).start();
    }

    protected String createRandomMessage() {
        return DateFormat.getTimeInstance().format( Calendar.getInstance().getTime() ) + ' ' + UUID.randomUUID().toString();
    }
}