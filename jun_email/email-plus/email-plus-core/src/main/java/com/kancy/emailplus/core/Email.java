package com.kancy.emailplus.core;

import java.io.File;
import java.util.Collections;
import java.util.Date;
import java.util.Map;
import java.util.Objects;

/**
 * Email
 *
 * @author kancy
 * @date 2020/2/19 23:03
 */
public interface Email {
    /**
     * 消息id
     * @return
     */
    String getId();
    /**
     * 收件人
     * @return
     */
    String[] getTo();

    /**
     * 抄送
     * @return
     */
    String[] getCc();

    /**
     * 主题
     * @return
     */
    String getSubject();

    /**
     * 邮件内容
     * @return
     */
    String getContent();

    /**
     * 附件
     * @return
     */
    default Map<String, File> getFiles(){
        return Collections.emptyMap();
    }

    /**
     * html格式
     * @return
     */
    default boolean isHtml(){
        return false;
    }

    /**
     * isMultipart
     * @return
     */
    default boolean isMultipart(){
        Map<String, File> files = getFiles();
        return Objects.nonNull(files) && !files.isEmpty();
    }

    /**
     * 设置发送时间
     * @param sendDate
     */
    void setSentDate(Date sendDate);
}
