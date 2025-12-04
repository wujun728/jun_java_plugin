/**   
 * Copyright © 2019 dream horse Info. Tech Ltd. All rights reserved.
 * @Package: com.gitee.mybatis.fl.entity
 * @author: flying-cattle  
 * @date: 2019年4月9日 下午8:15:25 
 */
package com.gitee.flying.cattle.mdg.entity;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**   
 * Copyright: Copyright (c) 2019 
 * 
 * <p>说明： 返回结果json对象</P>
 * @version: v3.0.0
 * @author: flying-cattle
 * 
 * Modification History:
 * Date         	Author          Version          Description
 *---------------------------------------------------------------*
 * 2019年4月9日      		flying-cattle   v3.0.0           initialize
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultJson implements Serializable{
	
	private static final long serialVersionUID = 123126L;
	
	private Integer code;
	
	private String message;
	
	private Object data;
}
