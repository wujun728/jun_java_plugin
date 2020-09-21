package com.chentongwei.dao.doutu;

import com.chentongwei.entity.doutu.io.DemoIO;
import com.chentongwei.entity.doutu.vo.DemoVO;

import java.util.List;

/**
 * Hello world!
 *
 */
public interface IDemoDAO {
	
	DemoVO getByID(Integer id);
	
	List<DemoVO> list(DemoIO demoIO);

	boolean save(DemoIO demoIO);
}
