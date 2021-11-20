package com.lli;

import java.util.Collection;
import java.util.List;

import org.apache.cxf.endpoint.Client;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
import org.apache.cxf.service.model.BindingInfo;
import org.apache.cxf.service.model.BindingMessageInfo;
import org.apache.cxf.service.model.BindingOperationInfo;
import org.apache.cxf.service.model.MessagePartInfo;
import org.apache.cxf.service.model.ServiceInfo;

import cn.hutool.core.codec.Base64;

import com.lli.webservice.CommonService;
import com.lli.webservice.User;

public class CxfClient {
    public static void main(String[] args) throws Exception {
        String param = Base64
                .encode("<?xml version=\"1.0\" encoding=\"utf-8\"?>"
                        + "<request>" + "<AHDM>" + Base64.encode("1234")
                        + "</AHDM>" + "</request>");
        System.out.println(param);
        cl3();
    }

    /**
     * 方式1.代理类工厂的方式,需要拿到对方的接口
     */
    public static void cl1(String param) {
        try {
            // 接口地址
            String address = "http://localhost:9999/services/CommonService?wsdl";
            // 代理工厂
            JaxWsProxyFactoryBean jaxWsProxyFactoryBean = new JaxWsProxyFactoryBean();
            // 设置代理地址
            jaxWsProxyFactoryBean.setAddress(address);
            // 设置接口类型
            jaxWsProxyFactoryBean.setServiceClass(CommonService.class);
            // 创建一个代理接口实现
            CommonService cs = (CommonService) jaxWsProxyFactoryBean.create();
            // 数据准备
            // String userName = "Leftso";
            // 调用代理接口的方法调用并返回结果
            String result = cs.sayHello(param);
            String decodeStr = Base64.decodeStr(result);
            System.out.println("返回结果:\n" + decodeStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 动态调用方式
     *
     * @throws Exception
     */
    public static void cl2() throws Exception {
        // 创建动态客户端
        JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
        Client client = dcf
                .createClient("http://localhost:9999/services/CommonService?wsdl");
        // getUser 为接口中定义的方法名称 张三为传递的参数 返回一个Object数组
        Object[] objects = client.invoke("getUser", "411001");
        for (Object object : objects) {
            User ss = (User) object;
            System.err.println(ss);
        }

        // 输出调用结果
        System.out.println(((User) objects[0]).getAge());
        System.out.println(objects[0].toString());
    }

    /**
     * 动态调用方式
     *
     * @throws Exception
     */
    public static void cl3() throws Exception {
        JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
        Client client = dcf
                .createClient("http://www.webxml.com.cn/WebServices/IpAddressSearchWebService.asmx?wsdl");

        List<ServiceInfo> infos = client.getEndpoint().getService()
                .getServiceInfos();
        for (ServiceInfo serviceInfo : infos) {
            Collection<BindingInfo> dd = serviceInfo.getBindings();
            for (BindingInfo bindingInfo2 : dd) {
                Collection<BindingOperationInfo> bb = bindingInfo2
                        .getOperations();
                for (BindingOperationInfo bindingOperationInfo : bb) {
                    System.out.println(bindingOperationInfo.getName().getLocalPart());
                    BindingMessageInfo inputMessageInfo = bindingOperationInfo
                            .getInput();
                    inputMessageInfo.getMessageInfo();
                    List<MessagePartInfo> parts = inputMessageInfo
                            .getMessageParts();
                    for (MessagePartInfo messagePartInfo : parts) {
                        System.out.println(messagePartInfo.getName().getLocalPart());
                    }
                }
            }
        }
        // for (BindingOperationInfo operationInfo :
        // bindingInfo.getOperations()) {
        // System.out.println(operationInfo.getInput());
        // System.out.println(operationInfo.getOutput());
        // System.out.println(operationInfo.getName());
        // System.out.println(operationInfo.getName().getLocalPart());
        // }

    }
}