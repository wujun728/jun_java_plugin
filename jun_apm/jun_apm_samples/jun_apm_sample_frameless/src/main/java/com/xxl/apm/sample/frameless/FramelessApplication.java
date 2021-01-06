package com.xxl.apm.sample.frameless;


import com.xxl.apm.client.XxlApm;
import com.xxl.apm.client.message.impl.XxlApmEvent;
import com.xxl.apm.client.message.impl.XxlApmHeartbeat;
import com.xxl.apm.client.message.impl.XxlApmTransaction;
import com.xxl.apm.sample.frameless.conf.FrameLessXxlApmConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @author xuxueli 2018-11-10 20:05:33
 */
public class FramelessApplication {
    private static Logger logger = LoggerFactory.getLogger(FramelessApplication.class);

    public static void main(String[] args) {
        try {

            // start
            FrameLessXxlApmConfig.start();
            TimeUnit.SECONDS.sleep(1);


            // event
            XxlApm.report(new XxlApmEvent("URL", "/user/add"));

            // transaction
            XxlApmTransaction transaction = new XxlApmTransaction("URL", "/user/query");
            TimeUnit.MILLISECONDS.sleep(5);
            transaction.setStatus("fail");
            transaction.setParam(new HashMap<String, String>(){{
                put("userid", "1001");
            }});
            //XxlApm.report(transaction);

            // mock mult-thread report
            for (int j = 0; j < 50; j++) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        for (int i = 0; i < 100; i++) {
                            XxlApmTransaction transaction2 = new XxlApmTransaction("URL", "/user/mock");
                            try {
                                TimeUnit.MILLISECONDS.sleep(new Random().nextInt(3));
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            XxlApm.report(transaction2);

                        }
                    }
                }).start();
            }
            // mock end

            // heartbeat
            XxlApm.report(new XxlApmHeartbeat());


            while (true) {
                TimeUnit.SECONDS.sleep(5);
            }

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {

            // stop
            FrameLessXxlApmConfig.stop();
        }
    }

}
