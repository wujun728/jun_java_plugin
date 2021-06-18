package com.jun.plugin.fastrpc.client;

import java.net.InetSocketAddress;
import java.util.concurrent.TimeUnit;

import com.jun.plugin.fastrpc.client.FastRpcClient;
import com.jun.plugin.fastrpc.client.IClient;

/**
 * @author peiyu
 */
public class ClientTest {

    public static void main(String[] args) {
        try(IClient client = new FastRpcClient()) {
            client.connect(new InetSocketAddress("127.0.0.1", 4567));
            ITestService service = client.getService("test", ITestService.class);
            String say = service.say("Hello!");
            System.out.println(say);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
