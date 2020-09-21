/*   
 * Project: OSMP
 * FileName: ExecutorWrapper.java
 * version: V1.0
 */
package com.osmp.web.demo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.osmp.web.demo.dao.DemoStudentMapper;
import com.osmp.web.demo.entity.DemoStudent;
import com.osmp.web.demo.service.DemoStudentService;
import com.osmp.web.utils.Pagination;

/**
 * Description:
 * 
 * @author: wangkaiping
 * @date: 2014年10月13日 下午3:14:34
 */
@Service
public class DemoStudentServiceImpl implements DemoStudentService {

	@Autowired
	private DemoStudentMapper studentMapper;

	@Override
	public void getStudent(Pagination<DemoStudent> p) {
		List<DemoStudent> list = studentMapper.selectByPage(p, DemoStudent.class, null);
		p.setList(list);
	}

}
