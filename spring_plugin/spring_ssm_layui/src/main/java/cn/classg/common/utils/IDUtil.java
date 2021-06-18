package cn.classg.common.utils;

import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.MessageDigest;
import java.util.UUID;

/**
 *  用于生产各种各样的ID
 */
public class IDUtil {

    private static String middle = "";

    static {
        middle = MathUtils.makeUpNewData(Math.abs(NetworkUtils.getHostIP().hashCode()) + "", 4) +   //4位IP地址hash
                MathUtils.makeUpNewData(NetworkUtils.getPid(), 4);                                 //4位PID进程hash
    }

    /**
     * 以毫微秒做基础计数, 返回唯一有序增长ID
     * <pre>System.nanoTime()</pre>
     * <pre>
     *  线程数量:   100
     *  执行次数:   1000
     *  平均耗时:   222 ms
     *  数组长度:   100000
     *  Map Size:   100000
     * </pre>
     * @return  ID长度32位
     */
    public static String getIncreaseIdByNanoTime(){
        return System.nanoTime()+                                                       //时间戳-14位
                middle+                                                                  //标志-8位
                MathUtils.makeUpNewData(Thread.currentThread().hashCode()+"", 3)+        //3位线程标志
                MathUtils.randomDigitNumber(7);                                          //随机7位数
    }

    /**
     * 以毫秒做基础计数, 返回唯一有序增长ID, 有几率出现线程并发
     * <pre>System.currentTimeMillis()</pre>
     * <pre>
     *  线程数量:   100
     *  执行次数:   1000
     *  平均耗时:   206 ms
     *  数组长度:   100000
     *  Map Size:   99992
     * </pre>
     * @return  ID长度32位
     */
    public static String getIncreaseIdByCurrentTimeMillis(){
        return  System.currentTimeMillis()+                                             //时间戳-14位
                middle+                                                                 //标志-8位
                MathUtils.makeUpNewData(Thread.currentThread().hashCode()+"", 3)+       //3位线程标志
                MathUtils.randomDigitNumber(8);                                         //随机8位数
    }

    /**
     * 基于UUID+MD5产生唯一无序ID
     * <pre>
     *  线程数量:   100
     *  执行次数:   1000
     *  平均耗时:       591 ms
     *  数组长度:   100000
     *  Map Size:   100000
     * </pre>
     * @return  ID长度32位
     */
    public static String getRandomIdByUUID(){
        return DigestUtils.md5Hex(UUID.randomUUID().toString());
    }

    /* ---------------------------------------------分割线------------------------------------------------ */
    /** 字符串MD5处理类 */
    private static class DigestUtils {

        private static final char[] DIGITS_LOWER =
                {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

        private static final char[] DIGITS_UPPER =
                {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

        private static char[] encodeHex(final byte[] data, final char[] toDigits) {
            final int l = data.length;
            final char[] out = new char[l << 1];
            for (int i = 0, j = 0; i < l; i++) {
                out[j++] = toDigits[(0xF0 & data[i]) >>> 4];
                out[j++] = toDigits[0x0F & data[i]];
            }
            return out;
        }

        public static String md5Hex(String str){
            return md5Hex(str, false);
        }

        public static String md5Hex(String str, boolean isUpper){
            try {
                return new String(encodeHex(MessageDigest.getInstance("MD5").digest(str.getBytes("UTF-8")), isUpper ? DIGITS_UPPER : DIGITS_LOWER));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }


    /* ---------------------------------------------分割线------------------------------------------------ */
    /** 网络相关的处理类  */
    private static class NetworkUtils {

        private static final String DEFAULT_HOST_IP = "10.10.10.10";

        /**
         *  获取当前进程的PID
         */
        public static String getPid(){
            return ManagementFactory.getRuntimeMXBean().getName().split("@")[0];
        }

        /**
         *  获取当前进程的主机IP地址
         */
        public static String getHostIP(){
            try {
                return InetAddress.getLocalHost().getHostAddress();
            } catch (UnknownHostException e) {
                return DEFAULT_HOST_IP;
            }
        }

    }

    /* ---------------------------------------------分割线------------------------------------------------ */
    /** 数据处理的相关类  */
    private static class MathUtils {

        private static final String DEFAULT_DIGITS =                    "0";
        private static final String FIRST_DEFAULT_DIGITS =              "1";

        /**
         * @param target    目标数字
         * @param length    需要补充到的位数, 补充默认数字[0], 第一位默认补充[1]
         * @return          补充后的结果
         */
        public static String makeUpNewData(String target, int length){
            return makeUpNewData(target, length, DEFAULT_DIGITS);
        }

        /**
         * @param target    目标数字
         * @param length    需要补充到的位数
         * @param add       需要补充的数字, 补充默认数字[0], 第一位默认补充[1]
         * @return          补充后的结果
         */
        public static String makeUpNewData(String target, int length, String add){
            if(target.startsWith("-")) target.replace("-", "");
            if(target.length() >= length) return target.substring(0, length);
            StringBuffer sb = new StringBuffer(FIRST_DEFAULT_DIGITS);
            for (int i = 0; i < length - (1 + target.length()); i++) {
                sb.append(add);
            }
            return sb.append(target).toString();
        }

        /**
         * 生产一个随机的指定位数的字符串数字
         * @param length
         * @return
         */
        public static String randomDigitNumber(int length){
            int start = Integer.parseInt(makeUpNewData("", length));//1000+8999=9999
            int end = Integer.parseInt(makeUpNewData("", length + 1)) - start;//9000
            return (int)(Math.random() * end) + start + "";
        }

    }

    public static void main(String[] args) {
        System.out.println(IDUtil.getIncreaseIdByCurrentTimeMillis());
        System.out.println(IDUtil.getIncreaseIdByNanoTime());
        System.out.println(IDUtil.getRandomIdByUUID());
    }

}
