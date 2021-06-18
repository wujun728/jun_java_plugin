import java.util.Date;

/**
 * Created by xiao-kang on 2017/5/31.
 */
public class MemcachedTest {

    public static void main(String[] args) {
        MemcachedUtil.put("aa", "aaaaa", new Date(5000)); // 指定失效时间为5秒钟，单位为ms
        System.out.println((String) MemcachedUtil.get("aa"));
        try {
            Thread.sleep(10 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println((String) MemcachedUtil.get("aa"));
    }
}
