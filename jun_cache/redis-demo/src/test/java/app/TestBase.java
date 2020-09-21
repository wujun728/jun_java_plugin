package app;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.transaction.TransactionConfiguration;

@ContextConfiguration(locations={"classpath:applicationContext.xml","classpath*:/durcframework_spring/*.xml"})
@TransactionConfiguration(defaultRollback=false)
public class TestBase extends AbstractJUnit4SpringContextTests {

}
