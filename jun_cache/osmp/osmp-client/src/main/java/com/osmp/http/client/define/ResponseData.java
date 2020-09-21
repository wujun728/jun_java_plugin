/*   
 * Project: OSMP
 * FileName: ResponseData.java
 * version: V1.0
 */
package com.osmp.http.client.define;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="ResponseBody")
@XmlAccessorType
public class ResponseData implements Serializable{
    private static final long serialVersionUID = 1L;
    private String code;
    private String message;
    private String data;
    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public String getData() {
        return data;
    }
    public void setData(String data) {
        this.data = data;
    }
    

    public String toString() {
        return "ResponseMessage [code=" + code + ", message=" + message
                + ", data=" + data + "]";
    }
    
}
