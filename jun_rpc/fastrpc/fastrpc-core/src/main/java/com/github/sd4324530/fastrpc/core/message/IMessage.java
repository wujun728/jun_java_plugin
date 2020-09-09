package com.github.sd4324530.fastrpc.core.message;

import java.io.Serializable;

/**
 * 消息接口
 * 消息依靠序列号来标识一次请求和一次响应
 *
 * @author peiyu
 */
public interface IMessage extends Serializable {

    /**
     * 设置序列号
     *
     * @param seq 序列号
     */
    void setSeq(String seq);

    /**
     * 获取序列号
     *
     * @return 序列号
     */
    String getSeq();

}
