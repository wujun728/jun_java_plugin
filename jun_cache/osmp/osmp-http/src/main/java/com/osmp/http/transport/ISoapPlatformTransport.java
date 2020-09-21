/*   
 * Project: OSMP
 * FileName: ISoapPlatformTransport.java
 * version: V1.0
 */
package com.osmp.http.transport;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import com.osmp.http.define.ResponseBody;
import com.osmp.intf.define.config.FrameConst;

/**
 * Description:统一服务接口 soap
 * @author: wangkaiping
 * @date: 2016年8月9日 上午9:15:42上午10:51:30
 */
@WebService(targetNamespace = "osmp.soap")
public interface ISoapPlatformTransport {
    @WebMethod(action = "data", operationName = "data")
    public ResponseBody data(@WebParam(name=FrameConst.SOURCE_NAME) String source,
            @WebParam(name=FrameConst.SERVICE_NAME) String interfaceName,
            @WebParam(name=FrameConst.PARAMETER_NAME) String parameter);
}
