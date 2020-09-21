/*   
 * Project: OSMP
 * FileName: CxfClientException.java
 * version: V1.0
 */
package com.osmp.http.client.exception;

public class CxfClientException extends Exception {
    private static final long serialVersionUID = 1489111148128622267L;
    
    public CxfClientException(){
        super();
    }
    public CxfClientException(String msg){
        super(msg);
    }
    
    public CxfClientException(Exception e){
        super(e);
    }
    
    public CxfClientException(String msg,Exception e){
        super(msg,e);
    }

}
