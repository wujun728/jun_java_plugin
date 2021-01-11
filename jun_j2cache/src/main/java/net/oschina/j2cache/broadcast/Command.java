package net.oschina.j2cache.broadcast;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.Charset;

/**
 * 广播消息命令封装
 * <P><规则：/P>
 * <ul>
 *     <li>第 1        个字节为命令代码，长度为1byte [OPT]</li>
 *     <li>第 2至3     个字节为缓存region长度，长度为2 [R_LEN]</li>
 *     <li>第 4至N     为region值，长度为N-4 [R_LEN]</li>
 *     <li>第 N+1至N+2 为key长度，长度为2 [K_LEN]</li>
 *     <li>第 N+3至M   为key内容容，长度为M-(N+3)</li>
 * </ul>
 *
 * @author FY
 */
public class Command {
    private static final Logger logger = LoggerFactory.getLogger(Command.class);
    private static final Charset UTF_8 = Charset.forName("UTF-8");

    private final byte operator;
    private final String region;
    private final String key;

    public Command(byte operator, String region, Object key) {
        this.operator = operator;
        this.region = region;
        this.key = String.valueOf(key);
    }

    public byte getOperator() {
        return operator;
    }

    public String getRegion() {
        return region;
    }

    public Object getKey() {
        return key;
    }

    // -----------------------------------------------------------------------

    /**
     * 转换为传输buff
     * @return {@link byte}
     */
    public byte[] toBuff() {
        byte[] kBuff = key.getBytes(UTF_8);
        byte[] rBytes = region.getBytes(UTF_8);

        int rLen = rBytes.length;
        int kLen = kBuff.length;

        byte[] buff = new byte[5 + rLen + kLen];
        int idx = 0;

        buff[idx] = operator;

        buff[++idx] = (byte) (rLen >> 8);
        buff[++idx] = (byte) (rLen & 0xFF);
        System.arraycopy(rBytes, 0, buff, ++idx, rLen);
        idx += rLen;

        buff[idx++] = (byte) (kLen >> 8);
        buff[idx++] = (byte) (kLen & 0xFF);
        System.arraycopy(kBuff, 0, buff, idx, kLen);

        return buff;
    }

    /**
     * 接收到广播消息后，解析为{@link Command}对象
     * @param buff 消息buff数据
     * @return {@link Command}
     */
    public static Command parse(byte[] buff) {
        Command cmd = null;

        try {
            int idx = 0;
            byte operator = buff[idx]; // 取得操作KEY, 如：{@see CacheCustoms#OPT_DELTED_KEY}

            int rLen = buff[++idx] << 8;
            rLen += buff[++idx];

            if (rLen > 0) {
                String region = new String(buff, ++idx, rLen, UTF_8);
                idx += rLen;

                int kLen = buff[idx++] << 8;
                kLen += buff[idx++];

                if (kLen > 0) {
                    byte[] kBuff = new byte[kLen];
                    System.arraycopy(buff, idx, kBuff, 0, kLen);
                    Object key = new String(kBuff, UTF_8);
                    cmd = new Command(operator, region, key);
                }
            }

        } catch (Exception e) {
            logger.error("解析缓存JGroup广播事件消息为Command对象失败.", e);
        }

        return cmd;
    }
}
