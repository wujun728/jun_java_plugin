package com.chentongwei.service.doutu.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chentongwei.dao.doutu.IDemoDAO;
import com.chentongwei.entity.doutu.io.DemoIO;
import com.chentongwei.entity.doutu.vo.DemoVO;
import com.chentongwei.service.doutu.IDemoService;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DemoServiceImpl implements IDemoService {

	private static final Logger LOG = LoggerFactory.getLogger(DemoServiceImpl.class);

	@Autowired
	private IDemoDAO demoDAO;
	
	@Override
	public DemoVO getByID(Integer id) {
		return demoDAO.getByID(id);
	}

	@Override
	public List<DemoVO> list(DemoIO demoIO) {
//		throw new BussinessException("1", "老子测试一下");
		return demoDAO.list(demoIO);
	}

	@Override
	public void testSlowLog() {
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		LOG.info("----------------记录慢日志-----------------");
	}

	@Override
	@Transactional
	public boolean save(DemoIO demoIO) {
		demoIO.setAge(123);
		demoDAO.save(demoIO);
		int i = 1/0;
		demoIO.setAge(456);
		return demoDAO.save(demoIO);
	}

}
