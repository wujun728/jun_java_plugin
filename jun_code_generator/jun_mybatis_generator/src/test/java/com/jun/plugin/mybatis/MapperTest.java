package com.jun.plugin.mybatis;

import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

import com.jun.plugin.bizservice.mapper.TestclobMapper;
import com.jun.plugin.bizservice.pojo.Testclob;

/**
 * 动态代理实现类测试用例
 * 
 */
public class MapperTest {

	private TestclobMapper test;

	@Before
	public void setUp() throws Exception {
		String resource = "mybatis-config.xml";
		InputStream inputStream = Resources.getResourceAsStream(resource);
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		SqlSession sqlSession = sqlSessionFactory.openSession(true);
		// 通过sqlSession获取到动态代理的实现类
		// try {
		// TestclobMapper mapper = sqlSession.getMapper(TestclobMapper.class);
		// Testclob blog = mapper.selectByPrimaryKey(1);
		// System.out.println(blog);
		// }catch (Exception e) {
		// e.printStackTrace();
		// }
		this.test = sqlSession.getMapper(TestclobMapper.class);
	}

	@Test
	public void testLogin() {
		Testclob obj = this.test.selectByPrimaryKey(1);
		System.out.println(obj.getResume());
	}
}