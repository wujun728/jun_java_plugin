package net.oschina.j2cache.serializer;

import org.junit.Before;
import org.junit.Test;

/** 
 * 序列化工具的简单性能测试
 * @author Wujun
 *
 */
public class SerializerBenchmarkTest {

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

    /**
     * 使用StringSerializer序列化字符串[cacheKey]，共 10000 次，耗时：7 ms
     * 使用StringSerializer序列化字符串[cacheKey]，共 100000 次，耗时：17 ms
     * 使用StringSerializer序列化字符串[cacheKey]，共 1000000 次，耗时：85 ms
     * 使用StringSerializer序列化字符串[cacheKey]，共 10000000 次，耗时：943 ms
     */
    @Test
    public void testStringSerializer() {
        int count = 10000000;
        long startTime = System.currentTimeMillis();
        // 循环测试
        for (int i = 0; i < count; i++) {
            byte[] bytes = stringSerializer.serialize(cacheKey);
        }

        System.out.println(String.format("使用StringSerializer序列化字符串[%s]，共 %d 次，耗时：%d ms", cacheKey, count, System.currentTimeMillis() - startTime));
    }

    /**
     * 使用FstSerializer序列化{@link #cacheValue}，共 10000 次，耗时：45 ms
     * 使用FstSerializer序列化{@link #cacheValue}，共 100000 次，耗时：121 ms
     * 使用FstSerializer序列化{@link #cacheValue}，共 1000000 次，耗时：436 ms
     * 使用FstSerializer序列化{@link #cacheValue}，共 10000000 次，耗时：2660 ms
     */
    @Test
    public void testFstSerializer() {
        int count = 10000000;
        long startTime = System.currentTimeMillis();
        // 循环测试
        for (int i = 0; i < count; i++) {
            byte[] bytes = fstSerializer.serialize(cacheValue);
        }

        System.out.println(String.format("使用FstSerializer序列化{@link #cacheValue}，共 %d 次，耗时：%d ms", count, System.currentTimeMillis() - startTime));
    }

    /**
     * 使用FstSnappySerializer序列化{@link #cacheValue}，共 10000 次，耗时：134 ms
     * 使用FstSnappySerializer序列化{@link #cacheValue}，共 100000 次，耗时：249 ms
     * 使用FstSnappySerializer序列化{@link #cacheValue}，共 1000000 次，耗时：963 ms
     * 使用FstSnappySerializer序列化{@link #cacheValue}，共 10000000 次，耗时：6678 ms
     */
    @Test
    public void testFstSnappy() {
        int count = 10000000;
        long startTime = System.currentTimeMillis();
        // 循环测试
        for (int i = 0; i < count; i++) {
            byte[] bytes = snappyFstSerializer.serialize(cacheValue);
        }

        System.out.println(String.format("使用FstSnappySerializer序列化{@link #cacheValue}，共 %d 次，耗时：%d ms", count, System.currentTimeMillis() - startTime));
    }

    /**
     * 使用JdkSerializer序列化{@link #cacheValue}，共 10000 次，耗时：68 ms
     * 使用JdkSerializer序列化{@link #cacheValue}，共 100000 次，耗时：303 ms
     * 使用JdkSerializer序列化{@link #cacheValue}，共 1000000 次，耗时：1565 ms
     * 使用JdkSerializer序列化{@link #cacheValue}，共 10000000 次，耗时：10152 ms
     */
    @Test
    public void testJdk() {
        int count = 10000000;
        long startTime = System.currentTimeMillis();
        // 循环测试
        for (int i = 0; i < count; i++) {
            byte[] bytes = jdkSerializer.serialize(cacheValue);
        }

        System.out.println(String.format("使用JdkSerializer序列化{@link #cacheValue}，共 %d 次，耗时：%d ms", count, System.currentTimeMillis() - startTime));
    }

}
