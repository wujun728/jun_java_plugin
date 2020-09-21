/*   
 * Project: OSMP
 * FileName: SoapPlatformTransport.java
 * version: V1.0
 */
package com.osmp.http.transport;

import java.util.Map;

import javax.jws.WebService;
import javax.servlet.http.HttpServletRequest;

import org.apache.cxf.message.Message;
import org.apache.cxf.phase.PhaseInterceptorChain;
import org.apache.cxf.transport.http.AbstractHTTPDestination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

import com.osmp.http.define.ResponseBody;
import com.osmp.http.define.RtCodeConst;
import com.osmp.http.service.DataServiceProxy;
import com.osmp.http.service.ServiceFactoryManager;
import com.osmp.intf.define.config.FrameConst;
import com.osmp.intf.define.model.InvocationDefine;
import com.osmp.intf.define.model.ServiceContext;
import com.osmp.utils.base.JSONUtils;
import com.osmp.utils.net.RequestInfoHelper;

/**
 * 
 * Description:统一服务接口SOAP
 * @author: wangkaiping
 * @date: 2016年8月9日 上午9:17:10上午10:51:30
 */
@WebService(portName="port",serviceName="data",endpointInterface = "com.osmp.http.transport.ISoapPlatformTransport")
public class SoapPlatformTransport implements ISoapPlatformTransport,InitializingBean{
    
    private Logger logger = LoggerFactory.getLogger(RestPlatformTransport.class);
    private ServiceFactoryManager serviceFactoryManager;

    public void afterPropertiesSet() throws Exception {
        Assert.notNull(serviceFactoryManager,"PlatformServiceTransport property dataServiceManager not set..");
    }
    
    public void setServiceFactoryManager(ServiceFactoryManager serviceFactoryManager) {
        this.serviceFactoryManager = serviceFactoryManager;
    }
    
    public ResponseBody data(String source, String interfaceName, String parameter) {
        Message message = PhaseInterceptorChain.getCurrentMessage();  
        HttpServletRequest request = (HttpServletRequest)message.get(AbstractHTTPDestination.HTTP_REQUEST);
        String ip = RequestInfoHelper.getRemoteIp(request);
        System.out.println("requstId:"+request.getAttribute(FrameConst.CLIENT_REQ_ID));
        System.out.println("ip:"+ip);
        ResponseBody res = new ResponseBody();
        // 获取对应服务
        InvocationDefine define = serviceFactoryManager.getInvocationDefine(interfaceName);
        if (define == null) {
            res.setCode(RtCodeConst.ERR_CODE);
            res.setMessage("未找到服务");
            logger.warn("未找到服务...");
            return res;
        }
        // 接口参数验证
        Map<String, String> sourceMap = JSONUtils.jsonString2Map(source);
        if (sourceMap == null || sourceMap.get(FrameConst.CLIENT_FROM) == null) {
            res.setCode(RtCodeConst.ERR_CODE);
            res.setMessage("接口参数非法");
            logger.warn("接口参数非法...");
            return res;
        }
        sourceMap.put(FrameConst.CLIENT_IP, ip);
        // 调用服务
        ServiceContext serviceContext = new ServiceContext(sourceMap, interfaceName, parameter);
        DataServiceProxy serviceProxy = new DataServiceProxy(define, serviceContext);
        Object result = serviceProxy.execute();

        res.setCode(RtCodeConst.SUCC_CODE);
        res.setData(result);
        return res;
    }
    

}
