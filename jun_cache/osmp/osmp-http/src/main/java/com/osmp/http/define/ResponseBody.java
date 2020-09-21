/*   
 * Project: OSMP
 * FileName: ResponseBody.java
 * version: V1.0
 */
package com.osmp.http.define;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 
 * Description:
 * @author: wangkaiping
 * @date: 2016年8月8日 上午11:42:23上午10:51:30
 */
@XmlRootElement(name="ResponseBody")
@XmlAccessorType
public class ResponseBody implements Serializable{
    private static final long serialVersionUID = 1L;
    private String code;
    private String message;
    private Object data;
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
    public Object getData() {
        return data;
    }
    public void setData(Object data) {
        this.data = data;
    }
    

    public String toString() {
        return "ResponseMessage [code=" + code + ", message=" + message
                + ", data=" + data + "]";
    }
    
}
