package org.itkk.udf.core.utils;

import org.itkk.udf.core.constant.IdWorkerConstant;
import org.itkk.udf.core.domain.id.Id;

import java.util.Random;

/**
 * IdUtil
 */
public class IdUtil {

    /**
     * 私有化构造函数
     */
    private IdUtil() {

    }

    /**
     * 反解析ID
     *
     * @param id id
     * @return id信息
     */
    public static Id reverse(long id) {
        Id reverseId = new Id();
        reverseId.setSequence((id) & ~(-1L << IdWorkerConstant.SEQUENCE_BITS));
        reverseId.setDwId((id >> (IdWorkerConstant.WORKER_ID_SHIFT)) & ~(-1L << (IdWorkerConstant.DATACENTER_ID_BITS + IdWorkerConstant.WORKER_ID_BITS)));
        reverseId.setWorkerId((id >> IdWorkerConstant.WORKER_ID_SHIFT) & ~(-1L << IdWorkerConstant.WORKER_ID_BITS));
        reverseId.setDatacenterId((id >> IdWorkerConstant.DATACENTER_ID_SHIFT) & ~(-1L << IdWorkerConstant.DATACENTER_ID_BITS));
        reverseId.setTimestamp((id >> IdWorkerConstant.TIMESTAMP_LEFT_SHIFT) + IdWorkerConstant.TWEPOCH);
        return reverseId;
    }

    /**
     * 生成6位随机字符串(0-9,A-Z)
     *
     * @param length length
     * @return String
     */
    public static String genRandomString(int length) {
        final String source = "1234567890abcdefghijklmnopqrstuvwsyz";
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(source.charAt(random.nextInt(source.length() - 1)));
        }
        return sb.toString().toUpperCase();
    }

}
