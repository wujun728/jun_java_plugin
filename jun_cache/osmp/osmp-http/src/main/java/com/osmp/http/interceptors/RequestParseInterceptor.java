/*   
 * Project: OSMP
 * FileName: RequestParseInterceptor.java
 * version: V1.0
 */
package com.osmp.http.interceptors;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;
import org.apache.cxf.transport.http.AbstractHTTPDestination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.osmp.intf.define.config.FrameConst;

/**
 * Description:接口转发生成唯一序号
 * @author: wangkaiping
 * @date: 2016年8月8日 上午11:44:19上午10:51:30
 */
public class RequestParseInterceptor extends AbstractPhaseInterceptor<Message> {
    private Logger logger = LoggerFactory.getLogger(RequestParseInterceptor.class);
    
    public RequestParseInterceptor() {
		super(Phase.RECEIVE);
	}

	public RequestParseInterceptor(String i, String p) {
		super(i, p);
	}

	public void handleMessage(Message message) throws Fault {
	    HttpServletRequest request = (HttpServletRequest) message.get(AbstractHTTPDestination.HTTP_REQUEST);
	    //生成一个请求序列号
	    request.setAttribute(FrameConst.CLIENT_REQ_ID, generateUuid()+"-"+System.currentTimeMillis());
	    logger.debug("requstId:"+request.getAttribute(FrameConst.CLIENT_REQ_ID));
	}
	
	
    private String[] chars = new String[] { "a", "b", "c", "d", "e", "f",  
        "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s",  
        "t", "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5",  
        "6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H", "I",  
        "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",  
        "W", "X", "Y", "Z" };  

    private String generateUuid() {  
        StringBuffer shortBuffer = new StringBuffer();  
        String uuid = UUID.randomUUID().toString().replace("-", "");  
        for (int i = 0; i < 8; i++) {  
            String str = uuid.substring(i * 4, i * 4 + 4);  
            int x = Integer.parseInt(str, 16);  
            shortBuffer.append(chars[x % 0x3E]);  
        }  
        return shortBuffer.toString();  
    }  
}
