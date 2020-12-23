package net.oschina.j2cache.serializer;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/** 
 *  序列化工具测试  
 * @author Wujun
 *
 */
public class SerializerBaseTest {
    private String cacheKey = "cacheKey";
    private TestObj cacheValue;

    private StringSerializer stringSerializer;
    private FstSerializer<TestObj> fstSerializer;
    private FstSnappySerializer<TestObj> snappyFstSerializer;
    private JdkSerializer<TestObj> jdkSerializer;

    @Before
    public void init() {
        stringSerializer = new StringSerializer();
        fstSerializer = new FstSerializer<TestObj>();
        snappyFstSerializer = new FstSnappySerializer<TestObj>();
        jdkSerializer = new JdkSerializer<TestObj>();

        cacheValue = new TestObj();
        cacheValue.setId("123");
        cacheValue.setName("wod");

    }

    @Test
    public void testStringSerializer() {
        byte[] bytes = stringSerializer.serialize(cacheKey);

        Assert.assertEquals(cacheKey, stringSerializer.deserialize(bytes));

    }

    @Test
    public void testFstSerializer() {
        byte[] bytes = fstSerializer.serialize(cacheValue);

        Assert.assertEquals(cacheValue, fstSerializer.deserialize(bytes));
    }

    @Test
    public void testFstSnappy() {
        byte[] bytes = snappyFstSerializer.serialize(cacheValue);

        Assert.assertEquals(cacheValue, snappyFstSerializer.deserialize(bytes));
    }

    @Test
    public void testJdk() {
        byte[] bytes = jdkSerializer.serialize(cacheValue);

        Assert.assertEquals(cacheValue, jdkSerializer.deserialize(bytes));
    }

}
