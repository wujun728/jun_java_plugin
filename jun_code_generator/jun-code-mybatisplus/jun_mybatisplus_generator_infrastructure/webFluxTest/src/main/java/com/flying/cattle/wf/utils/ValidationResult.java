/**
 * @filename:User 2019年6月1日
 * @project webFlux-redis  V1.0
 * Copyright(c) 2018 BianPeng Co. Ltd. 
 * All right reserved. 
 */
package com.flying.cattle.wf.utils;


/**   
 * Copyright: Copyright (c) 2019 
 * 
 * <p>说明： 验证结果集</P>
 * @version: V1.0
 * @author: BianPeng
 * 
 */
import java.util.Map;

import lombok.Data;

/**
 *	 校验结果
 * 	@author BianPeng
 */
@Data
public class ValidationResult {
 
    // 校验结果是否有错
    private boolean hasErrors;
    
    // 校验的第一个错误
    private String FirstErrors;
 
    // 校验错误信息
    private Map<String, String> errorMsg;
}