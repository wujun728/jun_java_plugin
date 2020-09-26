package com.server.process;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @author CYX
 * @create 2018-07-30-21:36
 */
public class FileConvertProcessor implements Processor {

    @Override
    public void process(Exchange exchange) throws Exception {
        InputStream body = null;
        try {
            body = exchange.getIn().getBody(InputStream.class);
            BufferedReader in = new BufferedReader(new InputStreamReader(body));
            StringBuffer strbf = new StringBuffer("");
            String str = null;
            str = in.readLine();
            while (str != null) {
                System.out.println(str);
                strbf.append(str + " ");
                str = in.readLine();
            }
            //exchange.getOut().setHeader(Exchange.FILE_NAME, "converted.txt");
            System.out.println("文件名 ： " + exchange.getIn().getHeader(Exchange.FILE_NAME));
            System.out.println("绝对路径 ： " + exchange.getIn().getHeader("CamelFileAbsolutePath"));
            System.out.println("最后修改时间 ： " + exchange.getIn().getHeader("CamelFileLastModified"));
            System.out.println("文件大小： " + exchange.getIn().getHeader("CamelFileLength"));

            exchange.getOut().setHeader(Exchange.FILE_NAME, exchange.getIn().getHeader(Exchange.FILE_NAME));

            // set the output to the file
            exchange.getOut().setBody(strbf.toString());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            body.close();
        }

    }
}
