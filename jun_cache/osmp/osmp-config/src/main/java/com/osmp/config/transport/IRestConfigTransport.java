/*   
 * Project: OSMP
 * FileName: IRestConfigTransport.java
 * version: V1.0
 */
package com.osmp.config.transport;

import javax.ws.rs.core.Response;

public interface IRestConfigTransport {
    public Response postForRefresh(String target) ;
    public Response getForRefresh(String target) ;
}
