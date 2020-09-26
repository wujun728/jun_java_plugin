package com.camel.helloworld;

import org.apache.camel.*;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.file.GenericFile;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.model.MulticastDefinition;

import java.io.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class FileCopierWithCamel {

    public static void main(String[] args) throws Exception {

        final ExecutorService executor = new ThreadPoolExecutor(10, 15, 3, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());

        CamelContext context = new DefaultCamelContext();

        context.addRoutes(new RouteBuilder() {

            @Override
            public void configure() throws Exception {
//                from("file:D:/A/inbox?noop=true&idempotent=false").process(new MyProcessor()).to("file:D:/A/outbox");

                // 加上线程池：编码顺序不能乱
                MulticastDefinition multicastDefinition = from("file:D:/A/inbox?noop=true&idempotent=false").multicast();
                multicastDefinition.setExecutorService(executor);
                multicastDefinition.setParallelProcessing(true);
                multicastDefinition.process(new MyProcessor()).to("file:D:/A/outbox");
            }
        });

        context.start();

        // 通用没有具体业务意义的代码，只是为了保证主线程不退出
        synchronized (FileCopierWithCamel.class) {
            FileCopierWithCamel.class.wait();
        }

    }




    /**
     * 处理器
     */
    public static class MyProcessor implements Processor {

        public void process(Exchange exchange) throws Exception {// 无法修改文件内容的
            // 因为很明确消息格式是http的，所以才使用这个类
            // 否则还是建议使用org.apache.camel.Message这个抽象接口
            Message message = exchange.getIn();
            FileInputStream bodyStream = new FileInputStream((File)( message.getBody(GenericFile.class)).getFile());
            String inputContext = this.analysisMessage(bodyStream);
            bodyStream.close();
            System.out.println("文件名："+message);// 多线程下该方法是无效的
            System.out.println("文件body对象："+message.getBody());
            System.out.println("文件内容:"+inputContext);
            // 存入到exchange的out区域
            exchange.setPattern(ExchangePattern.InOut);
            if (exchange.getPattern() == ExchangePattern.InOut) {
                Message outMessage = exchange.getOut();
                outMessage.setBody((inputContext + " ??????????????????????????????????????????????????/|| out"));
            }
        }

        /**
         * 从stream中分析字符串内容
         *
         * @param bodyStream
         * @return
         */
        private String analysisMessage(InputStream bodyStream) throws IOException {
            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
            byte[] contextBytes = new byte[4096];
            int realLen;
            while ((realLen = bodyStream.read(contextBytes, 0, 4096)) != -1) {
                outStream.write(contextBytes, 0, realLen);
            }

            // 返回从Stream中读取的字串
            try {
                return new String(outStream.toByteArray(), "UTF-8");
            } finally {
                outStream.close();
            }
        }
    }

}
