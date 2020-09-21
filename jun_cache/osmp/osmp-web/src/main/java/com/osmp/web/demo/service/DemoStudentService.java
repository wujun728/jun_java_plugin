/*   
 * Project: OSMP
 * FileName: ExecutorWrapper.java
 * version: V1.0
 */
package com.osmp.web.demo.service;

import com.osmp.web.demo.entity.DemoStudent;
import com.osmp.web.utils.Pagination;

/**
 * Description:
 * 
 * @author: wangkaiping
 * @date: 2014年10月13日 下午3:14:01
 */

public interface DemoStudentService {

	public void getStudent(Pagination<DemoStudent> page);

}
