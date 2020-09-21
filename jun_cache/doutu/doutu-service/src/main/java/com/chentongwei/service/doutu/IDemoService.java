package com.chentongwei.service.doutu;

import java.util.List;

import com.chentongwei.entity.doutu.io.DemoIO;
import com.chentongwei.entity.doutu.vo.DemoVO;

/**
 * Hello world!
 *
 */
public interface IDemoService {
	
	DemoVO getByID(Integer id);
	
	List<DemoVO> list(DemoIO demoIO);

	void testSlowLog();

	boolean save(DemoIO demoIO);
}
