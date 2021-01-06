package com.xxl.apm.client;

import com.xxl.apm.client.factory.XxlApmFactory;
import com.xxl.apm.client.message.XxlApmMsg;
import com.xxl.apm.client.message.impl.XxlApmEvent;
import com.xxl.apm.client.message.impl.XxlApmTransaction;
import com.xxl.rpc.util.IpUtil;
import com.xxl.rpc.util.ThrowableUtil;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * @author xuxueli 2018-12-22 18:31:48
 */
public class XxlApm {


    private XxlApmFactory xxlApmFactory;

    public XxlApm(XxlApmFactory xxlApmFactory) {
        this.xxlApmFactory = xxlApmFactory;
    }


    // ---------------------- instance tool ----------------------

    private static XxlApm instance = null;

    /**
     * set instance of XxlApm
     *
     * @param xxlApmFactory
     */
    public synchronized static void setInstance(XxlApmFactory xxlApmFactory) {
        if (XxlApm.instance != null) {
            throw new RuntimeException("xxl-apm, repeat generate XxlApm.");
        }
        XxlApm.instance = new XxlApm(xxlApmFactory);
    }


    /**
     * remove ParentMsgId
     */
    public static void removeParentMsgId(){
        instance.xxlApmFactory.parentMsgId.remove();
    }

    /**
     * set ParentMsgId
     *
     * @param value
     */
    public static void setParentMsgId(String value){
        instance.xxlApmFactory.parentMsgId.set(value);
    }

    /**
     * get ParentMsgId
     *
     * @return
     */
    public static String getParentMsgId(){
        return instance.xxlApmFactory.parentMsgId.get();
    }


    /**
     * generate MsgId
     *
     * @return
     */
    public static String generateMsgId(){
        return instance.xxlApmFactory.generateMsgId();
    }

    /**
     * get appname
     * @return
     */
    public static String getAppname() {
        return instance.xxlApmFactory.getAppname();
    }

    /**
     * get address
     *
     * @return
     */
    public static String getAddress(){
        String address = instance.xxlApmFactory.getAddress();
        if (address!=null && address.trim().length()>0) {
            return address;
        }
        return IpUtil.getIp();
    }

    /**
     * computing TP
     *
     * @param transaction
     */
    public static void computingTP(XxlApmTransaction transaction){
        instance.xxlApmFactory.computingTP(transaction);
    }

    // ---------------------- report tool ----------------------

    /**
     * report msg
     *
     * @param msgList
     * @return
     */
    public static boolean report(List<XxlApmMsg> msgList){
        // valid
        if (msgList==null || msgList.size()==0) {
            return false;
        }

        // complete msg
        for (XxlApmMsg apmMsg: msgList) {
            apmMsg.complete();
        }

        // async report
        return instance.xxlApmFactory.report(msgList);
    }

    /**
     * report msg
     *
     * @param apmMsg
     * @return
     */
    public static boolean report(XxlApmMsg apmMsg){
        // valid
        if (apmMsg == null) {
            return false;
        }

        // async report
        return report(Arrays.asList(apmMsg));
    }

    /**
     * report msg
     *
     * @param cause
     * @return
     */
    public static boolean report(final Throwable cause){
        String type = (cause instanceof Error)?"Error":(cause instanceof RuntimeException)?"RuntimeException":"Exception";
        String name = (cause.getCause()!=null?cause.getCause():cause).getClass().getName();

        XxlApmEvent xxlApmEvent = new XxlApmEvent(type, name);
        xxlApmEvent.setStatus(XxlApmEvent.ERROR_STATUS);
        xxlApmEvent.setParam(new HashMap<String, String>(){{
            put(XxlApmEvent.ERROR_STATUS, ThrowableUtil.toString(cause));
        }});

        return report(xxlApmEvent);
    }

}
