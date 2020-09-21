/*   
 * Project: OSMP
 * FileName: ExecutorWrapper.java
 * version: V1.0
 */
package com.osmp.web.demo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.osmp.web.demo.dao.DemoTeacherMapper;
import com.osmp.web.demo.entity.DemoTeacher;
import com.osmp.web.demo.service.DemoTeacherService;
import com.osmp.web.utils.Pagination;

/**
 * Description:
 * 
 * @author: wangkaiping
 * @date: 2014年10月13日 下午3:14:34
 */
@Service
public class DemoTeacherServiceImpl implements DemoTeacherService {

	@Autowired
	private DemoTeacherMapper studentMapper;

	@Override
	public void getTeacher(Pagination<DemoTeacher> page) {
		List<DemoTeacher> list = studentMapper.selectByPage(page, DemoTeacher.class, null);
		page.setList(list);
	}

	@Override
	public void insert(DemoTeacher teacher) {
		studentMapper.insert(teacher);
	}

}
