package com.buxiaoxa;

import com.buxiaoxia.Application;
import com.buxiaoxia.business.entity.Log;
import com.buxiaoxia.business.repository.LogRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by xw on 2017/2/23.
 * 2017-02-23 12:55
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {Application.class})
public class ApplicationTest {

	@Autowired
	private LogRepository logRepository;

	@Before
	public void before() {
		Log log = new Log();
		log.setCreateAt(new Date());
		log.setContent("日志1");
		logRepository.save(log);

		Log log2 = new Log();
		log2.setCreateAt(new Date());
		log2.setContent("日志1");
		logRepository.save(log2);
	}

	@Test
	public void testSaveLog() {
		Log log = new Log();
		log.setCreateAt(new Date());
		log.setContent("日志1");
		logRepository.save(log);
		assertNotNull(log.getId());
	}

	@Test
	public void testSaveMapLog() {
		Log log = new Log();
		Map map = new HashMap();
		map.put("a", 1);
		map.put("b", "b");
		log.setOther(map);
		log.setCreateAt(new Date());
		log.setContent("日志1");
		logRepository.save(log);
		assertNotNull(log.getId());
	}

	@Test
	public void testFindLog() {
		Log log = logRepository.findAll().get(0);
		assertNotNull(log.getId());
		assertEquals("日志1", log.getContent());
	}

}
