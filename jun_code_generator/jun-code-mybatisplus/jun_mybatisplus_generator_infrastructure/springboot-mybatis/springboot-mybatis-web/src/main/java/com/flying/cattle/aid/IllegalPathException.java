/**
 * @filename: IllegalPathException 2019年5月20日
 * @project springboot-mybatis  V1.0
 * Copyright(c) 2018 flying-cattle Co. Ltd. 
 * All right reserved. 
 */
package com.flying.cattle.aid;

import lombok.Getter;
import lombok.Setter;

/**   
 * Copyright: Copyright (c) 2019 
 * 
 * <p>说明： 路径视图等异常请求处理</P>
 * @version: V1.0
 * @author: flying-cattle
 * 
 */
public class IllegalPathException extends Exception {
  
	private static final long serialVersionUID = -2761914589358288904L;
	@Setter
	@Getter
	private String message;

    public IllegalPathException() {
        setMessage("This is an illegal path request!");
    }

    public IllegalPathException(String message) {
        this.message = message;
    }

}
