package com.techsoft.pool;

import java.sql.Connection;

import junit.framework.Assert;
import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.techsoft.ConnectionPool;
import com.techsoft.TestConsts;

public class TestConnectionPool extends TestCase {
	private Connection connone = null;
	private Connection conntwo = null;

	@Before
	protected void setUp() throws Exception {
		super.setUp();
	}

	@After
	protected void tearDown() throws Exception {
		if (connone != null) {
			connone.close();
			connone = null;
		}
		if (conntwo != null) {
			conntwo.close();
			conntwo = null;
		}
		super.tearDown();
	}

	@Test
	public void testGetConnection() {
		try {
			connone = TestConsts.getPool().getConnection();
			assertEquals(2, TestConsts.getPool().getMaxPool());
			assertEquals(1, TestConsts.getPool().getBusyPool());

			conntwo = TestConsts.getPool().getConnection();
			assertEquals(2, TestConsts.getPool().getMaxPool());
			assertEquals(2, TestConsts.getPool().getBusyPool());

			TestConsts.getPool().getConnection();
			fail("最大池已满时，还能取得连接, 出错!");

		} catch (Exception e) {
			Assert.assertTrue(e.getMessage().equalsIgnoreCase(
					ConnectionPool.MAXPOOLLIMIT));
		}
	}

}
