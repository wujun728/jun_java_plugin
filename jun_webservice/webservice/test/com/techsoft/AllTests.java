package com.techsoft;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.techsoft.executor.TestOracleExecutor;
import com.techsoft.pool.TestConnectionPool;

@RunWith(Suite.class)
@SuiteClasses({ TestConnectionPool.class, TestOracleExecutor.class })
public class AllTests {

}
